<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="saveProductOrder" namespace="/admin"
	method="post" validate="true" enctype="multipart/form-data"
	class="form-horizontal">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Product Order</h3>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<div class="row">
					<label for="product" class="col-sm-2 control-label">Product</label>
					<s:textfield id="product" class="form-control" name="productOrder.product.id"
						placeholder="Product" type="text" cssStyle="width:156px;" />
				</div>
				<div class="row">
					<label for="order" class="col-sm-2 control-label">Order</label>
					<s:textfield id="order" class="form-control" placeholder="Order"
						name="productOrder.order" type="number" cssStyle="width:156px;" />
				</div>
				<div class="row">
					<s:submit value="Add" class="btn btn-default"></s:submit>
					<a id="publish" class="btn btn-default" role="button">Publish</a>
				</div>
				<div class="row">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Id</th>
								<th>Order</th>
								<th>Product</th>
								<th>Image</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody id="productOrderBody">
							<s:if
								test="%{productOrderList != null && productOrderList.size > 0}">
								<s:iterator value="productOrderList" status="status"
									var="productOrder">
									<tr id="po_tr_<s:property value="id"/>">
										<td style="text-align: center;"><s:property value="id" />
										</td>
										<td style="text-align: center;"><s:property value="order" />
										</td>
										<td style="text-align: center;">
											<s:text name="%{getTranslaction(product.translationByNameTransId.id,'en')}" />
											<br>
											<s:text name="%{getTranslaction(product.translationByNameTransId.id,'fr')}" />
										</td>
										<td style="text-align: center;">
											<img style="width: 120px; height: 120px;" src="${productpath}<s:property value="product.id" />/<s:property value="product.image.name" />" />
											<div style="margin-top:3px;">Current Size:</div>		        			
								        	<span id="img_dim_1"><s:property value="product.image.width" /> X 
								        	<s:property value="product.image.height" /> - 
								        	<s:property value="product.image.size" />kb</span>	
										</td>
										<td style="text-align: center;">
											<div class="form-group">
												<a id="deletePO_<s:property value="id"/>"
													class="btn btn-default" role="button">Delete</a> 
											</div>
										</td>
									</tr>
								</s:iterator>
								<s:else>
									<tr></tr>
								</s:else>
							</s:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<section class="bar background-white no-mb">
		<div class="row portfolio no-space container-portafolio" data-animate="fadeInUp"> 
			<div class="grid-sizer"></div>
				<s:iterator value="productOrderListDTO" status="status" var="item">
				<div class="grid-item">
					<s:include value="../template/presentationProductTemplate.jsp">
						<s:param name="imagename"> 
							product/<s:property value="id" />/<s:property value="image.name" />
						</s:param>
						<s:param name="imagealt">
							<s:property value="name" />
						</s:param>
						<s:param name="imageurl">
							<s:property value="url" />
						</s:param>
						<s:param name="id">
							<s:property value="id" />
						</s:param>
						<s:param name="poptitle">
							<s:property value="collection.name" />
						</s:param>
						<s:param name="popurl">
							<s:text name="url.ourproduct.collections"/>/<s:property value="collection.url" />
						</s:param>
					</s:include>
				</div>
				</s:iterator>
		</div>
	</section>
	
	<div id="dialog" title="Information">List published</div>
</s:form>

<script type="text/javascript">
	$(document).ready(function() {
		$('[id^=deletePO]').click(function() {
			var id = $(this).attr('id').split('_')[1];
			$.getJSON('ajax/deleteProductOrder.action', {id : id}, function(a) {
				$('#po_tr_' + id).remove();
			});
		});
		
		$('#publish').click(function() {
			var id = $(this).attr('id').split('_')[1];
			$.getJSON('ajax/publishProductOrderList.action', {id : id}, function(a) {
				$("#dialog").dialog();
			});
		});
		
		$("#product").autocomplete({
	          source : function(request, response) {
	              $.ajax({
	                  url : "/admin/ajax/search",
	                  type : "POST",
	                  data : {
	                      term : $("#product").val()
	                  },
	                  dataType : "json",
	                  success : function(serverJsonResponse) {
	                    response( $.map( serverJsonResponse.list, function( item ) {
	                    	return {
	                        	label: item.value,
	                            value: item.key
	                  	}
	                    }));
	                  }
	              });
	          }
	      });
			
	});
</script>