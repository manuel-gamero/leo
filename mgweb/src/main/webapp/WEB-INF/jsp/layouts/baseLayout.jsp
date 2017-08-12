<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<jsp:include page="applicationVariablesFragment.jsp" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang='<s:property value="currentLanguage"/>'>
<head>
	<meta name="fragment" content="!"/>
	<meta name="google-site-verification" content="HnKXBzyCYLg-zquv5QjoYz5aO5CK8B5A8mAGfzh26es" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>
		<tiles:insertAttribute name="title" />
	</title>
	<tiles:insertAttribute name="keywords" />
	<tiles:insertAttribute name="description" />
	<tiles:insertAttribute name="facebook" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />	
	<meta name="robots" content="index,follow" />
	<link rel="alternate" hreflang="fr" href="http://www.latelierdeleo.com/fr/homePage" />
	<link rel="alternate" hreflang="en" href="http://www.latelierdeleo.com/en/homePage" />
	<link rel="icon" href="${images}/favicon.ico" type="image/x-icon" />	
	
	<link rel="stylesheet" type="text/css" href="${css}/bootstrap.min.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="${css}/font-awesome.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="${css}/animate.min.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="${css}/owl.carousel.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="${css}/owl.theme.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="${css}/style.default.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="${css}/mg.css" media="screen"/>


	<script type="text/javascript" src="${scripts}/jquery-1.10.1.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${scripts}/bootstrap.min.js"></script>
	<script type="text/javascript" src="${scripts}/bootstrap-hover-dropdown.js"></script>
	<script type="text/javascript" src="${scripts}/validator.js"></script>

	
		
	
	<!-- Add specific css files -->
	<tiles:importAttribute name="cssList" scope="request"/>
	<s:iterator value="#request.cssList">
	   <link rel="stylesheet" type="text/css" href="${css}/<s:property/>" />
	</s:iterator>
		
	


	
	
</head>
<body>
	<tiles:insertAttribute name="menu" />
	<div id="all">
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
	</div>
</body>

	<!-- Add specific js files -->
		<tiles:importAttribute name="scriptList" scope="request"/>
		<s:iterator value="#request.scriptList">
		   <script type="text/javascript" src="${scripts}/<s:property/>" charset="utf-8" ></script>
		</s:iterator>
		
	<!-- Add specific js files -->
	
	
	
</html>