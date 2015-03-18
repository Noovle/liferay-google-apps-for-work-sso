package it.noovle.googlelogin.portlet;

import it.noovle.googlelogin.services.model.LoginConfiguration;
import it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil;
import it.noovle.googlelogin.util.LoginConstants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.ConfigurationException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class GoogleLoginPortlet extends MVCPortlet {

	private static final Log log = LogFactory.getLog(GoogleLoginPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			
			User user = PortalUtil.getUser(renderRequest);
			if (user == null) {
				Portlet portlet =
						PortletLocalServiceUtil.getPortletByStrutsPath(PortalUtil.getCompanyId(renderRequest), "login/login");
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				PortletURL actionUrl =
						PortletURLFactoryUtil.create(renderRequest, portlet.getPortletId(), themeDisplay.getPlid(),
								PortletRequest.ACTION_PHASE);
				actionUrl.setWindowState(WindowState.NORMAL);
				// actionUrl.setParameter("saveLastPath", "0");
				actionUrl.setParameter("struts_action", "/login/open_id");
				renderRequest.setAttribute("openid_url", actionUrl.toString());				
				
				LoginConfiguration loginConfiguration = LoginConfigurationLocalServiceUtil.getLoginConfiguration(PortalUtil.getCompanyId(renderRequest));
				
				renderRequest.setAttribute("autoRedirect", loginConfiguration.isDoRedirect());
				renderRequest.setAttribute("timeout", loginConfiguration.getDoRedirectAfter());
				
				
				String redirect = findRedirect(renderRequest);
				PortletSession session = renderRequest.getPortletSession();
				if (Validator.isNotNull(redirect)) {
					session.setAttribute(LoginConstants.REDIRECT_AFTER_LOGIN, redirect, PortletSession.APPLICATION_SCOPE);
				}
				String state = new BigInteger(130, new SecureRandom()).toString(32);
				session.setAttribute(LoginConstants.STATE_TOKEN, state, PortletSession.APPLICATION_SCOPE);
				renderRequest.setAttribute("state", state);
			} else {
				renderRequest.setAttribute("user", user.getFullName());
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		super.doView(renderRequest, renderResponse);
	}	

	public void doLogin(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		try {
			String state = ParamUtil.get(actionRequest, "state", "");
			GoogleAuthorizationCodeFlow codeFlow = this.constructCodeFlowHelper(PortalUtil.getCompanyId(actionRequest));
			String redirect =
					codeFlow.newAuthorizationUrl().setRedirectUri(
							PortalUtil.getPortalURL(actionRequest) + LoginConstants.RELATIVE_OAUTH2_CALLBACK).setState(state).build();
						
			actionResponse.sendRedirect(redirect);
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}

	private GoogleAuthorizationCodeFlow constructCodeFlowHelper(long companyId) throws ConfigurationException,
	PortalException, SystemException {		
		LoginConfiguration config = LoginConfigurationLocalServiceUtil.getLoginConfiguration(companyId);
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		String clientId = config.getClientId();
		String clientSecret = config.getClientSecret();
		Collection<String> scopes = new ArrayList<String>();
		scopes.add("openid profile email");
		return new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, clientId, clientSecret, scopes).build();
	}

	private String findRedirect(RenderRequest renderRequest) {
		String redirect = null;
		String url = PortalUtil.getCurrentURL(renderRequest);
		Pattern p = Pattern.compile("redirect=([^&]+)");
		Matcher m = p.matcher(url);
		// prendo solo il primo, anche se sono sicuro che al max ce n'è uno.
		if (m.find()) {
			// ho preso il parametro intero, devo prendere solo il valore;
			try {
				redirect = URLDecoder.decode(m.group().split("=")[1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// nothing to do
			}
		}
		if (Validator.isNull(redirect)) {
			// nessun parametro di redirect, quindi tento con la landing page
			try {
				redirect =
						PrefsPropsUtil.getString(PortalUtil.getCompanyId(renderRequest), PropsKeys.DEFAULT_LANDING_PAGE_PATH);
			} catch (SystemException e) {
				// nothing to do
			}
			if (Validator.isNull(redirect)) {
				// non è settata la landing page o c'è stato un errore nel leggerla, vado di root del portale
				redirect = PortalUtil.getPortalURL(renderRequest);
			}
		}
		return redirect;
	}

}
