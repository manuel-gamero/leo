<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<jsp:include page="applicationVariablesFragment.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="../${css}/bootstrap.min.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../${css}/bootstrap-theme.min.css" media="screen"/>
	
<script type="text/javascript" src="../${scripts}/jquery-1.10.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../${scripts}/bootstrap.min.js"></script>
	
<!-- Add specific css files -->
<tiles:importAttribute name="cssList" scope="request"/>
<s:iterator value="#request.cssList">
   <link rel="stylesheet" type="text/css" href="../${css}/<s:property/>" />
</s:iterator>
<!-- Add specific css files -->

<!-- Add specific js files -->
<tiles:importAttribute name="scriptList" scope="request"/>
<s:iterator value="#request.scriptList">
   <script type="text/javascript" src="../${scripts}/<s:property/>" charset="utf-8" ></script>
</s:iterator>

<div id="ajax-modal" class="modal fade in">
  <div class="modal-dialog">
    <div class="modal-content">
		<!-- Add specific js files -->
		<tiles:insertAttribute name="body" />
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->