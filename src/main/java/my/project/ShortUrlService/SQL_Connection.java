package my.project.ShortUrlService;

import my.project.ShortUrlService.DAO.UrlInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SQL_Connection {
    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;

    private SQL_Connection() {
    }

    public static SessionFactory getFactory() {
        logger.info("Конект к базе");
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.connection.url", Constants.DB_SQL_URL)
                        .setProperty("hibernate.connection.username", Constants.DB_SQL_USERNAME)
                        .setProperty("hibernate.connection.password", Constants.DB_SQL_PASSWORD)
                        .setProperty("connection.driver_class", "org.postgresql.Driver")
                        .setProperty("dialect", "org.hibernate.dialect.PostgreSQLDialect")
                        .setProperty("current_session_context_class", "thread")
                        .addAnnotatedClass(UrlInfo.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Исключение: " + e);
            }
        }
        return sessionFactory;
    }

}
