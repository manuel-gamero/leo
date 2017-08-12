<%@ taglib uri="/struts-tags" prefix="s"%>
<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.term.title"/> </li>
			</ul>

		</div>
		
		<div class="col-md-3">
			<!-- *** PAGES MENU *** -->
			<s:include value="../template/pagesSectionTemplate.jsp">
				<s:param name="active" value="'term'" />
			</s:include>

			<!-- *** PAGES MENU END *** -->
		</div>

		<div class="col-md-9">

			<div class="box" id="contact">
				<h1><s:text name="bolsos.term.title"/></h1>

				<div class="panel-group" id="accordion">

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph1.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph1.text"/>
							</p>
						</div>
					</div>
				
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph2.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph2.text"/>
							</p>
						</div>
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph3.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph3.text"/>
							</p>
						</div>
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph4.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph4.text1"/>
							</p>
							<p>
								<s:text name="bolsos.term.paragraph4.text2"/>
							</p>
						</div>
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph5.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph5.text"/>
							</p>
						</div>
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph6.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph6.text"/>
							</p>
						</div>
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph7.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph7.text"/>
							</p>
						</div>
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4 class="panel-title">
								<s:text name="bolsos.term.paragraph8.title"/>
							</h4>
						</div>
						<div class="panel-body">
							<p>
								<s:text name="bolsos.term.paragraph8.text"/>
							</p>
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
