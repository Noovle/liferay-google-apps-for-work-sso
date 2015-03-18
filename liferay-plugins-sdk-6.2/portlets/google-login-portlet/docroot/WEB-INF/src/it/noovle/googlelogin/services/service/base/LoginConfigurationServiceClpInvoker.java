/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import it.noovle.googlelogin.services.service.LoginConfigurationServiceUtil;

import java.util.Arrays;

/**
 * @author
 * @generated
 */
public class LoginConfigurationServiceClpInvoker {
	public LoginConfigurationServiceClpInvoker() {
		_methodName18 = "getBeanIdentifier";

		_methodParameterTypes18 = new String[] {  };

		_methodName19 = "setBeanIdentifier";

		_methodParameterTypes19 = new String[] { "java.lang.String" };

		_methodName24 = "isAutomaticLogin";

		_methodParameterTypes24 = new String[] { "long" };

		_methodName25 = "getAutomaticLoginTimeout";

		_methodParameterTypes25 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName18.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes18, parameterTypes)) {
			return LoginConfigurationServiceUtil.getBeanIdentifier();
		}

		if (_methodName19.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes19, parameterTypes)) {
			LoginConfigurationServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName24.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes24, parameterTypes)) {
			return LoginConfigurationServiceUtil.isAutomaticLogin(((Long)arguments[0]).longValue());
		}

		if (_methodName25.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes25, parameterTypes)) {
			return LoginConfigurationServiceUtil.getAutomaticLoginTimeout(((Long)arguments[0]).longValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName18;
	private String[] _methodParameterTypes18;
	private String _methodName19;
	private String[] _methodParameterTypes19;
	private String _methodName24;
	private String[] _methodParameterTypes24;
	private String _methodName25;
	private String[] _methodParameterTypes25;
}