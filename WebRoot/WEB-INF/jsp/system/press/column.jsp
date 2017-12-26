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
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
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
		<title>媒体管理</title>
	</head>

	<body class="pos-r"> 
	<div>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 媒体管理 <span class="c-gray en">&gt;</span>标题栏管理 
<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"  href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<!-- <form id="company-form">
		<input type="text" name="TITLE_CATEGORY_NAME" value="" id="TITLE_CATEGORY_NAME" placeholder="标题类别名称" style="width:250px" class="input-text">
		<button onclick="quserpany('<%=basePath%>Press/querypressname.do')"  class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form> -->
		<input type="text" id="TITLE_CATEGORY_NAME" name="TITLE_CATEGORY_NAME"  placeholder="标题类别名称" style="width:250px" class="input-text"> 
    	<button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
	</div> 
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="piliangdel('<%=basePath%>Press/delpress.do')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
		<!-- <a class="btn btn-primary radius" data-title="批量禁止" href="javascript:;" onclick="uppany('<%=basePath%>Press/upis_show.do')"><i class="Hui-iconfont">&#xe600;</i> 显示/关闭</a>  -->
		<a class="btn btn-primary radius" data-title="添加栏目" onclick="tiao('添加栏目','<%=basePath%>Press/querypressid.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加栏目</a>
		</span> 
		 </div>
	<div class="mt-20">
		<form id="form-column">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
				    <th class="center" onclick="selectAll()">
				        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
				    </th>   
					<th>标题类别名称</th> 
					<th>是否显示</th> 
					<th>创建人</th> 
					<th>创建时间</th> 
					<th>类型</th>
					<th>位置编号</th>
					<th>修改时间</th>
					<th>操作</th> 
				</tr>
			</thead>
			<tbody> 
			</tbody>
		</table>
		</form>
	</div>
</div>
</div>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
<script type="text/javascript">

var table;
	$(function() {
		//table
		table = $('.table-sort').dataTable({
			"aaSorting": [
				[7, "desc"],
				[4, "desc"]
			], //默认第几个排序
			"aoColumnDefs": [
				//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
				//{"orderable":false,"aTargets":[3]} 
			],
			"bStateSave": true, //状态保存
			"bProcessing": false, // 是否显示取数据时的那个等待提示
             "bServerSide": true,//这个用来指明是通过服务端来取数据
             "sAjaxSource": "<%=basePath%>Press/querypress.do",//这个是请求的地址
             "bLengthChange" : true,// 每行显示记录数  
             "iDisplayLength" : 10,// 每页显示行数  
             "bSort" : true,// 排序
             "bAutoWidth" : false, //是否自适应宽度  
             "searching":false,//隐藏右侧搜索框 
             "bPaginate": true, //翻页功能
             "bLengthChange": true, //改变每页显示数据数量
			"aoColumns": [
			{  
			    "sClass":"center",  
			    "aTargets":[0],  
			    "mRender":function(a,b,c,d){//c表示当前记录行对象  
			        return '<input type=\"checkbox\" name=\"ID\" value=\"'+c.ID+'\">';  
			    }  
			}, 
                 {  
                     "sClass":"center", 
                     "mDataProp" : "TITLE_CATEGORY_NAME", 
                     "aTargets":[1],  
                     "mData":"TITLE_CATEGORY_NAME" 
                 },
                 {  
                     "sClass":"center", 
                     "mDataProp" : "SHOW_NAME", 
                     "aTargets":[2],  
                     "mData":"SHOW_NAME" 
                 },
                 {  
                     "sClass":"center", 
                     "mDataProp" : "NAME", 
                     "aTargets":[3],  
                     "mData":"NAME" 
                 },
                 {  
                     "sClass":"center", 
                     "mDataProp" : "CREATE_DATE", 
                     "aTargets":[4],  
                     "mData":"CREATE_DATE" 
                 },
                  {  
                     "sClass":"center", 
                     "mDataProp" : "TYPE_NAME", 
                     "aTargets":[5],  
                     "mData":"TYPE_NAME" 
                 }, 
                  {  
                     "sClass":"center", 
                     "mDataProp" : "LOCATION_NO_ID", 
                     "aTargets":[6],  
                     "mData":"LOCATION_NO_ID" 
                 }, 
                 {  
                     "sClass":"center", 
                     "mDataProp" : "MODIFY_DATE", 
                     "aTargets":[7],  
                     "mData":"MODIFY_DATE" 
                 }, 
                 {
                     "sClass":"center",  
                     "aTargets":[8],  
                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                     	return '<a data-title=\"编辑\" onclick=\"tiao(\'编辑\',\'<%=basePath%>Press/querypressid.do?ID='+c.ID+'\',\'\',\'550\')\" href=\"javascript:;\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i> 编辑</a>&nbsp<a data-title=\"删除\" href=\"javascript:;\" onclick=\"delbyid(\'<%=basePath%>Press/delpressByid.do?ID='+c.ID+'\')\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i> 删除</a>'; 
                    }  
                 }
			], 
			  
			 "fnServerData" : function(sSource, aoData, fnCallback) {  
				//获取检索参数
			    var TITLE_CATEGORY_NAME = $("#TITLE_CATEGORY_NAME").val(); 
				 $.ajax({
		                url : sSource,//这个就是请求地址对应sAjaxSource
		                data : {"aoData":JSON.stringify(aoData),"TITLE_CATEGORY_NAME":TITLE_CATEGORY_NAME},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
		                type : 'post',
		                dataType : 'json',
		                async : false,
		                success : function(result) {
		                	fnCallback(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
	
		    				//表格数据居中
		    				$("td").attr("style","text-align:center");
		                },
		                error : function(msg) {
		                }
		            });
	           }
		});
		
		//搜索
		$('#search').click( function() {
		    table.fnDraw(); 
		});
		
		$(document).keyup(function(event){
				  if(event.keyCode ==13){
				    $("#search").trigger("click");
				  }
				});
	});

