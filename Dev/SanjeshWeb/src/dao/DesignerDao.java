package dao;



import java.util.List;
import model.Designer;
import model.DesignerExpertInCourse;
import model.RegisterState;
/**
 *
 * @author Abbas
 */
public interface DesignerDao extends DaoBase<Designer>{
        
    void removeExpertInCourse(List<DesignerExpertInCourse> toRemove);
    void saveExpertInCourse(DesignerExpertInCourse toSave);
    List<Designer> findByState(RegisterState state);
    Designer findByUser(int userId);
    List<DesignerExpertInCourse> loadExpertInCourses(int designerId);
    List<Designer> loadDesignersCreatedByUser(int userId);
}
