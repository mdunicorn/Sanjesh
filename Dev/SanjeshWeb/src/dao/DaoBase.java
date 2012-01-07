/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.EntityBase;
import java.util.List;

/**
 *
 * @author Muhammad
 */
public interface DaoBase<T extends EntityBase> {

    T newEntity();

    void save(T u);

//    void update(T u);
    
    boolean validateSave(T entity, List<String> messages);
    
    boolean validateRemove(T entity, List<String> messages);

    void remove(T u);

    List<T> findAll();
    
    T findById(int id);
}
