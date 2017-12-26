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
		<title>升级入会管理</title>
		<style>
            .wt_img{
               width:200px;
               height:150px;
            }		
		</style>
	</head>

	<body class="pos-r"> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>会员管理<span class="c-gray en">&gt;</span>升级入会管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav> 
			<div class="page-container">
				<div class="text-c"> 
				     真实姓名：
				     <input type="text" id="NAME"  name="NAME"  placeholder="真实姓名" style="width:150px" class="input-text">&nbsp;&nbsp;
				        公司名称：
				     <input type="text" id="GSNAME"  name="GSNAME"  placeholder="公司名称" style="width:250px" class="input-text">&nbsp;&nbsp;
				     状态：<select id="STATUS" name="STATUS" class="select-box" style="width: 150px;">
				     	<option value="">--请选择审核状态--</option>
				     	<option value="01">--审核中--</option>
				     	<option value="03">--审核通过--</option>
				     	<option value="02">--审核不通过--</option>
				     </select>&nbsp;&nbsp;
				     <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
				</div>
				<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				    <%--  <span class="l">
				        <a href="javascript:;" onclick="piliangdel('<%=basePath%>activity/delactivity.do')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> 
					    <a class="btn btn-primary radius" data-title="添加活动" onclick="act_edit('添加活动','<%=basePath%>activity/queryById.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
					    <a class="btn btn-primary radius" data-title="审核" onclick="act_check('审核','<%=basePath%>activity/queryshById.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>审核</a>
					    <a class="btn btn-primary radius" data-title="上传活动封面" onclick="img_edit('上传活动封面','<%=basePath%>activity/toImgAdd.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>上传活动封面</a>
					</span>  --%>
                </div>
				<div class="mt-20">
					<form id="form-activity">
					<table class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c"> 
								<th style="width: 100px;">真实姓名</th> 
								<th style="width: 100px;">手机号</th> 
								<th style="width: 100px;">卡号</th>
								<th style="width: 100px;">所属行业</th>
								<th style="width: 100px;">所属建联</th>
								<th style="width: 100px;">状态</th> 
								<th style="width: 150px;">当前权益</th> 
								<th style="width: 150px;">升级权益</th>  
								<th style="width: 300px;">公司名称</th> 
								<!-- <th style="width: 150px;">职务</th> -->
								<th style="width: 200px;">创建时间</th>
								<th style="width: 200px;">操作</th> 
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
					[9, "desc"]
				], //默认第几个排序
				"aoColumnDefs": [
					/* {"bVisible": false, "aTargets": [8]} */ //控制列的隐藏显示
					{"orderable":false,"aTargets":[7]} 
				],
				"bStateSave": true, //状态保存
				"bProcessing": false, // 是否显示取数据时的那个等待提示
                "bServerSide": true,//这个用来指明是通过服务端来取数据
                "sAjaxSource": "<%=basePath%>umbs/queryList.do",//这个是请求的地址
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
          	                     "mDataProp" : "PHONE", 
          	                     "aTargets":[1],  
          	                     "mData":"PHONE" 
          	                 },
          	               {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "CARD_NO", 
          	                     "aTargets":[2],  
          	                     "mData":"CARD_NO" 
          	                 },
          	               {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "CATE_NAME", 
          	                     "aTargets":[3],  
          	                     "mData":"CATE_NAME" 
          	                 },
          	               {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "CLAN_NAME", 
          	                     "aTargets":[4],  
          	                     "mData":"CLAN_NAME" 
          	                 },
          	                 {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "STATUS", 
          	                     "aTargets":[5],  
          	                   	 "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象
	          	                   	if(c.STATUS == '00'){ 
	                         			return '未支付';
	                              	}if(c.STATUS == '01'){ 
                             			return '审核中';
                                  	}if(c.STATUS == '02'){ 
                             			return '审核不通过';
                                  	}if(c.STATUS == '03'){ 
                             			return '审核通过';
                                  	}if(c.STATUS == '04'){ 
                             			return '撤销中';
                                  	}if(c.STATUS == '05'){ 
                             			return '已撤销';
                                  	} 
                                 }  
          	                 },
          	                 {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "LEVELNAME", 
          	                     "aTargets":[6],  
          	                     "mData":"LEVELNAME" 
          	                 },
          	                  {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "SJLEVELNAME", 
          	                     "aTargets":[7],  
          	                     "mData":"SJLEVELNAME" 
          	                 }, 
          	                  {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "COMPANY_NAME", 
          	                     "aTargets":[8],  
          	                     "mData":"COMPANY_NAME" 
          	                 },
          	              /*  {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "POSITION", 
          	                     "aTargets":[6],  
          	                     "mData":"POSITION" 
          	                 }, */
          	                {  
          	                     "sClass":"center", 
          	                     "mDataProp" : "DATE", 
          	                     "aTargets":[9],  
          	                     "mData":"DATE" 
          	                 },
          	                 {
          	                     "sClass":"center",  
          	                     "aTargets":[10],  
          	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
          	                    	 var ro = "";
          	                    	if(c.STATUS == '01'){ 
          	                    		ro +='<a data-title=\"审核\" onclick=\"act_edit(\'审核\',\'<%=basePath%>umbs/toEdit.do?ID='+c.ID+'\',\'\',\'550\')\" href=\"javascript:;\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class="Hui-iconfont">&#xe600;</i> 审核</a>&nbsp;'; 
                                  	}
          	                     	ro +='<a data-title=\"查看\" onclick=\"act_edit(\'查看\',\'<%=basePath%>umbs/toSee.do?ID='+c.ID+'\',\'\',\'550\')\" href=\"javascript:;\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i> 查看</a>'; 
          	                        return ro;
          	                     }  
          	                 }
          			], 
				 "fnServerData" : function(sSource, aoData, fnCallback) {  
	                //获取检索参数
					var NAME = $("#NAME").val();
					var GSNAME = $("#GSNAME").val();
					var STATUS = $("#STATUS").val();
					 $.ajax({
			                url : sSource,//这个就是请求地址对应sAjaxSource
			                data : {"aoData":JSON.stringify(aoData),"NAME":NAME,"GSNAME":GSNAME,"STATUS":STATUS},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
			                type : 'post',
			                dataType : 'json',
			                async : false,
			                success : function(result) {
			                	fnCallback(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
			                	$(".01").css("color","red"); 
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
		
			function act_del(url){ 
				 layer.confirm('确认要删除该数据吗？',function(index){ 
					//此处请求后台程序，下方是成功后的前台处理…… 
					$.ajax({
		                url : url, 
		                type : 'post',
		                dataType : 'json',
		                async : false,
		                success : function(result) {
		                	 if(result.statusCode == 200){
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
			function act_edit(title,url,w,h){ 
				var index=layer.open({
					type:2,
					title:title,
					content:url

			});
				layer.full(index);
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
			         data:$('#form-activity').serialize(), 
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
