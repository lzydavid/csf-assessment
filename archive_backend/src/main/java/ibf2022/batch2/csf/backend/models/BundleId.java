package ibf2022.batch2.csf.backend.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class BundleId {
    private String bundleId;

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }
    
    public JsonObject toJSON(){
        JsonObject o = Json.createObjectBuilder().add("bundleId", getBundleId()).build();
        return o;
    }
}
