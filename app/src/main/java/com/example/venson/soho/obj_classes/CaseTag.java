package com.example.venson.soho.obj_classes;

import java.io.Serializable;

public class CaseTag extends Tag implements Serializable{

	private int caseTagId;
	private int caseId;

	public CaseTag(int id, String name, Category c) {
		super(id, name, c);	
	}

	public CaseTag( int caseTagId, int caseId,int id, String name, Category c) {
		super(id, name, c);
		this.caseTagId = caseTagId;
		this.caseId = caseId;
	}

	public int getCaseTagId() {
		return caseTagId;
	}

	public void setCaseTagId(int caseTagId) {
		this.caseTagId = caseTagId;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	
	
}
