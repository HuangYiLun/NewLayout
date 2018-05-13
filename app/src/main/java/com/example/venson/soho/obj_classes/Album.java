package com.example.venson.soho.obj_classes;

import java.io.Serializable;
import java.sql.Date;

public class Album implements Serializable {
	private int albumId;
	private String albumName;
	private int userId;
	private Date createDate;
	private Date updateDate;
	private String albumDes;
	
	public Album(int albumId, String albumName, int userId, Date createDate, Date updateDate, String albumDes) {
		super();
		this.albumId = albumId;
		this.albumName = albumName;
		this.userId = userId;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.albumDes = albumDes;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAlbumDes() {
		return albumDes;
	}

	public void setAlbumDes(String albumDes) {
		this.albumDes = albumDes;
	}
	
	
	
	

}
