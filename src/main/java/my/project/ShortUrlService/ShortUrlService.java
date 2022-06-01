package my.project.ShortUrlService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;

public class ShortUrlService {
    private static final Logger logger = LogManager.getLogger();

    public String getShortUrl(String url) {
        RequestSql requestSql = new RequestSql();
        if (requestSql.isInBase(url)) {
            UrlInfo urlInfo = requestSql.requestByUrl(url);
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
            int lastId = requestSql.getLastId() + 1;
            logger.info("id: " + lastId);
            urlInfo.setId(lastId);
            requestSql.createNewRowInTable(urlInfo);
            return urlInfo.getHash();
        }
    }

    public String getOriginalUrl(String shortUrl) {
        RequestSql requestSql = new RequestSql();
        if (requestSql.isShortUrlInBase(shortUrl)) {
            String originalUrl = requestSql.requestByShortUrl(shortUrl);
            logger.info("Получена Оригинальная ссылка из базы: " + originalUrl);
            return originalUrl;
        } else {
            return null;
        }
    }
}

