/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;



import java.util.List;
import model.Designer;
import model.RegisterState;
/**
 *
 * @author Abbas
 */
public interface DesignerDao extends DaoBase<Designer>{
        
    List<Designer> findByState(RegisterState state);
    Designer findByUser(int userId);    
}
