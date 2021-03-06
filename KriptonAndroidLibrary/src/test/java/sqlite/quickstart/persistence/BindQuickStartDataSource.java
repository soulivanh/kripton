package sqlite.quickstart.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.sqlite.AbstractDataSource;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import java.lang.Override;
import java.lang.Throwable;
import sqlite.quickstart.model.CommentTable;
import sqlite.quickstart.model.PostTable;
import sqlite.quickstart.model.TodoTable;
import sqlite.quickstart.model.UserTable;

/**
 * <p>
 * Rapresents implementation of datasource QuickStartDataSource.
 * This class expose database interface through Dao attribute.
 * </p>
 *
 * @see QuickStartDataSource
 * @see BindQuickStartDaoFactory
 * @see UserDao
 * @see UserDaoImpl
 * @see sqlite.quickstart.model.User
 * @see PostDao
 * @see PostDaoImpl
 * @see sqlite.quickstart.model.Post
 * @see CommentDao
 * @see CommentDaoImpl
 * @see sqlite.quickstart.model.Comment
 * @see TodoDao
 * @see TodoDaoImpl
 * @see sqlite.quickstart.model.Todo
 */
public class BindQuickStartDataSource extends AbstractDataSource implements BindQuickStartDaoFactory, QuickStartDataSource {
  /**
   * <p>datasource singleton</p>
   */
  private static BindQuickStartDataSource instance = new BindQuickStartDataSource();

  /**
   * <p>dao instance</p>
   */
  protected UserDaoImpl userDao = new UserDaoImpl(this);

  /**
   * <p>dao instance</p>
   */
  protected PostDaoImpl postDao = new PostDaoImpl(this);

  /**
   * <p>dao instance</p>
   */
  protected CommentDaoImpl commentDao = new CommentDaoImpl(this);

  /**
   * <p>dao instance</p>
   */
  protected TodoDaoImpl todoDao = new TodoDaoImpl(this);

  protected BindQuickStartDataSource() {
    super("kripton.quickstart.db", 1);
  }

  @Override
  public UserDaoImpl getUserDao() {
    return userDao;
  }

  @Override
  public PostDaoImpl getPostDao() {
    return postDao;
  }

  @Override
  public CommentDaoImpl getCommentDao() {
    return commentDao;
  }

  @Override
  public TodoDaoImpl getTodoDao() {
    return todoDao;
  }

  /**
   * <p>Executes a transaction. This method <strong>is thread safe</strong> to avoid concurrent problems. Thedrawback is only one transaction at time can be executed. The database will be open in write mode.</p>
   *
   * @param transaction
   * 	transaction to execute
   */
  public void execute(Transaction transaction) {
    SQLiteDatabase connection=openWritableDatabase();
    try {
      connection.beginTransaction();
      if (transaction!=null && transaction.onExecute(this)) {
        connection.setTransactionSuccessful();
      }
    } catch(Throwable e) {
      Logger.error(e.getMessage());
      e.printStackTrace();
      if (transaction!=null) transaction.onError(e);
    } finally {
      try {
        connection.endTransaction();
      } catch (Throwable e) {
        Logger.warn("error closing transaction %s", e.getMessage());
      }
      close();
    }
  }

  /**
   * instance
   */
  public static BindQuickStartDataSource instance() {
    return instance;
  }

  /**
   * Retrieve data source instance and open it.
   * @return opened dataSource instance.
   */
  public static BindQuickStartDataSource open() {
    instance.openWritableDatabase();
    return instance;
  }

  /**
   * Retrieve data source instance and open it in read only mode.
   * @return opened dataSource instance.
   */
  public static BindQuickStartDataSource openReadOnly() {
    instance.openReadOnlyDatabase();
    return instance;
  }

  /**
   * onCreate
   */
  @Override
  public void onCreate(SQLiteDatabase database) {
    // generate tables
    Logger.info("DDL: %s",UserTable.CREATE_TABLE_SQL);
    database.execSQL(UserTable.CREATE_TABLE_SQL);
    Logger.info("DDL: %s",PostTable.CREATE_TABLE_SQL);
    database.execSQL(PostTable.CREATE_TABLE_SQL);
    Logger.info("DDL: %s",CommentTable.CREATE_TABLE_SQL);
    database.execSQL(CommentTable.CREATE_TABLE_SQL);
    Logger.info("DDL: %s",TodoTable.CREATE_TABLE_SQL);
    database.execSQL(TodoTable.CREATE_TABLE_SQL);
    if (options.databaseLifecycleHandler != null) {
      options.databaseLifecycleHandler.onCreate(database);
    }
  }

  /**
   * onUpgrade
   */
  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    if (options.databaseLifecycleHandler != null) {
      options.databaseLifecycleHandler.onUpdate(database, oldVersion, newVersion, true);
    } else {
      // drop tables
      Logger.info("DDL: %s",TodoTable.DROP_TABLE_SQL);
      database.execSQL(TodoTable.DROP_TABLE_SQL);
      Logger.info("DDL: %s",CommentTable.DROP_TABLE_SQL);
      database.execSQL(CommentTable.DROP_TABLE_SQL);
      Logger.info("DDL: %s",PostTable.DROP_TABLE_SQL);
      database.execSQL(PostTable.DROP_TABLE_SQL);
      Logger.info("DDL: %s",UserTable.DROP_TABLE_SQL);
      database.execSQL(UserTable.DROP_TABLE_SQL);

      // generate tables
      Logger.info("DDL: %s",UserTable.CREATE_TABLE_SQL);
      database.execSQL(UserTable.CREATE_TABLE_SQL);
      Logger.info("DDL: %s",PostTable.CREATE_TABLE_SQL);
      database.execSQL(PostTable.CREATE_TABLE_SQL);
      Logger.info("DDL: %s",CommentTable.CREATE_TABLE_SQL);
      database.execSQL(CommentTable.CREATE_TABLE_SQL);
      Logger.info("DDL: %s",TodoTable.CREATE_TABLE_SQL);
      database.execSQL(TodoTable.CREATE_TABLE_SQL);
    }
  }

  /**
   * onConfigure
   */
  @Override
  public void onConfigure(SQLiteDatabase database) {
    // configure database
    database.setForeignKeyConstraintsEnabled(true);
    if (options.databaseLifecycleHandler != null) {
      options.databaseLifecycleHandler.onConfigure(database);
    }
  }

  /**
   * interface to define transactions
   */
  public interface Transaction extends AbstractTransaction<BindQuickStartDaoFactory> {
  }

  /**
   * Simple class implements interface to define transactions
   */
  public abstract static class SimpleTransaction implements Transaction {
    @Override
    public void onError(Throwable e) {
      throw(new KriptonRuntimeException(e));
    }
  }
}
