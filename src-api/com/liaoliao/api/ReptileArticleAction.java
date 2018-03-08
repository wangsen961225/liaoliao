package com.liaoliao.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.KeyWords;
import com.liaoliao.content.entity.ReptileArticle;
import com.liaoliao.content.service.ContentKindService;
import com.liaoliao.content.service.KeyWordsService;
import com.liaoliao.content.service.ReptileArticleService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.HanlpKeyWords;
import com.liaoliao.util.RandomKit;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping(value="/api")
public class ReptileArticleAction {
	
	@Autowired
	private ContentKindService contentKindService;
	
	@Autowired
	private ReptileArticleService reptileArticleService;
	
	@Autowired
	private  KeyWordsService keyWordsService;
	
	@RequestMapping(value="/reptileArticleByArticle")
	@ResponseBody
	public Map<String,Object> reptileArticleByArticle(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		Article article = null;
		List<ReptileArticle> list=reptileArticleService.list();
		if(list==null){
			map.put("msg", "暂无可加载的文章或视频.");
			map.put("code", "1");
			return map;
		}
		int n=0;
		for (ReptileArticle reptileArticle : list) {
			//处理视频
			/*Video video=new Video();
			if(reptileArticle.getNewsType().equals("video")){
				
			}*/
			article=new Article();
			//处理文章
			if(reptileArticle.getNewsType().equals("article")){
				String detail=reptileArticle.getDetail();
				/*String regEx = "[<link rel=alternate.]";  
				Pattern  p= Pattern.compile(regEx);  
				Matcher md = p.matcher(detail);  
				md.find();
				md.group();
				System.out.println(md.group()+"------------------------");*/
				String description=StringUtils.substringBetween(detail, "name=description content=",">");
				String content=StringUtils.substringBetween(detail, "content: '","groupId:");
				content=content.trim();
				content=content.substring(0, content.length()-2);
				content=StringEscapeUtils.unescapeHtml(content);
			        String img = "";  
			        Pattern p_image;  
			        Matcher m_image; 
			        String regEx_img = "[<img<image.]*src\\s*=\\s*(.*?)[^>]*?>";  
			        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);  
			        m_image = p_image.matcher(content);  
			        String mString="{\"imgList\":[";
			        String  mm="";
			        String  imgListUrl=null;
			        while (m_image.find()) {
			            // 得到<img />数据  
			            img = m_image.group();
			            // 匹配<img>中的src数据  
			            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img); 
			            while (m.find()) {  
			            	if(m.group(1)!=null){
			            		mm+="{\"url\":\""+m.group(1)+"\"},";
			            	}
			            } 
			           imgListUrl=mString+mm.substring(0, mm.length()-1)+"]}"; 
			        }  
			        article.setImgList(imgListUrl);
			        String imgUrl=null;
			        if(imgListUrl!=null){
			        	 String[] imgsUrl=imgListUrl.split(",");
				          imgUrl=StringUtils.substringBetween(imgsUrl[0], "\"url\":\"","\"}");
			        }
			        article.setImgUrl(imgUrl);
				String lable=reptileArticle.getLabel();
				KeyWords keyWords=null;
				List<String> lis=HanlpKeyWords.getMainIdea(lable);
				if(lis!=null&&lis.size()>0){
					for (String name : lis) {
						keyWords=keyWordsService.findById(null, name);
						if(keyWords!=null){
							keyWords.setFreq(keyWords.getFreq()+1);
							keyWordsService.updateMoble(keyWords);
							continue;
						}else{
						keyWords=new KeyWords();
						keyWords.setId(UUID.randomUUID().toString());
						keyWords.setAddDate(new Date());
						keyWords.setName(name);
						keyWords.setFreq(1);
						keyWordsService.add(keyWords);
						}
						
					}
				}
				Integer addDate=reptileArticle.getCreateTime();
				Date date=new Date(addDate.longValue());
				article.setAddTime(date);
				String title=reptileArticle.getTitle();
				title=CommonUtil.emojiFilter(title);
				article.setTitle(title);
				article.setDescription(description);
				article.setContent(content);
				int max=3000;
		        int min=300;
		        Random random = new Random();
		        int[] ints=new int[3];
		        for(int i=0;i<3;i++){
		        	ints[i]= random.nextInt(max)%(max-min+1) + min;
		        }
		       int reading = random.nextInt(50000)%(50000-4000+1) + 4000;
				article.setCommentCount(ints[1]);
				article.setKeyId(RandomKit.keyId());
				article.setLikingCount(ints[0]);
				article.setReadingCount(reading);
				article.setStatus(StaticKey.ArticleStatusLoding);
				article.setSendingCount(ints[2]);
				article.setType(-1);
				article.setSourceId(-1);
				article.setContentKind(contentKindService.findByName("头条"));
				n++;
				reptileArticle.setType(-1);
				reptileArticleService.updaeMoble(reptileArticle);
				//articleService.saveArticle(article);
			}
		}
		map.put("msg", "共添加:"+n);
		map.put("code", "0");
		return map;
		
	}
	
	
	
}

