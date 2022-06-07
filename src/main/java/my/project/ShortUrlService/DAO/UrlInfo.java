package my.project.ShortUrlService.DAO;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "Urls")
public class UrlInfo {

    @Column(name = "id")
    private Integer id;
    @Column(name = "hash")
    private String hash;
    @Id
    @Column(name = "originalurl")
    private String originalUrl;
    @Column(name = "createdAt")
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
