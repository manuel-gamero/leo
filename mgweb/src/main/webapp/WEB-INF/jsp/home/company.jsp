<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.company.title"/></li>
			</ul>
		</div>

		<div class="col-md-3">
			<!-- *** PAGES MENU *** -->
			<s:include value="../template/pagesSectionTemplate.jsp">
				<s:param name="active" value="'company'" />
			</s:include>

			<!-- *** PAGES MENU END *** -->
		</div>

		<div class="col-md-9">

			<div class="box" id="text-page">
				<h1></h1>
				<div class="row">
					<div class="col-md-7">
						<p class="lead">
							<s:text name="bolsos.company.paragraph1.text" ></s:text>
						</p>
					</div>
					<div class="col-md-5">
						<p class="text-center">
							<img src="${images}/atelier-Leo-a-propos-workshop.jpg" class="img-thumbnail  img-responsive" alt="">
						</p>
					</div>
				</div>

				<p>
					<s:text name="bolsos.company.paragraph2.text"/>
				</p>
				<p>
					<s:text name="bolsos.company.paragraph3.text"/>
				</p>
				<p>
					<s:text name="bolsos.company.paragraph4.text"/>
				</p>

				<h2 class="about-header">
					<s:text name="bolsos.company.title1.text"/>
				</h2>

				<div class="row">
					<div class="same-height text-center">
						<div class="icon">
							<i class="fa fa-leather icon-behind"> </i>
						</div>
						<h3 class="advertising-title">
							<s:text name="bolsos.company.advertisement1.title"/>
						</h3>
						<p>
							<s:text name="bolsos.company.advertisement1.text"/>
						</p>
					</div>
				</div>

				<div class="row">
					<div class="same-height text-center">
						<div class="icon">
							<i class="fa fa-machine icon-behind"> </i>
						</div>
						<h3 class="advertising-title">
							<s:text name="bolsos.company.advertisement2.title"/>
						</h3>
						<p>
							<s:text name="bolsos.company.advertisement2.text"/>
						</p>
					</div>
				</div>

				<div class="row">
					<div class="same-height text-center">
						<div class="icon">
							<i class="glyphicon fa-palette icon-behind"> </i>
						</div>
						<h3 class="advertising-title">
							<s:text name="bolsos.company.advertisement3.title"/>
						</h3>
						<p>
							<s:text name="bolsos.company.advertisement3.text"/>
						</p>
					</div>
				</div>

				<h2 class="about-header">
					<s:text name="bolsos.company.title2.text"/>
				</h2>
				<div class="row">
					<div class="col-md-5">
						<p class="text-center">
							<img src="${images}/atelier-Leo-a-propos-raphaele.jpg" class="img-thumbnail  img-responsive" alt="">
						</p>
					</div>

					<div class="col-md-7">
						<p>
							<s:text name="bolsos.company.paragraph5"/>
						</p>
						<p>
							<s:text name="bolsos.company.paragraph6"/>
						</p>
						<p>
							<s:text name="bolsos.company.paragraph7"/>
						</p>
						<p>
							<s:text name="bolsos.company.paragraph8"/>
						</p>
						<p>
							<s:text name="bolsos.company.paragraph9"/>
						</p>
						<p class="read-more">
							<a href='<s:text name="url.collections"/>' class="btn btn-primary">
								<s:text name="bolsos.company.button.collections" />
							</a>
						</p>
					</div>


				</div>

			</div>


		</div>
		<!-- /.col-md-9 -->
	</div>
	<!-- /.container -->
</div>
<!-- /#content -->