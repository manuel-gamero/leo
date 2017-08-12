<%@ taglib uri="/struts-tags" prefix="s"%>
<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li>FAQ</li>
			</ul>

		</div>

		<div class="col-md-3">
			<!-- *** PAGES MENU *** -->
			<s:include value="../template/pagesSectionTemplate.jsp">
				<s:param name="active" value="'faq'" />
			</s:include>

			<!-- *** PAGES MENU END *** -->
		</div>

		<div class="col-md-9">


			<div class="box" id="contact">
				<h1><s:text name="bolsos.faq.title"/></h1>

				<p class="lead">
					<s:text name="bolsos.faq.header"/>
				</p>
				
				<hr>

				<div class="panel-group" id="accordion">

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq1">
									<s:text name="bolsos.faq.question1.question"/>
								</a>

							</h4>
						</div>
						<div id="faq1" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question1.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq2">
									<s:text name="bolsos.faq.question2.question"/>
								</a>

							</h4>
						</div>
						<div id="faq2" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question2.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq3">
									<s:text name="bolsos.faq.question3.question"/>
								</a>

							</h4>
						</div>
						<div id="faq3" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question3.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq4">
									<s:text name="bolsos.faq.question4.question"/>
								</a>

							</h4>
						</div>
						<div id="faq4" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question4.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq5">
									<s:text name="bolsos.faq.question5.question"/>
								</a>

							</h4>
						</div>
						<div id="faq5" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question5.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq6">
									<s:text name="bolsos.faq.question6.question"/>
								</a>

							</h4>
						</div>
						<div id="faq6" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question6.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq7">
									<s:text name="bolsos.faq.question7.question"/>
								</a>

							</h4>
						</div>
						<div id="faq7" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question7.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">

								<a data-toggle="collapse" data-parent="#accordion" href="#faq8">
									<s:text name="bolsos.faq.question8.question"/>
								</a>

							</h4>
						</div>
						<div id="faq8" class="panel-collapse collapse">
							<div class="panel-body">
								<p>
									<s:text name="bolsos.faq.question8.respond"/>
								</p>
							</div>
						</div>
					</div>
					<!-- /.panel -->

					


					

				</div>
				<!-- /.panel-group -->


			</div>


		</div>
		<!-- /.col-md-9 -->
	</div>
	<!-- /.container -->
</div>
<!-- /#content -->
