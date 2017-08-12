<%-- 
/*
 *	This jsp fragment is used to resolve the html header title in a multi-lingual context. Here we can used simple key-values or parameterizable key-values. 3 parameter max are allowed.   
 *	@request.pageTitleKey : property key (mg.page.header.title.*) to display for html header title
 *	@request.pageTitleParam[1..3] : Some property keys can have variables to resolve in their value parts. Here only 3 params max are allowed.  
 */	 
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:if test="%{pageFbTitle != null && pageFbTitle != ''}">
    <s:set var="fbTitleValue" value="getText(pageFbTitle)" />
    <%-- 
         write keywords meta information only if the key in the resource bundle exist. 
         To do this test we check if the value of getText(key) is different of the key itself, because if the key doesn't exist getText() return the key.   
      --%>
	<s:if test="#fbTitleValue != pageFbTitle">
	    <meta property="og:title" content="<s:text name="%{pageFbTitle}" >
									    		<s:param><s:property value="#request.pageFbTitleParam1" /></s:param>
								    			<s:param><s:property value="#request.pageFbTitleParam2" /></s:param>
								    			<s:param><s:property value="#request.pageFbTitleParam3" /></s:param>
									   </s:text>" />
	</s:if>
	<s:else>
		<meta property="og:title" content="<s:text name="bolsos.pages.default.facebook.title"/>" />
	</s:else>
</s:if>
<s:else>
	<meta property="og:title" content="<s:text name="bolsos.pages.default.facebook.title"/>" />
</s:else>

<s:if test="%{pageFbDescription != null && pageFbDescription != ''}">
    <s:set var="fbDescriptionValue" value="getText(pageFbDescription)" />
    <%-- 
         write keywords meta information only if the key in the resource bundle exist. 
         To do this test we check if the value of getText(key) is different of the key itself, because if the key doesn't exist getText() return the key.   
      --%>
	<s:if test="#fbDescriptionValue != pageFbDescription">
	    <meta property="og:description" content="<s:text name="%{pageFbDescription}" >
									    		<s:param><s:property value="#request.pageFbDescriptionParam1" /></s:param>
								    			<s:param><s:property value="#request.pageFbDescriptionParam2" /></s:param>
								    			<s:param><s:property value="#request.pageFbDescriptionParam3" /></s:param>
									   </s:text>" />
	</s:if>
	<s:else>
		<meta property="og:description" content="<s:text name="bolsos.pages.default.facebook.description"/>" />
	</s:else>
</s:if>
<s:else>
	<meta property="og:description" content="<s:text name="bolsos.pages.default.facebook.description"/>" />
</s:else>

<s:if test="%{pageFbUrl != null && pageFbUrl != ''}">
    <s:set var="fbUrlValue" value="getText(pageFbUrl)" />
    <%-- 
         write keywords meta information only if the key in the resource bundle exist. 
         To do this test we check if the value of getText(key) is different of the key itself, because if the key doesn't exist getText() return the key.   
      --%>
	<s:if test="#fbUrlValue != pageFbUrl">
	    <meta property="og:url" content="<s:text name="%{pageFbUrl}" >
									    		<s:param><s:property value="#request.pageFbUrlParam1" /></s:param>
								    			<s:param><s:property value="#request.pageFbUrlParam2" /></s:param>
								    			<s:param><s:property value="#request.pageFbUrlParam3" /></s:param>
									   </s:text>" />
	</s:if>
	<s:else>
		<meta property="og:url" content="<s:text name="bolsos.pages.default.facebook.url"/>" />
	</s:else>
</s:if>
<s:else>
	<meta property="og:url" content="<s:text name="bolsos.pages.default.facebook.url"/>" />
</s:else>

<s:if test="%{pageFbImage != null && pageFbImage != ''}">
	<meta property="og:image" content="<s:text name="pageFbImage"/>" />
</s:if>
<s:else>
	<meta property="og:image" content="<s:text name="bolsos.pages.default.facebook.image"/>" />
</s:else>

	<meta property="og:site_name" content="L'atelier de Leo">
	<meta property="fb:app_id" content="878026268975881" >
	<meta property="og:type" content="website" >
	<meta property="og:locale" content="fr_CA" >
