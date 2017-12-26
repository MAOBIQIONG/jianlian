var appKey = '47045ca57e2ca57f805cb24563e34160';
var account = null;
var wyToken = null;
var data = {};
var nim = null;
// 注意这里, 引入的 SDK 文件不一样的话, 你可能需要使用 SDK.NIM.getInstance 来调用接口
var initIM = function (user) {//登录
	if( user ){
		account = user.ID;
	    wyToken = user.WYTOKEN;
	}
	if( !isNull(account) && !isNull(wyToken) ){
		nim = NIM.getInstance({
		    //debug: true,
		    appKey: appKey,
		    account: account,
		    token: wyToken,
		    onconnect: onConnect,
		    onwillreconnect: onWillReconnect,
		    ondisconnect: onDisconnect,
		    onerror: onError,
		    //消息监听
		    onroamingmsgs: onRoamingMsgs,
		    onofflinemsgs: onOfflineMsgs,
		    onmsg: onMsg
		});
	}
}

function logoutIM() {//登出
	console.log('登出 SDK');
	if( nim != null ){
		nim.disconnect();
	}

}

function isNull(str) {
    if( str == null || str == "" || str == "null" || str == undefined ){
    	return true;
    }   return false;
}

function onConnect() {
    console.log('连接成功');
}
function onWillReconnect(obj) {
    // 此时说明 SDK 已经断开连接, 请开发者在界面上提示用户连接已断开, 而且正在重新建立连接
    console.log('即将重连');
    //console.log(obj.retryCount);
    //console.log(obj.duration);
}
function onDisconnect(error) {
    // 此时说明 SDK 处于断开状态, 开发者此时应该根据错误码提示相应的错误信息, 并且跳转到登录页面
    console.log('丢失连接');
    //console.log(error);
    if (error) {
        switch (error.code) {
        // 账号或者密码错误, 请跳转到登录页面并提示错误
        case 302:
            break;
        // 重复登录, 已经在其它端登录了, 请跳转到登录页面并提示错误
        case 417:
            break;
        // 被踢, 请提示错误后跳转到登录页面
        case 'kicked':
            break;
        default:
            break;
        }
    }
}
function onError(error) {
    console.log(error);
}

//消息监听
function onRoamingMsgs(obj) {
    console.log('收到漫游消息', JSON.stringify(obj));
    pushMsg(obj.msgs);
}
function onOfflineMsgs(obj) {
    console.log('收到离线消息', JSON.stringify(obj));
    pushMsg(obj.msgs);
}
function onMsg(msg) {
	//收到消息逻辑代码
	afterReceiveMsg(msg);
	console.log('收到消息', msg.scene, msg.type, JSON.stringify(msg));
    pushMsg(msg);
    switch (msg.type) {
    case 'custom':
        onCustomMsg(msg);
        break;
    case 'notification':
        // 处理群通知消息
        onTeamNotificationMsg(msg);
        break;
    default:
        break;
    }
}
function pushMsg(msgs) {
    if (!Array.isArray(msgs)) { msgs = [msgs]; }
    var sessionId = msgs[0].sessionId;
    data.msgs = data.msgs || {};
    data.msgs[sessionId] = nim.mergeMsgs(data.msgs[sessionId], msgs);
}
function onCustomMsg(msg) {
    // 处理自定义消息
}

//发送文字消息
function sendTextMsg (to,content,scene) {
	if( scene == null || scene == undefined || scene == ""  ){
		scene = 'p2p';
	}
	var msg = nim.sendText({
	    scene: scene,
	    to: to,
	    text: content,
	    done: sendMsgDone
	});
	//console.log('正在发送p2p text消息, id=' + msg.idClient);
	pushMsg(msg);
}
function sendMsgDone(error, msg) {
    //console.log(error);
    //console.log(msg);
    //console.log('发送' + msg.scene + ' ' + msg.type + '消息' + (!error?'成功':'失败') + ', id=' + msg.idClient);
    pushMsg(msg);
    if(!error){//发送成功
		//保存消息逻辑代码
	}else{//发送失败
		mui.toast("发送消息失败，请重试！");
	}
}
