package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SessionCreator {

    public static SessionFactory getSessionFactory(String configFile, Class... annotatedClasses) {
        Configuration config = new Configuration().configure(configFile);
        for(Class annotattedClass : annotatedClasses) {
            config.addAnnotatedClass(annotattedClass);
        }
        ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        SessionFactory sessionFactory = config.buildSessionFactory(registry);
        return sessionFactory;
    }
}
