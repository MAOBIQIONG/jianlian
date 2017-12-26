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
		<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]--> 
<!-- <style>
.col-xs-2,.col-xs-1{
   padding:0px;
}
</style> -->
	</head>

	<body>
		<article class="page-container">
			<form class="form form-horizontal" id="form-add"> 
			    <div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>商品ID：</label>
				    <div class="formControls col-xs-8 col-sm-9">
						<input type="text" value="${pd.ID}" class="input-text" name="PROD_ID" id="PROD_ID" readOnly>
					</div> 
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>商品编号：</label>
				    <div class="formControls col-xs-8 col-sm-9">
						<input type="text" readonly="readonly" value="${pd.prod_no }" class="input-text" name="prod_no1" id="prod_no1">
					</div> 
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>商品名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" value="${pd.prod_name }" class="input-text" placeholder="" id="prod_name" name="prod_name">
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>商品头像：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						 <input type="file" id="prod_head" name="prod_head" onchange="check(prod_head)" style="width:150px;" />
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>邮费：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<input type="text" class="input-text" value="${pd.express_price }" id="express_price" name="express_price" />
					</div>
				</div> 
				<!-- <div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>商品滚动图：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<input type="file" id="images" name="images" onchange="check(images)" multiple style="width:150px;" />
						
					</div>
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>商品介绍图：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<input  type="file" id="descImgs" name="descImgs" onchange="check(images)" multiple style="width:150px;" />
						
					</div>
				</div>    -->
				
				<c:forEach items="${pd.propList }" var="prp" varStatus="pr">
					<c:if test="${pr.index == '0' }">
						<div class="row cl" id="sx_${pr.index+1 }">
							<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>产品属性：</label>
							<div class="formControls col-xs-8 col-sm-9"> 
								属性名：<input type="text" value="${prp.PROP_NAME }" class="input-text" title="例：属性名称：产地，属性值为：中国上海" style="width: 300px;" name="prod_sxn" id="prod_sxn" placeholder="属性名"/>
								属性值：<input type="text" value="${prp.PROP_VALUE }" class="input-text" name='prod_sxz' id='prod_sxz' style="width: 300px;" title='注：该值中不能含英文逗号' placeholder='属性值' />
							</div>
						</div> 
					</c:if>
					<c:if test="${pr.index != '0' }">
						<div class="row cl" id="sx_${pr.index+1 }">
							<label class="form-label col-xs-4 col-sm-2"></label>
							<div class="formControls col-xs-8 col-sm-9"> 
								属性名：<input type="text" value="${prp.PROP_NAME }" class="input-text" title="例：属性名称：产地，属性值为：中国上海" style="width: 300px;" name="prod_sxn" id="prod_sxn" placeholder="属性名"/>
								属性值：<input type="text" value="${prp.PROP_VALUE }" class="input-text" name='prod_sxz' id='prod_sxz' style="width: 300px;" title='注：该值中不能含英文逗号' placeholder='属性值' />
							   <button type="button" class="btn btn-primary radius size-MINI" onclick="delCpSX('${pr.index+1}')">-</button>
							</div>
						</div> 
					</c:if>
				</c:forEach>
				<div class="row cl" id="cpsx" style="margin-top: 20px;">
					<label class="form-label col-xs-4 col-sm-2"></label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<button class="btn btn-primary radius size-MINI" type="button" onclick="addCpSX()">+点击新增产品属性信息</button>
					</div>
				</div>  
				<c:forEach items="${pd.specList }" var="spec" varStatus="sp">
					<c:if test="${pr.index == '0' }">
						<div class="row cl" id="gg_${pr.index+1 }">
							<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>产品规格：</label>
							<div class="formControls col-xs-8 col-sm-9"> 
								规格名：<input type="text" readonly="readonly" value="${spec.PROD_SPEC_NAME }" class="input-text" title="例：规格名称：颜色，规格值为：白色#黑色" style="width: 300px;" name="prod_ggn" id="prod_ggn" placeholder="规格名"/>
								规格值：<input type="text" readonly="readonly" value="${spec.PROD_SPEC_VALUE }" class="input-text" name='prod_ggz' id='prod_ggz' style="width: 300px;" title="注：多个值以#号隔开；例：黑色#白色" onchange="resSku()" placeholder="规格值1#规格值2#规格值3..." />
							</div>
						</div> 
					</c:if>
					<c:if test="${pr.index != '0' }">
						<div class="row cl" id="gg_${pr.index+1 }">
							<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>产品规格：</label>
							<div class="formControls col-xs-8 col-sm-9"> 
								规格名：<input type="text" value="${spec.PROD_SPEC_NAME }" class="input-text" title="例：规格名称：颜色，规格值为：白色#黑色" style="width: 300px;" name="prod_ggn" id="prod_ggn" placeholder="规格名"/>
								规格值：<input type="text" value="${spec.PROD_SPEC_VALUE }" class="input-text" name='prod_ggz' id='prod_ggz' style="width: 300px;" title="注：多个值以#号隔开；例：黑色#白色" onchange="resSku()" placeholder="规格值1#规格值2#规格值3..." />
								<button type="button" class="btn btn-primary radius size-MINI" onclick="delCpGG('${pr.index+1}')">-</button>
							</div>
						</div> 
					</c:if>
				</c:forEach>
				<div class="row cl" id="cpgg" style="margin-top: 20px;">
					<label class="form-label col-xs-4 col-sm-2"></label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<button class="btn btn-primary radius size-MINI" type="button" onclick="addCpGG()">+点击新增产品规格信息</button>
					</div>
				</div>  
				
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>SKU信息：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					</div>
				</div> 
				<div class="row cl" style="margin-top: -20px;">
					<label class="form-label col-xs-4 col-sm-2"></label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<table>
							<tbody id="skuinfo">
						   	<tr id="s">
						   	   <td style="border: 1px solid #ddd;">序号</td>
						       <td style="border: 1px solid #ddd;">sku识别码</td>
						       <td style="border: 1px solid #ddd;">商品数量(件)</td>
						       <td style="border: 1px solid #ddd;">商品价格(元)</td>
						       <td style="border: 1px solid #ddd;">优惠价格(元)</td>
						       <td style="border: 1px solid #ddd;">市场价格(元)</td>
						    </tr>
						    <c:forEach items="${pd.skuList }" var="sku" varStatus="sk">
							    <tr>
									<td style="border: 1px solid #ddd;">${sk.index+1 }</td>
									<td style="border: 1px solid #ddd;"><input style="height: 25px;" readonly="readonly" type="text" name="sku_n" id="sku_n" title="${sku.SKU_INFO }" value="${sku.SKU_INFO }"></td>
									<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" value="${sku.STOCK_N }" name="sku_num" id="sku_num" ></td>
									<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" value="${sku.PRICE }" name="sku_price" id="sku_price"></td>
									<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" value="${sku.SALES_PRICE }" name="yh_price" id="yh_price"></td>
									<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" value="${sku.MARKET_PRICE }" name="sc_price" id="sc_price"></td>
								</tr>
						    </c:forEach>
						   </tbody>
						</table>
					</div>
				</div> 
				<div class="row cl" id="cpgg" style="margin-top: 20px;">
					<label class="form-label col-xs-4 col-sm-2"></label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<button class="btn btn-primary radius size-MINI" type="button" onclick="addSku()">生成产品sku信息</button>
					</div>
				</div> 
				
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<input class="btn btn-success radius" type="submit" value="&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;"> 
					</div>
				</div>
			</form>
		</article>

		<!--_footer 作为公共模版分离出去-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/icheck/jquery.icheck.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
		<!--/_footer /作为公共模版分离出去-->

		<!--请在下方写此页面业务相关的脚本-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script>  
		<script type="text/javascript"> 
		
		//生成产品sku信息
		function addSku(){
			var prod_no = $("#PROD_ID").val();
			if(prod_no == null || prod_no == ''){
				alert("请输入产品ID！");
				return;
			}
			if($("#prod_ggz").size() > 0){
				var f=true;
				var msg="";
				$("input[name='prod_ggz']").each(function(index,element){
					var gg = $(element).val();
					if(gg == null || gg == ''){
						f=false;
						msg="请输入产品规格值";
					}
				});
				$("input[name='prod_ggn']").each(function(index,element){
					var gg = $(element).val();
					if(gg == null || gg == ''){
						f=false;
						msg="请输入产品规格名称";
					}
				});
				if(f){
					var arr = new Array();
					$("input[name='prod_ggz']").each(function(index,element){
						var arr1 = new Array();
						var gg = $(element).val();
						arr[index] = gg.split("#");
					});
					var b = new Array();
					var bb = new Array();
					for(var n=0;n<arr.length;n++){
						if(b.length>0){
							var cd=0;
							for(var x=0;x<bb.length;x++){
								var zhi = bb[x]; 
								for(var m=0;m<arr[n].length;m++){
									b[cd] = zhi+"#"+arr[n][m];
									cd++;
								} 
							} 
							
							for(var x=0;x<b.length;x++){
								bb[x] = b[x];
							}
						}else{
							for(var m=0;m<arr[0].length;m++){
								b[m] = prod_no+"#"+arr[n][m];
								bb[m] = prod_no+"#"+arr[n][m];
							}
						}
					}
					//生成sku信息
					var tr='<tr id="s">';
				    tr+='<td>序号</td>';
				    tr+='<td>sku识别码</td>';
				    	tr+='<td>商品数量(件)</td>';
				    		tr+='<td>商品价格(元)</td>';
				    		tr+='<td>优惠价格(元)</td>';
				    		tr+='<td>市场价格(元)</td>';
				    				tr+='</tr>';
					for(var h=0;h<b.length;h++){
						var dn = h+1;
						tr+='<tr>';
							tr+='<td style="border: 1px solid #ddd;">'+dn+'</td>';
								tr+='<td style="border: 1px solid #ddd;"><input style="height: 25px;" readonly="readonly" type="text" name="sku_n" id="sku_n" title="'+b[h]+'" value="'+b[h]+'"></td>';
								tr+='<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" name="sku_num" id="sku_num" ></td>';
								tr+='<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" onchange="changeYh_price(this)" name="sku_price" id="sku_price"></td>';
								tr+='<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" name="yh_price" id="yh_price"></td>';
								tr+='<td style="border: 1px solid #ddd;"><input style="height: 25px;" type="text" name="sc_price" id="sc_price"></td>';
								tr+='</tr>';
					}
					$("#skuinfo").empty();
					$("#skuinfo").append(tr);
			  	
				}else{
					alert(""+msg);
				}
				
			}else{
				alert("请添加产品规格！");
			}
			
		}
		
		//输入商品价格是同步更新优惠价格
		function changeYh_price(obj){
			var index = $("input").index(obj);
			$("input").eq(Number(index+1)).val($(obj).val());
		}
		
		var i=1;
		var j=1;
		//新增产品属性
		function addCpSX(){  
			i++;
			var label="<div class='row cl' id='sx_"+i+"'>";
					label+="<label class='form-label col-xs-4 col-sm-2'></label>";
					label+="<div class='formControls col-xs-8 col-sm-9'> ";
					label+="属性名：<input type='text' class='input-text' title='例：属性名称：产地，那属性值为：中国上海' style='width: 300px;' name='prod_sxn' id='prod_sxn' placeholder='属性名'/> ";
					label+="属性值：<input type='text' class='input-text' name='prod_sxz' id='prod_sxz' style='width: 300px;' title='注：该值中不能含英文逗号' placeholder='属性值' />";
						label+="  <button type='button' class='btn btn-primary radius size-MINI' onclick='delCpSX("+i+")'>-</button>";
					label+="</div>";
				label+="</div>";
			$("#cpsx").before(label);
		}
		
		function delCpSX(ind){
			$("#sx_"+ind).remove();
		}
		
		//新增产品规格
		function addCpGG(){    
			j++;
			var label="<div class='row cl' id='gg_"+j+"'>";
			label+="<label class='form-label col-xs-4 col-sm-2'></label>";
				label+="<div class='formControls col-xs-8 col-sm-9'> ";
					label+="规格名：<input type='text' class='input-text' title='例：规格名称：颜色，规格值为：白色#黑色' style='width: 300px;' name='prod_ggn' id='prod_ggn' placeholder='规格名'/> ";
						label+="规格值：<input type='text' class='input-text' name='prod_ggz' id='prod_ggz' style='width: 300px;' title='注：多个值以#号隔开；例：黑色#白色' onchange='resSku()' placeholder='规格值1#规格值2#规格值3...' />";
						label+="  <button type='button' class='btn btn-primary radius size-MINI' onclick='delCpGG("+j+")'>-</button>";	
						label+="</div>";
								label+="</div>";
			$("#cpgg").before(label);
			resSku();
			
		}
		//重置sku信息
		function resSku(){
			$("#skuinfo").empty();
			var tr="";
			tr+="<tr id='s'>";
			tr+="<td style='border: 1px solid #ddd;'>序号</td>";
			tr+="<td style='border: 1px solid #ddd;'>sku识别码</td>";
			tr+="<td style='border: 1px solid #ddd;'>商品数量(件)</td>";
			tr+="<td style='border: 1px solid #ddd;'>商品价格(元)</td>";
			tr+="<td style='border: 1px solid #ddd;'>优惠价格(元)</td>";
			tr+="tr+='<td style='border: 1px solid #ddd;'>市场价格(元)</td>";
			tr+="</tr>";
				tr+="<tr>";
					tr+="<td  style='border: 1px solid #ddd;text-align: center;height: 25px;color: red' colspan='6'>";
					tr+="<b>请添加产品规格生成Sku信息</b>";
						tr+="</td>";
							tr+="</tr>";
			$("#skuinfo").append(tr);
		}
		
		function delCpGG(ind){
			$("#gg_"+ind).remove();
			//重置sku信息
			$("#skuinfo").empty();
		}
		
		//验证商品编号是否存在
		function findProdno(obj){
			var prod_no = $(obj).val().trim();
			if(prod_no == null || prod_no == '' ||prod_no == undefined){
				alert("商品编号不能为空!");
				return;
			}else{
				$.ajax({
			        url : "<%=basePath%>product/findByprodno.do",
			        data : {prod_no:prod_no},
			        type : 'post',
			        dataType : 'json',
			        async : false,
			        success : function(result) {
			          	if(result.pd != null && result.pd !=''){
			          		alert("商品编号已存在!！");
			          		$(obj).val('');
			          	}
			        }
			    });
			}
		}
		
		$(function(){
			var validate = $("#form-add").validate({ 
			   submitHandler: function(form){ 
				   save(); //执行提交方法
		        },
		     });    
		 });
		
		 function check(id){ 
			var aa=document.getElementById(id).value.toLowerCase().split('.');//以“.”分隔上传文件字符串
		    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById(id).files[0].size;
				if(imagSize<1024*1024){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
					document.getElementById(id).value=null;
					return false;
				} 
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById(id).value=null;
		        return false;
		    } 
		 }
		
		 function save(){  
			var index = parent.layer.getFrameIndex(window.name);
			var prod_no=$("#prod_no1").val();
			if(prod_no == null || prod_no == "" || prod_no == undefined){
				alert("请输入产品编号！");
				return ;
			}
			var prod_name=$("#prod_name").val();
			if(prod_name == null || prod_name == "" || prod_name == undefined){
				alert("请输入产品名称！");
				return ;
			}
			var express_price=$("#express_price").val();
			var prod_head=document.getElementById("prod_head").value;
			if(prod_head == null || prod_head == "" || prod_head == undefined){
				alert("请输入产品头像！");
				return ;
			}
			
			var sxn="",sxz="",ggn="",ggz="";
			var prod_sxn = document.getElementsByName("prod_sxn");
			for(i=0;i<prod_sxn.length;i++){
				if($(prod_sxn[i]).val() == null || $(prod_sxn[i]).val() == '' || $(prod_sxn[i]).val() == undefined){
					alert("属性名不能为空");
					return;
				}else{
					if($(prod_sxn[i]).val().toString().indexOf(",") >= 0 || $(prod_sxn[i]).val().toString().indexOf("，") >= 0){
						alert("属性名不允许在产品属性中输入\",\"号!");
						return;
					}
					sxn +=","+$(prod_sxn[i]).val();
					
				}
			}
			
			var prod_sxz = document.getElementsByName("prod_sxz");
			for(i=0;i<prod_sxz.length;i++){
				if($(prod_sxz[i]).val() == null || $(prod_sxz[i]).val() == '' || $(prod_sxz[i]).val() == undefined){
					alert("属性值不能为空");
					return;
				}else{
					if($(prod_sxz[i]).val().toString().indexOf(",") >= 0 || $(prod_sxz[i]).val().toString().indexOf("，") >= 0){
						alert("属性值不允许在产品属性中输入\",\"号!");
						return;
					}
					sxz +=","+$(prod_sxz[i]).val();
				}
			}
			
			var prod_ggn = document.getElementsByName("prod_ggn");
			for(i=0;i<prod_ggn.length;i++){
				if($(prod_ggn[i]).val() == null || $(prod_ggn[i]).val() == '' || $(prod_ggn[i]).val() == undefined){
					alert("规格名不能为空");
					return;
				}else{
					if($(prod_ggn[i]).val().toString().indexOf(",") >= 0 || $(prod_ggn[i]).val().toString().indexOf("，") >= 0){
						$("#mesg").alertmsg('info', "不允许在产品规格中输入\",\"号!");
						alert("规格名不允许在产品规格中输入\",\"号!");
						return;
					}
					ggn +=","+$(prod_ggn[i]).val();
				}
			}
			
			var prod_ggz = document.getElementsByName("prod_ggz");
			for(i=0;i<prod_ggz.length;i++){
				if($(prod_ggz[i]).val() == null || $(prod_ggz[i]).val() == '' || $(prod_ggz[i]).val() == undefined){
					alert("规格值不能为空");
					return;
				}else{
					if($(prod_ggz[i]).val().toString().indexOf(",") >= 0 || $(prod_ggz[i]).val().toString().indexOf("，") >= 0){
						$("#mesg").alertmsg('info', "不允许在产品规格中输入\",\"号!");
						alert("规格值不允许在产品规格中输入\",\"号!");
						return;
					}
					ggz +=","+$(prod_ggz[i]).val();
				}
			}
			if(prod_sxn.length == 0){
				alert("产品属性不能为空!");
				return;
			}
			if(prod_ggn.length == 0){
				alert("产品规格信息不能为空!");
				return;
			}
			var sku_n="",sku_num="",sku_price="",yh_price="",sc_price="";
			var skun = document.getElementsByName("sku_n");
			var skunum = document.getElementsByName("sku_num");
			var skuprice = document.getElementsByName("sku_price");
			var yuprice = document.getElementsByName("yh_price");
			var scprice = document.getElementsByName("sc_price");
			if(skun.length == 0){
				alert("产品SKU信息不能为空!");
				return;
			}
			
			for(i=0;i<skun.length;i++){
				if($(skun[i]).val() == null || $(skun[i]).val() == '' || $(skun[i]).val() == undefined){
					alert("产品SKU信息不能为空");
					return;
				}else{
					if($(skun[i]).val().toString().indexOf(",") >= 0 || $(skun[i]).val().toString().indexOf("，") >= 0){
						$("#mesg").alertmsg('info', "不允许在产品SKU信息中输入\",\"号!");
						alert("不允许在产品SKU信息中输入\",\"号!");
						return;
					}
					sku_n +=","+$(skun[i]).val();
				}
				
				if($(skunum[i]).val() == null || $(skunum[i]).val() == '' || $(skunum[i]).val() == undefined){
					alert("商品件数不能为空");
					return;
				}else{
					if($(skunum[i]).val().toString().indexOf(",") >= 0 || $(skunum[i]).val().toString().indexOf("，") >= 0){
						$("#mesg").alertmsg('info', "不允许在商品件数中输入\",\"号!");
						alert("不允许在商品件数中输入\",\"号!");
						return;
					}
					sku_num +=","+$(skunum[i]).val();
				}
				
				if($(skuprice[i]).val() == null || $(skuprice[i]).val() == '' || $(skuprice[i]).val() == undefined){
					alert("商品价格不能为空");
					return;
				}else{
					if($(skuprice[i]).val().toString().indexOf(",") >= 0 || $(skuprice[i]).val().toString().indexOf("，") >= 0){
						$("#mesg").alertmsg('info', "不允许在商品价格中输入\",\"号!");
						alert("不允许在商品价格中输入\",\"号!");
						return;
					}
					sku_price +=","+$(skuprice[i]).val();
				}
				
				if($(yuprice[i]).val() == null || $(yuprice[i]).val() == '' || $(yuprice[i]).val() == undefined){
					alert("优惠价格不能为空");
					return;
				}else{
					if($(yuprice[i]).val().toString().indexOf(",") >= 0 || $(yuprice[i]).val().toString().indexOf("，") >= 0){
						$("#mesg").alertmsg('info', "不允许在优惠价格中输入\",\"号!");
						alert("不允许在优惠价格中输入\",\"号!");
						return;
					}
					yh_price +=","+$(yuprice[i]).val();
				}
				
				if($(scprice[i]).val() == null || $(scprice[i]).val() == '' || $(scprice[i]).val() == undefined){
					alert("市场价格不能为空");
					return;
				}else{
					if($(scprice[i]).val().toString().indexOf(",") >= 0 || $(scprice[i]).val().toString().indexOf("，") >= 0){
						$("#mesg").alertmsg('info', "不允许在市场价格中输入\",\"号!");
						alert("不允许在市场价格中输入\",\"号!");
						return;
					}
					sc_price +=","+$(scprice[i]).val();
				}
			}
			/* for(i=0;i<skun.length;i++){
				sku_n +=","+$(skun[i]).val();
				sku_num +=","+$(skunum[i]).val();
				sku_price +=","+$(skuprice[i]).val();
				yh_price +=","+$(yuprice[i]).val();
				sc_price +=","+$(scprice[i]).val();
			} */
			if($("#sku_n").length == 0){
				alert("产品sku信息不能为空!");
				return;
			}
			var map = {
				"prod_no":prod_no,
				"prod_name":prod_name,
				"express_price":express_price,
				"sxn":sxn,
				"sxz":sxz,
				"ggn":ggn,
				"ggz":ggz,
				"sku_n":sku_n,
				"sku_num":sku_num,
				"sku_price":sku_price,
				"yh_price":yh_price,
				"sc_price":sc_price,
				"id":$("#PROD_ID").val(),
				"PROD_ID":$("#PROD_ID").val()
			};  
			var url="<%=basePath%>product/save.do"; 
			$.ajaxFileUpload({  
				  url :url,
				  type:'post',
				  data:map,
			      secureuri:false,  
			      fileElementId:['prod_head','prod_head'],//file标签的id  
			      dataType: 'json',//返回数据的类型    
			      success: function (data, status) { 
			          if(data.statusCode == 200){ 
						  parent.$('.breadcrumb .r .Hui-iconfont').click();
						  parent.layer.close(index);
			          }else{
			        	  alert("操作失败！");
			          } 
			      },  
			      error: function (data, status, e) { 
			         alert(e);  
			      }  
			 }); 
		}    
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>