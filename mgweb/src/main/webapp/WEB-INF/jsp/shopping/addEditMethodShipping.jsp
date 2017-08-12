<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveMethodShipping" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden id="id" name="id" />
	<s:hidden name="methodShipping.creationDate" />
	<s:hidden name="descrTranslationId" />
	<s:hidden name="nameTranslationId" />
	<s:hidden id="counterCurrency" value="%{priceEntrySet.size}" />
	
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Method Shipping</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<div class="form-group">
					<label for="code" class="col-sm-1 control-label">Code</label>
					<s:textfield id="code" name="methodShipping.code" class="form-control"
						placeholder="Code" />
				</div>
				<div class="form-group">
					<label for="nameEn" class="col-sm-1 control-label">nameEn</label>
					<s:textfield id="nameEn" name="nameEn" class="form-control"
						placeholder="nameEn" />
				</div>
				<div class="form-group">
					<label for="nameFr" class="col-sm-1 control-label">nameFr</label>
					<s:textfield id="nameFr" name="nameFr" class="form-control"
						placeholder="nameFr" />
				</div>
				<div class="form-group">
					<label for="descEn" class="col-sm-1 control-label">descEn</label>
					<s:textfield id="descEn" name="descEn" class="form-control"
						placeholder="descEn" />
				</div>
				<div class="form-group">
					<label for="descFr" class="col-sm-1 control-label">descFr</label>
					<s:textfield id="descFr" name="descFr" class="form-control"
						placeholder="descFr" />
				</div>
				
				<div class="form-group">
					<label for="statusCode" class="col-sm-2 control-label">Status</label>
					<s:select id="statusCode" name="statusCode"
						cssStyle="width:156px;" required="true" listKey="code"
						listValue="%{code}"
						list="%{@com.mg.web.WebConstants@ALL_METHOD_SHIPPING_STATUS}"
						class="form-control" />
				</div>

				<div class="form-group">
					<div class="row">
						<label for="cost" class="col-sm-2 control-label">Cost</label>
						<s:textfield id="cost" class="form-control"
							placeholder="Cost" type="number" cssStyle="width:156px;"/>
					</div>
					<div class="row">
						<label for="currency" class="col-sm-2 control-label">Currency</label>
						<s:select id="currency"
							cssStyle="width:156px;" required="true"
							list="%{@com.mg.web.WebConstants@ALL_CURRENCIES}"
							class="form-control" />
					</div>
					<div class="row">
						<a id="addCurrency" class="btn btn-default" role="button">Add</a>
					</div>
					<div class="row" >
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Id</th>
									<th>Price</th>
									<th>Currency</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="currencyBody">
								<s:if test="%{priceEntrySet != null && priceEntrySet.size > 0}">
									<s:iterator value="priceEntrySet" status="status" var="price">
										<tr id="pe_tr_<s:property value="id"/>">
											<td style="text-align: center;"><s:property value="id" /></td>
											<td style="text-align: center;"><s:property value="priceCurrency" /></td>
											<td style="text-align: center;"><s:property value="currency" /> </td>
											<td style="text-align: center;">
												<div class="form-group">
													<a id="deletePE_<s:property value="id"/>" class="btn btn-default" role="button">Delete</a>
												</div>
											</td>
										</tr>
									</s:iterator>
									<s:else>
										<tr></tr>
									</s:else>
								</s:if>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-md-1">
						<label for="password" class="control-label"> <s:text
								name="bolsos.createaccount.label.country" />
						</label>
					</div>
					<div class="form-group col-md-3 has-feedback">
						<s:select id="country" 
							name="country"
							label="%{getText('bolsos.createaccount.label.country')}"
							cssStyle="width:156px;" required="true" listKey="id"
							listValue="%{location}"
							list="#{}"
							class="col-md-1 form-control" />
					</div>
					
				</div>
			</fieldset>
		</div>
		<div class="panel-footer">
			<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>
		</div>
	</div>

</s:form>

<script type="text/javascript">
$(document).ready(function () {
	
	$.getJSON("/ajax/countryList.action", function(a) {
	      var options = '<option value="' + "--Select--" + '">'
	              + "--Select--" + '</option>';

	      for ( var i = 0; i < a.list.length; i++) {
	          //alert(j.items[i].key);
	          options += '<option value="' + a.list[i].key + '">'
	                  + a.list[i].value + '</option>';
	      }
	      $("select#country").html(options);
	      $("select#country").val('<s:property value="methodShipping.country"/>');
	      console.log('$("#country").val() :' + $("#country").val());
	  });
	
	$("a#addCurrency").click(function() {
		var counterCurrency = $("#counterCurrency").val();
	  	if(counterCurrency == ""){
	  		counterCurrency = 0;
	  	}
	  	var cid;
	  	var row;
	  	var cost = $("#cost").val();
		var currency = $("#currency option:selected" ).text();
		var ccost = '<td> <s:textfield  id="priceEntrySet[' + counterCurrency + '].priceCurrency" name="priceEntrySet[' + counterCurrency + '].price" value="' + cost + '" /></td>';
		var ccurrency = '<td> <s:textfield  id="priceEntrySet[' + counterCurrency + '].currency" name="priceEntrySet[' + counterCurrency + '].currency" value="' + currency + '" /></td>';
		
		console.log('counter: '  + counterCurrency);
		console.log('$("#id").val(): '  + $("#id").val());
		
		$.getJSON("/admin/ajax/addMethodShippingPrice.action", { price: $("#cost").val(), currency: $("#currency option:selected" ).text(), objId: $("#id").val()}, function(a) {
			if(a.id != null && a.id > 0){
				cid = '<td> <s:textfield  id="priceId" name="priceId" value="' + a.id + '" /></td>';
			}
			else{
				cid = '<td> </td>';
			}
			row = cid + ccost + ccurrency ;
			$("tbody#currencyBody").append('<tr>' + row + '</tr>');
			console.log('Row: '  + row);
		});
		
		$("#counterCurrency").val( parseInt(counterCurrency) + 1);
	});
	
	$('[id^=deletePE]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		console.log('Delete: ' + id);
		
		$.getJSON('/ajax/deletePriceEntry.action', {id : id}, function(a) {
	    	console.log('Remove row: ' + id); 
			$('#pe_tr_' + id).remove();
		});
	});
	
});
</script>
