<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="saveTranslation" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden name="id" />
	<s:hidden name="url" />
	
	<s:token></s:token>
	<div id="ajax-modal" class="modal fade" tabindex="-1" style="display: none;"></div>
	<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Add/Edit Translation</h4>
	</div>
	<div class="modal-body">
		<fieldset>
				<div class="form-group">
					<label for="nameEn" class="col-md-2 control-label">English text</label>
					<s:textarea id="textEn" name="textEn" class="col-md-1 form-control"
						placeholder="English text" type="text"/>
				</div>
				<div class="form-group">
					<label for="nameFr" class="col-sm-2 control-label">French text</label>
					<s:textarea id="textFr" name="textFr" class="form-control"
						placeholder="French text:" type="text"/>
				</div>
			</fieldset>
	</div>
	<div class="modal-footer">
	    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<s:submit value="Save" class="btn btn-primary"></s:submit>
	</div>
 </s:form>


<script type="text/javascript">

$(document).ready(function() { 
	tinymce.init({selector: 'textarea'});
	
});

  

  </script>
