package model;

public enum QuestionLevel {
	NotSpecified(0, "نا مشخص"),
	
	BelowEasy1(1,""),
	BelowEasy2(1,""),
    BelowEasy3(1,""),
    BelowEasy4(1,""),
	
	Easy(1,"ساده"),
	
	EasyMedium1(1,""),
	EasyMedium2(1,""),
    EasyMedium3(1,""),
    EasyMedium4(1,""),
	
	Medium(1,"متوسط"),
	
	MediumGood1(1,""),
	MediumGood2(1,""),
    MediumGood3(1,""),
    MediumGood4(1,""),
	
	Good(1,"خوب"),
	
    GoodHard1(1,""),
    GoodHard2(1,""),
    GoodHard3(1,""),
    GoodHard4(1,""),
    
	Hard(1,"مشکل"),
	
    AboveHard1(1,""),
    AboveHard2(1,""),
    AboveHard3(1,"");
    
    private int value;
    private String name;
    
    QuestionLevel(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int toInt() {
        return value;
    }

    public static QuestionLevel fromInt(int value) {
        for (QuestionLevel l : QuestionLevel.values())
            if (l.toInt() == value)
                return l;
        return null;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
	
