/*******************************************************************************
 * Copyright 2015, 2017 Francesco Benincasa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.abubusoft.kripton.processor.bind.transform;

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * Transformer for java primitive types and frequently used java types
 * 
 */
public abstract class BindTransformer {

	/**
	 * cache for transform
	 */
	private static final Map<TypeName, BindTransform> cache = new ConcurrentHashMap<TypeName, BindTransform>();

	/**
	 * Get transformer for type
	 * 
	 * @return transform
	 */
	public static BindTransform lookup(BindProperty property) {
		TypeName typeName=property.getPropertyType().getTypeName();
		
		if (property.hasTypeAdapter()) {
			typeName = typeName(property.typeAdapter.dataType);
		}
		
		return lookup(typeName);
	}

	public static boolean isBindedObject(TypeName typeName) {
		BindTransform t = lookup(typeName);

		if (t != null && t instanceof ObjectBindTransform) {
			return true;
		}
		return false;
	}

	public static boolean isBindedObject(BindProperty property) {
		BindTransform t = lookup(property);

		if (t != null && t instanceof ObjectBindTransform) {
			return true;
		}
		return false;
	}

	/**
	 * Get transformer for type
	 * @param modelEntity 
	 * 
	 * @param typeName
	 * @return transform
	 */
	public static BindTransform lookup(TypeName typeName) {
		BindTransform transform = cache.get(typeName);

		if (transform != null) {
			return transform;
		}

		transform = getTransform(typeName);

		AssertKripton.assertTrueOrUnsupportedFieldTypeException(transform != null, typeName);
		// transform will be always valorized
		cache.put(typeName, transform);

		return transform;
	}

	static BindTransform getTransform(TypeName typeName) {
		if (typeName.isPrimitive()) {
			return getPrimitiveTransform(typeName);
		}

		if (typeName instanceof ArrayTypeName) {
			ArrayTypeName typeNameArray = (ArrayTypeName) typeName;

			if (TypeUtility.isEquals(typeNameArray.componentType, Byte.TYPE.toString())) {
				return new ByteArrayBindTransform();
			} else {
				return new ArrayBindTransform(typeNameArray.componentType, typeNameArray.componentType.isPrimitive());
			}
		} else if (typeName instanceof ParameterizedTypeName) {
			ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;			
			if (TypeUtility.isList(parameterizedTypeName.rawType)) {
				return new ListBindTransformation(parameterizedTypeName);
			} else if (TypeUtility.isSet(parameterizedTypeName.rawType)) {
				return new SetBindTransformation(parameterizedTypeName);
			} else if (TypeUtility.isMap(parameterizedTypeName.rawType)) {
				return new MapBindTransformation(parameterizedTypeName);
			}
		}

		String name = typeName.toString();

		if (name.startsWith("java.lang")) {
			return getLanguageTransform(typeName);
		}

		if (name.startsWith("java.util")) {
			return getUtilTransform(typeName);
		}

		if (name.startsWith("java.math")) {
			return getMathTransform(typeName);
		}

		if (name.startsWith("java.net")) {
			return getNetTransform(typeName);
		}

		if (name.startsWith("java.sql")) {
			return getSqlTransform(typeName);
		}

		if (TypeUtility.isEnum(typeName)) {
			return new EnumBindTransform(typeName);
		}

		return new ObjectBindTransform();
	}

	static BindTransform getSqlTransform(TypeName typeName) {
		if (Time.class.getName().equals(typeName.toString())) {
			return new TimeBindTransform();
		}

		return null;
	}

	static BindTransform getNetTransform(TypeName typeName) {
		if (URL.class.getName().equals(typeName.toString())) {
			return new UrlBindTransform();
		}

		return null;
	}

	static BindTransform getMathTransform(TypeName typeName) {
		if (BigDecimal.class.getName().equals(typeName.toString())) {
			return new BigDecimalBindTransform();
		} else if (BigInteger.class.getName().equals(typeName.toString())) {
			return new BigIntegerBindTransform();
		}

		return null;
	}

	/**
	 * Get Java primitive type Transformable
	 * 
	 * @param type
	 * @return
	 */
	static BindTransform getPrimitiveTransform(TypeName type) {

		if (Integer.TYPE.toString().equals(type.toString())) {
			return new IntegerBindTransform(false);
		}
		if (Boolean.TYPE.toString().equals(type.toString())) {
			return new BooleanBindTransform(false);
		}
		if (Long.TYPE.toString().equals(type.toString())) {
			return new LongBindTransform(false);
		}
		if (Double.TYPE.toString().equals(type.toString())) {
			return new DoubleBindTransform(false);
		}
		if (Float.TYPE.toString().equals(type.toString())) {
			return new FloatBindTransform(false);
		}
		if (Short.TYPE.toString().equals(type.toString())) {
			return new ShortBindTransform(false);
		}
		if (Byte.TYPE.toString().equals(type.toString())) {
			return new ByteBindTransform(false);
		}
		if (Character.TYPE.toString().equals(type.toString())) {
			return new CharacterBindTransform(false);
		}
		return null;
	}

	/**
	 * Get Java primitive wrapping type Transformable
	 * 
	 * @param type
	 * @return
	 */
	static BindTransform getLanguageTransform(TypeName type) {
		String typeName = type.toString();

		if (Integer.class.getCanonicalName().equals(typeName)) {
			return new IntegerBindTransform(true);
		}
		if (Boolean.class.getCanonicalName().equals(typeName)) {
			return new BooleanBindTransform(true);
		}
		if (Long.class.getCanonicalName().equals(typeName)) {
			return new LongBindTransform(true);
		}
		if (Double.class.getCanonicalName().equals(typeName)) {
			return new DoubleBindTransform(true);
		}
		if (Float.class.getCanonicalName().equals(typeName)) {
			return new FloatBindTransform(true);
		}
		if (Short.class.getCanonicalName().equals(typeName)) {
			return new ShortBindTransform(true);
		}
		if (Byte.class.getCanonicalName().equals(typeName)) {
			return new ByteBindTransform(true);
		}
		if (Character.class.getCanonicalName().equals(typeName)) {
			return new CharacterBindTransform(true);
		}
		if (String.class.getCanonicalName().equals(typeName)) {
			return new StringBindTransform();
		}
		return null;
	}

	/**
	 * Get java.util type Transformable
	 * 
	 * @param type
	 * @return
	 */

	static BindTransform getUtilTransform(TypeName type) {
		String typeName = type.toString();

		// Integer.class.getCanonicalName().equals(typeName)
		if (Date.class.getCanonicalName().equals(typeName)) {
			return new DateBindTransform();
		}
		if (Locale.class.getCanonicalName().equals(typeName)) {
			return new LocaleBindTransform();
		}
		if (Currency.class.getCanonicalName().equals(typeName)) {
			return new CurrencyBindTransform();
		}
		if (Calendar.class.getCanonicalName().equals(typeName)) {
			return new CalendarBindTransform();
		}
		if (TimeZone.class.getCanonicalName().equals(typeName)) {
			return new TimeZoneBindTransform();
		}
		return null;
	}
	
	

}
