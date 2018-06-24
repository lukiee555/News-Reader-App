package com.lokeshsoni.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lokeshsoni.newsreader.Common.Common;
import com.lokeshsoni.newsreader.Interface.IconBetterIdeaService;
import com.lokeshsoni.newsreader.Interface.ItemClickListener;
import com.lokeshsoni.newsreader.ListNews;
import com.lokeshsoni.newsreader.Model.IconBetterIdea;
import com.lokeshsoni.newsreader.Model.WebSite;
import com.lokeshsoni.newsreader.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//
//class ListSourceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//    ItemClickListener itemClickListener;
//    TextView source_title;
//    CircleImageView source_image;
//
//    public  ListSourceHolder(View itemView){
//        super(itemView);
//
//        source_image = (itemView).findViewById(R.id.source_image);
//        source_title = (itemView).findViewById(R.id.source_name);
//    }
//
//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }
//
//    @Override
//    public void onClick(View v) {
//        itemClickListener.onClick(v,getAdapterPosition(),false);
//    }
//}
//
//public class ListSourceAdapter extends  RecyclerView.Adapter<ListSourceHolder>{
//
//    private Context context;
//    private WebSite webSite;
//    private IconBetterIdeaService iconBetterIdeaService;
//
//    public ListSourceAdapter(Context context, WebSite webSite ) {
//        this.context = context;
//        this.webSite = webSite;
//        this.iconBetterIdeaService = Common.getIconService();
//    }
//
//    @NonNull
//    @Override
//    public ListSourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.source_layout,parent,false);
//
//        return new ListSourceHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ListSourceHolder holder, final int position) {
//
//        holder.source_title.setText(webSite.getSources().get(position).getName());
//
//
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                Log.d("I am here","on Click");
//                Intent intent = new Intent(context, ListNews.class);
//                intent.putExtra("source",webSite.getSources().get(position).getId());
//                context.startActivity(intent);
//
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return webSite.getSources().size();
//    }
//}
class ListSourceViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
{
    ItemClickListener itemClickListener;

    TextView source_title;
    CircleImageView source_image;

    public ListSourceViewHolder(View itemView) {
        super(itemView);

        source_image = (CircleImageView) itemView.findViewById(R.id.source_image);
        source_title = (TextView)itemView.findViewById(R.id.source_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{
    private Context context;
    private WebSite webSite;

    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;

        mService = Common.getIconService();
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout,parent,false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {



        holder.source_title.setText(webSite.getSources().get(position).getName());

        StringBuilder iconBetterAPI = new StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=");
        iconBetterAPI.append(webSite.getSources().get(position).getUrl());
        Log.d("URL ICON",iconBetterAPI.toString() + "");

        mService.getIconUrl(iconBetterAPI.toString())
                .enqueue(new Callback<IconBetterIdea>() {

                    @Override
                    public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                        if(response.body().getIcons().size() > 0){
                            Picasso.with(context)
                                    .load(response.body().getIcons().get(0).getUrl())
                                    .into(holder.source_image);
                        }
                    }

                    @Override
                    public void onFailure(Call<IconBetterIdea> call, Throwable t) {

                    }
                });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ListNews.class);
                intent.putExtra("source",webSite.getSources().get(position).getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}