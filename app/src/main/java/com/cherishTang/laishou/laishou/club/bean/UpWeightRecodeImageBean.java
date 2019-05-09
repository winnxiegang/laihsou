package com.cherishTang.laishou.laishou.club.bean;

public class UpWeightRecodeImageBean {

    /**
     * code : 200
     * message :
     * data : {"fileName":"http://47.99.100.88:81/weight/201904261609067572.jpg","fileBase64":null}
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
         * fileName : http://47.99.100.88:81/weight/201904261609067572.jpg
         * fileBase64 : null
         */

        private String fileName;
        private Object fileBase64;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Object getFileBase64() {
            return fileBase64;
        }

        public void setFileBase64(Object fileBase64) {
            this.fileBase64 = fileBase64;
        }
    }
}
