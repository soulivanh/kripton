package sqlite.kripton64;

import com.abubusoft.kripton.android.sqlite.BindDaoFactory;

/**
 * <p>
 * Represents dao factory interface for BeanDataSource.
 * This class expose database interface through Dao attribute.
 * </p>
 *
 * @see BeanDataSource
 * @see BeanDao
 * @see BeanDaoImpl
 * @see Bean64
 */
public interface BindBeanDaoFactory extends BindDaoFactory {
  /**
   *
   * retrieve dao BeanDao
   */
  BeanDaoImpl getBeanDao();
}
