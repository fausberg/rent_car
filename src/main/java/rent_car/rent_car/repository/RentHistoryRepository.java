package rentcar.rentcar.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.RentHistory;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public class RentHistoryRepository {

    public void createRentHistory(RentHistory rentHistory) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(rentHistory);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void updateRentHistory(RentHistory rentHistory) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(rentHistory);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteRentHistory(RentHistory rentHistory) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(rentHistory);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public RentHistory getRentHistoryById(int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        RentHistory rentHistory = session.get(RentHistory.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return rentHistory;
    }

    public ArrayList<RentHistory> getAllRentHistory() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from RentHistory ");
        ArrayList<RentHistory> list = (ArrayList<RentHistory>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return list;
    }
}
