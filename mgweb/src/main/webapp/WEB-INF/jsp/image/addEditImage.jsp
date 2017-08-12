<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveCc" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden name="id" />
	<s:hidden name="creationDate" />
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">View Image</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<div class="form-group">
					<label for="name" class="col-sm-1 control-label">Name</label>
					<s:property value="name" />
				</div>
				
				<div class="form-group">
					<label for="type" class="col-sm-1 control-label">Type</label>
					<s:property value="typeCode" />
				</div>
				
				<div class="form-group">
					<label for="size" class="col-sm-1 control-label">Size</label>
					<s:property value="size" />
				</div>
				
				<div class="form-group">
					<label for="height" class="col-sm-1 control-label">Height</label>
					<s:property value="height" />
				</div>
				
				<div class="form-group">
					<label for="width" class="col-sm-1 control-label">Width</label>
					<s:property value="width" />
				</div>
				
				<div class="form-group">
					<label for="large" class="col-sm-1 control-label">Large</label>
					<s:property value="large" />
				</div>
				
				<div class="form-group">
					<label for="uploadDate" class="col-sm-1 control-label">UploadDate</label>
					<s:property value="uploadDate" />
				</div>
				
				<div class="form-group">
					<label for="resolution" class="col-sm-1 control-label">Resolution</label>
					<s:property value="resolution" />
				</div>
				
				<div class="form-group">
					<label for="score" class="col-sm-1 control-label">Score</label>
					<s:property value="score" />
				</div>
				
				<div class="form-group">
					<label for="realName" class="col-sm-1 control-label">Real Name</label>
					<s:property value="realName" />
				</div>
				
				<div class="form-group">
					<label for="creationDate" class="col-sm-1 control-label">Creation Date</label>
					<s:property value="creationDate" />
				</div>
				
				<div>
				    <img height="250" width="250" src="/images/collection/<s:property value="image.name" />" class="img-rounded" alt="Rounded Image">
				
				    <img height="250" width="250" src="/images/collection/large/<s:property value="image.name" />" class="img-thumbnail" alt="Circular Image">
				
				    <img  src="/images/collection/thumbnail/<s:property value="image.name" />" class="img-thumbnail" alt="Thumbnail Image">
				</div>
							
			</fieldset>
		</div>
		<div class="panel-footer">
			<!--<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>-->
		</div>
	</div>

</s:form>
