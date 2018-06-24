package com.lokeshsoni.newsreader.Interface;

import com.lokeshsoni.newsreader.Common.Common;
import com.lokeshsoni.newsreader.Model.News;
import com.lokeshsoni.newsreader.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {

    @GET
    Call<WebSite> getSources(@Url String url);

    @GET
    Call<News> getNewestArticles(@Url String url);

}
