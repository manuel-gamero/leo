<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.shoppingcart.address"/></li>
			</ul>
		</div>

		<div class="col-md-9" id="checkout">

			<div class="box">
				<s:form id="form" action="%{getText('url.shoppingcart.shipping')}" namespace="/"
						method="post" validate="true" enctype="multipart/form-data"
						class="form-horizontal" data-toggle="validator">
					<s:token></s:token>
					<div id="ajax-modal" class="modal fade" tabindex="-1"style="display: none;"></div>
					<h1>Checkout</h1>
					<s:include value="../template/shoppingCartProcessSectionTemplate.jsp">
						<s:param name="active" value="'address'" />
					</s:include>

					<div class="content">
						<div class="row first-line-address">
							<div class="col-md-1">
								<label for="shippingSelect" class="control-label"> <s:text
										name="bolsos.shoppingcart.address.label.address" />
								</label>
							</div>
							<div class="col-md-3 has-feedback">
									<s:select id="shippingSelect" 
										name="shippingSelect"
										label="%{getText('bolsos.createaccount.label.country')}"
										cssStyle="width:156px;" required="true" listKey="id"
										listValue="%{location}"
										list="shippingList"
										class="col-md-1 form-control" />
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<label for="firstname"><s:text name="bolsos.shoppingcart.address.label.firstname"/></label> 
								<input type="text" class="form-control" id="firstname" disabled="disabled">
							</div>
							<div class="col-md-4">
								<label for="lastname"><s:text name="bolsos.shoppingcart.address.label.lastname"/></label> 
								<input type="text" class="form-control" id="lastname" disabled="disabled">
							</div>
						</div>
						<!-- /.row -->

						<div class="row">
							<div class="col-md-6">
								<label for="street"><s:text name="bolsos.shoppingcart.address.label.street"/></label> 
								<input type="text" class="form-control" id="street" disabled="disabled">
							
							</div>
							<div class="col-md-3 col-sm-6">
								<label for="apartment"><s:text name="bolsos.shoppingcart.address.label.apartment"></s:text>  </label> 
								<input type="text" class="form-control" id="apartment" disabled="disabled">
							</div>
						</div>
						<!-- /.row -->

						<div class="row">
							<div class="col-md-3 col-sm-6">
									<label for="zip"><s:text name="bolsos.shoppingcart.address.label.city"/></label> <input type="text"
										class="form-control" id="city" disabled="disabled">
							</div>
							<div class="col-md-3 col-sm-6">
									<label for="zip"><s:text name="bolsos.shoppingcart.address.label.zip"/></label> <input type="text"
										class="form-control" id="zip" disabled="disabled">
							</div>
							<div class="col-md-3 col-sm-6">
									<label for="state"><s:text name="bolsos.shoppingcart.address.label.state"/></label> <input type="text" class="form-control"
										id="state" disabled="disabled">
							</div>
							<div class="col-md-3 col-sm-6">
									<label for="country"><s:text name="bolsos.shoppingcart.address.label.country"/></label> <input type="text"
										class="form-control" id="country" disabled="disabled">
							</div>
						</div>
						
						<div class="row button-address ">
							<div class="col-md-3">
								<a id="aUserAddressShipping" class="btn btn-default popup" ><s:text name="bolsos.shoppingcart.address.panel.button.add.shipping"/></a>
							</div>
							<div class="col-md-3">
								<a id="eUserAddressShipping" class="btn btn-default popup" ><s:text name="bolsos.shoppingcart.address.panel.button.edit.shipping"/></a>
							</div>
						</div>
						<!-- /.row -->
					</div>

					<div class="box-footer">
						<div class="pull-left">
							<a href='<s:text name="url.shoppingcart"/>' class="btn btn-default"><i
								class="fa fa-chevron-left"></i><s:text name="bolsos.shoppingcart.address.label.back"/></a>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-primary">
								<s:text name="bolsos.shoppingcart.address.label.continue"/>
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
	
	$("#shippingSelect").change(function() {
		var shippingAddress = $("#shippingSelect option:selected").text();
		console.log('shipping selection: ' + shippingAddress);
		var section = shippingAddress.split('-');
		var sectionAddress = section[1].split(',');
		var name = section[0].split(',');
		$("#firstname").val(name[0]);
		$("#lastname").val(name[1]);
		$("#street").val(sectionAddress[0]);
		$("#apartment").val(sectionAddress[1]);
		$("#city").val(sectionAddress[2]);
		$("#zip").val(sectionAddress[3]);
		$("#state").val(sectionAddress[4]);
		$("#country").val(sectionAddress[5]);
	});
	
	$("#shippingSelect").change();
	
	$('[id^=eUserAddressShipping]').click(function() {
		var id = $('#shippingSelect').val();
		console.log('shippingSelect: ' + id);
		$.get('/aeUserAddress.action?id=' + id + '&type=SHIPPING', function(data) {
			$(data).modal().on('hide.bs.modal', function (e) {
				$("#popupModal").remove();
			});
		});
	}); 
	
	$('[id^=aUserAddressShipping]').click(function() {
		$.get('/aeUserAddress.action?type=SHIPPING', function(data) {
			$(data).modal().on('hide.bs.modal', function (e) {
				$("#popupModal").remove();
			});
		});
	}); 
	  
});
</script>
