if(!dojo._hasResource["jsam.ArrayList"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["jsam.ArrayList"] = true;
dojo.provide("jsam.ArrayList");

jsam.ArrayList=function(/* array? */arr){
	//	summary
	//	Returns a new object of type dojox.collections.ArrayList
	var items=[];
	if(arr) items=items.concat(arr);
	this.count=items.length;
	this.add=function(/* object */obj, bDontFire){
		//	summary
		//	Add an element to the collection.
		items.push(obj);
		this.count=items.length;
		if(!bDontFire) {
			this.onAdd(obj);
		}
	};
	
	this.getItems = function() {
		return items;
	};	
	
	this.setData = function(/* array */a, bDontFire) {
		this.clear();
		items = a;
		this.count = items.length;
		if(!bDontFire) {
			this.onSetData();
		}
	};
	
	this.addRange=function(/* array */a, bDontFire){
		//	summary
		//	Add a range of objects to the ArrayList
		if(a.getIterator){
			var e=a.getIterator();
			while(!e.atEnd()){
				this.add(e.get());
			}
			this.count=items.length;
		}else{
			for(var i=0; i<a.length; i++){
				items.push(a[i]);
			}
			this.count=items.length;
		}
		
		if(!bDontFire) {
			this.onAddRange(a);
		}
	};
	this.clear=function(bDontFire){
		//	summary
		//	Clear all elements out of the collection, and reset the count.
		items.splice(0, items.length);
		this.count=0;
		if(!bDontFire) {
			this.onClear();
		}
	};
	this.clone=function(){
		//	summary
		//	Clone the array list
		return new dojox.collections.ArrayList(items);	//	dojox.collections.ArrayList
	};
	this.contains=function(/* object */obj){
		//	summary
		//	Check to see if the passed object is a member in the ArrayList
		for(var i=0; i < items.length; i++){
			if(items[i] == obj) {
				return true;	//	bool
			}
		}
		return false;	//	bool
	};
	this.forEach=function(/* function */ fn, /* object? */ scope){
		//	summary
		//	functional iterator, following the mozilla spec.
		var s=scope||dojo.global;
		if(Array.forEach){
			Array.forEach(items, fn, s);
		}else{
			for(var i=0; i<items.length; i++){
				fn.call(s, items[i], i, items);
			}
		}
	};
	this.getIterator=function(){
		//	summary
		//	Get an Iterator for this object
		return new dojox.collections.Iterator(items);	//	dojox.collections.Iterator
	};
	this.indexOf=function(/* object */obj){
		//	summary
		//	Return the numeric index of the passed object; will return -1 if not found.
		for(var i=0; i < items.length; i++){
			if(items[i] == obj) {
				return i;	//	int
			}
		}
		return -1;	// int
	};
	this.insert=function(/* int */ i, /* object */ obj, bDontFire){
		//	summary
		//	Insert the passed object at index i
		items.splice(i,0,obj);
		this.count=items.length;
		if(!bDontFire) {
			this.onInsert(i, obj);
		}
	};
	this.item=function(/* int */ i){
		//	summary
		//	return the element at index i
		return items[i];	//	object
	};
	this.remove=function(/* object */obj, bDontFire){
		//	summary
		//	Look for the passed object, and if found, remove it from the internal array.
		var i=this.indexOf(obj);
		if(i >=0) {
			items.splice(i,1);
		}
		this.count=items.length;
		
		if(!bDontFire) {
			this.onRemove(obj);
		}
	};
	this.removeAt=function(/* int */ i, bDontFire){
		//	summary
		//	return an array with function applied to all elements
		items.splice(i,1);
		this.count=items.length;
		if(!bDontFire) {
			this.onRemoveAt(i);
		}
	};
	this.reverse=function(){
		//	summary
		//	Reverse the internal array
		items.reverse();
	};
	this.sort=function(/* function? */ fn){
		//	summary
		//	sort the internal array
		if(fn){
			items.sort(fn);
		}else{
			items.sort();
		}
	};
	this.setByIndex=function(/* int */ i, /* object */ obj){
		//	summary
		//	Set an element in the array by the passed index.
		items[i]=obj;
		this.count=items.length;
	};
	this.toArray=function(){
		//	summary
		//	Return a new array with all of the items of the internal array concatenated.
		return [].concat(items);
	}
	this.toString=function(/* string */ delim){
		//	summary
		//	implementation of toString, follows [].toString();
		return items.join((delim||","));
	};
	
	this.update = function (obj, fieldPath, val, bDontFire) {
		var parts = fieldPath.split("."), i = 0, o = obj, field;
		if (parts.length > 1) {
			field = parts.pop();
			do {
				if (parts[i].indexOf("()") > -1) {
					var temp = parts[i++].split("()")[0];
					if (o[temp]) {
						o = o[temp]();
					} 
				} else {
					o = o[parts[i++]];
				}
			} while (i < parts.length && o != null);
		} else {
			field = parts[0];
		}
		obj[field] = val;
		if (!bDontFire) {
			this.onUpdate(obj, fieldPath, val);
		}
	};
	
	
	this.onUpdate =  function (obj, fieldPath, val){};
	this.onAdd = function(obj) {};
	this.onRemove = function(obj){};
	this.onRemoveAt = function(i){};
	this.onClear = function(){};
	this.onAddRange = function(a){};
	this.onInsert = function(i, obj){};
	this.onSetData = function(){};
};

}
