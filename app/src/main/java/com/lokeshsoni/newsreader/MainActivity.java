package com.lokeshsoni.newsreader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lokeshsoni.newsreader.Adapter.ListSourceAdapter;
import com.lokeshsoni.newsreader.Common.Common;
import com.lokeshsoni.newsreader.Interface.NewsService;
import com.lokeshsoni.newsreader.Model.WebSite;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    SpotsDialog alertDialog;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Paper.init(this);

        mService = Common.getNewsService();

        //Init
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });



        listWebsite = findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        alertDialog = new SpotsDialog(this);

        loadWebsiteSource(false);

    }

    private void loadWebsiteSource(boolean isRefreshed) {
        if(!isRefreshed)
        {

            Log.d("I AM HERE","loadWebsiteSource");

            String cache = Paper.book().read("cache");
            if(cache != null && !cache.isEmpty() && !cache.equals("null")) // If have cache
            {
                WebSite website = new Gson().fromJson(cache,WebSite.class); // Convert cache from Json to Object
                adapter = new ListSourceAdapter(getBaseContext(),website);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else // If not have cache
            {

                alertDialog.show();
                Log.d("I AM HERE","else");
                //Fetch new data
                mService.getSources(Common.getUrl(Common.API_KEY)).enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        Log.d("I AM HERE","onResponse");
                        adapter  = new ListSourceAdapter(getBaseContext(),response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);
                        Log.d("DATA",response+"");
                        //Save to cache
                        Paper.book().write("cache",new Gson().toJson(response.body()));

                        alertDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {
                        Log.d("I AM HERE","onFailure");
                        alertDialog.dismiss();
                    }
                });
            }
        }
        else // If from Swipe to Refresh
        {

            swipeRefreshLayout.setRefreshing(true);
            //Fetch new data
            mService.getSources(Common.getUrl(Common.API_KEY)).enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter  = new ListSourceAdapter(getBaseContext(),response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //Save to cache
                    Paper.book().write("cache",new Gson().toJson(response.body()));

                    //Dismiss refresh progressring
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });

        }
    }
}
