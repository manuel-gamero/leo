<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="dark-mask"></div>
<div id="content">

	<div class="container">
		<div id="instagram" class="center-block">
			<div class='row'>
				<s:iterator value="instagramFeed" status="status" var="item">
					<div class='col-md-4 col-xs-4 col-sm-4'>
						<a href='<s:property value="link" />'>
							<img src='<s:property value="image" />'/>
						</a>
					</div>
				</s:iterator>
			</div>
		</div>
	</div>

	<!-- *** ADVANTAGES HOMEPAGE ***
 _________________________________________________________ -->
	<div id="advantages">

		<div class="container">
			<div class="same-height-row">
				<div class="col-sm-4">
					<div class="box same-height">
						<div class="icon">
							<i class="fa fa-palette icon-behind"></i>
						</div>

						<h3 class="advertising-title">
							<s:text name="bolsos.home.advertising1.title" />
						</h3>
						<p>
							<s:text name="bolsos.home.advertising1.text" />
						</p>
					</div>
				</div>

				<div class="col-sm-4">
					<div class="box same-height">
						<div class="icon">
							<i class="fa fa-machine icon-behind"></i>
						</div>

						<h3 class="advertising-title">
							<s:text name="bolsos.home.advertising2.title" />
						</h3>
						<p>
							<s:text name="bolsos.home.advertising2.text" />
						</p>
					</div>
				</div>

				<div class="col-sm-4">
					<div class="box same-height">
						<div class="icon">
							<i class="fa fa-leather icon-behind"></i>
						</div>

						<h3 class="advertising-title">
							<s:text name="bolsos.home.advertising3.title" />
						</h3>
						<p>
							<s:text name="bolsos.home.advertising3.text" />
						</p>
					</div>
				</div>
			</div>
			<!-- /.row -->

		</div>
		<!-- /.container -->

	</div>
	<!-- /#advantages -->

	<!-- *** ADVANTAGES END *** -->
	
	<!-- *** START MEDIA VIDEO *** -->
	<div id='video_fragment' class="container audit" data-animate="fadeInUpBig">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<div class="box slideshow">
				<h3><s:text name="bolsos.home.video.banner.title"></s:text></h3>
				<p class="lead"><s:text name="bolsos.home.video.banner.text"/></p>
					
					<div class="embed-responsive embed-responsive-16by9">
						<iframe class="embed-responsive-item" src="https://www.youtube.com/embed/8HmbKIuWypw"></iframe>
						<!-- <iframe width="560" height="315" src="https://www.youtube.com/embed/8HmbKIuWypw" frameborder="0" allowfullscreen></iframe> -->
					</div>
				
			</div>
		</div>
	</div>
	
	<!-- *** END MEDIA VIDEO *** -->
	
	<!--  *** START PRODUCTS *** -->

	<div  id='product_fragment' class="box text-center audit" data-animate="fadeInUp">
		<div class="container">
			<div class="col-md-12">
				<h3 class="text-uppercase">
					<s:text name="bolsos.home.products.banner.title" />
				</h3>

				<p class="lead">
					<s:text name="bolsos.home.products.banner.text" />
				</p>
			</div>
		</div>
	</div>

	<section class="bar background-white no-mb">
		<div class="row portfolio no-space container-portafolio" data-animate="fadeInUp"> 
			<div class="grid-sizer"></div>
			<s:if test="%{productSaleList != null && productSaleList.size > 0}">
				<s:iterator value="productSaleList" status="status" var="item">
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
						<s:param name="paramnew">
							<s:property value="newProduct" />
						</s:param>
						<s:param name="paramsale">
							<s:property value="hasDiscount" />
						</s:param>
					</s:include>
				</div>
				</s:iterator>
			</s:if>
			<s:else>
				<div class="grid-item">
					<s:include value="../template/presentationProductTemplate.jsp">
						<s:param name="imagename" value="'Basket-Heritage-Large.jpg'" />
						<s:param name="imagealt" value="'Basket Heritage Large'" />
						<s:param name="imageurl" value="'185/heritage-large-basket-pink'" />
						<s:param name="id" value="'185'" />
						<s:param name="poptitle">
							<s:text name="%{getTranslaction(1214)}"/>
						</s:param>
						<s:param name="popurl" value="''">
						</s:param>
						<s:param name="paramnew">false</s:param>
						<s:param name="paramsale">false</s:param>
					</s:include>
				</div>
				<div class="grid-item">
					<s:include value="../template/presentationProductTemplate.jsp">
						<s:param name="imagename" value="'Travel-pouch-Heritage.jpg'" />
						<s:param name="imagealt" value="'Travel pouch Heritage'" />
						<s:param name="imageurl" value="'175/natural-cork-travel-pouch'" />
						<s:param name="id" value="'175'" />
						<s:param name="poptitle">
							<s:text name="%{getTranslaction(1112)}"/>
						</s:param>
						<s:param name="popurl" value="''">
						</s:param>
						<s:param name="paramnew">false</s:param>
						<s:param name="paramsale">false</s:param>
					</s:include>
				</div>
				<div class="grid-item">
					<s:include value="../template/presentationProductTemplate.jsp">
						<s:param name="imagename" value="'Basket-Heritage-Medium.jpg'" />
						<s:param name="imagealt" value="'Basket Heritage Medium'" />
						<s:param name="imageurl" value="'186/heritage-medium-basket-beige'" />
						<s:param name="id" value="'186'" />
						<s:param name="poptitle">
							<s:text name="%{getTranslaction(1216)}"/>
						</s:param>
						<s:param name="popurl" value="''">
						</s:param>
						<s:param name="paramnew">false</s:param>
						<s:param name="paramsale">false</s:param>
					</s:include>
				</div>
				<div class="grid-item">
					<s:include value="../template/presentationProductTemplate.jsp">
						<s:param name="imagename" value="'Basket-Heritage-Small.jpg'" />
						<s:param name="imagealt" value="'Basket Heritage Small" />
						<s:param name="imageurl" value="'190/heritage-small-basket-stars'" />
						<s:param name="id" value="'190'" />
						<s:param name="poptitle">
							<s:text name="%{getTranslaction(1218)}"/>
						</s:param>
						<s:param name="popurl" value="''">
						</s:param>
						<s:param name="paramnew">false</s:param>
						<s:param name="paramsale">false</s:param>
					</s:include>
				</div>
				<div class="grid-item">
					<s:include value="../template/presentationProductTemplate.jsp">
						<s:param name="imagename" value="'Basket-Heritage-pink-vinyl.jpg'" />
						<s:param name="imagealt" value="'Basket Heritage pink vinyl" />
						<s:param name="imageurl" value="'201/medium-basket-heritage-pink'" />
						<s:param name="id" value="'201'" />
						<s:param name="poptitle">
							<s:text name="%{getTranslaction(1234)}"/>
						</s:param>
						<s:param name="popurl" value="''">
						</s:param>
						<s:param name="paramnew">false</s:param>
						<s:param name="paramsale">false</s:param>
					</s:include>
				</div>
			</s:else>
		</div>
	</section>
	<!-- Start modal to display the image -->
	<s:include value="../template/popupImageTemplate.jsp"/>
	<!-- End modal to display the image -->
	
	<!-- *** END PRODUCTS *** -->

	<!--  *** COLLECTIONS *** -->

	<div id='collection_fragment' class="box text-center audit" data-animate="fadeInUp">
		<div class="container">
			<div class="col-md-12">
				<h3 class="text-uppercase">
					<s:text name="bolsos.home.collection.banner.title" />
				</h3>

				<p class="lead">
					<s:text name="bolsos.home.collection.banner.text" />
				</p>
			</div>
		</div>
	</div>

	<section class="bar background-white no-mb">
		<div id="collections" class="container" data-animate="fadeInUpBig">
			<div class="row">
				<div class="col-md-12">
					<div class="row portfolio">
						<s:iterator value="collectionDTOList.subList(1, 4)" status="status" var="collection">
							<div class="col-sm-4">
								<div class="box-image">
									<div class="image">
										<img src="${collectionpath}<s:property value="image.name" />" alt="<s:property value="image.name" />"
											class="img-responsive">
									</div>
									<div class="bg"></div>
									<div class="name">
										<h3>
											<a href='<s:text name="url.ourproduct.collections"/>/<s:property value="url" />'> <s:property value="name" />
											</a>
										</h3>
									</div>
									<div class="text">
										<p class="hidden-sm">
											${description}
										</p>
										<p class="buttons">
											<a href='<s:text name="url.ourproduct.collections"/>/<s:property value="url" />'
												class="btn btn-template-transparent-primary"><s:text name="bolsos.home.collection.banner.view"/></a>
										</p>
									</div>
								</div>
								<!-- /.item -->
							</div>
						</s:iterator>
					</div>

					<div class="see-more">
						<p><s:text name="bolsos.home.collection.paragraph1"/></p>

						<a href='<s:text name="url.collections"/>' class="btn btn-template-main"><s:text name="bolsos.home.collection.paragraph2"/></a>
					</div>

				</div>

			</div>
		</div>
	</section>


	<!-- *** END COLLECTIONS *** -->
	
	<!--  *** START MAGASINS *** -->

	<div  id='product_fragment' class="box text-center audit" data-animate="fadeInUp">
		<div class="container">
			<div class="col-md-12">
				<h3 class="text-uppercase">
					<s:text name="bolsos.home.magasins" />
				</h3>

				<p class="lead">
					<s:text name="bolsos.home.products.banner.text" />
				</p>
			</div>
		</div>
	</div>

