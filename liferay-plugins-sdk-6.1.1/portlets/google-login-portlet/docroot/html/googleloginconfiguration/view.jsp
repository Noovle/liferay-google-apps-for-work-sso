<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />

<portlet:actionURL name="saveConfig" var="saveConfig" portletMode="view" />

	<c:choose>
		<c:when test="${empty config}">
			<div id="noConfigAlert" class="alert">
					<liferay-ui:message key="login.configuration.no" />	  
			</div>			
		</c:when>
	</c:choose>

	<aui:form name="GoogleLoginConfiguration" action="${saveConfig}" method="POST">		
		
		<aui:fieldset label="login.configuration.title">			

			<aui:input type="hidden" name="companyId" value="${config.companyId}"></aui:input>

			<aui:input type="text" name="clientId" value="${config.clientId}" label="login.configuration.clientId.label" required="true" placeholder="login.configuration.clientId.label" cssClass="large-input"></aui:input>
			
			<aui:input type="text" name="clientSecret" value="${config.clientSecret}" label="login.configuration.clientSecret.label" required="true" placeholder="login.configuration.clientSecret.label" cssClass="large-input"></aui:input>
			
			<aui:input type="checkbox" name="createUserOnLogin" label="login.configuration.createUserOnLogin.label" checked="${config.createUserOnLogin}"></aui:input>
			
			<aui:input type="checkbox" name="doRedirect" label="login.configuration.doRedirect.label" checked="${config.doRedirect}" onClick="checkDoRedirect();"></aui:input>
			
			<aui:input type="number" name="doRedirectAfter" value="${config.doRedirectAfter}" label="login.configuration.doRedirectAfter.label"></aui:input>
			
			<aui:input type="text" name="accessDeniendPage" value="${config.accessDeniendPage}" label="login.configuration.accessDeniendPage.label" placeholder="login.configuration.accessDeniendPage.label" cssClass="large-input"></aui:input>
			
			<aui:button-row>						
				<aui:button type="submit"></aui:button>
				<aui:button type="reset"></aui:button>
			</aui:button-row>
												            	
		</aui:fieldset>
	</aui:form>
<script type="text/javascript">

	var checkDoRedirect =	function() {
								AUI()
									.use(
											'node',
											function(Y) {
												if(document.getElementById('<portlet:namespace/>doRedirectCheckbox').checked) {
													Y.one('#<portlet:namespace/>doRedirectAfter').removeAttribute('disabled');
												} else {
													Y.one('#<portlet:namespace/>doRedirectAfter').setAttribute('disabled','disabled');
												}
											}
										);
									
							}

	AUI()
		.ready(
				'node',
				'aui-alert',
				function(Y) {	
					checkDoRedirect();	
				}
		);
</script>