package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dao.CourseDao;
import dao.DesignerDao;
import dao.EducationFieldDao;
import dao.EducationGroupDao;
import dao.GradeDao;
import dao.UniversityDao;

//import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
//import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import core.SecurityItems;
import core.SecurityService;
import core.Utils;

import model.Course;
import model.Designer;
import model.DesignerExpertInCourse;
import model.EducationField;
import model.EducationGroup;
import model.Grade;
import model.RegisterState;
import model.University;

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
    @Inject
    private EducationFieldDao educationFieldDao;
    @Inject
    private EducationGroupDao educationGroupDao;
    @Inject
    private UniversityDao universityDao;
    
    private List<Grade> gradeList;
    private List<Course> courseList;
    private List<EducationField> educationFieldList;
    private List<EducationGroup> educationGroupList;
    private List<University> universityList;
    
    private DesignerExpertInCourse currentDesignerExpertInCourse = null;
    private List<DesignerExpertInCourse> designerExpertInCoursesToRemove =
            new ArrayList<DesignerExpertInCourse>();
    
	private String passwordCoinfirmation;

    @PostConstruct
    public void init() {
        super.init(dao);
    }
    
    @Override
    public String getEntityName() {
        return "طراح";
    }
    
    @Override
    public void edit(Designer d){
    	super.edit(d);
    	d.setExpertInCourses(dao.loadExpertInCourses(d.getId()));
    	updateDetailLists();
		setPasswordCoinfirmation(d.getUser().getPassword());
    }
    
    @Override
    public void createNew(){
    	super.createNew();
    	getToEdit().setExpertInCourses(new ArrayList<DesignerExpertInCourse>());
    	updateDetailLists();
    	setPasswordCoinfirmation(null);
    }
    
    private void updateDetailLists(){
    	gradeList = gradeDao.findAll();
    }
    
    public void updateUniversityList() {
        universityList = universityDao.findAll();
    }
    
    public void updateEducationFieldList() {
        educationFieldList = educationFieldDao.findAll();
    }
    
    public void updateEducationGroupList() {
        educationGroupList = educationGroupDao.findAll();
    }
    
    public void updateCourseList() {
        courseList = courseDao.findAll();
    }
    
    public List<Grade> getGradeList(){
    	return gradeList;
    }
    
    public List<Course> getCourseList(){
    	return courseList;
    }
    
    public List<EducationGroup> getEducationGroupList() {
        return educationGroupList;
    }
    
    public List<EducationField> getEducationFieldList() {
        return educationFieldList;
    }
    
    public List<University> getUniversityList() {
        return universityList;
    }    
    
    public Integer getSelectedGradeId(){
        Grade g = getToEdit().getGrade(); 
    	if( g == null)
    	    return null;
    	return g.getId();
    }
    
    public void setSelectedGradeId(Integer gradeId){
        if( gradeId == null )
            getToEdit().setGrade(null);
        else
            getToEdit().setGrade(gradeDao.findById(gradeId));
    }
    
    public String getCurrentEducationFieldString() {
        EducationField e = getToEdit().getEducationField();
        if( e != null )
            return e.getName();
        else
            return getToEdit().getEducationFieldOther();
    }
    
    public void updateSelectedEducationField(EducationField e) {
        getToEdit().setEducationFieldOther(null);
        getToEdit().setEducationField(e);
    }
    
    public void educationFieldOtherFilled() {
        getToEdit().setEducationField(null);
    }


    public String getCurrentDegreeUnivesityString() {
        University u = getToEdit().getDegreeUniversity();
        if( u != null )
            return u.getName();
        else
            return getToEdit().getDegreeUniversityOther();
    }
    
    public void updateSelectedDegreeUniversity(University u) {
        getToEdit().setDegreeUniversityOther(null);
        getToEdit().setDegreeUniversity(u);
    }
    
    public void degreeUniversityOtherFilled() {
        getToEdit().setDegreeUniversity(null);
    }
    
    public String getCurrentWorkUnivesityString() {
        University u = getToEdit().getWorkUniversity();
        if( u != null )
            return u.getName();
        else
            return getToEdit().getWorkUniversityOther();
    }
    
    public void updateSelectedWorkUniversity(University u) {
        getToEdit().setWorkUniversityOther(null);
        getToEdit().setWorkUniversity(u);
    }
    
    public void workUniversityOtherFilled() {
        getToEdit().setWorkUniversity(null);
    }
    
    public String getCurrentWorkEducationFieldString() {
        EducationField e = getToEdit().getWorkEducationField();
        if( e != null )
            return e.getCodeAndName();
        else
            return getToEdit().getWorkEducationFieldOther();
    }
    
    public void updateSelectedWorkEducationField(EducationField e) {
        getToEdit().setWorkEducationFieldOther(null);
        getToEdit().setWorkEducationField(e);
    }
    
    public void workEducationFieldOtherFilled() {
        getToEdit().setWorkEducationField(null);
    }

    

    
