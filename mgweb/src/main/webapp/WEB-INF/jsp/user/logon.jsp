<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
  <div class="panel-heading">Sign in</div>
  <div class="panel-body">
     <div class="container">

      <s:form namespace="/user" action="login" id="form" method="post"
					validate="true" class="form-signin">
		<s:hidden id="site" name="site" value="ADMIN"/>
        <h2 class="form-signin-heading"></h2>
        <label for="inputEmail" class="sr-only">Login</label>
        <s:textfield id="username" name="username" type="text" placeholder="Login" required="true" autofocus="true" class="form-control"/>
        <label for="inputPassword" class="sr-only">Password</label>
        <s:password id="password" name="password" type="password" placeholder="Password" required="true" class="form-control"/>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary" type="submit">Sign in</button>
      </s:form>

    </div> <!-- /container -->
  </div>
</div>
