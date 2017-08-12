<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set var="active">
   ${param.active}
</s:set>

<div class="panel-heading">
	<h3 class="panel-title"><s:text name="bolsos.template.customsection.title"/></h3>
</div>

<div class="panel-body">

	<ul class="nav nav-pills nav-stacked">
		<s:if test="#active == 'orders'">
			<li class="active">
				<a href='<s:text name="url.shoppingcart.user"/>'>
					<i class="fa fa-list"></i> 
					<s:text name="bolsos.template.customsection.label.orders"/>
				</a>
			</li>
		</s:if>
		<s:else>
			<li>
				<a href='<s:text name="url.shoppingcart.user"/>'>
					<i class="fa fa-list"></i> 
					<s:text name="bolsos.template.customsection.label.orders"/>
				</a>
			</li>
		</s:else>
		<s:if test="#active == 'account'">
			<li class="active">
				<a href='<s:text name="url.user.account"/>'>
					<i class="fa fa-user"></i> 
					<s:text name="bolsos.template.customsection.label.account"/>
				</a>
			</li>
		</s:if>
		<s:else>
			<li>
				<a href='<s:text name="url.user.account"/>'>
					<i class="fa fa-user"></i> 
					<s:text name="bolsos.template.customsection.label.account"/>
				</a>
			</li>
		</s:else>
		<li class="divider"></li>
		<li>	
			<a href="/user/logout">
				<i class="fa fa-sign-out"></i> 
				<s:text name="bolsos.template.customsection.label.logout"/>
			</a>
		</li>
	</ul>
</div>