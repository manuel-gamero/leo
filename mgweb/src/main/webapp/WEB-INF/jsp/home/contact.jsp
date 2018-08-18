<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.contact.title"/></li>
			</ul>

		</div>

		<div class="col-md-3">
			<!-- *** PAGES MENU ***-->
			<s:include value="../template/pagesSectionTemplate.jsp">
				<s:param name="active" value="'contact'" />
			</s:include>

			<!-- *** PAGES MENU END *** -->
		</div>

		<div class="col-md-9">


			<div class="box" id="contact">
				<h1><s:text name="bolsos.contact.title"/></h1>

				<p class="lead">
					<s:text name="bolsos.contact.paragraph1"/>
				</p>
				<p>
					<s:text name="bolsos.contact.paragraph2"/>
				</p>

				<hr>

				<div class="row">
					<div class="col-sm-4">
						<h3>
							<i class="fa fa-map-marker"></i>
							<%-- <s:text name="bolsos.contact.section.address.title"/> --%> 
						</h3>
						<p>
							<s:text name="bolsos.contact.section.address.text"/>
						</p>
					</div>
					<!-- /.col-sm-4 -->
					<div class="col-sm-4">
						<h3>
							<i class="fa fa-phone"></i> 
							<%-- <s:text name="bolsos.contact.section.call.title"/> --%>
						</h3>
						<p class="text-muted">
							<s:text name="bolsos.contact.section.call.text"/>
						</p>
						<p>
							<strong><s:text name="bolsos.contact.section.call.phone"/></strong>
						</p>
					</div>
					<!-- /.col-sm-4 -->
					<div class="col-sm-4">
						<h3>
							<i class="fa fa-envelope"></i> 
							<%-- <s:text name="bolsos.contact.section.support.title"/> --%>
						</h3>
						<p class="text-muted">
							<s:text name="bolsos.contact.section.support.text"/>
						</p>
						<p>
							<strong>
								<a href="mailto:info@latelierdeleo.com">
									<s:text name="bolsos.contact.section.support.mail"/>
								</a>
							</strong>
						</p>
					</div>
					<!-- /.col-sm-4 -->
				</div>
				<!-- /.row -->

				<hr>

				<div id="map"></div>

				<hr>
				<h2><s:text name="bolsos.contact.label.contact"/></h2>

				<s:form id="form" action="sendEmail" method="post" enctype="multipart/form-data"
					class="form-horizontal" namespace="/" validate="true" data-toggle="validator">
					<s:token></s:token>
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="firstname"><s:text name="bolsos.contact.label.firstname"/></label> 
								<s:textfield type="text" class="form-control" id="firstname" name="firstName"/>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="lastname"><s:text name="bolsos.contact.label.lastname"/></label> 
								<s:textfield type="text" class="form-control" id="lastname" name="lastName"/>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group has-feedback">
								<label for="email"><s:text name="bolsos.contact.label.email"/></label> 
								<s:textfield id="email" name="email" type="email"
									class="form-control" aria-describedby="email" 
									data-error="%{getText('bolsos.createaccount.error.label.email')}" required="" />
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
								<span class="sr-only" id="email">(success)</span>
								<small class="help-block with-errors"></small>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="subject"><s:text name="bolsos.contact.label.subject"/></label> 
								<s:textfield type="text" class="form-control" id="subject" name="subject"/>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<label for="message"><s:text name="bolsos.contact.label.message"/></label>
								<s:textarea id="message" class="form-control" name="message" />
							</div>
						</div>

						<div class="col-sm-12 text-center">
							<button type="submit" class="btn btn-primary">
								<i class="fa fa-envelope-o"></i> 
								<s:text name="bolsos.contact.button.send"/>
							</button>

						</div>
					</div>
					<!-- /.row -->
					</s:form>
				
				
				<h2 class="about-header">
						<s:text name="bolsos.contact.magasins"/>
				</h2>
				
				<div class="row">
				<div class="col-md-2 col-xs-3 col-sm-3 hidden-xs hidden-sm">
				</div>
				<!-- /.item -->
				<div class="col-md-2 col-xs-4 col-sm-3">
					<div class="box-image">
						<div class="image img-circle">
							<a href='http://bloguelemoment.com/'>
								<img src="${images}/blogue-le-moment_600x600.png" alt="Le Moment"
									class="img-magasin" />
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
				<div class="col-md-2 col-xs-4 col-sm-3 visibility-xs hidden-md hidden-lg">
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
			</div>
		</div>
				
			</div>
		</div>
		<!-- /.col-md-9 -->
	</div>
	<!-- /.container -->
</div>
<!-- /#content -->

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyCzCQPqMbWWCBUexMkXH2Pp0ydD9bBqZlQ"></script>



<script>
	function initialize() {
		var mapOptions = {
			zoom : 15,
			center : new google.maps.LatLng(45.5258768,-73.5951735),
			mapTypeId : google.maps.MapTypeId.ROAD,
			scrollwheel : false
		}
		var map = new google.maps.Map(document.getElementById('map'), mapOptions);

		var myLatLng = new google.maps.LatLng(45.5258768,-73.5951735);
		var marker = new google.maps.Marker({
			position : myLatLng,
			map : map
		});
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>
