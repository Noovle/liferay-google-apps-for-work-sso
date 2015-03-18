package it.noovle.googlelogin.portlet;

import it.noovle.googlelogin.services.model.LoginConfiguration;
import it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil;

import java.io.IOException;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class GoogleLoginConfigurationPortlet extends MVCPortlet {

	private static final Log log = LogFactory.getLog(GoogleLoginConfigurationPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			LoginConfiguration loginConfiguration = LoginConfigurationLocalServiceUtil.getLoginConfiguration(PortalUtil.getCompanyId(renderRequest));
			
			renderRequest.setAttribute("config", loginConfiguration);
			
		} catch (PortalException e) {
			log.error(e.getMessage());
		} catch (SystemException e) {
			log.error(e.getMessage());
		}
		
		super.doView(renderRequest, renderResponse);
	}
	
	public void saveConfig(ActionRequest actionRequest, ActionResponse actionResponse) throws SystemException, PortalException {
		long companyId = ParamUtil.getLong(actionRequest, "companyId", 0);
		
		LoginConfiguration currentConfiguration = null;
		
		if(companyId>0) {
			currentConfiguration = LoginConfigurationLocalServiceUtil.getLoginConfiguration(companyId);
		} else {
			currentConfiguration = LoginConfigurationLocalServiceUtil.createLoginConfiguration(PortalUtil.getCompanyId(actionRequest));
		}
		
		String clientId = ParamUtil.get(actionRequest, "clientId", "");
		String clientSecret = ParamUtil.get(actionRequest, "clientSecret", "");		
		boolean createUserOnLogin = ParamUtil.getBoolean(actionRequest, "createUserOnLogin", false);
		boolean doRedirect = ParamUtil.getBoolean(actionRequest, "doRedirect", false);
		long doRedirectAfter = ParamUtil.getLong(actionRequest, "doRedirectAfter", 0);		
		
		currentConfiguration.setClientId(clientId);
		currentConfiguration.setClientSecret(clientSecret);
		currentConfiguration.setCreateUserOnLogin(createUserOnLogin);
		currentConfiguration.setDoRedirect(doRedirect);
		currentConfiguration.setDoRedirectAfter(doRedirectAfter);		
		currentConfiguration.setModifiedDate(new Date());
		
		currentConfiguration.persist();	
	}
}
