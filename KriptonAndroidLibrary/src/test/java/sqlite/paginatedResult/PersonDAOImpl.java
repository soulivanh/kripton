package sqlite.paginatedResult;

import android.content.ContentValues;
import android.database.Cursor;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.sqlite.AbstractDao;
import com.abubusoft.kripton.android.sqlite.OnReadBeanListener;
import com.abubusoft.kripton.android.sqlite.PaginatedResult;
import com.abubusoft.kripton.android.sqlite.SqlUtils;
import com.abubusoft.kripton.common.DateUtils;
import com.abubusoft.kripton.common.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * DAO implementation for entity <code>Person</code>, based on interface <code>PersonDAO</code>
 * </p>
 *
 *  @see Person
 *  @see PersonDAO
 *  @see PersonTable
 */
public class PersonDAOImpl extends AbstractDao implements PersonDAO {
  public PersonDAOImpl(BindPersonDataSource dataSet) {
    super(dataSet);
  }

  /**
   * <h2>Select SQL:</h2>
   *
   * <pre>SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName LIMIT 20#{paginatedResult}</pre>
   *
   * <h2>Projected columns:</h2>
   * <dl>
   * 	<dt>id</dt><dd>is associated to bean's property <strong>id</strong></dd>
   * 	<dt>name</dt><dd>is associated to bean's property <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is associated to bean's property <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is associated to bean's property <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is associated to bean's property <strong>birthDay</strong></dd>
   * </dl>
   *
   * @return paginated result.
   */
  @Override
  public PaginatedResult<Person> selectPagedStatic1() {
    PaginatedResult2 paginatedResult=new PaginatedResult2();
    return paginatedResult;
  }

  /**
   * <h2>Select SQL:</h2>
   *
   * <pre>SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName LIMIT 20#{paginatedResult}</pre>
   *
   * <h2>Projected columns:</h2>
   * <dl>
   * 	<dt>id</dt><dd>is associated to bean's property <strong>id</strong></dd>
   * 	<dt>name</dt><dd>is associated to bean's property <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is associated to bean's property <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is associated to bean's property <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is associated to bean's property <strong>birthDay</strong></dd>
   * </dl>
   *
   * @param paginatedResult
   * 	handler of paginated result
   * @return result list
   */
  private List<Person> selectPagedStatic1(PaginatedResult2 paginatedResult) {
    // build where condition
    String[] _args={};

    //StringUtils, SqlUtils will be used in case of dynamic parts of SQL
    Logger.info(SqlUtils.formatSQL("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName LIMIT 20"+SqlUtils.printIf(paginatedResult.firstRow()>0, " OFFSET "+paginatedResult.firstRow()),(Object[])_args));
    try (Cursor cursor = database().rawQuery("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName LIMIT 20"+SqlUtils.printIf(paginatedResult.firstRow()>0, " OFFSET "+paginatedResult.firstRow()), _args)) {
      Logger.info("Rows found: %s",cursor.getCount());

      List<Person> resultList=new ArrayList<Person>();
      Person resultBean=null;

      if (cursor.moveToFirst()) {

        int index0=cursor.getColumnIndex("id");
        int index1=cursor.getColumnIndex("name");
        int index2=cursor.getColumnIndex("surname");
        int index3=cursor.getColumnIndex("birth_city");
        int index4=cursor.getColumnIndex("birth_day");

        do
         {
          resultBean=new Person();

          if (!cursor.isNull(index0)) { resultBean.id=cursor.getLong(index0); }
          if (!cursor.isNull(index1)) { resultBean.name=cursor.getString(index1); }
          if (!cursor.isNull(index2)) { resultBean.surname=cursor.getString(index2); }
          if (!cursor.isNull(index3)) { resultBean.birthCity=cursor.getString(index3); }
          if (!cursor.isNull(index4)) { resultBean.birthDay=DateUtils.read(cursor.getString(index4)); }

          resultList.add(resultBean);
        } while (cursor.moveToNext());
      }

      return resultList;
    }
  }

  /**
   * <h2>Select SQL:</h2>
   *
   * <pre>SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName#{pageSize}#{paginatedResult}</pre>
   *
   * <h2>Projected columns:</h2>
   * <dl>
   * 	<dt>id</dt><dd>is associated to bean's property <strong>id</strong></dd>
   * 	<dt>name</dt><dd>is associated to bean's property <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is associated to bean's property <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is associated to bean's property <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is associated to bean's property <strong>birthDay</strong></dd>
   * </dl>
   *
   * <h2>Dynamic parts:</h2>
   * <dl>
   * <dt>#{pageSize}</dt>is part of limit statement resolved at runtime.</dd>
   * </dl>
   *
   * @param pageSize
   * 	is used as <strong>dynamic LIMIT statement</strong> and it is formatted by ({@link StringUtils#format})
   * @return paginated result.
   */
  @Override
  public PaginatedResult<Person> selectPagedStatic2(int pageSize) {
    PaginatedResult3 paginatedResult=new PaginatedResult3(pageSize);
    return paginatedResult;
  }

