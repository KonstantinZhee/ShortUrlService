package my.project.ShortUrlService.DAO;

public class Factory {
    private static UrlInfoDAO urlInfoDAO = null;
    private static Factory instance = null;

    public synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public UrlInfoDAO getUrlInfoDAO() {
        if (urlInfoDAO == null) {
            urlInfoDAO = new UrlInfoDAOImpl();
        }
        return urlInfoDAO;
    }
}
