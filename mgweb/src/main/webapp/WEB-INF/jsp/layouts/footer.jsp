<%@ taglib uri="/struts-tags" prefix="s" %>
<!-- *** FOOTER ***
 _________________________________________________________ -->
<div id="footer" data-animate="fadeInUp">
	<div class="container">
		<div class="row">
			<div class="col-md-3 col-sm-6">
				<h4><s:text name="bolsos.footer.pages.title"/></h4>

				<ul>
					<li><a href='<s:text name="url.faq"/>'><s:text name="bolsos.footer.pages.faq"></s:text></a></li>
					<li><a href='<s:text name="url.contact"/>'><s:text name="bolsos.footer.pages.contact"/></a></li>
					<li><a href='<s:text name="url.termConditions"/>'><s:text name="bolsos.home.menu.termConditions"/></a></li>
					<li>
						<a data-target="#login-modal" data-toggle="modal" href="#">
							<s:text name="bolsos.mainmenu.label.login"/>
						</a>
					</li>
					<%-- <li><a href="text.html"><s:text name="bolsos.footer.pages.terms"/></a></li> --%>
					
					
				</ul>

				<hr class="hidden-md hidden-lg hidden-sm">

			</div>
			<!-- /.col-md-3 -->

			<div class="col-md-3 col-sm-6">

				<h4><s:text name="bolsos.footer.categories"/></h4>
				
				<ul>
					<li><a href='<s:text name="url.company"/>'><s:text name="bolsos.footer.pages.about"/></a></li>
					<li><a href='<s:text name="url.collections"/>'><s:text name="bolsos.footer.categories.collections"/></a></li>
					<li><a href='<s:text name="url.ourproduct.type.bag.custom"/>'><s:text name="enum.user.type.bag"/></a></li>
					<li><a href='<s:text name="url.ourproduct.type.large_pouch.custom"/>'><s:text name="enum.user.type.large_pouch"/></a></li>
					<li><a href='<s:text name="url.ourproduct.type.medium_pouch.custom"/>'><s:text name="enum.user.type.medium_pouch"/></a></li>
					<li><a href='<s:text name="url.ourproduct.type.wallet.custom"/>'><s:text name="enum.user.type.wallet"/></a></li>	
				</ul>

				<hr class="hidden-md hidden-lg">

			</div>
			
			<!-- /.col-md-3 -->
			<div class="col-md-3 col-sm-6">

				<h4><s:text name="bolsos.footer.news.title"/></h4>

				<p class="social">
					<a href="https://www.facebook.com/Latelier-de-Leo-1602516443339552" class="facebook external" data-animate-hover="shake">
						<i class="fa fa-facebook"></i>
					</a> 
					<a href="https://www.instagram.com/latelier_de_leo/" class="instagram external" data-animate-hover="shake">
						<i class="fa fa-instagram"></i>
					</a>  
					<a href="mailto:info@latelierdeleo.com" class="email external" data-animate-hover="shake">
						<i class="fa fa-envelope"></i>
					</a>
				</p>
				
			</div>
			<!-- /.col-md-3 -->

			<div class="col-md-3 col-sm-6">
				<h4><s:text name="bolsos.footer.payment.options"/></h4>
				
					<img src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/cc-badges-ppppcmcvdam.png" 
						 alt="Pay with PayPal, PayPal Credit or any major credit card" 
						 class="img-responsive center-block"/>
				
			</div>
			<!-- /.col-md-3 -->
		</div>
		<!-- /.row -->

	</div>
	<!-- /.container -->
</div>
<!-- /#footer -->

<!-- *** FOOTER END *** -->

<!-- *** COPYRIGHT ***
_________________________________________________________ -->
<div id="copyright">
    <div class="container">
        <div class="col-md-6">
            <p class="pull-left"><s:text name="bolsos.footer.copyright.text"/></p>
        </div>
        <div class="col-md-6">
            <p class="pull-right size-footer-right">Template by <a href="http://bootstrapious.com/e-commerce-templates">Bootstrap Ecommerce Templates</a> with support from Designové předměty 
                <!-- Not removing these links is part of the licence conditions of the template. Thanks for understanding :) -->
            </p>
        </div>
    </div>
    <div class="container">
        <div class="col-md-6">
            <p class="pull-left"><s:text name="bolsos.footer.photo.text"/></p>
        </div>
    </div>
</div>

<!-- *** COPYRIGHT END *** -->