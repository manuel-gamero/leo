<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveCouponsType" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden name="id" />
	<s:hidden name="taxe.creationDate" />
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Coupons Type</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				
				<div class="form-group">
					<label for=code class="col-sm-1 control-label">Code</label>
					<s:textfield id="code" name="couponsType.code" class="form-control"
						placeholder="code" />
				</div>
				
				<s:include value="../template/basicTranslationTemplate.jsp"></s:include>
				
				<div class="row">	   
					<label for="country" class="col-sm-1 control-label">Type</label>
					<s:select id="country" cssStyle="width:156px;" class="form-control" 
					name="couponsType.typeCode" list="codeTypeList" emptyOption="true"/>
				</div>
				
				<div class="form-group">
					<label for="multiTime" class="col-sm-1 control-label">multiTime</label>
					<s:textfield id="multiTime" name="couponsType.multiTime" class="form-control"
						placeholder="multiTime" />
				</div>
				<div class="form-group">
					<label for="multiUser" class="col-sm-1 control-label">multiUser</label>
					<s:textfield id="multiUser" name="couponsType.multiUser" class="form-control"
						placeholder="multiUser" />
				</div>
				
			</fieldset>
		</div>
		<div class="panel-footer">
			<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>
		</div>
	</div>

</s:form>
<script type="text/javascript">
$(document).ready(function () {
	

	tinymce.init({
		  selector: 'textarea',
		  height: 500,
		  toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
		  content_css: [
		    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
		    '//www.tinymce.com/css/codepen.min.css'
		  ]
		});
	
});
</script>