  /**
   * <h2>Select SQL:</h2>
   *
   * <pre>SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName#{pageSize}#{paginatedResult}</pre>
   *
   * <h2>Projected columns:</h2>
   * <dl>
   * 	<dt>id</dt><dd>is associated to bean's property <strong>id</strong></dd>
   * 	<dt>name</dt><dd>is associated to bean's property <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is associated to bean's property <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is associated to bean's property <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is associated to bean's property <strong>birthDay</strong></dd>
   * </dl>
   *
   * <h2>Dynamic parts:</h2>
   * <dl>
   * <dt>#{pageSize}</dt>is part of limit statement resolved at runtime.</dd>
   * </dl>
   *
   * @param pageSize
   * 	is used as <strong>dynamic LIMIT statement</strong> and it is formatted by ({@link StringUtils#format})
   * @param paginatedResult
   * 	handler of paginated result
   * @return result list
   */
  private List<Person> selectPagedStatic2(int pageSize, PaginatedResult3 paginatedResult) {
    // build where condition
    String[] _args={};

    //StringUtils, SqlUtils will be used in case of dynamic parts of SQL
    Logger.info(SqlUtils.formatSQL("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName"+SqlUtils.printIf(pageSize>0, " LIMIT "+pageSize)+SqlUtils.printIf(pageSize>0 && paginatedResult.firstRow()>0, " OFFSET "+paginatedResult.firstRow()),(Object[])_args));
    try (Cursor cursor = database().rawQuery("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName"+SqlUtils.printIf(pageSize>0, " LIMIT "+pageSize)+SqlUtils.printIf(pageSize>0 && paginatedResult.firstRow()>0, " OFFSET "+paginatedResult.firstRow()), _args)) {
      Logger.info("Rows found: %s",cursor.getCount());

      List<Person> resultList=new ArrayList<Person>();
      Person resultBean=null;

      if (cursor.moveToFirst()) {

        int index0=cursor.getColumnIndex("id");
        int index1=cursor.getColumnIndex("name");
        int index2=cursor.getColumnIndex("surname");
        int index3=cursor.getColumnIndex("birth_city");
        int index4=cursor.getColumnIndex("birth_day");

        do
         {
          resultBean=new Person();

          if (!cursor.isNull(index0)) { resultBean.id=cursor.getLong(index0); }
          if (!cursor.isNull(index1)) { resultBean.name=cursor.getString(index1); }
          if (!cursor.isNull(index2)) { resultBean.surname=cursor.getString(index2); }
          if (!cursor.isNull(index3)) { resultBean.birthCity=cursor.getString(index3); }
          if (!cursor.isNull(index4)) { resultBean.birthDay=DateUtils.read(cursor.getString(index4)); }

          resultList.add(resultBean);
        } while (cursor.moveToNext());
      }

      return resultList;
    }
  }

  /**
   * <p>SQL insert:</p>
   * <pre>INSERT INTO person (name, surname, birth_city, birth_day) VALUES (${name}, ${surname}, ${birthCity}, ${birthDay})</pre>
   *
   * <p><strong>Inserted columns:</strong></p>
   * <dl>
   * 	<dt>name</dt><dd>is binded to query's parameter <strong>${name}</strong> and method's parameter <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is binded to query's parameter <strong>${surname}</strong> and method's parameter <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is binded to query's parameter <strong>${birthCity}</strong> and method's parameter <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is binded to query's parameter <strong>${birthDay}</strong> and method's parameter <strong>birthDay</strong></dd>
   * </dl>
   *
   * @param name
   * 	is binded to column <strong>name</strong>
   * @param surname
   * 	is binded to column <strong>surname</strong>
   * @param birthCity
   * 	is binded to column <strong>birth_city</strong>
   * @param birthDay
   * 	is binded to column <strong>birth_day</strong>
   *
   *
   */
  @Override
  public void insertOne(String name, String surname, String birthCity, Date birthDay) {
    ContentValues contentValues=contentValues();
    contentValues.clear();

    if (name!=null) {
      contentValues.put("name", name);
    } else {
      contentValues.putNull("name");
    }

    if (surname!=null) {
      contentValues.put("surname", surname);
    } else {
      contentValues.putNull("surname");
    }

    if (birthCity!=null) {
      contentValues.put("birth_city", birthCity);
    } else {
      contentValues.putNull("birth_city");
    }

    if (birthDay!=null) {
      contentValues.put("birth_day", DateUtils.write(birthDay));
    } else {
      contentValues.putNull("birth_day");
    }

    //StringUtils and SqlUtils will be used to format SQL
    // log
    Logger.info(SqlUtils.formatSQL("INSERT INTO person (name, surname, birth_city, birth_day) VALUES ('"+StringUtils.checkSize(contentValues.get("name"))+"', '"+StringUtils.checkSize(contentValues.get("surname"))+"', '"+StringUtils.checkSize(contentValues.get("birth_city"))+"', '"+StringUtils.checkSize(contentValues.get("birth_day"))+"')"));
    database().insert("person", null, contentValues);
  }

