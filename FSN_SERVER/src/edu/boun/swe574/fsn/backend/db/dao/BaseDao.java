package edu.boun.swe574.fsn.backend.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class BaseDao {

    public Logger           logger = Logger.getLogger(BaseDao.class);

    protected EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public <T> void save(T persistentObject) {
        try {
            beginTransaction();
            em.persist(persistentObject);
            commit();
        } finally {
            rollbackIfTransactionActive();
        }
    }

    public <T> T update(T persistentObject) {
        T persistentObjectLocal = null;
        try {
            beginTransaction();
            /*            if(!em.contains(persistentObject)) {
                            throw new RuntimeException("Current object is not an attached object.");
                        }*/
            persistentObjectLocal = em.merge(persistentObject);
            commit();
        } finally {
            rollbackIfTransactionActive();
        }
        return persistentObjectLocal;
    }

    public <T> void delete(T persistentObject) {
        try {
            beginTransaction();
            em.remove(em.merge(persistentObject));
            commit();
        } finally {
            rollbackIfTransactionActive();
        }
    }
    
    public <T> boolean delete(T... persistentObjects) {
        try {
            beginTransaction();
            for (T persistentObject : persistentObjects) {
                em.remove(em.merge(persistentObject));
            }
            commit();
        } catch (Throwable e) {
            logger.error(e);
            return false;
        } finally {
            rollbackIfTransactionActive();
        }
        return true;
    }

    public <T> T find(Class<T> classT, Object primaryKey) {
        return em.find(classT, primaryKey);
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> classT) {
        List<T> lst = new ArrayList<T>();
        try {
            lst = em.createQuery("Select o from " + classT.getSimpleName() + " o").getResultList();
        } catch (Exception e) {
            // TODO düzenle
            e.printStackTrace();
            logger.error(e);
        }
        return lst;
    }

    public <T> List<T> findByCriteria(Class<T> classT, String parameter, Object value) {
        return findByCriteria(classT, new String[] {parameter}, new Object[] {value});
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(Class<T> classT, String[] parameters, Object[] value) {
        List<T> resultList = new ArrayList<T>();
        StringBuilder sb = new StringBuilder("Select o from " + classT.getSimpleName() + " o ");

        if (parameters == null || value == null) {
            logger.fatal("<findByCriteria> parameters AND value have to be non-null");
            return resultList;
        }

        if (parameters.length != value.length) {
            logger.fatal("<findByCriteria> parameters.length must be equal to value.length");
            return resultList;
        }

        if (parameters.length == 0) {
            logger.fatal("<findByCriteria> parameters.length AND value.length must be greater than zero");
            return resultList;
        }

        try {
            sb.append(" WHERE ");
            for (int i = 0; i < parameters.length; i++) {
                if (i > 0)
                    sb.append(" and ");
                sb.append("o.").append(parameters[i]).append(" = :").append(parameters[i]);
            }

            Query query = em.createQuery(sb.toString());

            for (int j = 0; j < value.length; j++) {
                query.setParameter(parameters[j], value[j]);
            }

            resultList = query.getResultList();

            if (logger.isTraceEnabled()) {
                logger.trace("Query object created=" + query);
            }
            return resultList;
        } catch (Throwable e) {
            logger.error(e);
            return resultList;
        }
    }

    public void beginTransaction() {
        em.getTransaction().begin();
    }

    public void commit() {
        em.getTransaction().commit();
    }

    public void lock(Object persistentObject, LockModeType lockModeType) {
        em.lock(persistentObject, lockModeType);
    }

    public void rollbackIfTransactionActive() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }

}
