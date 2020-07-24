package com.mmall.common;


import com.google.common.collect.Sets;

import java.util.Set;

public class Const {

    public static final String CURRENT_USER="current_user";
    public static final String USER_NAME="username";
    public static final String EMAIL="email";

    public interface CartChecked{
        int CHECKED = 1 ;
        int UN_CHECKED = 0 ;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface role{
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public interface productOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("ASC","DESC");
    }

    public enum orderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已支付"),
        SHIPPING(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSED(60,"订单关闭");
        private int code;
        private String status;

        orderStatusEnum(int code, String status) {
            this.code = code;
            this.status = status;
        }

        public static orderStatusEnum codeOf(int code){
            for (orderStatusEnum status:values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            throw new  RuntimeException("没有找到对应枚举");
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public interface alipayCallBack{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESOPNSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum payPlatFormEnum{
        ALIPAY(1,"支付宝");
        private int code;
        private String status;

        payPlatFormEnum(int code, String status) {
            this.code = code;
            this.status = status;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public enum payEnum {
        ONLINE_PAY(1, "在线支付");
        private int code;
        private String status;

        payEnum(int code, String status) {
            this.code = code;
            this.status = status;
        }

        public static payEnum codeOf(int code){
            for (payEnum pay:values()) {
                if (pay.getCode() == code) {
                    return pay;
                }
            }
            throw new  RuntimeException("没有找到对应枚举");
        }
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public enum productStatusEnum{
        ON_SALE(1,"在架");
        private int status;
        private String desc;

        productStatusEnum(int status,String desc){
            this.status = status;
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
