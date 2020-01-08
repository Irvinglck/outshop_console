package com.lck.outshop;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class OutshopApplicationTests {

    @Test
    void contextLoads() {
        Map<String,Object> dataMap = new HashMap<>();
        try {
            //日期
            dataMap.put("createDate", new SimpleDateFormat("yyyy年MM月dd日").format(new SimpleDateFormat("yyyy-MM-dd").parse("2018-09-19")));
            //受款人
            dataMap.put("name", "张三");
            //付款用途
            dataMap.put("content", "test势力扩大飞机啊圣诞快乐"+ System.getProperty("line.separator")+"房价来说来电反馈记录");
            //大写金额
            //Configuration 用于读取ftl文件
            Configuration configuration = new Configuration(new Version("2.3.29"));
            configuration.setDefaultEncoding("utf-8");

            /**
             * 以下是两种指定ftl文件所在目录路径的方式，注意这两种方式都是
             * 指定ftl文件所在目录的路径，而不是ftl文件的路径
             */
            //指定路径的第一种方式（根据某个类的相对路径指定）
//                configuration.setClassForTemplateLoading(this.getClass(), "");

            //指定路径的第二种方式，我的路径是C：/a.ftl
            configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\Administrator\\Desktop"));

            //输出文档路径及名称
            File outFile = new File("D:/听到模板.doc");

            //以utf-8的编码读取ftl文件
            Template template = configuration.getTemplate("chat_records_template.ftl", "utf-8");

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 1024);
            template.process(dataMap, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
