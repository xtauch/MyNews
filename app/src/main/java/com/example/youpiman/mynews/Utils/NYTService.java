package com.example.youpiman.mynews.Utils;



import com.example.youpiman.mynews.Models.ArticleSearch.ArticleSearch;
import com.example.youpiman.mynews.Models.MostPopular.MostPopular;
import com.example.youpiman.mynews.Models.TopStories.TopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NYTService {

    String API_KEY = "685c780d37994ac8b162cbf2f3b4ef24";

    @GET("topstories/v2/{section}.json?api-key="+API_KEY)
    Observable<TopStories> getTopStories(@Path("section") String section);

    @GET("mostpopular/v2/mostviewed/{section}/1.json?api-key="+API_KEY)
    Observable<MostPopular> getMostPopular(@Path("section") String section);


    @GET("search/v2/articlesearch.json")
    Observable<ArticleSearch> getArticleSearch(@Query("q") String query,
                                               @Query("begin_date") String beginDate,
                                               @Query("end_date") String endDate,
                                               @Query("fq") String newsDesk,
                                               @Query("api-key") String apiKey);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
