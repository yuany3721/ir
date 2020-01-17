package com.ir.site.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
 * @Author yanyi
 * @Description 配置文件读取工具类
 * @Date 10:53 2019/5/6
 **/
public class ProperUtils {


    /*
     * @Author yanyi
     * @Description 获取加密方式相关信息
     * @Date 10:53 2019/5/6
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String,String> getHashProper(){
        Map<String,String> map=new HashMap<String,String>();
        // 1. 通过当前类获取类加载器   访问方式： 类型.class.getClassLoader()
        ClassLoader classLoader=ProperUtils.class.getClassLoader();
        // 2. 通过类类加载器获取一个输入流
        InputStream is =classLoader.getResourceAsStream("hash.properties");
        // 3. 创建properties对象
        Properties p=new Properties();
        // 4. 加载输入流
        try{
            p.load(is);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 5. 获取相关参数的值
        map.put("hash",p.getProperty("hash"));
        map.put("pwd",p.getProperty("pwd"));
        map.put("salt",p.getProperty("salt"));
        map.put("num",p.getProperty("num"));
        return map;
    }

    public static void main(String[] args) {
        System.out.println(getHashProper());
    }
}
