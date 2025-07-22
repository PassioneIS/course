package infrastructure;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DataBaseConnection {

    private static SessionFactory sessionFactory;

    private DataBaseConnection() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
                configuration.addProperties(new Properties() {{
                    try (InputStream input = new FileInputStream("config/hibernate.properties")) {
                        load(input);
                    }
                }});
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Falló la creación del SessionFactory: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}