/**
 * Use for delete rows in ecside
 * @param options = {
 *    noneSelectedMsg:"If user select nothing to delete",
 *    confirmMsg:"Confirm message.",
 *    ecsideFormId:"ecside from id",
 *    deleteFormId:"Remove form id, you must provide a form to post delete request."
 * }
 */
function onRemove(options) {
	if(!options) options = {};
    var opt = {
    	noneSelectedMsg: (options.noneSelectedMsg) ? options.noneSelectedMsg:'请至少选择一个.',
    	confirmMsg: (options.confirmMsg) ? options.confirmMsg : '确认删除吗?',
    	ecsideFormId: (options.ecsideFormId) ? options.ecsideFormId : 'ec',
    	deleteFormId: (options.deleteFormId) ? options.deleteFormId : 'removeForm'
    };
    var checked = false;
    $('input').each(function(i, item) {
       if(item.checked && item.id == 'selectedItems') {
         checked = true;
       }
    });
    if(!checked) {
        alert(opt.noneSelectedMsg);
        return;
    }
    
    if(confirm(opt.confirmMsg)) {
       $('#' + opt.ecsideFormId)[0].action = $('#' + opt.deleteFormId)[0].action;
       $('#' + opt.ecsideFormId)[0].submit();
    } else {
       return false;
    }
}