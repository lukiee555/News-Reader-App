package com.lokeshsoni.newsreader.Model;

import android.graphics.drawable.Icon;

import java.util.List;

public class IconBetterIdea {

    private String url;
    private List<com.lokeshsoni.newsreader.Model.Icon> icons;

    public IconBetterIdea(){

    }

    public IconBetterIdea(String url, List<com.lokeshsoni.newsreader.Model.Icon> icons) {
        this.url = url;
        this.icons = icons;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<com.lokeshsoni.newsreader.Model.Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<com.lokeshsoni.newsreader.Model.Icon> icons) {
        this.icons = icons;
    }
}
