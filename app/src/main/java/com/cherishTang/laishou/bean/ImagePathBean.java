package com.cherishTang.laishou.bean;

/**
 * Created by 方舟 on 2018/1/23.
 *
 */

public class ImagePathBean {
    private Long SeqId;//图片id
    private String ImageUrl;//图片路径

    public ImagePathBean() {
    }

    public ImagePathBean(Long seqId, String imageUrl) {
        SeqId = seqId;
        ImageUrl = imageUrl;
    }

    public Long getSeqId() {
        return SeqId;
    }

    public void setSeqId(Long seqId) {
        SeqId = seqId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
