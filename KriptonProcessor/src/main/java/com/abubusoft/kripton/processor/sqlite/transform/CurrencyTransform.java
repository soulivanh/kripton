/*******************************************************************************
 * Copyright 2015, 2016 Francesco Benincasa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.abubusoft.kripton.processor.sqlite.transform;

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.setter;

import com.abubusoft.kripton.common.CurrencyUtil;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.MethodSpec.Builder;

/**
 * Transformer between a string and a java.util.Currency object
 * 
 * @author bulldog
 *
 */
class CurrencyTransform extends AbstractCompileTimeTransform {

	@Override
	public void generateReadProperty(Builder methodBuilder, TypeName beanClass, String beanName, ModelProperty property,
			String cursorName, String indexName) {
		methodBuilder.addCode("$L." + setter(beanClass, property, "$T.read($L.getString($L))"), beanName,
				CurrencyUtil.class, cursorName, indexName);

	}
	/*
	 * @Override public void generateRead(Builder methodBuilder, String
	 * cursorName, String indexName) {
	 * methodBuilder.addCode("$T.read($L.getString($L))", CurrencyUtil.class,
	 * cursorName, indexName); }
	 */

	@Override
	public void generateWriteProperty(Builder methodBuilder, TypeName beanClass, String beanName,
			ModelProperty property) {
		methodBuilder.addCode("$T.write($L)", CurrencyUtil.class, getter(beanName, beanClass, property));
	}

	@Override
	public void generateWriteProperty(Builder methodBuilder, String objectName) {
		methodBuilder.addCode("$T.write($L)", CurrencyUtil.class, objectName);
	}

	@Override
	public void generateResetProperty(Builder methodBuilder, TypeName beanClass, String beanName,
			ModelProperty property, String cursorName, String indexName) {
		methodBuilder.addCode("$L." + setter(beanClass, property, "null"), beanName);
	}

	@Override
	public String generateColumnType(ModelProperty property) {
		return "TEXT";
	}

	/*
	 * @Override public void generateDefaultValue(Builder methodBuilder) {
	 * methodBuilder.addCode("null"); }
	 */

}
