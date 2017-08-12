<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="form-group">
	<label for="nameEn" class="col-md-2 control-label"><s:text name="bolsos.template.translation.name.en"/></label>
	<s:textfield id="nameEn" name="nameEn" class="col-md-1 form-control" placeholder="English name" type="text" />
</div>
<div class="form-group">
	<label for="nameFr" class="col-sm-2 control-label"><s:text name="bolsos.template.translation.name.fr"/></label>
	<s:textfield id="nameFr" name="nameFr" class="form-control" placeholder="French name:" type="text" />
</div>
<div class="form-group">
	<label for="descEn" class="col-sm-3 control-label"><s:text name="bolsos.template.translation.descr.en"/></label>
	<s:textarea id="descEn" name="descEn" class="form-control" placeholder="English description" type="text" />
</div>
<div class="form-group">
	<label for="descFr" class="col-sm-2 control-label"><s:text name="bolsos.template.translation.descr.fr"/></label>
	<s:textarea id="descFr" name="descFr" class="form-control" placeholder="French description:" type="text" />
</div>