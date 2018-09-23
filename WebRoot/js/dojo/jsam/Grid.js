if(!dojo._hasResource["jsam.Grid"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["jsam.Grid"] = true;
dojo.provide("jsam.Grid");

dojo.require("dijit._Widget");
dojo.require("dojo.date.locale");
dojo.require("jsam.common");
dojo.require("dojox.collections");
dojo.require("jsam.ArrayList");

dojo.declare(
	"jsam.Grid",
	[dijit._Widget],
	{
		store : new jsam.ArrayList([]),
		keyField : "id",
		multiple : false,
		maxSelect : 0,
		maxSortable : 1,
        selectable: true,
		minRows : 0,
		datePattern : "yyyy-MM-dd",
		isInitialized : false,
		alternateRows : false,
		columns : [],
		useCustomerSort : false,
		sortInformation : [{field:'id', index:0, direction:0, dirName:'desc'}],
		headClass : "samGridHead",
		tbodyClass : "samGridTBody",
		headerClass : "samGridHeader",
		headerUpClass : "samGridHeaderUp",
		headerDownClass : "samGridHeaderDown",
		rowClass : "",
		rowAlternateClass : "rowAlternateClass",
		rowSelectedClass : "rowSelectedClass",
		noSortClass: "samNoSortClass",
		isContainer:false, templatePath:null, templateCssPath:null,
		
		getTypeFromString:function (s) {
			var parts = s.split("."); 
			var i = 0; 
			var obj = dojo.global;
			do {
				obj = obj[parts[i++]];
			} while (i < parts.length && obj);
			return (obj != dojo.global) ? obj : null;
		},
		
		getByRow:function (row/*HTML row*/) {
			var keyValue = jsam.html.getAttribute(row, "value");
			
			for(var itr = this.store.getIterator(); !itr.atEnd();) {
				var obj = itr.get();
				if(obj && obj[this.keyField] == keyValue) {
					return obj;
				}
			}	
			return null;		
		},
		
		getDataByRow:function (row/*HTML row*/) {
			return this.getByRow(row);
		},
		
		getRow:function (obj/*Object contains the key*/) {
			var rows = this.domNode.tBodies[0].rows;
			for (var i = 0; i < rows.length; i++) {
				if (this.getByRow(rows[i])[this.keyField] == obj[this.keyField]) {
					return rows[i];
				}
			}
			return null;
		},
		
		
		getColumnIndex:function (fieldPath) {
			for (var i = 0; i < this.columns.length; i++) {
				if (this.columns[i].getField() == fieldPath) {
					return i;
				}
			}
			return -1;
		}, 

		getSelectedData:function () {
            //if(!this.selectable) return [];
			var a = [];
			
			for (var itr = this.store.getIterator(); !itr.atEnd();) {
				var obj = itr.get();
				if (obj.isSelected) {
					a.push(obj);
				}
			}
			
			return (this.multiple) ? a : a[0];			
		},
		
		isSelected:function (obj) {
            //if(!this.selectable) return false;
			for (var itr = this.store.getIterator(); !itr.atEnd();) {
				var objx = itr.get();
				if (objx == obj) {
					return true;
				}
			}
			return false;
		}, 
		
		isRowSelected:function (row) {
            //if(!this.selectable) return false;
			var v = this.getByRow(row);
            if (v) {
				return v.isSelected;
			}
			return false;
		}, 
		
		reset:function () {
			this.store.clear();
			this.columns = [];
			this.sortInformation = [{field:'id', index:0, direction:0, dirName:'desc'}];
			this.resetSelections();
			this.isInitialized = false;
			this.onReset();
		},
		
		resetSelections:function () {
			this.store.forEach(function (element) {
				element.isSelected = false;
			});
		}, 
		
		onReset:function () {
		},
		
		select:function (obj) {
            //if(!this.selectable) return;
			var data = this.store.getItems();
			for (var i = 0; i < data.length; i++) {
				if (data[i] == obj) {
					data[i].isSelected = true;
					console.log("select " + obj[this.keyField]);
					break;
				}
			}
			this.onDataSelect(obj);
		},
		
		selectAll:function () {
			this.store.forEach(function (element) {
				element.isSelected = true;
			});
		},
		
		onDataSelect:function (obj) {
        },
    
	    toggleSelection:function (obj) {
		  var data = this.store.getItems();
		  
		  for (var i = 0; i < data.length; i++) {
		  	console.log("select " + data[i][this.keyField] + data[i].isSelected);
			if (data[i] == obj) {
				data[i].isSelected = !data[i].isSelected;			
				break;
			}
		  }
		  
		  this.onDataToggle(obj);
	    },
	    toggleSelectionByValue:function (val) {
		  this.toggleSelection(this.store.getDataByKey(val));
	    }, 
		toggleSelectionByIndex:function (idx) {
		  this.toggleSelection(this.store.getDataByIndex(idx));
	    }, 
		toggleSelectionByRow:function (row) {
		  this.toggleSelection(this.getDataByRow(row));
	    },
	    toggleAll:function () {
				this.store.forEach(function (element) {
					element.isSelected = !element.isSelected;
				});
		},
			
		onDataToggle:function (obj) {
		},
		
		_meta:{
				field:null, 
				format:null, 
				filterer:null, 
				noSort:false, 
				sortType:"String", 
				dataType:String, 
				sortFunction:null, 
				filterFunction:null, 
				label:null, 
				align:"left", 
				valign:"middle", 
				getField:function () {
				   return this.field || this.label;
			  },
			  getType:function () {
				   return this.dataType;
			  }
		},
		
		 createMetaData:function (obj) {
			for (var p in this._meta) {
				if (!obj[p]) {
					obj[p] = this._meta[p];
				}
			}
			if (!obj.label) {
				obj.label = obj.field;
			}
			if (!obj.filterFunction) {
				obj.filterFunction = this._defaultFilter;
			}
			return obj;
		},
		
		parseMetadata:function (head) {
			this.columns = [];
			this.sortInformation = [];
			var row = head.getElementsByTagName("tr")[0];
			var cells = row.getElementsByTagName("td");
			if (cells.length == 0) {
				cells = row.getElementsByTagName("th");
			}
			for (var i = 0; i < cells.length; i++) {
				var o = this.createMetaData({});
				if (jsam.html.hasAttribute(cells[i], "align"))  {
					o.align  = jsam.html.getAttribute(cells[i], "align");
				}
				if (jsam.html.hasAttribute(cells[i], "valign")) {
					o.valign = jsam.html.getAttribute(cells[i], "align");
				}
				if (jsam.html.hasAttribute(cells[i], "nosort")) {
					o.noSort = (jsam.html.getAttribute(cell[i], "nosort") == "true");
				}
				if (jsam.html.hasAttribute(cells[i], "sortusing")) {
					var trans = jsam.html.getAttribute(cells[i], "sortusing");
					var f = this.getTypeFromString(trans);
					if (f != null && f != window && typeof (f) == "function") {
						o.sortFunction = f;
					}
				}
				o.label = jsam.html.renderedTextContent(cells[i]);
				if(jsam.html.hasAttribute(cells[i], "field")) {
					o.field = jsam.html.getAttribute(cells[i], "field");
				} else {
					o.field = (o.label.length > 0) ? o.field = o.label : o.field = "field" + i;					
				}
				if(jsam.html.hasAttribute(cells[i], "format")) {
					o.format = jsam.html.getAttribute(cells[i], "format");
				}
				if(jsam.html.hasAttribute(cells[i], "dataType")) {
					var sortType = jsam.html.getAttribute(cells[i], "dataType");
					if (sortType.toLowerCase() == "html" || sortType.toLowerCase() == "markup") {
						o.sortType = "__markup__";
					} else {
						var type = this.getTypeFromString(sortType);
						if (type) {
							o.sortType = sortType;
							o.dataType = type;
						}
					}
				}
				if (jsam.html.hasAttribute(cells[i], "filterusing")) {
					var trans = jsam.html.getAttribute(cells[i], "filterusing");
					var f = this.getTypeFromString(trans);
					if (f != null && f != window && typeof (f) == "function") {
						o.filterFunction = f;
					}
				}
				this.columns.push(o);
				if (jsam.html.hasAttribute(cells[i], "sort")) {
					var info = {field: o.field, index:i, direction:0, dirName:'desc'};
					var dir = jsam.html.getAttribute(cells[i], "sort");
					if (dir) {
						info.direction = dir;
					} 
					this.sortInformation.push(info);
				}
			}
			if (this.sortInformation.length == 0) {
				this.sortInformation.push({field: "id", index:0, direction:0, dirName:'desc'});
			} else {
				if (this.sortInformation.length > this.maxSortable) {
					this.sortInformation.length = this.maxSortable;
				}
			}
		}, 
		
		parseData:function (body) {
			if (body.rows.length == 0 && this.columns.length == 0) {
				console.log("There is no table body");
				return;
			}
			
			var self = this;
			this["__selected__"] = [];
			var arr = this._getFromHtml(this.columns, body, function (obj, row) {
				if (typeof (obj[self.keyField]) == "undefined" || obj[self.keyField] == null) {
					obj[self.keyField] = jsam.html.getAttribute(row, "value");
				}
				if (jsam.html.getAttribute(row, "selected") == "true") {
					self["__selected__"].push(obj);
				}
			});
			this.store.setData(arr, true);
			this.render();
			for (var i = 0; i < this["__selected__"].length; i++) {
				this.select(this["__selected__"][i]);
			}
			this.renderSelections();
			delete this["__selected__"];
			this.isInitialized = true;
		}, 
		
		
		onSelect:function (e) {
				var row = jsam.html.getParentByType(e.target, "tr");
			if (jsam.html.hasAttribute(row, "emptyRow")) {
				return;
			}
			var body = jsam.html.getParentByType(row, "tbody");
			if (this.multiple) {
				if (e.shiftKey) {
					var startRow;
					var rows = body.rows;
					for (var i = 0; i < rows.length; i++) {
						if (rows[i] == row) {
							break;
						}
						if (this.isRowSelected(rows[i])) {
							startRow = rows[i];
						}
					}
					if (!startRow) {
						startRow = row;
						for (; i < rows.length; i++) {
							if (this.isRowSelected(rows[i])) {
								row = rows[i];
								break;
							}
						}
					}
					this.resetSelections();
					if (startRow == row) {
						this.toggleSelectionByRow(row);
					} else {
						var doSelect = false;
						for (var i = 0; i < rows.length; i++) {
							if (rows[i] == startRow) {
								doSelect = true;
							}
							if (doSelect) {
								this.selectByRow(rows[i]);
							}
							if (rows[i] == row) {
								doSelect = false;
							}
						}
					}
				} else {
					this.toggleSelectionByRow(row);
				}
			} else {
				this.resetSelections();
				this.toggleSelectionByRow(row);
			}
			this.renderSelections();
    },
    
    onSort:function (e) {
			var oldIndex = this.sortIndex;
			var oldDirection = this.sortDirection;
			var source = e.target;
			var row = jsam.html.getParentByType(source, "tr");
			var cellTag = "td";
			if (row.getElementsByTagName(cellTag).length == 0) {
				cellTag = "th";
			}
			var headers = row.getElementsByTagName(cellTag);
			var header = jsam.html.getParentByType(source, cellTag);
			for (var i = 0; i < headers.length; i++) {
				jsam.html.setClass(headers[i], this.headerClass);
				jsam.html.setClass(this.getSortNode(headers[i]), this.noSortClass);
				if (headers[i] == header) {
					if (this.sortInformation[0].index != i) {
						this.sortInformation.unshift({field: this.columns[i].field, 
						  index:i, direction:0, dirName:'desc'});
					} else {
						var dir = (~this.sortInformation[0].direction) & 1;
						this.sortInformation[0] = {field: this.columns[i].field, 
						  index:i, direction:dir,
						  dirName: (dir == 0) ? 'desc' : 'asc'};
					}
				}
			}
			this.sortInformation.length = Math.min(this.sortInformation.length, this.maxSortable);
			for (var i = 0; i < this.sortInformation.length; i++) {
				var idx = this.sortInformation[i].index;
				var dir = (~this.sortInformation[i].direction) & 1;
				var sortNode = this.getSortNode(headers[idx]);
				jsam.html.setClass(sortNode, dir == 0 ? this.headerDownClass : this.headerUpClass);
			}
			if(this.sortInformation.length > 0) {
			  console.log("Sort field " + this.sortInformation[0].field
			    + " Sort dir " + this.sortInformation[0].dirName);
			}
			if(this.useCustomerSort) {
				//this.store.setData(this.doSort(this.sortInformation));
                this.doSort(this.sortInformation);
			}
			this.render();
		},
		
		doSort : function(sortInformation) {
			return new jsam.ArrayList([]);
		},
		
		onFilter:function () {
		},
		_defaultFilter:function (obj) {
			return true;
		},
		setFilter:function (field, fn) {
			for (var i = 0; i < this.columns.length; i++) {
				if (this.columns[i].getField() == field) {
					this.columns[i].filterFunction = fn;
					break;
				}
			}
			this.applyFilters();
		},
		setFilterByIndex:function (idx, fn) {
			this.columns[idx].filterFunction = fn;
			this.applyFilters();
		},
		clearFilter:function (field) {
			for (var i = 0; i < this.columns.length; i++) {
				if (this.columns[i].getField() == field) {
					this.columns[i].filterFunction = this._defaultFilter;
					break;
				}
			}
			this.applyFilters();
		},
		clearFilterByIndex:function (idx) {
			this.columns[idx].filterFunction = this._defaultFilter;
			this.applyFilters();
		},
		clearFilters:function () {
			for (var i = 0; i < this.columns.length; i++) {
				this.columns[i].filterFunction = this._defaultFilter;
			}
			var rows = this.domNode.tBodies[0].rows;
			for (var i = 0; i < rows.length; i++) {
				rows[i].style.display = "";
				if (this.alternateRows) {
					dojo[((i % 2 == 1) ? "addClass" : "removeClass")](rows[i], this.rowAlternateClass);
				}
			}
			this.onFilter();
		},
		applyFilters:function () {
			var alt = 0;
			var rows = this.domNode.tBodies[0].rows;
			for (var i = 0; i < rows.length; i++) {
				var b = true;
				var row = rows[i];
				for (var j = 0; j < this.columns.length; j++) {
					var value = this._getField(this.getDataByRow(row), this.columns[j].getField());
					if (this.columns[j].getType() == Date && value != null && !value.getYear) {
						value = new Date(value);
					}
					if (!this.columns[j].filterFunction(value)) {
						b = false;
						break;
					}
				}
				row.style.display = (b ? "" : "none");
				if (b && this.alternateRows) {
					dojo[((alt++ % 2 == 1) ? "addClass" : "removeClass")](row, this.rowAlternateClass);
				}
			}
			this.onFilter();
		},
    createSorter:function (info) {
		    if(this.useCustomerSort) {
				return null;
			}
			var self = this;
			var sortFunctions = [];
			function createSortFunction(fieldIndex, dir) {
				var meta = self.columns[fieldIndex];
				var field = meta.getField();
				return function (rowA, rowB) {
					if (jsam.html.hasAttribute(rowA, "emptyRow")) {
						return 1;
					}
					if (jsam.html.hasAttribute(rowB, "emptyRow")) {
						return -1;
					}
					var a = self.getDataByRow(rowA)[field];
					var b = self.getDataByRow(rowB)[field];
					var ret = 0;
					if (a > b) {
						ret = 1;
					}
					if (a < b) {
						ret = -1;
					}
					return dir * ret;
				};
			}
			var current = 0;
			var max = Math.min(info.length, this.maxSortable, this.columns.length);
			while (current < max) {
				var direction = (info[current].direction == 0) ? 1 : -1;
				sortFunctions.push(createSortFunction(info[current].index, direction));
				current++;
			}
			return function (rowA, rowB) {
				var idx = 0;
				while (idx < sortFunctions.length) {
					var ret = sortFunctions[idx++](rowA, rowB);
					if (ret != 0) {
						return ret;
					}
				}
				return 0;
			};
		},
		createRow:function (obj) {
			var row = document.createElement("tr");
			dojo.setSelectable(row, false);
			if (obj[this.keyField] != null) {
				row.setAttribute("value", obj[this.keyField]);
			}
            
			for (var j = 0; j < this.columns.length; j++) {
				var cell = document.createElement("td");
				cell.setAttribute("align", this.columns[j].align);
				cell.setAttribute("valign", this.columns[j].valign);
				dojo.setSelectable(row, false);
				
				var val = this._getField(obj, this.columns[j].getField());
				if (typeof (val) == "undefined") {
					val = "";
				}
				
				this.fillCell(cell, this.columns[j], val);				
				row.appendChild(cell);
			}
			
			return row;
		},
		
		fillCell:function (cell, meta, val) {		  
			//	summary
			//	Fill the passed cell with value, based on the passed meta object.
			if(typeof val == 'object') {
	    	    cell.appendChild(val);
	        }  else if(meta.sortType=="__markup__"){
				cell.innerHTML=val;
			} else {
				if(meta.getType()==Date) {
					val=new Date(val);
					if(!isNaN(val)){
						var format = {datePattern:this.datePattern, selector:"date"};
						if(meta.format){
							format = {datePattern:meta.format, selector:"date"};
						}
						cell.innerHTML = dojo.date.locale.format(val, format);
					} else {
						cell.innerHTML = val;
					}
				} 
				 else if ("Number number int Integer float Float".indexOf(meta.getType())>-1){
					//	TODO: number formatting
					if(val.length == 0){
						val="0";
					}
					var n = parseFloat(val, 10) + "";
					//	TODO: numeric formatting + rounding :)
					if(n.indexOf(".")>-1){
						n = dojo.math.round(parseFloat(val,10),2);
					}
					cell.innerHTML = n;
				}else{
                    cell.innerHTML = (val == '') ? '&nbsp;' : val;
				}
			}
	  },
	  prefill:function () {
			this.isInitialized = false;
			var body = this.domNode.tBodies[0];
			while (body.childNodes.length > 0) {
				body.removeChild(body.childNodes[0]);
			}
			if (this.minRows > 0) {
				for (var i = 0; i < this.minRows; i++) {
					var row = document.createElement("tr");
					if (this.alternateRows) {
						dojo[((i % 2 == 1) ? "addClass" : "removeClass")](row, this.rowAlternateClass);
					}
					row.setAttribute("emptyRow", "true");
					for (var j = 0; j < this.columns.length; j++) {
						var cell = document.createElement("td");
						cell.innerHTML = "&nbsp;";
						row.appendChild(cell);
					}
					body.appendChild(row);
				}
			}
		}, 
		
		init:function () {
			this.isInitialized = false;
			var head = this.domNode.getElementsByTagName("thead")[0];
			if (head.getElementsByTagName("tr").length == 0) {
				var row = document.createElement("tr");
				for (var i = 0; i < this.columns.length; i++) {
					var cell = document.createElement("td");
					cell.setAttribute("align", this.columns[i].align);
					cell.setAttribute("valign", this.columns[i].valign);
					dojo.setSelectable(cell, false);
					cell.innerHTML = this.columns[i].label;
					row.appendChild(cell);
					if (!this.columns[i].noSort) {
						dojo.connect(cell, "onclick", this, "onSort");
					}
				}
				dojo.place(row, head, "first");
			}
			if (this.store.count == 0) {
				console.log("Data count = 0");
				return false;
			}
			console.log("Data count != 0");
			var idx = this.domNode.tBodies[0].rows.length;
			if (!idx || idx == 0 || this.domNode.tBodies[0].rows[0].getAttribute("emptyRow") == "true") {
				idx = 0;
				var body = this.domNode.tBodies[0];
				while (body.childNodes.length > 0) {
					body.removeChild(body.childNodes[0]);
				}
				
				var data = this.store.getItems();
				for (var i = 0; i < data.length; i++) {					
				    var row = this.createRow(data[i]);
					body.appendChild(row);
					idx++;
				}
			}
			
			if (this.minRows > 0 && idx < this.minRows) {
				idx = this.minRows - idx;
				for (var i = 0; i < idx; i++) {
					row = document.createElement("tr");
					row.setAttribute("emptyRow", "true");
					for (var j = 0; j < this.columns.length; j++) {
						cell = document.createElement("td");
						cell.innerHTML = "&nbsp;";
						row.appendChild(cell);
					}
					body.appendChild(row);
				}
			}
			var row = this.domNode.getElementsByTagName("thead")[0].rows[0];
			var cellTag = "td";
			if (row.getElementsByTagName(cellTag).length == 0) {
				cellTag = "th";
			}
			var headers = row.getElementsByTagName(cellTag);
			for (var i = 0; i < headers.length; i++) {				
				jsam.html.setClass(headers[i], this.headerClass);
				jsam.html.setClass(this.getSortNode(headers[i]), this.noSortClass);
			}
			for (var i = 0; i < this.sortInformation.length; i++) {
				var idx = this.sortInformation[i].index;
				var dir = (~this.sortInformation[i].direction) & 1;
				var sortNode = this.getSortNode(headers[idx]);
				jsam.html.setClass(sortNode, dir == 0 ? this.headerDownClass : this.headerUpClass);
			}
			this.isInitialized = true;
			console.log("Init end " + this.isInitialized);
			return this.isInitialized;
	},
	
	getSortNode: function(header) {
		if(header.childNodes.length > 1) {
			return header.childNodes[1];
		}
		
		var sn = dojo.doc.createElement('span');
		header.appendChild(sn);
		return sn;
	},
	
	render:function () {
		console.log("Render begin." + this.isInitialized);
		if (!this.isInitialized) {
			var b = this.init();
			if (!b) {
				this.prefill();
				return;
			}
		}
		
		var rows = [];
		var body = this.domNode.tBodies[0];
		var emptyRowIdx = -1;
		
		for (var i = 0; i < body.rows.length; i++) {
			rows.push(body.rows[i]);
		}
		var sortFunction = this.createSorter(this.sortInformation);
		if (sortFunction) {
			rows.sort(sortFunction);
		}
		
		for (var i = 0; i < rows.length; i++) {
			if (this.alternateRows) {
				dojo[((i % 2 == 1) ? "addClass" : "removeClass")](rows[i], this.rowAlternateClass);
			}
            if(this.selectable) {    
			    dojo[(this.isRowSelected(rows[i]) ? "addClass" : "removeClass")](rows[i],
                  this.rowSelectedClass);
            }
			body.appendChild(rows[i]);
		}
        
		console.log("Render over.");       
	}, 
	
	renderSelections:function () {
		var body = this.domNode.tBodies[0];
		for (var i = 0; i < body.rows.length; i++) {
            if(this.selectable) {
			    dojo[(this.isRowSelected(body.rows[i]) ? "addClass" : "removeClass")](body.rows[i], 
                this.rowSelectedClass);
            }
		}
	},
	
	startup:function () {
		var self = this;
		dojo.connect(this.store, "onSetData", function () {
			self.store.forEach(function (element) {
                if(element.isSelected == 'undefined') element.isSelected = false;
			});
			self.isInitialized = false;
			var body = self.domNode.tBodies[0];
			if (body) {
				while (body.childNodes.length > 0) {
					body.removeChild(body.childNodes[0]);
				}
			}
			console.log("Reset data.");
			self.render();
		});
		dojo.connect(this.store, "onClear", function () {
			self.isInitialized = false;
			self.render();
		});
		dojo.connect(this.store, "onAdd", function (addedObject) {
			var row = self.createRow(addedObject);
			self.domNode.tBodies[0].appendChild(row);
			self.render();
		});
		dojo.connect(this.store, "onAddRange", function (arr) {
			for (var i = 0; i < arr.length; i++) {
                if(arr[i].isSelected == 'undefined') arr[i].isSelected = false;
				var row = self.createRow(arr[i]);
				self.domNode.tBodies[0].appendChild(row);
			}
			self.render();
		});
		dojo.connect(this.store, "onRemove", function (removedObject) {
			var rows = self.domNode.tBodies[0].rows;
			for (var i = 0; i < rows.length; i++) {
				if (self.getDataByRow(rows[i]) == removedObject) {
					rows[i].parentNode.removeChild(rows[i]);
					break;
				}
			}
			self.render();
		});
		
		dojo.connect(this.store, "onUpdate", function (obj, fieldPath, val) {
			var row = self.getRow(obj);
			var idx = self.getColumnIndex(fieldPath);
			if (row && row.cells[idx] && self.columns[idx]) {
				self.fillCell(row.cells[idx], self.columns[idx], val);
			}
		});
	},
	
	postCreate:function () {		
		//this.store.keyField = this.keyField;
		if (this.domNode) {
			if (this.domNode.nodeName.toLowerCase() != "table") {
			}
			if (this.domNode.getElementsByTagName("thead")[0]) {
				var head = this.domNode.getElementsByTagName("thead")[0];
				if (this.headClass.length > 0) {
					head.className = this.headClass;
				}
				dojo.setSelectable(this.domNode, false);
				this.parseMetadata(head);
				var header = "td";
				if (head.getElementsByTagName(header).length == 0) {
					header = "th";
				}
				var headers = head.getElementsByTagName(header);
				for (var i = 0; i < headers.length; i++) {
					if (!this.columns[i].noSort) {
						dojo.connect(headers[i], "onclick", this, "onSort");
					}
				}
			} else {
				this.domNode.appendChild(document.createElement("thead"));
			}
			if (this.domNode.tBodies.length < 1) {
				var body = document.createElement("tbody");
				this.domNode.appendChild(body);
			} else {
				var body = this.domNode.tBodies[0];
			}
			if (this.tbodyClass.length > 0) {
				body.className = this.tbodyClass;
			}
			dojo.connect(body, "onclick", this, "onSelect");
			this.parseData(body);
			
		}
},
	_getField : function (obj, field) {
		   console.log(obj);
			var parts = field.split("."); var i = 0; var o = obj;
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
			
			return o;
		},
		_getFromHtml : function (meta, body, fnMod) {
			var rows = body.rows;
			var ctor = function (row) {
				var obj = {};
				for (var i = 0; i < meta.length; i++) {
					var o = obj;
					var data = row.cells[i].innerHTML;
					var p = meta[i].getField();
					if (p.indexOf(".") > -1) {
						p = p.split(".");
						while (p.length > 1) {
							var pr = p.shift();
							o[pr] = {};
							o = o[pr];
						}
						p = p[0];
					}
					var type = meta[i].getType();
					if (type == String) {
						o[p] = data;
					} else {
						if (data) {
							o[p] = new type(data);
						} else {
							o[p] = new type();
						}
					}
				}
				return obj;
			};
			var arr = [];
			for (var i = 0; i < rows.length; i++) {
				var o = ctor(rows[i]);
				if (fnMod) {
					fnMod(o, rows[i]);
				}
				arr.push(o);
			}
			return arr;
		}
	});
}