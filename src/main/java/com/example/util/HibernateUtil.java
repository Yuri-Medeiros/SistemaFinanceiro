package com.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class HibernateUtil extends Configuration {

    private static final SessionFactory factory;

    static {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getFactory() {
        return factory;
    }

}
