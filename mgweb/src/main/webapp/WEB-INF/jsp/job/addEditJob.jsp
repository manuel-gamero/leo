<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveJob" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">
	
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Coupons Type</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<s:iterator value="fieldsView" status="status" var="field">
					<div class="form-group">
						<label for=code class="col-sm-1 control-label"><s:property value="%{#field}" /></label>
						<input id="code" name="entity.<s:property value="%{#field}" />" class="form-control"
							value="<s:text name="%{getFieldValue(#field)}" />" />
					</div>
				</s:iterator>	
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