function checkSave(clazz_id){
			 if(clazz_id == null || clazz_id == ""){
				$("#baseInfo").tips({
					side:3,
		            msg:'请先保存班级基本信息',
		            bg:'#AE81FF',
		            time:2
		        });
				 return false;
			 }else
				 return true;
		}
		
//新增
function addBaby() {
	var CLAZZ_ID = $("#CLAZZ_ID").val();
	if (!checkSave(CLAZZ_ID))
		return;
	top.jzts();
	alert(1)
	var diag = new top.Dialog();
	diag.Drag = true;
	diag.Title = "新增";
	diag.URL = basepath + 'baby/goAdd.do?CLAZZ_ID=' + CLAZZ_ID;
	diag.Width = 860;
	diag.Height = 660;
	diag.CancelEvent = function() { // 关闭事件
		try {
			loadBabys(CLAZZ_ID);
			parent.loadClazzInfo($("#CLAZZ_ID").val());
		} catch (e) {
		}

		diag.close();
	};
	diag.show();
}

// 删除
function delBaby(Id) {
	bootbox.confirm("确定要删除吗?", function(result) {
		if (result) {
			// top.jzts();
			var url = bathpath + "baby/delete.do?BABY_ID=" + Id + "&tm="
					+ new Date().getTime();
			$.get(url, function(data) {
				loadBabys($("#CLAZZ_ID").val());
				parent.loadClazzInfo($("#CLAZZ_ID").val());
			});
		}
	});
}

// 修改
function editBaby(Id) {
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag = true;
	diag.Title = "编辑";
	diag.URL = bathpath + 'baby/goEdit.do?BABY_ID=' + Id;
	diag.Width = 860;
	diag.Height = 660;
	diag.CancelEvent = function() { // 关闭事件
		// nextPage(${page.currentPage});
		try {

			loadBabys($("#CLAZZ_ID").val());
			parent.loadClazzTree();
		} catch (e) {
		}
		diag.close();
	};
	diag.show();
}
function loadBabys(CLAZZ_ID){
	if(CLAZZ_ID!=null && CLAZZ_ID !=""){
		$.ajax({url:basepath+"baby/loadBaby",
			data:{CLAZZ_ID:CLAZZ_ID},
			success:function(re){
				//console.log(re);
				var html = "";
				for(var i in re){
					var pic = "static/images/no_head.jpg";
					if(re[i].HEADPIC != null && re[i].HEADPIC != "" ){
						pic = "<%=Const.FILEPATHIMG%>"+re[i].HEADPIC;
					}
					var add = "<a href='javascript:editBaby(\""+re[i].BABY_ID+"\")'><i class='icon-pencil'></i></a>";
					var valid = re[i].VALID == '1'?"<span class='tip yrz'>已认证</span>":"<span class='tip wrz'>未认证</span>";
					var remove = re[i].VALID == '1'?"":"<a href='javascript:delBaby(\""+re[i].BABY_ID+"\")'><i class='icon-remove red'></i></a>";
					html +="<li>"
					+"<img src='"+pic+"' width='80px'/>"
					/*+"<div class='tools'>"
					+add
					+remove +"</div>"*/
						+"<div class='text-center "+(re[i].SEX == "1"?"alert-success":"alert-danger")+"'>"+re[i].NAME+valid+"</div>"
						+"</li>";
				}
				if(re.length == 0){
					html+="<span class='pink'> <h4>本班级还没有学生哦,快去添加吧!</h4></span>";
				}
				$("#baby_"+CLAZZ_ID).empty().html(html);
				
		}});
	}
}
//打开导入学生信息
function importBaby(){
	 var CLAZZ_ID = $("#CLAZZ_ID").val();
	 if(!checkSave(CLAZZ_ID))
		 return;
	 top.jzts(); top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="导入学生信息";
	 diag.URL = bathpath+'baby/goImport.do?CLAZZ_ID='+CLAZZ_ID;
	 diag.Width =  540;
	 diag.Height =  250;
	 diag.CancelEvent = function(){ //关闭事件
		 loadBabys(CLAZZ_ID);
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 loadBabys($("#CLAZZ_ID").val());
			 parent.loadClazzTree();
		}
		diag.close();
	 };
	 diag.show();
}

$(function() {
	//loadTeachers('${pd.CLAZZ_ID}');
	loadBabys('${pd.CLAZZ_ID}');
	//loadSelects("TEACHER_ID")
	$("#babys").parent("div").css("height", $(window).height() * 0.75);
})