var serverPath="http://jianlian.shanghai-cu.com/jianlian/";
var serverBasePath="http://jianlian.shanghai-cu.com/jianlian/uploadImg/server/";
var uploadImg = uploadImg || {};
uploadImg.prototype={
	maxH:600,
	maxW:600,
	uploadType:1,//0、ajax；1、form。
	uploadPath:serverPath+"appUploadImg/serverBase64.do",//上传接口
	callback:function(){},
	
	//检测数据
	isNull:function(ele){
		if( ele != null && ele != undefined )
			return false;
		else 
		    return true;
	},
	
    init:function(param){
    	if( param && !uploadImg.prototype.isNull(param.maxH)){
    		uploadImg.prototype.maxH = param.maxH;
    	}
    	if( param && !uploadImg.prototype.isNull(param.maxW) ){
    		uploadImg.prototype.maxW = param.maxW;
    	}
    	if( param && !uploadImg.prototype.isNull(param.uploadPath) ){
    		uploadImg.prototype.uploadPath = param.uploadPath;
    	}
    	if( param && !uploadImg.prototype.isNull(param.callback) ){
    		uploadImg.prototype.callback = param.callback;
    	}
    	if( param && !uploadImg.prototype.isNull(param.imgPath) ){
    		uploadImg.prototype.uploadHead(param.imgPath,param.flag);
    	}
    },
	
	//上传头像图片
	uploadHead:function(imgPath,flag) { 
		var Orientation = null;  
		console.log("imgPath = " + imgPath);
		var image = new Image();
		image.setAttribute('crossOrigin', 'anonymous');
		image.src = imgPath;  
	    image.onload = function() { 
	    	if(flag==0){
	    		//获取照片方向角属性，用户旋转控制 
		        EXIF.getData(image, function() {  
		            EXIF.getAllTags(this);   
		            Orientation = EXIF.getTag(this, 'Orientation');  
		            var imgData = uploadImg.prototype.getBase64ImageConvert(Orientation,image);
		            if( uploadImg.prototype.uploadType == 0 ){
		            	uploadImg.prototype.$ajax(imgData);
		            }else{
		            	uploadImg.prototype.$form(imgData);
		            }
		        }); 
	    	}else{
	    		var imgData = uploadImg.prototype.getBase64Image(image); 
	    		if( uploadImg.prototype.uploadType == 0 ){
	            	uploadImg.prototype.$ajax(imgData);
	            }else{
	            	uploadImg.prototype.$form(imgData);
	            }
	    	} 
		}; 
	}, 

	getBase64ImageConvert:function(Orientation,img) {  
		var canvas = document.createElement("canvas"); 
		var width = img.width;  
        var height = img.height;   
        console.log("getBase64ImageConvert:width="+width+"<>height="+height);
        // 宽度等比例缩放 *= 
        var MAX_HEIGHT = uploadImg.prototype.maxH;
        var MAX_WIDTH = uploadImg.prototype.maxW;
        if( height > width && height > MAX_HEIGHT ) { 
			width *= MAX_HEIGHT/height; 
			height = MAX_HEIGHT; 
		}else if( width > height && width > MAX_WIDTH ){
			height *= MAX_WIDTH/width; 
			width = MAX_WIDTH; 
		}

		canvas.width = width;   /*设置新的图片的宽度*/
		canvas.height = height; /*设置新的图片的长度*/
		var ctx = canvas.getContext("2d"); 
		ctx.drawImage(img, 0, 0, width, height);
        var base64 = null;  
        //修复ios 
       if (navigator.userAgent.match(/iphone/i)) { 
             console.log('iphone'); 
            //如果方向角不为1，都需要进行旋转  
            if(Orientation != "" && Orientation != 1){  
                var degree = 1 * 90 * Math.PI / 180;
                ctx.rotate(degree); 
                ctx.drawImage(img, 0, -width,height,width); 
            } 
            base64 = canvas.toDataURL("image/png", 0.8); 
        }else if (navigator.userAgent.match(/Android/i)) {// 修复android 
            base64 = canvas.toDataURL("image/png", 0.8); 
        }else{ 
            base64 = canvas.toDataURL("image/png", 0.8); 
        } 
        return base64.replace("data:image/png;base64,", ""); 
	},  
   			
	getBase64Image:function(img) { 
		var canvas = document.createElement("canvas");
		var width = img.width;
		var height = img.height; 
		console.log("getBase64Image:width="+width+"<>height="+height);
        // 宽度等比例缩放 *= 
        var MAX_HEIGHT = uploadImg.prototype.maxH;
        var MAX_WIDTH = uploadImg.prototype.maxW;
        if( height > width && height > MAX_HEIGHT ) { 
			width *= MAX_HEIGHT/height; 
			height = MAX_HEIGHT; 
		}else if( width > height && width > MAX_WIDTH ){
			height *= MAX_WIDTH/width; 
			width = MAX_WIDTH; 
		}
		
		canvas.width = width;   //设置新的图片的宽度
		canvas.height = height; //设置新的图片的长度
		var ctx = canvas.getContext("2d");
		ctx.drawImage(img, 0, 0, width, height); //绘图
		var dataURL = canvas.toDataURL("image/png", 0.8); 
		return dataURL.replace("data:image/png;base64,", "");
	},
	
	$ajax:function(imgData){
		$.ajax({
    		url: uploadImg.prototype.uploadPath,
    		async: false,
    		data: {"img":imgData},
    		type: "post",
    		dataType: 'json',
    		success: function(data){  
				console.log("上传成功:"+JSON.stringify(data));
				if( data && data.errcode == "01" ){
					uploadImg.prototype.callback(data.data);
				}else{
					alert("上传失败！");
				}
			},
    		error: function(XMLHttpRequest, textStatus, errorThrown) { 
    			console.error(textStatus);
    			console.error(XMLHttpRequest);
    			console.error("HTTP ERROR");
    			errorCB();
    		}
    	});
	},
	
	$form:function(img){
		var form = $('<form id="tempForm" method="POST" action="'+uploadImg.prototype.uploadPath+'" style="display:none;"><input type="text" name="img" value="'+img+'"></form>');
		$("body").append(form);
		//form.submit();
		
		//ajax form提交，需引入jquery.form.js
        var options  = {    
            url:uploadImg.prototype.uploadPath,    
            type:'post',    
            success:function(data){    
            	console.log("form上传成功:"+JSON.stringify(data));
				if( data && data.errcode == "01" ){
					uploadImg.prototype.callback(data.data);
				}else{
					alert("上传失败！");
				}
            }    
        };    
        form.ajaxSubmit(options);  
	}
		
}
//$(function(){
//  var my = uploadImg.prototype;
//  my.init();             
//});