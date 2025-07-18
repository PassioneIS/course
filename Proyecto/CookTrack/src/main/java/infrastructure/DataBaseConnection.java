package infrastructure;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DataBaseConnection {

    // Instancia única de SessionFactory (Singleton)
    private static SessionFactory sessionFactory;

    // Constructor privado para evitar instanciación
    private DataBaseConnection() {}

    // Método para obtener la instancia de SessionFactory
    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                // Carga configuración desde hibernate.cfg.xml
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

    // Método auxiliar para obtener una Session
    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    // Método para cerrar SessionFactory al finalizar la aplicación
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
