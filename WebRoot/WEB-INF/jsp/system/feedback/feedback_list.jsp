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
		<title>字典管理</title>
	</head>
	<body class="pos-r"> 
		<div>
		    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>字典管理 <span class="c-gray en">&gt;</span>字典列表 
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav> 
			<div class="page-container">
				<div class="text-c"> 
		            <input type="text" id="NAME"  name="NAME"  placeholder="名称" style="width:250px" class="input-text"> 
		            <input type="text" id="StartTime" name="StartTime" placeholder="开始时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate"  style="width:250px">
		            <input type="text" id="EndTime" name="EndTime" placeholder="结束时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate"  style="width:250px">
				    <button name="search" id="search" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
				</div> 
				<div class="mt-20">
					<table class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c"> 
							    <th>反馈人</th>  
								<th>反馈内容</th>
								<th>联系邮箱</th> 
								<th>查看图片</th>
								<th>反馈时间</th>  
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
					[4, "asc"]
				], //默认第几个排序
				"aoColumnDefs": [
					//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
					{"orderable":false,"aTargets":[3]} 
				],
				"bStateSave": true, //状态保存
				"bProcessing": false, // 是否显示取数据时的那个等待提示
                "bServerSide": true,//这个用来指明是通过服务端来取数据
                "sAjaxSource": "<%=basePath%>feedback/queryByParam.do",//这个是请求的地址
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
                       	"mDataProp" : "CONTENT",  
                        "aTargets":[1],  
                        "mData":"CONTENT"
                    }, 
                    {  
                        "sClass":"center", 
                        "mDataProp" : "EMAIL", 
                        "aTargets":[2],  
                        "mData":"EMAIL"
                    },
                    {  
                        "sClass":"center", 
                        "mDataProp" : "IMG_PATH", 
                        "aTargets":[3],  
                        "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
                     	   return '<a class=\"btn btn-link\" onclick=\"feedback_edit(\'查看图片\',\'<%=basePath%>feedback/look.do?ID='+c.ID+'\',\'\',\'300\')\" href=\"javascript:;\">查看图片</a>';
                         }   
                    },
                    {  
                        "sClass":"center", 
                        "mDataProp" : "CREATE_DATE", 
                        "aTargets":[4],  
                        "mData":"CREATE_DATE"
                    }
                    
				],
				 "fnServerData" : function(sSource, aoData, fnCallback) {  
	                //获取检索参数
					var NAME = $("#NAME").val();
					var StartTime = $("#StartTime").val(); 
	                var EndTime=$("#EndTime").val(); 
					 $.ajax({
			                url : sSource,//这个就是请求地址对应sAjaxSource
			                data : {"aoData":JSON.stringify(aoData),"REAL_NAME":NAME,"StartTime":StartTime,"EndTime":EndTime},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
		
		function feedback_edit(title,url,w,h){  
			var index=layer.open({
				type:2,
				title:title,
				content:url 
			});
			layer.full(index);
		} 
	</script> 
</body>

</html>
			