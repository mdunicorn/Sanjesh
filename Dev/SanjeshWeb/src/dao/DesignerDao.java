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
