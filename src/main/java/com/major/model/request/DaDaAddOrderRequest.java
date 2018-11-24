package com.major.model.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author xuquanming
 * @date 2018/9/10 15:19
 */
@Getter
@Setter
public class DaDaAddOrderRequest {
    /**
     * 门店编号，门店创建后可在门店列表和单页查看
     */
    @NotBlank(message = "门店编号不能为空")
    @JSONField(name = "shop_no")
    private String shopNo;
    /**
     * 第三方订单ID
     */
    @NotBlank(message = "第三方订单ID不能为空")
    @JSONField(name = "origin_id")
    private String originId ;
    /**
     * 订单所在城市的code（查看各城市对应的code值）
     */
    @NotBlank(message = "城市的code不能为空")
    @JSONField(name = "city_code")
    private  String cityCode	;
    /**
     * 订单金额
     */
    @NotBlank(message = "订单金额不能为空")
    @JSONField(name = "cargo_price")
    private  Double cargoPrice;
    /**
     * 	是否需要垫付 1:是 0:否 (垫付订单金额，非运费)
     */
    @NotBlank(message = "是否需要垫付不能为空")
    @JSONField(name = "is_prepay")
    private Integer isPrepay	;
    /**
     *收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    @JSONField(name = "receiver_name")
    private  String receiverName;
    /**
     *收货人地址
     */
    @NotBlank(message = "收货人地址不能为空")
    @JSONField(name = "receiver_address")
    private  String receiverAddress	;
    /**
     *收货人地址维度（高德坐标系）
     */
    @NotBlank(message = "收货人地址维度不能为空")
    @JSONField(name = "receiver_lat")
    private Double receiverLat;
    /**
     *收货人地址经度（高德坐标系）
     */
    @NotBlank(message = "收货人地址经度不能为空")
    @JSONField(name = "receiver_lng")
    private Double receiverLng;
    /**
     *回调URL（查看回调说明）
     */
    @NotBlank(message = "回调URL不能为空")
    @JSONField(name = "callback")
    private  String callback;
    /**
     *收货人手机号（手机号和座机号必填一项）
     */
    @JSONField(name = "receiver_phone")
    private  String receiverPhone	;
    /**
     *收货人座机号（手机号和座机号必填一项）
     */
    @JSONField(name = "receiver_tel")
    private String receiverTel;
    /**
     *小费（单位：元，精确小数点后一位）
     */
    @JSONField(name = "tips")
    private Double tips;
    /**
     *订单备注
     */
    @JSONField(name = "info")
    private  String info	;
    /**
     *订单商品类型：食品小吃-1,饮料-2,鲜花-3,文印票务-8,便利店-
     * 9,水果生鲜-13,同城电商-19, 医药-20,蛋糕-21,酒品-
     * 24,小商品市场-25,服装-26,汽修零配-27,数码-28,小龙虾-29, 其他-5
     */
    @JSONField(name = "cargo_type")
    private Integer cargoType;
    /**
     *	订单重量（单位：Kg）
     */
    @JSONField(name = "cargo_weight")
    private  Double cargoWeight;
    /**
     *订单商品数量
     */
    @JSONField(name = "cargo_num")
    private  Integer cargoNum;
    /**
     *发票抬头
     */
    @JSONField(name = "invoice_title")
    private String invoiceTitle;
    /**
     *订单来源标示（该字段可以显示在达达app订单详情页面，只支持字母，最大长度为10）
     */
    @JSONField(name = "origin_mark")
    private  String originMark;
    /**
     *	订单来源编号（该字段可以显示在达达app订单详情页面，支持字母和数字，最大长度为30）
     */
    @JSONField(name = "origin_mark_no")
    private String originMarkNo;
    /**
     *  是否使用保价费（0：不使用保价，1：使用保价； 同时，请确保填写了订单金额（cargo_price））
     *     商品保价费(当商品出现损坏，可获取一定金额的赔付)
     *     保费=配送物品实际价值*费率（5‰），配送物品价值及最高赔付不超过10000元，
     *     最高保费为50元（物品价格最小单位为100元，不足100元部分按100元认定，保价费向上取整数，
     *     如：物品声明价值为201元，保价费为300元*5‰=1.5元，取整数为2元。）
     *     若您选择不保价，若物品出现丢失或损毁，最高可获得平台30元优惠券。 （优惠券直接存入用户账户中）。
     */
    @JSONField(name = "is_use_insurance")
    private Integer isUseInsurance;

    /**
     *收货码（0：不需要；1：需要。收货码的作用是：骑手必须输入收货码才能完成订单妥投）
     */
    @JSONField(name = "is_finish_code_needed")
    Integer isFinishCodeNeeded;
    /**
     *预约发单时间（预约时间unix时间戳(10位),精确到分;整10分钟为间隔，并且需要至少提前20分钟预约。）
     */
    @JSONField(name = "delay_publish_time")
    Integer delayPublishTime	;
    /**
     *是否选择直拿直送（0：不需要；1：需要。选择直拿只送后，
     * 同一时间骑士只能配送此订单至完成，同时，也会相应的增加配送费用）
     */
    @JSONField(name = "is_direct_delivery")
    Integer isDirectDelivery;
}
