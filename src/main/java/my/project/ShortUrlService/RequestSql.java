package my.project.ShortUrlService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class RequestSql {
    public static final String BYURL = "select * from Urls where originalurl = ? ";
    public static final String BYSHORTURL = "select originalurl from Urls where hash = ? ";

    public static final String LASTID = "select max(id) from Urls";
    public static final String NEWROW = "insert into urls values (?, ?, ?, ?)";
    public static final String EXISTSURL = "select exists (select originalurl from Urls where originalurl = ? )";
    public static final String EXISTSSHORTURL = "select exists (select hash from urls where hash = ? )";
    private static final Connection connection;
    private static final Logger logger = LogManager.getLogger();

    static {
        try {
            connection = DS_SQL.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    public UrlInfo requestByUrl (String url){
        UrlInfo urlInfo = new UrlInfo();
        logger.info("Начало отправки запроса для получения короткой ссылки в БД SQL");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(BYURL);
            preparedStatement.setString(1, url);
            ResultSet result = preparedStatement.executeQuery();
            logger.info("Запрос отправлен в БД SQL");
            urlInfo = createUrlInfoFromSql(result);
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return urlInfo;
    }
    public UrlInfo createUrlInfoFromSql(ResultSet result) throws SQLException {
        UrlInfo urlInfo = new UrlInfo();
        logger.info("Создание объекта UrlInfo из данных от БД:");
        while (result.next()) {
            urlInfo.setId(result.getInt("id"));
            urlInfo.setHash(result.getString("hash"));
            urlInfo.setOriginalUrl(result.getString("originalurl"));
            urlInfo.setCreatedAt(result.getTimestamp("createdAt"));
        }
        logger.info("Объект UrlInfo создан по данным из SQL.");
        return urlInfo;
    }
    public int getLastId() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(LASTID);
            ResultSet resultSet = preparedStatement.executeQuery();
            int lastId = 0;
            while (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }
                logger.info("Получили номер последней записи: " + lastId);
                return lastId;
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return 0;
    }
    public void createNewRowInTable (UrlInfo urlInfo){
        int id = urlInfo.getId();
        String hash = urlInfo.getHash();
        String url = urlInfo.getOriginalUrl();
        Timestamp date = urlInfo.getCreatedAt();
        try {
            logger.info("Записываем новую ссылку в базу: \n" + id +"\n"+hash + "\n" + url + "\n" + date);
            PreparedStatement preparedStatement = connection.prepareStatement(NEWROW);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, hash);
            preparedStatement.setString(3, url);
            preparedStatement.setTimestamp(4, date);
            int rows = preparedStatement.executeUpdate();
            logger.info("Новая ссылка в базе. Добавлено строк: " + rows);
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }

    }
    public boolean isInBase (String url){
        try {
            logger.info("Проверка наличия в базе оригинальной ссылки: " + url);
            PreparedStatement preparedStatement = connection.prepareStatement(EXISTSURL);
            preparedStatement.setString(1, url);
            logger.info("Запрос подготовлен: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Запрос отправлен");
            boolean isInBase = false;
            while (resultSet.next()) {
                isInBase = resultSet.getBoolean("exists");
                logger.info("Результат проверки: " + isInBase);
            }
            return isInBase;
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        logger.info("Нет в базе.");
        return false;
    }

    public boolean isShortUrlInBase (String shortUrl){
        try {
            logger.info("Проверка наличия в базе короткой ссылки: " + shortUrl);
            PreparedStatement preparedStatement = connection.prepareStatement(EXISTSSHORTURL);
            preparedStatement.setString(1, shortUrl);
            logger.info("Запрос подготовлен: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Запрос отправлен");
            boolean isInBase = false;
            while (resultSet.next()) {
                isInBase = resultSet.getBoolean("exists");
                logger.info("Результат проверки: " + isInBase);
            }
            return isInBase;
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        logger.info("Нет в базе.");
        return false;
    }
    public String requestByShortUrl (String shortUrl){
        String originalUrl = null;
        logger.info("Начало отправки запроса для получения Оригинальной ссылки в БД SQL");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(BYSHORTURL);
            preparedStatement.setString(1, shortUrl);
            ResultSet result = preparedStatement.executeQuery();
            logger.info("Запрос отправлен в БД SQL");
            while (result.next()) {
                originalUrl = result.getString("originalurl");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return originalUrl;
    }


}