  /**
   * <h2>Select SQL:</h2>
   *
   * <pre>SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName</pre>
   *
   * <h2>Projected columns:</h2>
   * <dl>
   * 	<dt>id</dt><dd>is associated to bean's property <strong>id</strong></dd>
   * 	<dt>name</dt><dd>is associated to bean's property <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is associated to bean's property <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is associated to bean's property <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is associated to bean's property <strong>birthDay</strong></dd>
   * </dl>
   *
   * @return collection of bean or empty collection.
   */
  @Override
  public List<Person> selectAll() {
    // build where condition
    String[] _args={};

    //StringUtils, SqlUtils will be used in case of dynamic parts of SQL
    Logger.info(SqlUtils.formatSQL("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName",(Object[])_args));
    try (Cursor cursor = database().rawQuery("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName", _args)) {
      Logger.info("Rows found: %s",cursor.getCount());

      LinkedList<Person> resultList=new LinkedList<Person>();
      Person resultBean=null;

      if (cursor.moveToFirst()) {

        int index0=cursor.getColumnIndex("id");
        int index1=cursor.getColumnIndex("name");
        int index2=cursor.getColumnIndex("surname");
        int index3=cursor.getColumnIndex("birth_city");
        int index4=cursor.getColumnIndex("birth_day");

        do
         {
          resultBean=new Person();

          if (!cursor.isNull(index0)) { resultBean.id=cursor.getLong(index0); }
          if (!cursor.isNull(index1)) { resultBean.name=cursor.getString(index1); }
          if (!cursor.isNull(index2)) { resultBean.surname=cursor.getString(index2); }
          if (!cursor.isNull(index3)) { resultBean.birthCity=cursor.getString(index3); }
          if (!cursor.isNull(index4)) { resultBean.birthDay=DateUtils.read(cursor.getString(index4)); }

          resultList.add(resultBean);
        } while (cursor.moveToNext());
      }

      return resultList;
    }
  }

  /**
   * <h2>Select SQL:</h2>
   *
   * <pre>SELECT id, name, surname, birth_city, birth_day FROM person WHERE typeName like ${nameTemp} || \'%\' #{where} ORDER BY #{orderBy}</pre>
   *
   * <h2>Projected columns:</h2>
   * <dl>
   * 	<dt>id</dt><dd>is associated to bean's property <strong>id</strong></dd>
   * 	<dt>name</dt><dd>is associated to bean's property <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is associated to bean's property <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is associated to bean's property <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is associated to bean's property <strong>birthDay</strong></dd>
   * </dl>
   *
   * <h2>Dynamic parts:</h2>
   * <dl>
   * <dt>#{where}</dt><dd>is part of where conditions resolved at runtime.</dd><dt>#{orderBy}</dt>is part of order statement resolved at runtime.</dd>
   * </dl>
   *
   * <h2>Query's parameters:</h2>
   * <dl>
   * 	<dt>${nameTemp}</dt><dd>is binded to method's parameter <strong>nameValue</strong></dd>
   * </dl>
   *
   * @param nameValue
   * 	is binded to <code>${nameTemp}</code>
   * @param where
   * 	is used as <strong>dynamic WHERE statement</strong> and it is formatted by ({@link StringUtils#format})
   * @param orderBy
   * 	is used as <strong>dynamic ORDER BY statement</strong> and it is formatted by ({@link StringUtils#format})
   * @return collection of bean or empty collection.
   */
  @Override
  public List<Person> selectOne(String nameValue, String where, String orderBy) {
    // build where condition
    String[] _args={(nameValue==null?"":nameValue)};

    //StringUtils, SqlUtils will be used in case of dynamic parts of SQL
    Logger.info(SqlUtils.formatSQL("SELECT id, name, surname, birth_city, birth_day FROM person WHERE typeName like '%s' || \'%%' "+SqlUtils.appendForLog(where)+" ORDER BY "+SqlUtils.appendForLog(orderBy),(Object[])_args));
    try (Cursor cursor = database().rawQuery("SELECT id, name, surname, birth_city, birth_day FROM person WHERE typeName like ? || \'%\' "+SqlUtils.appendForSQL(where)+" ORDER BY "+SqlUtils.appendForSQL(orderBy), _args)) {
      Logger.info("Rows found: %s",cursor.getCount());

      LinkedList<Person> resultList=new LinkedList<Person>();
      Person resultBean=null;

      if (cursor.moveToFirst()) {

        int index0=cursor.getColumnIndex("id");
        int index1=cursor.getColumnIndex("name");
        int index2=cursor.getColumnIndex("surname");
        int index3=cursor.getColumnIndex("birth_city");
        int index4=cursor.getColumnIndex("birth_day");

        do
         {
          resultBean=new Person();

          if (!cursor.isNull(index0)) { resultBean.id=cursor.getLong(index0); }
          if (!cursor.isNull(index1)) { resultBean.name=cursor.getString(index1); }
          if (!cursor.isNull(index2)) { resultBean.surname=cursor.getString(index2); }
          if (!cursor.isNull(index3)) { resultBean.birthCity=cursor.getString(index3); }
          if (!cursor.isNull(index4)) { resultBean.birthDay=DateUtils.read(cursor.getString(index4)); }

          resultList.add(resultBean);
        } while (cursor.moveToNext());
      }

      return resultList;
    }
  }

