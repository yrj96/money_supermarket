package me.sunlight.sdk.common.share;

/**
 * <pre>
 *     author : 戈传光
 *     e-mail : 1944633835@qq.com
 *     time   : 2017/12/11
 *     desc   :
 *     version:
 * </pre>
 */
public interface IShare {

    void initConfig();

    void share2WxFriend();

    void share2WxCircle();

    void share2Weibo();

    void unBind();

}
