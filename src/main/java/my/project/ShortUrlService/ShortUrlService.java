package my.project.ShortUrlService;

import my.project.ShortUrlService.DAO.Factory;
import my.project.ShortUrlService.DAO.UrlInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ShortUrlService {
    private static final Logger logger = LogManager.getLogger();

    public String getShortUrl(String url) throws SQLException {
        Factory factory = new Factory();
        if (factory.getInstance().getUrlInfoDAO().existUrl(url)) {
            UrlInfo urlInfo = factory.getInstance().getUrlInfoDAO().getUrlInfoByUrl(url);
            String shortUrl = urlInfo.getHash();
            logger.info("Получена короткая ссылка из базы: " + shortUrl);
            return shortUrl;
        } else {
            UrlInfo urlInfo = new UrlInfo();
            urlInfo.setOriginalUrl(url);
            logger.info("Записана оригинальная ссылка: " + url);
            CodeGenerator codeGenerator = new CodeGenerator();
            String shortUrl = codeGenerator.generate(6);
            logger.info("Получена короткая ссылка: " + shortUrl);
            urlInfo.setHash(shortUrl);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            logger.info("Записано время создания короткой ссылки: " + timestamp);
            urlInfo.setCreatedAt(timestamp);
            int lastId = factory.getInstance().getUrlInfoDAO().getLastUrlInfoId() + 1;
            logger.info("id: " + lastId);
            urlInfo.setId(lastId);
            factory.getInstance().getUrlInfoDAO().addUrlInfo(urlInfo);
            return urlInfo.getHash();
        }
    }

    public String getOriginalUrl(String shortUrl) throws SQLException {
        Factory factory = new Factory();
        if (factory.getInstance().getUrlInfoDAO().existShortUrl(shortUrl)) {
            String originalUrl = factory.getInstance().getUrlInfoDAO().getUrlByShortUrl(shortUrl);
            logger.info("Получена Оригинальная ссылка из базы: " + originalUrl);
            return originalUrl;
        } else {
            return null;
        }
    }
}



