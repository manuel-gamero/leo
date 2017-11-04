
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="content">
	<div class="container">

		<div class="col-md-12">

			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home"/></a></li>
				<li><s:text name="bolsos.account.title"/></li>
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
				<h1><s:text name="bolsos.account.title"/></h1>
				<p class="lead">
					<s:text name="bolsos.account.paragraph1"/>
				</p>

				<h3><s:text name="bolsos.account.label.detail"/></h3>

				<s:form id="form" action="saveUser" namespace="/" method="post"
					validate="true" enctype="multipart/form-data"
					class="form-horizontal" data-toggle="validator">
					<s:token></s:token>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="login"> <strong> <s:text
										name="bolsos.account.label.login" /></strong>
								</label>
								<s:property value="login" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><strong><s:text name="bolsos.account.table.column.firstname"/></strong></label>
								<s:property value="firstName" />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><strong><s:text name="bolsos.account.table.column.lastname"/></strong></label>
								<s:property value="lastName" />
							</div>
						</div>
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="email"> <strong><s:text
										name="bolsos.createaccount.label.email" /></strong>
								</label>
								<s:property value="email" />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="phone"> <strong><s:text
										name="bolsos.createaccount.label.phone" /></strong>
								</label>
								<s:property value="phone" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6 col-md-3">
							<div class="form-group">
								<label for="city"> <strong><s:text
										name="bolsos.account.label.lastlogin" /></strong>
								</label>
								<s:property value="lastLogindate" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 text-center">
							<a id="create" class="btn btn-primary" href='<s:text name="url.user.modify"/>'
								role="button"> <i class="fa fa-save"></i>
								<s:text name="bolsos.account.table.link.modify" /></a>
							<!-- <button type="submit" class="btn btn-primary">
								<i class="fa fa-save"></i> Save changes
							</button> -->

						</div>
					</div>
					
					<hr>

					<h3><s:text name="bolsos.account.label.shipping"/></h3>
					<div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<th><s:text name="bolsos.account.table.column.name" /></th>
									<th><s:text name="bolsos.account.table.column.street" /></th>
									<th><s:text	name="bolsos.account.table.column.aparment" /></th>
									<th><s:text	name="bolsos.account.table.column.city" /></th>
									<th><s:text	name="bolsos.account.table.column.codepost" /></th>
									<th><s:text	name="bolsos.account.table.column.province" /></th>
									<th><s:text	name="bolsos.account.table.column.country" /></th>
									<th><s:text	name="bolsos.account.table.column.action" /></th>
								</tr>
							</thead>
							<s:if test="%{userAddressDTOList != null && userAddressDTOList.size > 0}">
								<tbody>
									<s:iterator value="userAddressDTOList" status="status">
										<tr id="tr_<s:property value="id"/>">
											<th><s:property	value="lastName" />, <s:property value="firstName" /></th>
											<td><s:property	value="street" /></td>
											<td><s:property	value="apartment" /></td>
											<td><s:property	value="city" /></td>
											<td><s:property	value="postCode" /></td>
											<td><s:text	name="%{province.getLocalizationKey()}" />, 
												<s:property	value="province" />
											</td>
											<td><s:text	name="%{country.getLocalizationKey()}" /></td>
											<td>
												<a id='edit_<s:property value="id"/>' href="#" > 
													<i class="fa fa-save"></i>
												</a> 												
												<s:token/>
												<a href="<s:url action="removeAddress" namespace="/">
													<s:param name="id"><s:property value="id"/></s:param>
												  	<s:param name="struts.token.name" value="'token'"/>
													<s:param name="token" value="#session['struts.tokens.token']"/>
												</s:url>">
												<i class="fa fa-trash-o"></i>
												</a>
												
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</s:if>
						</table>
					</div>
					<div class="row">
						<a id="aUserAddressShipping" class="btn btn-default popup"> <s:text
								name="bolsos.account.button.add.newaddress" />
						</a>
					</div>
				</s:form>
			</div>
		</div>

	</div>
	<!-- /.container -->
</div>
<!-- /#content -->


<script type="text/javascript">
$(document).ready(
	function() {

		$('[id^=edit]').click( function() {
			var id = $(this).attr('id').split('_')[1];
			$.get('/aeUserAddress.action?id=' + id + '&type=SHIPPING', function(data) {
				$(data).modal().on('hide.bs.modal', function (e) {
					$("#popupModal").remove();
				});
			});
		});

		$('[id^=aUserAddressShipping]').click(function() {
			$.get('/aeUserAddress.action?type=SHIPPING', function(data) {
				$(data).modal().on('hide.bs.modal', function (e) {
			  		$("#popupModal").remove();
				});
			});
		});

	});
</script>

