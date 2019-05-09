package com.cherishTang.laishou.laishou.club.bean;

import java.util.List;

public class CircleThreeLeiBean {

    /**
     * code : 200
     * message :
     * data : {"order":"Id","desc":false,"list":[{"id":"6fab5d8e-31dc-49bf-a8f6-c291e3d74f9c","substationId":"9784e38e-2b9f-4b04-8326-1f1eadb00837","articleTitle":"好人","articleContent":"呵呵呵","status":2,"shareNumber":0,"likeNumber":0,"collectNumber":0,"noPassNote":"1","creater":"0785166c-fb6f-4f90-801f-f8dba372bc07","createrType":1,"createTime":"2019-02-28T15:23:46.223","clubId":null,"articleType":1,"isVideo":0,"img":"http://47.94.172.208:20192/file/showimg?filename=20190302094640.jpg","counselorId":null}],"rows":1,"page":1,"sidx":null,"sord":null,"records":0,"total":0,"table":null}
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
         * list : [{"id":"6fab5d8e-31dc-49bf-a8f6-c291e3d74f9c","substationId":"9784e38e-2b9f-4b04-8326-1f1eadb00837","articleTitle":"好人","articleContent":"呵呵呵","status":2,"shareNumber":0,"likeNumber":0,"collectNumber":0,"noPassNote":"1","creater":"0785166c-fb6f-4f90-801f-f8dba372bc07","createrType":1,"createTime":"2019-02-28T15:23:46.223","clubId":null,"articleType":1,"isVideo":0,"img":"http://47.94.172.208:20192/file/showimg?filename=20190302094640.jpg","counselorId":null}]
         * rows : 1
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
             * id : 6fab5d8e-31dc-49bf-a8f6-c291e3d74f9c
             * substationId : 9784e38e-2b9f-4b04-8326-1f1eadb00837
             * articleTitle : 好人
             * articleContent : 呵呵呵
             * status : 2
             * shareNumber : 0
             * likeNumber : 0
             * collectNumber : 0
             * noPassNote : 1
             * creater : 0785166c-fb6f-4f90-801f-f8dba372bc07
             * createrType : 1
             * createTime : 2019-02-28T15:23:46.223
             * clubId : null
             * articleType : 1
             * isVideo : 0
             * img : http://47.94.172.208:20192/file/showimg?filename=20190302094640.jpg
             * counselorId : null
             */

            private String id;
            private String substationId;
            private String articleTitle;
            private String articleContent;
            private String status;
            private String shareNumber;
            private String likeNumber;
            private String collectNumber;
            private String noPassNote;
            private String creater;
            private int createrType;
            private String createTime;
            private String clubId;
            private String articleType;
            private String isVideo;
            private String img;
            private String counselorId;
            private String simple;

            public String getSimple() {
                return simple;
            }

            public void setSimple(String simple) {
                this.simple = simple;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSubstationId() {
                return substationId;
            }

            public void setSubstationId(String substationId) {
                this.substationId = substationId;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getNoPassNote() {
                return noPassNote;
            }

            public void setNoPassNote(String noPassNote) {
                this.noPassNote = noPassNote;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public int getCreaterType() {
                return createrType;
            }

            public void setCreaterType(int createrType) {
                this.createrType = createrType;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getClubId() {
                return clubId;
            }

            public void setClubId(String clubId) {
                this.clubId = clubId;
            }

            public String getArticleType() {
                return articleType;
            }

            public void setArticleType(String articleType) {
                this.articleType = articleType;
            }

            public String getIsVideo() {
                return isVideo;
            }

            public void setIsVideo(String isVideo) {
                this.isVideo = isVideo;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getCounselorId() {
                return counselorId;
            }

            public void setCounselorId(String counselorId) {
                this.counselorId = counselorId;
            }
        }
    }
}
