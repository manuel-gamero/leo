<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title"><s:property value="title" /></h3>
	</div>
	<div class="panel-body">
		<div id="itemList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="itemListData">
				<table data-toggle="table" data-search="true" data-show-refresh="true"
       				   data-show-toggle="true" data-show-columns="true" data-sort-name="id"
       				   data-sort-order="desc">
					<thead>
						<tr>
							<s:iterator value="fieldsView" status="status" var="field">
								<th data-field="<s:property value="%{#field}" />" style="width: auto;">
									<span class="bold"><s:property value="%{#field}" /></span>
								</th>
							</s:iterator>
							<th data-field="action" style="width: auto;">
								<span class="bold">action</span>
							</th>
						</tr>
					</thead>
					<s:if test="%{itemList != null && itemList.size > 0}">
						<tbody>
							<s:iterator value="itemList" status="status" var="item">
								<tr id="tr_<s:property value="id"/>">
									<s:iterator value="fieldsView" status="status" var="field">
										<td style="text-align: center;"><s:text name="%{getFieldValue(#item, #field)}" /></td>
									</s:iterator>
							</s:iterator>
							<td class="text-align center">
								<a id="edit" href="aeEntity.action?id=<s:text name="%{getFieldValue(#item, 'id')}" />">edit</a>
							</td>
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
