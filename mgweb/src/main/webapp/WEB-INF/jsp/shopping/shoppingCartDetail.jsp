<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">

			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><a href="#"><s:text name="bolsos.shoppingcart.detail"/></a></li>
				<li><s:text name="bolsos.shoppingcart.detail.label.order"/><s:property value="reference" /></li>
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

		<div class="col-md-9" id="customer-order">
			<div class="box">
				<h1>
					<s:text name="bolsos.shoppingcart.detail.label.title"/>
					<s:property value="reference" />
				</h1>

				<p class="lead">
					<s:text name="bolsos.shoppingcart.detail.label.title"/>
					<s:property value="reference" />
					<s:text name="bolsos.shoppingcart.detail.label.place"/> 
					<strong><s:property value="creationDate" /></strong>
					<s:text name="bolsos.shoppingcart.detail.label.currently"/> 
					<strong><s:text name="%{getText(statusLocalization)}"/></strong>.
				</p>

				<hr>

				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th colspan="2"><s:text
										name="bolsos.shopping.cart.table.column.product" /></th>
								<th><s:text
										name="bolsos.shopping.cart.table.column.quantity" /></th>
								<th><s:text name="bolsos.shopping.cart.table.column.price" /></th>
								<th><s:text name="bolsos.shopping.cart.table.column.discount" /></th>
								<th><s:text name="bolsos.shopping.cart.table.column.total" /></th>
							</tr>
						</thead>
						<s:if
							test="%{shoppingCartItems != null && shoppingCartItems.size > 0}">
							<tbody>
								<s:iterator value="shoppingCartItems" status="status">
									<tr id="tr_<s:property value="product.id"/>">
										<td>
											<h4>
												<a href="#"><s:property value="productName" /></a>
											</h4> 
										</td>
										<td>
											<a href="#"> <img
													src="<s:property value="urlImage" />"
													alt="<s:property value="productName" />">
											</a>
										</td>
										<td><s:property value="quantity" /></td>
										<td><s:property value="price" /></td>
										<td><s:property value="discount" /></td>
										<td><s:property value="price" /></td>
									</tr>
								</s:iterator>
							</tbody>
						</s:if>
						<tfoot>
							<tr>
								<th colspan="5" class="text-right"><s:text name="bolsos.shoppingcart.detail.label.subtotal"/></th>
								<th><s:property value="subTotal" /></th>
							</tr>
							<tr>
								<th colspan="5" class="text-right"><s:text name="bolsos.shoppingcart.detail.label.embroidery"/></th>
								<th><s:property value="extras" /></th>
							</tr>
							<tr>
								<th colspan="5" class="text-right"><s:text name="bolsos.shoppingcart.detail.label.shipping"/></th>
								<th><s:property value="shippingFees" /></th>
							</tr>
							<tr>
								<th colspan="5" class="text-right"><s:text name="bolsos.shoppingcart.detail.label.tax"/></th>
								<th><s:property value="taxes" /></th>
							</tr>
							<tr>
								<th colspan="5" class="text-right"><s:text name="bolsos.shoppingcart.detail.label.total"/></th>
								<th><s:property value="total" /></th>
							</tr>
						</tfoot>
					</table>

				</div>
				<!-- /.table-responsive -->

				<div class="row addresses">
					<!-- <div class="col-md-6">
						<h2>Invoice address</h2>
						<p>
							John Brown <br>13/25 New Avenue <br>New Heaven <br>45Y
							73J <br>England <br>Great Britain
						</p>
					</div> -->
					<div class="col-md-6">
						<h2><s:text name="bolsos.shoppingcart.address.panel.title.shipping"/></h2>
						<p>
							<s:property value="shippingAddress.firstName" /> <s:property value="shippingAddress.lastName" /> 
							<br><s:property value="shippingAddress.street" /> <s:property value="shippingAddress.apartment" /> 
							<br><s:property value="shippingAddress.postCode" /> 
							<br><s:property value="shippingAddress.province" />  <br><s:property value="shippingAddress.country" /> 
						</p>
					</div>
				</div>

			</div>
		</div>

	</div>
	<!-- /.container -->
</div>
<!-- /#content -->

