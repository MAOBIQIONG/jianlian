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
		<title>会员到期管理</title>
	</head>

	<body class="pos-r"> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>企业管理 <span class="c-gray en">&gt;</span>会员到期管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav>
			<div class="page-container">
				<div class="text-c"> 
		             <input type="text" id="REAL_NAME"  name="REAL_NAME"  placeholder="用户名称" style="width:150px" class="input-text"> 
		             <input type="text" id="USER_NAME"  name="USER_NAME"  placeholder="用户账号" style="width:150px" class="input-text"> 
		             <input type="text" id="CARD_NO"  name="CARD_NO"  placeholder="卡号" style="width:150px" class="input-text"> 
		             <input type="text" id="PHONE"  name="PHONE"  placeholder="手机号" style="width:150px" class="input-text"> 
		             <input type="text" id="COMPANY_NAME"  name="COMPANY_NAME"  placeholder="公司名称" style="width:150px" class="input-text"> 
		                                       会员等级：
		             <span class="select-box" style="width:100px;">
					  <select class="select" size="1" name="level" id="LEVEL">
					    <option value="" selected>请选择</option>
					    <option value="01">会员</option>
					    <option value="02">普通用户</option>
					    <option value="03">副会长</option>
					    <option value="04">常务副会长</option>
					    <option value="05">会长</option>
					  </select>
					</span>&nbsp;&nbsp;
				     <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
			    </div>  
				<div class="mt-20">
					<form id="form-mem">
						<table class="table table-border table-bordered table-bg table-hover table-sort">
							<thead>
								<tr class="text-c"> 
									<th>会员名称</th> 
									<th>会员账号</th>  
									<th style="width:250px;">所属公司</th>
									<th>职位</th>
									<th>联系电话</th>  
									<th>会员等级</th>
									<th>卡号</th>
									<th>入会时间</th>
									<th>到期时间</th> 
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
						[8, "desc"]
					], //默认第几个排序
					"aoColumnDefs": [
						//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
						//{"orderable":false,"aTargets":[3]} 
					],
					"bStateSave": true, //状态保存
					"bProcessing": false, // 是否显示取数据时的那个等待提示
	                "bServerSide": true,//这个用来指明是通过服务端来取数据
	                "sAjaxSource": "<%=basePath%>userEndtime/listPage.do",//这个是请求的地址
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
	                     "mDataProp" : "USER_NAME", 
	                     "aTargets":[1],  
	                     "mData":"USER_NAME" 
	                 },  
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "C_NAME", 
	                     "aTargets":[2],  
	                     "mData":"C_NAME" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "POSITION", 
	                     "aTargets":[3],  
	                     "mData":"POSITION" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "PHONE", 
	                     "aTargets":[4],  
	                     "mData":"PHONE" 
	                 },  
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "LEVEL_NAME", 
	                     "aTargets":[5],  
	                     "mData":"LEVEL_NAME" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "CARD_NO", 
	                     "aTargets":[6],  
	                     "mData":"CARD_NO" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "UPGRADE_DATE", 
	                     "aTargets":[7],  
	                     "mData":"UPGRADE_DATE" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "DUE_DATE", 
	                     "aTargets":[8],  
	                     "mData":"DUE_DATE" 
	                 } 
			], 
			  
			 "fnServerData" : function(sSource, aoData, fnCallback) {  
				//获取检索参数
			    var REAL_NAME = $("#REAL_NAME").val(); 
			    var CARD_NO = $("#CARD_NO").val(); 
			    var PHONE = $("#PHONE").val(); 
			    var USER_NAME = $("#USER_NAME").val(); 
			    var COMPANY_NAME = $("#COMPANY_NAME").val(); 
			    var LEVEL = $("#LEVEL").val();  
				 $.ajax({
		                url : sSource,//这个就是请求地址对应sAjaxSource
		                data : {"aoData":JSON.stringify(aoData),"REAL_NAME":REAL_NAME,"CARD_NO":CARD_NO,"PHONE":PHONE,"USER_NAME":USER_NAME,"COMPANY_NAME":COMPANY_NAME,"VIP_LEVEL":LEVEL},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
			 
		</script>
	</body>
</html>