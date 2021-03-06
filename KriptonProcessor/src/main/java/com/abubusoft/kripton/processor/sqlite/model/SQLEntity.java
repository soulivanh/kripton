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
package com.abubusoft.kripton.processor.sqlite.model;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import com.abubusoft.kripton.android.annotation.BindTable;
import com.abubusoft.kripton.processor.core.ModelClass;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;

public class SQLEntity extends ModelClass<SQLProperty> {

	private String tableName;

	/**
	 * Set of entities for which there's a foreign in this entities. In other
	 * words, rapresents entities from which this entity depends.
	 */
	public Set<SQLEntity> referedEntities = new HashSet<>();

	public SQLEntity(TypeElement element) {
		super(element);
	}

	/**
	 * Check how many PK are defined in entity. Only one field can be PK.
	 * 
	 * @return number of PK
	 */
	public int countPrimaryKeys() {
		int countAnnotation = 0;

		for (SQLProperty item : collection) {
			if (item.isPrimaryKey()) {
				countAnnotation++;
			}
		}

		return countAnnotation;
	}

	/**
	 * True if there is a primary key
	 * 
	 * @return true if there is a primary key
	 */
	public SQLProperty getPrimaryKey() {
		for (SQLProperty item : collection) {
			if (item.isPrimaryKey()) {
				return item;
			}
		}

		// try to get id
		SQLProperty id = findByName("id");

		return id;
	}

	public String getTableName() {
		return tableName;
	}

	public String buildTableName(Elements elementUtils, SQLiteDatabaseSchema model) {
		tableName = getSimpleName();
		if (containsAnnotation(BindTable.class)) {
			String temp = AnnotationUtility.extractAsString(elementUtils, getElement(), BindTable.class, AnnotationAttributeType.VALUE);

			if (temp != null && temp.length() > 0)
				tableName = temp;
		}
		tableName = model.classNameConverter.convert(tableName);

		return tableName;

	}

}
