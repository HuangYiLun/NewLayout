package com.example.venson.soho.obj_classes;

public class CaseCompany extends Company {

	private int caseCompanyId;
	private int caseId;

	// int companyId;
	// int uniformNumber;
	// String name;
	// double latitude;
	// double longitude;

	public CaseCompany(int id, int caseId, int companyId, int uniformNumber, String name, double latitude,
			double longitude) {

		super(companyId, uniformNumber, name, latitude, longitude);
		this.caseCompanyId = id;
		this.caseId = caseId;
	}

	public int getCaseCompanyId() {
		return caseCompanyId;
	}

	public void setCaseCompanyId(int caseCompanyid) {
		this.caseCompanyId = caseCompanyid;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	

}
