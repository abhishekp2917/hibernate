package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionCreator {

    public static SessionFactory getSessionFactory(String configFile, Class... annotatedClasses) {
        Configuration config = new Configuration().configure(configFile);
        for(Class annotattedClass : annotatedClasses) {
            config.addAnnotatedClass(annotattedClass);
        }
        SessionFactory sessionFactory = config.buildSessionFactory();
        return sessionFactory;
    }
}
