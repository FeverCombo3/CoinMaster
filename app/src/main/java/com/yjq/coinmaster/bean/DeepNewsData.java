package com.yjq.coinmaster.bean;

/**
 * @author yjq
 * @date 2018/12/24
 */

public class DeepNewsData {

//             "head": "https://static.aicoinstorge.com/columnist/avatar/20171219/15136689466190.png",
//             "img": "https://static.aicoinstorge.com/article/20180807/153363664123271.jpg",
//             "createtime": 1533637209085,
//             "newsID": "5b6972596825b7331cac7d47",
//             "author": "狂人",
//             "description": "行情分析",
//
//            "title": "真利好和假利好的区别原来在这里，8月7日行情分析",
//            "type": "分析"


    private String head;
    private String img;
    private String createtime;
    private String newsID;
    private String author;
    private String description;
    private String title;
    private String type;
    private String contentUrl;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getDescription() {
        return description;
    }

    public String getHead() {
        return head;
    }

    public String getImg() {
        return img;
    }



    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setImg(String img) {
        this.img = img;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

}
