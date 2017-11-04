<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="form" action="saveProduct" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:set var="cciList" value="%{product.image.customComponentImagesForImageId}" scope="action"/>
	<s:hidden id="productId" name="product.id" />
	<s:hidden id="imageId" name="product.image.id" />
	<s:hidden id="imageId" name="product.price.id" />
	<s:hidden name="product.translationByDescriptionTransId.id" />
	<s:hidden name="product.translationByNameTransId.id" />
	<s:hidden name="product.creationDate" />
	<s:hidden id="posTop" name="customText.posTop" />
	<s:hidden id="posLeft" name="customText.posLeft" />
	<s:hidden id="width" name="customText.width" />
	<s:hidden id="height" name="customText.height" />
	<s:hidden id="imageWidth" name="customText.imageWidth" />
	<s:hidden id="imageHeight" name="customText.imageHeight" />
	<s:hidden id="counterList" value="%{customComponentImageList.size}" />
	<s:hidden id="counterCurrency" value="%{priceEntrySet.size}" />
	
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Product</h3>
		</div>
		<s:if test="hasActionErrors()">
			<div class="error-terms">
				<s:actionerror />
			</div>
		</s:if>
		<div class="panel-body">
			<fieldset>
				<table  class="table table-bordered table-striped table-hover">
				<tr>
				<td >
					<div class="form-group">
						<label for="nameEn" class="col-md-2 control-label">English name</label>
						<s:textfield id="nameEn" name="nameEn" class="col-md-1 form-control"
							placeholder="English name" type="text"/>
					</div>
					<div class="form-group">
						<label for="nameFr" class="col-sm-2 control-label">French name</label>
						<s:textfield id="nameFr" name="nameFr" class="form-control"
							placeholder="French name:" type="text"/>
					</div>
					<div class="form-group">
						<label for="descEn" class="col-sm-3 control-label">English description</label>
						<s:textfield id="descEn" name="descEn" class="form-control"
							placeholder="English description" type="text"/>
					</div>
					<div class="form-group">
						<label for="descFr" class="col-sm-2 control-label">French description</label>
						<s:textfield id="descFr" name="descFr" class="form-control"
							placeholder="French description:" type="text"/>
					</div>
					<div class="form-group">
						<label for="statusCode" class="col-sm-2 control-label">Status</label>
						<s:select id="statusCode" name="product.statusCode"
							cssStyle="width:156px;" required="true" listKey="code"
							listValue="%{code}"
							list="%{@com.mg.web.WebConstants@ALL_PRODUCT_STATUS}"
							class="form-control" />
					</div>
					<div class="form-group">
						<label for="typeCode" class="col-sm-2 control-label">Type</label>
						<s:select id="typeCode" name="product.typeCode"
							cssStyle="width:156px;" required="true" listKey="code"
							listValue="%{code}"
							list="%{@com.mg.web.WebConstants@ALL_PRODUCT_TYPE}"
							class="form-control" />
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">Custom:</label>
						<div class="checkbox">
							<label> <s:checkbox name="product.customProduct" label="Active:" />
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">Allow custom text:</label>
						<div class="checkbox">
							<label> <s:checkbox name="product.customText" label="Active:" />
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">New:</label>
						<div class="checkbox">
							<label> <s:checkbox name="product.newProduct" label="Active:" />
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="msrp" class="col-sm-2 control-label">MSRP</label>
						<s:textfield id="msrp" name="product.msrp" class="form-control"
							placeholder="MSRP" type="number" cssStyle="width:156px;"/>
					</div>
					<div class="form-group">
						<div class="row">
							<div id="file-uploader_null" style="text-align:center"></div>
							<div id="messageUpload" style="width: 200px;"></div>
						</div>
						<div class="row" >
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>id</th>
										<th>Image</th>
										<th>name</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody id="imagesBody">
									<s:if test="%{product.productImages != null && product.productImages.size > 0}">
										<s:iterator value="product.productImages" status="status" var="image">
											<tr id="pi_tr_<s:property value="id"/>">
												<td style="text-align: center;"><s:property value="id" /></td>
												<td style="text-align: center;"><img class="product-image-icon" src="${productpath}<s:property value="product.id" />/<s:property value="image.name" />" /></td>
												<td style="text-align: center;"><s:property value="image.name" /> </td>
												<td style="text-align: center;">
													<div class="form-group">
														<a id="deletePI_<s:property value="id"/>" class="btn btn-default" role="button">Delete</a>
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
										<th>New Price</th>
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
													<s:textfield id="discount_%{#price.id}" name="discount" class="form-control input-discount" placeholder="Discount" />
												</td>
												<td style="text-align: center;">
													<div class="form-group">
														<a id="deletePE_<s:property value="id"/>" class="btn btn-default" role="button">Delete</a>
														<a id="savePE_<s:property value="id"/>" class="btn btn-default" role="button">Save</a>
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
					<div class="form-group">
						<label for="weight" class="col-sm-2 control-label">Weight</label>
						<s:textfield id="weight" name="product.weight" class="form-control"
							placeholder="Weight" type="text"/>
					</div>
					
					<s:file name="file" label="Select a File to upload" size="40"  />
					</td>
					<td>
					<div class="form-group">
					<div class="image" style="margin-left:10px;width:200px;">
						 <s:if test="%{product.image!=null}">
							<img src="${productpath}<s:property value="product.id" />/<s:property value="product.image.name" />" />
							<div style="margin-top:3px;">Current Size:</div>		        			
					        	<span id="img_dim_1"><s:property value="product.image.width" /> X 
					        	<s:property value="product.image.height" /> - 
					        	<s:property value="product.image.size" />kb</span>		
						</s:if>
					</div>
					</div>
					</td>
				</tr>
					</table>
				
				<div class="row" id="productMain">
					<div class="col-sm-6">
						<div id="mainImage" class="product"
							style="height: 400px; width: 400px;">
							<div class="product-img">
						<s:set var="ccids" value='%{"0"}'/>
						<img  id="mainImageProduct" draggable="false" class="mask maskc img-responsive" src="${productpath}<s:property value="product.id" />/large/<s:property value="product.image.name" />" />
						<s:iterator value="%{product.collection.customComponentCollections}" status="status" var="ccc">
							<!--<s:if test="%{!#ccids.contains(#ccc.customComponent.id)}">
								<img id="mask_<s:property value="#ccc.customComponent.id"/>" class="mask_<s:property value="#status.index"/>"  />
								<s:set var="ccids" value="%{#ccids + ' ' + #ccc.customComponent.id}"/>
							</s:if>-->
							<s:if test='%{!#ccids.contains(#ccc.customComponent.id.toString())}'>
								<s:set var="ccids" value='%{#ccids + " " + #ccc.customComponent.id}'/>
								<img draggable="false" id="mask_<s:property value="#ccc.customComponent.id"/>" class="mask maska"  />
							</s:if>
						</s:iterator>
					</div>
					<div id="draggable" class="ui-widget-content draweble-div" 
						 style="height: <s:property value='customText.height'/>px; 
						 		width: <s:property value='customText.width'/>px; 
						 		left: <s:property value='customText.posLeft'/>px; 
						 		top: <s:property value='customText.posTop'/>px;  z-index: 50;" >
						<span id="displayTextSpan" style="display: block" class="ui-resizable"></span>
					</div>
				</div>
				</div>
				</div>
				
				<!-- <div id="containment-wrapper"> 
					<div id="draggable" class="ui-widget-content" >
						<span id="displayTextSpan" style="display: block" class="ui-resizable"></span>
					</div>
				</div>  -->
				
				<div class="row">
					<div class="form-group col-md-3">
							<label for="displayText" class="col-md-4 control-label">Display text:</label>
							<s:textfield id="displayText"  class="col-md-4 form-control"
								placeholder="Display text" type="text" cssStyle="width:156px;"/>
					</div>
					<div class="form-group col-md-2">
							<label for="maximun" class="col-md-4 control-label">Maximun:</label>
							<s:textfield id="maximun"  name="customText.maximum" class="col-md-4 form-control"
								placeholder="Maximun" type="number" cssStyle="width:156px;"/>
					</div>
					<div class="form-group col-md-2">
							<label for="align" class="col-md-4 control-label">Align:</label>
							<s:select id="align" class="col-md-4 form-control" cssStyle="width:156px;" headerKey="-1" list="#{'left':'left', 'center':'center', 'right':'right'}" name="customText.align" />
					</div>
					<div class="form-group col-md-2">
							<label for="font" class="col-md-4 control-label">Font:</label>
							<select id="font" class="col-md-4 form-control" style="width:156px;"> 
							    <option value="Times New Roman">Times New Roman</option> 
							    <option value="Georgia">Georgia</option> 
							    <option value="Arial">Arial</option> 
							    <option value="Courier New">Courier New</option> 
							    <option value="Lucida Console">Lucida Console</option> 
							</select> 
					</div>
					<div class="form-group col-md-2">
							<label for="size" class="col-md-4 control-label">Size:</label>
							<select id="size" class="col-md-4 form-control" style="width:156px;"> 
							    <option value="8">8</option> 
							    <option value="10">10</option> 
							    <option value="12">12</option> 
							    <option value="14">14</option> 
							    <option value="16">16</option> 
							</select> 
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label for="collection" class="col-sm-1 control-label">Collection</label>
						<s:select id="collection" cssStyle="width:156px;" name ="product.collection.id" class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true"/>
					
						<%-- <s:doubleselect class="form-control" label="Collection" name="collection" listKey="id" listValue="code" list="collectionList" 
										doubleName="component" doubleListKey="id" doubleListValue="typeCode" doubleList="getCustomComponentCollection(top)" /> --%>
					</div>
					
					<div class="form-group">
						<label for="customComponent" class="col-sm-1 control-label">Custom Component</label>
						<s:select id="customComponent"  cssStyle="width:275px;" class="form-control" list="#{}" listKey="id" listValue = "code"  emptyOption="true"/>
					
						<%-- <s:doubleselect class="form-control" label="Collection" name="collection" listKey="id" listValue="code" list="collectionList" 
										doubleName="component" doubleListKey="id" doubleListValue="typeCode" doubleList="getCustomComponentCollection(top)" /> --%>
					</div>
					
					<div class="form-group">
						<a id="addCCI" class="btn btn-default" role="button">Add</a>
					</div>					
					<!-- CustomComponentImage -->
					<div class="form-group">
					<table
						class="table table-bordered table table-hover table table-condensed"
						data-search="true">
						<thead class="gray_header">
							<tr>
								<th data-field="cc.id"><span style="width: 70px;" class="bold">cc.id</span></th>
								<th data-field="cci.id"><span style="width: 70px;" class="bold">cci.id</span></th>
								<th data-field="cci.custom_id"><span style="width: 70px;" class="bold">cci.custom_id</span></th>
								<th data-field="cc.code"><span style="width: 70px;" class="bold">cc.code</span></th>
								<th data-field="ccc.value"><span style="width: 70px;" class="bold">ccc.value</span></th>
								<th data-field="cci.image_id"><span style="width: 20px;" class="bold">cci.image_id</span></th>
								<th data-field="cci.image_mask_id"><span style="width: 70px;" class="bold">cci.imageByImageMaskId</span></th>
								<th data-field="cci.statusCode"><span style="width: 70px;" class="bold">cci.statusCode</span></th>
								<th data-field="cci.creationDate"><span style="width: 70px;" class="bold">cci.creationDate</span></th>
								<th data-field="action"><span style="width: 70px;" class="bold">action</span></th>	
							</tr>
						</thead>
						<tbody id="cciListData">
						<s:if test="%{product.image.customComponentImagesForImageId != null && product.image.customComponentImagesForImageId.size > 0}">
							
								<s:iterator value="cciList" status="status" var="cci">
									<tr id="tr_<s:property value="id"/>">
										<td style="text-align: center;"><s:property value="customComponentCollection.customComponent.id" /></td>
										<td style="text-align: center;"><s:property value="id" /> </td>
										<td style="text-align: center;"><s:property value="customComponentCollection.id" /></td>
										<td style="text-align: center;"><s:property	value="customComponentCollection.customComponent.code" /></td>
										<td style="text-align: center;">
											<!--<s:iterator value="customComponent.customComponentCollections" status="status" var="ccc">
												<s:if test='%{#ccc.typeCode.toString().equals("PATH")}'>
													<div class="collection_ico">
														<img width="30" height="30" src="${collectionpath}<s:property value="image.name" />" />
													</div>
												</s:if>
												<s:if test='%{#ccc.typeCode.toString().equals("COLOR")}'>
													<div class="collection_ico"  style="background:#<s:property value="value" />"></div>
												</s:if>
											</s:iterator>-->
											<s:if test='%{#cci.customComponentCollection.typeCode.toString().equals("PATH")}'>
												<div class="collection_ico">
													<img  id='imag_<s:property value="product.id" />_<s:property value="customComponentCollection.customComponent.id" />_<s:property value="id" />' width="30" height="30" src="${collectionpath}<s:property value="customComponentCollection.image.name" />" />
												</div>
											</s:if>
											<s:if test='%{#cci.customComponentCollection.typeCode.toString().equals("COLOR")}'>
												<div class="collection_ico"  style="background:#<s:property value="customComponentCollection.value" />"></div>
											</s:if>
										</td>
										<td style="text-align: center;"><s:property	value="image.id" /></td>
										<td style="text-align: center;"><a href="aeImage.action?id=<s:property value="imageByImageMaskId.id"/>"> <img id="image_<s:property value="imageByImageMaskId.id"/>" width="30" height="30" src="${productpath}<s:property value="product.id" />/<s:property value="imageByImageMaskId.name" />" /></a></td>
										<td id="status_<s:property value="id"/>" style="text-align: center;"><s:property	value="statusCode" /></td>
										<td style="text-align: center;"><s:property	value="creationDate" /></td>
										<td style="text-align: center;">
											<div class="form-group">
												<div id="file-uploader_<s:property value="imageByImageMaskId.id"/>" style="text-align:center"></div>
												<div id="messageUpload" style="width: 200px;"></div>
												<a id="chageCCI_<s:property value="id"/>" class="btn btn-default" role="button">Change Status</a>
												<a id="deleteCCI_<s:property value="id"/>" class="btn btn-default" role="button">Delete</a>
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
			</fieldset>
		</div>
		<div class="panel-footer">
			<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>
			<a id="cancelCC" class="btn btn-success btn-lg" role="button" href="productList.action" >Cancel</a>
		</div>
	</div>


