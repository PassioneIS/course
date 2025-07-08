import infrastructure.DataBaseConnection;
import models.Ingredient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class db {
    public static void main(String[] args) {
        System.out.println("Ejemplo uso de ORM hibernate");

        Ingredient ingredient = new Ingredient("Mazorca");

        Session session = null;
        Transaction transaction = null;

        try {
            session = DataBaseConnection.getSession();
            //transaction = session.beginTransaction();

            //session.persist(ingredient); //para guardar

            List<Ingredient> lista = session.createQuery("from Ingredient",Ingredient.class).list();

            //transaction.commit();
            System.out.println("Todo bien");
            for (Ingredient i : lista) {
                System.out.println(i.toString());
            }

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}