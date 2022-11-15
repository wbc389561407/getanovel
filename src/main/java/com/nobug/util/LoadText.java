package com.nobug.util;


import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 389561407@qq.com
 * @version 1.0
 * @since 2022-11-15
 */

public class LoadText {

    //保存文件
    public static String root = "D:/text/";

    public static String url = "";



    public static void runText(String url) {
        //输入地址获取小说章节
        List<TextFile> textFileList = ddxs(url);
        System.out.println("获取到目录："+textFileList.size());


        int num = 0;
        for (TextFile textFile : textFileList) {
            String path = root + textFile.getTitle() + "/";
            num++;
            String name = textFile.getName();
            String replace = name.replace("?", "");
            name = num +"."+replace;
            System.out.println("校验文件是否存在："+textFile.getName());

            File file = new File(path + name+".txt");
            if(!file.exists()){
                System.out.println("创建："+name);
                String text = urlToText(textFile.getUrl());
                toFile(path, name, text);
            }

        }

    }

    public static String urlToText(String url) {
        String html = HttpUtil.get("https://www.ddxs.cc"+url, Charset.forName("GBK"));
        Document parse = Jsoup.parse(html);
        Element content = parse.getElementById("content");
        return content.text();
    }

    private static String getValue(String url) {
        String html = HttpUtil.get(url);
        System.out.println(html);
        return null;
    }


    //文件夹路径，文件名称 ， 内容
    public static void toFile(String path, String name, String text) {

        createFile(path);
//        File file = new File(root + "大魔王" + "/" + "第一章节.txt");
        path = path + name+".txt";
        Path path1 = Paths.get(path);
        File file = path1.toFile();
        if(!file.exists()){
            System.out.println("正在写入："+name);
            try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path1, StandardCharsets.UTF_8)) {
                bufferedWriter.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 创建文件或者文件夹
     * @param path
     */
    private static void createFile(String path) {
        File file = new File(path);
        if(!file.exists()){
            System.out.println("文件不存在");
            if(path.endsWith("/")){
                //如果是创建文件夹直接创建
                System.out.println("如果是创建文件夹直接创建");
                file.mkdirs();
            }else {
                System.out.println("这里是创建文件");
                //创建文件需要确认上级文件夹存在
                File parentFile = file.getParentFile();
                //上级目录是否存在
                boolean exists = parentFile.exists();
                if(!exists){
                    System.out.println("创建上级文件夹");
                    parentFile.mkdirs();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }


    //https://www.ddxs.cc/ddxs/168130/
    public static List<TextFile> ddxs(String url) {
        String title;

        //获取资源地址
        String html = HttpUtil.get(url);

        Document parse = Jsoup.parse(html);
        Element body = parse.body();
        Element list = body.getElementById("list");

        if(list ==null){
            return null;
        }

        //拿到目录
        Elements dd = list.getElementsByTag("dd");
        Elements child = list.getElementsByTag("dt");
        title = child.text();
        System.out.println("获取到标题："+title);
        List<TextFile> textFiles = new ArrayList<>(dd.size());

        StringBuilder stringBuilder = new StringBuilder();
        for (Element zj : dd) {
            Element a = zj.child(0);
            //章节名称
//            System.out.println(a.text());
            String name = a.text();
            //章节地址
//            System.out.println(a.attr("href"));
//            System.out.println();

            stringBuilder.append(name);
            stringBuilder.append("\n");
            stringBuilder.append(a.attr("href"));
            stringBuilder.append("\n");
            textFiles.add(new TextFile(title,name, a.attr("href")));
        }

        String data = stringBuilder.toString();
        String path = root + title + "/";
        toFile(path,title,data);
        return textFiles;
    }
}
