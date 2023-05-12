package ibf2022.batch2.csf.backend.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Bundle {
    String title;
    String date;
    String id;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public static JsonArray toJSONArr(List<Bundle> b) {

        JsonArrayBuilder arrBld = Json.createArrayBuilder();
        for (Bundle bundle : b) {
            JsonObject o = Json.createObjectBuilder()
                .add("title", bundle.getTitle())
                .add("date", bundle.getDate())
                .add("id", bundle.getId())
                .build();
            arrBld.add(o);
        }
        JsonArray arr = arrBld.build();
        return arr;
    }
    @Override
    public String toString() {
        return "Bundle [title=" + title + ", date=" + date + ", id=" + id + "]";
    }
}
