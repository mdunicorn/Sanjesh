/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.DaoBase;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.EntityBase;

/**
 *
 * @author Muhammad
 */
public abstract class DaoImplBase<T extends EntityBase> implements DaoBase<T> {

    @PersistenceContext(name = "sanjeshPUnit")
    protected EntityManager em;
    protected Class<?> entityType;
    private String entityName;

    public DaoImplBase() {
        ParameterizedType c = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityType = ((Class<?>) c.getActualTypeArguments()[0]);
        this.entityName = entityType.getSimpleName();
    }

    public String getEntityName(){
    	return entityName;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public T newEntity() {
        try {
            return (T) entityType.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(DaoImplBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DaoImplBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void save(T u) {
        em.merge(u);
    }

    @Override
    public void remove(T u) {
        em.remove(em.merge(u));
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<T> findAll() {
        return em.createQuery("from " + entityName).getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public T findById(int id) {
        return (T) em.createQuery("from " + entityName + " where id=:id").
                setParameter("id", id).getSingleResult();
    }
}
