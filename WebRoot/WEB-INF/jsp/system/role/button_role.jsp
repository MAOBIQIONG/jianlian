<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" href="<%=basePath%>H-ui/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>组权限分配</title>
</head>
<body>
<article class="page-container">
<table class="table">
	<tr>
		<td class="va-t"><ul id="ztree" class="ztree"></ul></td>  
	</tr>
	<tr>
		<td class="va-t">
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
			    <input class="btn btn-primary radius" onclick="save()" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">   
			 </div>
		</div>
		</td> 
	</tr>
</table> 
</article>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script> 
<script type="text/javascript">
var setting = {
       view: {
		dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
		showLine: true,//是否显示节点之间的连线
		selectedMulti: false,//设置是否允许同时选中多个节点
	},
	check:{
	    //chkboxType: { "Y": "ps", "N": "ps" },
	    chkStyle: "checkbox",//复选框类型
	    enable: true //每个节点上是否显示 CheckBox 
	  },
	data: {
		simpleData: {//简单数据模式
			enable:true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: ""
		}
	}, 
	callback: {
		beforeClick: function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			if (treeNode.isParent) {
				zTree.expandNode(treeNode);//如果是父节点，则展开该节点
			} else {
			    zTree.checkNode(treeNode, !treeNode.checked, true, true);//单击勾选，再次单击取消勾选
			}
		}
	}
};

var zNodes =JSON.parse('${zTreeNodes}');
		
var code;
		
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}
		
$(document).ready(function(){
	var t = $("#ztree");
	t = $.fn.zTree.init(t, setting, zNodes);
});

function save(){
	var zTree    = $.fn.zTree.getZTreeObj("ztree")
	var nodes = zTree.getCheckedNodes();
	var tmpNode;
	var ids = "";
	for(var i=0; i<nodes.length; i++){
		tmpNode = nodes[i];
		if(i!=nodes.length-1){
			ids += tmpNode.id+",";
		}else{
			ids += tmpNode.id;
		}
	}
	
	var roleId = "${roleId}";
	var msg = "${msg}";
	var url = "<%=basePath%>role/roleButton/save.do";
	var postData;
	
	postData = {"ROLE_ID":roleId,"menuIds":ids,"msg":msg};
	
	$.post(url,postData,function(result){
	     if(result.status == 1){ 
			parent.$('.breadcrumb .r .Hui-iconfont').click(); 
     		parent.layer.close(index);
         }
	});
	
} 
</script>
</body>
</html>
