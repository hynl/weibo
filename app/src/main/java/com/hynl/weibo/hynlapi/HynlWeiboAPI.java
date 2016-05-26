package com.hynl.weibo.hynlapi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlconstants.URLs;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboParameters;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.WeiboAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by tt6000 on 2016/3/11.
 */
public class HynlWeiboAPI extends WeiboAPI {

//    private RequestListener mListener;
    private Handler mainLooperHandler = new Handler(Looper.getMainLooper());
//        @Override
//        public void handleMessage(Message msg) {
//            String response = (String) msg.obj;
//            if (mListener != null)
//                mListener.onComplete(response);
//        }


    public HynlWeiboAPI(Oauth2AccessToken oauth2AccessToken) {
        super(oauth2AccessToken);
    }


    public HynlWeiboAPI(Context context) {
        this(AccessTokenKeeper.readAccessToken(context));
    }

    public void requestInMainLooper(String url, WeiboParameters params,
                                    String httpMethod, final RequestListener listener) {
        request(url, params, httpMethod, new RequestListener() {

            @Override
            public void onIOException(final IOException e) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onIOException(e);
                    }
                });
            }

            @Override
            public void onError(final WeiboException e) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(e);
                    }
                });
            }

            @Override
            public void onComplete4binary(final ByteArrayOutputStream responseOS) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete4binary(responseOS);
                    }
                });
            }

            //成功获取字符串json
            @Override
            public void onComplete(final String response) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete(response);
                    }
                });
