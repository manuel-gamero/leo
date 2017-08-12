<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.shoppingcart.summary"/></li>
			</ul>
		</div>

		<div class="col-md-9" id="checkout">

			<div class="box">
				<s:form id="PaymentRequest" action="%{getAction()}"
						namespace="/" method="post" validate="true"
						enctype="multipart/form-data" class="form-horizontal"
						data-toggle="validator">
					<s:token></s:token>
					<h1><s:text name="bolsos.shoppingcart.summary"/></h1>
					<s:include value="../template/shoppingCartProcessSectionTemplate.jsp">
						<s:param name="active" value="'review'" />
					</s:include>

					<div class="content">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th colspan="2"><s:text name="bolsos.shopping.cart.table.column.product" /></th>
										<th><s:text name="bolsos.shopping.cart.table.column.quantity" /></th>
										<th><s:text name="bolsos.shopping.cart.table.column.price" /></th>
										<th><s:text name="bolsos.shopping.cart.table.column.discount" /></th>
										<th><s:text name="bolsos.shopping.cart.table.column.total" /></th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="shoppingCartList" status="status">
										<tr id="tr_<s:property value="product.id"/>">
											<td><a href="#"><s:property value="product.name" /></a></td>
											<td>
												<s:if test="%{product.customProduct}">
													<a href="${tmppath}<s:property value="nameImage" />" class="modalImage" title='<s:property value="product.name" />' data-link='<s:text name="url.product"/>/<s:property value="product.url"/>'> 
														<img src="${tmppath}<s:property value="nameImage" />" alt="<s:property value="product.name" />">
													</a>
												</s:if>
												<s:else>
													<a href="${productpath}<s:property value="product.id" />/<s:property value="nameImage" />" class="modalImage" title='<s:property value="product.name" />' data-link='<s:text name="url.product"/>/<s:property value="product.url"/>'> 
														<img src="${productpath}<s:property value="nameImage" />" alt="<s:property value="product.name" />">
													</a>
												</s:else>
											</td>
											<td>1</td>
											<td><s:property	value="product.msrp" /></td>
											<td><s:property	value="discount" /></td>
											<td><s:property	value="total" /></td>
										</tr>
									</s:iterator>
								</tbody>
								<tfoot>
									<tr>
										<th colspan="5"><s:text name="bolsos.shopping.cart.label.total"/></th>
										<th><s:property value="subTotalShoppingCart"/></th>
									</tr>
								</tfoot>
							</table>

						</div>
						<!-- /.table-responsive -->
						<div class="row text-right terms-conditions">
							<s:if test="hasActionErrors()">
								<div class="error-terms">
									<s:actionerror />
								</div>
							</s:if>
							<s:checkbox name="termsConditions" />
							<a href='<s:text name="url.termConditions"/>'>
								<s:text name="bolsos.shoppingcart.detail.check.terms"/>
							</a>
						</div>
						

					</div>
					<!-- /.content -->
					
					<!-- Start modal to display the image -->
						<s:include value="../template/popupImageTemplate.jsp"/>
					<!-- End modal to display the image -->

					<div class="box-footer">
						<div class="pull-left">
							<a href='/<s:text name="url.shoppingcart.shipping"/>' class="btn btn-default">
								<i class="fa fa-chevron-left"></i>
								<s:text name="bolsos.shoppingcart.summary.label.back" />
							</a>
						</div>
						<s:property value="payment" />
						<div class="pull-right">
							<s:if test="%{paymentType != 'paypal'}">
								<button type="submit" class="btn btn-primary submit">
									<s:text name="bolsos.shoppingcart.summary.label.continue"/> 
									<i class="fa fa-chevron-right"></i>
								</button>
							</s:if>
							<s:if test="%{paymentType == 'paypal'}">
								<input id="ppCheckout" class="highlightable hotspot submit" type="image" value="Check out with PayPal" name="pp_checkout" src="https://www.paypalobjects.com/en_US/i/btn/btn_xpressCheckout_SM.gif">
							</s:if>
						</div>
					</div>
				</s:form>
				
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col-md-9 -->

		<div class="col-md-3">
			<s:include value="../template/shoppingCartSummarySectionTemplate.jsp"></s:include>
		</div>
		<!-- /.col-md-3 -->

	</div>
	<!-- /.container -->
</div>
<!-- /#content -->

<script type="text/javascript">
$(document).ready(function() {
	$('button[type="submit"]').attr('disabled',true);
	$("input[name='termsConditions']").click(function(e) {
		$('button[type="submit"]').attr('disabled',!this.checked);
	});
});

	
</script>