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
        System.out.println((a-1)%2);
        System.out.println((b-1)%2);
    }
}
