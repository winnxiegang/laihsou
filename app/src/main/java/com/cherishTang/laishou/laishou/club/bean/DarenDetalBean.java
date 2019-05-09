package com.cherishTang.laishou.laishou.club.bean;

public class DarenDetalBean {

    /**
     * code : 200
     * message :
     * data : {"id":"47f59930-09cf-4390-8342-013cb47e47b5","imgUrl":"6368919872511694638786007.png","status":0,"creater":"acd20364-10af-4ec7-9018-0bd26898e9f2","createTime":"2019-03-26T12:11:50.183","content":null,"sort":null,"browseNumber":0}
     */

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
        /**
         * id : 47f59930-09cf-4390-8342-013cb47e47b5
         * imgUrl : 6368919872511694638786007.png
         * status : 0
         * creater : acd20364-10af-4ec7-9018-0bd26898e9f2
         * createTime : 2019-03-26T12:11:50.183
         * content : null
         * sort : null
         * browseNumber : 0
         */

        private String id;
        private String imgUrl;
        private int status;
        private String creater;
        private String createTime;
        private String content;
        private String sort;
        private int browseNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getBrowseNumber() {
            return browseNumber;
        }

        public void setBrowseNumber(int browseNumber) {
            this.browseNumber = browseNumber;
        }
    }
}
