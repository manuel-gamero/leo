<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveCc" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden name="customComponent.id" />
	<s:hidden name="customComponent.creationDate" />
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Custom Component</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<div class="form-group">
					<label for="code" class="col-sm-1 control-label">Code</label>
					<s:textfield id="code" name="customComponent.code" class="form-control"
						placeholder="Code" />
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-1 control-label">Name</label>
					<s:textfield id="name" name="customComponent.name" class="form-control" placeholder="Name" />
				</div>
				<div class="form-group">
					<label for="typeCode" class="col-sm-1 control-label">Type</label>
					<s:select id="typeCode" name="customComponent.typeCode"
						cssStyle="width:156px;" required="true" listKey="code"
						listValue="%{code}"
						list="%{@com.mg.web.WebConstants@ALL_COMPONENT_TYPE}"
						class="form-control" />
				</div>
				
			</fieldset>
		</div>
		<div class="panel-footer">
			<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>
		</div>
	</div>

</s:form>
