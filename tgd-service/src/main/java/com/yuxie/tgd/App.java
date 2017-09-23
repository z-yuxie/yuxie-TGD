package com.yuxie.tgd;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int a = 0;
        int b = -1;
//        String keyWord = "哈哈哈 好好好 ， 沙发哈哈 , 沙发发生啥事，，,, , ， ，";
//        String keyWord = "哈哈哈 好好好 ， 沙发哈哈 , 沙发发生啥事,  ,1,";
//        keyWord = keyWord.replace('，' , ',');
//        String[] keyWordArray = keyWord.split(",");
//        for (person keyWordTmp : keyWordArray) {
//            if (StringUtils.isNotBlank(keyWordTmp.trim())) {
//                keyWordTmp = keyWordTmp.trim();
//            }
//            keyWordList.add(keyWordTmp);
//        }
//        System.out.println(keyWordList.toString());
//        String json = JSONObject.toJSONString("zxxx");
//        System.out.println(StringUtils.isBlank(json));
//        System.out.println(keyWordList.contains(""));
        Persion myPersionTest = new Persion();
        myPersionTest.setSessionId("xxxxxxxxxxxxx");
        myPersionTest.setUserId(123456789L);
        myPersionTest.setUserName("hhhhhhh");
        String json = JSONObject.toJSONString(myPersionTest);
        System.out.println(json);
//        System.out.println((a-1)%2);
//        System.out.println((b-1)%2);
    }
}

class Persion{
    //用户ID
    private Long userId;
    //会话ID
    private String sessionId;
    //用户名
    private String userName;

    @Override
    public String toString() {
        return "Persion{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
