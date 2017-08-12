<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="datamining" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Audit List</h3>
		</div>
		<div class="panel-body">
			<div class="container">
			    <div class='col-md-5'>
			        <div class="form-group">
			            <div class='input-group date' id='datetimepicker6' >
			                <input type='text' class="form-control" name="startDate"/>
			                <span class="input-group-addon">
			                    <span class="glyphicon glyphicon-calendar"></span>
			                </span>
			            </div>
			        </div>
			    </div>
			    <div class='col-md-5'>
			        <div class="form-group">
			            <div class='input-group date' id='datetimepicker7'>
			                <input type='text' class="form-control" name="endDate"/>
			                <span class="input-group-addon">
			                    <span class="glyphicon glyphicon-calendar"></span>
			                </span>
			            </div>
			        </div>
			    </div>
			    <s:submit value="Search" class="btn btn-default"></s:submit>
			</div>
			<div id="auditList" class="fixed column"
				style="padding: 20px 0px 0px 0px;">
				<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
				<div id="auditListData">
					<table data-toggle="table" data-search="true" data-show-refresh="true"
       				   data-show-toggle="true" data-show-columns="true" data-sort-name="creationdate"
       				   data-sort-order="desc">
						<thead>
							<tr>
								<th data-field="username" style="width: 10px;"><span class="bold">username</span></th>
								<th data-field="location" style="width: 20px;"><span 
									class="bold">location</span></th>
								<th data-field="action" style="width: auto;"><span 
									class="bold">action</span></th>
								<th data-field="session" style="width: auto;"><span 
									class="bold">session</span></th>
								<th data-field="outcome" style="width: auto;"><span 
									class="bold">outcome</span></th>
								<th data-field="message" style="width: auto;"><span 
									class="bold">message</span></th>
								<th data-field="server" style="width: auto;"><span 
									class="bold">server</span></th>
								<th data-field="browser" style="width: auto;"><span 
									class="bold">browser</span></th>
								<th data-field="url" style="width: auto;"><span 
									class="bold">url</span></th>
								<th data-field="duration" style="width: auto;"><span 
									class="bold">duration</span></th>
								<th data-field="creationdate" data-sortable="true" class="auto"><span 
									class="bold">creationdate</span></th>
							</tr>
						</thead>
						<s:if test="%{auditList != null && auditList.size > 0}">
							<tbody>
								<s:iterator value="auditList" status="status" var="item">
									<tr id="tr_<s:property value="id"/>">
										<td style="text-align: center;"><s:property value="username" /></td>
										<td style="text-align: center;"><s:property value="location" /></td>
										<td style="text-align: center;"><s:property value="actionUser" /></td>
										<td style="text-align: center;"><s:property value="sessionGuid" /></td>
										<td style="text-align: center;"><s:property value="outcome" /></td>
										<td style="text-align: center;"><s:property value="message" /></td>
										<td style="text-align: center;"><s:property value="serverName" /></td>
										<td style="text-align: center;"><s:property value="browser" /></td>
										<td style="text-align: center;"><s:property	value="url" /></td>
										<td style="text-align: center;"><s:property	value="callDuration" /></td>
										<td style="text-align: center;"><s:date name="creationDate" format="dd/MM/yyyy HH:mm:ss" /></td>
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
			<s:if test="%{methodShippingList != null && methodShippingList.size > 0}">
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
    $(function () {
        $('#datetimepicker6').datetimepicker();
        $('#datetimepicker7').datetimepicker({
            useCurrent: false //Important! See issue #1075
        });
        $("#datetimepicker6").on("dp.change", function (e) {
            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker7").on("dp.change", function (e) {
            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
        });
    });
</script>
