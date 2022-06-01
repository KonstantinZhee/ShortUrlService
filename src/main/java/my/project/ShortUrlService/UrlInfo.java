package my.project.ShortUrlService;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.sql.Timestamp;

public class UrlInfo {
    @JsonProperty("id")
    private int id;
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("originalurl")
    private String originalUrl;
    @JsonProperty("createdAt")
    private Timestamp createdAt;

    public UrlInfo() {
    }

    public UrlInfo(int id, String hash, String originalUrl, Timestamp createdAt) {
        this.id = id;
        this.hash = hash;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


}
