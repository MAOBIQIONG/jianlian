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
		<title>商品管理</title>
	</head>

	<body class="pos-r"> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>商城管理 <span class="c-gray en">&gt;</span>订单管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav>
			<div class="page-container">
				<div class="text-c"> 
				    <div class="text-c">
					    <input type="text" name="SHOP_USERNAME" id="SHOP_USERNAME" placeholder="店铺名称" style="width:250px" class="input-text">&nbsp;&nbsp;&nbsp;
					    <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
				    </div>
				</div>  
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l"> 
						<a class="btn btn-primary radius" data-title="新增" onclick="shop_edit('新增','<%=basePath%>appproductOrder/toAdd.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>新增商品订单</a>	
					</span>  
				</div> 
				<div class="mt-20">
					<table class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c"> 
							    <th>店铺名称</th>
								<th>店铺地址</th>
								<th>联系手机号</th>
								<th>订单编号</th>  
								<th>销售/购买订单</th>
								<th>订单发货日期</th>
								<th>订单完成日期</th>
								<th>状态</th>
								<th>运费</th>
								<th>快递名称</th>
								<th>快递单号</th>
								<th>总价格</th>
								<th>是否结算佣金</th>
								<th>佣金相关值</th>
								<th>创建订单时间</th>
								<th width="220">操作</th>
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
						//[8, "desc"]
					], //默认第几个排序
					"aoColumnDefs": [
						//{"bVisible": false, "aTargets": [2 ]}, //控制列的隐藏显示
						//{"orderable":false,"aTargets":[9]} 
					],
					"bStateSave": true, //状态保存
					"bProcessing": false, // 是否显示取数据时的那个等待提示
	                "bServerSide": true,//这个用来指明是通过服务端来取数据
	                "sAjaxSource": "<%=basePath%>appproductOrder/queryList.do",//这个是请求的地址
	                "bLengthChange" : true,// 每行显示记录数  
	                "iDisplayLength" : 10,// 每页显示行数  
	                "bSort" : true,// 排序
	                "bAutoWidth" : false, //是否自适应宽度  
	                "searching":false,//隐藏右侧搜索框 
	                "bPaginate": true, //翻页功能
	                "bLengthChange": true, //改变每页显示数据数量
					"aoColumns": [ 
	                 {  
                       	"sClass":"right", 
                       	"mDataProp" : "SHOP_USERNAME",  
                        "aTargets":[0],  
                        "mData":"SHOP_USERNAME" 
	                 }, 
	                 {  
	                     "sClass":"right", 
	                     "mDataProp" : "SHOP_ADDRESS", 
	                     "aTargets":[1],  
	                     "mData":"SHOP_ADDRESS" 
	                 },  
	                 {  
	                     "sClass":"right", 
	                     "mDataProp" : "SHOP_PHONE", 
	                     "aTargets":[2],  
	                     "mData":"SHOP_PHONE" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "ORDER_NO", 
	                     "aTargets":[3],  
	                     "mData":"ORDER_NO" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "TYPE", 
	                     "aTargets":[4],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
	                     	 if(c.TYPE == '00'){
	                     		 return '销售订单';
	                     	 }
	                     	 if(c.TYPE == '01'){
	                     		 return '购买订单';
	                     	 }
	                     }
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "DELIVER_DATE", 
	                     "aTargets":[5],  
	                     "mData":"DELIVER_DATE" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "FINISH_DATE", 
	                     "aTargets":[6],  
	                     "mData":"FINISH_DATE" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "ORDER_STATU", 
	                     "aTargets":[7],  
	                     "mData":"ORDER_STATU" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "EXPRESS_PRICE", 
	                     "aTargets":[8],  
	                     "mData":"EXPRESS_PRICE" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "EXPRESS_NAME", 
	                     "aTargets":[9],  
	                     "mData":"EXPRESS_NAME" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "EXPRESS_NO", 
	                     "aTargets":[10],  
	                     "mData":"EXPRESS_NO" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "TOTAL_PRICE", 
	                     "aTargets":[11],  
	                     "mData":"TOTAL_PRICE" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "ISSEET", 
	                     "aTargets":[12],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
	                     	 if(c.ISSEET == '00'){
	                     		 return '未结算';
	                     	 }
	                     	 if(c.ISSEET == '01'){
	                     		 return '已结算';
	                     	 }
	                     }
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "COMM", 
	                     "aTargets":[13],  
	                     "mData":"COMM"
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "CREATE_DATE", 
	                     "aTargets":[14],  
	                     "mData":"CREATE_DATE"
	                 },
	                 {
	                     "sClass":"center",  
	                     "aTargets":[15],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
	                     	var bu='<a data-title=\"编辑\" onclick=\"shop_edit(\'编辑\',\'<%=basePath%>productOrder/goEdit.do?ID='+c.ID+'\',\'\',\'300\')\" href=\"javascript:;\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i> 编辑</a>&nbsp;&nbsp;<a data-title=\"删除\" href=\"javascript:;\" onclick=\"project_del(\'<%=basePath%>productOrder/delete.do?ID='+c.ID+'\')\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i> 删除</a>';
	                     	if(c.ISSEET == '00'){ 
	                      			bu+='&nbsp;&nbsp;<a data-title=\"结算\" href=\"javascript:;\" onclick=\"xiajia(\'<%=basePath%>productOrder/save.do?ID='+c.ID+'&ISSEET=01\')\" class=\"btn btn-danger radius\" style=\"text-decoration:none\">结算</a>';
	                         }
                           	 if(c.ISSEET == '01'){ 
                           		bu+='&nbsp;&nbsp;<a data-title=\"撤销结算\" href=\"javascript:;\" onclick=\"shangjia(\'<%=basePath%>productOrder/save.do?ID='+c.ID+'&ISSEET=00\')\" class=\"btn btn-success radius\" style=\"text-decoration:none\">撤销结算</a>';
	                         } 
	                        return bu;
	                    }  
	                 } 
					],  
						"fnServerData" : function(sSource, aoData, fnCallback) {  
		                //获取检索参数
						var SHOP_USERNAME = $("#SHOP_USERNAME").val(); 
						 $.ajax({
				                url : sSource,//这个就是请求地址对应sAjaxSource
				                data : {"aoData":JSON.stringify(aoData),"SHOP_USERNAME":SHOP_USERNAME},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
			
		
			/*编辑*/
			function shop_edit(title,url,w,h){ 
				var index=layer.open({
					type:2,
					title:title,
					content:url

			});
				layer.full(index);
	         } 
 
			
			
			function shangjia(url){ 
				 layer.confirm('确认要撤销结算订单？',function(index){ 
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
			
			
			function xiajia(url){ 
				 layer.confirm('确认要结算订单吗？',function(index){ 
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
		</script> 
	</body> 
</html> 