<section class="bar background-white no-mb">
		<div id="magasins" class="container magasins-section" data-animate="fadeInUpBig">
			<div class="row">
				<!-- <div class="col-md-2 col-xs-3 col-sm-3 hidden-xs hidden-sm">
				</div> -->
				<!-- /.item -->
				<%-- <div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image img-circle">
							<a href='http://bloguelemoment.com/'>
								<img src="${images}/blogue-le-moment_600x600.png" alt="Le Moment"
									class="img-magasin" />
							</a>
						</div>
					</div>
				</div> --%>
				<!-- /.item -->
				<div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image">
							<a href='https://www.boucleetpapier.com/'>
								<img src="${images}/Boucle&Papier_600x600.png" alt="Boucle & Papier"
									class="img-magasin" />
							</a>
						</div>
					</div>
				</div>
				<!-- /.item -->
				<div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image">
							<a href='https://lepalaisbulles.ca/'>
								<img src="${images}/Palais-Bulles_600x600.png" alt="Palais Bulles"
									class="img-magasin" />
							</a>
						</div>
					</div>
				</div>
				<!-- /.item -->
				<div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image">
							<a href='https://www.lavitrinefamiliale.com/'>
								<img src="${images}/La-Vitrine-Familiale_600x600.png" alt="La Vitrine Familiale"
									class="img-magasin" />
							</a>
						</div>
					</div>
				</div>
				<!-- /.item -->
				<div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image">
							<a href='https://bouche-bee.ca/'>
								<img src="${images}/Bouche-Bee_600x600.png" alt="Bouche Bée"
									class="img-magasin" />
							</a>
						</div>
					</div>
				</div>
				<!-- /.item -->
				<div class="col-md-2 col-xs-4 col-sm-3 hidden-xs hidden-md hidden-lg">
				</div>
				<div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image">
							<a href='https://www.lepetitcocon.com/'>
								<img src="${images}/LePetitCocon_600x600.png" alt="Le Petit Cocon"
									class="img-magasin"/>
							</a>
						</div>
					</div>
				</div>
				<!-- /.item -->
				<div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image">
							<a href='https://signelocal.com/'>
								<img src="${images}/signe-local_600x600.png" alt="Signé Local"
									class="img-magasin" />
							</a>
						</div>
					</div>
				</div>
				<!-- /.item -->
			</div>
		</div>
	</section>
	
	<!-- *** END MAGASINS *** -->
	
	<!-- *** GET INSPIRED ***
 _________________________________________________________ -->
