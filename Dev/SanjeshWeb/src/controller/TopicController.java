package controller;

import dao.CourseDao;
import dao.TopicDao;
import javax.faces.bean.ViewScoped;
import model.Topic;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
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
    private DataModel<Topic> list2;

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

    public DataModel<Topic> getList2() {
        return list2;
    }
    
    @Override
    public void loadList() {
        super.loadList();
        list2 = new ListDataModel<Topic>(super.getList());
//        Map<Course, List<Topic>> map = new HashMap<Course, List<Topic>>();
//        for (Topic t : super.getList()) {
//            if( !map.containsKey(t.getCourse()) )
//                map.put(t.getCourse(), new ArrayList<Topic>());
//            map.get(t.getCourse()).add(t);
//        }
//        
//        List<TopicGroup> l = new ArrayList<TopicGroup>();
//        for( Course c : map.keySet() ) {
//            l.add(new TopicGroup(c, new ListDataModel<Topic>(map.get(c))));
//        }
//        groupedList = new ListDataModel<TopicGroup>(l);
    }

}
