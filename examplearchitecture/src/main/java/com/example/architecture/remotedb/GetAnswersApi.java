package com.example.architecture.remotedb;

import com.example.architecture.entities.retrofit.AnswersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by P100651 on 2017-07-04.
 *
 * https://futurestud.io/tutorials/retrofit-2-add-multiple-query-parameter-with-querymap
 *
 * https://stackoverflow.com/questions/36730086/retrofit-2-url-query-parameter
 *
 */
public interface GetAnswersApi {

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<AnswersResponse> getAnswers();

    // /2.2/answers?page=3&pagesize=10&order=desc&sort=activity&site=stackoverflow
    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<AnswersResponse> getAnswers(@Query("page") int page, @Query("pagesize") int pagesize);


    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<AnswersResponse> getAnswers(@Query("tagged") String tags);

//    @GET("repos/{owner}/{repo}/contributors")
//    Call<List<Contributor>> repoContributors(
//            @Path("owner") String owner,
//            @Path("repo") String repo);


//    @GET("/news")
//    Call<List<News>> getNews(
//            @QueryMap Map<String, String> options
//    );
//
//    private void fetchNews() {
//        Map<String, String> data = new HashMap<>();
//        data.put("author", "Marcus");
//        data.put("page", String.valueOf(2));
//
//        // simplified call to request the news with already initialized service
//        Call<List<News>> call = newsService.getNews(data);
//        call.enqueue(…);
//    }
//
//    http://your.api.url/news?page=2&author=Marcus
}