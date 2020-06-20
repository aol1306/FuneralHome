package main;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class Helper {
    public static <T> List<T> selectAll(Class<T> type, Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        criteriaQuery.from(type);
        return session.createQuery(criteriaQuery).getResultList();
    }
}
