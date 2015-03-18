<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />

<portlet:actionURL name="saveConfig" var="saveConfig" portletMode="view" />

<div class="row">
	<div class="span12">	
		<c:choose>
			<c:when test="${empty config }">
				<div class="alert">				  
					<liferay-ui:message key="login.configuration.no" />	  
				</div>			
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		<form name='<portlet:namespace/>GoogleLoginConfiguration' id="name='<portlet:namespace/>GoogleLoginConfiguration'" action="${saveConfig}" class="form form-horizontal" method="POST">			
			<input type="hidden" name="<portlet:namespace/>companyId" id="<portlet:namespace/>configurationId" value="${config.companyId}" />
			<fieldset>
				<legend><liferay-ui:message key="login.configuration.title" /></legend>
				  
				<div class="control-group">
              		<label class="control-label" for="<portlet:namespace/>clientId"><liferay-ui:message key="login.configuration.clientId.label" /></label>
              		<div class="controls">
                		<input type="text" id="<portlet:namespace/>clientId" name="<portlet:namespace/>clientId" value="${config.clientId}" class="input-block-level">
              		</div>
            	</div>	    			
				<div class="control-group">
              		<label class="control-label" for="<portlet:namespace/>clientSecret"><liferay-ui:message key="login.configuration.clientSecret.label" /></label>
              		<div class="controls">
                		<input type="text" id="<portlet:namespace/>clientSecret" name="<portlet:namespace/>clientSecret" value="${config.clientSecret}" class="input-block-level">
              		</div>
            	</div>				
				<div class="control-group">
              		<label class="control-label" for="<portlet:namespace/>createUserOnLogin"><liferay-ui:message key="login.configuration.createUserOnLogin.label" /></label>
              		<div class="controls">
                		<input type="checkbox" id="<portlet:namespace/>createUserOnLogin" name="<portlet:namespace/>createUserOnLogin" <c:if test="${config.createUserOnLogin}">checked="checked"</c:if>>
              		</div>
            	</div>
				<div class="control-group">
              		<label class="control-label" for="<portlet:namespace/>doRedirect"><liferay-ui:message key="login.configuration.doRedirect.label" /></label>
              		<div class="controls">
                		<input type="checkbox" id="<portlet:namespace/>doRedirect" name="<portlet:namespace/>doRedirect" <c:if test="${config.doRedirect}">checked="checked"</c:if> onclick="checkDoRedirect();">
                		<liferay-ui:message key="login.configuration.doRedirectAfter.label" />
                		<input type="number" class="input-small" id="<portlet:namespace/>doRedirectAfter" name="<portlet:namespace/>doRedirectAfter" value="${config.doRedirectAfter}"> <liferay-ui:message key="login.configuration.doRedirectAfter.um.label" />
              		</div>
            	</div>
				<div class="form-actions">
				  <button type="submit" class="btn btn-primary"><liferay-ui:message key="login.configuration.save" /></button>
				  <button type="reset" class="btn"><liferay-ui:message key="login.configuration.cancel" /></button>
				</div>            	
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">

	var checkDoRedirect =	function() {
								AUI()
									.use(
											'node',
											function(Y) {
												if(document.getElementById('<portlet:namespace/>doRedirect').checked) {
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
				function(Y) {	
					checkDoRedirect();				
				}
		);
</script>