function quserpany(url){  
   $.ajax({  
         type: "POST",
         url:url,
         data:$('#form-column').serialize(), 
         async: false,
         error: function(request) {
             alert("Connection error");
         },
         success: function(data) {
         alert("操作成功！"); 
            $(".show_iframe").html(data);  
         }
     });
}

function tiao(title,url,w,h){  
	var index=layer.open({
		type:2,
		title:title,
		content:url

});
	layer.full(index);
   /*$.ajax({  
         type: "POST",
         url:url,
         async: false,
         error: function(request) {
             alert("Connection error");
         },
         success: function(data) {
         alert("操作成功！"); 
            $(".show_iframe").html(data);  
         }
     });*/
}

function piliangdel(url){  
var checklist = document.getElementsByName ("ID");
	   var len=$("input[name='ID']:checked").length;
	   if(len==0){
	      alert("请选择一个或多个需要删除的用户！");
	   }else if(len==1){
	      alert("只能选择两个或两个以上需要删除的用户！");
	   }
	   else{
	   var data=$("input[name='ID']:checked").val();
   $.ajax({  
         type: "POST",
         url:url,
         data:$('#form-column').serialize(), 
         
         async: false,
         success : function(result) { 
          		parent.$('.breadcrumb .r .Hui-iconfont').click();
          		window.location.reload();   
           },
           error : function(msg) {
           }
     });
}
}

function delbyid(url){  
layer.confirm('确认要删除该数据吗？',function(index){ 
   $.ajax({  
         type: "POST",
         url:url,
         data:$('#form-column').serialize(), 
         async: false,
         success : function(result) { 
          		parent.$('.breadcrumb .r .Hui-iconfont').click();
          		window.location.reload();   
           },
           error : function(msg) {
           }
     });
	});
}

//关闭/显示
function uppany(url){
	   var checklist = document.getElementsByName ("ID");
	   var len=$("input[name='ID']:checked").length;
	   if(len==0){
	      alert("请选择一个需要显示/关闭的用户！");
	   }else if(len>1){
	      alert("只能选择一个需要显示/关闭的用户！");
	   }else{
	   var data=$("input[name='ID']:checked").val();
       $.ajax({  
	         type: "POST",
	         url:url,
	         data:$('#form-column').serialize(),  
	         async: false,
	          success : function(result) { 
              		parent.$('.breadcrumb .r .Hui-iconfont').click();
              		window.location.reload();   
               },
               error : function(msg) {
               }
	    });
    }
    }
    
</script>
</body>

</html>