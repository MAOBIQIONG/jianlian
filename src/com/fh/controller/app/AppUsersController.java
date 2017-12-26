package com.fh.controller.app;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;  

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.service.app.AppCommonService; 
import com.fh.service.app.Appcertificates.AppCertificatesService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.area.AppAreaService;
import com.fh.service.app.clan.AppClanService;
import com.fh.service.app.company.AppCategoryService;
import com.fh.service.app.company.AppcompanyService;
import com.fh.service.app.connections.AppConnectionService;
import com.fh.service.app.notification.AppNotificationService;
import com.fh.service.app.pay.AppOrdersService;
import com.fh.service.app.project.AppProjectService;
import com.fh.service.system.company.CategoryService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.util.AppUtil;
import com.fh.util.ChineseToEnglish;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.PushUtil;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/appusers" })
public class AppUsersController extends AppController{

	@Resource(name = "appusersService")
	private AppusersService appusersService;
		
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name="appcompanyService")
	private AppcompanyService appCompanyService;
	
	@Resource(name = "appcertificatesService")
	private AppCertificatesService appCertService;
	
	@Resource(name = "appNotificationService")
	private AppNotificationService appNotifService;
	
	@Resource(name="appOrdersService")
	private AppOrdersService appOrdersService; 
	
	@Resource(name = "appCategoryService")
	private AppCategoryService appCateService;  
	
	@Resource(name = "appconnectionService")
	private AppConnectionService appconnService;
	
	@Resource(name = "appProjectService")
	private AppProjectService appProjectService;
	
	@Resource(name = "appClanService")
	private AppClanService appClanService;
	
	@Resource(name = "appareaService")
	private AppAreaService appareaService; 
	
    //人脉首页信息获取接口
	@RequestMapping({ "/indexInfo" })
	@ResponseBody
	public PageData indexInfo() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData()); 
		List<PageData> userList=this.appusersService.querySimpleInfoById(pa);//获取用户基本信息  
		PageData messageCounts=this.appNotifService.countNotification(pa);//获取消息通知总条数
		PageData orderCounts=this.appOrdersService.queryCounts(pa);//获取某会员未支付订单的总数
		PageData connCounts=this.appconnService.countUsersByUid(pa);//查询某会员的人脉总数
		PageData proCounts=this.appProjectService.countPubProject(pa);//查询某会员发布成功的项目总数
		PageData msg=new PageData();
		msg.put("USER_ID",pa.getString("userid")); 
		msg.put("IS_READ","0");
		PageData TotalMsg=this.appNotifService.queryCountsByParam(msg);//查询某会员的消息数
		msg.put("TABLE_NAME","project");
		PageData proMsg=this.appNotifService.queryCountsByParam(msg);//查询某会员项目的消息数
		msg.put("TABLE_NAME","orders");
		PageData ordersMsg=this.appNotifService.queryCountsByParam(msg);//查询某会员订单消息数
		msg.put("TABLE_NAME","activity");
		PageData actMsg=this.appNotifService.queryCountsByParam(msg);//查询某会员活动消息数
		msg.put("TABLE_NAME","certificates");
		PageData certMsg=this.appNotifService.queryCountsByParam(msg);//查询某会员证件消息数
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("user",userList.get(0));
		map.put("messageCounts",messageCounts);
		map.put("orderCounts",orderCounts); 
		map.put("connCounts",connCounts); 
		map.put("proCounts",proCounts); 
		map.put("TotalMsg",TotalMsg);
		map.put("proMsg",proMsg); 
		map.put("actMsg",actMsg); 
		map.put("certMsg",certMsg); 
		map.put("ordersMsg",ordersMsg); 
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}
	
	
	//根据邮箱查找密码
	@RequestMapping({ "/querEmail" })
	@ResponseBody
	public PageData querEmail() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData data=new PageData();
		data.put("EMAIL", pa.getString("EMAIL"));
		List<PageData> list=new ArrayList<PageData>();
		
		list=this.appusersService.findByEMAIL(pa);
		
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
	
	//根据ID查询当前用户的简单信息
	@RequestMapping({ "/querySimpleInfoById" })
	@ResponseBody
	public PageData querySimpleInfoById() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData data=new PageData();
		
		data.put("userid", pa.getString("userid"));
		List<PageData> list=new ArrayList<PageData>();
		list=this.appusersService.querySimpleInfoById(pa);
