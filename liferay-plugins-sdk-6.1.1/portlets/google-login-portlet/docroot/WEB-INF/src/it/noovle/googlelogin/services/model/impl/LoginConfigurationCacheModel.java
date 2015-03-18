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

package it.noovle.googlelogin.services.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import it.noovle.googlelogin.services.model.LoginConfiguration;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing LoginConfiguration in entity cache.
 *
 * @author
 * @see LoginConfiguration
 * @generated
 */
public class LoginConfigurationCacheModel implements CacheModel<LoginConfiguration>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{companyId=");
		sb.append(companyId);
		sb.append(", clientId=");
		sb.append(clientId);
		sb.append(", clientSecret=");
		sb.append(clientSecret);
		sb.append(", createUserOnLogin=");
		sb.append(createUserOnLogin);
		sb.append(", doRedirect=");
		sb.append(doRedirect);
		sb.append(", doRedirectAfter=");
		sb.append(doRedirectAfter);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", accessDeniendPage=");
		sb.append(accessDeniendPage);
		sb.append("}");

		return sb.toString();
	}

	public LoginConfiguration toEntityModel() {
		LoginConfigurationImpl loginConfigurationImpl = new LoginConfigurationImpl();

		loginConfigurationImpl.setCompanyId(companyId);

		if (clientId == null) {
			loginConfigurationImpl.setClientId(StringPool.BLANK);
		}
		else {
			loginConfigurationImpl.setClientId(clientId);
		}

		if (clientSecret == null) {
			loginConfigurationImpl.setClientSecret(StringPool.BLANK);
		}
		else {
			loginConfigurationImpl.setClientSecret(clientSecret);
		}

		loginConfigurationImpl.setCreateUserOnLogin(createUserOnLogin);
		loginConfigurationImpl.setDoRedirect(doRedirect);
		loginConfigurationImpl.setDoRedirectAfter(doRedirectAfter);

		if (modifiedDate == Long.MIN_VALUE) {
			loginConfigurationImpl.setModifiedDate(null);
		}
		else {
			loginConfigurationImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (accessDeniendPage == null) {
			loginConfigurationImpl.setAccessDeniendPage(StringPool.BLANK);
		}
		else {
			loginConfigurationImpl.setAccessDeniendPage(accessDeniendPage);
		}

		loginConfigurationImpl.resetOriginalValues();

		return loginConfigurationImpl;
	}

	public long companyId;
	public String clientId;
	public String clientSecret;
	public boolean createUserOnLogin;
	public boolean doRedirect;
	public long doRedirectAfter;
	public long modifiedDate;
	public String accessDeniendPage;
}