//    public Integer[] getSelectedCourseIds(){
//    	Integer[] array = new Integer[getToEdit().getExpertInCourses().size()];
//    	int i = 0;
//    	for (Course c : getToEdit().getExpertInCourses())
//    		array[i++] = c.getId();
//    	return array;
//    }
//    
//    public void setSelectedCourseIds(Integer[] courseIds){
//    	Designer d = getToEdit();
//    	d.getExpertInCourses().clear();
//    	for (Integer i : courseIds){
//    		d.getExpertInCourses().add(courseDao.findById(i));
//    	}
//    }

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
	
	public void newDesignerExpertInCourse() {
	    currentDesignerExpertInCourse = new DesignerExpertInCourse();
	    updateCourseList();
	}
	
	public DesignerExpertInCourse getCurrentDesignerExpertInCourse() {
	    return currentDesignerExpertInCourse;
	}
	
	public void addCurrentDesignerExpertInCourse() {
	    getToEdit().getExpertInCourses().add(currentDesignerExpertInCourse);
	    currentDesignerExpertInCourse.setDesigner(getToEdit());
	}
	
    public void removeExpertInCourse(DesignerExpertInCourse dec) {
        getToEdit().getExpertInCourses().remove(dec);
        designerExpertInCoursesToRemove.add(dec);
    }
    
    public void removeExpertInCourseQuestion(Course c) {
        getToEdit().getExpertInCoursesQuestions().remove(c);
    }

    public void addExpertInCourseQuestion(Course c) {
        getToEdit().getExpertInCoursesQuestions().add(c);
    }

	@Override
	protected boolean beforeSave() {
		Designer d = this.getToEdit();
		/*
		if (!this.getPasswordCoinfirmation().equals(
				d.getUser().getPassword())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"رمز عبور وارد شده مطابق تکرار آن نمیباشد.", "لطفاً با دقت بیشتری رمز و تکرار آن را وارد نمایید."));
			return false;
		}*/
		
		d.getUser().setUserName(d.getEmailAddress());
		//if (d.getId() == 0) { // if new entity
		d.getUser().setPassword(d.getNationalCode());
		//}
		
		return true;
	}
	
    @Override
    protected void afterSave() {
        for( DesignerExpertInCourse dec : getToEdit().getExpertInCourses()) 
            dao.saveExpertInCourse(dec);
        dao.removeExpertInCourse(designerExpertInCoursesToRemove);
        setPasswordCoinfirmation(null);
    }
    
    public boolean hasAccessToAcceptDesigner() {
        return SecurityService.hasPermission(SecurityItems.DesignerAccept);
    }
    
    public boolean shouldAcceptButtonBeVisibleForDesinger (Designer d) {
        return hasAccessToAcceptDesigner() && d.getState() != RegisterState.ACCEPTED;
    }
    
    public boolean shouldRejectButtonBeVisibleForDesinger (Designer d) {
        return hasAccessToAcceptDesigner() && d.getState() != RegisterState.REJECTED;
    }
    
    public void accpetDesigner(Designer d) {
        try {
            d.setState(RegisterState.ACCEPTED);
            d.getUser().setActive(true);
            dao.save(d);
            Utils.addFacesInformationMessage("'" + d.toString() + "' تأیید شد.");
        } catch (Throwable e) {
            if (Utils.handleBeanException(e))
                return;
            else
                throw e;
        }
        showList();
    }

    public void rejectDesigner(Designer d) {
        try {
            d.setState(RegisterState.REJECTED);
            d.getUser().setActive(false);
            dao.save(d);
            Utils.addFacesInformationMessage("عدم تأیید '" + d.toString() + "' ثبت شد.");
        } catch (Throwable e) {
            if (Utils.handleBeanException(e))
                return;
            else
                throw e;
        }
        showList();
    }
    
    @Override
    public void loadList() {
        if (SecurityService.hasPermission(SecurityItems.DesignerViewAll))
            super.loadList();
        else {
            dao.clear();
            int currentUserId = SecurityService.getLoginBean().getCurrentUser().getId();
            setList(dao.loadDesignersCreatedByUser(currentUserId));
        }
    }
}
