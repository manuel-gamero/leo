<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="form-group">
	<div class="row">
		<div class="col-md-1">
			<label for="login" class="control-label"> <s:text name="bolsos.signin.login" />
			</label>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3 has-feedback">
			<div class="input-group">
				<span id="userico" aria-hidden="true" class="left-icon glyphicon glyphicon-user input-group-addon"></span>
				<s:textfield id="username" name="username" type="text" placeholder="%{getText('bolsos.signin.login')}" required=""
					autofocus="true" class="form-control user-field" data-remote="/user/validateLogin"
					data-error="%{getText('bolsos.signin.error.label.user')}" aria-describedby="userico" cssStyle="width: 250px;" />
			</div>
			<span aria-hidden="true" class="glyphicon form-control-feedback"></span> <span class="sr-only" id="username">(success)</span>
			<small class="help-block with-errors"></small>

		</div>
	</div>

</div>

<div class="form-group">
	<div class="row">
		<div class="col-md-1">
			<label for="password" class="control-label"> <s:text name="bolsos.signin.password" />
			</label>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3 has-feedback">
			<div class="input-group">
				<span aria-hidden="true" class="left-icon glyphicon glyphicon-lock input-group-addon"></span>
				<s:password id="password" name="password" type="password" placeholder="%{getText('bolsos.signin.password')}"
					required="" data-remote="/user/validatePassword" data-error="%{getText('bolsos.signin.error.label.user')}"
					class="form-control user-field" cssStyle="width: 250px;" />
			</div>
			<span aria-hidden="true" class="glyphicon form-control-feedback"></span> <span class="sr-only" id="password">(success)</span>
			<small class="help-block with-errors"></small>
		</div>
	</div>
</div>
<div class="text-center">
	<button type="submit" class="btn btn-primary">
		<i class="fa fa-sign-in"></i> 
		<s:text name="bolsos.template.login.button.login"/>
	</button>
</div>