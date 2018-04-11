package com.sgj.web.enums;

public enum PlarformEnum {
	
	PLARFORM_BAZAAR("芭莎"),
	PLARFORM_COSMOPOLITAN("COSMOPOLITAN"),
	PLARFORM_ELLECHINA("ELLE"),
	PLARFORM_ENTQQ("腾讯娱乐"),
	PLARFORM_FASHIONQQ("腾讯时尚"),
	PLARFORM_GQ("GQ"),
	PLARFORM_HAIBAO("海报时尚"),
	PLARFORM_JIEMIAN("界面"),
	PLARFORM_MTIME("时光"),
	PLARFORM_VOGUE("VOGUE时尚"); 
	
	private String plarform;

	public String getPlarform() {
		return plarform;
	}

	public void setPlarform(String plarform) {
		this.plarform = plarform;
	}

	private PlarformEnum(String plarform) {
		this.plarform = plarform;
	}

	
	
}
