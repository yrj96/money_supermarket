package me.sunlight.sdk.common.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import me.sunlight.sdk.common.R;
import me.sunlight.sdk.common.app.Activity;
import me.sunlight.sdk.common.app.Application;
/**
 * <pre>
 *     author : 戈传光
 *     e-mail : 1944633835@qq.com
 *     time   : 2017/12/11
 *     desc   : 分享
 *     version:
 * </pre>
 */
public class ShareHelper implements IShare, WbShareCallback {
    //微信分享 注册
    private static IWXAPI api;
    private String WeixinAppId;
    private String WeiboAppId;
    @DrawableRes
    private int icon;
    private String title;
    private String description;
    private String url;
    /**
     * weibo分享
     */
    private WbShareHandler shareHandler;
    private int mShareType = 1;
    /**
     *  weibo分享
     *  appkey 2704950429
     *  AppSecret ec79cd5306119ef8d3da83df3c46542b
     */
    String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";


    private static final int TYPE_WEXIN_CIRCLE = 0;
    private static final int TYPE_WEXIN_FRIEND = 1;

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }


    private ShareHelper(Build build) {
        this.WeixinAppId = build.wxAppId;
        this.WeiboAppId = build.weiboAppId;
        this.icon = build.icon;
        this.title = build.title;
        this.description = build.description;
        this.url = build.url;

        if (!TextUtils.isEmpty(WeixinAppId)) {
            //微信分享 注册
            api = WXAPIFactory.createWXAPI(mContext, WeixinAppId, false);
            api.registerApp(WeixinAppId);
        }

        if (!TextUtils.isEmpty(WeiboAppId)) {
            // 初始化微博
            WbSdk.install(mContext, new AuthInfo(mContext, WeiboAppId, "http://www.sina.com", SCOPE));
            shareHandler = new WbShareHandler((Activity) mContext);
            shareHandler.registerApp();
            shareHandler.doResultIntent(((Activity) mContext).getIntent(), this);
        }
    }

    public static class Build {
        int icon;
        String wxAppId;
        String weiboAppId;
        String title;
        String description;
        String url;

        public Build addWxAppId(String appId) {
            this.wxAppId = appId;
            return this;
        }

        public Build addWeiboAppId(String weiboAppId) {
            this.weiboAppId = weiboAppId;
            return this;
        }

        public Build addIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Build addTitle(String title) {
            this.title = title;
            return this;
        }

        public Build addDescription(String description) {
            this.description = description;
            return this;
        }

        public Build adddUrl(String url) {
            this.url = url;
            return this;
        }

        public ShareHelper builder() {
            return new ShareHelper(this);
        }
    }

    @Override
    public void initConfig() {
    }

    @Override
    public void share2WxFriend() {
        share2weixin(TYPE_WEXIN_FRIEND, title, description, url);
    }

    @Override
    public void share2WxCircle() {
        share2weixin(TYPE_WEXIN_CIRCLE, title, description, url);
    }

    @Override
    public void share2Weibo() {
        sendMessage(true, false);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
        weiboMessage.mediaObject = getWebpageObj();
        shareHandler.shareMessage(weiboMessage, mShareType == 1);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMessage(boolean hasText, boolean hasImage) {
        sendMultiMessage(hasText, hasImage);
    }

    @Override
    public void unBind() {
        if (api != null) {
            api.unregisterApp();
        }
        mContext = null;
    }


    //调用分享的方法
    // 0是朋友圈  1是好友
    public void share2weixin(int flag, String title, String description, String url) {
        if (api == null) {
            Toast.makeText(mContext, "请先传入微信的appid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!api.isWXAppInstalled()) {
            Toast.makeText(mContext, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(),
                icon == 0 ? R.drawable.ic_launcher : icon);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 1 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        boolean b = api.sendReq(req);
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = description;
        textObject.title = title;
        textObject.actionUrl = url;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    @Override
    public void onWbShareSuccess() {
        Application.showToast("分享成功");
        Logger.e("分享成功");
    }

    @Override
    public void onWbShareCancel() {
        Application.showToast("分享取消");
        Logger.e("分享取消");
    }

    @Override
    public void onWbShareFail() {
        Application.showToast("分享失败");
        Logger.e("分享失败");
    }

}
