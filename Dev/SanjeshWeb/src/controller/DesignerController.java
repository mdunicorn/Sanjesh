package controller;

import java.util.List;
import java.util.Set;

import dao.CourseDao;
import dao.DesignerDao;
import dao.GradeDao;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import model.Course;
import model.Designer;
import model.Grade;

/**
 *
 * @author Abbas
 */
@ManagedBean
@ViewScoped
public class DesignerController extends EntityControllerBase<Designer> {
    
    @Inject
    private DesignerDao dao;
    @Inject
    private GradeDao gradeDao;
    @Inject
    private CourseDao courseDao;
    
    private List<Grade> gradeList;
    private List<Course> courseList;
	private String passwordCoinfirmation;

    @PostConstruct
    public void init() {
        super.init(dao);
    }
    
    @Override
    public void edit(Designer d){
    	super.edit(d);
    	updateDetailLists();
		setPasswordCoinfirmation(d.getUser().getPassword());
    }
    
    @Override
    public void createNew(){
    	super.createNew();
    	updateDetailLists();
    	setPasswordCoinfirmation(null);
    }
    
    private void updateDetailLists(){
    	gradeList = gradeDao.findAll();
    	courseList = courseDao.findAll();    	
    }
    
    public List<Grade> getGradeList(){
    	return gradeList;
    }
    
    public List<Course> getCourseList(){
    	return courseList;
    }
    
    public int getSelectedGradeId(){
    	if( getToEdit().getGrade() != null)
    		return getToEdit().getGrade().getId();
    	return 0;    		
    }
    
    public void setSelectedGradeId(int gradeId){
    	this.getToEdit().setGrade(gradeDao.findById(gradeId));
    }
    
    public Integer[] getSelectedCourseIds(){
    	Integer[] array = new Integer[getToEdit().getExpertInCourses().size()];
    	int i = 0;
    	for (Course c : getToEdit().getExpertInCourses())
    		array[i++] = c.getId();
    	return array;
    }
    
    public void setSelectedCourseIds(Integer[] courseIds){
    	Designer d = getToEdit();
    	d.getExpertInCourses().clear();
    	for (Integer i : courseIds){
    		d.getExpertInCourses().add(courseDao.findById(i));
    	}
    }

    public Integer[] getSelectedQuestionCourseIds(){
    	Set<Course> courses = getToEdit().getExpertInCoursesQuestions();
    	Integer[] array = new Integer[courses.size()];
    	int i = 0;
    	for (Course c : courses)
    		array[i++] = c.getId();
    	return array;
    }
    
    public void setSelectedQuestionCourseIds(Integer[] courseIds){
    	Designer d = getToEdit();
    	d.getExpertInCoursesQuestions().clear();
    	for (Integer i : courseIds){
    		d.getExpertInCoursesQuestions().add(courseDao.findById(i));
    	}
    }
    
	public String getPasswordCoinfirmation() {
		return passwordCoinfirmation;
	}

	public void setPasswordCoinfirmation(String passwordCoinfirmation) {
		this.passwordCoinfirmation = passwordCoinfirmation;
	}

	@Override
	public void save() {
		Designer d = this.getToEdit();
		if (!this.getPasswordCoinfirmation().equals(
				d.getUser().getPassword())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"رمز عبور وارد شده مطابق تکرار آن نمیباشد.", "لطفاً با دقت بیشتری رمز و تکرار آن را وارد نمایید."));
			return;
		}
		else{
			super.save();
			setPasswordCoinfirmation(null);
		}
	}

}
