package com.example.venson.soho.obj_classes;

import com.example.venson.soho.obj_classes.Category;

public class Tag {
	private int id;
	private int updatorId;
	private String name;
	private Category c;

	public Tag(int id, String name, Category c) {
		super();
		this.id = id;
		this.name = name;
		this.c = c;
	}

	public int getTagId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUpdatorId() {
		return updatorId;
	}

	public void setUpdatorId(int updatorId) {
		this.updatorId = updatorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getC() {
		return c;
	}

	public void setC(Category c) {
		this.c = c;
	}
	

}
