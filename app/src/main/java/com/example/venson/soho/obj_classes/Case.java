package com.example.venson.soho.obj_classes;

import java.io.Serializable;
import java.sql.Date;

public class Case implements Serializable {
	private int caseId;
	private String caseName;
	private Date caseRecruitStart, caseRecruitEnd;
	private int caseRecruitProgress;//Percent
	private String caseWorkDay;
	private Date caseLastEdit;
	private int caseMemberCount;//Number of members
	private int casePayMin,casePayMax;//Money range
	private int caseOwnerId;
	private String caseDes;//Description
	private String caseLocation;

	public Case(int caseId, String caseName, Date caseRecruitStart, Date caseRecruitEnd,
				int caseRecruitProgress, String caseWorkDay, Date caseLastEdit, int caseMemberCount,
				int casePayMin, int casePayMax, int caseOwnerId, String caseDes, String caseLocation) {
		this.caseId = caseId;
		this.caseName = caseName;
		this.caseRecruitStart = caseRecruitStart;
		this.caseRecruitEnd = caseRecruitEnd;
		this.caseRecruitProgress = caseRecruitProgress;
		this.caseWorkDay = caseWorkDay;
		this.caseLastEdit = caseLastEdit;
		this.caseMemberCount = caseMemberCount;
		this.casePayMin = casePayMin;
		this.casePayMax = casePayMax;
		this.caseOwnerId = caseOwnerId;
		this.caseDes = caseDes;
		this.caseLocation = caseLocation;
	}

	public Case(int caseId, String caseName, Date caseRecruitEnd,
				String caseWorkDay, int casePayMin, int casePayMax,
				String caseDes, String caseLocation) {
		this.caseId = caseId;
		this.caseName = caseName;
		this.caseRecruitEnd = caseRecruitEnd;
		this.caseWorkDay = caseWorkDay;
		this.casePayMin = casePayMin;
		this.casePayMax = casePayMax;
		this.caseDes = caseDes;
		this.caseLocation = caseLocation;
	}



	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Date getCaseRecruitStart() {
		return caseRecruitStart;
	}

	public void setCaseRecruitStart(Date caseRecruitStart) {
		this.caseRecruitStart = caseRecruitStart;
	}

	public Date getCaseRecruitEnd() {
		return caseRecruitEnd;
	}

	public void setCaseRecruitEnd(Date caseRecruitEnd) {
		this.caseRecruitEnd = caseRecruitEnd;
	}

	public int getCaseRecruitProgress() {
		return caseRecruitProgress;
	}

	public void setCaseRecruitProgress(int caseRecruitProgress) {
		this.caseRecruitProgress = caseRecruitProgress;
	}

	public String getCaseWorkDay() {
		return caseWorkDay;
	}

	public void setCaseWorkDay(String caseWorkDay) {
		this.caseWorkDay = caseWorkDay;
	}

	public Date getCaseLastEdit() {
		return caseLastEdit;
	}

	public void setCaseLastEdit(Date caseLastEdit) {
		this.caseLastEdit = caseLastEdit;
	}

	public int getCaseMemberCount() {
		return caseMemberCount;
	}

	public void setCaseMemberCount(int caseMemberCount) {
		this.caseMemberCount = caseMemberCount;
	}

	public int getCasePayMin() {
		return casePayMin;
	}

	public void setCasePayMin(int casePayMin) {
		this.casePayMin = casePayMin;
	}

	public int getCasePayMax() {
		return casePayMax;
	}

	public void setCasePayMax(int casePayMax) {
		this.casePayMax = casePayMax;
	}

	public int getCaseOwnerId() {
		return caseOwnerId;
	}

	public void setCaseOwnerId(int caseOwnerId) {
		this.caseOwnerId = caseOwnerId;
	}

	public String getCaseDes() {
		return caseDes;
	}

	public void setCaseDes(String caseDes) {
		this.caseDes = caseDes;
	}

	public String getCaseLocation() {
		return caseLocation;
	}

	public void setCaseLocation(String caseLocation) {
		this.caseLocation = caseLocation;
	}
}
