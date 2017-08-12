
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home" /></a></li>
				<li><s:text name="bolsos.shoppingcart.delivery" /></li>
			</ul>
		</div>

		<div class="col-md-9" id="checkout">

			<div class="box">
				<s:form id="form" action="%{getText('url.shoppingcart.summary')}" namespace="/" method="post" validate="true"
					enctype="multipart/form-data" class="form-horizontal" data-toggle="validator">
					<s:token></s:token>
					<s:hidden name="methodCode" />
					<h1>
						<s:text name="bolsos.shoppingcart.payment" />
					</h1>
					<s:include value="../template/shoppingCartProcessSectionTemplate.jsp">
						<s:param name="active" value="'payment'" />
					</s:include>

					<div class="content">
						<div class="row">
							<div class="col-sm-6">
								<div class="box payment-method">

									<h4><s:text name="bolsos.shoppingcart.methodtype.paypal.title"/></h4>

									<p><s:text name="bolsos.shoppingcart.methodtype.paypal.description"/></p>

									<div class="box-footer text-center">
										<input type="radio" name="paymentType" value="paypal" >
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="box payment-method">

									<h4><s:text name="bolsos.shoppingcart.methodtype.desjardins.title"/></h4>

									<p><s:text name="bolsos.shoppingcart.methodtype.desjardins.description"/></p>

									<div class="box-footer text-center">
										<input type="radio" name="paymentType" value="desjardins">
									</div>
								</div>
							</div>

						</div>
					</div>
					<!-- /.content -->

					<div class="box-footer">
						<div class="pull-left">
							<a href='/<s:text name="url.shoppingcart.shipping"/>' class="btn btn-default">
								<i class="fa fa-chevron-left"></i>
								<s:text name="bolsos.shoppingcart.summary.label.back" />
							</a>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-primary">
								<s:text name="bolsos.shoppingcart.delivery.label.continue" />
								<i class="fa fa-chevron-right"></i>
							</button>
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
$(document).ready(function () {
	  
});
</script>
