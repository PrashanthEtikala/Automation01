package com.GenericLib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesFileLib {

public static void fnRemoveCompleteData(String PropertiesFileName) throws IOException{

String filepath = "src/main/resources/"+PropertiesFileName;
InputStream input = new FileInputStream(filepath);
Properties prop1 = new Properties();
prop1.load(input);
prop1.clear();
OutputStream out = new FileOutputStream(filepath);
prop1.store(out, null);

}

public static void writePropertiesFile(String PropertiesFileName, String Property, String Value) throws IOException{

String filepath = "src/main/resources/"+PropertiesFileName;

InputStream input = new FileInputStream(filepath);
Properties prop1 = new Properties();
prop1.load(input);

OutputStream output = new FileOutputStream(filepath);
Properties prop = new Properties();

Enumeration<?> e = prop1.propertyNames();
while(e.hasMoreElements()){
String key = (String) e.nextElement();
String val = prop1.getProperty(key);

prop.setProperty(key, val);
}

prop.setProperty(Property, Value);
prop.store(output, null);

//Read the data
try {
Properties prop2 = new Properties();
InputStream input1 = new FileInputStream(filepath);
prop2.load(input1);
System.out.println(prop2.getProperty(Property));
} catch (FileNotFoundException e1) {
e1.printStackTrace();
} catch (IOException e2) {
e2.printStackTrace();
}
}

public static String ReadPropertiesFile(String PropertiesFileName, String Property){

String filepath1 = "src/main/resources/"+PropertiesFileName;

try{
Properties prop = new Properties();
InputStream input = new FileInputStream(filepath1);
prop.load(input);
return prop.getProperty(Property);
} catch(Exception e){
System.out.println("Exception: "+e);
return null;
}

}

}


