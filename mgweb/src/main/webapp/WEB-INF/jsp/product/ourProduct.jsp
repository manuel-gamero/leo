<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="%{action}" namespace="/" method="post" validate="true" enctype="multipart/form-data"
	class="form-horizontal">
	<s:hidden name="type" />
	<s:hidden name="collectionId" />
	<s:hidden name="tag" />
	<s:hidden name="title" />
	<s:hidden name="description" />

	<div id="content">
		<div class="container">

			<div class="col-md-12">

				<ul class="breadcrumb">
					<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
					<li><s:text name="bolsos.ourproduct.title"/></li>
				</ul>

				<div class="box collection-${collectionId}">
					<h1><s:property value="title"/></h1>
					<p>${description}</p>
				</div>

				<div class="box info-bar">
					<div class="row">
						<div class="col-sm-12 col-md-4 products-showing">
							<s:text name="bolsos.ourproduct.label.showing"/> 
							<strong>${list.size()}</strong> 
							<s:text name="bolsos.ourproduct.label.products"/>
						</div>

						<div class="col-sm-12 col-md-8  products-number-sort">
							<div class="row">
								<div class="form-inline">
									<div class="col-md-6 col-sm-6">	</div>
									<div class="col-md-6 col-sm-6">
										<div class="products-sort-by">
											<strong><s:text name="bolsos.ourproduct.label.sort"/></strong> 
											<s:select name="sortBy" class="form-control" list="#{'PRICE':getText('bolsos.ourproduct.label.sort.price'), 'NAME':getText('bolsos.ourproduct.label.sort.name')}"/>
												<!-- <option value="PRICE">Price</option>
												<option value="NAME">Name</option> -->
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row products">

					<s:iterator value="list" status="status" var="product">

						<div class="thumbnail col-xxs-1 col-xs-6 col-md-3 col-sm-4">
							<div class="product">
								<s:if test="%{#product.customProduct}">
									<div class="flip-container">
										<div class="flipper">
											<div class="front">
												<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="img-responsive"> <img
													src="${productpath}<s:property value="id" />/thumbnail/<s:property value="image.name" />"
													alt="<s:property value="product.image.name" />" />
												</a>
											</div>
											<div class="back">
												<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="img-responsive"> <img
													src="${productpath}<s:property value="id" />/thumbnail/<s:property value="image.name" />"
													alt="<s:property value="product.image.name" />" />
												</a>
											</div>
										</div>
									</div>
									<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="invisible"> <img
										src="${productpath}<s:property value="id" />/thumbnail/<s:property value="image.name" />"
										alt="<s:property value="product.image.name" />" >
									</a>
								</s:if>
								<s:else>
									<div class="flip-container">
										<div class="flipper">
											<div class="front">
												<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="img-responsive center-block product-image"> <img
													src="${productpath}<s:property value="id" />/<s:property value="image.name" />"
													alt="<s:property value="product.image.name" />" />
												</a>
											</div>
											<div class="back">
												<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="img-responsive center-block product-image"> <img
													src="${productpath}<s:property value="id" />/<s:property value="image.name" />"
													alt="<s:property value="product.image.name" />" />
												</a>
											</div>
										</div>
									</div>
									<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="invisible"> <img
										src="${productpath}<s:property value="id" />/<s:property value="image.name" />"
										alt="<s:property value="product.image.name" />" >
									</a>
								</s:else>
								<div class="text product-text-<s:property value="#product.customProduct" /> product-text-<s:property value="#product.customProduct" />-<s:property value="#product.typeCode" />">
									<s:if test="%{#product.customProduct}">
										<div class="text-center tissue-section">
										
											<s:iterator value="%{#product.customComponentDTOSet[0].customComponentCollections}"
												status="status" var="ccc">
												<img
													id='imag_<s:property value="%{#product.id}" />_<s:property value="%{#ccc.customComponent.id}" />_<s:property value="%{#ccc.customComponentImageId}" />_<s:property value="%{#cc.id}" />'
													class="product-icon"
													alt="<s:property value="%{#ccc.name}" />"
													src="${collectionpath}thumbnail/<s:property value="%{#ccc.image.name}" />" />
											</s:iterator>
										</div>
									</s:if>
									<s:if test="%{nameUrlType != null}">
										<h4 class="text-center">
											<s:if test='%{#product.collection.statusCode.toString().equals("ACTIVE")}'>
												<a href='<s:text name="url.ourproduct.collections"/>/<s:property value="collection.url" />'>
													<s:property value="collection.name" />
												</a>
											</s:if>
											<s:else>
												<s:property value="collection.name" />
											</s:else>
										</h4>
									</s:if>
									<h3 class="product-title">
										<a href='<s:text name="url.product"/>/<s:property value="url"/>'><s:property value="name" /></a>
									</h3>
									
									<p class="price">
										<s:if test="%{#product.hasDiscount}">
											<del><s:property value="oldPrice" /></del>
											<s:property value="msrp" />
										</s:if>
										<s:else>
											<s:property value="msrp" />
										</s:else>
									</p>
									<s:if test="%{#product.hasDiscount}">
										<div class="ribbon sale">
											<div class="theribbon new-sale-ribbon text-center"><s:text name="bolsos.common.text.sale"/></div>
											<div class="ribbon-background"></div>
										</div>
									</s:if>
									<s:if test="%{#product.newProduct}">
										<div class="ribbon new">
											<div class="theribbon new-sale-ribbon text-center"><s:text name="bolsos.common.text.new"/></div>
											<div class="ribbon-background"></div>
										</div>
									</s:if>
									<s:if test="%{#product.customProduct}">
										<p class="buttons">
											<!-- <a href="detail.html" class="btn btn-default">View detail</a> --> 
											<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="btn btn-primary">
												<i class="fa fa-machine custom-ico"></i>
													<s:text name="bolsos.ourproduct.button.add"/>
											</a>
										</p>
									</s:if>
									<s:else>
										<p class="buttons">
											<!-- <a href="detail.html" class="btn btn-default">View detail</a> --> 
											<a href='<s:text name="url.product"/>/<s:property value="url"/>' class="btn btn-default">
												<s:text name="bolsos.ourproduct.button.viewdetail"/>
											</a>
											<a href='/add_<s:property value="%{#product.id}" />' class="btn btn-primary ladda-button addItem"
													 data-style="expand-left" > 
												<i class="fa fa-shopping-cart"></i> 
												<s:text name="bolsos.product.button.add" />
											</a>
										</p>
									</s:else>
								</div>
								<!-- /.text -->
							</div>
							<!-- /.product -->
						</div>

					</s:iterator>

				</div>
				<!-- /.products -->

			</div>
			<!-- /.col-md-9 -->

		</div>
		<!-- /.container -->
	</div>
	<!-- /#content -->

</s:form>

<script type="text/javascript">
$(document).ready(function () {
	
	$("select[name=sortBy]").change(function() {
		$("#form").submit();
	});
	
	Ladda.bind( '.addItem' );
	
	$('.addItem').click(function(e) {
		e.preventDefault();
		var initText = $(this).text();
		var item = $(this);
		item.text("<s:text name='bolsos.shopping.cart.item.added'/>");
		var l = Ladda.create(this);
		l.start();
		var id = $(this).attr('href').split('_')[1];
		console.log('Add ajax call: ' + id);
		$.getJSON('/ajax/addAjax.action', {id : id}, function(e) {
			if (e.resutl == "success") {
				var itemsText = $(".shoppingCartItems").first().text();
				var itemsCount = itemsText.substring(itemsText.indexOf("(") + 1, itemsText.indexOf(")"));
				itemsCount++;
				var shoppingCartText = itemsText.substring(itemsText.indexOf(")") + 1, itemsText.length);
				$(".shoppingCartItems").text("(" + itemsCount + ")" + shoppingCartText);
			}
			setTimeout(function() {
				l.stop();
				item.html('<i class="fa fa-shopping-cart"></i>' + initText);
			}, 2000);
		});
	});
});	
</script>