<%-- 	<div id='instruction_fragment' class="container audit" data-animate="fadeInUpBig">
		<div class="col-md-12">
			<div class="box slideshow">
				<h3><s:text name="bolsos.home.advertising4.title"></s:text></h3>
				<p class="lead"><s:text name="bolsos.home.advertising4.text"/></p>
				<a href="#"> 
					<img width="100%" height="auto" src="${images}/<s:text name="bolsos.home.advertising4.image"/>" alt="how works" >
				</a>
				<!-- <div id="get-inspired" class="owl-carousel owl-theme">
					<div class="item">
						<a href="#"> <img src="images/getinspired1.jpg" alt="Get inspired" class="img-responsive">
						</a>
					</div>
					<div class="item">
						<a href="#"> <img src="images/getinspired2.jpg" alt="Get inspired" class="img-responsive">
						</a>
					</div>
					<div class="item">
						<a href="#"> <img src="images/getinspired3.jpg" alt="Get inspired" class="img-responsive">
						</a>
					</div>
				</div> -->
			</div>
		</div>
	</div> --%>
	<!-- *** GET INSPIRED END *** -->

	<!-- *** BLOG HOMEPAGE ***
 _________________________________________________________ -->

	<div id='new_fragment' class="box text-center audit" data-animate="fadeInUp">
		<div class="container">
			<div class="col-md-12">
				<h3 class="text-uppercase">
					<s:text name="bolsos.home.section1.title" />
				</h3>

				<p class="lead">
					<s:text name="bolsos.home.section1.text" />
				</p>
			</div>
		</div>
	</div>

	<div class="container">

		<div class="col-md-12" data-animate="fadeInUp">

			<div id="blog-homepage" class="row">
				<div class="col-sm-6">
					<div class="post">
						<h4>
							<a href='<s:text name="url.company"/>'><s:text name="bolsos.home.section1.sub1.title" /> </a>
						</h4>
						
						<p class="intro" style="padding-top: 15px;">
							<s:text name="bolsos.home.section1.sub1.text" />
						</p>
						<p class="read-more">
							<a href='<s:text name="url.company"/>' class="btn btn-primary"><s:text name="bolsos.home.section1.sub2.button" /></a>
						</p>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="post">
						<h4>
							<a href='<s:text name="url.faq"/>'><s:text name="bolsos.home.section1.sub2.title" /> </a>
						</h4>
						
						<p class="intro" style="padding-top: 15px;">
							<s:text name="bolsos.home.section1.sub2.text" />
						</p>
						<p class="read-more">
							<a href='<s:text name="url.faq"/>' class="btn btn-primary"><s:text name="bolsos.home.section1.sub2.button" /> </a>
						</p>
					</div>

				</div>

			</div>
			<!-- /#blog-homepage -->
		</div>
	</div>
	<!-- /.container -->

	<!-- *** BLOG HOMEPAGE END *** -->


