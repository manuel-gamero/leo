<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">User List</h3>
	</div>
	<div class="panel-body">
		<div id="userList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="userListData" >
				<table
					class="table table-bordered table table-hover table table-condensed"
					data-search="true">
					<thead class="gray_header">
						<tr>
							<th data-field="id"><span style="width: 70px;" class="bold">id</span></th>
							<th data-field="login"><span style="width: 70px;"
								class="bold">login</span></th>
							<th data-field="password"><span style="width: 70px;"
								class="bold">password</span></th>
							<th data-field="email"><span style="width: 70px;"
								class="bold">email</span></th>
							<th data-field="type"><span style="width: 70px;"
								class="bold">type</span></th>
							<th data-field="status"><span style="width: 50px;"
								class="bold">status</span></th>
							<th data-field="creationdate"><span style="width: 20px;"
								class="bold">creation date</span></th>
							<th data-field="action"><span style="width: 70px;"
								class="bold">action</span></th>
						</tr>
					</thead>
					<s:if test="%{userList != null && userList.size > 0}">
						<tbody>
							<s:iterator value="userList" status="status">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="id" /></td>
									<td style="text-align: center;"><s:property value="login" /></td>
									<td style="text-align: center;"><s:property
											value="password" /></td>
									<td style="text-align: center;"><s:property value="email" /></td>
									<td style="text-align: center;"><s:property
											value="typeCode" /></td>
									<td style="text-align: center;"><s:property
											value="statusCode" /></td>
									<td style="text-align: center;"><s:property
											value="creationDate" /></td>
									<td style="text-align: center;"><a id="edit"
										href="aeUser.action?id=<s:property value="id"/>">Edit</a></td>
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
		<s:if test="%{userList != null && userList.size > 0}">
			<div>
				<a id="export"
					href="exportTOExcel.do?userId=<s:property value="userId"/>&brand=<s:property value="brand"/>&category=<s:property value="category"/>&userName=<s:property value="userName"/>&upstatus=<s:property value="upstatus"/>">Export
					to excel</a>
			</div>
		</s:if>


	</div>
	<div class="panel-footer">
		<a id="create" class="btn btn-default" href="aeUser.action"	role="button">Create User</a>
	</div>
</div>

