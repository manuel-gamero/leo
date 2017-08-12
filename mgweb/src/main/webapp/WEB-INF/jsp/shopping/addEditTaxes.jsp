<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveTaxes" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden name="id" />
	<s:hidden name="taxe.creationDate" />
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Taxes</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<%-- <div class="form-group">
					<label for="code" class="col-sm-1 control-label">Code</label>
					<s:textfield id="code" name="taxe.code" class="form-control"
						placeholder="Code" />
				</div> --%>
				<div class="row">	   
					<label for="country" class="col-sm-1 control-label">Country</label>
					<s:select id="country" cssStyle="width:156px;" class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true"/>
				</div>
				<div class="row">	   
					<label for="province" class="col-sm-1 control-label">Province</label>
					<s:select id="province"  cssStyle="width:156px;" name ="taxe.code" class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true"/>
				</div>
				<div class="form-group">
					<label for="description" class="col-sm-1 control-label">Description</label>
					<s:textfield id="description" name="taxe.description" class="form-control"
						placeholder="Description" />
				</div>
				<div class="form-group">
					<label for="tax" class="col-sm-1 control-label">Tax</label>
					<s:textfield id="tax" name="taxe.tax" class="form-control"
						placeholder="Tax" />
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
	
	$.getJSON("/ajax/countryList.action", function(a) {
	      var options = '<option value="' + "--Select--" + '">'
	              + "--Select--" + '</option>';

	      for ( var i = 0; i < a.list.length; i++) {
	          //alert(j.items[i].key);
	          options += '<option value="' + a.list[i].key + '">'
	                  + a.list[i].value + '</option>';
	      }
	      $("select#country").html(options);
	      $("select#country").val('<s:property value="country"/>');
	      console.log('$("#country").val() :' + $("#country").val());
	      if('<s:property value="country"/>' != ''){
	    	  $("#country").change();
	      }
	  });
	
	$("#country").change(function(){
		 console.log('$("#country").val() :' + $("#country").val());
		 $.getJSON("/ajax/provinceList.action", { code: $("#country").val() }, function(a) {
		      var options = '<option value="' + "--Select--" + '">'
		              + "--Select--" + '</option>';
		              
		      for ( var i = 0; i < a.list.length; i++) {
		    	  options += '<option value="' + a.list[i].key + '">'
                  + a.list[i].value + '</option>';
		      }
		      console.log("options : " + options);
		      $("select#province").html(options);
		      $("select#province").val('<s:property value="province"/>');
		  });
	});
	
});
</script>