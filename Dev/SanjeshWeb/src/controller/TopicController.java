package controller;

import dao.CourseDao;
import dao.TopicDao;
import javax.faces.bean.ViewScoped;
import model.Topic;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import model.Course;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class TopicController extends EntityControllerBase<Topic> {

    @Inject
    private TopicDao dao;
    @Inject
    private CourseDao courseDao;

    @PostConstruct
    public void init() {
        super.init(dao);
    }
    
    @Override
    public String getEntityName() {
        return "سرفصل";
    }
    
    @Override
    public void saveAndNew() {
        Course c = getToEdit().getCourse();
        super.saveAndNew();
        getToEdit().setCourse(c);
    }

    public List<Course> getCourses(){
        return courseDao.findAll();
    }
    
    public int getSelectedCourseId(){
        if( getToEdit().getCourse() != null)
            return getToEdit().getCourse().getId();
        return 0;
    }
    
    public void setSelectedCourseId(int id){
        this.getToEdit().setCourse(courseDao.findById(id));
    }
}
