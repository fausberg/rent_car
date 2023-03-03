package rentcar.rentcar.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.Fine;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public class FineRepository {

    public void createFine(Fine fine) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(fine);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void updateFine(Fine fine) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(fine);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteFine(Fine fine) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(fine);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public Fine getFineById(int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Fine fine = session.get(Fine.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return fine;
    }

    public ArrayList<Fine> getAllFine() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Fine ");
        ArrayList<Fine> list = (ArrayList<Fine>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return list;
    }
}
