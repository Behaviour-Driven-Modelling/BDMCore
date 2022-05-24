package com.bdm;

public class BDMUtility {
    public static String BDMType(AnnotationType type) {
		switch (type) {
            case Given:
                return "io.cucumber.java.en.Given";
            case When:
                return "io.cucumber.java.en.When";
            case Then:
                return "io.cucumber.java.en.Then";
            default:
                return "io.cucumber.java.en.Given";
        }
	}
}
