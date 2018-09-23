
(function () {
	window.undefined = window.undefined;
	window.isIE = !!(window.attachEvent && !window.opera);
	if (window.isIE) {
		window.attachEvent("onunload", $_IEGC);
	}
	if ($_E) {
		$_E_original = $_E;
	}
	if (!window.isIE && window.Node) {
		Node.prototype.swapNode = function (B) {
			var C = this.nextSibling;
			var A = this.parentNode;
			B.parentNode.replaceChild(this, B);
			A.insertBefore(B, C);
		};
	}
	if (!window.isIE && window.HTMLElement) {
		HTMLElement.prototype.__defineGetter__("innerText", function () {
			return this.textContent;
		});
	}
})();
function $_IEGC() {
	CollectGarbage();
}
var ECSideConstants = {EMPTY_FUNCTION:function () {
}, EC_ID:"ec", ETI_ID:"eti", ETI_PAGE_FLAG:"eti_p", SORT_PREFIX:"_s_", FILTER_PREFIX:"_f_", ACTION:"a", FILTER_ACTION:"fa", CLEAR_ACTION:"ca", PAGEFIELD_SUFFIX:"_p", EXPORT_IFRAME_SUFFIX:"_ecs_export_iframe", SHADOW_ROW:"_shadowRow", HIDE_HEADER_ROW:"_hideListRow", DEFALUT_ADD_TEMPLATE:"add_template", AJAX_ZONE_BEGIN:"_begin_ ", AJAX_ZONE_END:" _end_", AJAX_ZONE_PREFIX:"<!-- ECS_AJAX_ZONE_PREFIX_", AJAX_ZONE_SUFFIX:"_ECS_AJAX_ZONE_SUFFIX -->", MIN_COL_WIDTH:10, SCROLLBAR_WIDTH:18, SCROLL_SPEED:50, MIN_COLWIDTH:"30", AJAX_HEADER:["useAjaxPrep", "true"], ROW_HIGHLIGHT_CLASS:"highlight", ROW_SELECTLIGHT_CLASS:"selectlight", DRAG_BUTTON_COLOR:"#3366ff", HEAD_ZONE_SUFFIX:"_headZone", BODY_ZONE_SUFFIX:"_bodyZone", FOOT_ZONE_SUFFIX:"_footZone", LIST_HEIGHT_FIXED:window.isIE ? 0 : 0, LIST_WIDTH_FIXED:window.isIE ? 0 : 1, IE_WIDTH_FIX_A:1, IE_WIDTH_FIX_B:2, FF_WIDTH_FIX_A:-3, FF_WIDTH_FIX_B:-6, OFFSET_A:2};
var $_E = function () {
	var C = [];
	for (var B = 0; B < arguments.length; B++) {
		var A = arguments[B];
		if (typeof (A) == "string") {
			var D = A;
			A = document.getElementById(D);
			if (A == null) {
				A = document.getElementsByName(D);
				if (A.length > 0) {
					A = A[0];
				} else {
					A = null;
				}
			}
		}
		if (arguments.length == 1) {
			return A;
		}
		C.push(A);
	}
	return C;
};
var ECSideList = {};
var ECSide = function (A) {
	var B = this;
	B.findAjaxZoneAtClient = true;
	B.ETI_ID = ECSideConstants.ETI_ID;
	B.ETI_PAGE_FLAG = ECSideConstants.ETI_PAGE_FLAG;
	B.MIN_COL_WIDTH = 80;
	B.onLoad = null;
	if (!A || A == "") {
		A = ECSideConstants.EC_ID;
	}
	B.id = A;
	B.EXPORT_IFRAME_ID = A + ECSideConstants.EXPORT_IFRAME_SUFFIX;
	B.SHADOWROW_ID = A + ECSideConstants.SHADOW_ROW;
	B.ECForm = null;
	B.ECMainContent = null;
	B.selectedRow = null;
	B.sortedColumn = null;
	B.sortType = "default";
	B.sortedColumnHearderId = null;
	B.afterFillForm = null;
	B.resizeWay = "resizeTable";
	B.listWidth = 0;
	B.isClassic = false;
	B.canResizeColWidth = false;
	B.useAjax = true;
	B.doPreload = true;
	B.doPreloadPrev = false;
	B.doPrep = "temp";
	B.doPrepPrev = "temp";
	B.isDebug = false;
	B.prepState = {next:0, prev:0};
	B.prepPage = {next:0, prev:0};
	B.prepareaName = {};
	B.pageFieldName = B.id + ECSideConstants.PAGEFIELD_SUFFIX;
	B.totalPagesFieldName = B.id + "_totalpages";
	B.prepareaName["next"] = B.id + "_ec_preparea_n";
	B.prepareaName["prev"] = B.id + "_ec_preparea_p";
	B.scrollList = false;
	B.orgListHeight = 0;
	B.listHeight = 0;
	B.minHeight = 0;
	B.columnNum = 0;
	ECSideList[A] = B;
	B.buildPrepArea = function () {
		if (!B.doPreload) {
			return;
		}
		var E = document.getElementById(this.prepareaName["next"]);
		if (!E) {
			var D = document.createElement("textarea");
			D.id = this.prepareaName["next"];
			D.disabled = true;
			D.style.display = "none";
			document.body.appendChild(D);
		}
		var F = document.getElementById(this.prepareaName["prev"]);
		if (!F) {
			var C = document.createElement("textarea");
			C.id = this.prepareaName["prev"];
			C.disabled = true;
			C.style.display = "none";
			document.body.appendChild(C);
		}
		if (B.isDebug) {
			D = document.getElementById(this.prepareaName["next"]);
			C = document.getElementById(this.prepareaName["prev"]);
			D.disabled = false;
			D.style.display = "inline";
			D.rows = 10;
			D.cols = 50;
			C.disabled = false;
			C.style.display = "inline";
			C.rows = 10;
			C.cols = 50;
		}
	};
	B.goPage = function () {
		var C = $_E(B.pageFieldName).value;
		var F = null;
		if (C == B.prepPage["next"] && B.prepState["next"] == 2) {
			F = "next";
		} else {
			if (C == B.prepPage["prev"] && B.prepState["prev"] == 2 && B.doPreloadPrev) {
				F = "prev";
			}
		}
		if (F !== null) {
			try {
				var G = $_E(B.prepareaName[F]).value;
				if (G == "") {
					$_E(B.id).submit();
					return;
				}
				B.ECMainContent.innerHTML = G;
				B.prepState[F] = 0;
				B.init();
				var D = {};
				D.responseText = G;
				if (B.afterFillForm && typeof (B.afterFillForm) == "function") {
					B.afterFillForm(D);
				}
				window.setTimeout(B.ajaxPrepSubmit, 10);
				if (typeof (B.onLoad) == "function") {
					B.onLoad();
				}
				B.handleResize();
				B.hideWaitingBar();
			}
			catch (E) {
				$_E(B.pageFieldName).value = C;
				B.ajaxSubmit();
			}
		} else {
			B.ajaxSubmit();
		}
	};
	B.dealResponse = {"next":function (C) {
		$_E(B.prepareaName["next"]).value = ECSideUtil.cutText(C.responseText, B.id);
		B.prepState["next"] = 2;
		B.doingAjaxSubmit = false;
	}, "prev":function (C) {
		$_E(B.prepareaName["prev"]).value = ECSideUtil.cutText(C.responseText, B.id);
		B.prepState["prev"] = 2;
		B.doingAjaxSubmit = false;
	}};
	B.ajaxPrepSubmit = function () {
		if (!B.doPreload) {
			return;
		}
		B.ajaxPrep(1);
		B.ajaxPrep(-1);
	};
	B.ajaxPrep = function (D) {
		var C;
		if (D == 1) {
			C = "next";
		} else {
			if (D == -1 && B.doPreloadPrev) {
				C = "prev";
			} else {
				return;
			}
		}
		B.prepState[C] = 1;
		B.prepPage[C] = $_E(B.pageFieldName).value / 1 + D;
		if (B.prepPage[C] < 1 || B.prepPage[C] > ($_E(B.totalPagesFieldName).value / 1)) {
			return;
		}
		$_E(B.pageFieldName).value = B.prepPage[C];
		B.ajaxSubmit(B.dealResponse[C], true);
		$_E(B.pageFieldName).value = B.prepPage[C] - D;
	};
	B.doingAjaxSubmit = false;
	B.ajaxSubmit = function (D, C, E) {
		if (!B.useAjax) {
			B.ECForm.submit();
			return;
		}
		if (!C) {
			C = false;
		}
		if (!D) {
			D = B.fillForm;
		}
		if (!C && B.doingAjaxSubmit) {
		}
		B.doingAjaxSubmit = true;
		if (B.findAjaxZoneAtClient === false) {
			if (!E) {
				E = {};
			}
			if (typeof (E) == "string") {
				E += "&findAjaxZoneAtClient=false&";
			} else {
				E.findAjaxZoneAtClient = "false";
			}
		}
		Form.request(B.ECForm, {method:"post", requestHeaders:ECSideConstants.AJAX_HEADER, asynchronous:C, parameters:E, onComplete:D});
	};
	B.fillForm = function (C) {
		var D = ECSideUtil.cutText(C.responseText, B.id);
		if (D == "") {
			B.hideWaitingBar();
			return;
		}
		if (D.indexOf("Exception:") == 0) {
			B.hideWaitingBar();
			B.ECMainContent.innerHTML = D;
			return;
		}
		B.ECMainContent.innerHTML = D;
		B.init();
		if (B.afterFillForm && typeof (B.afterFillForm) == "function") {
			B.afterFillForm(C);
		}
		B.doingAjaxSubmit = false;
		B.handleResize();
		B.hideWaitingBar();
	};
	B.currentShadowRowParent = null;
	B.currentShadowEventSrc = null;
	B.autoCloseOtherShadowRow = true;
	B.getTotalPage = function () {
		return B.ECForm[B.totalPagesFieldName].value / 1;
	};
	B.getPageNo = function () {
		if (!B.ECForm[B.pageFieldName]) {
			return 1;
		}
		return B.ECForm[B.pageFieldName].value / 1;
	};
	B.setPageNo = function (C) {
		B.ECForm[B.pageFieldName] = C;
	};
	B.showShadowRowCallBack = function (C, E, F, D) {
	};
	B.hideShadowRowCallBack = function (C, E, F, D) {
	};
	B.firstShowShadowRowCallBack = function (C, E, F, D) {
	};
	B.beforeFilter = function (C) {
	};
	B.beforeSave = function (C) {
	};
	B.beforeSubmit = function (C, E, F, D) {
	};
	B.init = function () {
		B.ECForm = document.getElementById(B.id);
		if (!B.ECForm) {
			return;
		}
		B.doPreload = B.doPrep == "temp" ? B.doPreload : B.doPrep;
		B.doPreloadPrev = B.doPrepPrev == "temp" ? B.doPreloadPrev : B.doPrepPrev;
		B.DEFAULT_ACTION = B.ECForm.getAttribute("action");
		B.ECMainContent = document.getElementById(B.id + "_main_content");
		if (window.isIE) {
			var D = document.getElementById(B.id + ECSideConstants.HIDE_HEADER_ROW);
			if (D) {
				D.style.display = "none";
			}
		}
		if (window.frameElement && window.frameElement.name == B.EXPORT_IFRAME_ID) {
			B.ECForm.style.visibility = "visible";
			ECSideUtil.printFrame(window.frameElement.contentWindow);
			return;
		}
		if (B.sortedColumnHearderId) {
			var E = document.getElementById(B.sortedColumnHearderId);
			if (E && B.sortType && B.sortType != "" && B.sortType != "default") {
				var C = ECSideUtil.trimString(E.innerHTML, -1) + "&#160;<div class=\"sort" + B.sortType.toUpperCase() + "\"></div>";
				E.innerHTML = C;
			}
		}
		if (!B.useAjax) {
			B.doPreload = false;
		}
		if (typeof (B.ajaxSubmit) != "function") {
			B.useAjax = false;
			B.ajaxSubmit = function () {
				B.ECForm.submit();
			};
		}
		if (B.useAjax) {
			B.buildPrepArea();
			B.ajaxPrepSubmit();
		}
		B.ECListHeadZone = document.getElementById(B.id + ECSideConstants.HEAD_ZONE_SUFFIX);
		B.ECListBodyZone = document.getElementById(B.id + ECSideConstants.BODY_ZONE_SUFFIX);
		B.ECListHead = document.getElementById(B.id + "_table_head");
		B.ECListBody = document.getElementById(B.id + "_table_body");
		B.ECListFoot = document.getElementById(B.id + "_table_foot");
		B.ECListToolbarTable = document.getElementById(B.id + "_toolbarTable");
		B.ECListToolbarShadow = document.getElementById(B.id + "_toolbarShadow");
		if (B.ECListToolbarTable) {
			B.ECListToolbarShadow.style.height = B.ECListToolbarTable.offsetHeight + 2 + "px";
		}
		B.orgListHeight = B.ECListBody.scrollHeight;
		B.initWaitingBar();
		B.initList();
		B.columnHandler();
		B.listWidth = B.ECListHead.parentNode.clientWidth;
		B.listHeight = B.orgListHeight;
		B.ECForm.style.visibility = "visible";
	};
	B.waitingBar = null;
	B.waitingBarCore = null;
	B.waitingShowTimes = 0;
	B.initWaitingBar = function () {
		B.waitingShowTimes = 0;
		B.waitingBar = document.getElementById(B.id + "_waitingBar");
		B.waitingBar.setAttribute("big", "false");
		B.waitingBarCore = document.getElementById(B.id + "_waitingBarCore");
		B.waitingBarCore.innerHTML = ECSideMessage.WAITTING_MSG;
		var C = ECSideUtil.getPosLeft(B.ECForm);
		var D = ECSideUtil.getPosTop(B.ECForm);
		B.waitingBar.style.left = C + "px";
		B.waitingBar.style.top = D + "px";
	};
	B.resizeWaitinBar = function () {
		if (B.waitingBar && B.waitingBar.getAttribute("big") == "true") {
			var D = B.ECForm.offsetWidth;
			var F = B.ECForm.offsetHeight;
			var C = B.waitingBarCore.offsetWidth;
			var E = B.waitingBarCore.offsetHeight;
			B.waitingBar.style.width = D + "px";
			B.waitingBar.style.height = F - 2 + "px";
			B.waitingBarCore.style.left = parseInt(B.waitingBar.style.left) + (D - C - 50) / 2 + "px";
			B.waitingBarCore.style.top = parseInt(B.waitingBar.style.top) + (F - E - 40) / 2 + "px";
		}
	};
	B.showWaitingBar = function () {
		B.waitingShowTimes++;
		B.waitingBar.style.height = "";
		B.waitingBar.style.width = "";
		B.waitingBar.setAttribute("big", "false");
		if (B.ECForm[B.id + "_rd"]) {
			B.ECForm[B.id + "_rd"].style.display = "";
		}
		B.waitingBarCore.style.left = B.waitingBar.style.left;
		B.waitingBarCore.style.top = B.waitingBar.style.top;
		B.waitingBar.style.display = "block";
		B.waitingBarCore.style.display = "block";
	};
	B.showBigWaitingBar = function () {
		B.waitingShowTimes++;
		B.waitingBar.setAttribute("big", "true");
		B.resizeWaitinBar();
		if (B.ECForm[B.id + "_rd"]) {
			B.ECForm[B.id + "_rd"].style.display = "none";
		}
		B.waitingBar.style.display = "block";
		B.waitingBarCore.style.display = "block";
	};
	B.hideWaitingBar = function () {
		B.waitingShowTimes--;
		if (B.waitingShowTimes < 1) {
			B.waitingBar.setAttribute("big", "false");
			B.waitingBar.style.display = "none";
			B.waitingBarCore.style.display = "none";
			B.waitingShowTimes = 0;
			if (B.ECForm[B.id + "_rd"]) {
				B.ECForm[B.id + "_rd"].style.display = "";
			}
		}
	};
	B.autoFitHeight = function () {
		if (B.ECListBodyZone.offsetHeight >= B.ECListBody.parentNode.scrollHeight) {
			var D = B.ECListBodyZone.offsetHeight - B.ECListBodyZone.clientHeight + ECSideConstants.LIST_HEIGHT_FIXED;
			if (D <= 2 && B.ECListBodyZone.offsetWidth - B.ECListBodyZone.clientWidth > 2) {
				D = ECSideConstants.SCROLLBAR_WIDTH;
			}
			var C = B.ECListBody.parentNode.scrollHeight + D;
			C = C < B.minHeight / 1 ? B.minHeight / 1 : C;
			B.ECListBodyZone.style.height = C + "px";
		}
	};
	B.columnHandler = function () {
		B.MIN_COL_WIDTH = B.ECForm.getAttribute("minColWidth");
		B.canResizeColWidth = B.ECForm.getAttribute("canResizeColWidth");
		if (B.canResizeColWidth == "true" || B.canResizeColWidth === true) {
			B.canResizeColWidth = true;
			ECSideUtil.initSeparateLine();
			B.ECListHead.parentNode.style.tableLayout = "fixed";
			B.ECListBody.parentNode.style.tableLayout = "fixed";
			ECSideUtil.resizeInit();
		} else {
			B.canResizeColWidth = false;
		}
		if (B.ECListHead && B.ECListHead.rows) {
			var C = B.ECListHead.rows[0].cells;
			B.columnNum = C.length;
			for (var D = 0; D < B.columnNum; D++) {
				if (C[D].getAttribute("group") == "true") {
					ECSideUtil.groupByCol(B.ECListBody.rows, D);
				}
			}
		}
	};
	B.handleScroll = function () {
		B.ECListHeadZone.scrollLeft = B.ECListBodyZone.scrollLeft;
	};
	B.resizeHeader = function () {
		var D = window.isIE ? 2 : 0;
		var C = B.ECListBodyZone.clientWidth;
		var E = B.ECListHeadZone.clientWidth;
		B.ECListHeadZone.style.width = B.ECListBodyZone.clientWidth + D + "px";
	};
	B.initList = function () {
		if (!B.ECListHeadZone || !B.ECListBodyZone) {
			B.isClassic = true;
		}
		if (!B.isClassic) {
			B.resizeWay = "resizeTable";
			ECSideUtil_addEvent(B.ECListBodyZone, "scroll", B.handleScroll);
			ECSideUtil_addEvent(window, "resize", B.handleResize);
			if (window.isIE) {
				B.ECListHead.parentNode.style.tableLayout = "fixed";
				ECSideUtil_addEvent(B.ECListBodyZone, "resize", B.handleResize);
			} else {
			}
			B.autoFitHeight();
			B.handleResize();
			B.orgListHeight = ECSideUtil.parseIntOrZero(B.ECListBodyZone.style.height);
		}
	};
	B.resized = 0;
	B.handleResize = function () {
		B.resizeWaitinBar();
		if (B.isClassic || !B.ECListBodyZone || !B.ECListHeadZone) {
			return;
		}
		var C = B.ECListBodyZone.clientWidth;
		if (window.isIE && B.resized > 0) {
			B.resizeHeader();
			B.resized = 0;
			return;
		}
		B.autoFitHeight();
		if (window.isIE && B.ECListToolbarTable) {
			if (B.ECListBodyZone.offsetWidth + 1 < B.ECListToolbarTable.clientWidth) {
				B.ECListToolbarShadow.style.display = "block";
				B.ECListToolbarTable.parentNode.style.position = "absolute";
			} else {
				B.ECListToolbarShadow.style.display = "none";
				B.ECListToolbarTable.parentNode.style.position = "static";
			}
		}
		C = B.ECListBodyZone.clientWidth;
		B.resizeHeader();
		ECSideUtil.syncRowsWidth(B.ECListHead.rows, B.ECListBody.rows);
		B.resized = 1;
	};
	B.updateCallBack = function (E) {
		var D = ECSideUtil.responseHandler(E);
		var H = ECSideUtil.trimString(D[0]);
		var J = ECSideUtil.getGridObj(H);
		for (var F = 1; F < D.length; F += 3) {
			var C = ECSideUtil.trimString(D[F] + "");
			if (C == "END OF org.ecside.defaultAjaxResopnse") {
				break;
			}
			var I = ECSideUtil.trimString(D[F + 1] + "");
			var G = ECSideUtil.trimString(D[F + 2] + "");
			if (C == "1" || C == "Success") {
				ECSideUtil.getRemoveUpdatedClassRows(J.forUpdateRows, I);
			} else {
				if (G.length > 1) {
					alert(G);
				}
			}
		}
		J.hideWaitingBar();
	};
	B.insertCallBack = function (E) {
		var D = ECSideUtil.responseHandler(E);
		var H = ECSideUtil.trimString(D[0]);
		var J = ECSideUtil.getGridObj(H);
		for (var F = 1; F < D.length; F += 3) {
			var C = ECSideUtil.trimString(D[F] + "");
			if (C == "END OF org.ecside.defaultAjaxResopnse") {
				break;
			}
			var I = ECSideUtil.trimString(D[F + 1] + "");
			var G = ECSideUtil.trimString(D[F + 2] + "");
			if (C == "1" || C == "Success") {
				ECSideUtil.getRemoveInsertedClassRows(J.forInsertRows, I);
			} else {
				if (G.length > 1) {
					alert(G);
				}
			}
		}
		J.hideWaitingBar();
	};
	B.deleteCallBack = function (E) {
		var D = ECSideUtil.responseHandler(E);
		var H = ECSideUtil.trimString(D[0]);
		var J = ECSideUtil.getGridObj(H);
		for (var F = 1; F < D.length; F += 3) {
			var C = ECSideUtil.trimString(D[F] + "");
			if (C == "END OF org.ecside.defaultAjaxResopnse") {
				break;
			}
			var I = ECSideUtil.trimString(D[F + 1] + "");
			var G = ECSideUtil.trimString(D[F + 2] + "");
			if (C == "1" || C == "Success") {
				ECSideUtil.getRemoveDeletedRows(J.forDeleteRows, I);
			} else {
				if (G.length > 1) {
					alert(G);
				}
			}
		}
		J.hideWaitingBar();
	};
};
var ECSideUtil = {};
ECSideUtil.responseHandler = function (C) {
	var A = C.responseText;
	A = ECSideUtil.trimString(A);
	var B = A.split("\n");
	return B;
};
ECSideUtil.syncRowsWidth = function (L, K) {
	var J = 0;
	var D = 0;
	if (L && K && L.length > 0 && K.length > 0) {
		var G = L[0].cells;
		var I = K[0].cells;
		if (window.isIE) {
			for (var E = 0; E < G.length; E++) {
				G[E].style.width = J + I[E].offsetWidth + "px";
			}
		} else {
			var B = G.length;
			for (var E = 0; E < B; E++) {
				var H;
				if (I[E].width && I[E].width.length > 0) {
					H = parseInt(I[E].width);
				} else {
					H = parseInt(I[E].style.width);
				}
				if (isNaN(H)) {
					continue;
				}
				var F = 0;
				F = G[E].getAttribute("resizeColWidth") == "true" ? 2 : (E == 0 ? 0 : (E + 1 == B ? 2 : 0));
				G[E].style.width = (H + D + F) + "px";
				var C = I[E].clientWidth - G[E].clientWidth;
				var A = I[E].offsetWidth - G[E].offsetWidth;
				if (G[E].getAttribute("resizeColWidth") != "true" && A != 0) {
					G[E].style.width = (H + D + F) + A + "px";
				}
			}
		}
	}
};
ECSideUtil.resizeAllGird = function () {
	for (var B in ECSideList) {
		var A = ECSideList[B];
		A.handleResize();
	}
};
ECSideUtil.getGridObj = function (A) {
	if (!A) {
		A = ECSideConstants.EC_ID;
	}
	return ECSideList[A];
};
ECSideUtil.getECSideForm = function (A) {
	var B = ECSideUtil.getGridObj(A);
	if (B) {
		return B.ECForm;
	}
	return null;
};
ECSideUtil.getMessage = function (A, D) {
	var C = ECSideMessage[A];
	for (var B = 1; B < arguments.length; B++) {
		C = ECSideUtil.replaceAll(C, "#{" + B + "}", arguments[B]);
	}
	return C;
};
ECSideUtil.getTotalPages = function (B) {
	var A = ECSideUtil.getECSideForm(B);
	try {
		return A[B + "_totalpages"].value;
	}
	catch (C) {
		return -1;
	}
};
ECSideUtil.getTotalRows = function (B) {
	var A = ECSideUtil.getECSideForm(B);
	try {
		return A[B + "_totalrows"].value;
	}
	catch (C) {
		return -1;
	}
};
ECSideUtil.clearTotalRows = function (B) {
	try {
		var A = ECSideUtil.getECSideForm(B);
		A[B + "_totalrows"].value = "";
	}
	catch (C) {
	}
};
ECSideUtil.cutText = function (F, C) {
	var G = ECSideUtil.getGridObj(C);
	if (F.responseText) {
		F = F.responseText;
	}
	if (G && !G.findAjaxZoneAtClient) {
		return F;
	}
	var B = ECSideConstants.AJAX_ZONE_PREFIX + ECSideConstants.AJAX_ZONE_BEGIN + C + ECSideConstants.AJAX_ZONE_SUFFIX;
	var A = ECSideConstants.AJAX_ZONE_PREFIX + ECSideConstants.AJAX_ZONE_END + C + ECSideConstants.AJAX_ZONE_SUFFIX;
	var E = F.indexOf(B);
	if (E != -1) {
		E += B.length;
		var D = F.indexOf(A, E);
		if (D != -1) {
			return F.substring(E, D);
		}
	}
	return F;
};
ECSideUtil.noExport = function (C) {
	var E = ECSideUtil.getGridObj(C);
	var A;
	var B;
	if (!E) {
		A = ECSideConstants.ETI_ID;
		B = document.getElementById(C);
	} else {
		A = E.ETI_ID;
		B = E.ECForm;
	}
	try {
		B[A].value = "";
	}
	catch (D) {
	}
};
ECSideUtil.refresh = function (B) {
	var D = ECSideUtil.getGridObj(B);
	var A;
	if (!D) {
		A = document.getElementById(B);
	} else {
		A = D.ECForm;
	}
	try {
		A[B + "_totalrows"].value = "";
	}
	catch (C) {
	}
};
ECSideUtil.reload = function (A, C) {
	ECSideUtil.noExport(A);
	ECSideUtil.refresh(A);
	var B = ECSideUtil.getGridObj(A);
	if (!C) {
		C = B.getPageNo();
	}
	ECSideUtil.gotoPage(C, A);
	ECSideUtil.showShadowRow(B.currentShadowRowParent, B.currentShadowEventSrc, A);
};
ECSideUtil.gotoPage = function (H, G) {
	var I = ECSideUtil.getGridObj(G);
	I.showWaitingBar();
	try {
		ECSideUtil.NearPagesBar.doHideMe();
	}
	catch (D) {
	}
	var B = I.pageFieldName;
	var A = I.ECForm;
	if (!A[B]) {
		var F = document.createElement("input");
		F.id = B;
		F.style.display = "none";
		A.appendChild(F);
	}
	A[B].value = H;
	ECSideUtil.noExport(G);
	A.action = I.DEFAULT_ACTION;
	try {
		if (I.doPreload) {
			I.goPage();
		} else {
			I.ajaxSubmit();
		}
	}
	catch (C) {
		try {
			I.ajaxSubmit();
		}
		catch (E) {
			A.submit();
		}
	}
};
ECSideUtil.gotoPageByInput = function (C, E) {
	var D = ECSideList[E].ECForm;
	var B = null;
	if (C.type != "text") {
		B = C.nextSibling;
		if (B.type != "text") {
			B = C.previousSibling;
		}
		C = B;
	}
	var F = C.value / 1;
	var A = D[E + "_totalpages"].value / 1;
	if (!isFinite(F) || (F + "").indexOf(".") != -1 || F < 1 || F > A) {
		alert(ECSideUtil.getMessage("ERR_PAGENO", A));
		C.focus();
		C.select();
		return;
	}
	if (F < 1) {
		F = 1;
	}
	ECSideUtil.gotoPage(F, E);
};
ECSideUtil.doSort = function (A, F, L, M, B) {
	var J = A || window.event;
	if (ECSideUtil.startDragobj == true || ECSideUtil.Dragobj) {
		return;
	}
	
	if (window.isIE && J.button > 1) {
		return;
	}
	
	var N = "asc";
	var G = "desc";
	var E = "default";
	var O = ECSideUtil.getGridObj(M);
	var D = O.pageFieldName;
	var C = O.ECForm;
	if (typeof (L) != "string") {
		B = L.id;
		L = null;
	} else {
		if (B && typeof (B) != "string") {
			B = B.id;
		}
	}
	var I = "default";
	if (L) {
		I = L;
	} else {
		if (O.sortedColumn == F) {
			if (!O.sortType || O.sortType == "default") {
				I = "asc";
			} else {
				if (O.sortType == "asc") {
					I = "desc";
				} else {
					if (O.sortType == "desc") {
						I = "default";
					} else {
						I = "asc";
					}
				}
			}
		} else {
			I = "asc";
		}
	}
	ECSideUtil.noExport(M);
	var H = C.action;
	C.action = O.DEFAULT_ACTION;
	if (O.sortedColumn && O.sortedColumn != "") {
		C[M + ECSideConstants.SORT_PREFIX + O.sortedColumn].value = "";
	}
	O.sortedColumn = F;
	O.sortType = I;
	O.sortedColumnHearderId = B;
	if (O.custSort) {
		O.custSort(F, I);
	} else {
		C[M + ECSideConstants.SORT_PREFIX + F].value = I;
	}
	try {
		O.ajaxSubmit();
		C.action = H;
	}
	catch (K) {
		C.submit();
	}
	ECSideUtil.ColmunMenu.doHideMe();
};
ECSideUtil.doCustomExport = function (G, F, D) {
	var C = ECSideUtil.getECSideForm(D);
	var B = C.target;
	var A = C.action;
	C[D + "_efn"].value = G;
	C.action = F;
	var E = ECSideList[D].EXPORT_IFRAME_ID;
	E = document.getElementById(E);
	if (E) {
		C.target = ECSideList[D].EXPORT_IFRAME_ID;
	}
	C.submit();
	C.target = B;
	C.action = A;
	ECSideUtil.noExport(D);
};
ECSideUtil.doExportList = function (D, C, B) {
	var A = "xls";
	ECSideUtil.doExport(A, D, C, B);
};
ECSideUtil.doExport = function (F, B, E, G) {
	if (arguments.length > 4) {
		F = arguments[4];
		B = arguments[5];
	}
	var K = ECSideUtil.getGridObj(G);
	var J = K.ETI_ID;
	var C = K.ETI_PAGE_FLAG;
	var A = K.ECForm;
	if (E == true) {
		E = true;
	} else {
		E = false;
	}
	E = !confirm(ECSideMessage.EXPORT_CONFIRM);
	var D = A.getAttribute("maxRowsExported");
	if (E === false && D && ECSideUtil.parseIntOrZero(D) > 0) {
		if (ECSideUtil.parseIntOrZero(D) < ECSideUtil.getTotalRows(G)) {
			alert(ECSideUtil.getMessage("OVER_MAXEXPORT", ECSideUtil.parseIntOrZero(D)));
			return;
		}
	}
	A[G + "_ev"].value = F;
	A[G + "_efn"].value = B;
	A[J].value = G;
	if (E === true) {
		A[C].value = "true";
	} else {
		A[C].value = "";
	}
	var I = A.target;
	A.action = K.DEFAULT_ACTION;
	var H = K.EXPORT_IFRAME_ID;
	H = document.getElementById(H);
	if (H) {
		A.target = K.EXPORT_IFRAME_ID;
	}
	A.submit();
	A.target = I;
	ECSideUtil.noExport(G);
};
ECSideUtil.changeRowsDisplayed = function (C, F) {
	var E = ECSideUtil.getGridObj(C);
	var A = E.pageFieldName;
	var B = E.ECForm;
	B[C + "_crd"].value = F.options[F.selectedIndex].value;
	B[A].value = "1";
	ECSideUtil.noExport(C);
	B.action = E.DEFAULT_ACTION;
	try {
		E.ajaxSubmit();
	}
	catch (D) {
		B.submit();
	}
};
ECSideUtil.checkAll = function (A, B, E) {
	var D = ECSideList[E].ECForm;
	if (!D.elements[B]) {
		return;
	}
	var C = false;
	if (A.className == "checkedboxHeader") {
		C = true;
		A.className = "checkboxHeader";
	} else {
		A.className = "checkedboxHeader";
	}
	if (!D.elements[B].length) {
		if (!D.elements[B].disabled) {
			D.elements[B].checked = !C;
		}
		return;
	}
	for (i = 0; i < D.elements[B].length; i++) {
		if (!D.elements[B][i].disabled) {
			D.elements[B][i].checked = !C;
		}
	}
};
ECSideUtil.selectRow = function (B, C) {
	var A = ECSideConstants.ROW_SELECTLIGHT_CLASS;
	var D = ECSideUtil.getGridObj(C);
	if (!D || B == D.selectedRow) {
		return;
	}
	ECSideUtil.addClass(B, A);
	ECSideUtil.removeClass(D.selectedRow, A);
	D.selectedRow = B;
};
ECSideUtil.lightRow = function (A, B) {
	ECSideUtil.addClass(A, ECSideConstants.ROW_HIGHLIGHT_CLASS);
};
ECSideUtil.unlightRow = function (A, B) {
	ECSideUtil.removeClass(A, ECSideConstants.ROW_HIGHLIGHT_CLASS);
};
ECSideUtil.lightHeader = function (A, C) {
	var B = A.className;
	if (B) {
		B = B.split(" ");
		B[0] += "Over";
	}
	A.className = B.join(" ");
};
ECSideUtil.unlightHeader = function (A, C) {
	var B = A.className;
	if (B) {
		B = B.split(" ");
		if (B[0].lastIndexOf("Over") == B[0].length - "Over".length) {
			B[0] = B[0].substring(0, B[0].length - "Over".length);
		}
	}
	A.className = B.join(" ");
};
ECSideUtil.getFirstChildElement = function (B) {
	var D = 0;
	try {
		var A = -1;
		while (A != 1 && D < B.childNodes.length) {
			A = B.childNodes[D].nodeType;
			D++;
		}
		D--;
		return B.childNodes[D];
	}
	catch (C) {
		return B.childNodes[0];
	}
};
ECSideUtil.getNextElement = function (A) {
	if (!A) {
		return null;
	}
	var B = A.nextSibling;
	while (B != null) {
		if (B.nodeType == 1) {
			return B;
		}
		B = B.nextSibling;
	}
	return null;
};
ECSideUtil.getShadowRow = function (D, C) {
	var F = ECSideUtil.getGridObj(C);
	var A = D.getAttribute("hasShadow");
	var E = null;
	if (A == "true") {
		var B = D.rowIndex;
		if (F.scrollList) {
			B++;
		}
		E = D.parentNode.rows[B];
	}
	return E;
};
ECSideUtil.ajaxRequest = function () {
};
ECSideUtil.operateECForm = function (C, E, I, J, H) {
	var K = ECSideUtil.getGridObj(H);
	K.showWaitingBar();
	var B = K.pageFieldName;
	var A = K.ECForm;
	if (!J) {
		J = true;
	}
	var F = "";
	if (I) {
		if (typeof (I) == "string") {
			F = I;
		} else {
			for (var D in I) {
				if (!A[D]) {
					F = F + D + "=" + I[D] + "&";
				} else {
					A[D].value = I[D];
				}
			}
		}
	}
	ECSideUtil.refresh(H);
	A[B].value = 1;
	ECSideUtil.noExport(H);
	A.action = C;
	try {
		K.ajaxSubmit(E, J, F);
	}
	catch (G) {
		A.submit();
	}
	try {
		A.action = K.DEFAULT_ACTION;
	}
	catch (G) {
	}
};
ECSideUtil.queryECForm = function (D, F, C) {
	if (C == null || C == window.undefined) {
		C = true;
	}
	var E = ECSideUtil.getGridObj(D);
	E.showWaitingBar();
	var A = E.pageFieldName;
	var B = E.ECForm;
	ECSideUtil.refresh(D);
	B[A].value = 1;
	ECSideUtil.noExport(D);
	E.ajaxSubmit(E.fillForm, C, F);
};
ECSideUtil.printFrame = function (E, A, D) {
	if (!E) {
		E = window;
	}
	if (!A) {
		A = "";
	}
	E.document.title = A;
	function B() {
		switch (typeof (D)) {
		  case "string":
			execScript(D);
			break;
		  case "function":
			D();
		}
		if (C && !C.disabled) {
			C.focus();
		}
		E.location = "about:blank";
	}
	if (E.document.readyState !== "complete" && !confirm("The document to print is not downloaded yet! Continue with printing?")) {
		B();
		return;
	}
	if (E.print) {
		var C = document.activeElement;
		E.focus();
		E.print();
		B();
		return;
	} else {
		alert("the PRINT for IE 5.0+ Only");
	}
};
function ECSideUtil_addEvent(C, B, A) {
	if (C.attachEvent) {
		C["e" + B + A] = A;
		C[B + A] = function () {
			C["e" + B + A](window.event);
		};
		C.attachEvent("on" + B, C[B + A]);
	} else {
		if (C.addEventListener) {
			C.addEventListener(B, A, false);
		}
	}
}
function ECSideUtil_stopEvent(A) {
	if (A.stopPropagation) {
		A.stopPropagation();
		A.preventDefault();
	} else {
		A.returnValue = false;
		A.cancelBubble = true;
	}
	return false;
}
function ECSideUtil_removeEvent(C, B, A) {
	if (C.detachEvent) {
		C.detachEvent("on" + B, C[B + A]);
		C[B + A] = null;
		C["e" + B + A] = null;
	} else {
		if (C.removeEventListener) {
			C.removeEventListener(B, A, false);
		}
	}
}
ECSideUtil.trimString = function (C, A) {
	if (!C.replace) {
		return C;
	}
	if (!C.length) {
		return C;
	}
	var B = (A > 0) ? (/^\s+/) : (A < 0) ? (/\s+$/) : (/^\s+|\s+$/g);
	return C.replace(B, "");
};
ECSideUtil.getPosTop = function (B) {
	var A = B.offsetTop;
	while ((B = B.offsetParent) != null) {
		A += (B.offsetTop - B.scrollTop);
	}
	return A;
};
ECSideUtil.getPosLeft = function (B) {
	var A = B.offsetLeft;
	while ((B = B.offsetParent) != null) {
		A += (B.offsetLeft - B.scrollLeft);
	}
	return A;
};
ECSideUtil.getPosRight = function (A) {
	return ECSideUtil.getPosLeft(A) + A.offsetWidth;
};
ECSideUtil.getPosBottom = function (A) {
	return ECSideUtil.getPosTop(A) + A.offsetHeight;
};
ECSideUtil.replaceAll = function (exstr, ov, value) {
	var gc = ECSideUtil.escapeRegExp(ov);
	if (gc == null || gc == "") {
		return exstr;
	}
	var reReplaceGene = "/" + gc + "/gm";
	var r = null;
	var cmd = "r=exstr.replace(" + reReplaceGene + "," + ECSideUtil.escapeString(value) + ")";
	eval(cmd);
	return r;
};
ECSideUtil.escapeRegExp = function (A) {
	return !A ? "" + A : ("" + A).replace(/\\/gm, "\\\\").replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm, "\\$1");
};
ECSideUtil.escapeString = function (A) {
	return !A ? "" + A : ("\"" + ("" + A).replace(/(["\\])/g, "\\$1") + "\"").replace(/[\f]/g, "\\f").replace(/[\b]/g, "\\b").replace(/[\n]/g, "\\n").replace(/[\t]/g, "\\t").replace(/[\r]/g, "\\r");
};
ECSideUtil.hasClass = function (A, B) {
	if (!A.className) {
		return false;
	}
	return (A.className.search("(^|\\s)" + B + "(\\s|$)") != -1);
};
ECSideUtil.removeClass = function (A, B) {
	if (!A) {
		return;
	}
	A.className = A.className.replace(new RegExp("(^|\\s)" + B + "(\\s|$)"), " ");
};
ECSideUtil.addClass = function (A, B) {
	if (!A || ECSideUtil.hasClass(A, B)) {
		return;
	}
	if (A.className) {
		A.className += " " + B;
	} else {
		A.className = B;
	}
};
ECSideUtil.parseIntOrZero = function (A) {
	return ECSideUtil.parseInt(A, 0);
};
ECSideUtil.parseIntOrOne = function (A) {
	return ECSideUtil.parseInt(A, 1);
};
ECSideUtil.parseInt = function (A, C) {
	var B = parseInt(A);
	return isNaN(B) ? C : B;
};
ECSideUtil.isCollection = function (A) {
	return A && typeof (A) != "string" && typeof (A.length) == "number";
};
ECSideUtil.appendMap = function (A, C) {
	for (var B in C) {
		if (B in A) {
			if (A[B].constructor != Array) {
				A[B] = [A[B]];
			}
			A[B].push(C[B]);
		} else {
			A[B] = C[B];
		}
	}
};
ECSideUtil.groupByCol = function (K, I, C, J) {
	if (!I) {
		I = 0;
	}
	if (!C || C < 0) {
		C = 0;
	}
	if (!J || J > K.length) {
		J = K.length;
	}
	var G = "";
	var E = "none";
	var H = null;
	var B = [];
	var F = K[C].cells[I];
	var A = F.rowSpan;
	for (var D = C + 1; D < J; D++) {
		H = K[D].cells[I];
		if (H.style.display == E) {
			A += H.rowSpan;
		} else {
			F.rowSpan = A;
			F = H;
			A = H.rowSpan;
		}
	}
	F.rowSpan = A;
	return B;
};
ECSideUtil.ungroupByCol = function (H, G, D, B) {
	if (!G) {
		G = 0;
	}
	if (!D || D < 0) {
		D = 0;
	}
	if (!B || B > H.length) {
		B = H.length;
	}
	var C = "";
	var F = "none";
	var A = null;
	for (var E = D; E < B; E++) {
		A = H[E].cells[G];
		if (A.style.display == F) {
			A.style.display = C;
		} else {
			if (A.rowSpan > 1) {
				A.rowSpan = 1;
			}
		}
	}
};
ECSideUtil.initSeparateLine = function () {
	var A = document.getElementById("separateLine");
	if (!A) {
		ECSideUtil.separateLine = document.createElement("DIV");
		ECSideUtil.separateLine.id = "separateLine";
		ECSideUtil.separateLine.className = "separateLine";
		ECSideUtil.separateLine.style.display = "none";
		document.body.appendChild(ECSideUtil.separateLine);
	}
};
var ECSPopup = function (A) {
	var B = this;
	B.id = A;
	B.currentContent = null;
	B.hideTimeout1 = 500;
	B.hideTimeout2 = 200;
	B.coreElement = null;
	B.isShow = false;
	B.setShow = function () {
		B.isShow = true;
	};
	B.setHide = function () {
		B.isShow = false;
	};
	B.initMe = function () {
	};
	B.showMe = function () {
		B.setShow();
	};
	B.hideMe = function () {
		window.setTimeout(B.tryHideMe, B.hideTimeout1);
		B.setHide();
	};
	B.tryHideMe = function () {
		if (!B.isShow) {
			B.doHideMe();
		}
	};
	B.doHideMe = function () {
		B.coreElement.style.display = "none";
		B.setHide();
	};
};
ECSideUtil.NearPagesBar = new ECSPopup("nearPagesBar");
ECSideUtil.NearPagesBar.initMe = function () {
	var A = document.getElementById("nearPagesBar");
	if (!A) {
		this.coreElement = document.createElement("DIV");
		this.coreElement.id = this.id;
		this.coreElement.className = this.id;
		this.coreElement.style.display = "none";
		document.body.appendChild(this.coreElement);
		ECSideUtil_addEvent(this.coreElement, "mouseover", this.setShow);
		ECSideUtil_addEvent(this.coreElement, "mouseout", this.hideMe);
		this.currentContent = "formid : nearPages";
	}
};
ECSideUtil.NearPagesBar.createNearPagesList = function (A, G) {
	var D = ECSideMessage.NEARPAGE_TITLE;
	var I = ECSideUtil.getGridObj(G);
	var E = I.getPageNo();
	var C = I.getTotalPage();
	var F = 0;
	var H = E - A;
	var B = E + A;
	if (H < 1) {
		B = B + (1 - H);
		H = 1;
	}
	if (B > C) {
		H = H - (B - C);
		B = C;
	}
	H = H < 1 ? 1 : H;
	D += "<nobr>";
	for (F = H; F <= B; F++) {
		if (F == E) {
			D += " <b>" + F + "</b> ";
		} else {
			D += "<a href=\"#\" onclick=\"ECSideUtil.gotoPage(" + F + ",'" + G + "');return false;\" >" + F + "</a>";
		}
	}
	D += "</nobr>";
	return D;
};
ECSideUtil.NearPagesBar.showMe = function (E, D) {
	var F = ECSideUtil.getGridObj(D);
	var A = ECSideUtil.parseIntOrZero(F.ECForm.getAttribute("nearPages"));
	if (A < 2) {
		return;
	}
	var C = F.getPageNo();
	this.currentContent = D + " : " + C;
	this.coreElement.innerHTML = this.createNearPagesList(A, D);
	this.setShow();
	this.coreElement.style.display = "block";
	var B = (this.coreElement.offsetWidth - E.offsetWidth) / 2;
	this.coreElement.style.left = ECSideUtil.getPosLeft(E) - B + document.body.scrollLeft + "px";
	this.coreElement.style.top = (ECSideUtil.getPosTop(E) - this.coreElement.offsetHeight + document.body.scrollTop) + "px";
};
ECSideUtil.doFilterFocus = function (B, A) {
	var C = B || window.event;
	A.select();
	ECSideUtil_stopEvent(C);
};
ECSideUtil.doFilter = function (C, A, B, D) {
	var E = C || window.event;
	if (A.type == "button") {
		A = A.nextSibling;
	} else {
		if (C.keyCode != 13) {
			return;
		}
	}
	ECSideUtil.doFilterCore(A, B, ECSideConstants.FILTER_ACTION, D);
};
ECSideUtil.doClearFilter = function (C, A, B, D) {
	ECSideUtil.doFilterCore(A, B, ECSideConstants.CLEAR_ACTION, D);
};
ECSideUtil.doFilterCore = function (E, A, G, I) {
	var J = ECSideUtil.getGridObj(I);
	var D = J.pageFieldName;
	var C = J.ECForm;
	var B = true;
	if (J.beforeFilter) {
		B = J.beforeFilter(I);
	}
	if (B === false) {
		return;
	}
	C[I + ECSideConstants.FILTER_PREFIX + ECSideConstants.ACTION].value = G;
	C[D].value = 1;
	ECSideUtil.clearTotalRows(I);
	ECSideUtil.noExport(I);
	var F = C.action;
	C.action = J.DEFAULT_ACTION;
	if (E != null) {
		C[A].value = E.value;
	}
	try {
		J.ajaxSubmit();
		C.action = F;
	}
	catch (H) {
		C.submit();
	}
	ECSideUtil.ColmunMenu.doHideMe();
};
ECSideUtil.showColmunMenu = function (B, A, C) {
	var D = B || window.event;
	ECSideUtil.ColmunMenu.showMe(A, C);
	return ECSideUtil_stopEvent(D);
};
ECSideUtil.ColmunMenu = new ECSPopup("columnMenu");
ECSideUtil.ColmunMenu.initMe = function () {
	var A = document.getElementById("columnMenu");
	if (!A) {
		this.coreElement = document.createElement("table");
		this.coreElement.id = this.id;
		this.coreElement.className = this.id;
		this.coreElement.style.display = "none";
		this.coreElement.style.left = "0px";
		this.coreElement.style.top = "0px";
		var B = document.createElement("tbody");
		var C = document.createElement("tr");
		var D = document.createElement("td");
		C.appendChild(D);
		B.appendChild(C);
		this.coreElement.appendChild(B);
		document.body.appendChild(this.coreElement);
		this.currentContent = "formid : columnIdx";
	}
	this.setHide();
};
ECSideUtil.ColmunMenu.getFilterItem = function (F, J, C, H) {
	var N = 20;
	var A = H + ECSideConstants.FILTER_PREFIX + C;
	var G = $_E(A);
	if (!G) {
		return "";
	}
	var M = G.value;
	var L = "ECSideUtil.doFilter(event,this,'" + A + "','" + H + "')";
	var D = "<nobr><input type=\"button\" class=\"filterIcon\" onclick=\"" + L + "\" />";
	var B = J.getAttribute("editTemplate");
	var K = document.getElementById(B);
	var I = window.isIE ? K.value : K.textContent;
	I = ECSideUtil.trimString(I);
	if (I.toLowerCase().indexOf("<select ") == 0) {
		I = I.substring(8);
		I = ECSideUtil.replaceAll(I, " name=\"", " tempname=\"");
		I = ECSideUtil.replaceAll(I, " id=\"", " tempid=\"");
		I = ECSideUtil.replaceAll(I, " style=\"", " tempstyle=\"");
		I = ECSideUtil.replaceAll(I, " class=\"", " tempclass=\"");
		I = ECSideUtil.replaceAll(I, " value=\"" + M + "\"", " value=\"" + M + "\" selected=\"selected\" ");
		D += "<select onclick=\"ECSideUtil_stopEvent(event)\" style=\"width:" + (F - N) + "px\" filterfield=\"true\" onkeypress=\"" + L + "\" " + I;
	} else {
		D += "<input type=\"text\" class=\"filterInput\" value=\"" + M + "\" style=\"width:" + (F - N) + "px\" onclick=\"ECSideUtil.doFilterFocus(event,this)\" onkeypress=\"" + L + "\" /></nobr>";
	}
	var E = "<nobr><input type=\"button\" class=\"clearIcon\" />";
	E += "<span class=\"itemText\" style=\"width:" + (F - N) + "px\" onclick=\"ECSideUtil.doClearFilter(event,this,'" + A + "','" + H + "')\" >" + ECSideMessage.FILTERCLEAR_TEXT + "</span></nobr>";
	return D + "<br />" + E;
};
ECSideUtil.ColmunMenu.getSortItem = function (E, J, B, I) {
	var M = 30;
	var A = I + ECSideConstants.SORT_PREFIX + B;
	var H = $_E(A);
	if (!H) {
		return "";
	}
	var D = "ECSideUtil.doSort(event,'" + B + "','asc','" + I + "')";
	var G = "ECSideUtil.doSort(event,'" + B + "','desc','" + I + "')";
	var C = "ECSideUtil.doSort(event,'" + B + "','default','" + I + "')";
	var F = "<nobr><input type=\"button\" class=\"ascIcon\" />";
	F += "<span class=\"itemText\" style=\"width:" + (E - M) + "px\" onclick=\"" + D + "\" >" + ECSideMessage.SORTASC_TEXT + "</span></nobr>";
	var L = "<nobr><input type=\"button\" class=\"descIcon\" />";
	L += "<span class=\"itemText\" style=\"width:" + (E - M) + "px\" onclick=\"" + G + "\" >" + ECSideMessage.SORTDESC_TEXT + "</span></nobr>";
	var K = "<nobr><input type=\"button\" class=\"defaultIcon\" />";
	K += "<span class=\"itemText\" style=\"width:" + (E - M) + "px\" onclick=\"" + C + "\" >" + ECSideMessage.SORTDEFAULT_TEXT + "</span></nobr>";
	return F + "<br />" + L + "<br />" + K;
};
ECSideUtil.ColmunMenu.showMe = function (G, H) {
	var A = 100;
	var K = 5;
	var F = 0;
	var C = G.getAttribute("columnName");
	if (this.currentContent == H + " : " + C && this.isShow) {
		ECSideUtil.ColmunMenu.doHideMe();
		return;
	}
	F = G.offsetWidth - K;
	F = F < A ? A : F;
	var I = this.coreElement.rows[0].cells[0];
	var J = this.getFilterItem(F, G, C, H);
	var E = this.getSortItem(F, G, C, H);
	if (J == "" && E == "") {
		return;
	}
	var B = J + "<hr />" + E;
	I.innerHTML = B;
	this.coreElement.style.top = ECSideUtil.getPosBottom(G) + document.body.scrollTop + "px";
	this.coreElement.style.display = "block";
	var D = ECSideUtil.getPosLeft(G) + document.body.scrollLeft;
	if (D + this.coreElement.offsetWidth >= document.body.clientWidth - 2) {
		D = document.body.clientWidth - 2 - this.coreElement.offsetWidth;
	}
	this.coreElement.style.left = D + "px";
	this.setShow();
	this.currentContent = H + " : " + C;
};
ECSideUtil.startDragobj = false;
ECSideUtil.MinColWidth = ECSideConstants.MIN_COLWIDTH;
ECSideUtil.Dragobj = null;
ECSideUtil.DragobjBodyCell = null;
ECSideUtil.DragobjBodyCellNext = null;
ECSideUtil.DragobjNext = null;
ECSideUtil.DragECSideObj = null;
ECSideUtil.leftC = 0;
ECSideUtil.rightC = 0;
ECSideUtil.startC = 0;
ECSideUtil.endC = 0;
ECSideUtil.StartResize = function (A, D, G) {
	var E = A || window.event;
	if (!G) {
		G = ECSideConstants.EC_ID;
	}
	D.focus();
	document.body.style.cursor = "col-resize";
	var I = window.isIE ? E.x : E.pageX;
	ECSideUtil.DragECSideObj = ECSideList[G];
	ECSideUtil.Dragobj = D.parentNode;
	ECSideUtil.DragobjNext = ECSideUtil.getNextElement(ECSideUtil.Dragobj);
	var H = ECSideUtil.DragECSideObj.resizeWay;
	ECSideUtil.leftC = ECSideUtil.getPosLeft(ECSideUtil.Dragobj) + parseInt(ECSideUtil.MinColWidth);
	if (H == "resizeTable" || !ECSideUtil.DragobjNext) {
		if (ECSideUtil.DragECSideObj.ECListBodyZone) {
			ECSideUtil.rightC = ECSideUtil.getPosRight(ECSideUtil.DragECSideObj.ECListBodyZone);
		} else {
			ECSideUtil.rightC = document.body.clientWidth;
		}
	} else {
		ECSideUtil.rightC = ECSideUtil.getPosRight(ECSideUtil.DragobjNext) - parseInt(ECSideUtil.MinColWidth);
	}
	ECSideUtil.leftC += document.body.scrollLeft;
	ECSideUtil.rightC += document.body.scrollLeft;
	var F = ECSideUtil.Dragobj.cellIndex;
	try {
		ECSideUtil.DragobjBodyCell = ECSideList[G].ECListBody.rows[0].cells[F];
	}
	catch (E) {
		ECSideUtil.DragobjBodyCell = null;
	}
	try {
		ECSideUtil.DragobjBodyCellNext = ECSideUtil.getNextElement(ECSideUtil.DragobjBodyCell);
	}
	catch (E) {
		ECSideUtil.DragobjBodyCellNext = null;
	}
	ECSideUtil.MinColWidth = ECSideList[G].MIN_COL_WIDTH;
	if (!ECSideUtil.MinColWidth || ECSideUtil.MinColWidth == "" || ECSideUtil.MinColWidth < 1) {
		ECSideUtil.MinColWidth = ECSideConstants.MIN_COLWIDTH;
	}
	ECSideUtil.separateLine.style.top = ECSideUtil.getPosTop(ECSideUtil.DragECSideObj.ECListHead) + 2;
	var C = window.isIE ? document.body.scrollLeft + E.clientX : document.body.scrollLeft + E.pageX;
	ECSideUtil.startC = C;
	ECSideUtil.separateLine.style.left = ECSideUtil.startC + "px";
	var B = ECSideUtil.DragECSideObj.ECListHead.parentNode.clientHeight;
	if (ECSideUtil.DragECSideObj.ECListHead.parentNode != ECSideUtil.DragECSideObj.ECListBody.parentNode) {
		B += ECSideUtil.DragECSideObj.ECListBodyZone.clientHeight;
	}
	ECSideUtil.separateLine.style.height = B + "px";
	ECSideUtil.separateLine.style.display = "block";
	ECSideUtil.startDragobj = true;
	ECSideUtil_stopEvent(E);
};
ECSideUtil.DoResize = function (B) {
	var C = B || window.event;
	var A = window.isIE ? document.body.scrollLeft + C.clientX : document.body.scrollLeft + C.pageX;
	if (!ECSideUtil.Dragobj || !ECSideUtil.startDragobj) {
		if (ECSideUtil.separateLine) {
			ECSideUtil.separateLine.style.display = "none";
		}
		document.body.style.cursor = "";
		return;
	}
	if (A <= ECSideUtil.leftC || A >= ECSideUtil.rightC) {
		document.body.style.cursor = "not-allowed";
		return;
	}
	if (document.body.style.cursor == "not-allowed") {
		document.body.style.cursor = "col-resize";
	}
	ECSideUtil.separateLine.style.left = A + "px";
};
ECSideUtil.EndResize = function (B) {
	if (!ECSideUtil.Dragobj) {
		ECSideUtil.startDragobj = false;
		document.body.style.cursor = "";
		return;
	}
	var D = B || window.event;
	ECSideUtil.endC = ECSideUtil.parseIntOrZero(ECSideUtil.separateLine.style.left);
	var A = ECSideUtil.startC - ECSideUtil.endC;
	var C = 0;
	if (window.isIE) {
		C = ECSideConstants.IE_WIDTH_FIX_A;
	} else {
		C = ECSideConstants.FF_WIDTH_FIX_A;
	}
	var E = 0;
	E = ECSideUtil.DragobjBodyCell.clientWidth - A + C;
	ECSideUtil.Dragobj.style.width = E + "px";
	ECSideUtil.DragobjBodyCell.style.width = E + "px";
	ECSideUtil.DragobjBodyCell.width = E + "px";
	if (!ECSideUtil.DragECSideObj.isClassic) {
		ECSideUtil.syncRowsWidth(ECSideUtil.DragECSideObj.ECListHead.rows, ECSideUtil.DragECSideObj.ECListBody.rows);
	}
	document.body.style.cursor = "";
	ECSideUtil.separateLine.style.display = "none";
	ECSideUtil.DragECSideObj.handleResize();
	try {
	}
	catch (D) {
	}
	ECSideUtil.startDragobj = false;
	ECSideUtil.Dragobj = null;
	ECSideUtil.DragECSideObj = null;
	ECSideUtil.DragobjBodyCell = null;
	ECSideUtil.DragobjBodyCellNext = null;
	ECSideUtil.DragobjNext = null;
	ECSideUtil_stopEvent(D);
};
ECSideUtil.resizeInit = function () {
	ECSideUtil_addEvent(document, "mousemove", ECSideUtil.DoResize);
	ECSideUtil_addEvent(document, "mouseup", ECSideUtil.EndResize);
	document.body.ondrag = function () {
		return false;
	};
	document.body.onselectstart = function () {
		return ECSideUtil.Dragobj == null && ECSideUtil.startDragobj == false;
	};
};
ECSideUtil.getColumnName = function (D, E) {
	var G = ECSideUtil.getGridObj(E);
	var C = "";
	try {
		var B = D.cellIndex;
		var A = G.ECListHead.rows[0].cells[B];
		C = A.getAttribute("columnName");
	}
	catch (F) {
		C = "";
	}
	return C;
};
ECSideUtil.editCell = function (E, F, B) {
	if (E.getAttribute("editing") == "true") {
		return;
	}
	var L = ECSideUtil.getGridObj(F);
	E.setAttribute("editing", "true");
	if (!B) {
		var I = E.cellIndex;
		var D = L.ECListHead.rows[0].cells[I];
		B = D.getAttribute("editTemplate");
	}
	var J = document.getElementById(B);
	var G = window.isIE ? J.value : J.textContent;
	G = ECSideUtil.trimString(G);
	var K = window.isIE ? E.innerText : E.textContent;
	var H = E.getAttribute("cellValue");
	H = H == null ? K : H;
	H = ECSideUtil.trimString(H);
	var A = ECSideUtil.getColumnName(E, F);
	if (G.indexOf("name=\"\"") > 0) {
		G = ECSideUtil.replaceAll(G, "name=\"\"", "name=\"" + A + "\"");
	}
	var C = "input";
	if (G.toLowerCase().indexOf("<input ") == 0) {
		if (G.indexOf(" type=\"checkbox\"") > 0) {
			C = "checkbox";
		} else {
			if (G.indexOf(" type=\"radio\"") > 0) {
				C = "radio";
			}
		}
	} else {
		if (G.toLowerCase().indexOf("<select ") == 0) {
			C = "select";
		}
	}
	if (C == "input") {
		E.innerHTML = ECSideUtil.replaceAll(G, "value=\"\"", "value=\"" + H + "\"");
	} else {
		if (C == "select") {
			E.innerHTML = ECSideUtil.replaceAll(G, "value=\"" + H + "\"", "value=\"" + H + "\" selected=\"selected\"");
		} else {
			if (C == "checkbox" || C == "radio") {
				E.innerHTML = ECSideUtil.replaceAll(G, "value=\"" + H + "\"", "value=\"" + H + "\" checked=\"checked\"");
			}
		}
	}
	ECSideUtil.getFirstChildElement(E).focus();
};
ECSideUtil.updateEditCell = function (C, B) {
	if (C.getAttribute("filterfield") == "true") {
		return;
	}
	var A = C.parentNode;
	ECSideUtil.updateCellContent(A, C);
	A.setAttribute("edited", "true");
	A.parentNode.setAttribute("edited", "true");
	A.setAttribute("editing", "false");
	ECSideUtil.addClass(A, "editedCell");
};
ECSideUtil.updateCell = function (B) {
	var A = Form.getElements(B);
	if (A.length > 0) {
		var C = A[0];
		ECSideUtil.updateCellContent(B, C);
	}
};
ECSideUtil.updateCellContent = function (C, A) {
	var B = A.tagName.toLowerCase();
	if (B == "input") {
		var D = A.type.toLowerCase();
		if (D == "checkbox" || D == "radio") {
			B = D;
		}
	}
	var E = A.value;
	if (B == "input") {
		C.innerHTML = A.value;
	} else {
		if (B == "select") {
			E = A.options[A.selectedIndex].value;
			C.innerHTML = A.options[A.selectedIndex].text;
		} else {
			if (B == "checkbox" || B == "radio") {
				C.innerHTML = A.nextSibling.nodeValue;
			} else {
				C.innerHTML = A.innerHTML;
			}
		}
	}
	C.setAttribute("cellValue", ECSideUtil.trimString(E));
};
ECSideUtil.getUpdatedRows = function (C) {
	var E = ECSideUtil.getGridObj(C);
	var D = [];
	if (E && E.ECListBody) {
		var A = E.ECListBody.rows;
		for (var B = 0; B < A.length; B++) {
			if (A[B].getAttribute("edited") == "true") {
				D.push(A[B]);
			}
		}
	}
	return D;
};
ECSideUtil.getDeletedRows = function (C) {
	var E = ECSideUtil.getGridObj(C);
	var D = [];
	if (E && E.ECListBody) {
		var A = E.ECListBody.rows;
		for (var B = 0; B < A.length; B++) {
			if (ECSideUtil.hasClass(A[B], "del")) {
				D.push(A[B]);
			}
		}
	}
	return D;
};
ECSideUtil.getRemoveUpdatedClassRows = function (B, C) {
	if (B && B.length) {
		for (var A = 0; A < B.length; A++) {
			if (B[A].getAttribute("recordKey") == ECSideUtil.trimString(C)) {
				B[A].setAttribute("edited", null);
				ECSideUtil.clearRowEditedFlag(B[A]);
			}
		}
	}
};
ECSideUtil.getRemoveInsertedClassRows = function (D, E) {
	if (D && D.length) {
		for (var C = 0; C < D.length; C++) {
			var B = D[C].cells;
			for (var A = 0; A < B.length; A++) {
				ECSideUtil.updateCell(B[A]);
			}
			D[C].className = "added";
		}
	}
};
ECSideUtil.clearRowEditedFlag = function (A) {
	var C = A.cells;
	for (var B = 0; B < C.length; B++) {
		C[B].setAttribute("edited", "false");
		ECSideUtil.removeClass(C[B], "editedCell");
	}
};
ECSideUtil.getRemoveDeletedRows = function (C, E) {
	if (C && C.length) {
		for (var A = 0; A < C.length; A++) {
			var D = C[A];
			if (D && D.getAttribute("recordKey") == ECSideUtil.trimString(E) && ECSideUtil.hasClass(D, "del")) {
				var B = D.rowIndex;
				if (D.getAttribute("hasShadow") == "true") {
					D.parentNode.removeChild(D.parentNode.rows[B + 1]);
				}
				D.parentNode.removeChild(D);
			}
		}
	}
};
ECSideUtil.getRowCellsMap = function (H, G) {
	var B = {};
	var E = H.getAttribute("recordKey");
	if (E) {
		B["recordKey"] = E;
	}
	var J = H.cells;
	for (var C = 0; C < J.length; C++) {
		var F = J[C];
		var A = ECSideUtil.getColumnName(F, G);
		if (!A) {
			continue;
		}
		var I = F.getAttribute("cellValue");
		if (!I) {
			I = window.isIE ? F.innerText : F.textContent;
		}
		if (B[A] == undefined) {
			B[A] = I;
		} else {
			if (ECSideUtil.isCollection(B[A])) {
				B[A].push(I);
			} else {
				var D = B[A];
				B[A] = [D];
				B[A].push(I);
			}
		}
	}
	return B;
};
ECSideUtil.getInsertRows = function (C) {
	var E = ECSideUtil.getGridObj(C);
	var D = [];
	if (E && E.ECListBody) {
		var A = E.ECListBody.rows;
		for (var B = 0; B < A.length; B++) {
			if (ECSideUtil.hasClass(A[B], "add")) {
				D.push(A[B]);
			}
		}
	}
	return D;
};
ECSideUtil.updateShadowRow = function (E, G, C, A) {
	var D = A.responseText;
	if (ECSideUtil.trimString(D) != "") {
		G.cells[0].innerHTML = D;
	} else {
		G.cells[0].innerHTML = ECSideMessage.SHADOWROW_FAILED + " ( recordKey : " + E.getAttribute("recordKey") + ").";
	}
	var B = ECSideUtil.parseIntOrZero(G.cells[0].scrollHeight);
	G.setAttribute("shadowRowHeight", B);
	G.cells[0].style.height = B + "px";
	var B = ECSideUtil.parseIntOrZero(G.getAttribute("shadowRowHeight"));
	var F = ECSideUtil.getGridObj(C);
	F.hideWaitingBar();
};
ECSideUtil.showShadowRow = function (C, E, J) {
	var M = ECSideUtil.getGridObj(J);
	if (!C && !E) {
		return;
	}
	if (typeof (C) == "string") {
		C = document.getElementById(C);
	}
	if (typeof (E) == "string") {
		E = document.getElementById(E);
	}
	if (C.tagName.toLowerCase() == "td") {
		C = C.parentNode;
	}
	var G = C.rowIndex;
	if (!M.isClassic) {
		G++;
	}
	var I = C.cells.length;
	var L = C.getAttribute("hasShadow");
	var K = null;
	var F = true;
	if (L == "true") {
		K = C.parentNode.rows[G];
		if (K.style.display == "none") {
			K.style.display = "";
			E.className = "shadowRowButtonOpen";
		} else {
			K.style.display = "none";
			E.className = "shadowRowButtonClose";
			F = false;
		}
	} else {
		K = C.parentNode.insertRow(G);
		K.className = "shadowRow";
		K.style.display = "";
		var B = document.createElement("td");
		B.colSpan = I;
		K.appendChild(B);
		C.setAttribute("hasShadow", "true");
		K.setAttribute("isShadow", "true");
		var A = M.ECForm.getAttribute("shadowRowAction");
		var D = ECSideUtil.getRowCellsMap(C, J);
		var H = ECSideUtil.updateShadowRow.bind(this, C, K, J);
		ECSideUtil.doAjaxUpdate(A, D, H, J);
		E.className = "shadowRowButtonOpen";
	}
	if (F && M.autoCloseOtherShadowRow) {
		if (M.currentShadowRowParent && M.currentShadowEventSrc && C.id != M.currentShadowRowParent) {
			ECSideUtil.showShadowRow(M.currentShadowRowParent, M.currentShadowEventSrc, M.id);
		}
	}
	if (F) {
		M.currentShadowRowParent = C.id;
		M.currentShadowEventSrc = E.id;
	} else {
		M.currentShadowRowParent = null;
		M.currentShadowEventSrc = null;
	}
};
ECSideUtil.saveGirdBatch = function (D, N, G) {
	if (!confirm(ECSideMessage.UPDATE_CONFIRM)) {
		return;
	}
	var P = ECSideUtil.getGridObj(N);
	var C = P.ECForm;
	var K = C.getAttribute("updateAction") + "";
	var Q = ECSideUtil.getUpdatedRows(N);
	var B = C.getAttribute("insertAction") + "";
	var J = ECSideUtil.getInsertRows(N);
	var F = C.getAttribute("deleteAction") + "";
	var L = ECSideUtil.getDeletedRows(N);
	var A = true;
	if (P.beforeSave) {
		A = P.beforeSave(N, Q, J, L);
	}
	if (A === false) {
		return;
	}
	var M;
	var O = "easyDataAccess=";
	if (G == null || G == window.undefined) {
		G = true;
	}
	var I = G;
	P.forUpdateRows = Q;
	M = {};
	I = I && K.indexOf(O) < 1;
	for (var H = 0; H < Q.length; H++) {
		var E = ECSideUtil.getRowCellsMap(P.forUpdateRows[H], N);
		if (I) {
			ECSideUtil.appendMap(M, E);
		} else {
			ECSideUtil.doAjaxUpdate(K, E, P.updateCallBack, N);
		}
	}
	if (I && Q.length > 0) {
		ECSideUtil.doAjaxUpdate(K, M, P.updateCallBack, N);
	}
	P.forInsertRows = J;
	M = {};
	I = I && B.indexOf(O) < 1;
	for (var H = 0; H < J.length; H++) {
		var E = Form.serialize(P.forInsertRows[H], true);
		if (I) {
			ECSideUtil.appendMap(M, E);
		} else {
			ECSideUtil.doAjaxUpdate(B, E, P.insertCallBack, N);
		}
	}
	if (I && J.length > 0) {
		ECSideUtil.doAjaxUpdate(B, M, P.insertCallBack, N);
	}
	P.forDeleteRows = L;
	M = {};
	I = I && F.indexOf(O) < 1;
	for (var H = 0; H < L.length; H++) {
		var E = ECSideUtil.getRowCellsMap(P.forDeleteRows[H], N);
		if (I) {
			ECSideUtil.appendMap(M, E);
		} else {
			ECSideUtil.doAjaxUpdate(F, E, P.deleteCallBack, N);
		}
	}
	if (I && L.length > 0) {
		ECSideUtil.doAjaxUpdate(F, M, P.deleteCallBack, N);
	}
	if (Q.length < 1) {
		P.forUpdateRows = [];
	}
	if (J.length < 1) {
		P.forInsertRows = [];
	}
	if (L.length < 1) {
		P.forDeleteRows = [];
	}
	if (Q.length < 1 && J.length < 1 && L.length < 1) {
		alert(ECSideMessage.NO_RECORD_UPDATE);
	}
};
ECSideUtil.saveGird = ECSideUtil.saveGirdBatch;
ECSideUtil.delFromGird = function (D, G, H) {
	var I = ECSideUtil.getGridObj(G);
	var B = I.ECForm;
	if (!H) {
		H = I.deleteFlags;
	}
	var A = B[H];
	var C = I.selectedRow;
	if (C && ECSideUtil.hasClass(C, "added")) {
		return;
	}
	if (C && ECSideUtil.hasClass(C, "add")) {
		C.parentNode.removeChild(C);
		return;
	}
	if ((!H || !A) && C) {
		if (ECSideUtil.hasClass(C, "del")) {
			ECSideUtil.removeClass(C, "del");
		} else {
			ECSideUtil.addClass(C, "del");
		}
		return;
	}
	if (!A) {
		return;
	}
	if (!ECSideUtil.isCollection(A)) {
		A = [A];
	}
	for (var E = 0; E < A.length; E++) {
		if (A[E].checked) {
			var F = A[E].parentNode.parentNode;
			if (ECSideUtil.hasClass(F, "del")) {
				ECSideUtil.removeClass(F, "del");
			} else {
				ECSideUtil.addClass(F, "del");
			}
			F.className = "del";
		}
	}
};
ECSideUtil.addToGird = function (C, D, F) {
	var J = ECSideUtil.getGridObj(F);
	var H = document.getElementById(D);
	if (!H) {
		H = document.getElementById(ECSideConstants.DEFALUT_ADD_TEMPLATE);
	}
	H = H.value;
	var A = 0;
	if (J.ECListBody.rows) {
		A = J.ECListBody.rows.length;
	}
	var G = J.ECListBody.insertRow(A);
	ECSideUtil_addEvent(G, "click", ECSideUtil.selectRow.bind(this, G, J.id));
	H = H.split("<tpsp />");
	G.className = "add";
	var I = [];
	for (var E = 0; E < J.columnNum; E++) {
		I[E] = G.insertCell(E);
		I[E].innerHTML = H[E];
	}
	var B = ECSideUtil.getPosTop(G);
	if (J.ECListBodyZone) {
		J.ECListBodyZone.scrollTop = B;
	}
};
ECSideUtil.doAjaxUpdate = function (A, B, E, D) {
	var G = ECSideUtil.getGridObj(D);
	var F = true;
	if (G.beforeSubmit) {
		F = G.beforeSubmit(B, D);
	}
	if (F === false) {
		return;
	}
	var C = new Ajax.Request(A, {requestHeaders:ECSideConstants.AJAX_HEADER, method:"POST", asynchronous:true, parameters:B, onComplete:E});
	G.showWaitingBar();
};
ECSideUtil.bindFunction = function (B) {
	var C = [];
	for (var A = 1; A < arguments.length; A++) {
		C.push(arguments[A]);
	}
	return function () {
		for (var D = 0; D < arguments.length; D++) {
			if (typeof (arguments[D]) != "undefined") {
				C[D] = arguments[D];
			}
		}
		return B.apply(this, C);
	};
};
ECSideUtil.changeListHeight = function (A, C) {
	var D = ECSideUtil.getGridObj(C);
	A = A + "";
	if ("auto" != A) {
		if (A.indexOf("+") == 0) {
			A = D.listHeight + ECSideUtil.parseIntOrZero(A.substring(1));
		} else {
			if (A.indexOf("-") == 0) {
				A = D.listHeight - ECSideUtil.parseIntOrZero(A.substring(1));
			} else {
				if (A == "reset") {
					A = D.orgListHeight;
				}
			}
		}
	}
	if (ECSideUtil.parseIntOrZero(A) > D.ECListBody.scrollHeight - ECSideConstants.OFFSET_A || A == "auto") {
		A = D.ECListBody.parentNode.scrollHeight;
		var B = D.ECListBodyZone.offsetHeight - D.ECListBodyZone.clientHeight + ECSideConstants.LIST_HEIGHT_FIXED;
		if (B <= 2 && Me.ECListBodyZone.offsetWidth - Me.ECListBodyZone.clientWidth > 2) {
			B = ECSideConstants.SCROLLBAR_WIDTH;
		}
		A = A / 1 + B;
	}
	A = A < D.minHeight / 1 ? D.minHeight / 1 : A;
	if (D.ECListBodyZone) {
		D.ECListBodyZone.style.height = A + "px";
	}
	D.listHeight = A;
	D.handleResize();
};
ECSideUtil.onWindowload = function () {
	ECSideUtil.initAllGird();
	ECSideUtil.ColmunMenu.initMe();
	ECSideUtil.NearPagesBar.initMe();
	ECSideUtil_addEvent(document.body, "click", ECSideUtil.ColmunMenu.doHideMe);
	ECSideUtil_addEvent(window, "resize", ECSideUtil.ColmunMenu.doHideMe);
};
ECSideUtil.initAllGird = function () {
	for (var B in ECSideList) {
		var A = ECSideList[B];
		A.init();
	}
};
ECSideUtil.createGird = function (B) {
	var A = ECSideUtil.getGridObj(B);
	if (!A) {
		A = new ECSide(B);
	}
	return A;
};
ECSideUtil_addEvent(window, "resize", ECSideUtil.resizeAllGird);
ECSideUtil_addEvent(window, "load", ECSideUtil.onWindowload);
ECSideUtil.showCalendar = function (A) {
	Calendar.trigger({inputField:A.previousSibling.name, ifFormat:"%Y-%m-%d", showsTime:false, button:"date_button", singleClick:true, onClose:ECSideUtil.fillDate, step:1});
};
ECSideUtil.fillDate = function (A) {
	ECSideUtil.updateEditCell(A.inputField);
	A.hide();
};
var ECCN = function (A) {
	return ECSideUtil.getGridObj(A);
};
ECCN.init = function () {
};
var ECSideUtil = ECSideUtil;

