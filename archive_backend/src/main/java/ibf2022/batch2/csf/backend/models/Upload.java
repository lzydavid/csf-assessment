package ibf2022.batch2.csf.backend.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class Upload {

    private String bundleID;
    private String date;
    private String title;
    private String name;
    private String comments;
    private List<String> urls;
    public String getBundleID() {
        return bundleID;
    }
    public void setBundleID(String bundleID) {
        this.bundleID = bundleID;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    @Override
    public String toString() {
        return "Upload [bundleID=" + bundleID + ", date=" + date + ", title=" + title + ", name=" + name + ", comments="
                + comments + ", urls=" + urls + "]";
    }

    public JsonObject toJSON() {
        JsonArray arr = Json.createArrayBuilder(getUrls()).build();
        JsonObject o = Json.createObjectBuilder()
            .add("bundleId", getBundleID())
            .add("date", getDate())
            .add("title", getTitle())
            .add("name", getName())
            .add("comments",getComments())
            .add("urls", arr)
            .build();
        return o;
    }
}
