<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set var="product" value="%{productDTO}" scope="action" />

<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li>
					<a href='<s:text name="url.home"/>'><s:text	name='bolsos.common.title.home' /></a>
				</li>
				<li>
					<s:if test='%{#product.collection.statusCode.toString().equals("ACTIVE")}'>
						<a href='<s:text name="url.ourproduct.collections"/>/<s:property value="%{#product.collection.url}" />'><s:property value="%{#product.collection.name}" /></a>
					</s:if>
					<s:else>
						<s:property value="%{#product.collection.name}" />
					</s:else>
					
				</li>
				<li>
					<s:property value="%{#product.name}" />
				</li>
			</ul>

		</div>

		<%-- <div class="col-md-3">
			<!-- *** MENUS AND FILTERS *** -->
			<div class="panel panel-default sidebar-menu">

				<div class="panel-heading">
					<h3 class="panel-title">Categories</h3>
				</div>

				<div class="panel-body">
					<ul class="nav nav-pills nav-stacked category-menu">
						<li><a href="category.html">Men <span
								class="badge pull-right">42</span></a>
							<ul>
								<li><a href="category.html">T-shirts</a></li>
								<li><a href="category.html">Shirts</a></li>
								<li><a href="category.html">Pants</a></li>
								<li><a href="category.html">Accessories</a></li>
							</ul></li>
						<li class="active"><a href="category.html">Ladies <span
								class="badge pull-right">123</span></a>
							<ul>
								<li><a href="category.html">T-shirts</a></li>
								<li><a href="category.html">Skirts</a></li>
								<li><a href="category.html">Pants</a></li>
								<li><a href="category.html">Accessories</a></li>
							</ul></li>
						<li><a href="category.html">Kids <span
								class="badge pull-right">11</span></a>
							<ul>
								<li><a href="category.html">T-shirts</a></li>
								<li><a href="category.html">Skirts</a></li>
								<li><a href="category.html">Pants</a></li>
								<li><a href="category.html">Accessories</a></li>
							</ul></li>

					</ul>

				</div>
			</div>

			<div class="panel panel-default sidebar-menu">

				<div class="panel-heading">
					<h3 class="panel-title">
						Brands <a class="btn btn-xs btn-danger pull-right" href="#"><i
							class="fa fa-times-circle"></i> Clear</a>
					</h3>
				</div>

				<div class="panel-body">

					<form>
						<div class="form-group">
							<div class="checkbox">
								<label> <input type="checkbox">Armani (10)
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox">Versace (12)
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox">Carlo Bruni (15)
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox">Jack Honey (14)
								</label>
							</div>
						</div>

						<button class="btn btn-default btn-sm btn-primary">
							<i class="fa fa-pencil"></i> Apply
						</button>

					</form>

				</div>
			</div>

			<div class="panel panel-default sidebar-menu">

				<div class="panel-heading">
					<h3 class="panel-title">
						Colours <a class="btn btn-xs btn-danger pull-right" href="#"><i
							class="fa fa-times-circle"></i> Clear</a>
					</h3>
				</div>

				<div class="panel-body">

					<form>
						<div class="form-group">
							<div class="checkbox">
								<label> <input type="checkbox"> <span
									class="colour white"></span> White (14)
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"> <span
									class="colour blue"></span> Blue (10)
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"> <span
									class="colour green"></span> Green (20)
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"> <span
									class="colour yellow"></span> Yellow (13)
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"> <span
									class="colour red"></span> Red (10)
								</label>
							</div>
						</div>

						<button class="btn btn-default btn-sm btn-primary">
							<i class="fa fa-pencil"></i> Apply
						</button>

					</form>

				</div>
			</div>

			<!-- *** MENUS AND FILTERS END *** -->

			<div class="banner">
				<a href="#"> <img src="img/banner.jpg" alt="sales 2014"
					class="img-responsive">
				</a>
			</div>
		</div> --%>
		<div class="col-md-1">
		</div>
		<div class="col-md-10">

			<s:form id="form" action="addShoppingCart" namespace="/"
				method="post" validate="true" enctype="multipart/form-data"
				class="form-horizontal">
				<s:hidden name="id" />
				<s:hidden id="color" name="item.color"/>
				<s:token></s:token>

				<div class="row" id="productMain">
					<div class="col-sm-7">
						<s:if test="%{#product.customProduct}">
							<div id="mainImage" class="product product-size product-size-<s:property value="%{#product.typeCode}" />">
								<div class="product-img">
									<s:set var="ccids" value='%{"0"}' />
									<img id="mainImageProduct" class="mask maskc img-responsive padding"
										src="${productpath}<s:property value="%{#product.id}" />/large/<s:property value="%{#product.image.name}" />" />
									<s:iterator
										value="%{#product.collection.customComponentCollections}"
										status="status" var="ccc">
										<!--<s:if test="%{!#ccids.contains(#ccc.customComponent.id)}">
												<img id="mask_<s:property value="#ccc.customComponent.id"/>" class="mask_<s:property value="#status.index"/>"  />
												<s:set var="ccids" value="%{#ccids + ' ' + #ccc.customComponent.id}"/>
											</s:if>-->
										<s:if
											test='%{!#ccids.contains(#ccc.customComponent.id.toString())}'>
											<s:set var="ccids"
												value='%{#ccids + " " + #ccc.customComponent.id}' />
											<img id="mask_<s:property value="#ccc.customComponent.id"/>"
												class="mask maska img-responsive padding" />
											<s:hidden id='hidden_%{#ccc.customComponent.id}'
												name='customComponentCollection[%{#status.index}]' />
										</s:if>
									</s:iterator>
									
								</div>
								<div id="customerText"
									style="position: absolute; z-index:100; height: <s:property value='%{#product.customComponentText.height}'/>px; width: <s:property value='%{#product.customComponentText.width}'/>px; left: <s:property value='%{#product.customComponentText.posLeft}'/>px; top: <s:property value='%{#product.customComponentText.posTop}'/>px;">
									<span id="displayTextSpan"
										style="display: block; text-align: <s:property value='%{#product.customComponentText.align}'/>; font-family: Arial Rounded MT; font-size: 20px;"></span>
								</div>
								<s:if test="%{#product.customProduct}">
									<div id="details">
										<div class="social social-mask">
											<h5><s:text name="bolsos.product.text.share"/></h5>
											<p class="pull-left">
												<a id="shareFb"  href='<s:text name="url.web"/><s:text name="url.product"/>/<s:property value="productDTO.url"/>' class="facebook external" data-animate-hover="pulse">
													<i class="fa fa-facebook"></i>
												</a>
											</p>
										</div>
									</div>
								</s:if>
							</div>
						</s:if>
						<s:else>
							<div class="product-finish-image">
								<img class="img-responsive zoom-image"
									src="${productpath}<s:property value="%{#product.id}" />/large/<s:property value="%{#product.image.name}" />" 
									data-zoom-image="${productpath}<s:property value="%{#product.id}" />/large/<s:property value="%{#product.image.name}" />" />
							</div>
						</s:else>
						<s:if test="%{#product.productImagesSetDTO != null && #product.productImagesSetDTO.size > 0}">
							<div class="row thumbs-container" id="thumbs"> 
								<s:iterator value="%{#product.productImagesSetDTO}" status="status" var="image">
									<div class="col-xs-4">
		                               	<a href="${productpath}<s:property value="%{#product.id}" />/<s:property value="%{#image.nameImage}" />" class="thumb thumb-image-product modalImage">
		                                	<img class="img-responsive" src="${productpath}<s:property value="%{#product.id}" />/thumbnail/<s:property value="%{#image.nameImage}" />" alt="<s:property value="%{#image.nameImage}" />" />
		                            	</a>
									</div>
								</s:iterator>
							</div>
							<!-- Start modal to display the image -->
							<s:include value="../template/popupImageTemplate.jsp">
								<s:param name="title"><s:property value="%{#product.name}" /></s:param>
								<s:if test='%{#product.collection.statusCode.toString().equals("ACTIVE")}'>
									<s:param name="subtitle"><s:property value="%{#product.collection.name}" /></s:param>
								</s:if>
							</s:include>
							  <!-- End modal to display the image -->
						</s:if>
					</div>
					<div class="col-sm-5">
						<div class="box">
							<h1 class="text-center"><s:property value="%{#product.name}" /></h1>
							<p class="goToDescription">
								<a class="scroll-to" href="#details-product">
									<s:text name="bolsos.product.text.scroll"/>
								</a>
							</p>
							<p class="product-price"><s:property value="%{#product.msrp}" /></p>
									
							<!-- Start CustomComponentCollection -->
							<s:if test="%{#product.customProduct}">
								<s:iterator value="%{#product.customComponentDTOSet}"
									status="status" var="cc">
									<div class="row">
										<div class="section-custom">
											<label class="section-title"> 
												<s:text name='%{#status.index + 1}' />. <s:text name='bolsos.code.customcomponent.%{#cc.name}' />
											</label>
											<span id="span_<s:property value="%{#cc.id}" />" class="section-choose"> </span>
										</div>
										
										<div class="section-custom-tissue">
											<s:iterator value="%{#cc.customComponentCollections}"
												status="status" var="ccc">
												<s:if test='%{#ccc.typeCode.toString().equals("PATH")}'>
	
													<a href="#"> <img
														id='imag_<s:property value="%{#product.id}" />_<s:property value="%{#ccc.customComponent.id}" />_<s:property value="%{#ccc.customComponentImageId}" />_<s:property value="%{#cc.id}" />'
														class="product-icon"
														alt="<s:property value="%{#ccc.name}" />"
														src="${collectionpath}thumbnail/<s:property value="%{#ccc.image.name}" />" />
														<%-- <img class="preview"
														alt="<s:property value="%{#ccc.name}" />"
														src="${collectionpath}thumbnail/<s:property value="%{#ccc.image.name}" />" /> --%>
													</a>
	
												</s:if>
												<s:if test='%{#ccc.typeCode.toString().equals("COLOR")}'>
													<div class="col-md-1 collection_ico"
														style="background:#<s:property value="%{#ccc.value}" />"></div>
												</s:if>
											</s:iterator>
										</div>
									</div>
								</s:iterator>
							</s:if>
							<s:if test="%{#product.customText}">
								<div class="row">
									<div class="section-custom">
										<label class="section-title"> 
											<s:if test="%{#product.customProduct}">
												<s:text name='%{#product.customComponentDTOSet.size + 1}' />. <s:text name='bolsos.code.customcomponent.TEXT' />
											</s:if>
											<s:else>
												1. <s:text name='bolsos.code.customcomponent.TEXT' />
											</s:else>
										</label>
									</div>
									<p class="text-left section-text-paragraph ">
										<s:text name="bolsos.common.title.product.display.text" >
										   <s:param ><s:property value="extraPrice"/></s:param>
										</s:text>
									</p>
									<div>
										<s:textfield id="displayText" class="form-control"
											placeholder="%{getText('bolsos.common.title.product.display')}"
											type="text" cssStyle="width:225px;"
											maxlength="%{#product.customComponentText.maximum}"
											name="item.text" />
									</div>
									<div class="section-text-item">
										<select id="font" class="form-control"
											style="width: 155px;" name="item.font"
											placeholder="%{getText('bolsos.common.title.product.font')}">
											<option value="Arial Rounded MT">Arial Rounded</option>
											<option value="lucida_calligraphyitalic">lucida</option>
										</select>
									</div>
									<div class="section-text-item"> 
										<select id="size" class="form-control"
											style="width: 70px;" name="item.size"
											placeholder="%{getText('bolsos.common.title.product.size')}">
											<option value="20">20</option>
										</select>
									</div>
									<div class="section-text-item">
										<div class="product-text-color col-md-2"
											style="background: #000000;"></div>
										<div class="product-text-color col-md-2"
											style="background: #808080;"></div>
										<div class="product-text-color col-md-2"
											style="background: #FFFFFF;"></div>
										<div class="product-text-color col-md-2"
											style="background: #000080;"></div>
										<div class="product-text-color col-md-2"
											style="background: #ADD8E6;"></div>
										<div class="product-text-color col-md-2"
											style="background: #FFC0CB;"></div>
									</div>
									
								</div>
							</s:if>
							<!-- End CustomComponentCollection -->
							<p class="text-center buttons section-button-add">
								<s:if test="%{#product.customProduct}">
									<button id="submitButton" type="submit" class="btn btn-primary">
										<i class="fa fa-shopping-cart"></i><s:text name="bolsos.product.button.add"/>
									</button>
								</s:if>
								<s:else>
									<p class="buttons">
										<button type="submit" class="btn btn-primary">
											<i class="fa fa-shopping-cart"></i><s:text name="bolsos.product.button.add"/>
										</button>
										<s:if test="%{#product.urlCustomProduct != null && #product.urlCustomProduct != ''}">
											<a href='<s:text name="url.product"/>/<s:property value="%{#product.urlCustomProduct}" />' class="btn btn-default">
												<i class="fa fa-machine custom-ico"></i>
													<s:text name="bolsos.ourproduct.button.add"/>
											</a> 
										</s:if>
									</p>
								</s:else>
							</p>
						</div>
					</div>

				</div>

			</s:form>
			<div class="box section-detail" id="details-product">
				<p>
				<h4><s:text name="bolsos.product.text.detail"/> </h4>
				<p>
					<s:property value="%{#product.description}" />
				</p>
				
				<s:if test="%{#product.customProduct}">
					<h4><s:text name="bolsos.product.text.materialcare"/> </h4>
					<ul id="contentMaterial">
					</ul>
				</s:if>
				<s:else>
					<s:if test="%{#product.descriptionList.size > 0}">
						<h4><s:text name="bolsos.product.text.materialcare"/> </h4>
						<ul>
						<s:iterator value="%{#product.descriptionList}" status="status" var="description">
							<li>
								<s:property value="#description"/>
							</li>
						</s:iterator>
						</ul>
					</s:if>
				</s:else>
				<s:if test="%{#product.width != null and #product.weight != null}">
					<h4><s:text name="bolsos.product.text.size"/></h4>
					<ul>
						<s:if test="%{#product.width != null}">
							<li><s:property value="%{#product.width}" />x<s:property value="%{#product.height}" />x<s:property value="%{#product.depth}" /></li>
						</s:if>
						<s:if test="%{#product.weight != null}">
							<li><s:property value="%{#product.weight}" />gr</li>
						</s:if>
					</ul>
				</s:if>
				
			</div>
			
			<s:if test="%{itemList != null && itemList.size > 0}">
				<div class="row same-height-row">
					<div class="col-md-3 col-sm-6">
						<div class="box same-height">
							<h3>
								<s:if test="%{#product.customProduct}">
									<s:text name="bolsos.product.text.sugestion"/>
								</s:if>
								<s:else>
									<s:text name="bolsos.product.text.like"/>
								</s:else>
							</h3>
						</div>
					</div>
	
					<s:iterator	value="itemList" status="status" var="item" >
						<div class="col-md-3 col-sm-6">
							<s:if test="%{#product.customProduct}">
								<div class="product same-height">
									<div class="flip-container">
										<div class="flipper">
											<div class="front">
												<img src="<s:property value="nameImage" />" alt="<s:property value="itemName" />"
													class="img-responsive">
											</div>
											<div class="back">
												<img src="<s:property value="nameImage" />" alt="<s:property value="itemName" />" class="img-responsive">
											</div>
										</div>
									</div>
									<a href="#" class="invisible"> 
										<img src="<s:property value="nameImage" />" alt="<s:property value="itemName" />" class="img-responsive">
									</a>
									<div class="text">
										<h3><s:property value="itemName" /></h3>
										<p class="price">
											<s:if test="%{#item.hasDiscount}">
												<del><s:property value="oldPrice" /></del>
												<s:property value="price" />
											</s:if>
											<s:else>
												<s:property value="price" />
											</s:else>
										</p>
									</div>
								</div>
							</s:if>
							<s:else>
								<div class="product same-height">
									<div class="flip-container">
										<div class="flipper">
											<div class="front">
												<a href="<s:text name="url.product"/>/<s:property value="url"/>"> <img src="<s:property value="nameImage" />" alt="<s:property value="itemName" />"
													class="img-responsive">
												</a>
											</div>
											<div class="back">
												<a href="<s:text name="url.product"/>/<s:property value="url"/>"> 
													<img src="<s:property value="nameImage" />" alt="<s:property value="itemName" />" class="img-responsive">
												</a>
											</div>
										</div>
									</div>
									<a href="#" class="invisible"> 
										<img src="<s:property value="nameImage" />" alt="<s:property value="itemName" />" class="img-responsive">
									</a>
									<div class="text">
										<h3>
											<a href='<s:text name="url.product"/>/<s:property value="url"/>' >
												<s:property value="itemName" />
											</a>
										</h3>
										<p class="price">
											<s:if test="%{#item.hasDiscount}">
												<del><s:property value="oldPrice" /></del>
												<s:property value="price" />
											</s:if>
											<s:else>
												<s:property value="price" />
											</s:else>
										</p>
									</div>
								</div>
							</s:else>
							<s:if test="%{#item.hasDiscount}">
								<div class="ribbon sale">
									<div class="theribbon new-sale-item text-center"><s:text name="bolsos.common.text.sale"/></div>
									<div class="ribbon-background"></div>
								</div>
							</s:if>
							<s:if test="%{#item.newProduct}">
								<div class="ribbon new">
									<div class="theribbon new-sale-item text-center"><s:text name="bolsos.common.text.new"/></div>
									<div class="ribbon-background"></div>
								</div>
							</s:if>
							<!-- /.product -->
						</div>
					</s:iterator>
	
				</div>
			</s:if>

		</div>
		<!-- /.col-md-9 -->
	</div>
	<!-- /.container -->
</div>
<!-- /#content -->

<script type="text/javascript">

function centerModal() {
	$('#imagemodal').css('display', 'block');
    var $dialog = $('#imagemodal').find(".modal-dialog");
    var offset = ($(window).height() - $dialog.height()) / 2;
    // Center modal vertically in window
    $dialog.css("margin-top", offset);
}

function rgb2hex(rgb) {
    if (/^#[0-9A-F]{6}$/i.test(rgb)) return rgb;

    rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    function hex(x) {
        return ("0" + parseInt(x).toString(16)).slice(-2);
    }
    return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
}

function locateImage() {
	var leftPosition = <s:property value='%{#product.customComponentText.posLeft}'/>;
	var witdhImage = $("#mainImageProduct").width();
	var imageWitdh = <s:property value='%{#product.customComponentText.imageWidth}'/>;
	var imageRealWitdh = <s:property value='%{#product.image.width}'/>;
	var padding = ($("#mainImage").width() - imageRealWitdh)/2;
	var newLeftPosition = 0;
	if(padding > 0){
		newLeftPosition = ((leftPosition * witdhImage) / imageRealWitdh) + padding;
	}
	else{
		//newLeftPosition = ((leftPosition * witdhImage) / imageRealWitdh) ;
		newLeftPosition = ((leftPosition * $("#mainImage").width()) / imageWitdh) ;
	}
	var topPosition = <s:property value='%{#product.customComponentText.posTop}'/>;
	var heightImage = $("#mainImageProduct").height();
	var imageHeight = <s:property value='%{#product.customComponentText.imageHeight}'/>;
	//var imageHeight = <s:property value='%{#product.image.height}'/>;
	var newTopPosition = ((topPosition * heightImage) / imageHeight);
	//var newTopPosition = ((topPosition * $("#mainImage").height()) / imageHeight);
	
	var widthText = <s:property value='%{#product.customComponentText.width}'/>;
	var newWidthText = ((widthText * witdhImage) / imageWitdh);
	//var newWidthText = ((widthText * $("#mainImage").width()) / imageWitdh);

	$("#customerText").css( "left", newLeftPosition );
	$("#customerText").css( "top", newTopPosition );
	$("#customerText").css( "width", newWidthText );
	if(padding > 0){
		$(".padding").css( "padding-left", padding);
	}
	else{
		$(".padding").css( "padding-left", 0);
	}
    
}

function loadCustomImage() {
	var cciList = <s:property value='customComponentForProduct'/>;
	if(cciList.length  > 0){
		for ( var i = 0; i < cciList.length; i++) {
	          var cciItem = cciList[i];
	          console.log('cciItem: ' + cciItem );
	          $("#imag_" + cciItem).click();
	      }
	}
}

$(document).ready(function() {
	console.log('Init events');
	
	$( window ).resize(function() {
		locateImage();
	});
	
	locateImage();
	
	$("#submitButton").prop('disabled', true);
	$("#shareFb").css("background-color", 'gainsboro');

	//$("#displayText").val("");

	$('#tabProperties a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});

	$('.product-text-color').click(function(e) {
		var color = $(this).css('background-color');
		$("#displayTextSpan").css("color", color);
		$("#color").val(rgb2hex(color));
	});

	$('#customTextCheck').click(function() {
		$(".custom-group-text").toggle(this.checked);
	});

	$("#font").change(function() {
		var font = $("#font").val();
		$("#displayTextSpan").css("font-family", font);
	});

	$("#size").change(function() {
		var size = $("#size").val() + "px";
		$("#displayTextSpan").css("font-size", size);
	});

	$("#displayText").change(function() {
		var textDisplayText = $("#displayText").val();
		$("#displayTextSpan").text(textDisplayText);
		locateImage();
	});

	$('.modalImage').on('click', function(e) {
		e.preventDefault();
		$('.imagepreview').attr('src', $(this).attr('href'));
		$('#imagemodal').modal('show');
		centerModal();
	});
	
	var listComponents = $("img[id*='imag_']");

	$(listComponents).each(	function() {
		$(this).click(function(e) {
			e.preventDefault();
			var component = $(this);
			console.log('component: '+ component.attr("id").split("_")[3]);
			var productId = component.attr("id").split(	"_")[1];
			var componentImageId = component.attr("id").split("_")[3];
			$.getJSON("/ajax/componentImage",	{productId : productId,	componentImageId : componentImageId},function(a) {
				var customComponentId = component.attr("id").split("_")[2];
				var mask = $("img[id*='mask_"+ customComponentId+ "']");
				var customTissue = $(".section-custom-tissue").length;
				mask.removeAttr('src');
				mask.attr('src',a.path);
				var hidden = $("input[id*='hidden_"	+ customComponentId	+ "']");
				hidden.val(component.attr("id"));
				var componentId = component.attr("id").split("_")[4];
				var name = component.attr("alt");
				$("#span_" + componentId).text(name);
				$("img[id$='_" + componentId + "']").removeClass( "product-ico-chosse" );
				component.addClass( "product-ico-chosse" );
				var liItem = $("li#" + componentId);
				console.log('liItem: '+ liItem.length );
				if( liItem.length  == 0 ){
					$("ul#contentMaterial").append("<li id='" + componentId + "'>" + a.name +": " + a.desc  +"</li>");
				}
				else{
					liItem.text(a.name +": " + a.desc );
				}	
				if( $(".product-ico-chosse").length  == customTissue ){
					$("#submitButton").prop('disabled', false);
					$("#shareFb").css("background-color", '#4460ae');
				}
			});
		});
	});
	
	$("img[id*='imag_']").popover({
		title: $(this).attr("alt") ,
		html: true,
		delay: { "show": 100, "hide": 100 },
		trigger: 'hover',
		placement: 'auto',
		content: function () {
			return '<img class="popover-imag img-responsive center-block" src="'+ $(this).attr("src").replace('thumbnail/', '') + '" /> <div class="text-center">' + $(this).attr("alt") +'</div>';
		}
	});
	
	$("#shareFb").click(function(e){
		e.preventDefault();
		if( rgb2hex($("#shareFb").css("background-color")) == '#4460ae' ){
			var id = $("[name='id']").val();
			//var ypos = $('#displayTextSpan').offset().top;
			//var xpos = $('#displayTextSpan').offset().left;
			var xpos = $('#customerText').position().left;
			var ypos = $('#customerText').position().top;
			var customComponentCollection = $('[id^=hidden_]').map(function() { return $( this ).val(); }).get().join();
			var size = $("#size").val();
			var font = $("#font").val();
			var color = $("#color").val();
			var text = $("#displayTextSpan").text();
			var link = $(this).attr("href");
			console.log(id + " " + xpos + " " + ypos + " " + customComponentCollection);
			$.getJSON("/ajax/shareImage.action", { id: id, xpos: xpos, ypos: ypos, 
												   customComponentCollection: customComponentCollection,
												   size: size, font: font, color: color, text: text }, function(a) {
				console.log("Sharing : ");
				$.ajaxSetup({ cache: true });
				$.getScript('//connect.facebook.net/en_US/sdk.js', function(){
					FB.init({
						appId	: '878026268975881',
						xfbml	: true,
						version : 'v2.6'
					});     
					// $('#loginbutton,#feedbutton').removeAttr('disabled');
					// FB.getLoginStatus(updateStatusCallback);
					e.preventDefault();
					console.log("link: " + link);
					FB.ui({
						method: 'share',
						name: a.name,
						href: link,
						picture: a.picture,
						caption: a.caption,
						description: a.description,
						message: ''
					});
				});
			});
		}
	});
	
	loadCustomImage();
	
	if( $("#displayText").val() != "" ){
		$("#displayText").change(); 
	}
});

	
</script>