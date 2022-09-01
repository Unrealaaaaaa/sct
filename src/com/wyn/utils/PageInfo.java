package com.wyn.utils;

import java.sql.SQLException;
import java.util.List;

import com.wyn.dao.DaoFactory;

/**
 * 通用的分页工具类
 * @author 86150
 *
 */
public class PageInfo<T> {
	
	private Integer pageNo = 1;
	private int pageSize = 10;
	private Long totalCount;
	private List<T> list;
	
	public PageInfo(Integer pageNo) {
		super();
		if(pageNo == null) {
			this.pageNo = 1;
		}else {
			this.pageNo = pageNo;
		}
	}

	//获取总页数
	public long getTotalPage() {
		Long totalPage = totalCount/pageSize;
		if(totalCount%pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}
	
	//下一页
	public int getNextPage() {
		Integer nextPage = 0;
		if (nextPage < this.getTotalPage()) {
			nextPage = pageNo + 1;
		}else {
			nextPage = pageNo; 
		}
		return nextPage;
	}
	
	//上一页
	public int getPrePage() {
		Integer prePage = 0;
		if (pageNo > 1 ) {
			prePage = pageNo - 1;
		}else {
			prePage = pageNo; 
		}
		return prePage;
	}
	
	//用来判断是否是第一页
	public boolean isFirstPage() {
		boolean isFirstPage = false;
		if(pageNo > 1) {
			isFirstPage = false;
		}else {
			isFirstPage = true;
		}
		return isFirstPage;
	}

	//用来判断是否是最后一页
	public boolean isLastPage() {
		boolean isLastPage = false;
		if(pageNo < this.getTotalPage()) {
			isLastPage = false;
		}else {
			isLastPage = true;
		}
		return isLastPage;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	
			
}
