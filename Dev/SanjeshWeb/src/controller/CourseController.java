package controller;

import dao.CourseDao;
import dao.EducationFieldDao;
import javax.faces.bean.ViewScoped;
import model.Course;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import model.EducationField;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class CourseController extends EntityControllerBase<Course> {

    @Inject
    private CourseDao dao;
    @Inject
    private EducationFieldDao fieldDao;

    public CourseController() {
    }

    @PostConstruct
    public void init() {
        super.init(dao);
    }
        
    public List<EducationField> getEducationFields(){
        return fieldDao.findAll();
    }
    
    public int getSelectedFieldId(){
        if( getToEdit().getField() != null)
            return getToEdit().getField().getId();
        return 0;
    }
    
    public void setSelectedFieldId(int id){
        this.getToEdit().setField(fieldDao.findById(id));
    }
}
