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
<LINK rel="Bookmark" href="/favicon.ico">
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
<title>媒体管理</title>
</head>

<body class="pos-r">
	<div>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 媒体管理 <span class="c-gray en">&gt;</span>新闻管理 
			<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
			</a>
		</nav>
		<div class="page-container">
			<div class="text-c">
				<!-- <form id="company-form">
		<input type="text" name="TITLE" value="" id="TITLE" placeholder="标题名称" style="width:250px" class="input-text">
		<button onclick="quserpany('<%=basePath%>Press/querypressname.do')"  class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form> -->
				<input type="text" id="TITLE" name="TITLE" placeholder="标题名称" style="width:250px" class="input-text">
				<button name="search" id="search" class="btn btn-success" type="submit">
					<i class="Hui-iconfont">&#xe665;</i>搜索
				</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l"><a href="javascript:;" onclick="piliangdel('<%=basePath%>Media/delmedia.do')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>批量删除</a> <!-- <a class="btn btn-primary radius" data-title="推荐/不推荐" href="javascript:;" onclick="upMMEND('<%=basePath%>Media/upISMMEND.do')"><i class="Hui-iconfont">&#xe600;</i> 推荐/不推荐</a> -->
					<a class="btn btn-primary radius" data-title="置顶/不置顶" href="javascript:;" onclick="upTOP('<%=basePath%>Media/upISTOP.do')"><i class="Hui-iconfont">&#xe600;</i> 置顶/不置顶</a> 
					<a class="btn btn-primary radius" data-title="添加新闻" onclick="tiao('添加新闻','<%=basePath%>Media/querymediaid.do','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加新闻</a> </span>
					<a href="<%=basePath%>Media/exportfeedback.do" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe600;</i>导出Excel</a> 
			</div>
			<div class="mt-20">
				<form id="form-media">
					<table
						class="table table-border table-bordered table-bg table-hover table-sort">
						<thead>
							<tr class="text-c">
								<th class="center" onclick="selectAll()"><label><input
										type="checkbox" id="zcheckbox" /><span class="lbl"></span>
								</label></th>
								<th style="width: 400px;">标题</th>
								<th style="width: 250px;">显示发布人</th>
								<th style="width: 250px;">发布时间</th>
								<th style="width: 200px;">类型（视频、文字）</th>
								<th style="width: 250px;">栏目ID</th>
								<th style="width: 200px;">是否推荐</th>
								<th style="width: 200px;">是否置顶</th>
								<th style="width: 250px;">查看图片/视频</th>
								<th style="width: 200px;">类别</th>
								<th style="width: 250px;">状态</th>
								<th style="width: 250px;">修改时间</th>
								<th style="width: 300px;">操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</form>

			</div>
		</div>
	</div>
	<input id="csmsg" type="hidden">
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
				[11, "desc"],
				[3, "desc"]
			], //默认第几个排序
			"aoColumnDefs": [
				//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
				//{"orderable":false,"aTargets":[3]} 
			],
			"bStateSave": true, //状态保存
			"bProcessing": false, // 是否显示取数据时的那个等待提示
	              "bServerSide": true,//这个用来指明是通过服务端来取数据
	              "sAjaxSource": "<%=basePath%>Media/querymedia.do",//这个是请求的地址
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
	                     "mDataProp" : "TITLE", 
	                     "aTargets":[1],  
	                     "mData":"TITLE" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "UNAME", 
	                     "aTargets":[2],  
	                     "mData":"UNAME" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "PUBLISH_DATE", 
	                     "aTargets":[3],  
	                     "mData":"PUBLISH_DATE" 
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "SHOW_SP", 
	                     "aTargets":[4],  
	                     "mData":"SHOW_SP" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "TC_NAME", 
	                     "aTargets":[5],  
	                     "mData":"TC_NAME" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "SHOW_TJ", 
	                     "aTargets":[6],  
	                     "mData":"SHOW_TJ" 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "SHOW_ZD", 
	                     "aTargets":[7],  
	                     "mData":"SHOW_ZD" 
	                 }, 
	                 {  
	                     "sClass":"center",  
	                     "aTargets":[8],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
         	   			     return '<a class=\"btn btn-link\" onclick=\"tiao(\'浏览\',\'<%=basePath%>Media/look.do?ID='+c.ID+'\',\'\',\'300\')\" href=\"javascript:;\"  >查看图片/视频</a>';
         	   			 }  
	                 },
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "MORS", 
	                     "aTargets":[9],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
	                     	 if(c.MORS == '1'){
	                     		 return '新闻';
	                     	 }
	                     	 if(c.MORS == '2'){
	                     		 return '上海建联';
	                     	 } 
	                     } 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "STATUS", 
	                     "aTargets":[10],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
	                     	 if(c.STATUS == '01'){
	                     		 return '待上线';
	                     	 }
	                     	 if(c.STATUS == '02'){
	                     		 return '已上线';
	                     	 }
	                     	 if(c.STATUS == '03'){
	                     		 return '已下线';
	                     	 }
	                     } 
	                 }, 
	                 {  
	                     "sClass":"center", 
	                     "mDataProp" : "MODIFY_DATE", 
	                     "aTargets":[11],  
	                     "mData":"MODIFY_DATE" 
	                 }, 
	                 {
	                     "sClass":"center",  
	                     "aTargets":[12],  
	                     "mRender":function(a,b,c,d){//a表示statCleanRevampId对应的值，c表示当前记录行对象  
	                    	 var br="";
	                    	 if(c.STATUS == '01'){
	                    		 br+='<a data-title=\"上线\" onclick=\"editStatus(\''+c.ID+'\',\'02\',\''+c.TITLE+'\',\''+c.MORS+'\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6dc;</i>上线</a>&nbsp&nbsp';
	                     		 br+='<a data-title=\"编辑\" onclick=\"tiao(\'编辑\',\'<%=basePath%>Media/querymediaid.do?ID='+c.ID+'\',\'\',\'550\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i>编辑</a>&nbsp&nbsp<a data-title=\"删除\" href=\"javascript:;\" onclick=\"delbyid(\'<%=basePath%>Media/delmediaByid.do?ID='+c.ID+'\')\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i>删</a>&nbsp&nbsp'; 
	                     	 }
	                     	 if(c.STATUS == '02'){
	                     		 br+='<a data-title=\"下线\" onclick=\"editStatus(\''+c.ID+'\',\'03\',\''+c.TITLE+'\',\''+c.MORS+'\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6de;</i>下线</a>';
	                     		 br+='<a data-title=\"推送\" onclick=\"topush(\''+c.ID+'\',\'02\',\''+c.TITLE+'\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe666;</i>推送</a>';
	                     	}
	                     	 if(c.STATUS == '03'){
	                     		 br+='<a data-title=\"上线\" onclick=\"editStatus(\''+c.ID+'\',\'02\',\''+c.TITLE+'\',\''+c.MORS+'\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6dc;</i>上线</a>&nbsp&nbsp';
	                     		 br+='<a data-title=\"编辑\" onclick=\"tiao(\'编辑\',\'<%=basePath%>Media/querymediaid.do?ID='+c.ID+'\',\'\',\'550\')\" href=\"javascript:;\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i>编辑</a>&nbsp&nbsp<a data-title=\"删除\" href=\"javascript:;\" onclick=\"delbyid(\'<%=basePath%>Media/delmediaByid.do?ID='+c.ID+'\')\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i>删</a>'; 
	                     	 }
	                     	return br;
	                    }  
	                 }
			],  
			 "fnServerData" : function(sSource, aoData, fnCallback) {  
				//获取检索参数
			    var TITLE = $("#TITLE").val(); 
				 $.ajax({
		                url : sSource,//这个就是请求地址对应sAjaxSource
		                data : {"aoData":JSON.stringify(aoData),"TITLE":TITLE},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
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
         data:$('#form-media').serialize(), 
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
//layer_show(title,url,w,h);
var index=layer.open({
		type:2,
		title:title,
		content:url

});
	layer.full(index);

}
   /*$.ajax({  
         type: "POST",
         url:url,
         async: false,
         error: function(request) {
             alert("Connection error");
         },
         success: function(data) {
         alert("操作成功！"); 
            $(".show_iframe").html(data);  
         }
     });*/


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
         data:$('#form-media').serialize(), 
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

function delbyid(url){  
layer.confirm('确认要删除该数据吗？',function(index){ 
   $.ajax({  
         type: "POST",
         url:url,
         data:$('#form-media').serialize(), 
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

//推荐/不推荐
function upMMEND(url){
	   var checklist = document.getElementsByName ("ID");
	   var len=$("input[name='ID']:checked").length;
	   if(len==0){
	      alert("请选择一个需要推荐/不推荐的用户！");
	   }else if(len>1){
	      alert("只能选择一个需要推荐/不推荐的用户！");
	   }else{
	   var data=$("input[name='ID']:checked").val();
       $.ajax({  
	         type: "POST",
	         url:url,
	         data:$('#form-media').serialize(),  
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
    
    
    //置顶/不置顶
function upTOP(url){
	   var checklist = document.getElementsByName ("ID");
	   var len=$("input[name='ID']:checked").length;
	   if(len==0){
	      alert("请选择一个需要置顶/不置顶的用户！");
	   }else if(len>1){
	      alert("只能选择一个需要置顶/不置顶的用户！");
	   }else{
	   var data=$("input[name='ID']:checked").val();
       $.ajax({  
	         type: "POST",
	         url:url,
	         data:$('#form-media').serialize(),  
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
    
    
        //推送
	function topush(ID,status,title){
		layer.confirm('确认要推送该新闻吗？',function(index){ 
		   $.ajax({  
		         type: "POST",
		         url:'<%=basePath%>Media/topush.do',
		         data: {
						ID: ID,
						status: status,
						title:title
				 },
		         async: false,
		         success : function(result) {  
		        	    	parent.$('.breadcrumb .r .Hui-iconfont').click();
		        	    	layer.msg('新闻推送成功'); 
			          		window.location.reload();  
			          		
		           },
		           error : function(msg) {
		           layer.msg('新闻推送失败,请联系管理员');
		           }
		   });
		 });      
 		}
function editStatus(ID,status,title,MORS){
	if(status == '02'){
		layer.confirm('确认要该新闻上线吗？',function(index){ 
			   $.ajax({  
			         type: "POST",
			         url:'<%=basePath%>Media/updates.do',
			         data: {
							ID: ID,
							status: status,
							title:title,
							MORS:MORS
					 },
			         async: false,
			         success : function(result) { 
			        	    if(result.statusCode=='200'){
			        	    	parent.$('.breadcrumb .r .Hui-iconfont').click();
				          		window.location.reload();  
			        	    }else{
			        	    	layer.msg('新闻上线失败！');
			        	    }
			          		 
			           },
			           error : function(msg) {
			           }
			   });
		 });
	}else{
		layer.confirm('确认要该新闻下线吗？',function(index){ 
			   $.ajax({  
			         type: "POST",
			         url:'<%=basePath%>Media/updates.do',
						data : {
							ID : ID,
							status : status,
							title : title
						},
						async : false,
						success : function(result) {
							if (result.statusCode == '200') {
								parent.$('.breadcrumb .r .Hui-iconfont')
										.click();
								window.location.reload();
							} else {
								layer.msg('新闻下线失败！');
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