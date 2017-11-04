<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="dark-mask"></div>
<div id="content">

	<div class="container">
			<!-- Carousel================================================== -->
			<%-- <div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
				</ol>
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img src="${images}/Harmony-rifle-paper-leo.jpg" alt="First slide">

						<div class="carousel-caption">
							<h1>
								<s:text name="bolsos.home.carrusel.slide4.title" />
							</h1>
							<p class="carousel-slide-desc">
								<s:text name="bolsos.home.carrusel.slide4.text" />
							</p>
							<p>
								<a class="btn btn-lg btn-primary" href='<s:text name="url.collection.harmony"/>' role="button"><s:text
										name="bolsos.home.carrusel.slide1.button" /></a>
							</p>
						</div>

					</div>
					<div class="item">
						<img src="${images}/sunny-dance-atelier-leo.jpg" alt="Second slide">

						<div class="carousel-caption">
							<h1 class="carousel-slide-desc">
								<s:text name="bolsos.home.carrusel.slide5.title" />
							</h1>
							<h1 class="carousel-slide-title">
								<s:text name="bolsos.home.carrusel.slide5.title" />
							</h1>
							<p class="carousel-slide-desc">
								<s:text name="bolsos.home.carrusel.slide5.text" />
							</p>
							<p>
								<a class="btn btn-lg btn-primary" href='<s:text name="url.collection.sunnydance"/>' role="button"><s:text
										name="bolsos.home.carrusel.slide1.button" /></a>
							</p>
						</div>

					</div>
					<div class="item">
						<img src="${images}/welcome-atelier-leo.jpg" alt="Third slide">

						<div class="carousel-caption">
							<h1>
								<s:text name="bolsos.home.carrusel.slide6.title" />
							</h1>
							<p class="carousel-slide-desc">
								<s:text name="bolsos.home.carrusel.slide3.text" />
							</p>
							<p>
								<a class="btn btn-lg btn-primary" href='<s:text name="url.company"/>' role="button"> <s:text
										name="bolsos.home.carrusel.slide1.button" />
								</a>
							</p>
						</div>

					</div>
				</div>
				<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only"><s:text
							name="bolsos.home.carrusel.previous" /></span>
				</a> <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only"><s:text
							name="bolsos.home.carrusel.next" /></span>
				</a>
			</div> --%>
			<!-- /.carousel -->
			<!-- /#main-slider -->
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
			<div class="grid-item">
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/173/cosmetic_bag_rifle_red_2.jpg'" />
					<s:param name="imagealt" value="'cosmetic bag harmony navy'" />
					<s:param name="imageurl" value="'173/harmony-pouch-navy'" />
					<s:param name="id" value="'173'" />
					<s:param name="poptitle">
						<s:text name='bolsos.home.carrusel.slide4.title' />
					</s:param>
					<s:param name="popurl">
						<s:text name='url.collection.harmony' />
					</s:param>
				</s:include>
			</div>
			<div class="grid-item">
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/135/large_tote_bag_sunny_dance_product.jpg'" />
					<s:param name="imagealt" value="'tote bag sunny dance anchors'" />
					<s:param name="imageurl" value="'135/tote-bag-anchors-navy--gold'" />
					<s:param name="id" value="'135'" />
					<s:param name="poptitle">
						<s:text name="bolsos.collections.C0011.title"/>
					</s:param>
					<s:param name="popurl">
						<s:text name='url.collection.sunnydance' />
					</s:param>
				</s:include>
			</div>
			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/149/lr_housse_ordi_jaune_1.jpg'" />
					<s:param name="imagealt" value="'housse ordi jaune'" />
					<s:param name="imageurl" value="'149/laptop-sleeve-yellow'" />
					<s:param name="id" value="'149'" />
					<s:param name="poptitle">
						<s:text name="%{getTranslaction(979)}"/>
					</s:param>
					<s:param name="popurl" value="''">
					</s:param>
				</s:include>
			</div>
			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/171/cosmetic_bag_rifle_pink_3.jpg'" />
					<s:param name="imagealt" value="'cosmetic bag harmony peach'" />
					<s:param name="imageurl" value="'171/harmony-pouch-peach'" />
					<s:param name="id" value="'171'" />
					<s:param name="poptitle">
						<s:text name='bolsos.home.carrusel.slide4.title' />
					</s:param>
					<s:param name="popurl">
						<s:text name='url.collection.harmony' />
					</s:param>
				</s:include>
				<!-- /.box-image -->
			</div>
			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/133/medium_pouch_open_sea_2.jpg'" />
					<s:param name="imagealt" value="'medium pouch open sea'" />
					<s:param name="imageurl" value="'133/medium-pouch-open-sea-navy'" />
					<s:param name="id" value="'133'" />
					<s:param name="poptitle">
						<s:text name="%{getTranslaction(785)}"/>
					</s:param>
					<s:param name="popurl" value="''">
					</s:param>
				</s:include>
				<!-- /.box-image -->
			</div>

			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/165/medium_pouch_pleat_green_hearts_2.jpg'" />
					<s:param name="imagealt" value="'cosmetic bag green hearts'" />
					<s:param name="imageurl" value="'165/green-hearts-cosmetic-pouch-sunny-dance'" />
					<s:param name="id" value="'165'" />
					<s:param name="poptitle">
						<s:text name="bolsos.collections.C0011.title"/>
					</s:param>
					<s:param name="popurl">
						<s:text name='url.collection.sunnydance' />
					</s:param>
				</s:include>
			</div>
			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/176/pencil_case_rifle_red_1.jpg'" />
					<s:param name="imagealt" value="'pencil case harmony navy'" />
					<s:param name="imageurl" value="'176/hamony-pencil-case-navy'" />
					<s:param name="id" value="'176'" />
					<s:param name="poptitle">
						<s:text name='bolsos.home.carrusel.slide4.title' />
					</s:param>
					<s:param name="popurl">
						<s:text name='url.collection.harmony' />
					</s:param>
				</s:include>
			</div>
			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/160/medium_pouch_pink_triangles_1.jpg'" />
					<s:param name="imagealt" value="'medium pouch sunny dance pink'" />
					<s:param name="imageurl" value="'160/pink-triangles-pouch-sunny-dance'" />
					<s:param name="id" value="'160'" />
					<s:param name="poptitle">
						<s:text name="bolsos.collections.C0011.title"/>
					</s:param>
					<s:param name="popurl">
						<s:text name='url.collection.sunnydance' />
					</s:param>
				</s:include>
				<!-- /.box-image -->
			</div>

			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/155/lr_housse_tablette_taupe_4.jpg'" />
					<s:param name="imagealt" value="'housse tablette taupe'" />
					<s:param name="imageurl" value="'155/tablet-sleeve-taupe-grey'" />
					<s:param name="id" value="'155'" />
					<s:param name="poptitle">
						<s:text name="%{getTranslaction(983)}"/>
					</s:param>
					<s:param name="popurl" value="''">
					</s:param>
				</s:include>
			</div>
			<div class="grid-item">
				<!-- /.box-image -->
				<s:include value="../template/presentationProductTemplate.jsp">
					<s:param name="imagename" value="'product/131/tote_bag_into_the_wild_product.jpg'" />
					<s:param name="imagealt" value="'tote bag into the wild product'" />
					<s:param name="imageurl" value="'131/tote-bag-zebras-pink'" />
					<s:param name="id" value="'131'" />
					<s:param name="poptitle">
						<s:text name="%{getTranslaction(781)}"/>
					</s:param>
					<s:param name="popurl" value="''"/>
				</s:include>
				<!-- /.box-image -->
			</div>
			
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

	<!-- *** GET INSPIRED ***
 _________________________________________________________ -->
	<div id='instruction_fragment' class="container audit" data-animate="fadeInUpBig">
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
	</div>
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
