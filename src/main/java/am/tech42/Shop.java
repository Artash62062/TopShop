package am.tech42;

import io.javalin.Javalin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;

public class Shop {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

        Javalin app = Javalin.create().start(8080);
        app.get("/items", ctx -> ctx.json(getItems()));
        app.post("/items", ctx -> {

            Item item = ctx.bodyAsClass(Item.class);
            addItem(item);
            ctx.status(201);
        });

    }

    public static void addItem(Item item) {
        Session session = sessionFactory.openSession();
        session.save(item);
        session.close();
    }

    public static List<Item> getItems() {
        Session session = sessionFactory.openSession();
        Query<Item> query = session.createQuery("from Item", Item.class);
        List<Item> itemsList = query.getResultList();
        session.close();
        return  itemsList;
    }
}
