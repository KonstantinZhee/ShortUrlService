package my.project.ShortUrlService.DAO;

import my.project.ShortUrlService.SQL_Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;


public class UrlInfoDAOImpl implements UrlInfoDAO {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public UrlInfo getUrlInfoByUrl(String url) {
        Session session = SQL_Connection.getFactory().openSession();
        UrlInfo urlInfo = session.get(UrlInfo.class, url);
        logger.info("Получен объект UrlInfo");
        session.close();
        return urlInfo;
    }

    @Override
    public String getUrlByShortUrl(String shortUrl) {
        Session session = SQL_Connection.getFactory().openSession();
        session.beginTransaction();
        String url = (String) session.createNativeQuery("select originalurl from Urls where hash = :shortUrl")
                .setParameter("shortUrl", shortUrl).getSingleResult();
        session.getTransaction().commit();
        logger.info("Получен url: " + url);
        session.close();
        return url;
    }

    @Override
    public void addUrlInfo(UrlInfo urlInfo) {
        Session session = SQL_Connection.getFactory().openSession();
        session.beginTransaction();
        session.save(urlInfo);
        session.getTransaction().commit();
        logger.info("Объект urlInfo записан в базу");
        session.close();
    }

    @Override
    public int getLastUrlInfoId() {
        Session session = SQL_Connection.getFactory().openSession();
        session.beginTransaction();
        int lastId = 0;
        Object object = session.createNativeQuery("select max(id) from Urls")
                .getSingleResult();
        session.getTransaction().commit();
        if (object == null) {
            logger.info("Получен last Id: " + lastId);
            return lastId;
        } else {
            lastId = (int) object;
            logger.info("Получен last Id: " + lastId);
            return lastId;
        }
    }

    @Override
    public boolean existShortUrl(String shortUrl) {
        Session session = SQL_Connection.getFactory().openSession();
        session.beginTransaction();
        boolean exist = (boolean) session.createNativeQuery("select exists (select hash from urls where hash = :hash )")
                .setParameter("hash", shortUrl).getSingleResult();
        session.getTransaction().commit();
        logger.info("Короткая ссылка есть в базе: " + exist);
        return exist;
    }


    @Override
    public boolean existUrl(String url) {
        Session session = SQL_Connection.getFactory().openSession();
        logger.info("Начало проверки в базе: ");
        boolean exist = (boolean) session.createNativeQuery("select exists (select originalurl from Urls where originalurl = :originalurl )")
                .setParameter("originalurl", url).getSingleResult();
        logger.info("Оригинальная ссылка есть в базе? : " + exist);
        return exist;
    }
}
