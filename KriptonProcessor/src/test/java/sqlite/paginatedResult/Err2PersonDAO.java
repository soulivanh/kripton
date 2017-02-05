package sqlite.paginatedResult;

import java.util.Date;

import com.abubusoft.kripton.android.annotation.BindDao;
import com.abubusoft.kripton.android.annotation.BindSqlInsert;
import com.abubusoft.kripton.android.annotation.BindSqlPageSize;
import com.abubusoft.kripton.android.annotation.BindSqlSelect;
import com.abubusoft.kripton.android.sqlite.PaginatedResult;

@BindDao(Err2Person.class)
public interface Err2PersonDAO {
	
	@BindSqlSelect(orderBy="name", pageSize=20)
	PaginatedResult<Err2Person> selectPagedStatic1();
	
	@BindSqlSelect(orderBy="name")
	PaginatedResult<Err2Person> selectPagedStatic2(@BindSqlPageSize long pageSize, @BindSqlPageSize int pageSize2);
	
	
	@BindSqlInsert
	void insertOne(String name, String surname, String birthCity, Date birthDay);
/*
	@BindSqlSelect(orderBy="name")
	List<Person> selectAll();
	
	@BindSqlSelect(where="name like ${nameTemp} || '%'")
	List<Person> selectOne(@BindSqlParam("nameTemp") String nameValue, @BindSqlWhere String where, @BindSqlOrderBy String orderBy);
	
	@BindSqlSelect(orderBy="name")
	void selectBeanListener(OnReadBeanListener<Person> beanListener, @BindSqlOrderBy String orderBy);*/
	
}