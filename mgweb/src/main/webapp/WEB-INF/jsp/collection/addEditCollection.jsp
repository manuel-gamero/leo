<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveCollection" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<!--<s:textfield name="user.id" label="User id: " />-->
	<s:set var="cccList" value="%{collection.customComponentCollections}" scope="action"/>
	<s:hidden name="collection.id" />
	<s:hidden name="collection.creationDate" />
	<s:hidden name="collection.image.id" />
	<s:hidden id="counterList" value="%{collection.customComponentCollections.size}" />
	<s:token></s:token>
	<div id="ajax-modal" class="modal fade" tabindex="-1" style="display: none;"></div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Collection</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				<div class="form-group">
					<label for="code" class="col-sm-1 control-label">Code</label>
					<s:textfield id="code" name="collection.code" class="form-control"
						placeholder="Code"/>
				</div>
				
				<s:include value="../template/basicTranslationTemplate.jsp"></s:include>
				
				<div class="form-group">
					<label for="statusCode" class="col-sm-1 control-label">Status</label>
					<s:select id="statusCode" name="collection.statusCode"
						cssStyle="width:156px;" required="true" listKey="code"
						listValue="%{code}"
						list="%{@com.mg.web.WebConstants@ALL_USER_STATUS}"
						class="form-control" />
				</div>
				
				<s:file name="imageDTO.file" label="Select a File to upload" size="40"  />
				<div class="form-group">
					<div class="image" style="margin-left:10px;width:200px;">
						 <s:if test="%{collection.image!=null}">
							<img src="${collectionpath}/<s:property value="collection.image.name" />" />
							<div style="margin-top:3px;">Current Size:</div>		        			
					        	<span id="img_dim_1"><s:property value="collection.image.width" /> X 
					        	<s:property value="collection.image.height" /> - 
					        	<s:property value="collection.image.size" />kb</span>		
						</s:if>
					</div>
				</div>
					
				<!-- CustomComponentCollection -->
				<div class="form-group">
				<table
					class="table table-bordered table table-hover table table-condensed"
					data-search="true">
					<thead class="gray_header">
						<tr>
							<th data-field="cc.id"><span style="width: 70px;" class="bold">cc.id</span></th>
							<th data-field="cc.code"><span style="width: 70px;"
								class="bold">cc.code</span></th>
							<th data-field="cc.name"><span style="width: 70px;"
								class="bold">cc.name</span></th>
							<th data-field="ccc.nameTransl"><span style="width: 20px;"
								class="bold">ccc.nameTransl</span></th>
							<th data-field="ccc.descTransl"><span style="width: 20px;"
								class="bold">ccc.descTransl</span></th>
							<th data-field="cc.typeCode"><span style="width: 70px;"
								class="bold">cc.typeCode</span></th>
							<th data-field="ccc.id"><span style="width: 70px;"
								class="bold">ccc.id</span></th>
							<th data-field="ccc.typeCode"><span style="width: 70px;"
								class="bold">ccc.typeCode</span></th>
							<th data-field="ccc.typeCode"><span style="width: 70px;"
								class="bold">ccc.statusCode</span></th>
							<th data-field="ccc.value"><span style="width: 70px;"
								class="bold">ccc.value</span></th>
							<th data-field="ccc.creationDate"><span style="width: 70px;"
								class="bold">ccc.creationDate</span></th>
							<th data-field="action"><span style="width: 70px;"
								class="bold">action</span></th>	
						</tr>
					</thead>
					<tbody id="ccListData">
					<s:if test="%{collection.customComponentCollections != null && collection.customComponentCollections.size > 0}">
						
							<s:iterator value="cccList" status="status" var="ccc">
								<tr id="tr_<s:property value="id"/>">
									<td style="text-align: center;"><s:property value="customComponent.id" /> </td>
									<td style="text-align: center;"><s:property value="customComponent.code" /></td>
									<td style="text-align: center;"><s:property	value="customComponent.name" /></td>
									<td style="text-align: center;"><s:property	value="translationByNameTransId.id" /></td>
									<td style="text-align: center;"><s:property	value="translationByDescriptionTransId.id" /></td>
									<td style="text-align: center;"><s:property	value="customComponent.typeCode" /></td>
									<td style="text-align: center;"><s:property	value="id" /></td>
									<td style="text-align: center;"><s:property	value="typeCode" /></td>
									<td id="status_<s:property value="id"/>" style="text-align: center;"><s:property	value="statusCode" /></td>
									<td style="text-align: center;">
										<s:if test="%{#ccc.image == null}">
											<s:if test='%{#ccc.typeCode.equals("COLOR")}'>
												<s:property	value="value" />
											</s:if>
											<s:if test='%{#ccc.typeCode.equals("FONT")}'>
												<s:property	value="value" />
											</s:if>
											<s:else>
												<s:property	value="value" />
											</s:else>
										</s:if>
										<s:else>
											<a href="aeImage.action?id=<s:property value="image.id"/>"> <img id="image_<s:property value="image.id"/>" width="30" height="30" src="${collectionpath}<s:property value="image.name" />" /></a>
										</s:else>
									</td>
									<td style="text-align: center;"><s:property	value="creationDate" /></td>
									<td style="text-align: center;">
										<div class="form-group">
											<div id="file-uploader_<s:property value="image.id"/>" style="text-align:center"></div>
											<a id="chageCCC_<s:property value="id"/>" class="btn btn-default" role="button">Change Status</a>
											<%-- <a id="deleteCCC_<s:property value="id"/>" class="btn btn-default" role="button">Delete</a> --%>
											<a id="translationCCC_<s:property value="id"/>" class="btn btn-default popup" >Edit translation</a>
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
			</fieldset>
			
			<div class="form-group">
				<label for="customComponent" class="col-sm-1 control-label">Custom Component</label>
				<s:select id="customComponent" 
					cssStyle="width:156px;" required="true" listKey="id"
					listValue="%{code}"
					list="#{}"
					class="form-control" />
			</div>
			<div class="form-group">
				<label for="typeCode" class="col-sm-1 control-label">Type</label>
				<s:select id="typeCode"
					cssStyle="width:156px;" required="true" listKey="code"
					listValue="%{code}"
					list="%{@com.mg.web.WebConstants@ALL_COMPONENT_ATTRIBUTE_TYPE}"
					class="form-control" />
			</div>
			<div id="valueDiv" class="form-group">
				<label for=value class="col-sm-1 control-label">Value</label>
				<s:textfield id="value" class="form-control" placeholder="Value" />
			</div>
			
			<div id="pickerDiv" class="form-group">
				<label for=picker class="col-sm-1 control-label">Value</label>
				<s:textfield id="picker" class="form-control" placeholder="Value" />
			</div>
			
			<div id="fontDiv" class="form-group">
				<label for=font class="col-sm-1 control-label">Value</label>
				<select id="font" class="form-control" style="width:156px;"> 
				    <option value="Times New Roman">Times New Roman</option> 
				    <option value="Georgia">Georgia</option> 
				    <option value="Arial">Arial</option> 
				    <option value="Courier New">Courier New</option> 
				    <option value="Lucida Console">Lucida Console</option> 
				</select> 
			</div>
				
			<div class="form-group">
				<a id="addCC" class="btn btn-default" role="button">Add</a>
			</div>
		</div>
		<div class="panel-footer">
			<s:submit value="Save" class="btn btn-success btn-lg"></s:submit>
			<a id="cancelCC" class="btn btn-success btn-lg" role="button" href="collectionList.action" >Cancel</a>
		</div>
	</div>
