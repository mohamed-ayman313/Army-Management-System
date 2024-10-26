package com.example.Army.SecuritySystem.Token;

import com.example.Army.hibernateConfiguration.HibernateConfiguration;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenDao {
    private final HibernateConfiguration sessionFactory;

    public void saveToken(ConfirmationToken confirmationToken) {
        org.hibernate.SessionFactory sf=sessionFactory.getSessionFactory();
        Session session=sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(confirmationToken);
        tx.commit();
        session.close();
    }
    public ConfirmationToken getToken(String token){
        org.hibernate.SessionFactory sf = sessionFactory.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from ConfirmationToken t where t.token = :token",ConfirmationToken.class);
        q.setParameter("token",token);
        ConfirmationToken token1 = (ConfirmationToken) q.getSingleResult();
        tx.commit();
        session.close();
        return token1;
    }
    public void updateToken(ConfirmationToken confirmationToken) {
        org.hibernate.SessionFactory sf=sessionFactory.getSessionFactory();
        Session session=sf.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(confirmationToken);
        tx.commit();
        session.close();
    }

}