//		List<PageData> clanList=this.appClanService.queryAll(null);
		//查询省份
		List<PageData> shenlist=this.appareaService.queryAllParent(null);
		for(int i=0;i<shenlist.size();i++){
			PageData datas=shenlist.get(i);
			List<PageData> child=this.appClanService.queryclanlist(datas);
			shenlist.get(i).put("children",child);
		}
		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("clanList", clanList);
		map.put("shenlist", shenlist); 
		map.put("baseInfo", list.get(0));
    	_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}
	
	
	//个人信息详细信息
	@RequestMapping({ "/querusers" })
	@ResponseBody
	public PageData querusers() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData data=new PageData();
		
		data.put("ID", pa.getString("ID"));
		List<PageData> list=new ArrayList<PageData>();
		list=this.appusersService.querusers(pa); 
		String keys=list.get(0).getString("ASSESS");
		String ronyu=list.get(0).getString("HONOR");
		if(keys!=null&&!"".equals(keys)){
			String[] assess=keys.split(",");
			list.get(0).put("assess", assess);
		}
		if(ronyu!=null&&!"".equals(ronyu)){
			String[] HONOR=ronyu.split(",");
			list.get(0).put("honor", HONOR);
		}
		_result = AppUtil.ss(list.get(0), "01", "成功","true", null);
		return _result;
	}
	
	//我的助理
	@RequestMapping({ "/querassname" })
	@ResponseBody
	public PageData querassname() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
//		PageData data=new PageData();
//		data.put("ID", pa.getString("ID"));
		List<PageData> list=new ArrayList<PageData>();
		list=this.appusersService.querassname(pa);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
		
	//我的简介修改
	@RequestMapping({ "/upSummary" })
	@ResponseBody
	public PageData upSummary() throws Exception { 
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData data=new PageData();
		data.put("userid", pa.getString("userid"));
		
		String da= pa.getString("SUMMARY");
		if ((da != null) && (!"".equals(da))) { 
			String param=new String(da.getBytes("ISO-8859-1"),"UTF-8").trim();
			data.put("SUMMARY",param);
		}
		
		Object obj=	this.appusersService.edit(data);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    } 
		return _result;
	} 
		
	//我的当前公司介绍
	@RequestMapping({ "/quercompanyjianjie" })
	@ResponseBody
	public PageData quercompanyjianjie() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list=new ArrayList<PageData>();
		list=this.appusersService.quercompanyjianjie(pa);
