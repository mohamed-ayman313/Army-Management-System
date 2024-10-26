package com.example.Army.SecuritySystem.Member;

import com.example.Army.hibernateConfiguration.HibernateConfiguration;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class MemberDao {
    private final HibernateConfiguration sessionFactory;
    public void addMember(Member member){
        System.out.println("a7mooooooooooooos");
        org.hibernate.SessionFactory sf=sessionFactory.getSessionFactory();
        Session session=sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(member);
        tx.commit();
        session.close();
    }
    public Member getMember(String mail){
        org.hibernate.SessionFactory sf = sessionFactory.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Member m where m.memberEmail = :email",Member.class);
        q.setParameter("email",mail);
        Member member = (Member) q.getSingleResult();
        tx.commit();
        session.close();
        return member;
    }
    public List<Member> getAllMembers(){
        org.hibernate.SessionFactory sf = sessionFactory.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Member",Member.class);
        List<Member> member = (List<Member>) q.getResultList();
        tx.commit();
        session.close();
        return member;
    }

    public void updateMember(Member member){
        org.hibernate.SessionFactory sf=sessionFactory.getSessionFactory();
        Session session=sf.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(member);
        tx.commit();
        session.close();
    }

}
