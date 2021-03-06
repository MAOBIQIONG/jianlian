﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
		<title>问答管理</title>
		<style>
            .wt_img{
               width:250px;
               height:150px;
            }		
		</style>
	</head> 

	<body class="pos-r"> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>问答管理 <span class="c-gray en">&gt;</span>问答管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav>
			<div class="page-container">
				<div class="text-c"> 
		             <input type="text" id="REAL_NAME" name="REAL_NAME"  placeholder="发布人" style="width:250px" class="input-text"> 
		             <input type="text" id="TITLE" name="TITLE"  placeholder="标题" style="width:250px" class="input-text"> 
				     <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
			    </div> 
				<div class="mt-20">
					<table class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c">
							    <th style="width:100px;">提问人</th> 
								<th style="width:180px;">问题标题</th>
								<th style="width:250px;">问题内容</th>
								<th>问题图片</th>
								<th>赞同数</th>
								<th>回答数</th>
								<th>关注数</th> 
								<th>发布时间</th> 
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
						//[1, "asc"]
					], //默认第几个排序
					"aoColumnDefs": [
						//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
						//{"orderable":false,"aTargets":[3]} 
					],
					"bStateSave": true, //状态保存
					"bProcessing": false, // 是否显示取数据时的那个等待提示
	                "bServerSide": true,//这个用来指明是通过服务端来取数据
	                "sAjaxSource": "<%=basePath%>question/queryByParam.do",//这个是请求的地址
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
                         "mDataProp" : "REAL_NAME", 
                         "aTargets":[0],  
                         "mData":"REAL_NAME" 
                     },  
                     {  
	                     "sClass":"center", 
	                     "mDataProp" : "TITLE", 
	                     "aTargets":[1],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                            return '<a _href=\"<%=basePath%>question/toAnswer.do?WT_ID='+c.ID+'\" data-title=\"查看回答\" class=\"btn btn-link\" onclick=\"Hui_admin_tab(this)\" href=\"javascript:;\"  >'+c.TITLE+'</a>';
                         }  
	                 }, 
                     {  
                         "sClass":"center", 
                         "mDataProp" : "DESCRIPTION", 
                         "aTargets":[2],  
                         "mData":"DESCRIPTION" 
                     },   
                     {  
                      	"sClass":"center",  
                       "aTargets":[3],  
                       "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象
                    		if(c.IMG_PATH!=null&&c.IMG_PATH!=''){
                    			//return '<img class="wt_img" src="http://139.196.169.139:8082/jianlian/uploadImg/client/'+c.IMG_PATH+'"></img>';
                    			return '<img class="wt_img" src="/jianlian/uploadImg/client/'+c.IMG_PATH+'"></img>';
                         	}else{
                         		return '';
                         	} 
                        }  
                     },  
                     {  
                         "sClass":"center", 
                         "mDataProp" : "AGREE_COUNT", 
                         "aTargets":[4],  
                         "mData":"AGREE_COUNT" 
                     }, 
                     {  
                         "sClass":"center", 
                         "mDataProp" : "COMMENT_COUNT", 
                         "aTargets":[5],  
                         "mData":"COMMENT_COUNT" 
                     }, 
                     {  
                         "sClass":"center", 
                         "mDataProp" : "FOCUS_COUNT", 
                         "aTargets":[6],  
                         "mData":"FOCUS_COUNT" 
                     }, 
                     {  
                         "sClass":"center", 
                         "mDataProp" : "CREATE_DATE", 
                         "aTargets":[7],  
                         "mData":"CREATE_DATE" 
                     }, 
                     {
                         "sClass":"center",  
                         "aTargets":[13],  
                         "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                         	return '<a data-title=\"删除\" href=\"javascript:;\" onclick=\"del(\'<%=basePath%>question/delById.do?ID='+c.ID+'\')\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i> 删除</a>'; 
                        }  
                     }
					], 
					  
					 "fnServerData" : function(sSource, aoData, fnCallback) {  
		                //获取检索参数
						var USERNAME = $("#REAL_NAME").val(); 
						var TITLE = $("#TITLE").val(); 
						 $.ajax({
				                url : sSource,//这个就是请求地址对应sAjaxSource
				                data : {"aoData":JSON.stringify(aoData),"REAL_NAME":USERNAME,"TITLE":TITLE},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
		
			function del(url){
				 layer.confirm('确认要删除该数据吗？',function(index){ 
					//此处请求后台程序，下方是成功后的前台处理…… 
					$.ajax({
		                url : url, 
		                type : 'post',
		                dataType : 'json',
		                async : false,
		                success : function(result) {
		                	 if(result.statusCode ==200){
		                		table.fnDraw();
		                		$(".layui-layer-setwin .layui-layer-close1").click();//关闭遮罩层
			                } 
		                },
		                error : function(msg) {
		                }
		            });
				 }); 
			} 
		</script> 
	</body> 
</html>