package com.hynl.weibo.hynlconstants;
/**
 * Created by tt6000 on 2016/3/11.
 */
public interface URLs {

	// host
	String BASE_URL = "https://api.weibo.com/2/";
	//地点详情
	String Geoaddress=BASE_URL+"location/geo/address_to_geo.json";
	//附近位置的微博
	String NearbyStatus=BASE_URL+"place/nearby_timeline.json";
	//附近位置的人
	String Nearbyuser=BASE_URL+"place/nearby/users.json";
	//粉丝列表
	String Followers=BASE_URL+"friendships/followers.json";
	//各种消息未读数
	String Remind="https://rm.api.weibo.com/2/remind/unread_count.json";
	//我的新朋友（关注人列表）
	String Friends = BASE_URL+"friendships/friends.json";
	//获取@我的微博信息
	String Mentions=BASE_URL+"comments/mentions.json";
	//删除微博收藏
	String Delete =BASE_URL+"favorites/destroy.json";
	//添加微博收藏
	String Creat =BASE_URL+"favorites/create.json";
	//获取收藏微博列表
	String Favorites = BASE_URL+"favorites.json";
	//注销当前用户
	String RevokeOauth2="https://api.weibo.com/oauth2/revokeoauth2";
	// 获取用户信息
	String usersShow = BASE_URL + "users/show.json";
	// 获取某个用户最新发表的微博列表
	String statusesUser_timeline = BASE_URL + "statuses/user_timeline.json";
	// 首页微博列表
	String statusesHome_timeline = BASE_URL + "statuses/home_timeline.json";
	// 微博评论列表
	String commentsShow = BASE_URL + "comments/show.json";
	// 对一条微博进行评论
	String commentsCreate = BASE_URL + "comments/create.json";
	// 转发一条微博
	String statusesRepost = BASE_URL + "statuses/repost.json";
	// 发布一条微博(带图片)
	String statusesUpload = BASE_URL + "statuses/upload.json";
	// 发布一条微博(不带图片)
	String statusesUpdate = BASE_URL + "statuses/update.json";

	
}
