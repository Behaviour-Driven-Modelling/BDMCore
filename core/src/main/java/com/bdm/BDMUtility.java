package com.bdm;


public class BDMUtility {
    public static String BDMType(AnnotationTypes type) {
		switch (type) {
            case Given:
                return "io.cucumber.java.en.Given";
            case When:
                return "io.cucumber.java.en.When";
            case Then:
                return "io.cucumber.java.en.Then";
            case Before:
                return "io.cucumber.java.Before";
            case BeforeAll:
                return "io.cucumber.java.BeforeAll";
            default:
                return "io.cucumber.java.en.Given";
        }
    }

    public static String BDMTypeValidator(AnnotationTypes type) {
        switch (type) {
            case Given:
                return "Given";
            case When:
                return "When";
            case Then:
                return "Then";
            case Before:
                return "Before";
            case BeforeAll:
                return "BeforeAll";
            default:
                return "Given";
        }
    }
}

