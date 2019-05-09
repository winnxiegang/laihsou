package com.cherishTang.laishou.laishou.club.bean;

import java.util.List;

public class CirclePageBean {

    /**
     * code : 200
     * message :
     * data : {"order":"Id","desc":false,"list":[{"id":"ac9a2631-4b05-43d0-9f0a-ce47b38a8084","introduce":"","picture":"201904241426222758.jpg;201904241426226308.jpg;","photoList":["http://47.99.100.88:81/file/showimg?filename=201904241426222758.jpg","http://47.99.100.88:81/file/showimg?filename=201904241426226308.jpg"],"name":"","headImg":"http://47.99.100.88:81/file/showimg?filename=201903061111.jpg","createTime":"2019-04-24T14:26:22.99","strCreateTime":"2019年04月24日发表"},{"id":"53eae739-b5ff-4fce-b67d-c6d308e9eedd","introduce":"好饿啊","picture":"201904231904009524.jpg;201904231904010106.jpg;","photoList":["http://47.99.100.88:81/file/showimg?filename=201904231904009524.jpg","http://47.99.100.88:81/file/showimg?filename=201904231904010106.jpg"],"name":"代玥","headImg":"http://47.99.100.88:81/file/showimg?filename=201903242202345793.jpg","createTime":"2019-04-23T19:04:01.31","strCreateTime":"2019年04月23日发表"}],"rows":50,"page":1,"sidx":null,"sord":null,"records":0,"total":0,"table":null}
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
         * order : Id
         * desc : false
         * list : [{"id":"ac9a2631-4b05-43d0-9f0a-ce47b38a8084","introduce":"","picture":"201904241426222758.jpg;201904241426226308.jpg;","photoList":["http://47.99.100.88:81/file/showimg?filename=201904241426222758.jpg","http://47.99.100.88:81/file/showimg?filename=201904241426226308.jpg"],"name":"","headImg":"http://47.99.100.88:81/file/showimg?filename=201903061111.jpg","createTime":"2019-04-24T14:26:22.99","strCreateTime":"2019年04月24日发表"},{"id":"53eae739-b5ff-4fce-b67d-c6d308e9eedd","introduce":"好饿啊","picture":"201904231904009524.jpg;201904231904010106.jpg;","photoList":["http://47.99.100.88:81/file/showimg?filename=201904231904009524.jpg","http://47.99.100.88:81/file/showimg?filename=201904231904010106.jpg"],"name":"代玥","headImg":"http://47.99.100.88:81/file/showimg?filename=201903242202345793.jpg","createTime":"2019-04-23T19:04:01.31","strCreateTime":"2019年04月23日发表"}]
         * rows : 50
         * page : 1
         * sidx : null
         * sord : null
         * records : 0
         * total : 0
         * table : null
         */

        private String order;
        private boolean desc;
        private int rows;
        private int page;
        private Object sidx;
        private Object sord;
        private int records;
        private int total;
        private Object table;
        private List<ListBean> list;

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public boolean isDesc() {
            return desc;
        }

        public void setDesc(boolean desc) {
            this.desc = desc;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public Object getSidx() {
            return sidx;
        }

        public void setSidx(Object sidx) {
            this.sidx = sidx;
        }

        public Object getSord() {
            return sord;
        }

        public void setSord(Object sord) {
            this.sord = sord;
        }

        public int getRecords() {
            return records;
        }

        public void setRecords(int records) {
            this.records = records;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public Object getTable() {
            return table;
        }

        public void setTable(Object table) {
            this.table = table;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : ac9a2631-4b05-43d0-9f0a-ce47b38a8084
             * introduce :
             * picture : 201904241426222758.jpg;201904241426226308.jpg;
             * photoList : ["http://47.99.100.88:81/file/showimg?filename=201904241426222758.jpg","http://47.99.100.88:81/file/showimg?filename=201904241426226308.jpg"]
             * name :
             * headImg : http://47.99.100.88:81/file/showimg?filename=201903061111.jpg
             * createTime : 2019-04-24T14:26:22.99
             * strCreateTime : 2019年04月24日发表
             */

            private String id;
            private String introduce;
            private String picture;
            private String name;
            private String headImg;
            private String createTime;
            private String strCreateTime;
            private List<String> photoList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getStrCreateTime() {
                return strCreateTime;
            }

            public void setStrCreateTime(String strCreateTime) {
                this.strCreateTime = strCreateTime;
            }

            public List<String> getPhotoList() {
                return photoList;
            }

            public void setPhotoList(List<String> photoList) {
                this.photoList = photoList;
            }
        }
    }
}
