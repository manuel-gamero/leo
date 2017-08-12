<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Coupon Type List</h3>
	</div>
	<div class="panel-body">
		<div id="couponsTypeList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="couponsTypeListData">
				<table data-toggle="table" data-search="true" data-show-refresh="true"
       				   data-show-toggle="true" data-show-columns="true" data-sort-name="id"
       				   data-sort-order="desc">
					<thead>
						<tr>
							<th data-field="id" data-sortable="true" style="width: 10px;">
								<span class="bold">id</span>
							</th>
							<th data-field="couponsType" data-sortable="true" style="width: 20px;">
								<span class="bold">couponsType</span>
							</th>
							<th data-field="couponName" data-sortable="true" style="width: 20px;">
								<span class="bold">couponName</span>
							</th>
							<th data-field="statusCode" style="width: auto;">
								<span class="bold">statusCode</span>
							</th>
							<th data-field="inactiveDate" style="width: auto;">
								<span class="bold">inactiveDate</span>
							</th>
							<th data-field="creationdate" class="auto">
								<span class="bold">creation date</span>
							</th>
							<th data-field="action" style="width: auto;">
								<span class="bold">action</span>
							</th>
						</tr>
					</thead>
					<s:if test="%{couponsList != null && couponsList.size > 0}">
						<tbody>
							<s:iterator value="couponsList" status="status">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="id" /></td>
									<td style="text-align: center;"><s:property value="couponsType.code" /></td>
									<td style="text-align: center;"><s:property value="couponName" /></td>
									<td style="text-align: center;"><s:property value="statusCode" /></td>
									<td style="text-align: center;"><s:property value="inactiveDate" /></td>
									<td style="text-align: center;"><s:property	value="creationDate" /></td>
									<td style="text-align: center;">
										<a id="edit" href="aeCouponsType.action?id=<s:property value="id"/>">inactive</a>
										<s:token/>
										<a href="<s:url  action="removeCouponsType">
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

</div>
