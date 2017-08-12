 <%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveUser" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<!--<s:textfield name="user.id" label="User id: " />-->
	<s:hidden name="user.id" />
	<s:hidden name="user.creationDate" />
	<s:hidden name="site" value="ADMIN"/>
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit User</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<div class="form-group">
					<label for="login" class="col-sm-1 control-label">User Name</label>
					<s:textfield id="login" name="user.login" class="form-control"
						placeholder="User Name" />
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-1 control-label">Password</label>
					<s:textfield id="password" name="user.password" type="password"
						class="form-control" placeholder="Password" />
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-1 control-label">Email</label>
					<div class="col-sm-10">
						<s:textfield id="email" name="user.email" type="email"
							class="form-control" placeholder="Email" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">Active:</label>
					<div class="checkbox">
						<label> <s:checkbox name="user.active" label="Active:" />
						</label>
					</div>
				</div>
				<div class="form-group">
					<label for="typeCode" class="col-sm-1 control-label">Type</label>
					<s:select id="typeCode" name="user.typeCode"
						cssStyle="width:156px;" required="true" listKey="code"
						listValue="%{code}"
						list="%{@com.mg.web.WebConstants@ALL_USER_TYPE}"
						class="form-control" />
				</div>
				<div class="form-group">
					<label for="statusCode" class="col-sm-1 control-label">Status</label>
					<s:select id="statusCode" name="user.statusCode"
						cssStyle="width:156px;" required="true" listKey="code"
						listValue="%{code}"
						list="%{@com.mg.web.WebConstants@ALL_USER_STATUS}"
						class="form-control" />
				</div>

				<s:textfield name="user.mgUserAddresses.street"
					label="street Name: " />
				<s:textfield name="user.mgUserAddresses.apartment"
					label="apartment Name: " />
				<s:textfield name="user.mgUserAddresses.postCode"
					label="postCode Name: " />
				<s:textfield name="user.mgUserAddresses.province"
					label="province Name: " />
				<s:textfield name="user.mgUserAddresses.country"
					label="country Name: " />
				<s:select id="mgUserAddresses.typeCode"
					name="user.mgUserAddresses.typeCode" cssStyle="width:156px;"
					required="true" headerKey="-1" headerValue="--Please Select--"
					listKey="code" listValue="%{code}"
					list="%{@com.mg.web.WebConstants@ALL_USER_ADDRESS_TYPE}" />


			</fieldset>
		</div>
		<div class="panel-footer">
			<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>
		</div>
	</div>


</s:form>
