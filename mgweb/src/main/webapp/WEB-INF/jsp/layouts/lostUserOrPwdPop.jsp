<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- Modal HTML -->

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">
		<s:text name="bolsos.lostorpwd.title"></s:text>
	</h4>
</div>
<div class="modal-body">
	<s:form namespace="/user" action="lostUserOrPwd" id="form"
		method="post" validate="true">
		<fieldset>
			<s:token />
			<div class="form-group">
				<label for="email" class="sr-only"> 
					<s:text name="bolsos.lostorpwd.label.mail" />
				</label>
				<div class="col-sm-10">
					<s:textfield id="email" name="email" type="email"
						class="form-control"
						placeholder="%{getText('bolsos.lostorpwd.label.mail')}" />
				</div>
			</div>

			<button class="btn btn-lg btn-primary" type="submit">
				<s:text name="bolsos.lostorpwd.button.send" />
			</button>
		</fieldset>
	</s:form>
</div>
