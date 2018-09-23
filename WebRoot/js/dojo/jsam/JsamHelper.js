var JsamHelper = function(){};

JsamHelper.DEFAULT_PAGE_SIZE = 8;

JsamHelper.resetTable = function(table, data) {
    if(table) {
        table.store.setData(data);
    }
};

JsamHelper.resetPaginator = function(paginator, pageInfo) {
    if(paginator) {
        paginator.setParams({
                 pageNo    : pageInfo.pageNo,
                 totalCount: pageInfo.rows,
                 pageSize  : JsamHelper.DEFAULT_PAGE_SIZE });  
    }
};

JsamHelper.cleanNull = function(item, fieldNames) {
    if(!fieldNames || !item) return;
    for(var x = 0; x < fieldNames.length; x++) {
          if(!item[fieldNames[x]]) {
              item[fieldNames[x]] = '';
          }
    }
};

JsamHelper.createCheckboxColumn = function(data, func) {
             
        var input = document.createElement('input');
        input.setAttribute('type', 'checkbox');
        input.style.border = "0";
        input.data = data;
        input.defaultChecked = data.changed;
        input.checked = data.changed;
        dojo.connect(input, "onclick", dojo.global, func);
        
        data.changed = input;        
};

JsamHelper.handler = function(page, table, paginator) {
         JsamHelper.resetTable(table, page.data);
         JsamHelper.resetPaginator(paginator, page);
};