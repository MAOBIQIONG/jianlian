package com.fh.controller.system.upload;

import java.util.List; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;  
import net.sf.json.JSONObject; 
import com.fh.controller.base.BaseController; 
import com.fh.util.FileUpload;
import com.fh.util.PageData; 
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/upload" })
public class uploadController extends BaseController {  
	
	//上传文件存放路径
	public static String videosBasePath = fileConfig.getString("videosBasePath");
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	 
	@RequestMapping({ "/uploadImgs" }) 
	@ResponseBody
	public JSONObject uploadImgs(MultipartHttpServletRequest request)throws Exception{
		JSONObject obj = new JSONObject();
		PageData pd = new PageData();  
		//MultipartFile IMGS=request.getFile("IMGS");   
		List<MultipartFile> imgs=request.getFiles("IMG_PATH");
		int num=0;
		for(MultipartFile img:imgs){
			if ((img != null) && (!img.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(img,filePath,get32UUID());
				num++;
				pd.put("img"+num, fileName); 
			}else{
				num++;
			} 
		}
		if(num==imgs.size()){
			obj.put("imgs",pd);  
			obj.put("statusCode", 200);  
		}else{
			obj.put("statusCode",500);  
		} 
		return obj;
	} 
}

