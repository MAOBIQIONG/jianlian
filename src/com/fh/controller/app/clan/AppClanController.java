package com.fh.controller.app.clan;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.area.AppAreaService;
import com.fh.service.app.clan.AppClanService;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.clan.ClanService;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

@Controller
@RequestMapping({ "/appClan" })
public class AppClanController extends BaseController {
	String menuUrl = "clan/listclan.do";

	@Resource(name = "appClanService")
	private AppClanService appClanService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
		
	@Resource(name="appareaService")
	private AppAreaService appareaService;
	//通过ID获取城市建联列表详细信息
	@RequestMapping({ "/queryById" })
	@ResponseBody
	public PageData queryById()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list=new ArrayList<PageData>();
		
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int totalSize=currentPage*pageSize; 
		pa.put("totalSize",totalSize); 
		
		PageData hj=new PageData();
		hj.put("CLAN_ID", pa.getString("ID"));
		hj.put("userid", pa.getString("userid")); 
		
	    PageData dataList= this.appClanService.queryByuserclan(hj); //查询某个城市建联的参与人数
	    int count=0;
	    if(dataList!=null&&dataList.size()>0){
	        count=dataList.size();
	    }
		    
		PageData clan=this.appClanService.queryById(pa);
		list=this.appClanService.queryByCId(pa);//某个城市建联的用户信息 
		
		clan.put("count",count);
		clan.put("userList",list);
		clan.put("userCount",list.size());
		_result = AppUtil.ss(clan, "01", "成功","true", null);
		return _result; 
	}
		
	/*//获取城市建联列表信息
	@RequestMapping({ "/queryAll" })
	@ResponseBody
	public PageData queryAll() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> clanlist=this.appClanService.queryAll();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("clanlist",clanlist);
		if(clanlist!=null&&clanlist.size()>0){
			map.put("count",clanlist.size());
		}else{
			map.put("count",0);
		}
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}*/
	
	//通过ID获取城市建联用户信息
	@RequestMapping({ "/queryUserByCId" })
	@ResponseBody
	public PageData queryUserByCId()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list=new ArrayList<PageData>(); 
		list=this.appClanService.queryByCId(pa);//某个城市建联的用户信息 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	}
		
	//获取用户关注的城市建联信息
	@RequestMapping({ "/queryByUId" })
	@ResponseBody
	public PageData queryByUId()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
//		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
//		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
//		int totalSize=currentPage*pageSize; 
//		pa.put("totalSize",totalSize);  
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		pa.put("start",start); 
		pa.put("pageSize",pageSize);
		
		String clanname=pa.getString("clanNAME");
		if ((clanname != null) && (!"".equals(clanname))) { 
			clanname=new String(clanname.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("NAME",clanname);
		}
		
		
		int cszong = 0;
		PageData csszong=new PageData();
		List<PageData> clanlist=new ArrayList<PageData>();
		int flag=Integer.parseInt(pa.get("flag")+"");
	    if(flag==0){
		   clanlist=this.appClanService.queryAll(pa);//查询所有城市建联 
		   cszong=clanlist.size();
		   csszong=this.appClanService.queryAllzong(pa);
	    }else if(flag==1){
		   clanlist=this.appClanService.queryByUId(pa);//查询某用户关注的城市建联 
		   cszong=clanlist.size();
		   csszong=this.appClanService.queryByUIdzong(pa);
	    }else if(flag==2){
			   clanlist=this.appClanService.queryAll(pa);//查询某用户关注的城市建联 
			   cszong=clanlist.size();
			   csszong=this.appClanService.queryAllzong(pa);
		    }
	    List<PageData> shengfen=appareaService.queryAllParent(null);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("clanlist",clanlist);
		map.put("cszong",cszong);
		map.put("csszong",csszong);
		map.put("shengfen",shengfen);
		if(clanlist!=null&&clanlist.size()>0){
			map.put("count",clanlist.size());
		}else{
			map.put("count",0);
		}
		_result = AppUtil.ss(map, "01", "成功","true", null); 
		return _result; 
	}
	
	//关注或取消建联关注
	@RequestMapping({ "/guanzhu" })
	@ResponseBody
	public PageData guanzhu()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData uidclan=this.appClanService.queryByuserclan(pa);
		if(uidclan==null){
			String id=this.get32UUID();
			pa.put("ID", id);
			pa.put("DATE", new Date());
			pa.put("CLAN_ID",pa.getString("CLAN_ID"));
			Object obj=appClanService.saveguanzhu(pa);
			if(Integer.valueOf(obj.toString()) >= 1){
				PageData data=new PageData();
				data.put("CLAN_ID",pa.getString("CLAN_ID")); 
				data.put("NUMBER_COUNTS",1); 
				Object ob=appClanService.updateaddClanCounts(data);
				if(Integer.valueOf(ob.toString()) >= 1){
					_result = AppUtil.ss(null, "01", "关注成功","true",null);
				}else{
					_result = AppUtil.ss(null, "90", "关注失败","false",null);
				}
			}else{
				 _result = AppUtil.ss(null, "90", "失败","false",null);
			} 
		}else{
				Object del=this.appClanService.deleteguanzhu(uidclan);
				if(Integer.valueOf(del.toString()) >= 1){
					PageData data=new PageData();
					data.put("CLAN_ID",pa.getString("CLAN_ID")); 
					data.put("NUMBER_COUNTS",1); 
					Object ob=appClanService.updatedelClanCounts(data);
					if(Integer.valueOf(ob.toString()) >= 1){
						_result = AppUtil.ss(null, "01", "取消关注成功","true",null);
					}else{
						_result = AppUtil.ss(null, "90", "取消关注失败","false",null);
					}
				}else{
					 _result = AppUtil.ss(null, "90", "失败","false",null);
				}
			}
		return _result; 
	}
	
	//获取城市建联的成员
	@RequestMapping({ "/queryclanusers" })
	@ResponseBody
	public PageData queryclanusers()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
//		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
//		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
//		int totalSize=currentPage*pageSize; 
//		pa.put("totalSize",totalSize); 
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		pa.put("start",start); 
		pa.put("pageSize",pageSize);
		
		List<PageData> list=new ArrayList<PageData>(); 
		list=this.appClanService.queryclanusers(pa);//某个城市建联的用户信息 
		int jlyh=list.size();
		PageData count=this.appClanService.queryclanuserscount(pa);
		PageData hj=new PageData();
		hj.put("list", list);
		hj.put("count", count);
		hj.put("jlyh", jlyh);
		_result = AppUtil.ss(hj, "01", "成功","true", null);
		return _result; 
	}
}