//                mListener = listener;
//                Message message = mainLooperHandler.obtainMessage();
//                message.obj = response;
//                mainLooperHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void request(String url, WeiboParameters params,
                           String httpMethod, RequestListener listener) {
        // TODO Auto-generated method stub
        super.request(url, params, httpMethod, listener);
    }

    public void LogoutUser(RequestListener listener) {
        WeiboParameters para = new WeiboParameters();
        requestInMainLooper(URLs.RevokeOauth2, para, WeiboAPI.HTTPMETHOD_GET, listener);
    }
    public void addressGeo(String address1 ,RequestListener listener) {
        WeiboParameters para = new WeiboParameters();

            para.add("address", address1);



        requestInMainLooper(URLs.Geoaddress,para,WeiboAPI.HTTPMETHOD_GET,listener);
    }
    /**
     * 获取某个位置周边的动态
     *
     * @param lat 纬度。有效范围：-90.0到+90.0，+表示北纬。
     * @param lon 经度。有效范围：-180.0到+180.0，+表示东经。
     * @param range 搜索范围，单位米，默认2000米，最大11132米。


     * @param count 单页返回的记录条数，最大为50，默认为20。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false。
     * @param offset 传入的经纬度是否是纠偏过，false：没纠偏、true：纠偏过，默认为false。
     * @param listener
     */
    public void nearbyTimeline( String lat, String lon, int range,int count, int page, boolean base_app, boolean offset,
                                RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("lat", lat);
        params.add("long", lon);
        params.add("range", range);
//        params.add("starttime", starttime);
//        params.add("endtime", endtime);
//        params.add("sort", sort.ordinal());
        params.add("count", count);
        params.add("page", page);
        if (base_app) {
            params.add("base_app", 1);
        } else {
            params.add("base_app", 0);
        }
        if (offset) {
            params.add("offset", 1);
        } else {
            params.add("offset", 0);
        }
        requestInMainLooper(URLs.NearbyStatus, params, HTTPMETHOD_GET, listener);
    }
    /**
     * 获取附近发位置微博的人
     *
     * @param lat 纬度，有效范围：-90.0到+90.0，+表示北纬。
     * @param lon 经度，有效范围：-180.0到+180.0，+表示东经。
     * @param range 查询范围半径，默认为2000，最大为11132，单位米。


     * @param count 单页返回的记录条数，默认为20，最大为50。
     * @param page 返回结果的页码，默认为1。
     * @param offset 传入的经纬度是否是纠偏过，false：没纠偏、true：纠偏过，默认为false。
     * @param listener
     */
    public void nearbyUsers( String lat, String lon, int range,
                               int count, int page, boolean offset, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("lat",lat);
        params.add("long", lon);
        params.add("range", range);
//        params.add("starttime", starttime);
//        params.add("endtime", endtime);
//        params.add("sort", sort.ordinal());
        params.add("count", count);
        params.add("page", page);
        if (offset) {
            params.add("offset", 1);
        } else {
            params.add("offset", 0);
        }
        requestInMainLooper(URLs.Nearbyuser, params, HTTPMETHOD_GET, listener);
    }
    /**
     * 获取用户的粉丝列表(最多返回5000条数据)
     *
     * @param uid 需要查询的用户UID。
     * @param count 单页返回的记录条数，默认为50，最大不超过200。
     * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0。
     * @param listener
     */
    public void followers( String  uid, int count, int cursor,
                           RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("uid", uid);
        params.add("count", count);
        params.add("cursor", cursor);

        requestInMainLooper(URLs.Followers, params, HTTPMETHOD_GET, listener);
    }
   /**
    * 获取各类消息未读数
   */
   public void reminds(String uid,RequestListener listener){
       WeiboParameters paras = new WeiboParameters();
       paras.add("uid",uid);
       requestInMainLooper(URLs.Remind,paras,HTTPMETHOD_GET,listener);
   }
    /**
     * 获取用户的关注列表
     *
     * @param uid 需要查询的用户UID。
     * @param count 单页返回的记录条数，默认为50，最大不超过200。
     * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0。
     * @param listener
     */
    public void friends( String uid, int count, int cursor,
                         RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("uid", uid);
        params.add("count", count);
        params.add("cursor", cursor);
//        if (trim_status) {
//            params.add("trim_status", 1);
//        } else {
//            params.add("trim_status", 0);
//        }
        requestInMainLooper(URLs.Friends, params, HTTPMETHOD_GET, listener);
    }
    /**
     * 获取最新的提到当前登录用户的评论，即@我的评论
     *  若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
//     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
//     * @param filter_by_source 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0。
     * @param since_id 若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param max_id 若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param listener
     */
    public void mentions(long since_id, long max_id, int count, int page,
                         RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("since_id", since_id);
        params.add("max_id", max_id);
        params.add("count", count);
        params.add("page", page);
//        params.add("filter_by_author", filter_by_author.ordinal());
//        params.add("filter_by_source", filter_by_source.ordinal());
        requestInMainLooper(URLs.Mentions, params, HTTPMETHOD_GET, listener);
    }
    /**
     * 取消收藏一条微博
     *
     * @param id 要取消收藏的微博ID。
     * @param listener
     */
    public void destroy( long id, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("id", id);
        requestInMainLooper(URLs.Delete, params, HTTPMETHOD_POST, listener);
    }
    /**
     * 添加一条微博到收藏里
     *
     * @param id 要收藏的微博ID。
     * @param listener
     */
    public void create( long id, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("id", id);
        requestInMainLooper(URLs.Creat, params, HTTPMETHOD_POST, listener);
    }

    /**
     * 获取当前登录用户的收藏列表
     *
     * @param count    单页返回的记录条数，默认为50。
     * @param page     返回结果的页码，默认为1。
     * @param listener
     */
    public void favorites(int count, int page, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("count", count);
        params.add("page", page);
        requestInMainLooper(URLs.Favorites, params, HTTPMETHOD_GET, listener);

    }

    /**
     * 获取用户信息(uid和screen_name二选一)
     *
     * @param uid         根据用户ID获取用户信息
     * @param screen_name 需要查询的用户昵称。
     * @param listener
     */
    public void usersShow(String uid, String screen_name, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        if (!TextUtils.isEmpty(uid)) {
            params.add("uid", uid);
        } else if (!TextUtils.isEmpty(screen_name)) {
            params.add("screen_name", screen_name);
        }
        requestInMainLooper(URLs.usersShow, params, WeiboAPI.HTTPMETHOD_GET, listener);
    }

    /**
     * 获取某个用户最新发表的微博列表(uid和screen_name二选一)
     *
     * @param uid         需要查询的用户ID。
     * @param screen_name 需要查询的用户昵称。
     * @param page        返回结果的页码。(单页返回的记录条数，默认为20。)
     * @param listener
     */
    public void statusesUser_timeline(long uid, String screen_name, long page, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        if (uid > 0) {
            params.add("uid", uid);
        } else if (!TextUtils.isEmpty(screen_name)) {
            params.add("screen_name", screen_name);
        }
        params.add("page", page);
        requestInMainLooper(URLs.statusesUser_timeline, params, WeiboAPI.HTTPMETHOD_GET, listener);
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博
     *
     * @param page     返回结果的页码。(单页返回的记录条数，默认为20。)
     * @param listener
     */
    public void statusesHome_timeline(long page, RequestListener listener) {
        WeiboParameters parameters = new WeiboParameters();
        parameters.add("page", page);
        requestInMainLooper(URLs.statusesHome_timeline, parameters, HTTPMETHOD_GET, listener);
    }

    /**
     * 根据微博ID返回某条微博的评论列表
     *
     * @param id       需要查询的微博ID。
     * @param page     返回结果的页码。(单页返回的记录条数，默认为50。)
     * @param listener
     */
    public void commentsShow(long id, long page, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("id", id);
        params.add("page", page);
        requestInMainLooper(URLs.commentsShow, params, WeiboAPI.HTTPMETHOD_GET, listener);
    }

    /**
     * 对一条微博进行评论
     *
     * @param id       需要评论的微博ID。
     * @param comment  评论内容
     * @param listener
     */
    public void commentsCreate(long id, String comment, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.add("id", id);
        params.add("comment", comment);
        requestInMainLooper(URLs.commentsCreate, params, WeiboAPI.HTTPMETHOD_POST, listener);
    }

    /**
     * 发布或转发一条微博
     * <p/>
     * //         * @param context
     * //         * @param status
     * //         *            要发布的微博文本内容。
     * //         * @param imgFilePath
     * //         *            要上传的图片文件路径(为空则代表发布无图微博)。
     * //         * @param retweetedStatsId
     * //         *            要转发的微博ID(<=0时为原创微博)。
     * //         * @param mListener
     */
    public void statusesSend(String status, String imgFilePath, long retweetedStatusId, RequestListener listener) {
        String url;

        WeiboParameters params = new WeiboParameters();
        params.add("status", status);
        if (retweetedStatusId > 0) {
            url = URLs.statusesRepost;
            params.add("id", retweetedStatusId);
        } else if (!TextUtils.isEmpty(imgFilePath)) {
            params.add("pic", imgFilePath);
            url = URLs.statusesUpload;
        } else {
            url = URLs.statusesUpdate;
        }
        requestInMainLooper(url, params, WeiboAPI.HTTPMETHOD_POST, listener);
    }

}


