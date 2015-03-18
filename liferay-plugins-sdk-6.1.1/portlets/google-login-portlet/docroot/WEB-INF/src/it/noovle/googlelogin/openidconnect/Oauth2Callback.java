package it.noovle.googlelogin.openidconnect;

import it.noovle.googlelogin.services.model.LoginConfiguration;
import it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil;
import it.noovle.googlelogin.util.LoginConstants;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.ConfigurationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

public class Oauth2Callback extends BaseStrutsAction {

	private static final Log log = LogFactory.getLog(Oauth2Callback.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String sessionState = (String) session.getAttribute(LoginConstants.STATE_TOKEN);
		String currentState = request.getParameter("state");
		
		if (currentState!=null && sessionState!=null && !currentState.equals(sessionState)) {
			return null;
		}
		try {
			String code = request.getParameter("code");
			if (Validator.isNotNull(code)) {
				GoogleAuthorizationCodeFlow codeFlow = this.constructCodeFlowHelper(PortalUtil.getCompanyId(request));
				GoogleTokenResponse token =
						codeFlow.newTokenRequest(code).setRedirectUri(
								PortalUtil.getPortalURL(request) + LoginConstants.RELATIVE_OAUTH2_CALLBACK).execute();
				HttpTransport transport = new NetHttpTransport();
				JsonFactory jsonFactory = new JacksonFactory();
				GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).build();
				GoogleIdToken googkeIdToken = googleIdTokenVerifier.verify(token.getIdToken());
				session.setAttribute(LoginConstants.USER_VERIFIED_EMAIL, googkeIdToken.getPayload().getEmail());
				String redirect = (String) session.getAttribute(LoginConstants.REDIRECT_AFTER_LOGIN);
				if (Validator.isNull(redirect)) {
					redirect = PrefsPropsUtil.getString(PortalUtil.getCompanyId(request), PropsKeys.DEFAULT_LANDING_PAGE_PATH);
					if (Validator.isNull(redirect)) {
						redirect = PortalUtil.getPortalURL(request);
					}
				}				
				response.sendRedirect(redirect);
			} else {
				log.info("user denied access to his data");
				response.sendRedirect(PortalUtil.getPortalURL(request));
			}
		} catch (ConfigurationException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		} catch (IOException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		} catch (GeneralSecurityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	private GoogleAuthorizationCodeFlow constructCodeFlowHelper(long companyId) throws ConfigurationException,
	PortalException, SystemException {
		
		LoginConfiguration loginConfiguration = LoginConfigurationLocalServiceUtil.getLoginConfiguration(companyId);
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		String clientId = loginConfiguration.getClientId();
		String clientSecret = loginConfiguration.getClientSecret();
		Collection<String> scopes = new ArrayList<String>();
		scopes.add("openid profile email");
		return new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, clientId, clientSecret, scopes).build();
	}

}
