$(document).ready(function() {
	var loginFormValid = $("#loginForm").validate({
		onkeyup: false,
		submitHandler: function(form) {
			$("#btn_login").attr("disabled",true);
			$("#loginForm").tips({
				side: 1,
				msg: '正在登录 , 请稍后 ...',
				bg: '#68B500',
				time: 5
			});
			var loginname = $("#loginForm #loginname").val();
			var password = $("#loginForm #password").val();
			var code = "qq313596790fh" + loginname + ",fh," + password + "QQ978336446fh" + ",fh," + $("#code").val();
			$.ajax({
				type: "POST",
				url: 'login_login',
				data: {
					KEYDATA: code,
					tm: new Date().getTime()
				},
				dataType: 'json',
				cache: false,
				timeout:5000,
				error:function(){
					$("#loginForm").tips({
						side: 1,
						msg: '服务异常,请稍后尝试 ...',
						bg: '#68B500',
						time: 3
					});
					$("#btn_login").attr("disabled",false);
				},
				success: function(data) {
					if ("success" == data.result) {
						saveCookie();
						window.location.href = "main/index";
					} else if ("usererror" == data.result) {
						$("#loginForm #loginname").tips({
							side: 1,
							msg: "用户名或密码有误",
							bg: '#FF5080',
							time: 3
						});
						$("#loginForm #loginname").focus();
					} else if ("codeerror" == data.result) {
						$("#code").tips({
							side: 1,
							msg: "验证码输入有误",
							bg: '#FF5080',
							time: 3
						});
						$("#code").focus();
					} else {
						$("#loginForm #loginname").tips({
							side: 1,
							msg: "缺少参数",
							bg: '#FF5080',
							time: 3
						});
						$("#loginForm #loginname").focus();
					}
					$("#btn_login").attr("disabled",false);
				}
			});
		}
	});
	
	var resetFormValid = $("#resetForm").validate({
		onkeyup: false,
		submitHandler: function(form) {
			$("#btn-reset").attr("disabled", true);
			var loginname = $("#resetForm #loginname").val();
			var password = $("#resetForm #password").val();
			var msgCode = $("#resetForm #msgCode").val();
			var code = loginname + "," + password + "," + msgCode + "," + $("#code").val();
			$.ajax({
				type: "POST",
				url: 'appuser/password/reset',
				data: {
					MOBILE:loginname,
					PASSWORD:password,
					VALID_CODE:msgCode
				},
				dataType: 'json',
				cache: false,
				success: function(data) {
					console.log(data);
					if(data.result=="01"){
						//mui.currentWebview.close();
						$("#resetForm ").tips({
							side: 1,
							msg: "密码重置成功",
							bg: '#FF5080',
							time: 3
						});
						setTimeout(function() {
							show_box('login-box');
						}, 2000);
					}else{
						if("验证码错误" == data.reason){
							$("#resetForm #msgCode").tips({
								side: 1,
								msg: data.reason,
								bg: '#FF5080',
								time: 3
							});
						}
						
					}
				}
			});
		}
	});


	var registeFormValid = $("#registeForm").validate({
		onkeyup: false,
		submitHandler: function(form) {
			$("#btn-registe").attr("disabled", true);
			$.ajax({
				type: "POST",
				url: 'appuser/login/registe',
				data: $("#registeForm").serialize(),
				dataType: 'json',
				cache: false,
				success: function(data) {
					console.log(data);
					if ("01" == data.result) {
						$("#registeForm").tips({
							side: 1,
							msg: "恭喜您注册成功,请登录!",
							bg: 'green',
							time: 3
						});
						setTimeout(function() {
							$("#loginForm #loginname").val($("#registeForm #loginname").val())
							show_box('login-box');
						}, 2000);
					} else {
						$("#registeForm").tips({
							side: 1,
							msg: data.reason,
							bg: '#FF5080',
							time: 3
						});
						$("#btn-registe").attr("disabled", false);
					}
				}
			});
		}
	});
});

$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		$("#to-recover").trigger("click");
	}
});

function genTimestamp() {
	var time = new Date();
	return time.getTime();
}

function changeCode() {
	$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
}
var second = 60;
function sendMsg(){
	$.ajax({
		url:"appuser/login/validsms/send",
		data:{MOBILE:$("#resetForm #loginname").val(),VALID_EXIST:"1"},
		dataTye:"json",
		type:"post",
		success:function(data){
			
			  if(data.result == "01"){
				var v = setInterval(function(){
					second --;
					if(second  == 0){
						clearInterval(v);
						$("#resetForm #btn_send").attr("disabled",false).html("<i class=\"icon-lightbulb\"></i> 发送验证码");
						second = 60;
					}else{
						$("#resetForm #btn_send").attr("disabled",false).html(second+"秒后发送");
					}
				}, 1000);
			}else{
				$("#resetForm").tips({
					side: 1,
					msg: data.reason,
					bg: '#FF5080',
					time: 3
				});
			}
		}
	})
}


function savePaw() {
	if (!$("#saveid").attr("checked")) {
		$.cookie('loginname', '', {
			expires: -1
		});
		$.cookie('password', '', {
			expires: -1
		});
		$("#loginname").val('');
		$("#password").val('');
	}
}

function saveCookie() {
	if ($("#saveid").attr("checked")) {
		$.cookie('loginname', $("#loginname").val(), {
			expires: 7
		});
		$.cookie('password', $("#password").val(), {
			expires: 7
		});
	}
}

jQuery(function() {
	var loginname = $.cookie('loginname');
	var password = $.cookie('password');
	if (typeof(loginname) != "undefined" && typeof(password) != "undefined") {
		$("#loginForm #loginname").val(loginname);
		$("#loginForm #password").val(password);
		$("#loginForm #saveid").attr("checked", true);
		$("#loginForm #code").focus();
	}
});

// TOCMAT重启之后 点击左侧列表跳转登录首页
if (window != top) {
	top.location.href = location.href;
}