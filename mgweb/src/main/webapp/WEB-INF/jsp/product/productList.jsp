<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Product List</h3>
	</div>
	<div class="panel-body">
		<div id="collectionList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="colectionListData">
				<table data-toggle="table" data-search="true" data-show-refresh="true"
       				   data-show-toggle="true" data-show-columns="true" data-sort-name="id"
       				   data-sort-order="id">
					<thead class="gray_header">
						<tr>
							<th data-field="id" data-sortable="true"><span style="width: 70px;" class="bold">id</span></th>
							<th data-field="code" data-sortable="true"><span style="width: 70px;"
								class="bold">Collection</span></th>
							<th data-field="name"><span style="width: 70px;"
								class="bold">name</span></th>
							<th data-field="type"><span style="width: 70px;"
								class="bold">type</span></th>
							<th data-field="new"><span style="width: 70px;"
								class="bold">new</span></th>
							<th data-field="status"><span style="width: 70px;"
								class="bold">Status</span></th>
							<th data-field="status"><span style="width: 70px;"
								class="bold">Custom</span></th>
							<th data-field="creationdate" data-sortable="true"><span style="width: 20px;"
								class="bold">creation date</span></th>
							<th data-field="action"><span style="width: 70px;"
								class="bold">action</span></th>
						</tr>
					</thead>
					<s:if test="%{list != null && list.size > 0}">
						<tbody>
							<s:iterator value="list" status="status">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="id" /></td>
									<td style="text-align: center;"><s:property value="collection.code" /> - <s:text name="%{getTranslaction(collection.translationByNameTransId.id,'en')}" /></td>
									<td style="text-align: center;">
										<p>En: <s:text name="%{getTranslaction(translationByNameTransId.id,'en')}" /></p>
										<p>Fr: <s:text name="%{getTranslaction(translationByNameTransId.id,'fr')}" /></p>
									</td>
									<td style="text-align: center;"><s:property	value="typeCode.code" /></td>
									<td style="text-align: center;"><s:property	value="newProduct" /></td>
									<td style="text-align: center;"><s:property value="statusCode" /></td>
									<td style="text-align: center;"><s:property value="customProduct" /></td>
									<td style="text-align: center;"><s:property
											value="creationDate" /></td>
									<td style="text-align: center;"><a id="edit"
										href="aeProduct.action?id=<s:property value="id"/>">Edit</a></td>
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
		<s:if test="%{list != null && list.size > 0}">
			<div>
				<a id="export"
					href="exportTOExcel.do?userId=<s:property value="userId"/>&brand=<s:property value="brand"/>&category=<s:property value="category"/>&userName=<s:property value="userName"/>&upstatus=<s:property value="upstatus"/>">Export
					to excel</a>
			</div>
		</s:if>


	</div>
	<div class="panel-footer">
		<a id="create" class="btn btn-default" href="aeProduct.action"	role="button">Create Product</a>
	</div>
</div>

