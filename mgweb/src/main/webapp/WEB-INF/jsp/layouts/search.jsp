<%@ taglib prefix="s" uri="/struts-tags"%>

<table border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td>
<!-- BEGIN SEARCH BOX -->
<s:form action="searchResults.do" method="GET">
<div id="search" class="unit" style="margin:100px 0px 60px 0px;">
<div class="columns">
<div class="fixed column pa10" style="margin-top:7px;"><s:textfield id="query" name="query" cssStyle="width:420px;height:20px;padding:3px 4px 0px 4px;" cssClass="f14"/>
	<s:hidden name="searchType"  value="1"></s:hidden>
	<s:hidden name="search"  value="true"></s:hidden>
</div>
	
<button id="btn" type="button" class="btn" >Launch modal</button>

<div class="fixed column pa10" style="margin-top:5px;"><input type="submit" name="search_button" style="padding:5px 20px;font-size:12px;" id="search_button" value="<s:text name="mg.common.text.short.search"/>"/></div>
</div>
</div>

<!-- Modal HTML -->
    <div id="myModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Confirmation</h4>
                </div>
                <div class="modal-body">
                    <p>Do you want to save changes you made to document before closing?</p>
                    <p class="text-warning"><small>If you don't save, your changes will be lost.</small></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
    
    
</s:form>
<!-- END SEARCH BOX -->
</td>
</tr>
</table>
