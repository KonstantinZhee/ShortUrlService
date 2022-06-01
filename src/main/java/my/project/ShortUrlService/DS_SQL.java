package my.project.ShortUrlService;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DS_SQL {
    private static final Logger logger = LogManager.getLogger();
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        logger.info("Устанавливаем соединение с БД SQL.\t" +  Constants.DBSQLURL + "\t" +
                Constants.DBSQLUSERNAME  + "\t" +  Constants.DBSQLPASSWORD);
        config.setJdbcUrl(Constants.DBSQLURL);
        config.setUsername(Constants.DBSQLUSERNAME);
        config.setPassword(Constants.DBSQLPASSWORD);
        logger.info("Данные авторизации для БД SQL установлены.");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(5);
        ds = new HikariDataSource(config);
        logger.info("Пул соединений c БД SQL создан");
    }

    public static Connection getConnection() throws SQLException {
        logger.info("Получено соединение из пула подключения к БД SQL.");
        return ds.getConnection();
    }

}
