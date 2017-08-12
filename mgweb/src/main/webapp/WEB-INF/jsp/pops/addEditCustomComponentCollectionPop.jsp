<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="saveCccTranslation" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden name="id" />
	<s:hidden name="url" />
	<s:hidden name="descrTranslationId" />
	<s:hidden name="nameTranslationId" />
	
	<s:token></s:token>
	<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Add/Edit Translation</h4>
	</div>
	<div class="modal-body">
		<fieldset>
				<div class="form-group">
					<label for="nameEn" class="col-md-2 control-label">English name</label>
					<s:textfield id="nameEn" name="nameEn" class="col-md-1 form-control"
						placeholder="English name" type="text"/>
				</div>
				<div class="form-group">
					<label for="nameFr" class="col-sm-2 control-label">French name</label>
					<s:textfield id="nameFr" name="nameFr" class="form-control"
						placeholder="French name:" type="text"/>
				</div>
				<div class="form-group">
					<label for="descEn" class="col-sm-3 control-label">English description</label>
					<s:textfield id="descEn" name="descEn" class="form-control"
						placeholder="English description" type="text"/>
				</div>
				<div class="form-group">
					<label for="descFr" class="col-sm-2 control-label">French description</label>
					<s:textfield id="descFr" name="descFr" class="form-control"
						placeholder="French description:" type="text"/>
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

  console.log('Asign click event to addCC');
  
   
});

  

  </script>
