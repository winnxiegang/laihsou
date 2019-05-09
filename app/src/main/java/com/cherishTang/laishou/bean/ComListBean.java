package com.cherishTang.laishou.bean;

import java.util.List;

public class ComListBean {

    /**
     * code : 200
     * message :
     * data : {"hotList":[{"articleTitle":"商家们看过来~莱宝教你玩转专属的莱瘦商家后台！","img":"20190417173906.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190417173906.jpg"},{"articleTitle":"莱瘦独有的\u201c锁客引流\u201d~ 请查收！","img":"20190416175823.png","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190416175823.png"},{"articleTitle":"莱瘦商家入驻操作规程2.0（详细版）","img":"20190413151045.png","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190413151045.png"},{"articleTitle":"来自创客们的六大问题，莱宝给您解答啦~","img":"20190413145212.jpeg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190413145212.jpeg"}],"videoList":[{"articleTitle":"莱瘦，一个可以不用节食、不用运动却能让你拥有完美身材的APP","articleContent":"http://images.laiscn.cn/lifs-7M5GpCLz8M0g6yxVgQfHWQR"},{"articleTitle":"莱瘦达人风采\u2014\u20146个半月减重61斤","articleContent":"http://images.laiscn.cn/FrpK48AGWckXN3DF0HJGrjEEyktY"},{"articleTitle":"莱瘦达人风采\u2014\u2014累计减重110斤","articleContent":"http://images.laiscn.cn/Fp0OIvJlm-VXJETlBCHXcZfuK7pR"},{"articleTitle":"莱瘦达人风采\u2014\u2014累计减重128斤","articleContent":"http://images.laiscn.cn/ln5XuGIg4T0nFKYHEJ9C1Wy_s71Q"}],"spellGoodsList":[{"goodsName":"2019夏装原创新款撞色高腰显瘦系带连体裤微喇阔腿长裤连身裤女潮","goodsUrl":"20190415111540.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190415111540.jpg"},{"goodsName":"C&M女装","goodsUrl":"20190417160235.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190417160235.jpg"},{"goodsName":"保健、康复、理疗（易筋堂推拿艾灸馆）","goodsUrl":"20190415155333.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190415155333.jpg"},{"goodsName":"鲍式金勺子土菜馆","goodsUrl":"20190415143234.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190415143234.jpg"}],"activityList":[{"activityTitle":"你减肥  我出钱","img":"20190415135908.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190415135908.jpg"},{"activityTitle":"会员集体婚礼","img":"20190408133905.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190408133905.jpg"},{"activityTitle":"会员服装秀","img":"20190408141310.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190408141310.jpg"},{"activityTitle":"饺子派对","img":"20190408142102.jpg","imgUrl":"http://47.99.100.88:81/file/showimg?filename=20190408142102.jpg"}]}
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
        private List<HotListBean> hotList;
        private List<VideoListBean> videoList;
        private List<SpellGoodsListBean> spellGoodsList;
        private List<ActivityListBean> activityList;

        public List<HotListBean> getHotList() {
            return hotList;
        }

        public void setHotList(List<HotListBean> hotList) {
            this.hotList = hotList;
        }

        public List<VideoListBean> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListBean> videoList) {
            this.videoList = videoList;
        }

        public List<SpellGoodsListBean> getSpellGoodsList() {
            return spellGoodsList;
        }

        public void setSpellGoodsList(List<SpellGoodsListBean> spellGoodsList) {
            this.spellGoodsList = spellGoodsList;
        }

        public List<ActivityListBean> getActivityList() {
            return activityList;
        }

        public void setActivityList(List<ActivityListBean> activityList) {
            this.activityList = activityList;
        }

        public static class HotListBean {
            /**
             * articleTitle : 商家们看过来~莱宝教你玩转专属的莱瘦商家后台！
             * img : 20190417173906.jpg
             * imgUrl : http://47.99.100.88:81/file/showimg?filename=20190417173906.jpg
             */

            private String articleTitle;
            private String img;
            private String imgUrl;
            private String id;
            private  String articleContent;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getArticleContent() {
                return articleContent;
            }

            public void setArticleContent(String articleContent) {
                this.articleContent = articleContent;
            }

            public String getArticleTitle() {
                return articleTitle;
            }

            public void setArticleTitle(String articleTitle) {
                this.articleTitle = articleTitle;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }

        public static class VideoListBean {
            /**
             * articleTitle : 莱瘦，一个可以不用节食、不用运动却能让你拥有完美身材的APP
             * articleContent : http://images.laiscn.cn/lifs-7M5GpCLz8M0g6yxVgQfHWQR
             */

            private String articleTitle;
            private String articleContent;
            private String imgUrl;
            private String id;

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
        }

        public static class SpellGoodsListBean {
            /**
             * goodsName : 2019夏装原创新款撞色高腰显瘦系带连体裤微喇阔腿长裤连身裤女潮
             * goodsUrl : 20190415111540.jpg
             * imgUrl : http://47.99.100.88:81/file/showimg?filename=20190415111540.jpg
             */

            private String goodsName;
            private String goodsUrl;
            private String imgUrl;
            private String id;
            private String articleTitle;
            private  String articleContent;

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

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsUrl() {
                return goodsUrl;
            }

            public void setGoodsUrl(String goodsUrl) {
                this.goodsUrl = goodsUrl;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }

        public static class ActivityListBean {
            /**
             * activityTitle : 你减肥  我出钱
             * img : 20190415135908.jpg
             * imgUrl : http://47.99.100.88:81/file/showimg?filename=20190415135908.jpg
             */

            private String activityTitle;
            private String img;
            private String imgUrl;
            private String id;
            private String articleTitle;

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

            private  String articleContent;
            public String getActivityTitle() {
                return activityTitle;
            }

            public void setActivityTitle(String activityTitle) {
                this.activityTitle = activityTitle;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
