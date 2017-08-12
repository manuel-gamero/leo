<%@ taglib uri="/struts-tags" prefix="s" %>

<%-- custom style for the Sign In form --%>
<style type="text/css">
label {
	width:100px;
}
.vanadium-advice {
	margin-left: 121px;
}
input.vanadium-valid {
	background-image: none;
}
</style>
   
<div id="modal" class="unit" style="width: 510px;">
  <s:form namespace="/user" action="login.action" id="form" method="post" validate="true">
  
  <div class="pop_title">Sign In</div>
  <div id="globalAdvice" style="padding:15px 20px 15px 20px;display: none;" class="gray_bg"></div>
  <div id="formContainer" >
     <fieldset>
        <label for="username"><strong><s:text name="bolsos.common.text.short.username" /></strong></label>
        <s:textfield id="username" 
        			 name="username" 
        			 maxlength="14" 
        			 cssStyle="width:130px;" />
        <div id="usernameError" style="display:none"></div>
        <br />
        <label for="password"><strong><s:text name="bolsos.common.text.short.password" />:</strong></label>
        <s:password id="password" 
        			 name="password" 
        			 maxlength="12" 
        			 cssStyle="width:130px;"/>
        <div id="passwordError" style="display:none"></div>
        <br />
        <div class="on-2 columns same-height" style="margin:10px 20px 0px 120px;">
          <div class="fixed column">
          	<div class="vertical-center">
          		<s:submit name="submit" value="%{getText('bolsos.common.title.signIn')}" cssStyle="padding:3px 10px 3px 10px;"/>
          	</div>
          </div>
        <div class="fixed column">
          <div class="vertical-center pb20"><a id="lostPasswrd" href="lostUserOrPwd.action"><s:text name="bolsos.common.text.long.lostUserOrPwd" /></a></div></div>
        </div>
      </fieldset>
   
  </div>
  <div style="padding:15px 20px 15px 20px;" class="gray_bg"><strong><s:text name="bolsos.common.text.long.dontHaveAccount" /> <a id="createAccount" href="createProfilePop.action"><s:text name="bolsos.common.title.createProfile" /></a></strong></div>
 </s:form>
</div>

<script type="text/javascript">

jQuery(document).ready(function () {
	$("#modal").modal('show');
});	   
</script>