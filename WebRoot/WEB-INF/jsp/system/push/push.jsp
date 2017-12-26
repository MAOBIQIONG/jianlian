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
		<title>活动管理</title>
	</head>

	<body class="pos-r"> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>消息推送<span class="c-gray en">&gt;</span>推送管理
				<!-- <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a> -->
			</nav> 
			<div class="page-container">
				<form class="form form-horizontal" id="form-horizontal"> 
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>推送标题：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input type="text" class="input-text valib" placeholder="" id="Title" name="Title" data-rule-required="true">
						</div>
					</div>  
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>推送内容：</label>
						<div class="formControls col-xs-8 col-sm-9"> 
					       <textarea class="textarea"  placeholder=""  id="Text" name="Text" data-rule-required="true"></textarea> 
						</div>
					</div> 
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否全部推送消息：</label> 
						<div class="formControls col-xs-8 col-sm-9 skin-minimal">
							<div class="radio-box">
								<input id="isFree" name="IS_FREE" type="radio" value="1">
								<label for="flag-1">是</label>
							</div>
							<div class="radio-box">
								<input id="noFree" type="radio"  name="IS_FREE" value="2">
								<label for="flag-2">否</label>
							</div> 
						</div>
					</div> 
					<!-- <div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否全部推送消息：</label>
						<div class="formControls col-xs-8 col-sm-9"> 
					       	<button class="btn btn-success">是</button>
					       	<button class="btn btn-success" onclick="tiao()">否</button>
						</div>
					</div>  -->
					
				</form>
					 <div id="xian" style="border: 1px solid #cccccc;margin-top:20px;padding-top:20px;display:none;">
						<div class="text-c"> 
						     用户姓名：<input type="text" id="REAL_NAME"  name="REAL_NAME"   style="width:150px" class="input-text"> 
						     公司名称：<input type="text" id="COMPANY_NAME"  name="COMPANY_NAME"   style="width:150px" class="input-text"> 
						     会员等级:<select class="select" size="1" id="VIP_LEVEL" name="VIP_LEVEL" style="width: 200px;height: 31px;">
							    <option value="" selected>所有</option>
							    <option value="02">普通用户</option>
							    <option value="01">会员</option> 
							    <option value="03">副会长</option> 
							    <option value="04">常务副会长</option>
							    <option value="05">会长</option> 
							</select>
				     		<button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
						</div>
						<div class="mt-20">
							<form id="form-push">
								<table class="table table-border table-bordered table-bg table-hover table-sort">
									<thead>
										<tr class="text-c">
										    <th class="center" onclick="selectAll()">
										        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
										    </th>   
											<th style="width: 400px;">用户姓名</th> 
											<th style="width: 250px;">公司职位</th> 
											<th style="width: 880px;">公司名称</th> 
											<th style="width: 400px;">会员等级</th>  
										</tr>
									</thead>
									<tbody> 
									</tbody>
								</table>
							</form>
						</div>
					 </div>  
					 <div class="row cl" style="margin-top:20px;">
						<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
							<input class="btn btn-primary radius" onclick="AppPushAdd()" value="&nbsp;&nbsp;消息推送&nbsp;&nbsp;">
						</div>
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
		$(function(){
        	var value=$("input[name='IS_FREE']:checked").val();
        	if(value=='2'){
        		 $("#xian").show();
        	}else{
        		 $("#xian").hide();
        	} 
     	   $("#noFree").click(function(){ 
     		    $("#xian").show();
     		});
     	   
     	   $("#isFree").click(function(){ 
     		   $("#xian").hide();
 	   		}); 
     	   
 		}); 
		
		var table;
			$(function() {
				//table
				table = $('.table-sort').dataTable({
					"aaSorting": [
						[4, "asc"]
					], //默认第几个排序
					"aoColumnDefs": [
						//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
						//{"orderable":false,"aTargets":[3]} 
					],
					"bStateSave": true, //状态保存
					"bProcessing": false, // 是否显示取数据时的那个等待提示
			              "bServerSide": true,//这个用来指明是通过服务端来取数据
			              "sAjaxSource": "<%=basePath%>push/querypushuser.do",//这个是请求的地址
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
							        return '<input type=\"checkbox\" name=\"ID\" pf=\"'+c.PLATFORM+'\" value=\"'+c.ID+'\">';  
							    }  
							}, 
			                 {  
			                     "sClass":"center", 
			                     "mDataProp" : "REAL_NAME", 
			                     "aTargets":[1], 
			                     "mData":"REAL_NAME"  
		        	   			  
			                 },
			                 {  
			                     "sClass":"center", 
			                     "mDataProp" : "POSITION", 
			                     "aTargets":[2],  
			                     "mData":"POSITION" 
			                 },
			                 {  
			                     "sClass":"center", 
			                     "mDataProp" : "COMPANY_NAME", 
			                     "aTargets":[3],  
			                     "mData":"COMPANY_NAME" 
			                 },
			                 {  
			                     "sClass":"center", 
			                     "mDataProp" : "NAME", 
			                     "aTargets":[4],  
			                     "mData":"NAME" 
			                 }
					], 
					  
					 "fnServerData" : function(sSource, aoData, fnCallback) {  
						//获取检索参数
					    var REAL_NAME = $("#REAL_NAME").val(); 
					    var COMPANY_NAME = $("#COMPANY_NAME").val(); 
					    var VIP_LEVEL = $("#VIP_LEVEL").val(); 
						 $.ajax({
				                url : sSource,//这个就是请求地址对应sAjaxSource
				                data : {"aoData":JSON.stringify(aoData),"REAL_NAME":REAL_NAME,"COMPANY_NAME":COMPANY_NAME,"VIP_LEVEL":VIP_LEVEL},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
				});
		
		
		 
 		
		/* $(function(){
			var validate = $("#form-horizontal").validate({ 
			   submitHandler: function(form){ 
				   AppPushAdd(); //执行提交方法
		        },
		     });    
		 }); */
		function AppPushAdd(){    
			var obj = document.getElementsByName ("ID"); 
			var value=$("input[name='IS_FREE']:checked").val();
			var text=$("#Text").val(); 
			if(value==null||value==""||text==null||text==""||text==undefined){
				if(value==null||value==""){
					alert("请选择推送类型！");
				} 
				if(text==null||text==""||text==undefined){
					alert("请选择推送内容！");
				}
			}else {
				var check_val = [];
				var check_ios = [];
			    for(k in obj){
			        if(obj[k].checked){ 
			        	//alert(obj[k].value);获取checkBox的值
			        	if( obj[k].getAttribute("pf") == "0" ){
			        		check_ios.push(obj[k].value);
			        	}else{
			        		check_val.push(obj[k].value);
			        	}
			        } 
			    } 
				var index = parent.layer.getFrameIndex(window.name); 
				  $.ajax({
	                url : "<%=basePath%>push/pushToApp.do?ID="+check_val+"&IOSID="+check_ios,
	                data :$('#form-horizontal').serialize(),
	                type : 'post',
	                dataType : 'json',
	                async : false,
	                success : function(result) {
	                	if(result.status==200){
	                		alert("消息推送成功！");
	                		parent.$('.breadcrumb .r .Hui-iconfont').click();
		       				parent.layer.close(index);
	                	}else{
	                		alert("消息推送失败！");
	                	} 
	                },
	                error : function(msg) {
	                }
	            });
			  }
		   }
		</script>
</body> 
</html>
