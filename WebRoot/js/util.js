var Util = function() {
}; 

Util.resetDatePicker = function(pickerId) {
    var picker = document.getElementById(pickerId);
    var v = picker.value;
    if(!v) return;
    var idx = v.indexOf('0:00:00.000');
    idx = (idx > 0) ? idx : v.indexOf(' 00:00:00.0');
    if( idx > 0) {  
        v = v.substring(0, idx);
    } 
    picker.value = v;
};

