package com.cherishTang.laishou.bean;

public class MainArcticdertal {
    //    id	string	文章id
//    articleTitle	string	文章标题
//    articleContent	string	文章内容
//    shareNumber	int	分享数
//    likeNumber	int	点赞数
//    collectNumber	int	收藏数
//    img	string	图片
//    simple	string	简介
    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String articleTitle;
        private String articleContent;
        private String shareNumber;
        private String likeNumber;
        private String collectNumber;
        private String img;
        private String simple;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public String getArticleContent() {
            return articleContent;
        }

        public void setArticleContent(String articleContent) {
            this.articleContent = articleContent;
        }

        public String getShareNumber() {
            return shareNumber;
        }

        public void setShareNumber(String shareNumber) {
            this.shareNumber = shareNumber;
        }

        public String getLikeNumber() {
            return likeNumber;
        }

        public void setLikeNumber(String likeNumber) {
            this.likeNumber = likeNumber;
        }

        public String getCollectNumber() {
            return collectNumber;
        }

        public void setCollectNumber(String collectNumber) {
            this.collectNumber = collectNumber;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSimple() {
            return simple;
        }

        public void setSimple(String simple) {
            this.simple = simple;
        }
    }
}
