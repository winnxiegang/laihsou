package com.cherishTang.laishou.bean.rent;

import java.util.List;

/**
 * Created by 方舟 on 2017/10/16.
 * 发布的房源参数
 */

public class RentParamsBean{
    private boolean status;
    private String code;
    private String msg;
    private Result data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getData() {
        return data;
    }

    public void setData(Result data) {
        this.data = data;
    }

    public class Result{

        private List<Detail> city;
        private List<Detail> area;
        private List<Detail> houseUse;
        private List<Detail> houseSize;
        private List<Detail> orientation;
        private List<Detail> decoration;
        private List<Detail> paymentMode;
        private List<Detail> propertyType;
        private List<Detail> leaseItem;
        private List<Detail> cardType;
        private List<Detail> houseStruct;
        private List<Detail> rentPayCycle;
        private List<Detail> annualIncome;//年收入 ,
        private List<Detail> censusRegister;//户籍 ,
        private List<Detail> profession;//职业
        private List<Detail> education;//学历 ,
        private List<Detail> street;//街道

        public List<Detail> getStreet() {
            return street;
        }

        public void setStreet(List<Detail> street) {
            this.street = street;
        }

        public List<Detail> getAnnualIncome() {
            return annualIncome;
        }

        public void setAnnualIncome(List<Detail> annualIncome) {
            this.annualIncome = annualIncome;
        }

        public List<Detail> getCensusRegister() {
            return censusRegister;
        }

        public void setCensusRegister(List<Detail> censusRegister) {
            this.censusRegister = censusRegister;
        }

        public List<Detail> getProfession() {
            return profession;
        }

        public void setProfession(List<Detail> profession) {
            this.profession = profession;
        }

        public List<Detail> getEducation() {
            return education;
        }

        public void setEducation(List<Detail> education) {
            this.education = education;
        }

        public List<Detail> getRentPayCycle() {
            return rentPayCycle;
        }

        public void setRentPayCycle(List<Detail> rentPayCycle) {
            this.rentPayCycle = rentPayCycle;
        }

        public List<Detail> getHouseStruct() {
            return houseStruct;
        }

        public void setHouseStruct(List<Detail> houseStruct) {
            this.houseStruct = houseStruct;
        }

        public List<Detail> getCardType() {
            return cardType;
        }

        public void setCardType(List<Detail> cardType) {
            this.cardType = cardType;
        }

        public List<Detail> getLeaseItem() {
            return leaseItem;
        }

        public void setLeaseItem(List<Detail> leaseItem) {
            this.leaseItem = leaseItem;
        }

        public List<Detail> getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(List<Detail> paymentMode) {
            this.paymentMode = paymentMode;
        }

        public List<Detail> getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(List<Detail> propertyType) {
            this.propertyType = propertyType;
        }

        public List<Detail> getCity() {
            return city;
        }

        public void setCity(List<Detail> city) {
            this.city = city;
        }

        public List<Detail> getArea() {
            return area;
        }

        public void setArea(List<Detail> area) {
            this.area = area;
        }

        public List<Detail> getHouseUse() {
            return houseUse;
        }

        public void setHouseUse(List<Detail> houseUse) {
            this.houseUse = houseUse;
        }

        public List<Detail> getHouseSize() {
            return houseSize;
        }

        public void setHouseSize(List<Detail> houseSize) {
            this.houseSize = houseSize;
        }

        public List<Detail> getOrientation() {
            return orientation;
        }

        public void setOrientation(List<Detail> orientation) {
            this.orientation = orientation;
        }

        public List<Detail> getDecoration() {
            return decoration;
        }

        public void setDecoration(List<Detail> decoration) {
            this.decoration = decoration;
        }
    }
    public class Detail{
        private Integer SeqId;
        private Integer ParentId;
        private Integer Id;
        private String Name;

        public Detail() {
        }

        public Detail(Integer id, String name) {
            Id = id;
            Name = name;
        }

        public Integer getSeqId() {
            return SeqId;
        }

        public void setSeqId(Integer seqId) {
            SeqId = seqId;
        }

        public Integer getParentId() {
            return ParentId;
        }

        public void setParentId(Integer parentId) {
            ParentId = parentId;
        }

        public Integer getId() {
            return Id;
        }

        public void setId(Integer id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
