package com.liaoliao.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;  


@Controller
@RequestMapping(value="/upload")
public class Upload {  
	
	@RequestMapping(path="/avatar")
	public String avatar (@RequestParam MultipartFile uploadFile, HttpServletRequest request) throws Exception{
	String realPath = request.getServletContext().getRealPath("upload");
	System.out.println(realPath);
	File dir = new File(realPath);
	
	if (!dir.exists()) {
	       dir.mkdir();
        } 
	uploadFile.transferTo(new File(realPath,uploadFile.getOriginalFilename()));
	return  "admin/upload";
	}  
  
}  

