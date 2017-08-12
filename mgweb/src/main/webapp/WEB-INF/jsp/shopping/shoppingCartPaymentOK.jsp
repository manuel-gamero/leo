
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">

			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.shoppingcart.ok.title"/> </li>
			</ul>


			<div class="row" id="error-page">
				<div class="col-sm-6 col-sm-offset-3">
					<div class="box">

						<p class="text-center">
							<img src="${images}/logo_small.jpg" alt="Obaju template">
						</p>

						<h3><s:text name="bolsos.shoppingcart.ok.text.info1"/></h3>
						<h4 class="text-muted"><s:text name="bolsos.shoppingcart.ok.text.info2"/></h4>

						<p class="buttons">
							<a href='<s:text name="url.shoppingcart.user"/>' class="btn btn-primary"><i class="fa fa-home"></i><s:text name="bolsos.shoppingcart.ok.button.text"/></a>
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
