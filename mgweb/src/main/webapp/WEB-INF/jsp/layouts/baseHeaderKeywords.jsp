<%-- 
/*
 *	This jsp fragment is used to resolve the html header meta data keywords in a multi-lingual context. Here we can used simple key-values or parameterizable key-values. 3 parameters max are allowed.   
 *	@request.pageKeywordsKey : property key (mg.page.header.keywords.*) to display for html header keywords
 *	@request.pageKeywordsParam[1..3] : Some property keys can have variables to resolve in their value parts. Here only 3 params max are allowed.  
 */	 
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:if test="%{pageKeywordsKey != null && pageKeywordsKey != ''}">
    <s:set var="keywordsValue" value="getText(pageKeywordsKey)" />
    <%-- 
         write keywords meta information only if the key in the resource bundle exist. 
         To do this test we check if the value of getText(key) is different of the key itself, because if the key doesn't exist getText() return the key.   
      --%>
	<s:if test="#keywordsValue != pageKeywordsKey">
	    <meta name="keywords" content="<s:text name="%{pageKeywordsKey}" >
									    		<s:param><s:property value="#request.pageKeywordsParam1" /></s:param>
								    			<s:param><s:property value="#request.pageKeywordsParam2" /></s:param>
								    			<s:param><s:property value="#request.pageKeywordsParam3" /></s:param>
									   </s:text>" />
	</s:if>
	<s:else>
		<meta name="keywords" content=" <s:text name="bolsos.pages.default.keywords"/>"/>
	</s:else>
</s:if>
<s:else>
	<meta name="keywords" content=" <s:text name="bolsos.pages.default.keywords"/>"/>
</s:else>