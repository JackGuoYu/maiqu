package com.maiqu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class TestService {
    public static void main(String[] args) {
        String path = "E:/demo.json";
        String file = ReadFile(path);
        JSONObject jsonobject = JSON.parseObject(file);
        JSONArray list = jsonobject.getJSONObject("data").getJSONArray("list");
        for(int i=0;i<list.size();i++){
            JSONObject json = list.getJSONObject(i);
            String nickName = json.getString("nickname");
            String headImg = json.getString("avatar_url");
            String phoneNum = json.getString("phone_number");
            String wxNumber = json.getString("wx_hao");
            System.out.println("微信昵称："+nickName+"--头像："+headImg+"--手机号："+phoneNum+"--微信号："+wxNumber);
        }
    }

    public static String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }
}
