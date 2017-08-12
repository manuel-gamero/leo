
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.shoppingcart.delivery"/></li>
			</ul>
		</div>

		<div class="col-md-9" id="checkout">

			<div class="box">
				<s:form id="form" action="%{getAction()}" namespace="/"
						method="post" validate="true" enctype="multipart/form-data"
						class="form-horizontal" data-toggle="validator">
						<s:token></s:token>
					<h1><s:text name="bolsos.shoppingcart.delivery"/></h1>
					<s:include value="../template/shoppingCartProcessSectionTemplate.jsp">
						<s:param name="active" value="'delivery'" />
					</s:include>
					
					<div class="content">
						<div class="row">
							<s:iterator value="shippingMethod" status="status" var="item">
								<div class="col-sm-6">
									<div class="box shipping-method">
										<h4><s:property value="name" /></h4>
										<p><s:property value="desc" /></p>
										<p class="text-center" ><s:property value="price" /></p>
									
										<div class="box-footer text-center">
											<s:if test='%{#item.code == methodCode }'>
												<input type="radio" checked="checked" name="methodCode" value="<s:property value="code" />" required>
											</s:if>
											<s:else>
												<input type="radio" name="methodCode" value="<s:property value="code" />" required>
											</s:else>
										</div>
									</div>
								</div>
							</s:iterator>						
						</div>
						<!-- /.row -->
					</div>
					<!-- /.content -->

					<div class="box-footer">
						<div class="pull-left">
							<a href='/<s:text name="url.shoppingcart.address"/>' class="btn btn-default"><i
								class="fa fa-chevron-left"></i><s:text name="bolsos.shoppingcart.delivery.label.back"/></a>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-primary">
								<s:text name="bolsos.shoppingcart.delivery.label.continue"/> 
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
