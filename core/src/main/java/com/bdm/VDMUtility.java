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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class VDMUtility {
    public static String FindTargetFolder() {
		return "target/test-classes";
	}

    public static final Map<String, String> DataTypeJAVAToVDM = new HashMap<String,String>(){{
		put("int", "int");
		put("float", "real");
		put("string", "seq of char");
	}};

	public static final Map<String, String> DataTypeJAVAToPackage = new HashMap<String,String>(){{
		put("int", "int");
		put("float", "float");
		put("string", "java.lang.String");
	}};

	public static String ParamHelper(String dataType, int idx) {
		switch (dataType) {
			case "string":
				return "+\"\\\"\"+$"+(idx+1)+"+\"\\\"\"+";
			case "int":
				return "+$"+(idx+1)+"+";
			case "float":
			return "+$"+(idx+1)+"+";
			default:
				return "";
		}
	}
    /* This is a helper function which finds all VDM files in a BDM project */
    public static String[] getAllVDMFiles() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("");
    
        String path = url.getPath();
    
        File file = new File(path);
        File parentFile = new File(file.getParent());
        System.out.println(parentFile.getPath());
        List<File> files = new ArrayList<>();
        if  (parentFile.isDirectory()){
            System.out.println(parentFile.toPath());
            try {
                Files.walk(parentFile.toPath()).filter(Files::isRegularFile).forEach(f -> files.add(f.toFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }   else    {
            files.add(file);
        }
    
        List<File> vdmFiles = files.stream().filter(f -> f.getName().endsWith(".vdmpp")).collect(Collectors.toList());
    
        List<String> fileNames = new ArrayList<String>();
        for (File vdmfile : vdmFiles)
        {
            System.out.println(vdmfile.getName());
            fileNames.add(vdmfile.getName());
            
        }
        String[] fileNamesArray = new String[fileNames.size()];
        fileNames.toArray(fileNamesArray);
        return fileNamesArray;
        }
}
