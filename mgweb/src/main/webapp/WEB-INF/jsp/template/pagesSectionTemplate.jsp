<%@ taglib uri="/struts-tags" prefix="s"%>

<s:set var="active">
   ${param.active}
</s:set>

<div class="panel panel-default sidebar-menu">

	<div class="panel-heading">
		<h3 class="panel-title"><s:text name="bolsos.common.title.pages"/> </h3>
	</div>

	<div class="panel-body">
		<ul class="nav nav-pills nav-stacked">
			<s:if test="#active == 'company'">
				<li class="active"><a href='<s:text name="url.company"/>'><s:text name="bolsos.home.menu.company"/></a></li>
			</s:if>
			<s:else>
				<li><a href='<s:text name="url.company"/>'><s:text name="bolsos.home.menu.company"/></a></li>
			</s:else>
			<s:if test="#active == 'contact'">
				<li class="active"><a href='<s:text name="url.contact"/>'><s:text name="bolsos.home.menu.contact"/></a></li>
			</s:if>
			<s:else>
				<li><a href='<s:text name="url.contact"/>'><s:text name="bolsos.home.menu.contact"/></a></li>
			</s:else>
			<s:if test="#active == 'faq'">
				<li class="active"><a href='<s:text name="url.faq"/>'><s:text name="bolsos.home.menu.faq"/></a></li>
			</s:if>
			<s:else>
				<li><a href='<s:text name="url.faq"/>'><s:text name="bolsos.home.menu.faq"/></a></li>
			</s:else>
			<s:if test="#active == 'term'">
				<li class="active"><a href='<s:text name="url.termConditions"/>'><s:text name="bolsos.home.menu.termConditions"/></a></li>
			</s:if>
			<s:else>
				<li><a href='<s:text name="url.termConditions"/>'><s:text name="bolsos.home.menu.termConditions"/></a></li>
			</s:else>

		</ul>

	</div>
</div>