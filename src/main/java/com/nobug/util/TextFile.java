package com.nobug.util;

/**
 * @author 389561407@qq.com
 * @version 1.0
 * @since 2022-11-15
 */
public class TextFile {

    private String title;

    private String name;

    private String url;

    public TextFile(String title, String name, String href) {
        this.title = title;
        this.name = name;
        url = href;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TextFile{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
