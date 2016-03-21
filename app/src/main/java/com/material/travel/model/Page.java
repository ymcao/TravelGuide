package com.material.travel.model;

import java.io.Serializable;

public class Page implements Serializable{
	
	private static final long serialVersionUID = 3711514977568416935L;
	public int currentPage;  // 当前页码数
	public int pageSize;     // 每页个数
	public int recordNum;    // 当前页记录个数
	public int totalPageNum; // 总共记录个数
	public int count;        // 总数
	public boolean hasPre;
	public boolean hasNext;
}
