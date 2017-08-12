<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="ourProduct" namespace="/" method="post" validate="true" enctype="multipart/form-data"
	class="form-horizontal">
	<div id="content">
		<div class="container">

			<div class="col-md-12">

				<ul class="breadcrumb">
					<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
					<li><s:text name="bolsos.home.menu.collection"/></li>
				</ul>

				<div class="box">
					<h1><s:text name="bolsos.home.collection.banner.title"/> </h1>
					<p><s:text name="bolsos.home.collection.banner.description"/></p>
				</div>

				<!-- collections -->
				<div class="row portfolio">
						<s:iterator value="collectionDTOList" status="status" var="collection">
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
												class="btn btn-template-transparent-primary">
												<s:text name="bolsos.ourcollections.button.view"/>
											</a>
										</p>
									</div>
								</div>
								<!-- /.item -->
							</div>
						</s:iterator>
					</div>
				<!-- /.collections -->

			</div>
			<!-- /.col-md-9 -->

		</div>
		<!-- /.container -->
	</div>
	<!-- /#content -->

</s:form>

<script type="text/javascript">
$(document).ready(function () {
	
	$("select[name=sortBy]").change(function() {
		$("#form").submit();
	});
});	
</script>