</div>
<!-- /#content -->

<!-- Promotion popup Start -->
<s:if test="%{promotion}">
	<div class="modal fade" id="promotionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span>
					</button>
					<s:if test="%{promotionUrl != ''}">
						<a href="<s:property value="promotionUrl" />">
							<img src="${promotionpath}/<s:property value="promotionImage" />" alt='<s:property value="couponTextName"/>' class="modal-content-image" style="width: 100%;" >
						</a>
					</s:if>
					<s:else>
						<img src="${promotionpath}/<s:property value="promotionImage" />" alt='<s:property value="couponTextName"/>' class="modal-content-image" style="width: 100%;" >
					</s:else>
				</div>
			</div>
		</div>
	</div>
</s:if>
<!-- Promotion popup End -->

<script type="text/javascript">

function checkPosition()
{
    if($(window).width() <= 375) 
    {
    	$('#instagram img:gt(5)').css("display", "none");
    }
    else if($(window).width() > 375 && $(window).width() <= 534)
    {
    	$('#instagram img:gt(8)').css("display", "none");
    }
    else if($(window).width() > 534 && $(window).width() <= 640)
    {
    	$('#instagram img:gt(7)').css("display", "none");
    }
    else if($(window).width() > 640 && $(window).width() <= 990)
    {
    	$('#instagram img:gt(8)').css("display", "none");
    }
    else if($(window).width() > 990 && $(window).width() <= 1200)
    {
    	$('#instagram img:gt(11)').css("display", "none");
    }
    else if($(window).width() > 1200){
    	$('#instagram img:gt(9)').css("display", "none");	
	}
}

