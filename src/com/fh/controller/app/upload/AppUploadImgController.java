package com.fh.controller.app.upload;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;
import com.fh.controller.base.BaseController;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/appUploadImg"})
public class AppUploadImgController extends BaseController {  
	
	//上传文件存放路径
	public static String clientBasePath = fileConfig.getString("clientBasePath");
	
	//上传文件存放路径
	public static String imgBasePath = fileConfig.getString("imgBasePath");
	
	//上传文件存放路径
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	 
	 @RequestMapping(value={"/imgBase64"}) 
	 @ResponseBody
	 public PageData imgBase64() throws Exception{ 
		PageData _result=new PageData();
		PageData pa=getPageData(); 
		String img=pa.getString("img"); 
         
    	if (img == null){//图像数据为空  
    	    _result = AppUtil.ss(null, "90", "失败","false",null);
		}else{
			 
			BASE64Decoder decoder = new BASE64Decoder();  
			//Base64解码  
            byte[] b = decoder.decodeBuffer(img);  
            for(int i=0;i<b.length;++i){  
                if(b[i]<0){//调整异常数据
                    b[i]+=256;  
                }  
            }  
            //生成png图片   
            String imgName=get32UUID()+".png"; 
            String imgFilePath =clientBasePath+imgName; //新生成的图片 
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();
            _result = AppUtil.ss(imgName, "01", "成功","success",null);
		 } 
         return _result;
	}  
	 
	 @RequestMapping(value={"/imgBase64_2"}) 
	 @ResponseBody
	 public PageData imgBase64_2() throws Exception{ 
		PageData _result=new PageData();
		PageData pa=getPageData(); 
		String img=pa.getString("img"); 
         
    	if (img == null){//图像数据为空  
    	    _result = AppUtil.ss(null, "90", "失败","false",null);
		}else{
			 
			BASE64Decoder decoder = new BASE64Decoder();  
			//Base64解码  
            byte[] b = decoder.decodeBuffer(img);  
            for(int i=0;i<b.length;++i){  
                if(b[i]<0){//调整异常数据
                    b[i]+=256;  
                }  
            }  
            //生成png图片   
            String imgName=get32UUID()+".png"; 
            String imgFilePath =imgBasePath+imgName; //新生成的图片 
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();
            _result = AppUtil.ss(imgName, "01", "成功","success",null);
		 } 
         return _result;
	} 
	 
	 @RequestMapping(value={"/serverBase64"}) 
	 @ResponseBody
	 public PageData serverBase64() throws Exception{ 
		PageData _result=new PageData();
		PageData pa=getPageData(); 
		String img=pa.getString("img"); 
         
    	if (img == null){//图像数据为空  
    	    _result = AppUtil.ss(null, "90", "失败","false",null);
		}else{
			 
			BASE64Decoder decoder = new BASE64Decoder();  
			//Base64解码  
            byte[] b = decoder.decodeBuffer(img);  
            for(int i=0;i<b.length;++i){  
                if(b[i]<0){//调整异常数据
                    b[i]+=256;  
                }  
            }  
            //生成png图片   
            String imgName=get32UUID()+".png"; 
            String imgFilePath =serverBasePath+imgName; //新生成的图片 
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();
            _result = AppUtil.ss(imgName, "01", "成功","success",null);
		 } 
         return _result;
	 } 
	 
	 @RequestMapping(value={"/audioBase64_wav"}) 
	 @ResponseBody
	 public PageData audioBase64_wav() throws Exception{ 
		PageData _result=new PageData();
		PageData pa=getPageData(); 
		String img=pa.getString("img"); 
         
    	if (img == null){//图像数据为空  
    	    _result = AppUtil.ss(null, "90", "失败","false",null);
		}else{
			 
			BASE64Decoder decoder = new BASE64Decoder();  
			//Base64解码  
            byte[] b = decoder.decodeBuffer(img);  
            for(int i=0;i<b.length;++i){  
                if(b[i]<0){//调整异常数据
                    b[i]+=256;  
                }  
            }  
            //生成png图片   
            String imgName=get32UUID()+".wav"; 
            String imgFilePath =imgBasePath+imgName; //新生成的图片 
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();
            _result = AppUtil.ss(imgName, "01", "成功","success",null);
		 } 
         return _result;
	}
	 
	 @RequestMapping(value={"/audioBase64"}) 
	 @ResponseBody
	 public PageData audioBase64() throws Exception{ 
		PageData _result=new PageData();
		PageData pa=getPageData(); 
		String img=pa.getString("img"); 
         
    	if (img == null){//图像数据为空  
    	    _result = AppUtil.ss(null, "90", "失败","false",null);
		}else{
			 
			BASE64Decoder decoder = new BASE64Decoder();  
			//Base64解码  
            byte[] b = decoder.decodeBuffer(img);  
            for(int i=0;i<b.length;++i){  
                if(b[i]<0){//调整异常数据
                    b[i]+=256;  
                }  
            }  
            //生成png图片   
            String imgName=get32UUID()+".amr"; 
            String imgFilePath =imgBasePath+imgName; //新生成的图片 
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();
            _result = AppUtil.ss(imgName, "01", "成功","success",null);
		 } 
         return _result;
	}
}
