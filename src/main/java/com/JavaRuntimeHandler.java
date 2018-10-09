package com;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaRuntimeHandler {

    private static final Logger LOGGER = LogManager.getLogger("JavaRuntimeHandler");

    static String processName = "YellCRM-0.0.1-SNAPSHOT.jar";
    static String linuxCommandToShowAllTheRunningProcess = "ps -ef";
    static String pathToKillShellScriptFile = "/home/bitnami/kill_pid.sh";
    static String pathToStartTheProcessFile = "/home/bitnami/start_program.sh";

    protected void findProcessKillProcessStartProcess() {
        LOGGER.info("Started [Find Process] [Kill Process] [Re-run Process ] ..");
        String psEfOutput = null;
        String pid = null;
        try {
            Process  p = Runtime.getRuntime().exec(linuxCommandToShowAllTheRunningProcess);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while( (psEfOutput= bufferedReader.readLine())!=null){
                if(psEfOutput.contains(processName)) {
                    LOGGER.info(processName+" > found : " + psEfOutput);
                    String[] elements = psEfOutput.split("  ");
                    LOGGER.info("pid:" + elements[1] + ",pname : " + elements[0]);
                    pid = elements[1];
                    if(pid!=null){
                        if(killProceessByPid(pid)){
                            startTheProcess();
                        }
                    }
                }
                else{
                    startTheProcess(); //start-up time,when no process is running.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean startTheProcess() {
        LOGGER.info("startin the jar ..");
        boolean isStarted = false;
        String pop = null;
        try {
            Runtime r = Runtime.getRuntime();
            String cmd[] = {pathToStartTheProcessFile,""};
            Process process= r.exec(cmd);

            if(getErrorStream(process)!=null){
                isStarted = false;
                throw new RuntimeException("Error while starting the process ");
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while( (pop= bufferedReader.readLine())!=null){
                LOGGER.info("Output while starting the jar : "+pop);
            }

            isStarted = true;

        } catch (IOException e) {
            isStarted = false;
            e.printStackTrace();
        }

        return isStarted;
    }

    private boolean killProceessByPid(String pid) {
        LOGGER.info("pid to be killed : " + pid);
        boolean isPidkilled = false;
        try{
            Runtime r = Runtime.getRuntime();
            String cmd[] = {pathToKillShellScriptFile,pid};
            Process process = r.exec(cmd);
            if(getErrorStream(process)!=null){
                isPidkilled = false;
                throw new RuntimeException("Error while killing the pid : " + pid);
            }
            isPidkilled = true;
        }
        catch(Exception e ){
            isPidkilled = false;
            e.printStackTrace();
        }
        LOGGER.info("pid killed successfully : " + pid);

        return isPidkilled;
    }

    private String getErrorStream(Process process) throws IOException {
        BufferedReader stdError = new BufferedReader(new  InputStreamReader(process.getErrorStream()));
        String s = null;
        while ((s = stdError.readLine()) != null) {
            LOGGER.info("Error in process " +  s);
            return s;
        }
        return null;
    }
}
