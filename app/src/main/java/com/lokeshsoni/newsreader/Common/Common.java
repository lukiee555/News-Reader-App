package com.lokeshsoni.newsreader.Common;

import com.lokeshsoni.newsreader.Interface.IconBetterIdeaService;
import com.lokeshsoni.newsreader.Interface.NewsService;
import com.lokeshsoni.newsreader.Remote.IconBetterIdeaClient;
import com.lokeshsoni.newsreader.Remote.RetrofitClient;

public class Common {

    private static final String BASE_URL = "https://ww.newsapi.org/";
    public static final String API_KEY = "8d1d318ab1e546fdb315d880b0ed6f77";

    public static NewsService getNewsService(){
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService(){
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

//https://newsapi.org/v2/sources?language=en&country=in&apiKey=8d1d318ab1e546fdb315d880b0ed6f77

    public static String getUrl(String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/sources?language=en&country=us");
        return apiUrl
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }

    public static String getAPIUrl(String source,String sortBy ,String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }
}
