/*******************************************************************************
 *
 *	Copyright (c) 2022 Malthe Dalgaard Jensen & Kristoffer Stampe Villadsen.
 *
 *	Author: Malthe Dalgaard Jensen & Kristoffer Stampe Villadsen
 *
 *	This file is part of BDMCore.
 *
 *	BDMCore is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	BDMCore is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with BDMCore.  If not, see <http://www.gnu.org/licenses/>.
 *	SPDX-License-Identifier: GPL-3.0-or-later
 *
 ******************************************************************************/
package com.bdm;


public class BDMUtility {
    /* This operation handles the identification of Cucumber annotations for creation of annotation in BDM*/
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

