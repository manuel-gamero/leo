<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="box" id="order-summary">
	<div class="box-header">
		<h3><s:text name="bolsos.template.shoppingcart.summary.title"/></h3>
	</div>
	<p class="text-muted"><s:text name="bolsos.template.shoppingcart.summary.paragraph1"/></p>

	<div class="table-responsive">
		<table class="table">
			<tbody>
				<tr>
					<td><s:text name="bolsos.template.shoppingcart.summary.table.column.subtotal"/></td>
					<th><s:property value="subTotalShoppingCart" /></th>
				</tr>
				<tr>
					<td><s:text name="bolsos.template.shoppingcart.summary.table.column.shipping"/></td>
					<th><s:property value="shippingPriceShoppingCart" /></th>
				</tr>
				<tr>
					<td><s:text name="bolsos.template.shoppingcart.summary.table.column.extra"/></td>
					<th><s:property value="extraTextPriceShoppingCart" /></th>
				</tr>
				<tr>
					<td><s:text name="bolsos.template.shoppingcart.summary.table.column.tax"/></td>
					<th><s:property value="taxesShoppingCart" /></th>
				</tr>
				<s:if test="#thereIsCoupon">
					<tr class="hover-coupon">
						<s:if test="%{promotion}">
							<s:if test="%{totalDiscountCouponShoppingCart != '0.00'}">
								<td><s:text name="bolsos.template.shoppingcart.summary.table.column.promotion"/></td>
							</s:if>
						</s:if>
						<s:else>
							<td><s:text name="bolsos.template.shoppingcart.summary.table.column.coupon"/></td>
						</s:else>
						<th><s:property value="totalDiscountCouponShoppingCart" /></th>
					</tr>
				</s:if>
				<tr class="total">
					<td><s:text name="bolsos.template.shoppingcart.summary.table.column.total"/></td>
					<th><s:property value="totalShoppingCart" /></th>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<s:if test="%{couponTextDescription != ''}">
	<script type="text/javascript">
		$(".hover-coupon").popover({
			title: "<s:property value='couponTextName'/>",
			html: true,
			delay: { "show": 100, "hide": 100 },
			trigger: 'hover',
			placement: 'auto',
			content: function () {
				return "${couponTextDescription}";
			}
		});
	</script>
</s:if>