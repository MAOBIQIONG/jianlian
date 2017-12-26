function reLoadOpenURL(event, treeId, treeNode) {
	// alert(treeNode.tId + ", " + treeNode.name+","+treeNode.id);
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	if (treeNode.isParent) {// 如果是父节点
		// zTree.reAsyncChildNodes(treeNode, "refresh",false);//异步刷新，清空后加载，加载后打开
		if (treeNode.open) {// 父节点为展开状态，折叠父节点
			// alert(treeNode.open);
			zTree.expandNode(treeNode, false, true, true, false);
			// expandNode参数说明：节点，展开(true)/折叠(false)，是否影响子孙节点，是否设置焦点，是否触发beforeExpand/onExpand或beforeCollapse/onCollapse事件回调函数
		} else {// 父节点是折叠的
			if (treeNode.id != 1)
				zTreeBeforeExpand(treeId, treeNode);// 如果不是根节点（本例根节点为1），则强制异步刷新子节点数据
			else
				zTree.expandNode(treeNode, true, false, true, false);// 如果是根节点则展开
		}
		return false;
	} else {// 不是父节点，打开对应链接

		clickChildNode(treeNode)
		return true;
	}

}
// 用于捕获父节点展开之前的事件回调函数，并且根据返回值确定是否允许展开操作 ，false不代开
function zTreeBeforeExpand(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	if (treeNode.isParent && treeNode.id != "1") {
		zTree.reAsyncChildNodes(treeNode, "refresh");// 异步刷新，清空后加载，加载后打开,需要不打开加参数true
		return false;// 使用了异步强行加载，如果用true,节点展开将不会按照expandSpeed属性展开，false将按照设定速度展开
	} else {
		return true;
	}
};
// 用于捕获节点被展开后的事件回调函数
function zTreeOnExpand(event, treeId, treeNode) {
	alert(treeNode.tId + ", " + treeNode.name);
};

// 数据过滤方法，如后台数据已确认无误可直接返回数据，不需注册此回调函数
function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	return childNodes;
}
// 异步加载失败时调用的方法
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
		errorThrown) {
	console.log(XMLHttpRequest)
	console.log(textStatus)
	console.log(errorThrown)
	alert("加载失败！");
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
	cancelHalf(treeNode);
}
function cancelHalf(treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(setting.treeId | "tree");
	treeNode.halfCheck = false;
	zTree.updateNode(treeNode); // 异步加载成功后刷新树节点
}
function beforeAsync() {
	alert(1)
}