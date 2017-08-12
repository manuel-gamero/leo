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
							<th data-field="Code" data-sortable="true" style="width: 20px;">
								<span class="bold">Code</span>
							</th>
							<th data-field="typeCode" data-sortable="true" style="width: 20px;">
								<span class="bold">typeCode</span>
							</th>
							<th data-field="multiTime" style="width: auto;">
								<span class="bold">multiTime</span>
							</th>
							<th data-field="multiUser" style="width: auto;">
								<span class="bold">multiUser</span>
							</th>
							<th data-field="name" style="width: auto;">
								<span class="bold">Name</span>
							</th>
							<th data-field="description" style="width: auto;">
								<span class="bold">Description</span>
							</th>
							<th data-field="creationdate" class="auto">
								<span class="bold">creation date</span>
							</th>
							<th data-field="action" style="width: auto;">
								<span class="bold">action</span>
							</th>
						</tr>
					</thead>
					<s:if test="%{couponsTypeList != null && couponsTypeList.size > 0}">
						<tbody>
							<s:iterator value="couponsTypeList" status="status">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="id" /></td>
									<td style="text-align: center;"><s:property value="code" /></td>
									<td style="text-align: center;"><s:property value="typeCode" /></td>
									<td style="text-align: center;"><s:property value="multiTime" /></td>
									<td style="text-align: center;"><s:property value="multiUser" /></td>
									<td style="text-align: center;">
										<p>En: <s:text name="%{getTranslaction(translationByNameTransId.id,'en')}" /></p>
										<p>Fr: <s:text name="%{getTranslaction(translationByNameTransId.id,'fr')}" /></p>
									</td>
									<td style="text-align: center;">
										<p>En: <s:text name="%{getTranslaction(translationByDescriptionTransId.id,'en')}"/></p>
									</td>
									<td style="text-align: center;"><s:property	value="creationDate" /></td>
									<td style="text-align: center;">
										<a id="edit" href="aeCouponsType.action?id=<s:property value="id"/>">Edit</a>
										<s:token/>
										<a href="<s:url  action="removeCouponsType">
											<s:param name="id"><s:property value="id"/></s:param>
										  	<s:param name="struts.token.name" value="'token'"/>
											<s:param name="token" value="#session['struts.tokens.token']"/>
										</s:url>">Remove</a>
										<div>	  
											<s:form id="form" action="createCouponsType" namespace="/admin" method="post"
											validate="true" enctype="multipart/form-data" class="form-horizontal">
												<s:token/>
												<s:hidden name="id" value="%{id}" />
												<s:textfield id="number" name="number" class="form-control"
												placeholder="Number" cssStyle="width:90px;" />
												<s:submit value="Create" class="btn btn-info"></s:submit>
											</s:form>
										</div>
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
		<a id="create" class="btn btn-default" href="aeCouponsType.action" role="button">Create Coupon Type</a>
	</div>
</div>
