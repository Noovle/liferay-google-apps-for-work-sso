/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package it.noovle.googlelogin.services.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import it.noovle.googlelogin.services.NoSuchLoginConfigurationException;
import it.noovle.googlelogin.services.model.LoginConfiguration;
import it.noovle.googlelogin.services.model.impl.LoginConfigurationImpl;
import it.noovle.googlelogin.services.model.impl.LoginConfigurationModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the login configuration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author
 * @see LoginConfigurationPersistence
 * @see LoginConfigurationUtil
 * @generated
 */
public class LoginConfigurationPersistenceImpl extends BasePersistenceImpl<LoginConfiguration>
	implements LoginConfigurationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LoginConfigurationUtil} to access the login configuration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LoginConfigurationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			LoginConfigurationModelImpl.FINDER_CACHE_ENABLED,
			LoginConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			LoginConfigurationModelImpl.FINDER_CACHE_ENABLED,
			LoginConfigurationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			LoginConfigurationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the login configuration in the entity cache if it is enabled.
	 *
	 * @param loginConfiguration the login configuration
	 */
	public void cacheResult(LoginConfiguration loginConfiguration) {
		EntityCacheUtil.putResult(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			LoginConfigurationImpl.class, loginConfiguration.getPrimaryKey(),
			loginConfiguration);

		loginConfiguration.resetOriginalValues();
	}

	/**
	 * Caches the login configurations in the entity cache if it is enabled.
	 *
	 * @param loginConfigurations the login configurations
	 */
	public void cacheResult(List<LoginConfiguration> loginConfigurations) {
		for (LoginConfiguration loginConfiguration : loginConfigurations) {
			if (EntityCacheUtil.getResult(
						LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
						LoginConfigurationImpl.class,
						loginConfiguration.getPrimaryKey()) == null) {
				cacheResult(loginConfiguration);
			}
			else {
				loginConfiguration.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all login configurations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LoginConfigurationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LoginConfigurationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the login configuration.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoginConfiguration loginConfiguration) {
		EntityCacheUtil.removeResult(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			LoginConfigurationImpl.class, loginConfiguration.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LoginConfiguration> loginConfigurations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoginConfiguration loginConfiguration : loginConfigurations) {
			EntityCacheUtil.removeResult(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
				LoginConfigurationImpl.class, loginConfiguration.getPrimaryKey());
		}
	}

	/**
	 * Creates a new login configuration with the primary key. Does not add the login configuration to the database.
	 *
	 * @param companyId the primary key for the new login configuration
	 * @return the new login configuration
	 */
	public LoginConfiguration create(long companyId) {
		LoginConfiguration loginConfiguration = new LoginConfigurationImpl();

		loginConfiguration.setNew(true);
		loginConfiguration.setPrimaryKey(companyId);

		return loginConfiguration;
	}

	/**
	 * Removes the login configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyId the primary key of the login configuration
	 * @return the login configuration that was removed
	 * @throws it.noovle.googlelogin.services.NoSuchLoginConfigurationException if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LoginConfiguration remove(long companyId)
		throws NoSuchLoginConfigurationException, SystemException {
		return remove(Long.valueOf(companyId));
	}

	/**
	 * Removes the login configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the login configuration
	 * @return the login configuration that was removed
	 * @throws it.noovle.googlelogin.services.NoSuchLoginConfigurationException if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LoginConfiguration remove(Serializable primaryKey)
		throws NoSuchLoginConfigurationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LoginConfiguration loginConfiguration = (LoginConfiguration)session.get(LoginConfigurationImpl.class,
					primaryKey);

			if (loginConfiguration == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoginConfigurationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(loginConfiguration);
		}
		catch (NoSuchLoginConfigurationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LoginConfiguration removeImpl(
		LoginConfiguration loginConfiguration) throws SystemException {
		loginConfiguration = toUnwrappedModel(loginConfiguration);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, loginConfiguration);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(loginConfiguration);

		return loginConfiguration;
	}

	@Override
	public LoginConfiguration updateImpl(
		it.noovle.googlelogin.services.model.LoginConfiguration loginConfiguration,
		boolean merge) throws SystemException {
		loginConfiguration = toUnwrappedModel(loginConfiguration);

		boolean isNew = loginConfiguration.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, loginConfiguration, merge);

			loginConfiguration.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			LoginConfigurationImpl.class, loginConfiguration.getPrimaryKey(),
			loginConfiguration);

		return loginConfiguration;
	}

	protected LoginConfiguration toUnwrappedModel(
		LoginConfiguration loginConfiguration) {
		if (loginConfiguration instanceof LoginConfigurationImpl) {
			return loginConfiguration;
		}

		LoginConfigurationImpl loginConfigurationImpl = new LoginConfigurationImpl();

		loginConfigurationImpl.setNew(loginConfiguration.isNew());
		loginConfigurationImpl.setPrimaryKey(loginConfiguration.getPrimaryKey());

		loginConfigurationImpl.setCompanyId(loginConfiguration.getCompanyId());
		loginConfigurationImpl.setClientId(loginConfiguration.getClientId());
		loginConfigurationImpl.setClientSecret(loginConfiguration.getClientSecret());
		loginConfigurationImpl.setCreateUserOnLogin(loginConfiguration.isCreateUserOnLogin());
		loginConfigurationImpl.setDoRedirect(loginConfiguration.isDoRedirect());
		loginConfigurationImpl.setDoRedirectAfter(loginConfiguration.getDoRedirectAfter());
		loginConfigurationImpl.setModifiedDate(loginConfiguration.getModifiedDate());
		loginConfigurationImpl.setAccessDeniendPage(loginConfiguration.getAccessDeniendPage());

		return loginConfigurationImpl;
	}

	/**
	 * Returns the login configuration with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the login configuration
	 * @return the login configuration
	 * @throws com.liferay.portal.NoSuchModelException if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LoginConfiguration findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the login configuration with the primary key or throws a {@link it.noovle.googlelogin.services.NoSuchLoginConfigurationException} if it could not be found.
	 *
	 * @param companyId the primary key of the login configuration
	 * @return the login configuration
	 * @throws it.noovle.googlelogin.services.NoSuchLoginConfigurationException if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LoginConfiguration findByPrimaryKey(long companyId)
		throws NoSuchLoginConfigurationException, SystemException {
		LoginConfiguration loginConfiguration = fetchByPrimaryKey(companyId);

		if (loginConfiguration == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + companyId);
			}

			throw new NoSuchLoginConfigurationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				companyId);
		}

		return loginConfiguration;
	}

	/**
	 * Returns the login configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the login configuration
	 * @return the login configuration, or <code>null</code> if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LoginConfiguration fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the login configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param companyId the primary key of the login configuration
	 * @return the login configuration, or <code>null</code> if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LoginConfiguration fetchByPrimaryKey(long companyId)
		throws SystemException {
		LoginConfiguration loginConfiguration = (LoginConfiguration)EntityCacheUtil.getResult(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
				LoginConfigurationImpl.class, companyId);

		if (loginConfiguration == _nullLoginConfiguration) {
			return null;
		}

		if (loginConfiguration == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				loginConfiguration = (LoginConfiguration)session.get(LoginConfigurationImpl.class,
						Long.valueOf(companyId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (loginConfiguration != null) {
					cacheResult(loginConfiguration);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LoginConfigurationModelImpl.ENTITY_CACHE_ENABLED,
						LoginConfigurationImpl.class, companyId,
						_nullLoginConfiguration);
				}

				closeSession(session);
			}
		}

		return loginConfiguration;
	}

	/**
	 * Returns all the login configurations.
	 *
	 * @return the login configurations
	 * @throws SystemException if a system exception occurred
	 */
	public List<LoginConfiguration> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the login configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of login configurations
	 * @param end the upper bound of the range of login configurations (not inclusive)
	 * @return the range of login configurations
	 * @throws SystemException if a system exception occurred
	 */
	public List<LoginConfiguration> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the login configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of login configurations
	 * @param end the upper bound of the range of login configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of login configurations
	 * @throws SystemException if a system exception occurred
	 */
	public List<LoginConfiguration> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<LoginConfiguration> list = (List<LoginConfiguration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LOGINCONFIGURATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LOGINCONFIGURATION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LoginConfiguration>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LoginConfiguration>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the login configurations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LoginConfiguration loginConfiguration : findAll()) {
			remove(loginConfiguration);
		}
	}

	/**
	 * Returns the number of login configurations.
	 *
	 * @return the number of login configurations
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LOGINCONFIGURATION);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the login configuration persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.it.noovle.googlelogin.services.model.LoginConfiguration")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LoginConfiguration>> listenersList = new ArrayList<ModelListener<LoginConfiguration>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<LoginConfiguration>)InstanceFactory.newInstance(
							clazz.getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LoginConfigurationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = LoginConfigurationPersistence.class)
	protected LoginConfigurationPersistence loginConfigurationPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_LOGINCONFIGURATION = "SELECT loginConfiguration FROM LoginConfiguration loginConfiguration";
	private static final String _SQL_COUNT_LOGINCONFIGURATION = "SELECT COUNT(loginConfiguration) FROM LoginConfiguration loginConfiguration";
	private static final String _ORDER_BY_ENTITY_ALIAS = "loginConfiguration.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LoginConfiguration exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LoginConfigurationPersistenceImpl.class);
	private static LoginConfiguration _nullLoginConfiguration = new LoginConfigurationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LoginConfiguration> toCacheModel() {
				return _nullLoginConfigurationCacheModel;
			}
		};

	private static CacheModel<LoginConfiguration> _nullLoginConfigurationCacheModel =
		new CacheModel<LoginConfiguration>() {
			public LoginConfiguration toEntityModel() {
				return _nullLoginConfiguration;
			}
		};
}