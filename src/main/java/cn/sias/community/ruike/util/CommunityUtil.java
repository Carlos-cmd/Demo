package cn.sias.community.ruike.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

public class CommunityUtil {



    //生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }




    //MD5加密,原始字符串加随机字符串进行加密
    public static String md5(String key){
        //这个方法判定字符串是否为空，空串和空格都能检测为空
        if(StringUtils.isBlank(key)){
            return null;
        }
        //加密为16进制的字符串
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    //数据转JSON工具
    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if(map!=null){
            for (String key: map.keySet()){
                json.put(key,map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code,String msg){
        return getJSONString(code,msg,null);
    }
    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }

}
