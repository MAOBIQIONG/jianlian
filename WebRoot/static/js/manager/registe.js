function severCheck() {
	if (check()) {
		var loginname = $("#loginname").val();
		var password = $("#password").val();
		var msgCode = $("#msgCode").val();
		var code = loginname + "," + password + "," + msgCode + ","
				+ $("#code").val();
		$.ajax({
			type : "POST",
			url : getRootPath()+'/appuser/login/registe',
			data : {
				KEYDATA : code,
				tm : new Date().getTime()
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				console.log(data);
				if ("01" == data.result) {
					alert("注册成功!");
					window.location.href = getRootPath();
				} else  {
					alert(data.reason);
				} 
			}
		});
	}
}

$(document).ready(function() {
	changeCode();
	$("#codeImg").bind("click", changeCode);
	var loginname = $.cookie('loginname');
	var password = $.cookie('password');
	if (typeof (loginname) != "undefined" && typeof (password) != "undefined") {
		$("#loginname").val(loginname);
		$("#password").val(password);
		$("#saveid").attr("checked", true);
		$("#code").focus();
	}
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

// 客户端校验
function check() {
	if ($("#loginname").val() == "") {

		$("#loginname").tips({
			side : 2,
			msg : '请填写您的手机号码',
			bg : '#AE81FF',
			time : 3
		});

		$("#loginname").focus();
		return false;
	} else if (!mobileValid($("#loginname").val())) {
		$("#loginname").tips({
			side : 2,
			msg : '非法的手机号',
			bg : '#AE81FF',
			time : 3
		});
		$("#loginname").focus();
		return false;

	} else {
		$("#loginname").val(jQuery.trim($('#loginname').val()));
	}

	if ($("#password").val() == "") {

		$("#password").tips({
			side : 2,
			msg : '请填写您的密码',
			bg : '#AE81FF',
			time : 3
		});

		$("#password").focus();
		return false;
	}
	if ($("#code").val() == "") {

		$("#code").tips({
			side : 1,
			msg : '验证码不得为空',
			bg : '#AE81FF',
			time : 3
		});

		$("#code").focus();
		return false;
	}

	$("#loginbox").tips({
		side : 1,
		msg : '正在注册 , 请稍后 ...',
		bg : '#68B500',
		time : 10
	});

	return true;
}

function savePaw() {
	if (!$("#saveid").attr("checked")) {
		$.cookie('loginname', '', {
			expires : -1
		});
		$.cookie('password', '', {
			expires : -1
		});
		$("#loginname").val('');
		$("#password").val('');
	}
}

function saveCookie() {
	if ($("#saveid").attr("checked")) {
		$.cookie('loginname', $("#loginname").val(), {
			expires : 7
		});
		$.cookie('password', $("#password").val(), {
			expires : 7
		});
	}
}

var second = 60;

function validateMobile() {
	return mobileValid($.trim($("#loginname").val()));
}
var interval = null;
var noSend = false;
function sendMsg() {
	if (noSend)
		return;
	if (!validateMobile()) {
		$("#loginname").tips({
			side : 2,
			msg : '非法的手机号',
			bg : '#AE81FF',
			time : 3
		});
		$("#loginname").focus();
		return;
	}

	$.ajax({
		url:getRootPath()+"/appuser/login/validsms/send",
		dataType : "json",
		data : {
			MOBILE : $("#loginname").val(),
			VALID_EXIST : true
		},
		success : function(data) {
			if (data.result == "01") {
				$("#msgCode").focus();
				$("#btnSendMsg").attr("disabled", true);
				$("#btnSendMsg").html(second-- + "秒后重发");
				$("#msgCode").fadeIn(300);
				noSend = true;
				$("#loginname").css("width", 100);
				interval = setInterval(function() {
					$("#btnSendMsg").html(second-- + "秒后重发");
					if (second <= -1) {
						$("#btnSendMsg").text("发送短信").attr("disabled", false);
						noSend = false;
						second = 60;
						clearInterval(interval);
						interval = null;
						return false;
					}

				}, 1000);
			} else {
				alert(data.reason);
			}

		}
	})
}
// TOCMAT重启之后 点击左侧列表跳转登录首页
if (window != top) {
	top.location.href = location.href;
}
