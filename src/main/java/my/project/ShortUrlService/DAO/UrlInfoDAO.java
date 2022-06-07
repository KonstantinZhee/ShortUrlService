package my.project.ShortUrlService.DAO;

import java.sql.SQLException;

public interface UrlInfoDAO {
    UrlInfo getUrlInfoByUrl(String url) throws SQLException;

    String getUrlByShortUrl(String shortUrl) throws SQLException;

    void addUrlInfo(UrlInfo urlInfo) throws SQLException;

    int getLastUrlInfoId();

    boolean existShortUrl(String shortUrl) throws SQLException;

    boolean existUrl(String url) throws SQLException;
}

