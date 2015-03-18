package it.noovle.googlelogin.openidconnect;

import it.noovle.googlelogin.services.model.LoginConfiguration;
import it.noovle.googlelogin.services.service.LoginConfigurationLocalServiceUtil;
import it.noovle.googlelogin.util.LoginConstants;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.BaseAutoLogin;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

public class GoogleAutoLogin extends BaseAutoLogin {

	private static final Log log = LogFactory.getLog(GoogleAutoLogin.class);
	
	@Override
	protected String[] doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			String userEmail = GetterUtil.getString(session.getAttribute(LoginConstants.USER_VERIFIED_EMAIL));
			if (Validator.isNotNull(userEmail)) {
				session.removeAttribute(LoginConstants.USER_VERIFIED_EMAIL);
				long companyId = PortalUtil.getCompanyId(request);

				User user = null;

				try {
					user = UserLocalServiceUtil.getUserByEmailAddress(companyId, userEmail);
				} catch(PortalException ex) {
					log.info("User with mail "+userEmail+" not exists.");

					LoginConfiguration loginConfiguration = LoginConfigurationLocalServiceUtil.getLoginConfiguration(companyId);

					if(loginConfiguration.isCreateUserOnLogin()) {
						log.info("Create user!");
						user = this.createUser(userEmail, companyId);
					} else {
						log.info("Don't create user!");
					}

					if(user == null) {
						session.setAttribute("GOOGLE_LOGIN_FAILED", true);
						return null; 						
					}
					
					
				}

				String[] credentials = new String[3];
				credentials[0] = String.valueOf(user.getUserId());
				credentials[1] = user.getPassword();
				credentials[2] = String.valueOf(user.isPasswordEncrypted());
				return credentials;
			}
			return null;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw e;			
		}
	}
	
	private User createUser(String userEmail, long companyId) {
		// Preparo tutti i parametri per creare l'utente (più per leggibilità che altro)

		Calendar today = Calendar.getInstance();

		boolean autoPassword = true;
		String password1 = RandomStringUtils.randomAlphanumeric(15);
		String password2 = password1;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		int facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName= userEmail.substring(0, userEmail.lastIndexOf("@"));
		String middleName = StringPool.BLANK;
		String lastName = firstName;
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = today.get(Calendar.MONTH);
		int birthdayDay = today.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = today.get(Calendar.YEAR);
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();
		User liferayUser = null;
		try {
			long creatorUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
			liferayUser = UserLocalServiceUtil.addUser(
					creatorUserId,
					companyId,
					autoPassword,
					password1,
					password2,
					autoScreenName,
					screenName,
					userEmail,
					facebookId,
					openId,
					locale,
					firstName,
					middleName,
					lastName,
					prefixId,
					suffixId,
					male,
					birthdayMonth,
					birthdayDay,
					birthdayYear,
					jobTitle,
					groupIds,
					organizationIds,
					roleIds,
					userGroupIds,
					sendEmail,
					serviceContext);
			log.info("creato utente " + userEmail);
			// setto alcuni parametri per l'utente
			try {
				liferayUser.setPasswordReset(false);
				liferayUser.setTimeZoneId(TimeZoneUtil.getDefault().getID());
				liferayUser.setLanguageId(LanguageUtil.getLanguageId(LocaleUtil.getDefault()));
				// utente attivo di default
				liferayUser.setStatus(WorkflowConstants.STATUS_APPROVED);
				liferayUser = UserLocalServiceUtil.updateUser(liferayUser);				
			} catch (Exception e) {
				log.error("Impossibile aggiornare alcune informazioni per l'utente " + userEmail);
				log.error(ExceptionUtils.getStackTrace(e));
			}
		} catch (DuplicateUserEmailAddressException e) {
			log.warn("Utente con email " + userEmail + " già presente in Liferay");
		} catch (Exception e) {
			log.error("Impossibile creare l'utente " + userEmail + ". Ragione sconosciuta.");
			log.error(ExceptionUtils.getStackTrace(e));
		}

		return liferayUser;
	}	
}
