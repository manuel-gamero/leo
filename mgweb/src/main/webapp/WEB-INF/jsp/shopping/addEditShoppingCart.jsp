 <%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveShoppingCart" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<!--<s:textfield name="user.id" label="User id: " />-->
	<s:hidden name="id" />
	<s:hidden name="site" value="ADMIN"/>
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit ShoppingCart</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<div class="form-group">
					<label for="reference" class="col-sm-1 control-label">Reference</label>
					<s:textfield id="reference" name="reference" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="login" class="col-sm-1 control-label">User Name</label>
					<s:textfield id="login" name="users.login" class="form-control" disabled="true" />
				</div>
				<div class="form-group">
					<label for="login" class="col-sm-1 control-label">Method Shipping</label>
					<s:textfield id="login" name="methodShipping.code" class="form-control"/>
				</div>
				<s:if test="%{transaction != null}">
					<div class="form-group">
						<label for="login" class="col-sm-1 control-label">Transaction</label>
						<s:textfield id="login" name="transaction.codeRetour" class="form-control"
							disabled="true"/>
					</div>
				</s:if>
				<div class="form-group">
					<label for="statusCode" class="col-sm-1 control-label">Status</label>
					<s:select id="statusCode" name="statusCode"
						cssStyle="width:156px;" required="true" listKey="code"
						listValue="%{code}"
						list="%{@com.mg.web.WebConstants@ALL_SHOPPINGCART_STATUS}"
						class="form-control" />
				</div>
				<div class="form-group">
					<label for="total" class="col-sm-1 control-label">Total</label>
					<s:textfield id="total" name="total" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="shippingFees" class="col-sm-1 control-label">Shipping Fees</label>
					<s:textfield id="shippingFees" name="shippingFees" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="taxes" class="col-sm-1 control-label">Taxes</label>
					<s:textfield id="taxes" name="taxes" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="shippingAddress" class="col-sm-1 control-label">Shipping Address</label>
					<s:textfield id="shippingAddress" name="shippingAddressId" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="trackNumber" class="col-sm-1 control-label">Track Number</label>
					<s:textfield id="trackNumber" name="trackNumber" class="form-control" />
				</div>
				<div class="form-group">
					<label for="paymentMethod" class="col-sm-1 control-label">Payment Method</label>
					<s:textfield id="paymentMethod" name="paymentMethod" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="purchaseDate" class="col-sm-1 control-label">Purchase Date</label>
					<s:textfield id="purchaseDate" name="purchaseDate" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="sendDate" class="col-sm-1 control-label">Send Date</label>
					<s:textfield id="sendDate" name="sendDate" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="shippingDate" class="col-sm-1 control-label">Shipping Date</label>
					<s:textfield id="shippingDate" name="shippingDate" class="form-control" />
				</div>
				<div class="form-group">
					<label for="creationDate" class="col-sm-1 control-label">Creation Date</label>
					<s:textfield id="creationDate" name="creationDate" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="comment" class="col-sm-1 control-label">Comment</label>
					<s:textfield id="comment" name="comment" class="form-control" />
				</div>
				<div class="form-group">
					<label for="commentUser" class="col-sm-1 control-label">Comment User</label>
					<s:textfield id="commentUser" name="commentUser" class="form-control" disabled="true"/>
				</div>
				<div class="form-group">
					<label for="currency" class="col-sm-1 control-label">Currency</label>
					<s:textfield id="currency" name="currency" class="form-control" disabled="true"/>
				</div>
				
				<div id="shoppingCartItemsData">
					<table
						class="table table-bordered table table-hover table table-condensed"
						data-search="true">
						<thead class="gray_header">
							<tr>
								<th data-field="id" style="width: 10px;"><span class="bold">id</span></th>
								<th data-field="code" style="width: 60px;"><span 
									class="bold">Name</span></th>
								<th data-field="code" style="width: 60px;"><span 
									class="bold">Image</span></th>
								<th data-field="name" style="width: 20px;"><span 
									class="bold">Status</span></th>
								<th data-field="name" style="width: 20px;"><span 
									class="bold">Price</span></th>
								<th data-field="creationdate" style="width: 20px;"><span 
									class="bold">Quantity</span></th>
								<th data-field="creationDate" style="width: 60px;"><span 
									class="bold">Creation Date</span></th>
							</tr>
						</thead>
						<s:if test="%{shoppingCartItems != null && shoppingCartItems.size > 0}">
							<tbody>
								<s:iterator value="shoppingCartItems" status="status">
									<tr id="tr_<s:property value="id"/>">
										<td style="text-align: center;"><s:property value="id" /></td>
										<td style="text-align: center;"><s:property value="productName" /></td>
										<td style="text-align: center;">
											<s:set var="cciSet" value='%{""}' />
											<img class="shopping-cart-ico"
											src="${tmppath}<s:property value="nameImage" />" />
											<p><s:property value="text" /></p>
											<p><s:property value="font" /></p>
											<p><s:property value="size" /></p>
											<p><s:property value="color" /></p>
										</td>
										<td style="text-align: center;"><s:property value="statusCode" /></td>
										<td style="text-align: center;"><s:property value="price" /></td>
										<td style="text-align: center;"><s:property	value="quantity" /></td>
										<td style="text-align: center;"><s:property	value="creationDate" /></td>
										<%-- <td style="text-align: center;">
											<a id="edit" href="aeTaxes.action?id=<s:property value="id"/>">Edit</a>
											<a id="remove" href="removeTaxes.action?id=<s:property value="id"/>">Remove</a>
										</td> --%>
									</tr>
								</s:iterator>
	
								<s:else>
									<tr></tr>
								</s:else>
							</tbody>
						</s:if>
					</table>
	
				</div>
			
			</fieldset>
		</div>
		<div class="panel-footer">
			<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>
		</div>
	</div>


</s:form>
