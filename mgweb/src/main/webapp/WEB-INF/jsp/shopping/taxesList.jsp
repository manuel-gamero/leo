<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Taxes List</h3>
	</div>
	<div class="panel-body">
		<div id="taxesList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="taxesListData">
				<table
					class="table table-bordered table table-hover table table-condensed"
					data-search="true">
					<thead class="gray_header">
						<tr>
							<th data-field="id" style="width: 10px;"><span class="bold">id</span></th>
							<th data-field="code" style="width: 20px;"><span 
								class="bold">code</span></th>
							<th data-field="name" style="width: auto;"><span 
								class="bold">Description</span></th>
							<th data-field="name" style="width: auto;"><span 
								class="bold">Tax</span></th>
							<th data-field="creationdate" class="auto"><span 
								class="bold">creation date</span></th>
							<th data-field="action" style="width: auto;"><span 
								class="bold">action</span></th>
						</tr>
					</thead>
					<s:if test="%{taxesList != null && taxesList.size > 0}">
						<tbody>
							<s:iterator value="taxesList" status="status">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="id" /></td>
									<td style="text-align: center;"><s:property value="code" /></td>
									<td style="text-align: center;"><s:property value="description" /></td>
									<td style="text-align: center;"><s:property value="tax" /></td>
									<td style="text-align: center;"><s:property	value="creationDate" /></td>
									<td style="text-align: center;">
										<a id="edit" href="aeTaxes.action?id=<s:property value="id"/>">Edit</a>
										<s:token/>
										<a href="<s:url  action="removeTaxes">
											<s:param name="id"><s:property value="id"/></s:param>
										  	<s:param name="struts.token.name" value="'token'"/>
											<s:param name="token" value="#session['struts.tokens.token']"/>
										</s:url>">Remove</a>
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
		<s:if test="%{taxesList != null && taxesList.size > 0}">
			<div>
				<a id="export"
					href="exportTOExcel.do?userId=<s:property value="userId"/>&brand=<s:property value="brand"/>&category=<s:property value="category"/>&userName=<s:property value="userName"/>&upstatus=<s:property value="upstatus"/>">Export
					to excel</a>
			</div>
		</s:if>


	</div>
	<div class="panel-footer">
		<a id="create" class="btn btn-default" href="aeTaxes.action" role="button">Create Tax</a>
	</div>
</div>

