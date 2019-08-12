package utils;

import java.io.*;
import java.util.logging.Logger;

import java.io.*;

public class FileUtil {

        public static String readFileS(String filename) {
            String result="";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = reader.readLine()) != null) {
                    result+=line;
                }
                reader.close();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        public static void writeFile(String testData, String s) {

            File f= new File(System.getProperty("user.dir")+"/target/"+s);
            try {
                FileWriter fw= new FileWriter(f);
                fw.write(testData);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
