package com.example.venson.soho.obj_classes;

public enum Category {

	INSTRUMENT("Instrument", 1), WEB_DEVELOPEMENT("Web Developement", 2), GRAPHIC_DESIGN("Graphic Design",
			3), SOFTWARE("Software", 4), SALES("Sales", 5), SUPPORTIVE("Supportive", 6);

	private String CategoryName;
	private int CategoryId;

	Category(String name, int id) {
		this.CategoryName = name;
		this.CategoryId = id;
	}

	public static Category getCategoryById(int id) {

		switch (id) {
		case 1:
			return Category.INSTRUMENT; //文書
		case 2:
			return Category.WEB_DEVELOPEMENT; // 網頁設計
		case 3:
			return Category.GRAPHIC_DESIGN; // 美工設
		case 4:
			return Category.SOFTWARE; //軟體
		case 5:
			return Category.SALES; //銷售
		case 6:
			return Category.SUPPORTIVE;  //志工
		default:
			return null;

		}

	}

	public String getName() {
		return CategoryName;
	}

	public int getId() {
		return CategoryId;
	}
}
