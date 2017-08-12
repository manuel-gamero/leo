<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="panel panel-primary">
	<div class="panel-heading collections-background-header">
		<h3 class="panel-title">
			<s:text name="bolsos.collections.title" />
		</h3>
	</div>
	<div class="panel-body">
		<div id="collectionList" class="fixed column"
			style="padding: 20px 0px 0px 0px;">
			<div class="blue f12 bold pb10" style="padding-bottom: 10px;"></div>
			<div id="collectionListData">
				<div class="col-xs-4 ol-xs-offset-1  col-md-4 col-md-offset-1">
					<div class="view view-first">
						<img src="${images}/_VLA8412_300_200.jpg">
						<div class="mask">
							<h2>Hover Style #1</h2>
							<p>A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart.</p>
							<a class="info" href="ourProduct.action?collectionId=12">Read More</a>
						</div>
					</div>
				</div>
				<div class="col-xs-4 ol-xs-offset-1  col-md-4 col-md-offset-1">
					<div class="view view-first">
						<img src="${images}/_VLA8304_300_200.jpg">
						<div class="mask">
							<h2>Hover Style #1</h2>
							<p>A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart.</p>
							<a class="info" href="ourProduct.action?collectionId=13">Read More</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

