package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NotifyChange {

    String recentValue = "";
    String lastValue = "";

    public static void main(String args[]){
        new NotifyChange().invoke();
    }

    public void invoke(){
        System.out.println("Notify Change - listener program started..");
        while(true)
        {
            try
            {
                Thread.sleep(1000);
                DynamicPropertyReader dr = new DynamicPropertyReader();
                dr.notifyChanges();
                recentValue = dr.getProperty("spring.datasource.url");
                System.out.println("Waiting for a change ..");
                if(!lastValue.equals(recentValue)){
                    System.out.println("Yes..something changed: "+recentValue);
                    lastValue = recentValue;
                    /*main process to destroy the existing process, run the process again */
                    JavaRuntimeHandler javaRuntimeHandler = new JavaRuntimeHandler();
                    javaRuntimeHandler.findProcessKillProcessStartProcess();
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }

    private void killProcessStartProcess() {
        String s = null;
        try {
            Process p = Runtime.getRuntime().exec("ps -ef");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
