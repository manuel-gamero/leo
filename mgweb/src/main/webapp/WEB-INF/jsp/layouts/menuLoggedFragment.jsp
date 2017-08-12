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
					<i class="fa fa-gift"></i> <s:text name="couponTextName" />
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
			</ul>
		</div>
	</div>

</div>

<!-- *** TOP BAR END *** -->
<!-- *** NAVBAR ***
 _________________________________________________________ -->

<div class="navbar navbar-default yamm" role="navigation" id="navbar">
	<div class="container">
		<div class="navbar-header">

			<a class="navbar-brand home" href='<s:text name="url.home"/>' data-animate-hover="bounce"> <img src="${images}/logo_small.jpg"
				alt="L'atelier de LEO logo" class="hidden-xs logo-icon"> <img src="${images}/logo_small.jpg"
				alt="L'atelier de LEO logo" class="visible-xs logo-icon"> <span class="sr-only"><s:text
						name="bolsos.mainmenu.label.leohome" /></span>
			</a>
			<div class="navbar-buttons">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation">
					<span class="sr-only"><s:text name="bolsos.mainmenu.label.toggle.navigation" /></span> <i
						class="fa fa-align-justify"></i>
				</button>
				<%-- <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#search">
					<span class="sr-only"><s:text name="bolsos.mainmenu.label.toggle.search" /></span> <i class="fa fa-search"></i>
				</button> --%>
				<a class="btn btn-default navbar-toggle" href='<s:text name="url.shoppingcart"/>'> <i
					class="glyphicon glyphicon-shopping-cart"></i> <span class="hidden-xs shoppingCartItems">(${shoppingCartCount}) <s:text
							name="bolsos.mainmenu.label.items" /></span>
				</a>
				<a class="share-page-movil share-email navbar-share-movil navbar-toggle" data-animate-hover="pulse" href="mailto:?subject=<s:text name='bolsos.share.email.subject'/> &amp;body=<s:text name='bolsos.share.email.body'/><s:text name='url.web'/><s:url/>"
				   title='<s:text name="bolsos.share.email.title"/>'>
				  <i class="fa fa-envelope ico-email-share-movil"></i>
				</a>
				<a id="sharePage" class="share-page-movil share-fb navbar-share-movil navbar-toggle" title='<s:text name="bolsos.share.fb.title"/>' href="<s:url/>" data-animate-hover="pulse">
						<i class="fa fa-facebook text-fb-share-movil"></i>
				</a>
				<!-- PayPal Logo -->
					<a href="https://www.paypal.com/webapps/mpp/paypal-popup" class="navbar-share-movil paypal-logo" onclick="javascript:window.open('https://www.paypal.com/webapps/mpp/paypal-popup','WIPaypal','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1060, height=700'); return false;">
					<img src="https://www.paypalobjects.com/webstatic/mktg/logo/bdg_now_accepting_pp_2line_w.png" class="paypal-banner" border="0" alt="Now Accepting PayPal"></a>
				<!-- PayPal Logo -->
			</div>
		</div>
		<!--/.navbar-header -->

		<div class="navbar-collapse collapse" id="navigation">

			<ul class="nav navbar-nav navbar-left">
				<li class="dropdown yamm-fw"><a href='<s:text name="url.home"/>' class="dropdown-toggle" data-hover="dropdown"
					data-delay="200"> <s:text name="bolsos.mainmenu.label.home" />
				</a>
				<li class="dropdown yamm-fw"><a href='<s:text name="url.company"/>' class="dropdown-toggle" data-hover="dropdown"
					data-delay="200"> <s:text name="bolsos.home.menu.company" />
				</a></li>
				
				<!-- Start collection section -->
				<li class="dropdown yamm-fw"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
					data-hover="dropdown" data-delay="200"><s:text name="bolsos.home.menu.collection"></s:text> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li>
							<div class="yamm-content">
								<div class="row">
									<s:iterator value="collectionDTOList" status="status" var="collection">
										<div class="col-sm-2">
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
					<ul class="dropdown-menu menu-type">
						<li>
							<div class="yamm-content">
								<div class="row">
									<div class="col-sm-2">
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
									<div class="col-sm-2">
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
									<div class="col-sm-2">
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
									<div class="col-sm-2">
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
					<ul class="dropdown-menu menu-type">
						<li>
							<div class="yamm-content">
								<div class="row">
									<div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.bag"/>'> <img src="${images}/large-tote-bag-sunny-dance-front.png"
												class="img img-responsive type-imag center-block" alt='<s:text name="bolsos.home.menu.products.bag"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.bag"/>'> <s:text name="bolsos.home.menu.products.bag" />
													</a>
												</h4>
											</div>
										</div>
									</div>
									<div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.large_pouch"/>'> <img src="${images}/large-pouch-into-the-wild-detail-1.png"
												class="img img-responsive type-imag center-block" alt='<s:text name="bolsos.home.menu.products.pouche"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.large_pouch"/>'> <s:text name="bolsos.home.menu.products.pouche" />
													</a>
												</h4>
											</div>
										</div>
									</div>
									<%-- <div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.wallet"/>'> <img src="${images}/wallet-open-sea-front.png"
												class="img img-responsive type-imag center-block" alt='<s:text name="bolsos.home.menu.products.wallet"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.wallet"/>'> <s:text name="bolsos.home.menu.products.wallet" />
													</a>
												</h4>
											</div>
										</div>
									</div> --%>
									<div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.medium_pouch"/>'> 
												<img
												src="${images}/Medium-pouch-open-sea-front.png" class="img img-responsive type-imag center-block"
												alt='<s:text name="enum.user.type.medium_pouch"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.medium_pouch"/>'> 
													<s:text name="enum.user.type.medium_pouch"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.tablet_pouch"/>'> 
												<img
												src="${images}/tablet-sleeve.png" class="img img-responsive type-imag center-block"
												alt='<s:text name="enum.user.type.tablet_pouch.title"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.tablet_pouch"/>'> 
													<s:text name="enum.user.type.tablet_pouch.title"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.laptop_pouch"/>'> 
												<img
												src="${images}/laptop-sleeve.png" class="img img-responsive type-imag center-block"
												alt='<s:text name="enum.user.type.laptop_pouch.title"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.laptop_pouch"/>'> 
													<s:text name="enum.user.type.laptop_pouch.title"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<%-- <div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.snack_bag"/>'> 
												<img
												src="${images}/<s:text name='enum.user.type.snack_bag.image'/>" class="img img-responsive type-imag center-block"
												alt='<s:text name="enum.user.type.snack_bag.title"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.snack_bag"/>'> 
													<s:text name="enum.user.type.snack_bag.title"/>
													</a>
												</h4>
											</div>
										</div>
									</div>
									<div class="col-sm-2">
										<div class="banner">
											<a href='<s:text name="url.ourproduct.type.girl_purse"/>'> 
												<img
												src="${images}/<s:text name='enum.user.type.girl_purse.image'/>" class="img img-responsive type-imag-custom center-block"
												alt='<s:text name="enum.user.type.girl_purse.title"/>' />
											</a>
											<div class="name text-center">
												<h4>
													<a href='<s:text name="url.ourproduct.type.girl_purse"/>'> 
													<s:text name="enum.user.type.girl_purse.title"/>
													</a>
												</h4>
											</div>
										</div>
									</div> --%>
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

		<div class="navbar-buttons">
			<div class="navbar-collapse collapse right navbar-share" >
				<a href="<s:url/>" title='<s:text name="bolsos.share.fb.title"/>' class="share-page share-fb" data-animate-hover="pulse">
						<i class="fa fa-facebook text-fb-share"></i>
				</a>
				<a class="share-page share-email" data-animate-hover="pulse" href="mailto:?subject=<s:text name='bolsos.share.email.subject'/> &amp;body=<s:text name='bolsos.share.email.body'/><s:text name='url.web'/><s:url/>"
				   title="<s:text name="bolsos.share.email.title"/>">
				  <i class="fa fa-envelope ico-email-share"></i>
				</a>
			</div>
			<div class="navbar-collapse collapse right" id="basket-overview">
				<a href='<s:text name="url.shoppingcart"/>' class="btn btn-primary navbar-btn"> <i class="glyphicon glyphicon-shopping-cart"></i>
					<span class="hidden-xs shoppingCartItems">(${shoppingCartCount}) <s:text name="bolsos.mainmenu.label.items" /></span>
				</a>
			</div>
			<!-- PayPal Logo -->
			<div class="navbar-collapse collapse right">
				<a href="https://www.paypal.com/webapps/mpp/paypal-popup" onclick="javascript:window.open('https://www.paypal.com/webapps/mpp/paypal-popup','WIPaypal','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1060, height=700'); return false;">
				<img src="https://www.paypalobjects.com/webstatic/mktg/logo/bdg_now_accepting_pp_2line_w.png" class="paypal-banner" border="0" alt="Now Accepting PayPal"></a>
			</div>
			<!-- PayPal Logo -->
			<!--/.nav-collapse -->

			<%-- <div class="navbar-collapse collapse right" id="search-not-mobile">
				<button type="button" class="btn navbar-btn btn-primary" data-toggle="collapse" data-target="#search">
					<span class="sr-only"><s:text name="bolsos.mainmenu.label.toggle.search" /></span> <i class="fa fa-search"></i>
				</button>
			</div> --%>

		</div>

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



