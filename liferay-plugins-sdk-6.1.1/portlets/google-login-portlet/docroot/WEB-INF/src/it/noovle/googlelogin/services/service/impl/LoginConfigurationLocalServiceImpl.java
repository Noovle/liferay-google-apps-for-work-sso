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

package it.noovle.googlelogin.services.service.impl;

import it.noovle.googlelogin.services.model.LoginConfiguration;
import it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil;
import it.noovle.googlelogin.services.service.base.LoginConfigurationLocalServiceBaseImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the login configuration local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link it.noovle.googlelogin.services.service.LoginConfigurationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author
 * @see it.noovle.googlelogin.services.service.base.LoginConfigurationLocalServiceBaseImpl
 * @see it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil
 */
public class LoginConfigurationLocalServiceImpl
	extends LoginConfigurationLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil} to access the login configuration local service.
	 */
	
	public boolean isAutomaticLogin(long companyId) throws PortalException, SystemException  {
		boolean isAutomatic = false;
		
		LoginConfiguration loginConfiguration = LoginConfigurationLocalServiceUtil.getLoginConfiguration(companyId);
		
		if(loginConfiguration!=null) {		
			isAutomatic = loginConfiguration.isDoRedirect();
		}
		
		return isAutomatic;
	}
	
	public long getAutomaticLoginTimeout(long companyId) throws PortalException, SystemException {

		long automaticLoginTimeout = 0;
		
		LoginConfiguration loginConfiguration = LoginConfigurationLocalServiceUtil.getLoginConfiguration(companyId);
		
		if(loginConfiguration!=null) {		
			automaticLoginTimeout = loginConfiguration.getDoRedirectAfter();
		}
		
		return automaticLoginTimeout;
	}
}