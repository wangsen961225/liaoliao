package com.liaoliao.util.hqlpage;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Page {
	private int pageCount;// 总页数
	private int pageSize;// 每页记录数
	private int currentPage;// 当前页数
	private int firstRecord; // 从第几条开始
	private int recordCount; // 总记录数
	private String hql;// 查询的hql
	private List<Object> params;// 查询的条件
	private List list = new ArrayList();// 查询的结果
	private String url;// 查询的条件
	private String pageStr;// 分页的标签

	public String getPageStr() {// 用来生成翻页的工具条
		String pageCount_ = "&pageCount=" + pageCount;
		String currentPage_ = "&currentPage=";
		String url_ = url + pageCount_ + currentPage_;

		StringBuffer sb = new StringBuffer();
		if (pageCount < currentPage)
			return null;
		if (currentPage == 1) {
			sb.append("<span class='disabled'>&lt;&lt;首页</span>&nbsp;<span class='disabled'>&nbsp;&lt;&lt;上一页</span>");
		} else {
			sb.append("<a href='" + url_ + "1'>&lt;&lt;首页</a><a href='" + url_ + (currentPage - 1)
					+ "'>&nbsp;&lt;&lt;上一页</a>");
		}
		if ((currentPage - 5) > 0)
			sb.append("<a href='" + url_ + (currentPage - 5) + "'>&nbsp;" + (currentPage - 5) + "</a>");
		if ((currentPage - 4) > 0)
			sb.append("<a href='" + url_ + (currentPage - 4) + "'>&nbsp;" + (currentPage - 4) + "</a>");
		if ((currentPage - 3) > 0)
			sb.append("<a href='" + url_ + (currentPage - 3) + "'>&nbsp;" + (currentPage - 3) + "</a>");
		if ((currentPage - 2) > 0)
			sb.append("<a href='" + url_ + (currentPage - 2) + "'>&nbsp;" + (currentPage - 2) + "</a>");
		if ((currentPage - 1) > 0)
			sb.append("<a href='" + url_ + (currentPage - 1) + "'>&nbsp;" + (currentPage - 1) + "</a>");

		sb.append("<span class='current'>&nbsp;" + currentPage + "</span>");

		if ((pageCount - currentPage) > 0)
			sb.append("<a href='" + url_ + (currentPage + 1) + "'>&nbsp;" + (currentPage + 1) + "</a>");
		if ((pageCount - currentPage) > 1)
			sb.append("<a href='" + url_ + (currentPage + 2) + "'>&nbsp;" + (currentPage + 2) + "</a>");
		if ((pageCount - currentPage) > 2)
			sb.append("<a href='" + url_ + (currentPage + 3) + "'>&nbsp;" + (currentPage + 3) + "</a>");
		if ((pageCount - currentPage) > 3)
			sb.append("<a href='" + url_ + (currentPage + 4) + "'>&nbsp;" + (currentPage + 4) + "</a>");
		if ((pageCount - currentPage) > 4)
			sb.append("<a href='" + url_ + (currentPage + 5) + "'>&nbsp;" + (currentPage + 5) + "</a>");

		if (pageCount == currentPage) {
			sb.append("<span class='disabled'>&nbsp;下一页&gt;&gt;</span><span class='disabled'>&nbsp;末页&gt;&gt;</span>");
		} else {
			sb.append("<a href='" + url_ + (currentPage + 1) + "'>&nbsp;下一页&gt;&gt;</a><a href='" + url_ + (pageCount)
					+ "'>&nbsp;末页&gt;&gt;</a>");
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {

		return (this.currentPage == 0) ? 1 : this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	// 当前开始位置为（当前页-1）*每页记录数
	public int getFirstRecord() {
		currentPage = (currentPage == 0) ? 1 : currentPage;
		return (currentPage - 1) * pageSize;
	}

	public void setFirstRecord(int firstRecord) {
		this.firstRecord = firstRecord;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public List<Object> getListQuery() {
		return params;
	}

	public void setListQuery(List<Object> params) {
		this.params = params;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}