</s:form>



<script type="text/javascript">

$(document).ready(function() { 

	tinymce.init({selector: 'textarea'});

  console.log('Asign click event to addCC');
	
  var $modal = $('#ajax-modal');
  
  $('[id^=translationCCC]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		$.get('aeCccTranslation.action?id=' + id, function(data) {
		      $(data).modal();
		  });
	});
  
  $("#typeCode").change(function() {
	var typeCode= $("#typeCode option:selected" ).text();
	console.log('typeCode: ' + typeCode );
	if(typeCode == 'COLOR'){
		$("#pickerDiv").show();
		$("#valueDiv").hide();
		$("#fontDiv").hide();
	}
	if(typeCode == 'PATH'){
		$("#pickerDiv").hide();
		$("#valueDiv").hide();
		$("#fontDiv").hide();
	}
	if(typeCode == 'FONT'){
		$("#pickerDiv").hide();
		$("#valueDiv").hide();
		$("#fontDiv").show();
	}
	if(typeCode == 'SIZE'){
		$("#pickerDiv").hide();
		$("#valueDiv").show();
		$("#fontDiv").hide();
	}
  });

  $.getJSON("ajax/customComponentList.action", function(a) {
      var options = '<option value="' + "--Select--" + '">'
              + "--Select--" + '</option>';

      for ( var i = 0; i < a.list.length; i++) {
          //alert(j.items[i].key);
          options += '<option value="' + a.list[i].id + '">'
                  + a.list[i].value + '</option>';
      }
      $("select#customComponent").html(options);
  });

  
  $("a#addCC").click(function() {
	  	var counter = $("#counterList").val();
	  	if(counter==""){
	  		counter = 0;
	  	}
		var customComponentId = $("#customComponent").val();
		var customComponentCode = $("#customComponent option:selected" ).text();
		var typeCode = $("#typeCode").val();
		var value = $("#value").val();
		if($('#pickerDiv').is(':visible')) {
			value = $("#picker").val();
		}
		var ccid = '<td> <s:textfield  id="ccc_' + counter + '_customComponent_id" name="customComponentCollections[' + counter + '].customComponent.id" value="' + customComponentId + '" /></td>';
		var cccode = '<td> <s:textfield  id="ccc_' + counter + '_customComponent_code" name="customComponentCollections[' + counter + '].customComponent.code" value="' + customComponentCode + '" /></td>';
		var ccname = '<td> <s:textfield  id="ccc_' + counter + '_customComponent_name" name="customComponentCollections[' + counter + '].customComponent.name"  /></td>';
		var cccreationDate = '<td> <s:textfield  id="ccc_' + counter + '_customComponent_creationDate" name="customComponentCollections[' + counter + '].customComponent.creationDate"  /></td>';
		var cctypeCode = '<td> <s:textfield  id="ccc_' + counter + '_customComponent_typeCode" name="customComponentCollections[' + counter + '].customComponent.typeCode"  /></td>';
		
		var cccid = '<td> <s:textfield  id="ccc_' + counter + '_id" name="customComponentCollections[' + counter + '].id"  /></td>';
		var typeCode= $("#typeCode option:selected" ).text();
		var cccvalue;
		console.log('typeCode: ' + typeCode);
		if(typeCode == 'COLOR'){
			cccvalue = '<td bgcolor="#' + value +'"> <s:textfield  id="ccc_' + counter + '_value" name="customComponentCollections[' + counter + '].value" value="' + value + '" /></td>';
		}
		if(typeCode == 'PATH'){
			cccvalue = '<td> <s:file  id="ccc_' + counter + '_file" name="customComponentCollections[' + counter + '].imageDTO.file" /></td>';
		}
		if(typeCode == 'FONT'){
			value = $("#font option:selected" ).text();
			cccvalue = '<td> <s:textfield class="font_input" cssStyle="font-family:' + value + ';" id="ccc[' + counter + '].value" name="customComponentCollections[' + counter + '].value" value="' + value + '" /></td>';
		}
		var ccctypeCode = '<td> <s:textfield  id="ccc_' + counter + '_typeCode" name="customComponentCollections[' + counter + '].typeCode" value="' + typeCode + '" /></td>';
		var ccccreationDate = '<td> <s:textfield  id="ccc_' + counter + '_creationDate" name="customComponentCollections[' + counter + '].creationDate"  /></td>';
		var image = '<td> <img id="imag_' + counter + '" width="30" height="30" src="${collectionpath}" /></td>';
		var buttonDelete = '<td style="text-align: center;"> <div class="form-group"> ' +
						   '<a id="deleterow_' + counter + '" class="btn btn-default" role="button">Delete</a></div></td>';
		
		var row = ccid + cccode + ccname + cccreationDate + '<td></td>' + cctypeCode + cccid + ccctypeCode + image + ccccreationDate + cccvalue + buttonDelete;

		$("tbody#ccListData").append('<tr id="ttr_' + counter +'">' + row + '</tr>');
		
		$("#counterList").val( parseInt(counter) + 1);
		
		$('#ccc_' + counter + '_file').change(function() {
			var imageName = $(this).val();
			var id = $(this).attr('id').split('_')[1];
			var ccId = $('input#ccc_' + id + '_customComponent_id').val();
			console.log('imageName: ' + imageName); 
			
			$.getJSON('ajax/retrieveCustomComponentCollection.action', { imageName : imageName, ccId: ccId}, function(a) {
				if(a.ccName != null && a.ccName!= ""){
					$('input#ccc_' + id + '_customComponent_name').val(a.ccName);
					$('input#ccc_' + id + '_customComponent_typeCode').val(a.ccTypeCode);
					$('input#ccc_' + id + '_id').val(a.cccId);
					$('input#ccc_' + id + '_typeCode').val(a.cccTypeCode);
					$('#imag_'+ id).attr("src",$('#imag_'+ id).attr("src") + a.imageName);
				}
			});
	    });
		
		$('[id^=deleterow]').click(function() {
			var id = $(this).attr('id').split('_')[1];
			$('#ttr_' + id).remove();
		});
	});

	$('#picker').colpick({
		layout:'hex',
		submit:0,
		colorScheme:'dark',
		onChange:function(hsb,hex,rgb,el,bySetColor) {
			$(el).css('border-color','#'+hex);
			// Fill the text box just if the color was set using the picker, and not the colpickSetColor function.
			if(!bySetColor) $(el).val(hex);
		}
	}).keyup(function(){
		$(this).colpickSetColor(this.value);
	});
	
	$('[id^=deleteCCC]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		$.getJSON('ajax/deleteCCC.action', {
			id : id
		      }, function(jsonResponse) {
				$('#tr_' + id).remove();
		});
	});
	
	$('[id^=chageCCC]').click(function() {
		var id = $(this).attr('id').split('_')[1];
		var status = $('#status_' + id).text();		
		$.getJSON('ajax/changeStatusCCC.action', {
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
		  	  uploaderes[index].setParams({ imageId: cciid });          
		    },
		    onComplete: function(id, fileName, res) {
		        $(".qq-upload-list").empty();
		    	if( res.success == true ) {
		    		var imageName = res.name;
		    		var path = res.path;
		    		d = new Date();
		    		$("#image_" + cciid).attr("src", path + imageName + "?" +d.getTime());
			var app = '<div class="hcenter" style="padding-top:10px;">' +
					  '<div id="image" class="hcenter"><img width="300" height="300" src="${product}large/'+res.name+'?_='+new Date().getTime()+'" /></div>'+
					  '<div style="margin-top:3px;">Current Size:</div>'+
					  '<span id="img_dim">'+ res.width +'x' + res.height +' - ' + res.size + 'kb</span></div>';
		
			$("#imageId").val(res.imageId);
				
			$(this).html(app);
		    	} 
		    	else {
		        	$("#messageUpload").removeClass(vanadiumAdv);        
		        	$("#messageUpload").addClass(vanadiumAdv).html("<span>" + res.success + "&nbsp;<a href=# target='_blank'>" + res.msg + "</a></span>").show();                        
		     	}
		    }        
		}); 

	});

});

  

  </script>
