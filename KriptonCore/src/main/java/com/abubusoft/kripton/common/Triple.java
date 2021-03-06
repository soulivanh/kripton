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
/**
 * 
 */
package com.abubusoft.kripton.common;

/**
 * @author Francesco Benincasa (abubusoft@gmail.com)
 *
 * @param <V1>
 * @param <V2>
 * @param <V3>
 */
public class Triple<V1, V2, V3> {

	public Triple()
	{
		
	}
	
	public Triple(V1 v1, V2 v2, V3 v3)
	{
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
	}
	
	public V1 v1;
	
	public V2 v2;
	
	public V3 v3;
}