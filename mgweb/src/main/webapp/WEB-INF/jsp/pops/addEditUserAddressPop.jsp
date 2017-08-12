<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="formAdrressPopup" action="saveUserAddress" namespace="/" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">
	<s:hidden name="id" />
	<s:hidden name="type" />
	<s:hidden name="referer" />

	<s:token></s:token>
	<div class="modal-header popup-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title popup-title"><s:property value="title"/></h4>
	</div>
	<div class="modal-body">
		<fieldset>
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label"> <strong> <s:text
								name="bolsos.shoppingcart.address.popup.label.name" />
					</strong>
					</label>
				</div>
				<div class="row">
					<div class="col-md-5 has-feedback">
						<s:textfield id="firstname" name="userAddress.firstName" type="text"
							class="form-control"
							placeholder="%{getText('bolsos.createaccount.label.firstname')}" />
						<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
						<small class="help-block with-errors"></small>
					</div>
	
					<div class="col-md-5 has-feedback">
						<s:textfield id="lastname" name="userAddress.lastName"
							type="text" class="form-control"
							placeholder="%{getText('bolsos.createaccount.label.lastname')}" />
						<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
						<small class="help-block with-errors"></small>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="row">
					<label class="col-sm-1 control-label">
						<strong>
							<s:text name="bolsos.createaccount.label.address"/>
						</strong>
					</label>
				</div>
				<div class="row">
					<div class="col-md-5 has-feedback">
						<s:textfield id="address" name="userAddress.street" type="text"
							class="form-control" placeholder="%{getText('bolsos.createaccount.label.street')}" />
						<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
				    	<small class="help-block with-errors"></small>
					</div>
					
					<div class="col-md-3">
						<s:textfield id="appartment" name="userAddress.apartment" type="text"
							class="form-control col-md-offset-1" placeholder="%{getText('bolsos.createaccount.label.apartment')}" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-md-5 has-feedback">
						<s:textfield id="city" name="userAddress.city" type="text"
							class="form-control" placeholder="%{getText('bolsos.createaccount.label.city')}" 
							data-error="%{getText('bolsos.createaccount.error.label.mandatory')}" required=""/>
						<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
				    	<small class="help-block with-errors"></small>
					</div>
					<div class="col-md-5 has-feedback">
						<s:textfield id="postCode" name="userAddress.postCode" type="text"
							class="form-control" placeholder="%{getText('bolsos.createaccount.label.postCode')}" />
						<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
				    	<small class="help-block with-errors"></small>
					</div>
				</div>
			</div>	
			<div class="form-group">	
				<div class="row">   
					<div class="col-md-2">
						<label for="country" class="control-label"><s:text name="bolsos.createaccount.label.country"/></label>
					</div>
					<div class="col-md-3 has-feedback">  
						<s:select id="countryPopup" cssStyle="width:156px;" name ="userAddress.country" class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true"/>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">	   
					<div class="col-md-2">
						<label for="province" class="control-label"><s:text name="bolsos.createaccount.label.province"/></label>
					</div>
					<div class=" col-md-3 has-feedback">  
						<s:select id="provincePopup"  cssStyle="width:156px;" name ="userAddress.province" class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true"/>
					</div>
				</div>
			</div>
		</fieldset>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<s:submit value="Save" class="btn btn-primary"></s:submit>
	</div>
</s:form>


<script type="text/javascript">
$(document).ready(function () {
	
	$.getJSON("/ajax/countryList", function(a) {
	      var options = '';

	      for ( var i = 0; i < a.list.length; i++) {
	          //alert(j.items[i].key);
	          options += '<option value="' + a.list[i].key + '">'
	                  + a.list[i].value + '</option>';
	      }
	      $("select#countryPopup").html(options);
	      
	      if('<s:property value="userAddress.country"/>' != ''){
	    	  $("select#countryPopup").val('<s:property value="userAddress.country.code"/>');
	    	  if('<s:property value="userAddress.province"/>' != ''){
			      	$("select#provincePopup").val('<s:property value="userAddress.province"/>');
			  }
	      }
	      $("select#countryPopup").change();
	  });
	
	$("select#countryPopup").change(function(){
		 $.getJSON("/ajax/provinceList", { code: $("select#countryPopup option:selected").val() }, function(a) {
		      var options = '';
		              
		      for ( var i = 0; i < a.list.length; i++) {
		    	  options += '<option value="' + a.list[i].key + '">'
                  + a.list[i].value + '</option>';
		      }
		      $("select#provincePopup").html(options);
		      
		  });
	});
	
});
</script>
