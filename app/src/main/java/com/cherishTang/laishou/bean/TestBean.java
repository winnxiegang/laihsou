package com.cherishTang.laishou.bean;

import java.util.List;

public class TestBean {

    /**
     * code : 0
     * data : [{"boxSize":10,"boxStatus":30,"currentLocation":0,"gmtCreate":1555644609000,"gmtModified":1555644609000,"id":832,"orderBoxNo":"26155564077149301","orderNo":"261555640771493","orderTime":1555640773000,"packagePics":"","packageState":1,"packageTime":null,"packageUserId":1115909554438279168},{"boxSize":10,"boxStatus":10,"currentLocation":0,"gmtCreate":1555644609000,"gmtModified":1555644609000,"id":833,"orderBoxNo":"26155564077149302","orderNo":"261555640771493","orderTime":1555640773000,"packagePics":"","packageState":1,"packageTime":null,"packageUserId":1115909554438279168},{"boxSize":10,"boxStatus":10,"currentLocation":0,"gmtCreate":1555644609000,"gmtModified":1555644609000,"id":834,"orderBoxNo":"26155564077149303","orderNo":"261555640771493","orderTime":1555640773000,"packagePics":"","packageState":1,"packageTime":null,"packageUserId":1115909554438279168},{"boxSize":10,"boxStatus":10,"currentLocation":0,"gmtCreate":1555644609000,"gmtModified":1555644609000,"id":835,"orderBoxNo":"26155564077149304","orderNo":"261555640771493","orderTime":1555640773000,"packagePics":"","packageState":1,"packageTime":null,"packageUserId":1115909554438279168},{"boxSize":10,"boxStatus":10,"currentLocation":0,"gmtCreate":1555644609000,"gmtModified":1555644609000,"id":836,"orderBoxNo":"26155564077149305","orderNo":"261555640771493","orderTime":1555640773000,"packagePics":"","packageState":1,"packageTime":null,"packageUserId":1115909554438279168}]
     * msg : 成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * boxSize : 10
         * boxStatus : 30
         * currentLocation : 0
         * gmtCreate : 1555644609000
         * gmtModified : 1555644609000
         * id : 832
         * orderBoxNo : 26155564077149301
         * orderNo : 261555640771493
         * orderTime : 1555640773000
         * packagePics :
         * packageState : 1
         * packageTime : null
         * packageUserId : 1115909554438279168
         */

        private int boxSize;
        private int boxStatus;
        private int currentLocation;
        private long gmtCreate;
        private long gmtModified;
        private int id;
        private String orderBoxNo;
        private String orderNo;
        private long orderTime;
        private String packagePics;
        private int packageState;
        private Object packageTime;
        private long packageUserId;

        public int getBoxSize() {
            return boxSize;
        }

        public void setBoxSize(int boxSize) {
            this.boxSize = boxSize;
        }

        public int getBoxStatus() {
            return boxStatus;
        }

        public void setBoxStatus(int boxStatus) {
            this.boxStatus = boxStatus;
        }

        public int getCurrentLocation() {
            return currentLocation;
        }

        public void setCurrentLocation(int currentLocation) {
            this.currentLocation = currentLocation;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public long getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(long gmtModified) {
            this.gmtModified = gmtModified;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderBoxNo() {
            return orderBoxNo;
        }

        public void setOrderBoxNo(String orderBoxNo) {
            this.orderBoxNo = orderBoxNo;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(long orderTime) {
            this.orderTime = orderTime;
        }

        public String getPackagePics() {
            return packagePics;
        }

        public void setPackagePics(String packagePics) {
            this.packagePics = packagePics;
        }

        public int getPackageState() {
            return packageState;
        }

        public void setPackageState(int packageState) {
            this.packageState = packageState;
        }

        public Object getPackageTime() {
            return packageTime;
        }

        public void setPackageTime(Object packageTime) {
            this.packageTime = packageTime;
        }

        public long getPackageUserId() {
            return packageUserId;
        }

        public void setPackageUserId(long packageUserId) {
            this.packageUserId = packageUserId;
        }
    }
}
