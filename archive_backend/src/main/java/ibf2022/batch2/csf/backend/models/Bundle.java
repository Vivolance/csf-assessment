package ibf2022.batch2.csf.backend.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bundle {
    private String bundleId;
    private LocalDate date;
    private String title;
    private String name;
    private String comments;
    private ArrayList<String> urls;

    public Bundle(String bundleId, LocalDate date, String title, String name, String comments, ArrayList<String> urls) {
        this.bundleId = bundleId;
        this.date = date;
        this.title = title;
        this.name = name;
        this.comments = comments;
        this.urls = urls;
    }
    
    public Bundle() {
        
    }

    public String getBundleId() {
        return bundleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getComments() {
        return comments;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    /**
     * Define a method which serializes a Bundle Object into a jakarta JSONObject
     */
    // public JsonObject serialize() {
    //     JsonObject bundleJson = new JsonObject();
    //     bundleJson.("bundleId", this.bundleId);
    //     bundleJson.put("date", this.date);
    //     bundleJson.put("title", this.title);
    //     bundleJson.put("name", this.name);
    //     bundleJson.put("comments", this.comments);
    //     bundleJson.put("urls", this.urls);
    //     return bundleJson;
    // }
}