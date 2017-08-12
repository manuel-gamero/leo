<%-- 
/*
 */	 
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:if test="#request.pageDescriptionKey != null && #request.pageDescriptionKey != ''">
	<s:set var="descriptionValue" value="getText(pageDescriptionKey)" />
    <%-- 
         write keywords meta information only if the key in the resource bundle exist. 
         To do this test we check if the value of getText(key) is different from the key itself, because if the key doesn't exist getText() return the key.   
      --%>
	<s:if test="#descriptionValue != pageDescriptionKey">
	    <meta name="description" content="<s:text name="%{pageDescriptionKey}">
									    		<s:param><s:property value="#request.pageDescriptionParam1" /></s:param>
								    			<s:param><s:property value="#request.pageDescriptionParam2" /></s:param>
								    			<s:param><s:property value="#request.pageDescriptionParam3" /></s:param>
								    	  </s:text>" />
	</s:if>
	<s:else>
		<meta name="description" content="<s:text name="bolsos.pages.default.description"/>" />
	</s:else>
</s:if>
<s:else>
	<meta name="description" content="<s:text name="bolsos.pages.default.description"/>" />
</s:else>