</s:form>


<script type="text/javascript">

$(document).ready(function() { 
	
	function storeImageProperties() {
		console.log('resizing stopped');
  	  $("#posTop").val($("#draggable").css( 'top' ).replace("px", ""));
  	  $("#posLeft").val($("#draggable").css( 'left' ).replace("px", ""));
  	  $("#width").val($("#draggable").css( 'width' ).replace("px", ""));
  	  $("#height").val($("#draggable").css( 'height' ).replace("px", ""));
  	  $("#imageWidth").val($("#mainImageProduct").css( 'width' ).replace("px", ""));
  	  $("#imageHeight").val($("#mainImageProduct").height());
	}
	
	function addLine(type, componentCode, counter, componentId, imageId, imageName, customId){
		if(counter==""){
  	  		counter = 0;
  	  	}
		ccid = '<td> <s:textfield  id="customComponentImageList[' + counter + '].customComponentCollection.customComponent.id" name="customComponentImageList[' + counter + '].customComponentCollection.customComponent.id" value="' + customId + '" /></td>';
		cciid = '<td> <s:textfield  id="customComponentImageList[' + counter + '].id" name="customComponentImageList[' + counter + '].id" /></td>';
		cccid = '<td> <s:textfield  id="customComponentImageList[' + counter + '].customComponentCollection.id" name="customComponentImageList[' + counter + '].customComponentCollection.id" value="' + componentId + '" /></td>';
		cccode = '<td> ' + componentCode + '</td>';
		
		if(type == "PATH"){
		console.log("PATH");
		option = '<div class="collection_ico"> <img width="30" height="30" src="${collectionpath}' + imageName +'" /></div>';
		}
		if(type == "COLOR"){
			console.log("COLOR");
			option = '<div class="collection_ico"  style="background:#' + imageName + '"></div>';
		}
		cccvalue= '<td>' +  option + '</td>';
		cciimageid = '<td> <s:textfield  id="customComponentImageList[' + counter + '].image.id" name="customComponentImageList[' + counter + '].image.id" value="' + imageId + '" /></td>';
		cciimagemaskid = '<td> <s:file  id="customComponentImageList[' + counter + '].file" name="customComponentImageList[' + counter + '].file" /></td>';
		ccicreationdate = '<td> <s:textfield  id="customComponentImageList[' + counter + '].creationDate" name="customComponentImageList[' + counter + '].creationDate"  /></td>';
		var buttonDelete = '<td style="text-align: center;"> <div class="form-group"> ' +
		'<a id="deleterow_' + counter + '" class="btn btn-default" role="button">Delete</a></div></td>';

		row = ccid + cciid + cccid + cccode + cccvalue + cciimageid + cciimagemaskid + ccicreationdate + buttonDelete ;
		
		console.log('Row: '  + row);
		console.log('counter: '  + counter);
		$("tbody#cciListData").append('<tr id="ttr_' + counter +'">' + row + '</tr>');
		$("#counterList").val( parseInt(counter) + 1);
	}

  console.log('Asign click event to addCC');
  
  var isDragging, 
  top = 0, left = 0,
  curX, curY;
  

$("#draggable").mousedown(function (e) {
  e.preventDefault();
});

$("#containment-wrapper").mousedown(function (e) {
  isDragging = true;

  curX = e.pageX;
  curY = e.pageY;

  left = Number($("#draggable").css("left").
                toString().replace("px", ""));
  top = Number($("#draggable").css("top").
               toString().replace("px", ""));
});

$(document).mouseup(function () {
  if (isDragging){
      // reset
      isDragging = false;
      top = 0;
      left = 0;
  }
  $("#posTop").val($("#draggable").css( 'top' ).replace("px", ""));
  $("#posLeft").val($("#draggable").css( 'left' ).replace("px", ""));
  $("#width").val($("#draggable").css( 'width' ).replace("px", ""));
  $("#height").val($("#draggable").css( 'height' ).replace("px", ""));
});

$("#containment-wrapper").mousemove(function(e){
  if (!isDragging) {
      return;
  }

  left += e.pageX - curX;
  top += e.pageY - curY;

  // set the position
  $("#draggable").css("left", left + "px").
      css("top", top + "px");

  curX = e.pageX;
  curY = e.pageY;  
});
  
  $('#draggable').draggable({ containment: "#containment-wrapper", scroll: false}).resizable({helper: "ui-resizable-helper"  });
  
  $('#draggable')
  .resizable({
      stop: function(e, ui) {
    	  storeImageProperties();
    }
  });
  
  $('#draggable')
  .draggable({
      stop: function(e, ui) {
    	  storeImageProperties();
      }
  });
  
  $("#displayText").change(function(){
	  var textDisplayText = $("#displayText").val();
	  $("#displayTextSpan").text(textDisplayText);
  });
  
  $("#align").change(function(){
	  var align = $("#align").val();
	  $("#displayTextSpan").css('text-align',align);
  });
  
  $("#font").change(function(){
	  var font = $("#font").val();
	  $("#displayTextSpan").css("font-family", font);
  });
  
  $("#size").change(function(){
	  var size = $("#size").val() + "px";
	  $("#displayTextSpan").css("font-size", size);
  });
	
  $.getJSON("ajax/colllectionList.action", function(a) {
      var options = '<option value="' + "--Select--" + '">'
              + "--Select--" + '</option>';

      for ( var i = 0; i < a.list.length; i++) {
          //alert(j.items[i].key);
          options += '<option value="' + a.list[i].id + '">'
                  + a.list[i].value + '</option>';
      }
      $("select#collection").html(options);
      $("select#collection").val(<s:property value="product.collection.id"/>);

  });
  
  	var obj = $(this);
 	// var components = obj.find(o.swatchClass);
  	var listComponents = $("img[id*='imag_']");
	
  	$(listComponents).each(function(){		
		$(this).click(function(){ 
			var component = $(this);
			console.log('component: '  + component.attr("id").split("_")[3]);
			var productId = component.attr("id").split("_")[1];
			var componentImageId = component.attr("id").split("_")[3];
			$.getJSON("ajax/componentImage.action", 
					   { productId: productId, componentImageId: componentImageId }, 
					   function(a) {
						   var customComponentId = component.attr("id").split("_")[2];
						   var mask = $("img[id*='mask_" + customComponentId + "']");
						   mask.removeAttr('src');
						   mask.attr('src', a.path);

	  		});
		});
	});
  	
  var productId = $("#productId").val();
  var imageId = $("#imageId").val();
	
  var uploaderes = [];
  $('[id^=file-uploader]').each(function (index, value) { 
	  var cciid = $(this).attr('id').split('_')[1]; 
	  uploaderes[index] = new qq.FileUploader( {
	      // pass the dom node (ex. $(selector)[0] for jQuery users)
	      element: document.getElementById('file-uploader_' + cciid),
	      // path to server-side upload script
	      action: 'ajax/uploadImage.action',
	      //add params
	      params: {},
	      debug: true,
	      onSubmit: function(id, fileName) {
	    	  uploaderes[index].setParams({imageId: cciid, productId: productId});          
	      },
	      onComplete: function(id, fileName, res) {
	          $(".qq-upload-list").empty();
	      	if( res.success == true ) {
	      		var imageName = res.name;
	      		var path = res.path;
	      		d = new Date();
	      		if(cciid != "null"){
		      		$("#image_" + cciid).attr("src", path + imageName + "?" +d.getTime());
		      		var app = '<div class="hcenter" style="padding-top:10px;">' +
							  '<div id="image" class="hcenter"><img width="300" height="300" src="${product}large/'+res.name+'?_='+new Date().getTime()+'" /></div>'+
							  '<div style="margin-top:3px;">Current Size:</div>'+
							  '<span id="img_dim">'+ res.width +'x' + res.height +' - ' + res.size + 'kb</span></div>';
	
					$("#imageId").val(res.imageId);
					$(this).html(app);
		      	}
	      		else{
	      			var row = '<tr id="pi_tr_' + res.id + '">' + 
	      					  '<td style="text-align: center;">' + res.id + '</td>' +
	      					  '<td style="text-align: center;">' +
	      					  '<img class="product-image-icon" src="' + res.path + res.name + '">' +
	      					  '</td><td style="text-align: center;">' + res.name + '</td> ' +
	      					  '<td style="text-align: center;"><div class="form-group">' +
	      					  '</div></td></tr>';
	      			$('#imagesBody').append(row);
	      		}
	      	} 
	      	else {
	          	$("#messageUpload").removeClass(vanadiumAdv);        
	          	$("#messageUpload").addClass(vanadiumAdv).html("<span>" + res.success + "&nbsp;<a href=# target='_blank'>" + res.msg + "</a></span>").show();                        
	       	}
	      }        
	  }); 

	});
  
 $("#addCCI").click(function(){
	console.log('$("#collection").val() :' + $("#collection").val());
	if($("#customComponent").val() == "--Select--"){
		$.getJSON("ajax/componentCollectionList.action", { id: $("#collection").val() }, function(a) {
			options = '';
			var option = '';
			$("tbody#cciListData").html('<tr></tr>');
			for ( var i = 0; i < a.list.length; i++) {	          
				type = a.list[i].key.split('-')[0];
				componentCode = a.list[i].key.split('-')[1];
				counter = $("#counterList").val();
				componentId = a.list[i].key.split('-')[3];
				imageId = $("#imageId" ).val();
				imageName = a.list[i].value;
				customId = a.list[i].key.split('-')[2];

				addLine(type, componentCode, counter, componentId, imageId, imageName,customId);
			}
			//Add click event
			$('[id^=deleterow]').click(function() {
				var id = $(this).attr('id').split('_')[1];
				$('#ttr_' + id).remove();
			});
		});
	}
	else{
		type = $("#customComponent").val().split('-')[0];
		componentCode = $("#customComponent").val().split('-')[1];
		counter = $("#counterList").val();
		componentId = $("#customComponent").val().split('-')[3];
		imageId = $("#imageId" ).val();
		imageName = $("#customComponent").find('option:selected').text();
		customId = $("#customComponent").val().split('-')[2];

		addLine(type, componentCode, counter, componentId, imageId, imageName, customId);
		
		//Add click event
		$('[id^=deleterow]').click(function() {
			var id = $(this).attr('id').split('_')[1];
			$('#ttr_' + id).remove();
		});
	}
 });

  $("#collection").change(function(){
	 console.log('$("#collection").val() :' + $("#collection").val());
	 var oDropdown = $("#customComponent").msDropdown().data("dd");
	 oDropdown.destroy();
	 $.getJSON("ajax/componentCollectionList.action", { id: $("#collection").val() }, function(a) {
	      var options = '<option value="' + "--Select--" + '">'
	              + "--Select--" + '</option>';

	      var option = '';
	      var type = '';
	      
	      for ( var i = 0; i < a.list.length; i++) {
	          //alert(a.list[i].key);
	          type = a.list[i].key.split('-')[0];
	          console.log(type);
	          
	           if(type == "PATH"){
	        	  console.log("PATH");
	        	  option = '${collectionpath}' + a.list[i].value ;
	          }
	          if(type == "COLOR"){
	        	  console.log("COLOR");
	        	  option = '<div class="collection_ico"  style="background:#' + a.list[i].value + '"></div>';
	          } 
	          
	          console.log("option : " + option);
	          options += '<option value="' + a.list[i].key + '" data-image="'
	                  + option + '" data-imagecss="collection_ico">' + a.list[i].value +'</option>';
	      }
	      console.log("options : " + options);
	      $("select#customComponent").html(options);

	      $("#customComponent").msDropDown();
	      oDropdown = $("#customComponent").msDropdown().data("dd");
	      oDropdown.refresh();
	  });
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
		
		$.getJSON("ajax/addProductPrice.action", { price: $("#cost").val(), currency: $("#currency option:selected" ).text(), objId: $("#productId").val(), productId: productId}, function(a) {
			if(a.id != null && a.id > 0){
				cid = '<td> <s:textfield  id="priceId" name="priceId" value="' + a.id + '" /></td>';
			}
			else{
				cid = '<td> </td>';
			}
			row = cid + ccost + ccurrency ;
			$("tbody#currencyBody").append('<tr>' + row + '</tr>');
		});
		
		$("#counterCurrency").val( parseInt(counterCurrency) + 1);
	});
	
  $('[id^=deleteCCI]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		$.getJSON('ajax/deleteCCI.action', {
			id : id
		      }, function(jsonResponse) {
				$('#tr_' + id).remove();
		});
	});

  $('[id^=deletePE]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		$.getJSON('ajax/deletePriceEntry.action', {id : id, productId: productId}, function(a) {
			$('#pe_tr_' + id).remove();
		});
	});
  
  $('[id^=savePE]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		var newPrice = $('#discount_' + id).val();
		var productId = $('#productId').val();
		
		$.getJSON('ajax/savePriceEntry.action', {id : id, newPrice: newPrice, productId: productId}, function(a) {
	    	alert("New price save it.");
		});
	});
  
  $('[id^=deletePI]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		$.getJSON('ajax/deleteProductImage.action', {imageId : id}, function(a) {
			$('#pi_tr_' + id).remove();
		});
	});
  

  $('[id^=chageCCI]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		var status = $('#status_' + id).text();
		$.getJSON('ajax/changeStatusCCI.action', {
			id : id, code : status
		      }, function(jsonResponse) {
				if(status == "ACTIVE"){
					$('#status_' + id).text("INACTIVE");
				}
				else{
					$('#status_' + id).text("ACTIVE");
				}
		    	
		});
	});

});

  

  </script>
