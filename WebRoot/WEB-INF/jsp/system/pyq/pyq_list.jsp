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
		<title>朋友圈管理</title>
	</head>

	<body class="pos-r"> 
		<div> 
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>朋友圈管理 <span class="c-gray en">&gt;</span>朋友圈管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav>
			<div class="page-container">
				<div class="text-c"> 
				    <div class="text-c">
					    <input type="text" name="PRO_NAME" id="PRO_NAME" placeholder="用户名" style="width:250px" class="input-text">&nbsp;&nbsp;&nbsp;
					    <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
				    </div>
				</div>  
				<!-- <div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l">
					    <a class="btn btn-primary radius" data-title="新增" onclick="project_edit('新增','<%=basePath%>friends/toAdd.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>新增</a>
					</span>  
				</div>  -->
				<div class="mt-20">
					<table class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c">
							    <th class="center" onclick="selectAll()" style="width: 250px">
							        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
							    </th>  
							    <th style="width: 250px;">用户名</th>
								<th style="width: 250px">发布时间</th> 
								<th style="width: 250px">内容</th> 
								<th style="width: 250px">城市</th>
								<th style="width: 250px">地址</th> 
								<th style="width:250px">点赞数</th>
								<th style="width: 880px">评论数</th>
								<th style="width:250px">查看图片</th>
								<th>操作</th> 
							</tr>
						</thead>
						<tbody> 
						</tbody>
					</table>
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
					[2,"desc"]
				], //默认第几个排序
				"aoColumnDefs": [
					//{"bVisible": false, "aTargets": [11]}, //控制列的隐藏显示
					//{"orderable":false,"aTargets":[0,11]} 
				],
				"bStateSave": true, //状态保存
				"bProcessing": false, // 是否显示取数据时的那个等待提示
                "bServerSide": true,//这个用来指明是通过服务端来取数据
                "sAjaxSource": "<%=basePath%>friends/queryList.do",//这个是请求的地址
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
				        return '<input type=\"checkbox\" name=\"ids\" value=\"'+c.ID+'\">';  
				    }  
				},
                 {  
                     "sClass":"center", 
                     "mDataProp" :"REAL_NAME", 
                     "aTargets":[1],  
                     "mData":"REAL_NAME" 
                 }, 
                 {  
                     "sClass":"center", 
                     "mDataProp" :"PUBLISH_DATE", 
                     "aTargets":[2],  
                     "mData":"PUBLISH_DATE" 
                 }, 
                 {  
                     "sClass":"center", 
                     "mDataProp" :"CONETENT", 
                     "aTargets":[3],  
                     "mData":"CONETENT" 
                 }, 
                 {  
                     "sClass":"center", 
                     "mDataProp" :"CITY", 
                     "aTargets":[4],  
                     "mData":"CITY" 
                 }, 
                 {  
                     "sClass":"center", 
                     "mDataProp" :"ADDR", 
                     "aTargets":[5],  
                     "mData":"ADDR" 
                 },
                 {  
                     "sClass":"center", 
                     "mDataProp" : "dzshu", 
                     "aTargets":[6],  
                    "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                           return '<a _href=\"<%=basePath%>friends/toDz.do?PID='+c.ID+'\" data-title=\"查看点赞人\" class=\"btn btn-link\" onclick=\"Hui_admin_tab(this)\" href=\"javascript:;\"  >'+c.dzshu+'</a>';
                        }  
                  }, 
               	 {  
                     "sClass":"center", 
                     "mDataProp" : "plshu", 
                     "aTargets":[6],  
                    "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                           return '<a _href=\"<%=basePath%>friends/toPL.do?PID='+c.ID+'\" data-title=\"查看评论\" class=\"btn btn-link\" onclick=\"Hui_admin_tab(this)\" href=\"javascript:;\"  >'+c.plshu+'</a>';
                        }  
                  }, 
	              {  
                     "sClass":"center", 
                     "aTargets":[7],  
                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                         return '<a _href=\"<%=basePath%>pyqtp/toLookImgs.do?ID='+c.ID+'\" data-title=\"查看朋友圈图片\" class=\"btn btn-link\" onclick=\"Hui_admin_tab(this)\" href=\"javascript:;\"  >查看图片</a>';
                      }  
	              }, 
                 {
                     "sClass":"center",  
                     "aTargets":[8],  
                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                     	return '<a data-title=\"编辑\" onclick=\"project_edit(\'编辑\',\'<%=basePath%>friends/goEdit.do?ID='+c.ID+'\',\'\',\'300\')\" href=\"javascript:;\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i> 编辑</a>&nbsp;&nbsp;<a data-title=\"删除\" href=\"javascript:;\" onclick=\"project_del(\'<%=basePath%>friends/delete.do?ID='+c.ID+'\')\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i> 删除</a>'; 
                    }  
                 }
				], 
				 "fnServerData" : function(sSource, aoData, fnCallback) {  
	                //获取检索参数
					var REAL_NAME = $("#PRO_NAME").val(); 
					 $.ajax({
			                url : sSource,//这个就是请求地址对应sAjaxSource
			                data : {"aoData":JSON.stringify(aoData),"REAL_NAME":REAL_NAME},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
			                type : 'post',
			                dataType : 'json',
			                async : false,
			                success : function(result) {
			                	fnCallback(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
			                	$(".1").css("color","red"); 
			    				//表格数据居中
			    				$("td").attr("style","text-align:center");
			                },
			                error : function(msg) {
			                }
			            });
		           }
			});
			
			//检索
			$('#search').click( function() {
			    table.fnDraw(); 
			});
			
			$(document).keyup(function(event){
				  if(event.keyCode ==13){
				    $("#search").trigger("click");
				  }
				});
		}); 
		
			function project_del(url){ 
				 layer.confirm('确认要删除该数据吗？',function(index){ 
					//此处请求后台程序，下方是成功后的前台处理…… 
					$.ajax({
		                url : url, 
		                type : 'post',
		                dataType : 'json',
		                async : false,
		                success : function(result) {
		                	 if(result.status == 1){
		                		table.fnDraw();
		                		$(".layui-layer-setwin .layui-layer-close1").click();  
			                } 
		                },
		                error : function(msg) {
		                }
		            });
				 }); 
			}
		 
			
			/*编辑*/
			function project_edit(title,url,w,h){   
				var index=layer.open({
					type:2,
					title:title,
					content:url

			});
				layer.full(index);
	         } 
 
	
	function project_check(title,url,w,h){ 
	   var checklist = document.getElementsByName ("ids");
	   var len=$("input[name='ids']:checked").length; 
	   if(len==0){
	      alert("请选择一个项目！");
	   }else if(len>1){
	      alert("只能选择一个项目！");
	   }else{
	      var data=$("input[name='ids']:checked").val(); 
	      var URL=url+"?ID="+data;
	      var index=layer.open({
	  		type:2,
	  		title:title,
	  		content:URL

	  });
	  	layer.full(index); 
	   }
    } 
</script> 
</body>

</html>
 