<%@ taglib uri="/struts-tags" prefix="s"%>
<!-- *** TOPBAR *** -->
<div id="top">
	<div class="container">
		<div class="col-md-6 offer" data-animate="fadeInDown">
			<%-- <span>
				<i class="glyphicon glyphicon-gift"></i> 
				<s:text name="bolsos.home.menu.offerta"/>
			</span> --%>
			<s:if test="%{couponTextName != ''}">
				<p class="coupon-info">
					<i class="fa fa-gift"></i> <s:property value="couponTextName"/>
				</p>
			</s:if>
		</div> 
		<div class="col-md-6">
			<ul class="menu">
				<li class="dropdown yamm-fw"><a class="dropdown-toggle" data-delay="200" data-hover="dropdown"
					data-toggle="dropdown" href="#"> <i class="fa fa-user"></i> <s:property value="#loginName" /><span
						class="caret"></span>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a class="dropdown-user-item" href='<s:text name="url.shoppingcart.user"/>'> <i class="fa fa-list"></i> <s:text
									name="bolsos.mainmenu.label.user.orders" />
						</a></li>
						<li><a class="dropdown-user-item" href='<s:text name="url.user.account"/>'> <i class="fa fa-user"></i> <s:text
									name="bolsos.mainmenu.label.user.profile" />
						</a></li>
						<li class="divider"></li>
						<li><a class="dropdown-user-item" href="/user/logout"> <i class="fa fa-sign-out"></i> <s:text
									name="bolsos.mainmenu.label.user.logout" />
						</a></li>
					</ul></li>
				<li class="dropdown yamm-fw">
					<a class="dropdown-toggle" data-delay="200" data-hover="dropdown"
					data-toggle="dropdown" href="#">
						<s:text name="bolsos.mainmenu.label.lang" /> <span class="caret"></span>
					</a>
					<ul class="dropdown-menu menu-lang">
						<li><a href="/user/switchLang.action?lang=en" class="dropdown-user-item"><s:text
									name="bolsos.mainmenu.label.lang.english" /></a></li>
						<li><a href="/user/switchLang.action?lang=fr" class="dropdown-user-item"><s:text
									name="bolsos.mainmenu.label.lang.french" /></a></li>
					</ul></li>
				<s:if test="#isCurrencyChoice">
					<li class="dropdown yamm-fw">
						<a class="dropdown-toggle" data-delay="200" data-hover="dropdown"
						data-toggle="dropdown" href="#"> 
							<s:property value="currentCurrencyCode"/> <span class="caret"></span>
						</a>
						<ul class="dropdown-menu menu-lang">
							<li><a href="/user/switchCurrency.action?currency=CAD" class="dropdown-user-item">
							CAD</a></li>
							<li><a href="/user/switchCurrency.action?currency=EUR" class="dropdown-user-item">
							EUR</a></li>
							<li><a href="/user/switchCurrency.action?currency=USD" class="dropdown-user-item">
							USD</a></li>
						</ul>
					</li>
				</s:if>
				<s:else>
					<li class="yamm-fw menu-top"><span><s:property value="currentCurrencyCode"/></span></li>
				</s:else>
				<li class="hidden-xs">
					<a href='<s:text name="url.shoppingcart"/>' > 
						<i class="glyphicon glyphicon-shopping-cart"></i>
						<span class="hidden-xs shoppingCartItems">
							(${shoppingCartCount})
						</span>
					</a>
				</li>
			</ul>
		</div>
	</div>

</div>

<!-- *** TOP BAR END *** -->
<!-- *** NAVBAR ***
 _________________________________________________________ -->

