package dao;

import model.EntityBase;
import java.util.List;

/**
 *
 * @author Muhammad
 */
public interface DaoBase<T extends EntityBase> {

    T newEntity();

    void save(T entity);
    
    boolean validateSave(T entity, List<String> messages);
    
    boolean validateRemove(T entity, List<String> messages);

    T refresh(T entity);

    void remove(T entity);

    List<T> findAll();
    
    T findById(int id);
    
    void clear();
}
