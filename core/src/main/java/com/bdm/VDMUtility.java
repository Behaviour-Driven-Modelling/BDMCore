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
    public String FindTargetFolder() {
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