jQuery(document).ready(function () {
	
	console.log('bind event');
	$(document).load($(window).bind("resize", checkPosition));
	
	$('.audit').waypoint(function(direction) {
		var msg = $(this).attr('id') + ' direction: ' + direction;
		if($(this).attr('id') == 'video_fragment'){
			console.log('masonry event');
			$('.container-portafolio').masonry({
			  itemSelector: '.grid-item',
			  percentPosition: true,
			  columnWidth: '.grid-sizer'
			});
		}
		$.getJSON("/ajax/auditAjax.action", { audit: msg });
	},
	{
		triggerOnce: true
	});
	
	new Fingerprint2().get(function(result, components){
		var excludeKey = "canvas,js_fonts,regular_plugins,webgl";
		var values = "";
		var key = "";
		var value = "";
		for (var i = 0; i < components.length; i++) {
			key = components[i].key;
			value = components[i].value;
			if( excludeKey.indexOf(key) == -1 ){
				values = "key: " + key + " value: " + value + " " + values;
			}
		}
		$.getJSON("/ajax/auditAjax.action", { audit: "fpw[" + result + "-" + values + "]" });
	});
	
	Ladda.bind( '.addItem' );
	
	$('.addItem').click(function(e) {
		e.preventDefault();
		var initText = $(this).text();
		var item = $(this);
		item.text("<s:text name='bolsos.shopping.cart.item.added'/>");
		item.css("background-color", "#fff");
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
				item.css("background-color", "");
			}, 2000);
		});
	});
	
	checkPosition();
	
	<s:if test="%{promotion}">
		 setTimeout(function(){
			var visited = $.cookie('visited');
			if (visited != 'true') {
				$('#promotionModal').modal('show');
				$.cookie('visited', 'true', { expires: 1 }); //Expirder in 1 day
		    } 		
		   },5000); // 5000 to load it after 5 seconds from page load  
	</s:if>
	
	
/*	
	$.getJSON('/ajax/userFeedMedia.action', {resolution : $(window).width()}, function(e) {
		var ref;
		var img;
		var col;
		var row = "<div class='row'></div>";
		$('#instagram').append( row );
		for ( var i = 0; i < e.listImage.length; i++) {
	          //img = "<img src='" + e.listImage[i].image + "' style='display: unset;'>";
	          img = "<img src='" + e.listImage[i].image + "' >";
	          ref = "<a href='" + e.listImage[i].link + "'>" + img + "</a>";
	          col = "<div class='col-md-4 col-xs-4 col-sm-4'>" + ref + "</div>";
	          $('#instagram .row').append( col );
	      }	
		checkPosition();
		$('.container-portafolio').masonry({
			  itemSelector: '.grid-item',
			  percentPosition: true,
			  columnWidth: '.grid-sizer'
			});
	});
	*/
	// init Masonry
	
	// layout Masonry after each image loads
	//$grid.imagesLoaded().progress( function() {
	//  $grid.masonry();
	//});  

	// bind event listener
	//$grid.on( 'layoutComplete', function() {
	//	$grid.masonry();
	//});
	
});
</script>
