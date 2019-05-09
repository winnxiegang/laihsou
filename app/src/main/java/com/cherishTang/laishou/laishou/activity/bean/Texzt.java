package com.cherishTang.laishou.laishou.activity.bean;

import java.util.List;

public class Texzt {

    /**
     * code : 0
     * data : [{"abnormalBoxNos":[],"abnormalBoxNum":1,"addressType":1,"arriveSiteId":500,"arriveSiteName":"烟台市福山区站点","departNo":"CD1555688100183","departSiteId":499,"departSiteName":"青岛市黄岛区站点","driverName":"忘司机","gmtCreate":1555687930000,"id":5,"logisticsId":109,"normalBoxNos":[],"normalBoxNum":6,"orderNum":4,"receiveAreaId":0,"receiveAreaName":""},{"abnormalBoxNos":[],"abnormalBoxNum":2,"addressType":1,"arriveSiteId":501,"arriveSiteName":"青岛市城阳区站点","departNo":"CD1555688100209","departSiteId":499,"departSiteName":"青岛市黄岛区站点","driverName":"忘司机","gmtCreate":1555687930000,"id":6,"logisticsId":109,"normalBoxNos":[],"normalBoxNum":6,"orderNum":4,"receiveAreaId":0,"receiveAreaName":""},{"abnormalBoxNos":[],"abnormalBoxNum":1,"addressType":1,"arriveSiteId":500,"arriveSiteName":"烟台市福山区站点","departNo":"CD1555687403847","departSiteId":499,"departSiteName":"青岛市黄岛区站点","driverName":"忘司机","gmtCreate":1555687234000,"id":3,"logisticsId":109,"normalBoxNos":[],"normalBoxNum":6,"orderNum":4,"receiveAreaId":0,"receiveAreaName":""},{"abnormalBoxNos":[],"abnormalBoxNum":2,"addressType":1,"arriveSiteId":501,"arriveSiteName":"青岛市城阳区站点","departNo":"CD1555687403873","departSiteId":499,"departSiteName":"青岛市黄岛区站点","driverName":"忘司机","gmtCreate":1555687234000,"id":4,"logisticsId":109,"normalBoxNos":[],"normalBoxNum":6,"orderNum":4,"receiveAreaId":0,"receiveAreaName":""},{"abnormalBoxNos":[],"abnormalBoxNum":1,"addressType":1,"arriveSiteId":500,"arriveSiteName":"烟台市福山区站点","departNo":"CD1555687009163","departSiteId":499,"departSiteName":"青岛市黄岛区站点","driverName":"忘司机","gmtCreate":1555686839000,"id":1,"logisticsId":109,"normalBoxNos":[],"normalBoxNum":6,"orderNum":4,"receiveAreaId":0,"receiveAreaName":""},{"abnormalBoxNos":[],"abnormalBoxNum":2,"addressType":1,"arriveSiteId":501,"arriveSiteName":"青岛市城阳区站点","departNo":"CD1555687009214","departSiteId":499,"departSiteName":"青岛市黄岛区站点","driverName":"忘司机","gmtCreate":1555686839000,"id":2,"logisticsId":109,"normalBoxNos":[],"normalBoxNum":6,"orderNum":4,"receiveAreaId":0,"receiveAreaName":""}]
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
         * abnormalBoxNos : []
         * abnormalBoxNum : 1
         * addressType : 1
         * arriveSiteId : 500
         * arriveSiteName : 烟台市福山区站点
         * departNo : CD1555688100183
         * departSiteId : 499
         * departSiteName : 青岛市黄岛区站点
         * driverName : 忘司机
         * gmtCreate : 1555687930000
         * id : 5
         * logisticsId : 109
         * normalBoxNos : []
         * normalBoxNum : 6
         * orderNum : 4
         * receiveAreaId : 0
         * receiveAreaName :
         */

        private int abnormalBoxNum;
        private int addressType;
        private int arriveSiteId;
        private String arriveSiteName;
        private String departNo;
        private int departSiteId;
        private String departSiteName;
        private String driverName;
        private long gmtCreate;
        private int id;
        private int logisticsId;
        private int normalBoxNum;
        private int orderNum;
        private int receiveAreaId;
        private String receiveAreaName;
        private List<?> abnormalBoxNos;
        private List<?> normalBoxNos;

        public int getAbnormalBoxNum() {
            return abnormalBoxNum;
        }

        public void setAbnormalBoxNum(int abnormalBoxNum) {
            this.abnormalBoxNum = abnormalBoxNum;
        }

        public int getAddressType() {
            return addressType;
        }

        public void setAddressType(int addressType) {
            this.addressType = addressType;
        }

        public int getArriveSiteId() {
            return arriveSiteId;
        }

        public void setArriveSiteId(int arriveSiteId) {
            this.arriveSiteId = arriveSiteId;
        }

        public String getArriveSiteName() {
            return arriveSiteName;
        }

        public void setArriveSiteName(String arriveSiteName) {
            this.arriveSiteName = arriveSiteName;
        }

        public String getDepartNo() {
            return departNo;
        }

        public void setDepartNo(String departNo) {
            this.departNo = departNo;
        }

        public int getDepartSiteId() {
            return departSiteId;
        }

        public void setDepartSiteId(int departSiteId) {
            this.departSiteId = departSiteId;
        }

        public String getDepartSiteName() {
            return departSiteName;
        }

        public void setDepartSiteName(String departSiteName) {
            this.departSiteName = departSiteName;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLogisticsId() {
            return logisticsId;
        }

        public void setLogisticsId(int logisticsId) {
            this.logisticsId = logisticsId;
        }

        public int getNormalBoxNum() {
            return normalBoxNum;
        }

        public void setNormalBoxNum(int normalBoxNum) {
            this.normalBoxNum = normalBoxNum;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public int getReceiveAreaId() {
            return receiveAreaId;
        }

        public void setReceiveAreaId(int receiveAreaId) {
            this.receiveAreaId = receiveAreaId;
        }

        public String getReceiveAreaName() {
            return receiveAreaName;
        }

        public void setReceiveAreaName(String receiveAreaName) {
            this.receiveAreaName = receiveAreaName;
        }

        public List<?> getAbnormalBoxNos() {
            return abnormalBoxNos;
        }

        public void setAbnormalBoxNos(List<?> abnormalBoxNos) {
            this.abnormalBoxNos = abnormalBoxNos;
        }

        public List<?> getNormalBoxNos() {
            return normalBoxNos;
        }

        public void setNormalBoxNos(List<?> normalBoxNos) {
            this.normalBoxNos = normalBoxNos;
        }
    }
}
