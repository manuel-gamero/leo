<%@ taglib uri="/struts-tags" prefix="s" %>
<s:if test="#isLogin">
	<jsp:include page="menuLoggedAdminFragment.jsp" />
</s:if>
<s:else>
	<jsp:include page="menuNotLoggedAdminFragment.jsp" />
</s:else>