  /**
   * <h2>Select SQL:</h2>
   *
   * <pre>SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName#{orderBy}</pre>
   *
   * <h2>Projected columns:</h2>
   * <dl>
   * 	<dt>id</dt><dd>is associated to bean's property <strong>id</strong></dd>
   * 	<dt>name</dt><dd>is associated to bean's property <strong>name</strong></dd>
   * 	<dt>surname</dt><dd>is associated to bean's property <strong>surname</strong></dd>
   * 	<dt>birth_city</dt><dd>is associated to bean's property <strong>birthCity</strong></dd>
   * 	<dt>birth_day</dt><dd>is associated to bean's property <strong>birthDay</strong></dd>
   * </dl>
   *
   * <h2>Dynamic parts:</h2>
   * <dl>
   * <dt>#{orderBy}</dt>is part of order statement resolved at runtime.</dd>
   * </dl>
   *
   * @param beanListener
   * 	is the Person listener
   * @param orderBy
   * 	is used as <strong>dynamic ORDER BY statement</strong> and it is formatted by ({@link StringUtils#format})
   */
  @Override
  public void selectBeanListener(OnReadBeanListener<Person> beanListener, String orderBy) {
    // build where condition
    String[] _args={};

    //StringUtils, SqlUtils will be used in case of dynamic parts of SQL
    Logger.info(SqlUtils.formatSQL("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName"+SqlUtils.appendForLog(orderBy),(Object[])_args));
    try (Cursor cursor = database().rawQuery("SELECT id, name, surname, birth_city, birth_day FROM person ORDER BY typeName"+SqlUtils.appendForSQL(orderBy), _args)) {
      Logger.info("Rows found: %s",cursor.getCount());
      Person resultBean=new Person();
      if (cursor.moveToFirst()) {

        int index0=cursor.getColumnIndex("id");
        int index1=cursor.getColumnIndex("name");
        int index2=cursor.getColumnIndex("surname");
        int index3=cursor.getColumnIndex("birth_city");
        int index4=cursor.getColumnIndex("birth_day");

        int rowCount=cursor.getCount();
        do
         {
          // reset mapping
          resultBean.id=0L;
          resultBean.name=null;
          resultBean.surname=null;
          resultBean.birthCity=null;
          resultBean.birthDay=null;

          // generate mapping
          if (!cursor.isNull(index0)) { resultBean.id=cursor.getLong(index0); }
          if (!cursor.isNull(index1)) { resultBean.name=cursor.getString(index1); }
          if (!cursor.isNull(index2)) { resultBean.surname=cursor.getString(index2); }
          if (!cursor.isNull(index3)) { resultBean.birthCity=cursor.getString(index3); }
          if (!cursor.isNull(index4)) { resultBean.birthDay=DateUtils.read(cursor.getString(index4)); }

          beanListener.onRead(resultBean, cursor.getPosition(), rowCount);
        } while (cursor.moveToNext());
      }
    }
  }

  public class PaginatedResult2 extends PaginatedResult<Person> {
    PaginatedResult2() {
      this.pageSize=20;
    }

    public List<Person> execute() {
      list=PersonDAOImpl.this.selectPagedStatic1(this);
      return list;
    }
  }

  public class PaginatedResult3 extends PaginatedResult<Person> {
    PaginatedResult3(int pageSize) {
      this.pageSize=pageSize;
    }

    public List<Person> execute() {
      list=PersonDAOImpl.this.selectPagedStatic2(pageSize, this);
      return list;
    }
  }
}
