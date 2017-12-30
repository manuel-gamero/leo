<%@ taglib uri="/struts-tags" prefix="s"%>



<s:form id="form" action="saveCouponsType" namespace="/admin" method="post"
	validate="true" enctype="multipart/form-data" class="form-horizontal">

	<s:hidden name="id" />
	<s:hidden name="couponsType.imageEn.id" />
	<s:hidden name="couponsType.imageFr.id" />
	<s:hidden name="couponsType.creationDate" />
	<s:token></s:token>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Add/Edit Coupons Type</h3>
		</div>
		<div class="panel-body">
			<fieldset>
				
				<div class="form-group">
					<label for=code class="col-sm-1 control-label">Code</label>
					<s:textfield id="code" name="couponsType.code" class="form-control"
						placeholder="code" />
				</div>
				
				<s:include value="../template/basicTranslationTemplate.jsp"></s:include>
				
				<div class="row">	   
					<label for="country" class="col-sm-1 control-label">Type</label>
					<s:select id="country" cssStyle="width:156px;" class="form-control" 
					name="couponsType.typeCode" list="codeTypeList" emptyOption="true"/>
				</div>
				
				<div class="form-group">
					<label for="multiTime" class="col-sm-1 control-label">multiTime</label>
					<s:textfield id="multiTime" name="couponsType.multiTime" class="form-control"
						placeholder="multiTime" />
				</div>
				<div class="form-group">
					<label for="multiUser" class="col-sm-1 control-label">multiUser</label>
					<s:textfield id="multiUser" name="couponsType.multiUser" class="form-control"
						placeholder="multiUser" />
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">Promotion:</label>
					<label> <s:checkbox name="couponsType.promotion" label="Promotion:" />
					</label>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">Start date:</label>
		            <div class='input-group date col-md-2' id='datetimepicker6'>
		            	<s:textfield id="date6" name="couponsType.promotionStart" class="form-control"
						placeholder="dateStart" />
		                <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
		        </div>
			    <div class="form-group">
			    	<label class="col-sm-1 control-label">End date:</label>
		            <div class='input-group date col-md-2' id='datetimepicker7'>
		            	<s:textfield id="date7" name="couponsType.promotionEnd" class="form-control"
						placeholder="dateEnd" />
		                <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
		        </div>
		       <!-- Image En Start -->
				<s:file name="imageEn.file" label="Select a File (EN) to upload" size="40"  />
				<div class="form-group">
					<div class="image" style="margin-left:10px;width:200px;">
						 <s:if test="%{couponsType.imageEn!=null}">
							<img src="${promotionpath}/<s:property value="couponsType.imageEn.name" />" />
							<div style="margin-top:3px;">Current Size:</div>		        			
					        	<span id="img_dim_1">
					        		<s:property value="couponsType.imageEn.width" /> X 
					        		<s:property value="couponsType.imageEn.height" /> - 
					        		<s:property value="couponsType.imageEn.size" />kb
					        	</span>		
						</s:if>
					</div>
				</div>
				<!-- Image En End -->
				
				<!-- Image Fr Start -->
				<s:file name="imageFr.file" label="Select a File (FR) to upload" size="40"  />
				<div class="form-group">
					<div class="image" style="margin-left:10px;width:200px;">
						 <s:if test="%{couponsType.imageFr!=null}">
							<img src="${promotionpath}/<s:property value="couponsType.imageFr.name" />" />
							<div style="margin-top:3px;">Current Size:</div>		        			
					        	<span id="img_dim_1">
					        		<s:property value="couponsType.imageFr.width" /> X 
					        		<s:property value="couponsType.imageFr.height" /> - 
					        		<s:property value="couponsType.imageFr.size" />kb
					        	</span>		
						</s:if>
					</div>
				</div>
				<!-- Image Fr End -->
				<div class="form-group">
					<label for="urlEn" class="col-sm-1 control-label">url En:</label>
					<s:textfield id="urlEn" name="urlEn" class="form-control" placeholder="English url" />
				</div>
				<div class="form-group">
					<label for="urlFr" class="col-sm-1 control-label">url Fr:</label>
					<s:textfield id="urlFr" name="urlFr" class="form-control" placeholder="French url:" />
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
	

	tinymce.init({
		  selector: 'textarea',
		  height: 500,
		  toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
		  content_css: [
		    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
		    '//www.tinymce.com/css/codepen.min.css'
		  ]
		});
	
	$('#datetimepicker6').datetimepicker();
    $('#datetimepicker7').datetimepicker({
        useCurrent: false //Important! See issue #1075
    });
    $("#datetimepicker6").on("dp.change", function (e) {
        $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
    });
    $("#datetimepicker7").on("dp.change", function (e) {
        $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
    });
	
});
</script>