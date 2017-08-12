<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">

			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.shoppingcartuser"/></li>
			</ul>

		</div>

		<div class="col-md-3">
			<!-- *** CUSTOMER MENU *** -->
			<div class="panel panel-default sidebar-menu">
				<s:include value="../template/customerSectionTemplate.jsp">
					<s:param name="active" value="'orders'" />
				</s:include>
			</div>
			<!-- /.col-md-3 -->

			<!-- *** CUSTOMER MENU END *** -->
		</div>

		<div class="col-md-9" id="customer-orders">
			<div class="box">
				<h1><s:text name="bolsos.shoppingcartuser"/></h1>

				<p class="lead"><s:text name="bolsos.shoppingcartuser.paragraph1"/></p>
				<p class="text-muted">
					<s:text name="bolsos.shoppingcartuser.paragraph2"/>
				</p>

				<hr>

				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th><s:text name="bolsos.shoppingcartuser.table.column.reference"/></th>
								<th><s:text name="bolsos.shoppingcartuser.table.column.creationdate"/></th>
								<th><s:text name="bolsos.shoppingcartuser.table.column.total"/></th>
								<th><s:text name="bolsos.shoppingcartuser.table.column.status"/></th>
								<th><s:text name="bolsos.shoppingcartuser.table.column.methoshipping"/></th>
								<th><s:text name="bolsos.shoppingcartuser.table.column.action"/></th>
							</tr>
						</thead>
						<s:if test="%{list != null && list.size > 0}">
							<tbody>
								<s:iterator value="list" status="status">
									<tr id="tr_<s:property value="id"/>">
										<th><s:property value="reference" /></th>
										<td><s:property	value="creationDate" /></td>
										<td><s:property value="total" /></td>
										<td>
											<s:if test='%{statusCode.toString().equals("MAKING")}'>
												<span class="label label-info"><s:text name="bolsos.shoppingcartuser.label.status.prepared"/></span>
											</s:if>
											<s:if test='%{statusCode.toString().equals("FINAL")}'>
												<span class="label label-success"><s:text name="bolsos.shoppingcartuser.label.status.received"/></span>
											</s:if>
											<s:if test='%{statusCode.toString().equals("CANCEL")}'>
												<span class="label label-danger"><s:text name="bolsos.shoppingcartuser.label.status.cancelled"/></span>
											</s:if>
											<s:if test='%{statusCode.toString().equals("NEW")}'>
												<span class="label label-warning"><s:text name="bolsos.shoppingcartuser.label.status.hold"/></span>
											</s:if>
											<s:if test='%{statusCode.toString().equals("PAIMENT")}'>
												<span class="label label-primary"><s:text name="bolsos.shoppingcartuser.label.status.payed"/></span>
											</s:if>
										<td><s:property value="methodShipping.code" /></td>
										<td>
											<a href='<s:text name="url.shoppingcart.detail"/>/<s:property value="id"/>' class="btn btn-primary btn-sm">
												<s:text name="bolsos.shoppingcartuser.button.view"></s:text> 
											</a>
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
		</div>

	</div>
	<!-- /.container -->
</div>
<!-- /#content -->


