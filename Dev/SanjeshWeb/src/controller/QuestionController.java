package controller;

import java.util.List;

import dao.CourseDao;
import dao.DesignerDao;
import dao.QuestionDao;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import model.Course;
import model.Designer;
import model.Question;
import org.primefaces.event.FileUploadEvent;

/**
 * 
 * @author Abbas
 */
@ManagedBean
@ViewScoped
public class QuestionController extends EntityControllerBase<Question> {

	@Inject
	private QuestionDao dao;
	@Inject
	private DesignerDao designerDao;
	@Inject
	private CourseDao courseDao;

	@PostConstruct
	public void init() {
		super.init(dao);
	}

	public List<Designer> getDesignerList() {
		return designerDao.findAll();
	}

	public int getSelectedDesignerId() {
		Designer d = getToEdit().getDesigner();
		if (d != null)
			return d.getId();
		return 0;
	}
	
	public void setSelectedDesignerId( int designerId){
		this.getToEdit().setDesigner(designerDao.findById(designerId));
	}

	public List<Course> getCourseList() {
		return courseDao.findAll();
	}

	public int getSelectedCourseId() {
		if (getToEdit().getCourse() != null)
			return getToEdit().getCourse().getId();
		return 0;
	}

	public void setSelectedCourseId(int courseId) {
		getToEdit().setCourse(courseDao.findById(courseId));
	}

	public void uploadQuestionPic(FileUploadEvent ev) {
		getToEdit().setQuestionImage(ev.getFile().getContents());
	}

//	public void uploadCurrectAnswerPic(FileUploadEvent ev) {
//		getToEdit().setCurrectAnswerImage(ev.getFile().getContents());
//	}
//
//	public void uploadWrongAnswer1Pic(FileUploadEvent ev) {
//		getToEdit().setWrongAnswer1Image(ev.getFile().getContents());
//	}
//
//	public void uploadWrongAnswer2Pic(FileUploadEvent ev) {
//		getToEdit().setWrongAnswer2Image(ev.getFile().getContents());
//	}
//
//	public void uploadWrongAnswer3Pic(FileUploadEvent ev) {
//		getToEdit().setWrongAnswer3Image(ev.getFile().getContents());
//	}

	/*
	 * private byte[] image;
	 * 
	 * 
	 * @PostConstruct public void init() { //super.setDao(dao); image = new
	 * byte[2]; }
	 * 
	 * 
	 * public StreamedContent getImage() { InputStream temp = new
	 * ByteArrayInputStream(image); return new DefaultStreamedContent(temp); }
	 */

}
