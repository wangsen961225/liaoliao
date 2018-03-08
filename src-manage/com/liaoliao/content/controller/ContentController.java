package com.liaoliao.content.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.ContentKindService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping(value = "/content")
public class ContentController {

	@Autowired
	private VideoService videoService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ContentKindService contentKindService;

	private Integer page = 1;

	/**
	 * 进入抓取文章页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toGetArticle")
	public String toGetArticle() {
		return "/content/getArticle";
	}

	/**
	 * 进入抓取视频页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toGetVideo")
	public String toGetVideo() {
		return "/content/getVideo";
	}

	/**
	 * 进入文章列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toArticle")
	public String toArticle(HttpServletRequest request, Integer pageNo, Integer searchType) {
		Map<String, Object> map = new HashMap<>();
		String condition = "";
		if (searchType != null && !("".equals(searchType))) {
			map.put("searchType", searchType);
			condition += "&searchType=" + searchType;
		}

		if (pageNo == null) {
			pageNo = page;
		} else {
			pageNo = pageNo > 1 ? pageNo : page;
		}
		Integer count = null;
		count = articleService.findCount(map);
		if (count == null) {
			request.setAttribute("count", 0);
			request.getSession().setAttribute("count", 0);
		} else {
			request.setAttribute("count", (int) (Math.ceil((double) count / StaticKey.ArticlePageNum)));
			request.getSession().setAttribute("count", (int) (Math.ceil((double) count / StaticKey.ArticlePageNum)));
		}
		request.setAttribute("url", "/content/toArticle");
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("condition", condition);
		request.setAttribute("searchType", searchType);

		List<Article> list = articleService.findAll(pageNo, map);
		request.setAttribute("list", list);
		return "/content/articleList";
	}

	/**
	 * 进入视频列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toVideo")
	public String toVideo(HttpServletRequest request, Integer pageNo, Integer searchType) {
		Map<String, Object> map = new HashMap<>();
		String condition = "";
		if (searchType != null && !("".equals(searchType))) {
			map.put("searchType", searchType);
			condition += "&searchType=" + searchType;
		}

		if (pageNo == null) {
			pageNo = page;
		} else {
			pageNo = pageNo > 1 ? pageNo : page;
		}
		Integer count = null;
		count = videoService.findCount(map);
		if (count == null) {
			request.setAttribute("count", 0);
			request.getSession().setAttribute("count", 0);
		} else {
			request.setAttribute("count", (int) (Math.ceil((double) count / StaticKey.ArticlePageNum)));
			request.getSession().setAttribute("count", (int) (Math.ceil((double) count / StaticKey.ArticlePageNum)));
		}
		// request.setAttribute("condition", "");
		request.setAttribute("url", "/content/toVideo");
		request.setAttribute("condition", condition);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("searchType", searchType);

		List<Video> list = videoService.findAll(pageNo, map);
		request.setAttribute("list", list);
		return "/content/videoList";
	}

	/**
	 * 修改视频
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/modifyVideo")
	public String modifyVideo(HttpServletRequest request, Integer id) {
		return null;
	}

	/**
	 * 删除视频
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/deleteVideo")
	@ResponseBody
	public Map<String, Object> deleteVideo(HttpServletRequest request, Integer id) {
		Map<String, Object> map = new HashMap<>();
		Video video = videoService.findById(id);
		if (video == null) {
			map.put("msg", "视频不存在");
			map.put("code", 404);
			return map;
		}
		videoService.deleteVideo(video);
		map.put("msg", "删除成功!");
		map.put("code", 200);
		return map;
	}

	/**
	 * 封印视频..
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/banVideo")
	@ResponseBody
	public Map<String, Object> banVideo(HttpServletRequest request, Integer id) {
		Map<String, Object> map = new HashMap<>();
		Video video = videoService.findById(id);
		if (video == null) {
			map.put("msg", "视频不存在");
			map.put("code", 404);
			return map;
		}
		video.setStatus(StaticKey.VideoStatusFalse);
		videoService.updateVideo(video);
		map.put("msg", "操作成功!");
		map.put("code", 200);
		return map;
	}

	/**
	 * 推荐视频..
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/tuiVideo")
	@ResponseBody
	public Map<String, Object> tuiVideo(HttpServletRequest request, Integer id) {
		Map<String, Object> map = new HashMap<>();
		Video video = videoService.findById(id);
		if (video == null) {
			map.put("msg", "视频不存在");
			map.put("code", 404);
			return map;
		}
		if (StaticKey.ArticleStatusTui.equals(video.getStatus())) {
			map.put("msg", "请勿重复推荐.");
			map.put("code", 200);
			return map;
		}
		video.setStatus(StaticKey.VideoStatusTui);
		videoService.updateVideo(video);
		map.put("msg", "操作成功!");
		map.put("code", 200);
		return map;
	}

	/**
	 * 修改文章
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/modifyArticle")
	public String modifyArticle(HttpServletRequest request, Integer id) {
		return null;
	}

	/**
	 * 删除文章
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/deleteArticle")
	@ResponseBody
	public Map<String, Object> deleteArticle(HttpServletRequest request, Integer id) {
		Map<String, Object> map = new HashMap<>();
		Article article = articleService.findById(id);
		if (article == null) {
			map.put("msg", "文章不存在");
			map.put("code", 404);
			return map;
		}
		articleService.deleteArticle(article);
		map.put("msg", "删除成功!");
		map.put("code", 200);
		return map;
	}

	/**
	 * 封印文章..
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/banArticle")
	@ResponseBody
	public Map<String, Object> banArticle(HttpServletRequest request, Integer id) {
		Map<String, Object> map = new HashMap<>();
		Article article = articleService.findById(id);
		if (article == null) {
			map.put("msg", "文章不存在");
			map.put("code", 404);
			return map;
		}
		article.setStatus(StaticKey.ArticleStatusFalse);
		articleService.updateArticle(article);
		map.put("msg", "操作成功!");
		map.put("code", 200);
		return map;
	}

	/**
	 * 封印文章..
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/tuiArticle")
	@ResponseBody
	public Map<String, Object> tuiArticle(HttpServletRequest request, Integer id) {
		System.out.println("故事的故事大概啥的");
		Map<String, Object> map = new HashMap<>();
		Article article = articleService.findById(id);
		if (article == null) {
			map.put("msg", "文章不存在");
			map.put("code", 404);
			return map;
		}
		if (StaticKey.ArticleStatusTui.equals(article.getStatus())) {
			map.put("msg", "请勿重复推荐.");
			map.put("code", 200);
			return map;
		}
		article.setStatus(StaticKey.ArticleStatusTui);
		articleService.updateArticle(article);
		map.put("msg", "操作成功!");
		map.put("code", 200);
		return map;
	}

}
