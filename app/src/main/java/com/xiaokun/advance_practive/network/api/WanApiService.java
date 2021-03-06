package com.xiaokun.advance_practive.network.api;

import com.xiaokun.advance_practive.network.wanAndroid.TotalResEntity;
import com.xiaokun.baselib.network.WanBaseResponseEntity;
import com.xiaokun.advance_practive.network.wanAndroid.WanLoginEntityRes;
import com.xiaokun.advance_practive.network.wanAndroid.TotalResEntity.HomeArticles;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/12/06
 *      描述  ：wanandroid api
 *      版本  ：1.0
 * </pre>
 */
public interface WanApiService {

    String baseUrl = "http://www.wanandroid.com/";

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<WanBaseResponseEntity<WanLoginEntityRes.DataBean>> login(@Field("username") String username,
                                                                        @Field("password") String password);

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<WanBaseResponseEntity<TotalResEntity.Register>> register(@Field("username") String username, @Field("password") String password,
                                                                        @Field("repassword") String repassword);

    /**
     * 获取首页文章
     *
     * @param page 页码,从0开始
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<WanBaseResponseEntity<HomeArticles>> getHomeArticles(@Path("page") int page);

    /**
     * 获取首页banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<WanBaseResponseEntity<List<TotalResEntity.HomeBanner>>> getHomeBanner();

    /**
     * 获取常用网站
     *
     * @return
     */
    @GET("friend/json")
    Observable<WanBaseResponseEntity<TotalResEntity.CommonWebsite>> getCommonWebsite();

    /**
     * 获取搜索热词
     *
     * @return
     */
    @GET("hotkey/json")
    Observable<WanBaseResponseEntity<TotalResEntity.SearchHotWords>> getSearchHotWords();

    /**
     * 获取体系数据
     *
     * @return
     */
    @GET("tree/json")
    Observable<WanBaseResponseEntity<TotalResEntity.SystemData>> getSystemData();

    /**
     * 获取收藏数据
     *
     * @param page 页码,从0开始
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<WanBaseResponseEntity<TotalResEntity.Collect>> getCollect(@Path("page") int page);
}
