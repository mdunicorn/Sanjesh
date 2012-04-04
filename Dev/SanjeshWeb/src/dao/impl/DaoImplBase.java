package dao.impl;

import dao.DaoBase;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;
import model.EntityBase;
import org.apache.commons.lang3.StringUtils;

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
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(T u) {
    	List<String> errorMessages = new ArrayList<String>();
    	if(!validateSave(u, errorMessages)){
    		throw new ValidationException(StringUtils.join(errorMessages, "\n"));
    	}
        em.merge(u);
    }

    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(T u) {
    	List<String> errorMessages = new ArrayList<String>();
    	if(!validateRemove(u, errorMessages)){
    		throw new ValidationException(StringUtils.join(errorMessages, "\n"));
    	}
        em.remove(em.merge(u));
    }
    
    @Override
    public boolean validateSave(T entity, List<String> messages) {    	
    	return true;
    }

    @Override
    public boolean validateRemove(T entity, List<String> messages) {
    	return true;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> findAll() {
        return em.createQuery("from " + entityName).getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T findById(int id) {
//        return (T) em.createQuery("from " + entityName + " where id=:id").
//                setParameter("id", id).getSingleResult();
    	return (T)em.find(entityType, id);
    }
}
