package com.example.venson.soho.obj_classes;

public class CaseCategory {
	
	private int caseCategoryId;
	private int caseId;
	private Category c;
	
	public CaseCategory(int caseCategoryId, int caseId, Category c) {
		super();
		this.caseCategoryId = caseCategoryId;
		this.caseId = caseId;
		this.c = c;
	}

	public int getCaseCategoryId() {
		return caseCategoryId;
	}

	public void setCaseCategoryId(int caseCategoryId) {
		this.caseCategoryId = caseCategoryId;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public Category getC() {
		return c;
	}

	public void setC(Category c) {
		this.c = c;
	}
	
	
	
	
	
	

}
