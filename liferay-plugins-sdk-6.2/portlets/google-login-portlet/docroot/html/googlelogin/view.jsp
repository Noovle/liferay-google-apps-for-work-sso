<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />

<portlet:actionURL name="doLogin" var="doLogin">
	<portlet:param name="saveLastPath" value="0" />
</portlet:actionURL>

<c:choose>
	<c:when test="${not empty user}">
		<liferay-ui:message key="authenticated-user-label" /> <c:out value="${user}" />
	</c:when>
	<c:otherwise>
		<aui:form action="${doLogin}" method="post" name="fm">
			<aui:fieldset>
				<input type="hidden" value="${state}" name="<portlet:namespace/>state" />
				<aui:button-row>
					<aui:button type="submit" value="sign-in" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>
		<c:set var="authError" value="${not empty sessionScope.GOOGLE_LOGIN_FAILED}"></c:set>		
		<c:if test="${authError}">
<%
			session.removeAttribute("GOOGLE_LOGIN_FAILED");	
		%>
			<div class="alert alert-error">
				<liferay-ui:message key="login.action.denied.user.not.present" />						
			</div>		
		</c:if>
		
		<c:if test="${autoRedirect and timeout>0 and not authError}">
			<script type="text/javascript">
				AUI()
				.ready(
						'node',
						function(Y) {			
							setTimeout(function() { Y.one("#<portlet:namespace/>fm").submit() }, ${timeout * 1000});
						}
				);
			</script>
		</c:if>			
	</c:otherwise>
</c:choose>