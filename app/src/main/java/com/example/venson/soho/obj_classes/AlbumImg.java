package com.example.venson.soho.obj_classes;

import java.io.Serializable;
import java.sql.Date;

public class AlbumImg implements Serializable{
	
	private int imgId;
	private int albumId;
	private String imgName;
	private String imgPath;
	private Date imgCreateDate;
	private Date imgLastEditDate;
	private String imgDes;
	
	public AlbumImg(int imgId, int albumId, String imgName, String imgPath, Date imgCreateDate, Date imgLastEditDate,
			String imgDes) {
		super();
		this.imgId = imgId;
		this.albumId = albumId;
		this.imgName = imgName;
		this.imgPath = imgPath;
		this.imgCreateDate = imgCreateDate;
		this.imgLastEditDate = imgLastEditDate;
		this.imgDes = imgDes;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Date getImgCreateDate() {
		return imgCreateDate;
	}

	public void setImgCreateDate(Date imgCreateDate) {
		this.imgCreateDate = imgCreateDate;
	}

	public Date getImgLastEditDate() {
		return imgLastEditDate;
	}

	public void setImgLastEditDate(Date imgLastEditDate) {
		this.imgLastEditDate = imgLastEditDate;
	}

	public String getImgDes() {
		return imgDes;
	}

	public void setImgDes(String imgDes) {
		this.imgDes = imgDes;
	}
	
	

}
