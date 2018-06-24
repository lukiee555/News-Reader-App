package com.lokeshsoni.newsreader.Model;

import java.util.List;

public class WebSite {
    private String status;
    private List<Sources> sources;

    public WebSite() {

    }

    public WebSite(String status, List<Sources> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sources> getSources() {
        return sources;
    }

    public void setSources(List<Sources> sources) {
        this.sources = sources;
    }
}
