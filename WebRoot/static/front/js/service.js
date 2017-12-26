var service_url = "http://192.168.9.100:8080/yzy_web/";
var img_url = "http://192.168.9.100:8080/image/";
var login_url = service_url + "appuser/login.do";
alert(service_url)
var token = "e30378951d8541e48011ad5480c53b71";
function myAjax(options) {
	var default_options = {
		dataType: 'json', //服务器返回json格式数据
		type: 'post', //HTTP请求类型
		timeout: 10000, //超时时间设置为10秒；
		error: function(xhr, type, errorThrown) {
			//console.log(type);
		}
	};

	options = $.extend(options, default_options);
	mui.ajax(options.url, options);
}

function login(callback) {
	if (!localStorage.token) {

		myAjax({
			url: login_url,
			data: {
				USERNAME: "15000814629",
				PASSWORD: "814629"
			},
			success: function(data) {
				token = data.data.apptoken;
				localStorage.token = token;
				localStorage.clazz_id = data.data.classinfo[0].CLAZZ_ID;
				localStorage.BABY_ID = data.data.classinfo[0].BABY_ID;
				localStorage.clazz_name = data.data.classinfo[0].CLAZZ_NAME;
				localStorage.user_id = data.data.user_id;
				localStorage.school_id = data.data.classinfo[0].SCHOOL_ID;
				localStorage.school_name = data.data.classinfo[0].SCHOOL_NAME;
				if (callback) {
					callback();
				}
			}
		})
	} else {
		token = localStorage.token;
		callback();
	}
}
var flag = false;
function animate(src, active, delay,callback) {
	if(flag){
		return;
	}
	flag = true;
	$(src).addClass("animated " + active);
	setTimeout(function() {
		$(src).removeClass("animated " + active);
		flag = false;
		if(callback && typeof callback == "function")
			callback();
	}, delay||1000);
}