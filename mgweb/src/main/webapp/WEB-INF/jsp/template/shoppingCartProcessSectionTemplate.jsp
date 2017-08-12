<%@ taglib uri="/struts-tags" prefix="s"%>

<s:set var="active">
   ${param.active}
</s:set>

<ul class="nav nav-pills nav-justified">

	<s:if test="#active == 'address'">
		<li class="active"><a href="#"><i class="fa fa-map-marker"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.address"/></a></li>
	</s:if>
	<s:else>
		<li><a href='/<s:text name="url.shoppingcart.address"/>'><i class="fa fa-map-marker"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.address"/></a>
	</s:else>
	
	<s:if test="#active == 'delivery'">
		<li class="active"><a href="#"><i class="fa fa-truck"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.delivery"/></a></li>
	</s:if>
	<s:elseif test="#active != 'delivery' and (#active == 'review' or #active == 'payment')">
		<li><a href='/<s:text name="url.shoppingcart.shipping"/>'><i class="fa fa-truck"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.delivery"/></a></li>
	</s:elseif>
	<s:else>
		<li class="disabled"><a href="#"><i class="fa fa-truck"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.delivery"/></a></li>
	</s:else>
	
	<s:if test="%{isAllowPaymentMethod()}">
		<s:if test="#active == 'payment' ">
			<li class="active"><a href="#"><i class="fa fa-money"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.payment"/></a>
		</s:if>
		<s:elseif test="#active != 'payment' and #active == 'review'">
			<li><a href='/<s:text name="url.shoppingcart.payment"/>'><i class="fa fa-money"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.payment"/></a></li>
		</s:elseif>
		<s:else>
			<li class="disabled"><a href="#"><i class="fa fa-money"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.payment"/></a>
		</s:else>
	</s:if>
	
	<s:if test="#active == 'review'">
		<li class="active"><a href="#"><i class="fa fa-eye"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.review"/></a></li>
	</s:if>
	<s:else>
		<li class="disabled"><a href="#"><i class="fa fa-eye"></i><br><s:text name="bolsos.template.shoppingcart.proccess.label.review"/></a></li>
	</s:else>
	
	<!-- <li class="disabled"><a href="#"><i class="fa fa-money"></i><br>Payment Method</a></li> -->
</ul>