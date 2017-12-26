package com.fh.util.wangyi;

import net.sf.json.JSONArray;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fh.util.PageData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class WyUtil {
	public static String appKey = "47045ca57e2ca57f805cb24563e34160";
	public static String appSecret = "818942ff3fac";
	
	/**
	 * 注册网易云信账户
	 * id:用户ID
	 * name:用户REAL_NAME
	 * path:用户IMG
	 * */
    public static String createWyAccount(String id,String name,String path) throws Exception{
        String url = "https://api.netease.im/nimserver/user/create.action";
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", id));
        nvps.add(new BasicNameValuePair("name", name));
        nvps.add(new BasicNameValuePair("icon", path));
        String result = POST(url,nvps);
        System.out.println("创建网易账号:"+result);
        return result;
    }
    
    /**
     * 更新并获取新token
	 * id:用户ID
	 * */
    public static String refreshToken(String id) throws Exception{
        String url = "https://api.netease.im/nimserver/user/refreshToken.action";
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", id));
        String result = POST(url,nvps);
        System.out.println("更新并获取新TOKEN:"+result);
        return result;
    }
    
    /**
     *  创建群组
	 *  参数	类型	必须	说明
		tname	String	是	群名称，最大长度64字符
		owner	String	是	群主用户帐号，最大长度32字符
		members	String	是	["aaa","bbb"](JSONArray对应的accid，如果解析出错会报414)，一次最多拉200个成员
		announcement	String	否	群公告，最大长度1024字符
		intro	String	否	群描述，最大长度512字符
		msg	String	是	邀请发送的文字，最大长度150字符
		magree	int	是	管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。其它会返回414
		joinmode	int	是	群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。其它返回414
		custom	String	否	自定义高级群扩展属性，第三方可以跟据此属性自定义扩展自己的群属性。（建议为json）,最大长度1024字符
		icon	String	否	群头像，最大长度1024字符
		beinvitemode	int	否	被邀请人同意方式，0-需要同意(默认),1-不需要同意。其它返回414
		invitemode	int	否	谁可以邀请他人入群，0-管理员(默认),1-所有人。其它返回414
		uptinfomode	int	否	谁可以修改群资料，0-管理员(默认),1-所有人。其它返回414
		upcustommode	int	否	谁可以更新群自定义属性，0-管理员(默认),1-所有人。其它返回414
	 * */
    public static String createGroup(PageData pd) throws Exception{
        String url = "https://api.netease.im/nimserver/team/create.action";
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("tname", pd.getString("tname")));
		nvps.add(new BasicNameValuePair("owner", pd.getString("owner")));
		nvps.add(new BasicNameValuePair("members", pd.getString("members")));
		nvps.add(new BasicNameValuePair("announcement", pd.getString("announcement")));
		nvps.add(new BasicNameValuePair("intro", pd.getString("intro")));
		
		nvps.add(new BasicNameValuePair("msg", pd.getString("msg")));
		nvps.add(new BasicNameValuePair("magree", pd.getString("magree")));
		nvps.add(new BasicNameValuePair("joinmode", pd.getString("joinmode")));
		//nvps.add(new BasicNameValuePair("custom", pd.getString("custom")));
		nvps.add(new BasicNameValuePair("icon", pd.getString("icon")));
		
		nvps.add(new BasicNameValuePair("beinvitemode", "1"));
		//nvps.add(new BasicNameValuePair("invitemode", "0"));
		//nvps.add(new BasicNameValuePair("uptinfomode", "0"));
		//nvps.add(new BasicNameValuePair("upcustommode", "0"));
		
        String result = POST(url,nvps);
        System.out.println("创建群组:"+result);
        return result;
    }
    
    /**
     *  拉人进群组
	 *  参数	类型	必须	说明
		tid	String	是	网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
		owner	String	是	群主用户帐号，最大长度32字符
		members	String	是	["aaa","bbb"](JSONArray对应的accid，如果解析出错会报414)，一次最多拉200个成员
		magree	int	是	管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。其它会返回414
		msg	String	是	邀请发送的文字，最大长度150字符
		attach	String	否	自定义扩展字段，最大长度512
	 * */
    public static String addToGroup(PageData pd) throws Exception{
        String url = "https://api.netease.im/nimserver/team/add.action";
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("tid", pd.getString("tid")));
		nvps.add(new BasicNameValuePair("owner", pd.getString("owner")));
		nvps.add(new BasicNameValuePair("members", pd.getString("members")));
		nvps.add(new BasicNameValuePair("msg", pd.getString("msg")));
		//nvps.add(new BasicNameValuePair("attach", pd.getString("attach")));
		
        String result = POST(url,nvps);
        System.out.println("拉人进群:"+result);
        return result;
    }
    
    /**
     *  踢人出群组
	 *  参数	类型	必须	说明
		tid	String	是	网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
		owner	String	是	群主的accid，用户帐号，最大长度32字符
		member	String	是	被移除人的accid，用户账号，最大长度字符
		attach	String	否	自定义扩展字段，最大长度512
	 * */
    public static String kickFromGroup(PageData pd) throws Exception{
        String url = "https://api.netease.im/nimserver/team/kick.action";
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("tid", pd.getString("tid")));
		nvps.add(new BasicNameValuePair("owner", pd.getString("owner")));
		nvps.add(new BasicNameValuePair("member", pd.getString("member")));
		//nvps.add(new BasicNameValuePair("attach", pd.getString("attach")));
		
        String result = POST(url,nvps);
        System.out.println("踢人出群:"+result);
        return result;
    }
    
    /**
     *  解散群组
	 *  参数	类型	必须	说明
		tid	String	是	网易云通信服务器产生，群唯一标识，创建群时会返回，最大长度128字符
		owner	String	是	群主的accid，用户帐号，最大长度32字符
	 * */
    public static String removeGroup(PageData pd) throws Exception{
        String url = "https://api.netease.im/nimserver/team/remove.action";
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("tid", pd.getString("tid")));
		nvps.add(new BasicNameValuePair("owner", pd.getString("owner")));
		
        String result = POST(url,nvps);
        System.out.println("解散群组:"+result);
        return result;
    }
    
    /**
     * POST请求
	 * url:网易云信接口url
	 * nvps：网易云信接口参数
	 * */
    public static String POST(String url,List<NameValuePair> nvps
    		) throws Exception{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //String url = "https://api.netease.im/nimserver/user/refreshToken.action";
        HttpPost httpPost = new HttpPost(url);

        String nonce =  UUID.randomUUID().toString();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        //List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //nvps.add(new BasicNameValuePair("accid", id));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        // 打印执行结果
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
    
    public static void main(String[] args) throws Exception {
    	//String obj = refreshToken("78f6ab1802b34bae9cd4095d1a95b3fb");
    	//JSONObject jo = new JSONObject(obj);
    	//System.out.println(jo.getString("code"));
    	//建群(管理员群组)
//    	List<String> ids = new ArrayList<String>();
//    	ids.add("f1e3cfff9686475aaa8383e959bf8f9c");
//    	JSONArray jsonArr = JSONArray.fromObject(ids);
    	//创建群
//    	PageData pd = new PageData();
//    	pd.put("tname","管理员群组");
//    	pd.put("owner","f1e3cfff9686475aaa8383e959bf8f9c");
//    	pd.put("members",jsonArr.toString());
//    	pd.put("announcement","公告：群主为系统表超级管理员");
//    	pd.put("intro","管理员群组创建于2017-11-24日，用于前后端实时更新数据。");
//    	pd.put("msg","建联超级管理员邀请您加入管理员群组。");
//    	pd.put("magree","0");
//    	pd.put("joinmode","0");
//    	pd.put("icon","");
//    	String obj=createGroup(pd);
//    	System.out.println(obj);
    	//obj:{"code":200,"tid":"204877827"}
    	
    	//拉人进群组
    	List<String> ids = new ArrayList<String>();
    	ids.add("48a26e1c11c54498b9edec1accbb793e");
    	JSONArray jsonArr = JSONArray.fromObject(ids);
    	PageData pd = new PageData();
    	pd.put("tid","204877827");
    	pd.put("owner","f1e3cfff9686475aaa8383e959bf8f9c");
    	pd.put("members",jsonArr.toString());
    	pd.put("msg","建联超级管理员邀请您加入管理员群组。");
    	addToGroup(pd);
    	
    	//pd.put("member","fda17af561bc47b5a7de50ffa3aa5c2c");
    	//pd.put("msg","邀请内容。。。");
    	//kickFromGroup(pd);
    	
    	//removeGroup(pd);
    }
    
    
    /**
     *  发送普通消息
	 *  from	String	是	发送者accid，用户帐号，最大32字符，
		必须保证一个APP内唯一
		ope	int	是	0：点对点个人消息，1：群消息（高级群），其他返回414
		to	String	是	ope==0是表示accid即用户id，ope==1表示tid即群id
		type	int	是	0 表示文本消息,
		1 表示图片，
		2 表示语音，
		3 表示视频，
		4 表示地理位置信息，
		6 表示文件，
		100 自定义消息类型（特别注意，对于未对接易盾反垃圾功能的应用，该类型的消息不会提交反垃圾系统检测）
		body	String	是	请参考下方消息示例说明中对应消息的body字段，
		最大长度5000字符，为一个JSON串
	 * */
    public static String sendMsg(PageData pd) throws Exception{
    	String from = "f1e3cfff9686475aaa8383e959bf8f9c";//默认超级管理员
    	String ope = "1";//默认群消息
    	String to = "204877827";//默认管理员群组
    	String type = "0";//默认文本消息
        String url = "https://api.netease.im/nimserver/msg/sendMsg.action";
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("from", from));
        nvps.add(new BasicNameValuePair("ope", ope));
        nvps.add(new BasicNameValuePair("to", to));
        nvps.add(new BasicNameValuePair("type", type));
        nvps.add(new BasicNameValuePair("body", pd.getString("body")));
        String result = POST(url,nvps);
        System.out.println("发送消息:"+result);
        return result;
    }
}
