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

package it.noovle.googlelogin.services.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import it.noovle.googlelogin.services.model.LoginConfiguration;
import it.noovle.googlelogin.services.service.LoginConfigurationLocalService;
import it.noovle.googlelogin.services.service.LoginConfigurationService;
import it.noovle.googlelogin.services.service.persistence.LoginConfigurationPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base implementation of the login configuration local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link it.noovle.googlelogin.services.service.impl.LoginConfigurationLocalServiceImpl}.
 * </p>
 *
 * @author
 * @see it.noovle.googlelogin.services.service.impl.LoginConfigurationLocalServiceImpl
 * @see it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil
 * @generated
 */
public abstract class LoginConfigurationLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements LoginConfigurationLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil} to access the login configuration local service.
	 */

	/**
	 * Adds the login configuration to the database. Also notifies the appropriate model listeners.
	 *
	 * @param loginConfiguration the login configuration
	 * @return the login configuration that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LoginConfiguration addLoginConfiguration(
		LoginConfiguration loginConfiguration) throws SystemException {
		loginConfiguration.setNew(true);

		return loginConfigurationPersistence.update(loginConfiguration, false);
	}

	/**
	 * Creates a new login configuration with the primary key. Does not add the login configuration to the database.
	 *
	 * @param companyId the primary key for the new login configuration
	 * @return the new login configuration
	 */
	public LoginConfiguration createLoginConfiguration(long companyId) {
		return loginConfigurationPersistence.create(companyId);
	}

	/**
	 * Deletes the login configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyId the primary key of the login configuration
	 * @return the login configuration that was removed
	 * @throws PortalException if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public LoginConfiguration deleteLoginConfiguration(long companyId)
		throws PortalException, SystemException {
		return loginConfigurationPersistence.remove(companyId);
	}

	/**
	 * Deletes the login configuration from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loginConfiguration the login configuration
	 * @return the login configuration that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public LoginConfiguration deleteLoginConfiguration(
		LoginConfiguration loginConfiguration) throws SystemException {
		return loginConfigurationPersistence.remove(loginConfiguration);
	}

	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(LoginConfiguration.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return loginConfigurationPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return loginConfigurationPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return loginConfigurationPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return loginConfigurationPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public LoginConfiguration fetchLoginConfiguration(long companyId)
		throws SystemException {
		return loginConfigurationPersistence.fetchByPrimaryKey(companyId);
	}

	/**
	 * Returns the login configuration with the primary key.
	 *
	 * @param companyId the primary key of the login configuration
	 * @return the login configuration
	 * @throws PortalException if a login configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LoginConfiguration getLoginConfiguration(long companyId)
		throws PortalException, SystemException {
		return loginConfigurationPersistence.findByPrimaryKey(companyId);
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return loginConfigurationPersistence.findByPrimaryKey(primaryKeyObj);
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
	public List<LoginConfiguration> getLoginConfigurations(int start, int end)
		throws SystemException {
		return loginConfigurationPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of login configurations.
	 *
	 * @return the number of login configurations
	 * @throws SystemException if a system exception occurred
	 */
	public int getLoginConfigurationsCount() throws SystemException {
		return loginConfigurationPersistence.countAll();
	}

	/**
	 * Updates the login configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param loginConfiguration the login configuration
	 * @return the login configuration that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LoginConfiguration updateLoginConfiguration(
		LoginConfiguration loginConfiguration) throws SystemException {
		return updateLoginConfiguration(loginConfiguration, true);
	}

	/**
	 * Updates the login configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param loginConfiguration the login configuration
	 * @param merge whether to merge the login configuration with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	 * @return the login configuration that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LoginConfiguration updateLoginConfiguration(
		LoginConfiguration loginConfiguration, boolean merge)
		throws SystemException {
		loginConfiguration.setNew(false);

		return loginConfigurationPersistence.update(loginConfiguration, merge);
	}

	/**
	 * Returns the login configuration local service.
	 *
	 * @return the login configuration local service
	 */
	public LoginConfigurationLocalService getLoginConfigurationLocalService() {
		return loginConfigurationLocalService;
	}

	/**
	 * Sets the login configuration local service.
	 *
	 * @param loginConfigurationLocalService the login configuration local service
	 */
	public void setLoginConfigurationLocalService(
		LoginConfigurationLocalService loginConfigurationLocalService) {
		this.loginConfigurationLocalService = loginConfigurationLocalService;
	}

	/**
	 * Returns the login configuration remote service.
	 *
	 * @return the login configuration remote service
	 */
	public LoginConfigurationService getLoginConfigurationService() {
		return loginConfigurationService;
	}

	/**
	 * Sets the login configuration remote service.
	 *
	 * @param loginConfigurationService the login configuration remote service
	 */
	public void setLoginConfigurationService(
		LoginConfigurationService loginConfigurationService) {
		this.loginConfigurationService = loginConfigurationService;
	}

	/**
	 * Returns the login configuration persistence.
	 *
	 * @return the login configuration persistence
	 */
	public LoginConfigurationPersistence getLoginConfigurationPersistence() {
		return loginConfigurationPersistence;
	}

	/**
	 * Sets the login configuration persistence.
	 *
	 * @param loginConfigurationPersistence the login configuration persistence
	 */
	public void setLoginConfigurationPersistence(
		LoginConfigurationPersistence loginConfigurationPersistence) {
		this.loginConfigurationPersistence = loginConfigurationPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource remote service.
	 *
	 * @return the resource remote service
	 */
	public ResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * Sets the resource remote service.
	 *
	 * @param resourceService the resource remote service
	 */
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * Returns the resource persistence.
	 *
	 * @return the resource persistence
	 */
	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	/**
	 * Sets the resource persistence.
	 *
	 * @param resourcePersistence the resource persistence
	 */
	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("it.noovle.googlelogin.services.model.LoginConfiguration",
			loginConfigurationLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"it.noovle.googlelogin.services.model.LoginConfiguration");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return LoginConfiguration.class;
	}

	protected String getModelClassName() {
		return LoginConfiguration.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = loginConfigurationPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = LoginConfigurationLocalService.class)
	protected LoginConfigurationLocalService loginConfigurationLocalService;
	@BeanReference(type = LoginConfigurationService.class)
	protected LoginConfigurationService loginConfigurationService;
	@BeanReference(type = LoginConfigurationPersistence.class)
	protected LoginConfigurationPersistence loginConfigurationPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private LoginConfigurationLocalServiceClpInvoker _clpInvoker = new LoginConfigurationLocalServiceClpInvoker();
}