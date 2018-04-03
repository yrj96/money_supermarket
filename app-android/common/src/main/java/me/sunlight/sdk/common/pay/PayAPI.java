package me.sunlight.sdk.common.pay;

import me.sunlight.sdk.common.pay.wxchat.WechatPayAPI;
import me.sunlight.sdk.common.pay.wxchat.WechatPayReq;

/**
 * 支付的API
 *
 * @author 戈传光
 */
public class PayAPI {


    private static final Object mLock = new Object();
    private static PayAPI mInstance;

    public static PayAPI getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new PayAPI();
                }
            }
        }
        return mInstance;
    }

    /**
     * 微信支付请求
     *
     * @param wechatPayReq
     */
    public void sendPayRequest(WechatPayReq wechatPayReq) {
        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
    }

    //----------------微信支付的使用如下------------------//
//    //1.创建微信支付请求
//    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
//            .with(this) //activity实例
//            .setAppId(appid) //微信支付AppID
//            .setPartnerId(partnerid)//微信支付商户号
//            .setPrepayId(prepayid)//预支付码
////								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
//            .setNonceStr(noncestr)
//            .setTimeStamp(timestamp)//时间戳
//            .setSign(sign)//签名
//            .create();
//    //2.发送微信支付请求
//    PayAPI.getInstance().sendPayRequest(wechatPayReq);
//
//

}

