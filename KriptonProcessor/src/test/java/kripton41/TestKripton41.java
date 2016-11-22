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
package kripton41;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import base.BaseProcessorTest;

/**
 * @author xcesco
 *
 */
@RunWith(JUnit4.class)
public class TestKripton41 extends BaseProcessorTest {

	@Test(expected=AssertionError.class)
	public void testSelectError() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy01DataSource.class, DaoBeanSelectERR.class, Bean01.class, BaseDao.class);
	}	
	
	@Test
	public void testSelectOK() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy02DataSource.class, DaoBeanSelectOK.class, Bean01.class, BaseDao.class);
	}	

	@Test(expected=AssertionError.class)
	public void testInsertError() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy03DataSource.class, DaoBeanInsertERR.class, Bean01.class, BaseDao.class);
	}	
	
	@Test
	public void testInsertOK() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy04DataSource.class, DaoBeanInsertOK.class, Bean01.class, BaseDao.class);
	}	
	
	@Test(expected=AssertionError.class)
	public void testUpdateError() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy05DataSource.class, DaoBeanUpdateERR.class, Bean01.class, BaseDao.class);
	}	
	
	@Test
	public void testUpdateOK() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy06DataSource.class, DaoBeanUpdateOK.class, Bean01.class, BaseDao.class);
	}	

	@Test(expected=AssertionError.class)
	public void testDeleteError() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy07DataSource.class, DaoBeanDeleteERR.class, Bean01.class, BaseDao.class);
	}	
	
	@Test
	public void testDeleteOK() throws IOException, InstantiationException, IllegalAccessException {
		buildDataSourceProcessorTest(Dummy08DataSource.class, DaoBeanDeleteOK.class, Bean01.class, BaseDao.class);
	}	

}