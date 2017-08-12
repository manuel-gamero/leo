<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="box-image">

	<div class="image">
		<img src="${images}/${param.imagename}" alt="${param.imagealt}"
			class="img-responsive">
	</div>
	<div class="bg"></div>
	<div class="name">
		<a href="${images}/${param.imagename}"
			class="modalImage btn btn-lg btn-template-transparent-primary btn-circle"
			title='${param.poptitle}' 
			data-link='${param.popurl}'> 
			<i class="glyphicon glyphicon-search zoom-icon"></i>
		</a>
	</div>
	<div class="text">
		<p class="buttons">
			<a href='<s:text name="url.product"/>/${param.imageurl}' class="btn btn-template-transparent-primary">
				<s:text name="bolsos.ourproduct.button.viewdetail" />
			</a> 
			<a href='/add_${param.id}' class="btn btn-template-transparent-primary ladda-button addItem"
					data-spinner-color="#38a7bb" data-style="expand-left" > 
				<i class="fa fa-shopping-cart"></i> 
				<s:text name="bolsos.product.button.add" />
			</a>
		</p>

	</div>

</div>

