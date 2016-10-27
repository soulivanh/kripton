package com.abubusoft.kripton.processor.kripton58;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.abubusoft.kripton.android.KriptonLibrary;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.sqlite.AbstractDataSource;
import java.lang.Override;
import java.lang.String;

/**
 * <p>
 * Represents implementation of datasource Dummy02DataSource.
 * This class expose database interface through Dao attribute.
 * </p>
 *
 * @see Dummy02DataSource
 * @see BindDummy02DaoFactory
 * @see DaoBean02
 * @see BindDaoBean02
 * @see Bean02
 */
public class BindDummy02DataSource extends AbstractDataSource implements BindDummy02DaoFactory {
  /**
   * <p><singleton of datasource,/p>
   */
  private static BindDummy02DataSource instance;

  /**
   * <p><file name used to save database,/p>
   */
  public static final String name = "dummy";

  /**
   * <p>database version</p>
   */
  public static final int version = 1;

  /**
   * <p>dao instance</p>
   */
  protected BindDaoBean02 daoBean02 = new BindDaoBean02(this);

  protected BindDummy02DataSource(Context context) {
    super(context, name, null, version);
  }

  @Override
  public BindDaoBean02 getDaoBean02() {
    return daoBean02;
  }

  /**
   * <p>executes a transaction. This method is synchronized to avoid concurrent problems. The database will be open in write mode.</p>
   *
   * @param transaction transaction to execute
   */
  public synchronized void execute(Transaction transaction) {
    SQLiteDatabase connection=openDatabase();
    try {
      connection.beginTransaction();
      if (transaction!=null && transaction.onExecute(this)) {
        connection.setTransactionSuccessful();
      }
    } finally {
      connection.endTransaction();
      close();
    }
  }

  /**
   * instance
   */
  public static BindDummy02DataSource instance() {
    if (instance==null) {
      instance=new BindDummy02DataSource(KriptonLibrary.context());
    }
    return instance;
  }

  /**
   * onCreate
   */
  @Override
  public void onCreate(SQLiteDatabase database) {
    // generate tables
    Logger.info("DDL: %s",com.abubusoft.kripton.processor.kripton58.Bean02Table.CREATE_TABLE_SQL);
    database.execSQL(com.abubusoft.kripton.processor.kripton58.Bean02Table.CREATE_TABLE_SQL);
  }

  /**
   * onUpgrade
   */
  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    // drop tables
    Logger.info("DDL: %s",com.abubusoft.kripton.processor.kripton58.Bean02Table.DROP_TABLE_SQL);
    database.execSQL(com.abubusoft.kripton.processor.kripton58.Bean02Table.DROP_TABLE_SQL);

    // generate tables
    Logger.info("DDL: %s",com.abubusoft.kripton.processor.kripton58.Bean02Table.CREATE_TABLE_SQL);
    database.execSQL(com.abubusoft.kripton.processor.kripton58.Bean02Table.CREATE_TABLE_SQL);
  }

  /**
   * interface to define transactions
   */
  public interface Transaction extends AbstractTransaction<BindDummy02DaoFactory> {
  }
}