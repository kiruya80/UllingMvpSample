package com.example.architecture.common;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 17.
 * @description :
 * @since :
 */

public class ApiUrl  {

    public static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    public static final String HTTP_URL = "http";
    public static final String ENCODING = "UTF-8";
    public static final String TYPE_JSON = ".json";
    public static final String HEADER_BASIC = "Basic ";
    public static final String SLASH = "/";
    public static final String QUESTION = "?";
    public static final String EQUALS = "=";
    public static final String HASH_TAG = "#";
    public static final String AND = "&";


    public static final String BASE_URL = "https://api.stackexchange.com/2.2/";
    private String testUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&pageToken=CDIQAA&q=vr+360&regionCode=us&type=video&videoCategoryId=30&videoDefinition=high&key=AIzaSyAwRlzD0rSndasixYHrVe4hdnOKeVGtCGg\n";
    //    https://twitter.com/search?q=%23iPhone8&src=tyah
    //    https://api.twitter.com/1.1/search/tweets.json?q=%23iPhone8&src=tyah
    private String testUrl2 = "https://api.stackexchange.com/2.2/answers?order=desc&sort=activity&site=stackoverflow";

//    public static SOService getSOService() {
//        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
//    }
}
