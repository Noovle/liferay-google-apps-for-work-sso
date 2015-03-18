package it.noovle.googlelogin.util;

public class LoginConstants {

	private static final String LIFERAY_SHARED_SESSION_VARIABLE = "LIFERAY_SHARED_";

	public static final String USER_VERIFIED_EMAIL = LIFERAY_SHARED_SESSION_VARIABLE + "userVerifiedEmail";
	public static final String STATE_TOKEN = LIFERAY_SHARED_SESSION_VARIABLE + "stateToken";
	public static final String REDIRECT_AFTER_LOGIN = LIFERAY_SHARED_SESSION_VARIABLE + "redirectAfterLogin";
	public static final String RELATIVE_OAUTH2_CALLBACK = "/c/portal/rest/noovle/openidconnect/oauth2callback";
}
