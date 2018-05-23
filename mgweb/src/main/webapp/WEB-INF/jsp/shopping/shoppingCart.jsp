<%@ taglib uri="/struts-tags" prefix="s"%>
<div id="content">
	<div class="container">

		<div class="col-md-12">
			<ul class="breadcrumb">
				<li><a href='<s:text name="url.home"/>'><s:text name="bolsos.mainmenu.label.home" /></a></li>
				<li><s:text name="bolsos.shoppingcart" /></li>
			</ul>
		</div>

		<div class="col-md-9" id="basket">

			<div class="box">

				<s:form method="post" action="%{getText('url.shoppingcart.address')}" namespace="/">
					<h1>
						<s:text name="bolsos.shoppingcart" />
					</h1>
					<p class="text-muted">
						<s:text name="bolsos.shopping.cart.label.contentcart1" />
						${shoppingCartCount}
						<s:text name="bolsos.shopping.cart.label.contentcart2" />
					</p>
					<div class="table-responsive">
						<table class="table">
							<thead>
								<tr>
									<th colspan="2"><s:text name="bolsos.shopping.cart.table.column.product" /></th>
									<th><s:text name="bolsos.shopping.cart.table.column.quantity" /></th>
									<th><s:text name="bolsos.shopping.cart.table.column.price" /></th>
									<th><s:text name="bolsos.shopping.cart.table.column.discount" /></th>
									<th><s:text name="bolsos.shopping.cart.table.column.total" /></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="shoppingCartList" status="status">
									<tr id="tr_<s:property value="product.id"/>">
										<td>
											<h4>
												<a href='<s:text name="url.product"/>/<s:property value="product.url"/>'><s:property
														value="product.name" /></a>
											</h4> <s:iterator value="itemComponentDTOs" status="status" var="cc">
												<p class="desc-item">
													<strong><s:text
															name='bolsos.code.customcomponent.%{#cc.customComponentCollectionDTO.customComponent.name}' />:</strong>
													<s:property value="customComponentCollectionDTO.name" />
												</p>
											</s:iterator>
										</td>
										<td>
											<div class="center-block">
												<s:if test="%{product.customProduct}">
													<a href="${tmppath}<s:property value="nameImage" />" class="modalImage"
														title='<s:property value="product.name" />'
														data-link='<s:text name="url.product"/>/<s:property value="product.url"/>'> <img
														src="${tmppath}<s:property value="nameImage" />" alt="<s:property value="product.name" />">
													</a>
												</s:if>
												<s:else>
													<a href="${productpath}<s:property value="nameImage" />" class="modalImage"
														title='<s:property value="product.name" />'
														data-link='<s:text name="url.product"/>/<s:property value="product.url"/>'> <img
														src="${productpath}<s:property value="nameImage" />" alt="<s:property value="product.name" />">
													</a>
												</s:else>
											</div> <s:if test="%{text != null and text != ''}">
												<p class="desc-item">
													<strong><s:text name='bolsos.shopping.cart.label.text' />:</strong>
													<s:property value="text" />
												</p>

												<s:if test="%{font != null and font != ''}">
													<p class="desc-item">
														<strong><s:text name='bolsos.shopping.cart.label.font' />:</strong>
														<s:property value="font" />
													</p>
												</s:if>
												<s:if test="%{size != null and size != ''}">
													<p class="desc-item">
														<strong><s:text name='bolsos.shopping.cart.label.size' />:</strong>
														<s:property value="size" />
													</p>
												</s:if>
												<s:if test="%{color != null and color != ''}">
													<p class="desc-item">
														<strong><s:text name='bolsos.shopping.cart.label.color' />:</strong>
														<s:property value="color" />
													</p>
												</s:if>
											</s:if>
										</td>
										<td>1</td>
										<td><s:property value="product.msrp" /></td>
										<td><s:property value="discount" /></td>
										<td><s:property value="total" /></td>
										<td>
											<s:token /> 
											<a href="<s:url action="removeItem" namespace="/">
														<s:param name="index">${status.index}</s:param>
													  	<s:param name="struts.token.name" value="'token'"/>
														<s:param name="token" value="#session['struts.tokens.token']"/>
													</s:url>">
													<i class="fa fa-trash-o"></i>
											</a>
											<a href="shoppingcart/updateItem/${status.index}">
												<span class="glyphicon glyphicon-pencil"></span>
											</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
							<tfoot>
								<tr>
									<th colspan="5"><s:text name="bolsos.shopping.cart.label.total" /></th>
									<th><s:property value="subTotalShoppingCart" /></th>
								</tr>
							</tfoot>
						</table>
					</div>

					<!-- Start modal to display the image -->
					<s:include value="../template/popupImageTemplate.jsp" />
					<!-- End modal to display the image -->

					<!-- /.table-responsive -->

					<div class="box-footer">
						<div class="pull-left">
							<a href='<s:text name="url.collections"/>' class="btn btn-default"><i class="fa fa-chevron-left"></i> <s:text
									name="bolsos.shopping.cart.label.continue" /> </a>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-primary">
								<s:text name="bolsos.shopping.cart.label.checkout" />
								<i class="fa fa-chevron-right"></i>
							</button>
						</div>
					</div>
				</s:form>
			</div>
			<!-- /.box -->


			<%-- <div class="row same-height-row">
				<div class="col-md-3 col-sm-6">
					<div class="box same-height">
						<h3><s:text name="bolsos.shopping.cart.label.maylike"/></h3>
					</div>
				</div>

				<div class="col-md-3 col-sm-6">
					<div class="product same-height">
						<div class="flip-container">
							<div class="flipper">
								<div class="front">
									<a href="detail.html"> <img src="img/product2.jpg" alt=""
										class="img-responsive">
									</a>
								</div>
								<div class="back">
									<a href="detail.html"> <img src="img/product2_2.jpg" alt=""
										class="img-responsive">
									</a>
								</div>
							</div>
						</div>
						<a href="detail.html" class="invisible"> <img
							src="img/product2.jpg" alt="" class="img-responsive">
						</a>
						<div class="text">
							<h3>Fur coat</h3>
							<p class="price">$143</p>
						</div>
					</div>
					<!-- /.product -->
				</div>

				<div class="col-md-3 col-sm-6">
					<div class="product same-height">
						<div class="flip-container">
							<div class="flipper">
								<div class="front">
									<a href="detail.html"> <img src="img/product1.jpg" alt=""
										class="img-responsive">
									</a>
								</div>
								<div class="back">
									<a href="detail.html"> <img src="img/product1_2.jpg" alt=""
										class="img-responsive">
									</a>
								</div>
							</div>
						</div>
						<a href="detail.html" class="invisible"> <img
							src="img/product1.jpg" alt="" class="img-responsive">
						</a>
						<div class="text">
							<h3>Fur coat</h3>
							<p class="price">$143</p>
						</div>
					</div>
					<!-- /.product -->
				</div>


				<div class="col-md-3 col-sm-6">
					<div class="product same-height">
						<div class="flip-container">
							<div class="flipper">
								<div class="front">
									<a href="detail.html"> <img src="img/product3.jpg" alt=""
										class="img-responsive">
									</a>
								</div>
								<div class="back">
									<a href="detail.html"> <img src="img/product3_2.jpg" alt=""
										class="img-responsive">
									</a>
								</div>
							</div>
						</div>
						<a href="detail.html" class="invisible"> <img
							src="img/product3.jpg" alt="" class="img-responsive">
						</a>
						<div class="text">
							<h3>Fur coat</h3>
							<p class="price">$143</p>

						</div>
					</div>
					<!-- /.product -->
				</div>
			</div> --%>
		</div>
		<!-- /.col-md-9 -->

		<div class="col-md-3">
			<s:include value="../template/shoppingCartSummarySectionTemplate.jsp"></s:include>

			<div class="box">
				<div class="box-header">
					<h4>
						<s:text name="bolsos.shopping.cart.coupon.title" />
					</h4>
				</div>
				<p class="text-muted">
					<s:text name="bolsos.shopping.cart.coupon.text" />
				</p>
				<s:if test="hasActionErrors()">
					<div class="error-coupon">
						<s:actionerror />
					</div>
				</s:if>
				<s:if test="%{couponText != null}">
					<p>
						<s:text name="%{getText(couponText)}" />
					</p>
				</s:if>
				<s:form method="post" action="coupon" namespace="/" validate="true" enctype="multipart/form-data"
					class="form-horizontal">
					<div class="input-group">
						<s:textfield class="form-control" name="couponName" />
						<span class="input-group-btn">
							<button class="btn btn-primary" type="submit">
								<i class="fa fa-gift"></i>
							</button>
						</span>
					</div>
					<!-- /input-group -->
				</s:form>
			</div>

		</div>
		<!-- /.col-md-3 -->

	</div>
	<!-- /.container -->
</div>
<!-- /#content -->