//		List<PageData> lists=new ArrayList<PageData>();
//		lists=this.appusersService.querycategory(null);
		List<PageData> lists=this.appCateService.listByPId("0");
		for(int i=0;i<lists.size();i++){
			PageData data=lists.get(i);
			List<PageData> child=this.appCateService.listByPId(data.getString("value"));
			lists.get(i).put("children",child);
		}
		PageData result=new PageData();
		result.put("list", list);
		result.put("hylist", lists);
		List<PageData> cateList=this.appCateService.listByPId("0");
		for(int i=0;i<cateList.size();i++){
			PageData data=cateList.get(i);
			List<PageData> child=this.appCateService.listByPId(data.getString("value"));
			cateList.get(i).put("children",child);
		} 
		result.put("cateAll",cateList);
		_result = AppUtil.ss(result, "01", "成功","true", null);
		return _result;
	}
	 //查询所有行业 
		@RequestMapping({ "/queryAllPIDCategory" })
		@ResponseBody
		public PageData queryAllPIDCategory()throws Exception{
			PageData _result=new PageData(); 
			List<PageData> list=this.appCateService.listByPId("0");
			for(int i=0;i<list.size();i++){
				PageData data=list.get(i);
				List<PageData> child=this.appCateService.listByPId(data.getString("value"));
				list.get(i).put("children",child);
			}
			List<PageData> clanList=this.appClanService.queryAll(null);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("clanList", clanList);
			map.put("catelist", list);
	  		_result = AppUtil.ss(map, "01", "成功","true", null);
			return _result; 
		} 
	//我的荣誉新增
	@RequestMapping({ "/addHonor" }) 
	@ResponseBody
	public PageData addHonor()throws Exception{
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
		PageData data = new PageData();
		data.put("ID", pa.getString("ID"));
		PageData user=this.appusersService.queryById(data);
		String honor=user.getString("HONOR");
		String param=pa.getString("HONOR");
		if ((param != null) && (!"".equals(param))) { 
			param=new String(param.getBytes("ISO-8859-1"),"UTF-8").trim();
		}
		if(param!=null&&!"".equals(param)&&honor!=null&&!"".equals(honor)){ 
			honor=honor+","+param;
		}else{
			honor=honor+param;
		}  
		data.put("HONOR",honor);
		Object obj=this.appusersService.edit(data);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    } 
		return _result; 
	}
	
	//我的荣誉修改
	@RequestMapping({ "/uphonor" })
	@ResponseBody
	public PageData uphonor()throws Exception{
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
		PageData data = new PageData();
		String param=pa.getString("HONOR");
		data.put("userid", pa.getString("userid"));
		if ((param != null) && (!"".equals(param))) { 
			param=new String(param.getBytes("ISO-8859-1"),"UTF-8").trim();
			data.put("HONOR",param);
		} 
		
		Object obj=this.appusersService.edithonor(data);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    } 
		return _result; 
	}
	
	//我的基本信息修改
	@RequestMapping({ "/updateBasicInfo" })
	@ResponseBody
	public PageData updateBasicInfo() throws Exception {  
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
		
		PageData data = new PageData();
		data.put("ID", pa.getString("ID")); 
		PageData page=appusersService.queryById(data);
		data.put("SEX", pa.getString("SEX"));
		data.put("PHONE", pa.getString("PHONE"));
		data.put("EMAIL", pa.getString("EMAIL"));
		data.put("IMG", pa.getString("IMG"));
		data.put("CLAN_ID", pa.getString("CLAN_ID"));
		data.put("CARD_NO", pa.getString("CARD_NO"));
		ChineseToEnglish pin=new ChineseToEnglish();
		String param=pa.getString("REAL_NAME");
		if ((param != null) && (!"".equals(param))) { 
			param=new String(param.getBytes("ISO-8859-1"),"UTF-8").trim();
			data.put("REAL_NAME",param);
			data.put("VALERIE", pin.getPingYinToHeaderChar(param)) ;
		} 
		
		Object obj=this.appusersService.upbasic(data);
		page.put("MEMBER_COUNT",1);  
		data.put("MEMBER_COUNT",1);  
		String clan_id=pa.getString("CLAN_ID");
		if(clan_id!=null&&clan_id!=""){
			Object o=this.appClanService.updatedelClanCounts(page);
			Object j=this.appClanService.updateaddClanCounts(data);
		} 
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "90", "失败","false",null);
	    } 
		return _result; 
	}	
		
	
	//我的头像修改
	@RequestMapping({ "/upimg" })
	@ResponseBody
	public PageData upimg()throws Exception { 
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());  
		pa.put("IMG",getPageData().getString("IMG"));  
		pa.put("ID", pa.getString("userid"));  
		Object obj= this.appusersService.upimg(pa);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "90", "失败","false",null);
	    } 
		return _result;
	}
		
	//个人信息评价获取
	@RequestMapping({ "/querrated" })
	@ResponseBody
	public PageData querrated() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
//			PageData data = new PageData();
//			data.put("ID", pa.getString("ID"));
		List<PageData> list=new ArrayList<PageData>();
		list =this.appusersService.querrated(pa);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
	
	//个人信息评价修改
	@RequestMapping({ "/addAssess" })
	@ResponseBody
	public PageData addAssess()throws Exception{
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
		PageData data = new PageData();
		data.put("userid", pa.getString("ID"));
		String ASSESS=pa.getString("ASSESS");
		if ((ASSESS != null) && (!"".equals(ASSESS))) { 
			ASSESS=new String(ASSESS.getBytes("ISO-8859-1"),"UTF-8").trim(); 
		}
		data.put("ASSESS",ASSESS);
		Object obj=this.appusersService.edit(data);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    } 
		return _result; 
	}
		
	//账号和安全信息获取
	@RequestMapping({ "/queranquan" })
	@ResponseBody
	public PageData queranquan() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
