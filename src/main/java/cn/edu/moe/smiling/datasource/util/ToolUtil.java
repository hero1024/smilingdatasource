package cn.edu.moe.smiling.datasource.util;

import java.io.File;

public class ToolUtil {
    /**
     * 判断权限是否存在
     */
    public static Boolean bollPurviewTool(String[] list, String str){
        String[] list2 = str.split(",");
        for (String item:list) {
            for (String item2:list2) {
                if (item.equals(item2)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前系统路径的上一级
     */
    public static String currentParentPath(){
        String currentDirectory = System.getProperty("user.dir");
        File file = new File(currentDirectory);
        String parentDirectory = file.getParent();
        return  parentDirectory;
    }



}