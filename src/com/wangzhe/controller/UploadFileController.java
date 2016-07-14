package com.wangzhe.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.wangzhe.util.Config;

@EnableWebMvc
@Controller
public class UploadFileController extends BaseController{
	private static final String FILE_PATH;
	static{
		if(Config.isDebug()){
			FILE_PATH = "D:\\upload\\";
		}else {
			FILE_PATH = "/usr/wangzhe/upload/";
		}
	}
	
	
	@RequestMapping(path = "/uploadAvatar")
	public void uploadAvatar(HttpServletRequest request) throws IllegalStateException, IOException{
		 //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String newFileName = getNewFileName(file.getOriginalFilename()); 
                        //定义上传路径  
                        String path = FILE_PATH + newFileName;  
                        File localFile = new File(path);  
                        file.transferTo(localFile);  
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }  
              
        }  
        
	}
	
	private Random random = new Random();
	
	private String getNewFileName(String oldFileName){
		String nowTime = System.currentTimeMillis() + "";
		int rand = 900 + random.nextInt() * 100;
		
		String newFileName;
		if(oldFileName.length() < 8){
			newFileName = oldFileName;
		}else{
			newFileName = oldFileName.substring(oldFileName.length() - 8, 8);
		}
		return nowTime.substring(nowTime.length() - 6) + "_" + rand + "_" + newFileName;
	}

}
