package com.fh.controller.shoper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.app.AppController;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User; 
import com.fh.service.product.ProductService;
import com.fh.service.shop.ShopService;
import com.fh.service.shoper.ProductsService;
import com.fh.util.FileUpload;
import com.fh.util.PageData; 
import com.fh.util.SystemLog; 
import com.fh.util.fileConfig;
import com.fh.util.generate_on.ProNoUtil;

@Controller
@RequestMapping({ "/appproduct" })
public class appProductController extends AppController {  
	
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	public static String serverImgPath = fileConfig.getString("serverImgPath");
	@Resource(name = "productsService")
	private ProductsService productsService;  
	
	@Resource(name = "shopService")
	private ShopService shopService; 
	 
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData data=getPageData();
		if(data!=null&&data.get("SHOP_ID")!=null){
			mv.addObject("SHOP_ID",data.getString("SHOP_ID"));
		} 
		mv.setViewName("shoper/product_list");
		return mv;
	} 
	
	@RequestMapping(value={"/queryList"}) 
	@ResponseBody
	public String queryList(Page page) throws Exception {
		PageData pd = new PageData();
		pd = getPageData();

		JSONObject getObj = new JSONObject();
		String sEcho = "0";// 记录操作的次数  每次加1  
		String iDisplayStart = "0";// 起始  
		String iDisplayLength = "10";// size  
		String mDataProp = "mDataProp_";
		String sortName = "";//排序字段
		String iSortCol_0 = "";//排序索引
		String sSortDir_0 = "";//排序方式
		//获取jquery datatable当前配置参数  
		JSONArray jsonArray = JSONArray.fromObject(pd.getString("aoData"));  
		for (int i = 0; i < jsonArray.size(); i++){  
		    try {  
		        JSONObject jsonObject = (JSONObject)jsonArray.get(i);  
		        if (jsonObject.get("name").equals("sEcho")){ 
		            sEcho = jsonObject.get("value").toString();  
		        }
		        else if (jsonObject.get("name").equals("iDisplayStart")){
		            iDisplayStart = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iDisplayLength")){ 
		            iDisplayLength = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("sSortDir_0")){
		        	sSortDir_0 = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iSortCol_0")){
		        	iSortCol_0 = jsonObject.get("value").toString();
		        	mDataProp = mDataProp+""+iSortCol_0;
		        	for (int j = 0; j < jsonArray.size(); j++)  {
		        		JSONObject jsonObject1 = (JSONObject)jsonArray.get(j);  
		        		if(jsonObject1.get("name").equals(mDataProp)){
		        			sortName = jsonObject1.get("value").toString(); 
		        		}
		        	}
		        }
		        
		    }  
		    catch (Exception e) { 
		    	e.printStackTrace();
		        break;  
		    }  
		}  
		pd.put("USERID",getUser().getID());
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List<PageData> shops = this.productsService.listPageByParam(page);
		Object	total = this.productsService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData data:shops){ 
			data.put("CREATE_DATE",data.get("CREATE_DATE")==null?"":data.get("CREATE_DATE").toString()); 
			data.put("MODIFY_DATE",data.get("MODIFY_DATE")==null?"":data.get("MODIFY_DATE").toString()); 
		} 
		getObj.put("aaData", shops);//要以JSON格式返回
		return getObj.toString();
	}  
	
	@RequestMapping({ "/toAdd" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView(); 
		PageData data=getPageData(); 
		String PROD_NO=ProNoUtil.getProdNo();
		mv.addObject("PROD_ID",get32UUID());
		mv.addObject("PROD_NO",PROD_NO);
		mv.addObject("SHOP_ID", data.getString("SHOP_ID"));
	    mv.setViewName("shoper/product_add");  
		return mv;
	}
	 
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			List<PageData> data = this.productsService.findById(pd);   
			if(data!=null&&data.size()==1){
				pd=data.get(0);
			} 
			mv.addObject("pd",pd);  
			mv.setViewName("shoper/product_edit"); 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		return mv;
	}
	
	/**
	 * 验证商品编号是否存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/findByprodno" })
	@ResponseBody
	public JSONObject findByprodno() throws Exception {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		pd = getPageData();
		List<PageData> data = this.productsService.findById(pd);
		if(data!=null&&data.size()>=1){
			obj.put("code","400");
		}else{
			obj.put("code", "200");
		} 
		return obj;
	}
		
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="商品管理",type="商品编辑")
	 @ResponseBody
	 public String save(MultipartHttpServletRequest request)throws Exception{ 
	     PageData pd = new PageData();
	     JSONObject getObj = new JSONObject();
	     Object ob=0;
	     try {  
	    	 pd=getPageData();   
	    	 MultipartFile file=request.getFile("prod_head"); 
	    	 String id=request.getParameter("id");  
	    	 pd.put("PROD_ID", request.getParameter("PROD_ID"));
	    	 pd.put("PROD_NO", request.getParameter("prod_no"));
	    	 pd.put("PROD_NAME", request.getParameter("prod_name"));
	    	 pd.put("EXPRESS_PRICE", request.getParameter("express_price"));
	    	 pd.put("sxn", request.getParameter("sxn")); 
	    	 pd.put("sxz", request.getParameter("sxz"));
	    	 pd.put("ggn", request.getParameter("ggn"));
	    	 pd.put("ggz", request.getParameter("ggz"));
	    	 pd.put("sku_n", request.getParameter("sku_n")); 
	    	 pd.put("sku_num", request.getParameter("sku_num"));
	    	 pd.put("sku_price", request.getParameter("sku_price"));
	    	 pd.put("yh_price", request.getParameter("yh_price"));
	    	 pd.put("sc_price", request.getParameter("sc_price")); 
	    	 
	    	 if ((file != null) && (!file.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(file,filePath,get32UUID());
				pd.put("PROD_HEAD", fileName); 
	    	 } 
	    	 if(id==null||"".equals(id)){//新增  
	    		 pd.put("SHOP_ID", request.getParameter("SHOP_ID"));
	    		 pd.put("ID",pd.getString("PROD_ID")); 
	    		 pd.put("CREATE_DATE",new Date()); 
	    		 PageData shop=this.shopService.queryBySId(pd);  
	    		 pd.put("USER_ID",shop.getString("USER_ID"));
	    		 ob=this.productsService.save(pd);   
	    	 }else{//修改 
	    		 pd.put("ID",id); 
	    		 ob=this.productsService.edit(pd);  
	    	 }  
		 } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		 }   
	     if(Integer.parseInt(ob.toString())>=1){
	    	 getObj.put("statusCode", 200);
	     }else{
	    	 getObj.put("statusCode", 400);
	     } 
		 return getObj.toString(); 
	 }  
	 
	
	@RequestMapping({ "/updateStatus" })
	@SystemLog(methods="商品管理",type="更新商品状态")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		JSONObject getObj = new JSONObject();
		Object ob=0;
		try {
			pd = getPageData(); 
			ob=this.productsService.updateStatus(pd);  
			if(Integer.parseInt(ob.toString())>=1){
				getObj.put("statusCode", 200);
			}else{
				getObj.put("statusCode", 400);
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return getObj.toString(); 
	} 
	
	@RequestMapping({ "/toImg" })
	public ModelAndView toImg() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			
			List<PageData> imgs =productsService.findImages(pd);  
			mv.addObject("imgs", imgs); 
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("shoper/pro_img_list");  
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping({ "/toDescImg" })
	public ModelAndView todescImg() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			List<PageData> imgs =productsService.finddescImages(pd);  
			mv.addObject("imgs", imgs); 
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("shoper/pro_descimg_list");  
		mv.addObject("pd", pd);
		return mv;
	}
	@RequestMapping({ "/toAddimg" })
	public ModelAndView toAddimg() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("shoper/pro_img_add"); 
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping({ "/uploadImgs" }) 
	@ResponseBody
	public JSONObject uploadImgs(MultipartHttpServletRequest request)throws Exception{
		JSONObject obj = new JSONObject(); 
		PageData pd = new PageData();  
		String PRO_ID=request.getParameter("PRO_ID");
		pd.put("PRODUCT_ID",PRO_ID); 
		pd.put("ORDER_BY", request.getParameter("IMGORDER"));
		pd.put("TYPE",request.getParameter("TYPE")); 
		MultipartFile img=request.getFile("IMG_PATH");  
		if ((img != null) && (!img.isEmpty())) {
			String filePath =serverBasePath; 
			String fileName = FileUpload.fileUp(img,filePath,get32UUID());  
			if(fileName!=null&&!"".equals(fileName)){
				pd.put("IMG_PATH",serverImgPath+fileName.trim()); 
			} 
		}  
		pd.put("ID", get32UUID()); 
		Object ob=productsService.saveImg(pd);
		if(Integer.parseInt(ob.toString())>0){
			obj.put("statusCode", 200);   
		}else{
			obj.put("statusCode", 400);   
		} 
		return obj;
	} 
	 
	//批量删除
		@RequestMapping({ "/delImg" })
		@ResponseBody
		public String delImg() throws Exception{
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();  
			pd = getPageData();
			String id = pd.getString("ID");
			String[] ids= id.split(",");
			try {
				this.productsService.delete(ids);
			} catch (Exception e) { 
				e.printStackTrace();
			}  
			obj.put("statusCode", "200");
			obj.put("message", "操作成功"); 
			return obj.toString();
		}
		
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
} 