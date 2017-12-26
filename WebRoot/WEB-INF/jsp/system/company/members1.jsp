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
		<title>内部员工管理</title>
	</head>

	<body class="pos-r"> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>企业管理 <span class="c-gray en">&gt;</span>内部员工管理
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
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l">
					    <%-- <a class="btn btn-primary radius" data-title="录入会员信息" onclick="tiao('录入会员信息','<%=basePath%>tbuser/userinfo_add.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>录入会员信息</a>
					    --%> <a class="btn btn-primary radius" data-title="录入内部员工信息" onclick="tiao('录入内部员工信息','<%=basePath%>tbuser/innerUser_add.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>录入内部员工信息</a>
					   <%--  <a class="btn btn-primary radius" data-title="添加用户" onclick="tiao('添加用户','<%=basePath%>tbuser/adduserid.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加</a> --%>
					    <a class="btn btn-primary radius" data-title="完善资料" href="javascript:;" onclick="fenpei('完善资料','<%=basePath%>tbuser/querybywanid.do','','550')"><i class="Hui-iconfont">&#xe600;</i>完善资料</a>
					   <a class="btn btn-primary radius" data-title="完善信息" href="javascript:;" onclick="fenpei('完善信息','<%=basePath%>tbuser/querybywsxx.do','','550')"><i class="Hui-iconfont">&#xe600;</i>完善信息</a>
					    <a class="btn btn-primary radius" data-title="分配" href="javascript:;" onclick="fenpei('分配','<%=basePath%>tbuser/fenpei.do','','550')"><i class="Hui-iconfont">&#xe600;</i>分配</a> 
					    <a href="javascript:;" onclick="piliangdel('<%=basePath%>tbuser/delTbuser.do')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> 
					    <a  class="btn btn-primary radius" data-title="修改用户身份标识" href="javascript:;" onclick="fenpei('修改用户身份标识','<%=basePath%>tbuser/user_identify.do','','550')"><i class="Hui-iconfont">&#xe600;</i>修改用户身份标识</a> 
						<%-- <a class="btn btn-primary radius" data-title="审核" href="javascript:;" onclick="tiaosh('审核','<%=basePath%>tbuser/querybyid.do','','550')"><i class="Hui-iconfont">&#xe600;</i>审核/不审核</a> --%>
				    </span>  
				</div> 
				<div class="mt-20">
					<form id="form-mem">
						<table class="table table-border table-bordered table-bg table-hover table-sort">
							<thead>
								<tr class="text-c">
								    <th class="center" onclick="selectAll()">
								        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
								    </th>  
									<th>会员名称</th> 
									<th>会员账号</th>  
									<th style="width:250px;">所属公司</th>
									<th>职位</th>
									<th>联系电话</th>
									<th>邮箱</th>
									<th>是否为会员</th>
									<th>会员等级</th>
									<th>卡号</th>
									<th>服务类型</th>
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
						//[1, "asc"]
					], //默认第几个排序
					"aoColumnDefs": [
						//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
						{"orderable":false,"aTargets":[0,11]} 
					],
					"bStateSave": true, //状态保存
					"bProcessing": false, // 是否显示取数据时的那个等待提示
	                "bServerSide": true,//这个用来指明是通过服务端来取数据
	                "sAjaxSource": "<%=basePath%>tbuser/querytbuser.do",//这个是请求的地址
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
	                     "mDataProp" : "REAL_NAME", 
	                     "aTargets":[1],  
	                     "mData":"REAL_NAME" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "USER_NAME", 
	                     "aTargets":[2],  
	                     "mData":"USER_NAME" 
	                 },  
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "C_NAME", 
	                     "aTargets":[3],  
	                     "mData":"C_NAME" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "POSITION", 
	                     "aTargets":[4],  
	                     "mData":"POSITION" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "PHONE", 
	                     "aTargets":[5],  
	                     "mData":"PHONE" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "EMAIL", 
	                     "aTargets":[6],  
	                     "mData":"EMAIL" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "VIP_NAME", 
	                     "aTargets":[7],  
	                     "mData":"VIP_NAME" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "LEVEL_NAME", 
	                     "aTargets":[8],  
	                     "mData":"LEVEL_NAME" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "CARD_NO", 
	                     "aTargets":[9],  
	                     "mData":"CARD_NO" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "SERVICE_TYPEN", 
	                     "aTargets":[10],  
	                     "mData":"SERVICE_TYPEN" 
	                 }, 
	                 {
	                     "sClass":"center",  
	                     "aTargets":[11],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
	                     	return '<a data-title=\"编辑\" onclick=\"tiao(\'编辑\',\'<%=basePath%>tbuser/querybyid1.do?ID='+c.ID+'\',\'\',\'550\')\" href=\"javascript:;\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i> 编辑</a>&nbsp&nbsp<a data-title=\"删除\" href=\"javascript:;\" onclick=\"delbyid(\'<%=basePath%>tbuser/delmembyid.do?ID='+c.ID+'\')\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i> 删除</a>'; 
	                    }  
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
		                data : {"aoData":JSON.stringify(aoData),"REAL_NAME":REAL_NAME,"CARD_NO":CARD_NO,"PHONE":PHONE,"USER_NAME":USER_NAME,"COMPANY_NAME":COMPANY_NAME,"VIP_LEVEL":LEVEL,"TY":0},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
				         data:$('#form-mem').serialize(), 
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
				}

				//审核
				function tiaosh(title,url,w,h){
					    var checklist = document.getElementsByName ("ID");
					   var len=$("input[name='ID']:checked").length; 
					   if(len==0){
					      alert("请选择一个会员！");
					   }else if(len>1){
					      alert("只能选择一个会员！");
					   }else{
					      var data=$("input[name='ID']:checked").val();
					      var URL=url+"?ID="+data;
					      var index=layer.open({
					  		type:2,
					  		title:title,
					  		content:URL

					  });
					  	layer.full(index); 
					   }
				    }

				function piliangdel(url){  
				   $.ajax({  
				        url : url, 
				         type : 'post',
				         dataType : 'json',
				         data:$('#form-mem').serialize(), 
				         async: false,
				        success : function(result) { 
				          		parent.$('.breadcrumb .r .Hui-iconfont').click();
				          		window.location.reload();   
				           },
				           error : function(msg) {
				           }
				     });
				}

				function project_del(url){
								 layer.confirm('确认要删除该数据吗？',function(index){ 
									//此处请求后台程序，下方是成功后的前台处理…… 
									$.ajax({
						                url : url, 
						                type : 'post',
						                dataType : 'json',
						                async : false,
						                success : function(result) {
						                	 /*if(result.status == 1){*/
						                		parent.$('.breadcrumb .r .Hui-iconfont').click();
						                		window.location.reload(); 
						                },
						                error : function(msg) {
						                }
						            });
								 }); 
							}
							
				function delbyid(url){  
				layer.confirm('确认要删除该数据吗？',function(index){ 
				   $.ajax({  
				         type: "POST",
				         url:url,
				         data:$('#form-pany').serialize(), 
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
				//禁止/解禁
				function uppany(url){
					   var checklist = document.getElementsByName ("ID");
					   var len=$("input[name='ID']:checked").length;
					   alert(len);
					   if(len==0){
					      alert("请选择一个需要审核/不审核的用户！");
					   }else if(len>1){
					      alert("只能选择一个需要审核/不审核的用户！");
					   }else{
					   var data=$("input[name='ID']:checked").val();
				       $.ajax({  
					         type: "POST",
					         url:url,
					         data:$('#form-mem').serialize(),  
					         async: false,
					          success : function(result) { 
					       /*    
				             if(result.statusCode == 200){ */
				              		parent.$('.breadcrumb .r .Hui-iconfont').click();
				              		window.location.reload();   
				               },
				               error : function(msg) {
				               }
					    });
				    }
				    }
				   //分配
				function fenpei(title,url,w,h){
					    var checklist = document.getElementsByName ("ID");
					   var len=$("input[name='ID']:checked").length; 
					   if(len==0){
					      alert("请选择一个会员！");
					   }else if(len>1){
					      alert("只能选择一个会员！");
					   }else{
					      var data=$("input[name='ID']:checked").val();
					      var URL=url+"?ID="+data;
					      var index=layer.open({
					  		type:2,
					  		title:title,
					  		content:URL

					  });
					  	layer.full(index); 
					   }
				    }
				    
				  //重置密码
				  function uppw(url){
					  var checklist = document.getElementsByName ("ID");
					   var len=$("input[name='ID']:checked").length;
					   if(len==0){
					      alert("请选择一个需要重置的用户！");
					   }else if(len>1){
					      alert("只能选择一个需要重置的用户！");
					   }else{
					   var data=$("input[name='ID']:checked").val();
				       $.ajax({  
					         type: "POST",
					         url:url,
					         data:$('#form-mem').serialize(),  
					         async: false,
					          success : function(result) {  
				             		alert("密码重置成功！");
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