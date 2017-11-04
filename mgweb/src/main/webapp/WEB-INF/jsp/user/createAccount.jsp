<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">
		<div class="col-md-12">

			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.createaccount.title2"/></li>
			</ul>
		</div>
		<div class="col-md-12">
			<div class="box">
				<h1>
					<s:text name="bolsos.createaccount.title" />
				</h1>
				<p class="lead"><s:text name="bolsos.shoppingcart.authorization.paragraph1"/></p>
				<p><s:text name="bolsos.shoppingcart.authorization.paragraph2"/></p>
				<p class="text-muted">
					<s:text name="bolsos.shoppingcart.authorization.paragraph3"/>
				</p>
				<s:form id="form" action="registerUser" namespace="/user" method="post"
					validate="true" enctype="multipart/form-data"
					class="form-horizontal" data-toggle="validator">
					<s:token></s:token>
					<div class="form-group">
						<div class="row">
							<div class="col-md-3">
								<label for="firstName" class="control-label"> <s:text
										name="bolsos.createaccount.label.fullname" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4 has-feedback">
								<s:textfield id="firstName" name="user.firstName"
									class="required form-control" type="text"
									placeholder="%{getText('bolsos.createaccount.label.firstname')}"
									data-error="%{getText('bolsos.createaccount.error.label.mandatory')}"
									required="" aria-describedby="inputFirstName" autofocus="true" />
								<span aria-hidden="true"
									class="name-feedback glyphicon form-control-feedback"></span> <span
									class="sr-only" id="inputFirstName">(success)</span> <small
									class="help-block with-errors"></small>
							</div>
							<div class="col-md-4 has-feedback">
								<s:textfield id="lastName" name="user.lastName"
									class="form-control col-md-offset-1" type="text"
									placeholder="%{getText('bolsos.createaccount.label.lastname')}"
									data-error="%{getText('bolsos.createaccount.error.label.mandatory')}"
									required="" aria-describedby="inputLastName" />
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
								<span class="sr-only" id="inputLastName">(success)</span> <small
									class="help-block with-errors"></small>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-1">
								<label for="email" class="control-label">
									<s:text name="bolsos.createaccount.label.email"/>
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-9 has-feedback">
								<s:textfield id="email" name="user.email" type="email"
									class="form-control" placeholder="%{getText('bolsos.createaccount.label.email')}" 
									data-error="%{getText('bolsos.createaccount.error.label.email')}" required="" 
									aria-describedby="email" data-remote="/user/validateEmail"/>
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
								<span class="sr-only" id="email">(success)</span>
								<small class="help-block with-errors"></small>
							</div>
							
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-3">
								<label for="password" class="control-label">
									<s:text name="bolsos.createaccount.label.password"/>
								</label>
							</div>
						</div>
						<div class="row">
						    <div class="col-md-5 has-feedback">
						      	<s:textfield type="password" name="user.password" data-minlength="6" class="form-control" id="inputPassword" 
						      	placeholder="%{getText('bolsos.createaccount.label.password')}" 
						      	data-error="%{getText('bolsos.createaccount.error.label.mandatory')}" required="" />
						      	<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
								<small class="help-block with-errors"><s:text name="bolsos.createaccount.error.label.password"/></small>
						    </div>
						    <div class="col-md-5 has-feedback">
						      	<s:textfield type="password" class="form-control" id="inputPasswordConfirm" 
						      	data-match="#inputPassword" data-match-error="%{getText('bolsos.createaccount.error.label.nomatch')}"
						      	data-error="%{getText('bolsos.createaccount.error.label.mandatory')}" 
						      	placeholder="%{getText('bolsos.createaccount.label.confirm')}" required="" />
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
									class="form-control" placeholder="%{getText('bolsos.createaccount.label.street')}" 
									data-error="%{getText('bolsos.createaccount.error.label.mandatory')}" required=""/>
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
									class="form-control" placeholder="%{getText('bolsos.createaccount.label.postCode')}" 
									data-error="%{getText('bolsos.createaccount.error.label.mandatory')}" required=""/>
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span>
						    	<small class="help-block with-errors"></small>
							</div>
						</div>
					</div>	
					<div class="form-group">
						<div class="row">
							<div class="col-md-3">
								<s:textfield id="phone" name="user.phone" class="required form-control" type="text"
									placeholder="%{getText('bolsos.createaccount.label.phone')}" aria-describedby="phone"/>
							</div>
						</div>
					</div>
					<div class="form-group">	
						<div class="row">   
							<div class="col-md-2">
								<label for="country" class="control-label"><s:text name="bolsos.createaccount.label.country"/></label>
							</div>
							<div class="col-md-3 has-feedback">  
								<s:select id="country" cssStyle="width:156px;" name ="userAddress.country" 
								class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true" 
								data-error="%{getText('bolsos.createaccount.error.label.mandatory')}" required=""/>
							</div>
						</div>
					</div>
					<div class="form-group provice">
						<div class="row">	   
							<div class="col-md-2">
								<label for="province" class="control-label"><s:text name="bolsos.createaccount.label.province"/></label>
							</div>
							<div class=" col-md-3 has-feedback">  
								<s:select id="province"  cssStyle="width:156px;" name ="userAddress.province" 
								class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true" 
								data-error="%{getText('bolsos.createaccount.error.label.mandatory')}" required=""/>
							</div>
						</div>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-user-md"></i> 
							<s:text name="bolsos.createaccount.button.register"/>
						</button>
					</div>
				</s:form>
			</div>
		</div>

	</div>
	<!-- /.container -->
</div>
<!-- /#content -->

<script type="text/javascript">
$(document).ready(function () {
	
	$.getJSON("/ajax/countryList", function(a) {
	      var options = '';

	      for ( var i = 0; i < a.list.length; i++) {
	          //alert(j.items[i].key);
	          options += '<option value="' + a.list[i].key + '">'
	                  + a.list[i].value + '</option>';
	      }
	      $("select#country").html(options);
	      $("#country").change();
	  });
	
	$("#country").change(function(){
		 console.log('$("#country").val() :' + $("#country").val());
		 $.getJSON("/ajax/provinceList", { code: $("#country").val() }, function(a) {
		      var options = '';
		              
		      for ( var i = 0; i < a.list.length; i++) {
		    	  options += '<option value="' + a.list[i].key + '">'
                  + a.list[i].value + '</option>';
		      }
		      $("select#province").html(options);
		  });
		 
			 
	});
	
});
</script>
