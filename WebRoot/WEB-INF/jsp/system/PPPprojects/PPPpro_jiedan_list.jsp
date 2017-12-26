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
		<title>中标管理</title>
	</head>

	<body class="pos-r"> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>PPP项目管理 <span class="c-gray en">&gt;</span>PPP中标管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav>
			<div class="page-container">
				<div class="text-c"> 
				    <div class="text-c">
					    <input type="text" name="PROJECT_NAME" id="PROJECT_NAME" placeholder="项目名称" style="width:250px" class="input-text">&nbsp;&nbsp;&nbsp;
					    <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
				    </div>
				</div>  
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l"> 
					    <a class="btn btn-primary radius"  href="javascript:void(0)" onclick="project_check('<%=basePath%>PPPproject/updateStatus.do?STATUS=6')"><i class="Hui-iconfont">&#xe600;</i>完成交易</a>
					</span>    
				</div>
				
				<div class="mt-20">
					<table class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c">
							    <th class="center" onclick="selectAll()" style="width: 250px">
							        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
							    </th>  
							    <th style="width: 250px;">项目名称</th>
								<th style="width: 250px">项目发布时间</th> 
								<th style="width: 250px">所在地区</th> 
								<th style="width: 880px">详细地址</th>
								<th style="width: 250px">所处阶段</th> 
								<th style="width: 250px">项目状态</th>
								<th style="width: 250px">起始时间</th>
								<th style="width: 250px">截止时间</th>
								<th style="width: 250px">项目经理</th>
								<th style="width: 200px">参与总人数</th>
								<th style="width: 250px">交易金额</th>
								<th style="width: 250px">中标人</th>
								<th style="width: 250px">交易时间</th>
								<th style="width: 250px;">修改时间</th>
								<th style="width: 250px;">创建时间</th>
								<!-- <th>操作</th>  -->
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
						[14, "desc"],
						[15, "desc"]
					], //默认第几个排序
					"aoColumnDefs": [
						//{"bVisible": false, "aTargets": [ 13 ]}, //控制列的隐藏显示
						//{"orderable":false,"aTargets":[0]} 
					],
					"bStateSave": true, //状态保存
					"bProcessing": false, // 是否显示取数据时的那个等待提示
	                "bServerSide": true,//这个用来指明是通过服务端来取数据
	                "sAjaxSource": "<%=basePath%>PPPproject/queryList.do",//这个是请求的地址
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
	                     "mDataProp" :"PROJECT_NAME", 
	                     "aTargets":[1],  
	                     "mData":"PROJECT_NAME" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"RELEASE_TIME", 
	                     "aTargets":[2],  
	                     "mData":"RELEASE_TIME" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"CITY", 
	                     "aTargets":[3],  
	                     "mData":"CITY" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"ADDR", 
	                     "aTargets":[4],  
	                     "mData":"ADDR" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"JDNAME", 
	                     "aTargets":[5],  
	                     "mData":"JDNAME" 
	                 },  
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"PROJECT_STATUS", 
	                     "aTargets":[6],  
	                     "mData":"PROJECT_STATUS"
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"START_DATE", 
	                     "aTargets":[7],  
	                     "mData":"START_DATE"
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"DUE_DATE", 
	                     "aTargets":[8],  
	                     "mData":"DUE_DATE" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"XMJL", 
	                     "aTargets":[9],  
	                     "mData":"XMJL"
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "TOTAL_BIDDER", 
	                     "aTargets":[10],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                            return '<a _href=\"<%=basePath%>PPPproBidder/toBidder.do?PROJECT_ID='+c.ID+'&BIDDER_ID='+c.BID_WINNER_ID+'\" data-title=\"查看参与人员\" class=\"btn btn-link\" onclick=\"Hui_admin_tab(this)\" href=\"javascript:;\"  >'+c.TOTAL_BIDDER+'</a>';
                         }  
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"BID_PRICE", 
	                     "aTargets":[11],  
	                     "mData":"BID_PRICE"
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"BIDDER", 
	                     "aTargets":[12],  
	                     "mData":"BIDDER"
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"COMPLETE_DATE", 
	                     "aTargets":[13],  
	                     "mData":"COMPLETE_DATE"
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"MODIFY_DATE", 
	                     "aTargets":[14],  
	                     "mData":"MODIFY_DATE"
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" :"CREATE_DATE", 
	                     "aTargets":[15],  
	                     "mData":"CREATE_DATE"
	                 }
					],  
					 "fnServerData" : function(sSource, aoData, fnCallback) {  
		                //获取检索参数
						var PROJECT_NAME = $("#PROJECT_NAME").val(); 
						 $.ajax({
				                url : sSource,//这个就是请求地址对应sAjaxSource
				                data : {"aoData":JSON.stringify(aoData),"PROJECT_NAME":PROJECT_NAME,"STATUS":4},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
			
		
			/*编辑*/
			function project_edit(title,url,w,h){ 
				var index=layer.open({
					type:2,
					title:title,
					content:url

			});
				layer.full(index);
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
			
			function project_zhongbiao(title,url,w,h){ 
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
	
	function project_check(url){  
	   var checklist = document.getElementsByName ("ids");
	   var len=$("input[name='ids']:checked").length;  
	   if(len==0){
	      alert("请选择一个项目！");
	   }else if(len>1){
	      alert("只能选择一个项目！");
	   }else{ 
	      var data=$("input[name='ids']:checked").val();  
		 layer.confirm('确认完成交易吗？',function(index){  
			//此处请求后台程序，下方是成功后的前台处理…… 
			 $.ajax({
	              url : url, 
	              type : 'post',
	              dataType : 'json',
	              data:{"ID":data},//已成功交易
	              async : false,
	              success : function(result) { 
	              	  if(result.status == 200){
	              		table.fnDraw();
	              		$(".layui-layer-setwin .layui-layer-close1").click();
		              }   
	              },
	              error : function(msg) {
	              }
	          });
		 }); 
	   }
	}
</script> 
</body> 
</html> 