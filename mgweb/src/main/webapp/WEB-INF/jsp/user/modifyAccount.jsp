
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">

			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home" /></a></li>
				<li><s:text name="bolsos.account.title" /></li>
			</ul>

		</div>

		<div class="col-md-3">
			<!-- *** CUSTOMER MENU *** -->
			<div class="panel panel-default sidebar-menu">
				<s:include value="../template/customerSectionTemplate.jsp">
					<s:param name="active" value="'account'" />
				</s:include>
			</div>
			<!-- /.col-md-3 -->

			<!-- *** CUSTOMER MENU END *** -->
		</div>

		<div class="col-md-9">
			<div class="box">
				<h1>
					<s:text name="bolsos.account.title" />
				</h1>
				<p class="lead">
					<s:text name="bolsos.account.paragraph1" />
				</p>
				<p class="text-muted">
					<s:text name="bolsos.account.paragraph2" />
				</p>
				<h3>
					<s:text name="bolsos.account.label.detail" />
				</h3>

				<s:form id="form" action="saveModifyUser" namespace="/" method="post" validate="true" enctype="multipart/form-data"
					class="form-horizontal" data-toggle="validator">
					<s:token></s:token>

					<div class="form-group">
						<div class="row">
							<div class="col-md-3">
								<label for="firstName" class="control-label"> <s:text name="bolsos.createaccount.label.fullname" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-5 has-feedback">
								<s:textfield id="firstName" name="firstName" class="required form-control" type="text"
									placeholder="%{getText('bolsos.createaccount.label.firstname')}" required="" aria-describedby="inputFirstName"
									autofocus="true" />
								<span aria-hidden="true" class="name-feedback glyphicon form-control-feedback"></span> <span class="sr-only"
									id="inputFirstName">(success)</span> <small class="help-block with-errors"></small>
							</div>
							<div class="col-md-5 has-feedback">
								<s:textfield id="lastName" name="lastName" class="form-control col-md-offset-1" type="text"
									placeholder="%{getText('bolsos.createaccount.label.lastname')}" required="" aria-describedby="inputLastName" />
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span> <span class="sr-only"
									id="inputLastName">(success)</span> <small class="help-block with-errors"></small>
							</div>
						</div>
					</div>

					<!-- /.row -->

					<div class="form-group">
						<div class="row">
							<div class="col-md-1">
								<label for="email" class="control-label"> <s:text name="bolsos.createaccount.label.email" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-9 has-feedback">
								<s:textfield id="email" name="email" type="email" class="form-control"
									placeholder="%{getText('bolsos.createaccount.label.email')}"
									data-error="%{getText('bolsos.createaccount.error.label.email')}" required="" aria-describedby="email"
									data-remote="/user/validateEmail" />
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span> <span class="sr-only" id="email">(success)</span>
								<small class="help-block with-errors"></small>
							</div>

						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-1">
								<label for="phone" class="control-label"> <s:text name="bolsos.createaccount.label.phone" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-5">
								<s:textfield id="phone" name="phone" class="required form-control" type="text"
									placeholder="%{getText('bolsos.createaccount.label.phone')}" aria-describedby="phone" />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 text-center">
							<button type="submit" class="btn btn-primary">
								<i class="fa fa-save"></i>
								<s:text name="bolsos.modifyuser.button.save.detail" />
							</button>
						</div>
					</div>
				</s:form>

				<h3>
					<s:text name="bolsos.account.label.change" />
				</h3>

				<s:form id="form" action="saveModifyPassword" namespace="/" method="post" validate="true" enctype="multipart/form-data"
					class="form-horizontal" data-toggle="validator">
					<s:token></s:token>
					
					<div class="form-group">
						<div class="row">
							<div class="col-md-2">
								<label for="password_old"> 
									<s:text name="bolsos.account.label.old" />
								</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-5">
								<s:textfield id="password_old" name="oldPassword" type="password" class="form-control"
										placeholder="%{getText('bolsos.account.label.old')}" required="" />
									<span aria-hidden="true" class="glyphicon form-control-feedback"></span> 
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-1">
								<label for="password" class="control-label"> <s:text name="bolsos.createaccount.label.password" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-5 has-feedback">
								<s:textfield id="password" name="password" type="password" class="form-control"
									placeholder="%{getText('bolsos.createaccount.label.password')}" data-minlength="6" required="" />
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span> <small class="help-block with-errors"><s:text
										name="bolsos.createaccount.error.label.password" /></small>
							</div>

							<div class="col-md-5 has-feedback">
								<s:textfield type="password" class="form-control col-md-offset-1" id="inputPasswordConfirm"
									data-match="#password" data-match-error="These don't match" placeholder="Confirm" required="" />
								<span aria-hidden="true" class="glyphicon form-control-feedback"></span> <small class="help-block with-errors"></small>
							</div>
						</div>
					</div>

					<!-- /.row -->
					<div class="row">
						<div class="col-sm-12 text-center">
							<button type="submit" class="btn btn-primary">
								<i class="fa fa-save"></i>
								<s:text name="bolsos.modifyuser.button.save.password" />
							</button>
						</div>
					</div>
					<!-- /.row -->

				</s:form>
			</div>
		</div>

	</div>
	<!-- /.container -->
</div>
<!-- /#content -->

<script type="text/javascript">
$(document).ready(function () {
	
	
});
</script>

