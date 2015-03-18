<%--
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
--%>

<%@page import="com.liferay.portal.kernel.portlet.PortletClassLoaderUtil"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.ClassResolverUtil"%>
<%@ include file="/html/portlet/login/init.jsp" %>
<%@page import="java.math.BigInteger"%>
<%@page import="com.liferay.portal.kernel.security.SecureRandom"%>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String signedInAs = HtmlUtil.escape(user.getFullName());

		if (themeDisplay.isShowMyAccountIcon() && (themeDisplay.getURLMyAccount() != null)) {
			String myAccountURL = String.valueOf(themeDisplay.getURLMyAccount());

			if (PropsValues.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP) {
				signedInAs = "<a class=\"signed-in\" href=\"javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: true}, title: '" + HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "my-account")) + "', uri: '" + HtmlUtil.escapeJS(myAccountURL) + "'});\">" + signedInAs + "</a>";
			}
			else {
				myAccountURL = HttpUtil.setParameter(myAccountURL, "controlPanelCategory", PortletCategoryKeys.MY);

				signedInAs = "<a class=\"signed-in\" href=\"" + HtmlUtil.escape(myAccountURL) + "\">" + signedInAs + "</a>";
			}
		}
		%>

		<%= LanguageUtil.format(pageContext, "you-are-signed-in-as-x", signedInAs, false) %>
	</c:when>
	<c:otherwise>

		<%
		String redirect = ParamUtil.getString(request, "redirect");

		String login = LoginUtil.getLogin(request, "login", company);
		String password = StringPool.BLANK;
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");

		if (Validator.isNull(authType)) {
			authType = company.getAuthType();
		}
		%>

		<portlet:actionURL secure="<%= PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS || request.isSecure() %>" var="loginURL">
			<portlet:param name="struts_action" value="/login/login" />
		</portlet:actionURL>

		<aui:form action="<%= loginURL %>" autocomplete='<%= PropsValues.COMPANY_SECURITY_LOGIN_FORM_AUTOCOMPLETE ? "on" : "off" %>' cssClass="sign-in-form" method="post" name="fm">
			<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="doActionAfterLogin" type="hidden" value="<%= portletName.equals(PortletKeys.FAST_LOGIN) ? true : false %>" />

			<c:choose>
				<c:when test='<%= SessionMessages.contains(request, "userAdded") %>'>

					<%
					String userEmailAddress = (String)SessionMessages.get(request, "userAdded");
					String userPassword = (String)SessionMessages.get(request, "userAddedPassword");
					%>

					<div class="alert alert-success">
						<c:choose>
							<c:when test="<%= company.isStrangersVerify() || Validator.isNull(userPassword) %>">
								<%= LanguageUtil.get(pageContext, "thank-you-for-creating-an-account") %>

								<c:if test="<%= company.isStrangersVerify() %>">
									<%= LanguageUtil.format(pageContext, "your-email-verification-code-has-been-sent-to-x", userEmailAddress) %>
								</c:if>
							</c:when>
							<c:otherwise>
								<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account.-your-password-is-x", userPassword, false) %>
							</c:otherwise>
						</c:choose>

						<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED) %>">
							<%= LanguageUtil.format(pageContext, "your-password-has-been-sent-to-x", userEmailAddress) %>
						</c:if>
					</div>
				</c:when>
				<c:when test='<%= SessionMessages.contains(request, "userPending") %>'>

					<%
					String userEmailAddress = (String)SessionMessages.get(request, "userPending");
					%>

					<div class="alert alert-success">
						<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account.-you-will-be-notified-via-email-at-x-when-your-account-has-been-approved", userEmailAddress) %>
					</div>
				</c:when>
			</c:choose>

			<liferay-ui:error exception="<%= AuthException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-login-because-the-maximum-number-of-users-has-been-reached" />
			<liferay-ui:error exception="<%= CookieNotSupportedException.class %>" message="authentication-failed-please-enable-browser-cookies" />
			<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= PasswordExpiredException.class %>" message="your-password-has-expired" />
			<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= UserLockoutException.class %>" message="this-account-has-been-locked" />
			<liferay-ui:error exception="<%= UserPasswordException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="authentication-failed" />

			<aui:fieldset>

				<%
				String loginLabel = null;

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					loginLabel = "email-address";
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					loginLabel = "screen-name";
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
					loginLabel = "id";
				}
				%>

				<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.EXCLUSIVE) || windowState.equals(WindowState.MAXIMIZED) %>" cssClass="clearable" label="<%= loginLabel %>" name="login" showRequiredLabel="<%= false %>" type="text" value="<%= login %>">
					<aui:validator name="required" />
				</aui:input>

				<aui:input name="password" showRequiredLabel="<%= false %>" type="password" value="<%= password %>">
					<aui:validator name="required" />
				</aui:input>

				<span id="<portlet:namespace />passwordCapsLockSpan" style="display: none;"><liferay-ui:message key="caps-lock-is-on" /></span>

				<c:if test="<%= company.isAutoLogin() && !PropsValues.SESSION_DISABLED %>">
					<aui:input checked="<%= rememberMe %>" name="rememberMe" type="checkbox" />
				</c:if>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" value="sign-in" />
			</aui:button-row>
		</aui:form>		
		<%
			try {		
								
				Class<?> IMPL = ClassResolverUtil.resolve("it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil", PortletClassLoaderUtil.getClassLoader("1_WAR_googleappsforworkssoportlet"));

				boolean isAutomaticLogin = ((Boolean)IMPL.getMethod("isAutomaticLogin", long.class).invoke(null, PortalUtil.getCompanyId(request))).booleanValue();		
				long automaticLoginTimeout = ((Long)IMPL.getMethod("getAutomaticLoginTimeout", long.class).invoke(null, PortalUtil.getCompanyId(request))).longValue();
				
				String state = null;
								
				if(session.getAttribute("LIFERAY_SHARED_stateToken")!=null) {
					state = (String)session.getAttribute("LIFERAY_SHARED_stateToken");
				} else {
					state = new BigInteger(130, new SecureRandom()).toString(32);
					session.setAttribute("LIFERAY_SHARED_stateToken", state);
				}
				
		%>		
			<liferay-portlet:actionURL name="doLogin" var="loginAction" portletName="1_WAR_googleappsforworkssoportlet" portletMode="view" />
				
			<aui:form action="<%=loginAction%>" method="post" name="fmgl">
				<aui:fieldset>
					<input type="hidden" value="<%=state%>" name="_1_WAR_googleappsforworkssoportlet_state" />
					<aui:button-row>
						<aui:button type="submit" value="login.action.do"></aui:button>
					</aui:button-row>
				</aui:fieldset>
			</aui:form>				
<%
	boolean authError = (session.getAttribute("GOOGLE_LOGIN_FAILED")!=null);
	
	if(authError) {
		session.removeAttribute("GOOGLE_LOGIN_FAILED");				
%>
<div class="alert alert-error">
	<liferay-ui:message key="login.action.denied.user.not.present" />	
</div>
<%		
	}
%>			
			
 			<c:if test="<%=isAutomaticLogin && automaticLoginTimeout>0 && !authError%>">
				<script type="text/javascript">
					AUI()
					.ready(
							'node',
							function(Y) {			
								setTimeout(function() { Y.one("#<portlet:namespace/>fmgl").submit() }, <%=automaticLoginTimeout*1000%>);
							}
					);
				</script>
			</c:if>	
<%
			} catch(ClassNotFoundException ex) {
%>
<div class="alert alert-error">
	<liferay-ui:message key="login.configuration.ClassNotFoundException" />		
</div>
<%				
			}
%>
		<liferay-util:include page="/html/portlet/login/navigation.jsp" />

		<aui:script use="aui-base">
			var password = A.one('#<portlet:namespace />password');

			if (password) {
				password.on(
					'keypress',
					function(event) {
						Liferay.Util.showCapsLock(event, '<portlet:namespace />passwordCapsLockSpan');
					}
				);
			}
		</aui:script>
	</c:otherwise>
</c:choose>