<div class="navbar navbar-default yamm" role="navigation" id="navbar">
	<div class="container">
		<div>
			<a class="home" href='<s:text name="url.home"/>' data-animate-hover="bounce"> 
				<img src="${images}/atelier-Leo-logo-small-black.png"	alt="L'atelier de LEO logo" class="img-responsive center-block logo"> 
				<span class="sr-only">
					<s:text	name="bolsos.mainmenu.label.leohome" />
				</span>
			</a>
		</div>
		<div class="navbar-header">
			<div class="navbar-buttons">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation">
					<span class="sr-only"><s:text name="bolsos.mainmenu.label.toggle.navigation" /></span> <i
						class="fa fa-align-justify"></i>
				</button>
				<%-- <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#search">
					<span class="sr-only"><s:text name="bolsos.mainmenu.label.toggle.search" /></span> <i class="fa fa-search"></i>
				</button> --%>
				<a class="btn btn-default navbar-toggle" href='<s:text name="url.shoppingcart"/>'> <i
					class="glyphicon glyphicon-shopping-cart"></i> 
					<span class="hidden-xs shoppingCartItems">
						(${shoppingCartCount}) <s:text name="bolsos.mainmenu.label.items" />
					</span>
				</a>
			</div>
		</div>
		<!--/.navbar-header -->

		<div class="navbar-collapse collapse navbar-center	" id="navigation">

			<ul class="nav navbar-nav">
				<li class="dropdown yamm-fw"><a href='<s:text name="url.home"/>' class="dropdown-toggle" data-hover="dropdown"
					data-delay="200"> <s:text name="bolsos.mainmenu.label.home" />
				</a>
				</li>
				<li class="dropdown yamm-fw"><a href='<s:text name="url.company"/>' class="dropdown-toggle" data-hover="dropdown"
					data-delay="200"> <s:text name="bolsos.home.menu.company" />
				</a>
				</li>
				
				<!-- Start collection section -->
				<li class="dropdown yamm-fw"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
					data-hover="dropdown" data-delay="200"><s:text name="bolsos.home.menu.collection"></s:text> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li>
							<div class="yamm-content">
								<div class="row">
									<s:iterator value="collectionDTOList" status="status" var="collection">
										<div class="col-sm-3 col-md-2">
											<div class="banner">
												<a href='<s:text name="url.ourproduct.collections"/>/<s:property value="url" />'> <img
													src="${collectionpath}<s:property value="image.name" />" class="img img-responsive collection-imag"
													alt="<s:property value="image.name" />" />
												</a>
												<div class="name">
													<h4>
														<a href='<s:text name="url.ourproduct.collections"/>/<s:property value="url" />'> <s:property value="name" />
														</a>
													</h4>
												</div>
											</div>
										</div>
									</s:iterator>
								</div>
							</div> <!-- /.yamm-content -->
						</li>
					</ul></li>
				<!-- End collection section -->
				
				<!-- Start product custom section -->
				<li class="dropdown yamm-fw">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"
					data-hover="dropdown" data-delay="200"> 
						<s:text name="bolsos.home.menu.custom" /> 
						<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li>
							<div class="yamm-content">
								<div class="row">
									<div class="col-sm-3 col-md-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.bag.custom"/>'> 
												<img
												src="${images}/Large_tote_bag.png" class="img img-responsive type-imag-bag center-block"
												alt='<s:text name="bolsos.home.menu.products.bag"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.bag.custom"/>'> 
													<s:text name="bolsos.home.menu.products.bag"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<div class="col-sm-3 col-md-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.large_pouch.custom"/>'> 
												<img
												src="${images}/Large_pouch.png" class="img img-responsive type-imag-custom center-block"
												alt='<s:text name="bolsos.home.menu.products.pouche"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.large_pouch.custom"/>'> 
													<s:text name="bolsos.home.menu.products.pouche"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<%-- <div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.wallet.custom"/>'> 
												<img
												src="${images}/Wallet.png" class="img img-responsive type-imag-custom center-block"
												alt='<s:text name="bolsos.home.menu.products.wallet"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.wallet.custom"/>'> 
													<s:text name="bolsos.home.menu.products.wallet"/>
													</a>
												</h4>
											</div>
										</div>
									</div> --%>
									<div class="col-sm-3 col-md-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.medium_pouch.custom"/>'> 
												<img
												src="${images}/Medium-pouch-RV.png" class="img img-responsive type-imag-custom center-block"
												alt='<s:text name="enum.user.type.medium_pouch"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.medium_pouch.custom"/>'> 
													<s:text name="enum.user.type.medium_pouch"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<div class="col-sm-3 col-md-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.girl_purse.custom"/>'> 
												<img
												src="${images}/<s:text name='enum.user.type.girl_purse.image'/>" class="img img-responsive type-imag-custom center-block"
												alt='<s:text name="enum.user.type.girl_purse.title"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.girl_purse.custom"/>'> 
													<s:text name="enum.user.type.girl_purse.title"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
								</div>
							</div> <!-- /.yamm-content -->
						</li>
					</ul>
				</li>
				<!-- End product custom section -->
				
				<!-- Start product section -->
				<li class="dropdown yamm-fw"><a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
					data-delay="200"> <s:text name="bolsos.home.menu.products" /> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li>
							<div class="yamm-content">
								<div class="row">
									<div class="col-sm-3 col-md-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.pouches"/>'> 
												<img
												src="${images}/Travel-pouch.png" class="img img-responsive type-imag center-block"
												alt='<s:text name="enum.user.type.pouches"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.pouches"/>'>
													<s:text name="enum.user.type.pouches"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<div class="col-sm-3 col-md-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.baskets"/>'> 
												<img
												src="${images}/Basket-pot-holder.png" class="img img-responsive type-imag center-block"
												alt='<s:text name="enum.user.type.baskets"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.baskets"/>'> 
													<s:text name="enum.user.type.baskets"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
								</div>
							</div> <!-- /.yamm-content -->
						</li>
					</ul>
				</li>
				<!-- End product section -->
				<li class="dropdown yamm-fw"><a href='<s:text name="url.contact"/>' class="dropdown-toggle" data-hover="dropdown"
					data-delay="200"> <s:text name="bolsos.home.menu.contact" />
				</a></li>

			</ul>

		</div>
		<!--/.nav-collapse -->

		<div class="collapse clearfix" id="search">

			<form class="navbar-form" role="search">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search"> <span class="input-group-btn">

						<button type="submit" class="btn btn-primary">
							<i class="fa fa-search"></i>
						</button>

					</span>
				</div>
			</form>

		</div>
		<!--/.nav-collapse -->

	</div>
	<!-- /.container -->
</div>
<!-- /#navbar -->

<!-- *** NAVBAR END *** -->

<!-- END TOP MENU -->
<script type="text/javascript">
$(document).ready(function () {
	
	$('#sign_in_link').click(function() {
		$.get('signInPop.action', function(data) {
		      $(data).modal('show');
		      //$('body').removeAttr( "style" );
		  });
	});
	
	$(".share-fb").click(function(e){
		e.preventDefault();
		var name = $('meta[property="og:title"]').attr('content');
		var link = window.location.href;
		var picture = $('meta[property="og:image"]').attr('content');
		var caption = $('meta[property="og:title"]').attr('content');
		var description = $('meta[property="og:description"]').attr('content');
		console.log("Sharing page: ");
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
			console.log("name: " + name);
			console.log("caption: " + caption);
			console.log("link: " + link);
			console.log("picture: " + picture);
			FB.ui({
				method: 'share',
				name: name,
				href: link,
				picture: picture,
				caption: caption,
				description: description,
				message: ''
			});
		});
	});

});	
</script>



