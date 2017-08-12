<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Custom Component List</h3>
	</div>
	<div class="panel-body">
		<div id="customComponentList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="customComponentListData">
				<table
					class="table table-bordered table table-hover table table-condensed"
					data-search="true">
					<thead class="gray_header">
						<tr>
							<th data-field="id" style="width: 10px;"><span class="bold">id</span></th>
							<th data-field="code" style="width: 20px;"><span 
								class="bold">code</span></th>
							<th data-field="name" style="width: 30px;"><span 
								class="bold">name</span></th>
							<th data-field="type" style="width: auto;"><span 
								class="bold">type</span></th>
							<th data-field="creationdate" class="auto"><span 
								class="bold">creation date</span></th>
							<th data-field="action" style="width: 10px;"><span 
								class="bold">action</span></th>
						</tr>
					</thead>
					<s:if test="%{list != null && list.size > 0}">
						<tbody>
							<s:iterator value="list" status="status">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="id" /></td>
									<td style="text-align: center;"><s:property value="code" /></td>
									<td style="text-align: center;"><s:property
											value="name" /></td>
									<td style="text-align: center;"><s:property
											value="typeCode" /></td>
									<td style="text-align: center;"><s:property
											value="creationDate" /></td>
									<td style="text-align: center;"><a id="edit"
										href="aeCc.action?id=<s:property value="id"/>">Edit</a></td>
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
		<s:if test="%{customComponentList != null && customComponentList.size > 0}">
			<div>
				<a id="export"
					href="exportTOExcel.do?userId=<s:property value="userId"/>&brand=<s:property value="brand"/>&category=<s:property value="category"/>&userName=<s:property value="userName"/>&upstatus=<s:property value="upstatus"/>">Export
					to excel</a>
			</div>
		</s:if>


	</div>
	<div class="panel-footer">
		<a id="create" class="btn btn-default" href="aeCc.action"	role="button">Create Custom Component</a>
	</div>
</div>

