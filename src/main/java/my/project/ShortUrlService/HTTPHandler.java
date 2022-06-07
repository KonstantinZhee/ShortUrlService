package my.project.ShortUrlService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class HTTPHandler {
    private static final Logger logger = LogManager.getLogger();


    HTTPHandler() {
        requestTaker();
    }

    void requestTaker() {
        post("/", (request, response) -> {
            String url = request.body();
            logger.info("***** \n Получена ссылка от Клиента: " + url);
            ShortUrlService shortUrlService = new ShortUrlService();
            String shortUrl = shortUrlService.getShortUrl(url);
            logger.info("Отправлена короткая ссылка Клиенту: " + shortUrl + "\n *****");
            return shortUrl;
        });
        get("/:url", (request, response) -> {
            logger.info("***** \n Получен GET запрос.");
            String shortUrl = request.params(":url");
            logger.info("..по короткой ссылке: " + shortUrl);
            ShortUrlService shortUrlService = new ShortUrlService();
            String originalUrl = shortUrlService.getOriginalUrl(shortUrl);
            logger.info("Перенаправляем Клиента по оригинальной ссылке " + originalUrl + "\n *****");
            response.redirect(originalUrl);
            return response;
        });
    }
}