//			PageData data = new PageData();
//			data.put("ID", pa.getString("ID"));
		List<PageData> list=new ArrayList<PageData>();
		list=this.appusersService.queranquan(pa);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
 
	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/updatePass" })
	@ResponseBody
	public PageData updatePass() throws Exception {

		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData pad = new PageData(); 
		pad.put("USER_NAME",pa.getString("USER_NAME"));
		String password = new SimpleHash("SHA-1",pa.getString("USER_NAME"),pa.getString("PASSWORD")).toString();
		pad.put("PASSWORD",password);
		PageData user = this.appusersService.checkLogin(pad);
		
		if (user != null) {
			// 基本信息 
//			PageData pad = new PageData(); 
//			String password = new SimpleHash("SHA-1",pa.getString("USER_NAME"),pa.getString("PASSWORD1")).toString();
			String password1 = new SimpleHash("SHA-1",pa.getString("USER_NAME"),pa.getString("PASSWORD1")).toString();
			pad.put("PASSWORD",password1);
			Object re = this.appusersService.editPassWord(pad);

			if (Integer.valueOf(re.toString()) == 1) { 
				_result = AppUtil.ss(null, "01", "成功", "true", "");
			} else {
				_result = AppUtil.ss(null, "92", "失败", "false", null);

			}
		}
		return _result;
	}
	
	/**
	 * 查询必上传证件是否全部验证通过
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/isValid" })
	@ResponseBody
	public PageData isValid() throws Exception {

		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list=this.appusersService.querySimpleInfoById(pa);  
		List<PageData> certList=appCertService.findByUserId(pa);//查询用户的全部证件
		if(certList==null||certList.size()<=0){
			list.get(0).put("valid","0");//表示未上传证件 
			_result = AppUtil.ss(list.get(0), "01", "请上传证件信息！", "false", "");
		}else {
			int num=0;
			int mm=0;
			for(int i=0;i<certList.size();i++){
				PageData cert=certList.get(i);
				if("03".equals(cert.getString("STATUS"))){ 
					num++;
					if("1".equals(cert.getString("CERTIFICATE_TYPE"))||"2".equals(cert.getString("CERTIFICATE_TYPE"))){
						mm++;
					}
				}else{ 
					break;
				}
			}
			if(mm==2){
				if(num==certList.size()){
					list.get(0).put("valid","1"); // 证件已全部验证成功
					_result = AppUtil.ss(list.get(0), "01", "证件已全部验证成功", "true", "");	
				}else{
					list.get(0).put("valid","2");//必填证件已验证成功
					_result = AppUtil.ss(list.get(0), "01", "部分证件未验证，必上传证件已验证！", "false", "");  
				} 
			}else {
				list.get(0).put("valid","3");//证件未验证
				_result = AppUtil.ss(list.get(0), "01", "证件未验证，请耐心等待！", "false", "");  
			}  
		}
		return _result;
	}
	
	/**
	 * 升级入会
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/goToVip" })
	@ResponseBody
	public PageData goToVip() throws Exception {

		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
	/*	PageData data=new PageData();
		data.put("USER_ID",pa.getString("userid"));
		List<PageData> certList=appCertService.findByUserId(data);
		if(certList==null||certList.size()<=0){
			_result = AppUtil.ss(null, "92", "请上传证件信息！", "true", "");
		}else {
			int num=0;
			for(int i=0;i<certList.size();i++){
				PageData cert=certList.get(i);
				if("3".equals(cert.getString("STATUS"))){
					num++;
				}else{
					_result = AppUtil.ss(null, "92", "证件未验证，请耐心等待！", "false", null); 
					break;
				}
			}
			if(num==certList.size()){*/
				PageData page=new PageData();
				page.put("userid",pa.getString("userid"));
				page.put("IS_VIP","2");//标识会员请求 入会,且未审核
				Object re = this.appusersService.edit(page);
				if (Integer.valueOf(re.toString()) == 1) {
					PageData orders=new PageData();
					orders.put("ID",get32UUID());
					orders.put("USER_ID",pa.getString("userid"));
					orders.put("PRICE",1000);
					orders.put("DATE",new Date());
					orders.put("EVENT","普通用户升级成为会员!");
					orders.put("STATUS","01");//未支付
					Object rel = this.appOrdersService.save(orders); 
					PageData notif=new PageData();
	    	 		notif.put("ID", get32UUID()); 
	    	 	    notif.put("CONTENT","您有一笔订单要支付！"); 
	    	 		notif.put("USER_ID", pa.getString("userid"));
	    	 		notif.put("CREATE_DATE", new Date());
	    	 		notif.put("TABLE_NAME","orders");
	    	 		notif.put("OBJECT_ID",orders.getString("ID"));
	    	 		notif.put("STATUS","01"); 
	    	 		appNotifService.save(notif);
	    	 		
	    	 		PageData pagedata = new PageData();
	    	 		pagedata.put("ID",pa.getString("userid"));
	    	 		PageData user = this.appusersService.queryById(pagedata);
	    	 		if( user != null ){
	    	 			//推送审核消息
//						NotificationTemplate template = null;
//						if( user.getString("PLATFORM") == "1" ){
//							template = PushUtil.notificationTemplateDemo(); 
//							template.setTitle("上海建联");
//							template.setText("您有一笔订单要支付！");
//						}
	    	 			String bt = "上海建联";
	    	 			String nr = "您有一笔订单要支付！";
	    	 			String jsonStr = "{'type':'order','title':'"+bt+"','content':'"+nr+"'}";//透传内容
						TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo(bt,nr,jsonStr);
						PushUtil pushApp=new PushUtil();
						String alias = pa.getString("userid");
						pushApp.pushToSingle(tmTemplate,alias); 
	    	 		}
					
					if (Integer.valueOf(rel.toString()) == 1) {
						_result = AppUtil.ss(null, "01", "成功", "true", "");
					}else{
						_result = AppUtil.ss(null, "90", "失败", "true", "");
					} 
				} else {
					_result = AppUtil.ss(null, "92", "失败", "false", null);

				} 
		/*	}
		}*/
		return _result;
	}
	
	
	//修改公司信息
	@RequestMapping({ "/updateCompany" })
	@ResponseBody
	public PageData updateCompany() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData data=new PageData(); 
		
		data.put("LOGO", pa.getString("LOGO")); 
		data.put("COMPANY_NAME", pa.getString("COMPANY_NAME"));
		data.put("INTERNET_PATH", pa.getString("COMPANYPROFILE"));
		data.put("CGID", pa.getString("CGID"));
		
		String DESCRIPTION=pa.getString("DESCRIPTION");
		String COMPANY_NAME=pa.getString("COMPANY_NAME");
		String ADDR=pa.getString("ADDR");
		String CATNAME=pa.getString("CATNAME");
		String POSITION=pa.getString("POSITION");
		if ((DESCRIPTION != null) && (!"".equals(DESCRIPTION))) { 
			DESCRIPTION=new String(DESCRIPTION.getBytes("ISO-8859-1"),"UTF-8").trim(); 
		}
		if ((COMPANY_NAME != null) && (!"".equals(COMPANY_NAME))) { 
			COMPANY_NAME=new String(COMPANY_NAME.getBytes("ISO-8859-1"),"UTF-8").trim(); 
		}
		if ((ADDR != null) && (!"".equals(ADDR))) { 
			ADDR=new String(ADDR.getBytes("ISO-8859-1"),"UTF-8").trim(); 
		}
		if ((CATNAME != null) && (!"".equals(CATNAME))) { 
			CATNAME=new String(CATNAME.getBytes("ISO-8859-1"),"UTF-8").trim(); 
		}
		if ((POSITION != null) && (!"".equals(POSITION))) { 
			POSITION=new String(POSITION.getBytes("ISO-8859-1"),"UTF-8").trim(); 
		}
		data.put("DESCRIPTION",DESCRIPTION);
		data.put("COMPANY_NAME",COMPANY_NAME);
		data.put("ADDR",ADDR);
		data.put("CATNAME",CATNAME);
		data.put("POSITION", POSITION);
		Object cmo=null;
		Object pos=null;
		String comid=pa.getString("COMID"); 
		if(comid==""||comid==null){//新增
			data.put("ID",get32UUID());
			data.put("UID", pa.getString("userid"));
			data.put("CREATE_BY",pa.getString("userid"));
			data.put("CREATE_DATE",new Date());
			cmo = this.appCompanyService.save(data); 
			data.put("COMPANY_ID",data.getString("ID")); 
			pos=this.appCompanyService.editPOSITION(data);
			comid=data.getString("ID");
		}else{//修改
			data.put("COMID",comid);
			data.put("UID", pa.getString("UID"));
			cmo = this.appCompanyService.editCompany(data);
			pos=this.appCompanyService.editPOSITION(data);
		} 
		if (cmo != null && pos!=null) {
			if (Integer.valueOf(cmo.toString()) == 1) { 
				_result = AppUtil.ss(comid, "01", "成功", "true", "");
			} else {
				_result = AppUtil.ss(null, "92", "失败", "false", null);

			}
		}
		return _result;
	}
	
}
