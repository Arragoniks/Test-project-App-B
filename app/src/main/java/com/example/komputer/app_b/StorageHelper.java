package com.example.komputer.app_b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StorageHelper {

    public static String pathSDForSaving(){
        String result = "";
        String line;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
                is = proc.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                while ((line = br.readLine()) != null) {

                    if (line.contains("fuse")
                            && line.contains("sdcard1")
                            && line.contains("default_permissions")
                            && line.contains("allow_other")) {
                        if(!line.contains("rw")){
                            //якщо sd є тільки wo / ro то повідомлення
                        }else {
                            String columns[] = line.split(" ");
                            result = columns[1];
                        }
                    }

                }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
                isr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(result == null)
            return "PLUGSD";//якщо sd не є доступною то "PLUGSD"


        return result;


    }

}


