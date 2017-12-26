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
		<title>订单管理</title>
	</head>

	<body class="pos-r"> 
		<div> 
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>订单管理 <span class="c-gray en">&gt;</span>订单管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav>
			<div class="page-container">
				<div class="text-c">  
					<!-- <input type="text" name="order_id" id="order_id" placeholder="订单号" style="width:200px" class="input-text">&nbsp;&nbsp; -->
				           状态:<select class="select" size="1" id="STATUS" name="STATUS" style="width: 200px;height: 31px;">
					    <option value="" selected>所有</option>
					    <option value="01">未支付</option>
					    <option value="02">支付失败</option>
					    <option value="03">支付成功</option> 
					    <option value="04">退款中</option> 
					    <option value="05">已退款</option>
					    <option value="06">已作废</option>
					    <option value="07">索要发票</option>
					    <option value="08">发票已处理</option>
					    <option value="20">已取消订单</option> 
					</select> &nbsp;&nbsp;
					支付方式:<select class="select" size="1" id="PAY_TYPE" name="PAY_TYPE" style="width: 200px;height: 31px;">
						<option value="">所有</option>
					    <option value="ALI_APP">支付宝</option>
					    <option value="WX_APP">银联</option>
					    <option value="UN_WEB">微信</option>
					 </select>&nbsp;&nbsp;
					 类型:<select class="select" size="1" id="OTYPE" name="OTYPE" style="width: 200px;height: 31px;">
						<option value="">所有</option>
					    <option value="01">会费</option>
					    <option value="02">活动费</option>
					 </select>
				     <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
				</div>
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l">
					    <a class="btn btn-primary radius" data-title="新增订单" onclick="orders_edit('新增订单','<%=basePath%>pay/toAdd.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 新增</a>	
					    <a href="<%=basePath%>pay/exportfeedback.do" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe600;</i>导出Excel</a> 
					</span>  
				</div> 
				<div class="mt-20">
					<table class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c">  
								<th>订单号</th>
								<th>会员</th>
								<th>手机号</th>
								<th>卡号</th>
								<th>所属建联</th>
								<th>所属行业</th>
								<th>类型</th>
								<th>事件</th>
								<th>状态</th>
								<th>金额（元）</th>
								<th>支付方式</th> 
								<th>日期</th>
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
						[11, "desc"]
					], //默认第几个排序
					"aoColumnDefs": [
						//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
						//{"orderable":false,"aTargets":[3]} 
					],
					"bStateSave": true, //状态保存
					"bProcessing": false, // 是否显示取数据时的那个等待提示
	                "bServerSide": true,//这个用来指明是通过服务端来取数据
	                "sAjaxSource": "<%=basePath%>pay/queryPageList.do",//这个是请求的地址
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
	                            "mDataProp" : "ID",  
	                            "aTargets":[0],  
	                            "mData":"ID"
	                     },
                        {  
                            "sClass":"center",  
                            "mDataProp" : "REAL_NAME",  
                            "aTargets":[1],  
                            "mData":"REAL_NAME"
                        },
                        {  
                            "sClass":"center",  
                            "mDataProp" : "PHONE",  
                            "aTargets":[2],  
                            "mData":"PHONE"
                        },
                        {  
                            "sClass":"center",  
                            "mDataProp" : "CARD_NO",  
                            "aTargets":[3],  
                            "mData":"CARD_NO"
                        },
                        {  
                            "sClass":"center",  
                            "mDataProp" : "CLAN_NAME",  
                            "aTargets":[4],  
                            "mData":"CLAN_NAME"
                        },
                        {  
                            "sClass":"center",  
                            "mDataProp" : "CATE_NAME",  
                            "aTargets":[5],  
                            "mData":"CATE_NAME"
                        },
                        {  
                            "sClass":"center", 
                            "mDataProp" : "OTYPE", 
                            "aTargets":[6],  
                            "mData":"OTYPE"
                        },
                        {  
                            "sClass":"center", 
                            "mDataProp" : "EVENT", 
                            "aTargets":[7],  
                            "mData":"EVENT"
                        },
                       /*  {  
                            "sClass":"center", 
                            "mDataProp" : "STATUS_NAME", 
                            "aTargets":[8],  
                            "mData":"STATUS_NAME"
                        }, */
                        {  
     	                     "sClass":"center", 
     	                     "mDataProp" : "STATUS", 
     	                     "aTargets":[8],  
     	                   	 "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象
         	                   	if(c.STATUS == '01'){ 
                        			return '未支付';
                             	}if(c.STATUS == '02'){ 
                        			return '支付失败';
                             	}if(c.STATUS == '03'){ 
                        			return '支付成功';
                             	}if(c.STATUS == '04'){ 
                        			return '退款中';
                             	}if(c.STATUS == '05'){ 
                        			return '已退款';
                             	}if(c.STATUS == '06'){ 
                        			return '已作废';
                             	}if(c.STATUS == '07'){ 
                        			return '索要发票';
                             	}if(c.STATUS == '08'){ 
                        			return '发票已处理';
                             	}if(c.STATUS == '20'){ 
                        			return '订单已取消';
                             	}   
                            }  
     	                 },
                        {  
                            "sClass":"center", 
                            "mDataProp" : "PRICE", 
                            "aTargets":[9],  
                            "mData":"PRICE"
                        }, 
                        {  
                            "sClass":"center", 
                            "mDataProp" : "PAY_NAME", 
                            "aTargets":[10],  
                            "mData":"PAY_NAME"
                        }, 
                        {  
                            "sClass":"center",  
                            "mDataProp" : "DATE", 
                            "aTargets":[11],  
                            "mData":"DATE"
                        },
                        {
   	                     "sClass":"center",  
   	                     "aTargets":[12],  
   	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
   	                    	 var br="";
   	                    	 if(c.STATUS == '01'){
   	                    		 br+='<a data-title=\"确认支付\" onclick=\"editStatus(\''+c.ID+'\',\'03\',\'确认该订单支付成功了吗？\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6dc;</i>确认支付</a>';
   	                    		 br+='<a data-title=\"作废\" onclick=\"editStatus(\''+c.ID+'\',\'06\',\'确认作废该订单吗？\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6dc;</i>作废</a>';
   	                     	 }else  if(c.STATUS == '02'){
   	                     		 br+='<a data-title=\"确认支付\" onclick=\"editStatus(\''+c.ID+'\',\'03\',\'确认该订单支付成功了吗？\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6de;</i>确认支付</a>';
   	                     	 }else  if(c.STATUS == '04'){
   	                     		 br+='<a data-title=\"已退款\" onclick=\"editStatus(\''+c.ID+'\',\'05\',\'确认该订单已退款成功了吗？\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6de;</i>已退款</a>';
   	                     	 }else  if(c.STATUS == '07'){
   	                     		 br+='<a data-title=\"查看发票\" onclick=\"orders_edit(\'查看发票\',\'<%=basePath%>pay/toSee.do?ID='+c.ID+'\',\'\',\'550\')\" href=\"javascript:;\" class=\"btn btn-secondary radius\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i>查看发票</a>'; 
   	                     	 }else {
   	                     		 br+='';
   	                     	 }
   	                     	return br;
   	                    }  
   	                 }
					], 
					  
					 "fnServerData" : function(sSource, aoData, fnCallback) {  
		                //获取检索参数
						var STATUS = $("#STATUS").val();
						/* var order_id = $("#order_id").val(); */
						var PAY_TYPE = $("#PAY_TYPE").val();
						var OTYPE = $("#OTYPE").val();
						
						 $.ajax({
				                url : sSource,//这个就是请求地址对应sAjaxSource
				                data : {"aoData":JSON.stringify(aoData),"PAY_TYPE":PAY_TYPE,"STATUS":STATUS,"OTYPE":OTYPE},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
			function orders_edit(title,url,w,h){
				var index=layer.open({
					type:2,
					title:title,
					content:url 
				});
				layer.full(index);
			}
			
			function editStatus(ID,status,data){ 
				var url='';
				if(status=='03'){
					url='<%=basePath%>pay/updates.do';
				}else{
					url='<%=basePath%>pay/saveOrders.do';
				}
				layer.confirm(data,function(index){ 
				   $.ajax({  
				         type: "POST",
				         url:url,
				         data: {
								ID: ID,
								STATUS: status
						 },
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
		</script>
	</body> 
</html>
			 