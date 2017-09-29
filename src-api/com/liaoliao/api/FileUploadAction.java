package com.liaoliao.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.user.service.UserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/api")
public class FileUploadAction  {
	
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE=16*1024;
//	private File img;
//	private String imgFileName;
//	private String imgContentType;
	private PrintWriter writer = null;
	
	private Integer userID;

	/**
	 * 上传头像：
	 * 手机客户端使用
	 * @return
	 * @throws IOException 
	 */
	
	public void uploadHead(HttpServletResponse response,HttpServletRequest request,File img,String imgFileName,String imgContentType) throws IOException{
		JSONObject json;
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");  
		writer = response.getWriter();
		json = new JSONObject();
		System.out.println("111111111111111111111");
//	        //接受文件流并命名
	        String imageFileName = imgFileName; 
	   			String fileContentType = imgContentType;
	   			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
	   				imageFileName = userID +  ".jpg";
	   			else if(fileContentType.equals("image/gif"))
	   				imageFileName = userID +  ".gif";
	   			else if(fileContentType.equals("image/png"))
	   				imageFileName = userID +  ".png";
	   			else {
	   				json.put("msg", "图片格式不支持");
	   				json.put("code", 500);
	   				writer.print(json);
	   				writer.flush();
	   				writer.close();
	   				return ;
	   			}
			File upload=new File(request.getServletContext().getRealPath("upload"));
			if(!upload.exists()){
				upload.mkdir();
			}
			//生成头像目录
			File dataName=new File(request.getServletContext().getRealPath("upload")+"\\"+"head");
			if(!dataName.exists()){
				dataName.mkdir();
			}
			//保存到服务器中
			String dstPath=dataName+"\\"+imageFileName;
			File dstFile=new File(dstPath);
			copy(img,dstFile);
			
			String url="/upload/head/"+imageFileName+"?"+System.currentTimeMillis();
			
			json.put("headUrl", url);
			json.put("msg", "success");
			json.put("code", 200);
			writer.print(json);
			writer.flush();
			writer.close();
		}
	
	
	//读取图片放入服务器中：
	private static void copy(File src,File dst){
		InputStream in=null;
		OutputStream out=null;
		try{
			in=new BufferedInputStream(new FileInputStream(src),BUFFER_SIZE);
			out=new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
			byte[] buffer=new byte[BUFFER_SIZE];
			int len=0;
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=in){
				try{
					in.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(null!=out){
				try{
					out.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
}
