<%-- 
/*
 *	This jsp fragment is used to resolve the html header title in a multi-lingual context. Here we can used simple key-values or parameterizable key-values. 3 parameter max are allowed.   
 *	@request.pageTitleKey : property key (mg.page.header.title.*) to display for html header title
 *	@request.pageTitleParam[1..3] : Some property keys can have variables to resolve in their value parts. Here only 3 params max are allowed.  
 */	 
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:if test="%{pageTitleKey != null}">
		<s:set var="titleValue" value="getText(pageTitleKey)" />
	    <%-- 
	         write keywords meta information only if the key in the resource bundle exist. 
	         To do this test we check if the value of getText(key) is different of the key itself, because if the key doesn't exist getText() return the key.   
	      --%>
		<s:if test="#titleValue != pageTitleKey">
		    <s:text name="%{pageTitleKey}">
		    		<s:param><s:property value="#request.pageTitleParam1" /></s:param>
		    		<s:param><s:property value="#request.pageTitleParam2" /></s:param>
		    		<s:param><s:property value="#request.pageTitleParam3" /></s:param>
		    </s:text>
		</s:if>
		<s:else>
			<s:text name="bolsos.pages.default.title"/>
		</s:else>
</s:if>
<s:else>
	<s:text name="bolsos.pages.default.title"/>
</s:else>