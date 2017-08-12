package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum Language implements BasicEnum{
	
	ENGLISH(1, "en"),
	FRENCH(2, "fr"),
	SPANISH(3, "es"),
	ARABIC(4,"ar"),
	AWADHI(5,"hw"),
	AZERBAIJANI(6,"az"),
	BENGALI(7,"bn"),
	BHOJPURI(8,"bh"),
	BURMESE(9,"my"),
	CHINESE_CANTONESE(10,"cc"),
	CHINESE_GAN(11,"cg"),
	CHINESE_HAKKA(12,"ch"),
	CHINESE_JINYU(13,"cj"),
	CHINESE_MANDARIN(14, "cm"),
	CHINESE_MIN_NAN(15,"cn"),
	CHINESE_WU(16,"cw"),
	CHINESE_XIANG(17,"cx"),
	DUTCH(18,"nl"),
	GERMAN(19,"de"),
	GUJARATI(21,"gu"),
	HAUSA(22,"ha"),
	HINDI(23,"hi"),
	ITALIAN(24,"it"),
	JAPANESE(25,"ja"),
	JAVANESE(27,"jv"),
	KANNADA(28,"kn"),
	KOREAN(29,"ko"),
	MAITHILI(30,"mi"),
	MALAYALAM(31,"ml"),
	MARATHI(32,"mr"),
	ORIYA(33,"or"),
	PANJABI(34,"pa"),
	PERSIAN(35,"fa"),
	POLISH(36,"pl"),
	PORTUGUESE(37,"pt"),
	ROMANIAN(38,"ro"),
	RUSSIAN(39,"ru"),
	SERBO_CROATIAN(40,"sr"),
	SINDHI(41,"sd"),
	TAMIL(42,"ta"),
	TELUGU(43,"te"),
	THAI(44,"th"),
	TURKISH(45,"tr"),
	UKRAINIAN(46,"uk"),
	URDU(47,"ur"),
	VIETNAMESE (48,"vi"),
	YORUBA (49,"yo")
	;
	
	private String localizationKey = "mg.enum.language." + name().toLowerCase();
	private Integer value;
	private String code;

	public String getLocalizationKey() {
		return localizationKey;
	}

	public Integer getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

	private Language(Integer value, String code){
		this.value = value;
		this.code = code;
	}	
	
	public static Language getLanguageByCode(String lang){
		for (Language item : Language.values()) {
			if(item.getCode().equals(lang)){
				return (item);
			}
		} 
		return (null);
	}
}
