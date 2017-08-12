<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">ShoppingCart List</h3>
	</div>
	<div class="panel-body">
		<div id="shoppingCartList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="shoppingCartListData">
				<table data-toggle="table" data-search="true" data-show-refresh="true"
       				   data-show-toggle="true" data-show-columns="true" data-sort-name="id"
       				   data-sort-order="desc">
					<thead>
						<tr>
							<th data-field="id" data-sortable="true" style="width: 10px;"><span class="bold">id</span></th>
							<th data-field="user" data-sortable="true" style="width: 20px;"><span 
								class="bold">User</span></th>
							<th data-field="statusCode" style="width: auto;"><span 
								class="bold">Status</span></th>
							<th data-field="methodShipping" style="width: auto;"><span 
								class="bold">Method Shipping</span></th>
							<th data-field="total" style="width: auto;"><span 
								class="bold">Total</span></th>
							<th data-field="creationdate" data-sortable="true" class="auto"><span 
								class="bold">creation date</span></th>
							<th data-field="action" style="width: auto;"><span 
								class="bold">action</span></th>
						</tr>
					</thead>
					<s:if test="%{list != null && list.size > 0}">
						<tbody>
							<s:iterator value="list" status="status">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="id" /></td>
									<td style="text-align: center;"><s:property value="users.login" /></td>
									<td style="text-align: center;"><s:property value="statusCode" /></td>
									<td style="text-align: center;"><s:property value="methodShipping" /></td>
									<td style="text-align: center;"><s:property value="totalShopping" /></td>
									<td style="text-align: center;"><s:property	value="creationDate" /></td>
									<td style="text-align: center;">
										<a id="edit" href="aeShoppingCart.action?id=<s:property value="id"/>">Edit</a>
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

