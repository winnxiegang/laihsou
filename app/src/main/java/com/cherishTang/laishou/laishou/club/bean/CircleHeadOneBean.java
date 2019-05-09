package com.cherishTang.laishou.laishou.club.bean;

import java.util.List;

public class CircleHeadOneBean {

    /**
     * code : 200
     * message :
     * data : {"order":"Id","desc":false,"list":[{"id":"00000000-0000-0000-0000-000000000000","clubId":"00000000-0000-0000-0000-000000000000","loginId":"00000000-0000-0000-0000-000000000000","name":null,"mobile":null,"counselorId":"00000000-0000-0000-0000-000000000000","recommendId":"00000000-0000-0000-0000-000000000000","integral":0,"lastSignTime":null,"states":0,"creater":"00000000-0000-0000-0000-000000000000","createTime":"2019-03-26T12:11:50.183","sex":null,"birthday":null,"height":null,"weight":null,"befond":null,"industry":null,"job":null,"targetWeight":null,"headImg":"http://47.99.100.88:81/file/showimg?filename=6368919872511694638786007.png","thenLose":0,"bmi":null,"fatRate":null,"chestWai":null,"waistWai":null,"hipWai":null}],"rows":92,"page":1,"sidx":null,"sord":null,"records":0,"total":0,"table":null}
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
         * list : [{"id":"00000000-0000-0000-0000-000000000000","clubId":"00000000-0000-0000-0000-000000000000","loginId":"00000000-0000-0000-0000-000000000000","name":null,"mobile":null,"counselorId":"00000000-0000-0000-0000-000000000000","recommendId":"00000000-0000-0000-0000-000000000000","integral":0,"lastSignTime":null,"states":0,"creater":"00000000-0000-0000-0000-000000000000","createTime":"2019-03-26T12:11:50.183","sex":null,"birthday":null,"height":null,"weight":null,"befond":null,"industry":null,"job":null,"targetWeight":null,"headImg":"http://47.99.100.88:81/file/showimg?filename=6368919872511694638786007.png","thenLose":0,"bmi":null,"fatRate":null,"chestWai":null,"waistWai":null,"hipWai":null}]
         * rows : 92
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
             * id : 00000000-0000-0000-0000-000000000000
             * clubId : 00000000-0000-0000-0000-000000000000
             * loginId : 00000000-0000-0000-0000-000000000000
             * name : null
             * mobile : null
             * counselorId : 00000000-0000-0000-0000-000000000000
             * recommendId : 00000000-0000-0000-0000-000000000000
             * integral : 0
             * lastSignTime : null
             * states : 0
             * creater : 00000000-0000-0000-0000-000000000000
             * createTime : 2019-03-26T12:11:50.183
             * sex : null
             * birthday : null
             * height : null
             * weight : null
             * befond : null
             * industry : null
             * job : null
             * targetWeight : null
             * headImg : http://47.99.100.88:81/file/showimg?filename=6368919872511694638786007.png
             * thenLose : 0.0
             * bmi : null
             * fatRate : null
             * chestWai : null
             * waistWai : null
             * hipWai : null
             */

            private String id;
            private String clubId;
            private String loginId;
            private Object name;
            private Object mobile;
            private String counselorId;
            private String recommendId;
            private int integral;
            private Object lastSignTime;
            private int states;
            private String creater;
            private String createTime;
            private Object sex;
            private Object birthday;
            private Object height;
            private Object weight;
            private Object befond;
            private Object industry;
            private Object job;
            private Object targetWeight;
            private String headImg;
            private double thenLose;
            private Object bmi;
            private Object fatRate;
            private Object chestWai;
            private Object waistWai;
            private Object hipWai;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClubId() {
                return clubId;
            }

            public void setClubId(String clubId) {
                this.clubId = clubId;
            }

            public String getLoginId() {
                return loginId;
            }

            public void setLoginId(String loginId) {
                this.loginId = loginId;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
                this.mobile = mobile;
            }

            public String getCounselorId() {
                return counselorId;
            }

            public void setCounselorId(String counselorId) {
                this.counselorId = counselorId;
            }

            public String getRecommendId() {
                return recommendId;
            }

            public void setRecommendId(String recommendId) {
                this.recommendId = recommendId;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public Object getLastSignTime() {
                return lastSignTime;
            }

            public void setLastSignTime(Object lastSignTime) {
                this.lastSignTime = lastSignTime;
            }

            public int getStates() {
                return states;
            }

            public void setStates(int states) {
                this.states = states;
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

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public Object getBefond() {
                return befond;
            }

            public void setBefond(Object befond) {
                this.befond = befond;
            }

            public Object getIndustry() {
                return industry;
            }

            public void setIndustry(Object industry) {
                this.industry = industry;
            }

            public Object getJob() {
                return job;
            }

            public void setJob(Object job) {
                this.job = job;
            }

            public Object getTargetWeight() {
                return targetWeight;
            }

            public void setTargetWeight(Object targetWeight) {
                this.targetWeight = targetWeight;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public double getThenLose() {
                return thenLose;
            }

            public void setThenLose(double thenLose) {
                this.thenLose = thenLose;
            }

            public Object getBmi() {
                return bmi;
            }

            public void setBmi(Object bmi) {
                this.bmi = bmi;
            }

            public Object getFatRate() {
                return fatRate;
            }

            public void setFatRate(Object fatRate) {
                this.fatRate = fatRate;
            }

            public Object getChestWai() {
                return chestWai;
            }

            public void setChestWai(Object chestWai) {
                this.chestWai = chestWai;
            }

            public Object getWaistWai() {
                return waistWai;
            }

            public void setWaistWai(Object waistWai) {
                this.waistWai = waistWai;
            }

            public Object getHipWai() {
                return hipWai;
            }

            public void setHipWai(Object hipWai) {
                this.hipWai = hipWai;
            }
        }
    }
}
