<%
if( pageContext.getResponse().getLocale().getLanguage() == "fr"){
	response.sendRedirect("/fr/homePage");
}
else{
	response.sendRedirect("/en/homePage");
}%>