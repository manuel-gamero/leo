<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="auditList" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Translation List</h3>
		</div>
		<div>
			<a id="translationT_0" class="btn btn-default popup" >Create translation</a>
		</div>
		<div class="panel-body">
			<div id="translationList" class="fixed column"
				style="padding: 20px 0px 0px 0px;">
				<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
				<div id="translationListData">
					<table data-toggle="table" data-search="true" data-show-refresh="true"
       				   data-show-toggle="true" data-show-columns="true" data-sort-name="creationdate"
       				   data-sort-order="desc">
						<thead>
							<tr>
								<th data-field="id" style="width: 10px;"><span class="bold">id</span></th>
								<th data-field="enTranslation" style="width: auto;"><span 
									class="bold">En translation</span></th>
								<th data-field="frTranslation" style="width: auto;"><span 
									class="bold">Fr translation</span></th>
								<th data-field="creationDate" style="width: auto;"><span 
									class="bold">Creation Date</span></th>
								<th data-field="action" style="width: auto;"><span 
									class="bold">Action</span></th>
							</tr>
						</thead>
						<s:if test="%{translationList != null && translationList.size > 0}">
							<tbody>
								<s:iterator value="translationList" status="status" var="item">
									<tr id="tr_<s:property value="id"/>">
										<td style="text-align: center;"><s:property value="id" /></td>
										<td style="text-align: center;"><s:text name="%{getTranslaction(id,'en')}" /></td>
										<td style="text-align: center;"><s:text name="%{getTranslaction(id,'fr')}" /></td>
										<td style="text-align: center;"><s:date name="creationDate" format="dd/MM/yyyy HH:mm:ss" /></td>
										<td style="text-align: center;">
											<a id="translationT_<s:property value="id"/>" class="btn btn-default popup" >Edit translation</a>
										</td>
									</tr>
								</s:iterator>
	
								<s:else>
									<tr></tr>
								</s:else>
							</tbody>
						</s:if>
					</table>
	
				</div>
			</div>
			<s:if test="%{translationListData != null && translationListData.size > 0}">
				<div>
					<a id="export"
						href="exportTOExcel.do?userId=<s:property value="userId"/>&brand=<s:property value="brand"/>&category=<s:property value="category"/>&userName=<s:property value="userName"/>&upstatus=<s:property value="upstatus"/>">Export
						to excel</a>
				</div>
			</s:if>
	
	
		</div>
		<div class="panel-footer">
		
		</div>
	</div>
</s:form>
<script type="text/javascript">

$(document).ready(function() { 

  console.log('Asign click event to addCC');
	
  var $modal = $('#ajax-modal');
  
  $('[id^=translationT]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		$.get('aeTranslation.action?id=' + id, function(data) {
		      $(data).modal();
		  });
	});
});

</script>
