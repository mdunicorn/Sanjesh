package controller;

import java.util.List;

import dao.CourseDao;
import dao.DesignerDao;
import dao.QuestionDao;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import model.Course;
import model.Designer;
import model.Question;
import model.QuestionLevel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import core.SecurityItems;
import core.SecurityService;

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

    private UploadedFile questionImage;
    private UploadedFile answerImage;
    private UploadedFile incorrectOption1Image;
    private UploadedFile incorrectOption2Image;
    private UploadedFile incorrectOption3Image;
    private boolean designerFieldVisible;
    private List<Designer> designerList;
    private List<Course> courseList;

	@PostConstruct
	public void init() {
		super.init(dao);
	}

    @Override
    public String getEntityName() {
        return "سؤال";
    }

    @Override
    public void createNew() {
        super.createNew();
        designerFieldVisible = toEdit.getDesigner() == null;
        loadDetailLists();
    }
    
    @Override
    public void edit(Question q) {
        super.edit(q);
        loadDetailLists();
    }

    @Override
    public void loadList() {
        if (SecurityService.hasPermission(SecurityItems.QuestionViewAll))
            super.loadList();
        else {
            dao.clear();
            int currentUserId = SecurityService.getLoginBean().getCurrentUser().getId();
            setList(dao.loadQuestionsCreatedByUser(currentUserId));
        }
    }
    
    public List<Designer> getDesignerList() {
        return designerList;
    }
    
    public void loadDesignerList() {
		designerList = designerDao.findAll();
	}
    
    public List<Course> getCourseList() {
        return courseList;
    }
    
    public void loadCourseList() {
        //courseList = courseDao.findAll();
        if (toEdit.getDesigner() == null )
            courseList = null;
        else
            courseList = toEdit.getDesigner().getExpertInCoursesQuestionsList();
    }
    
    private void loadDetailLists() {
        loadCourseList();
        loadDesignerList();
    }
        
	public int getSelectedDesignerId() {
		Designer d = getToEdit().getDesigner();
		if (d != null)
			return d.getId();
		return 0;
	}
	
	public void setSelectedDesignerId( int designerId){
	    if (designerId == 0)
	        getToEdit().setDesigner(null);
	    else
	        getToEdit().setDesigner(designerDao.findById(designerId));
	}

	public int getSelectedCourseId() {
		if (getToEdit().getCourse() != null)
			return getToEdit().getCourse().getId();
		return 0;
	}

	public void setSelectedCourseId(int courseId) {
	    if (courseId == 0)
	        getToEdit().setCourse(null);
	    else
	        getToEdit().setCourse(courseDao.findById(courseId));
	}

	public void uploadQuestionPic(FileUploadEvent ev) {
		getToEdit().setQuestionImage(ev.getFile().getContents());
		getToEdit().setQuestionImageFilename(ev.getFile().getFileName());
	}

	public void uploadAnswerPic(FileUploadEvent ev) {
		getToEdit().setAnswerImage(ev.getFile().getContents());
        getToEdit().setAnswerImageFilename(ev.getFile().getFileName());
	}

	public void uploadIncorrectOption1Pic(FileUploadEvent ev) {
		getToEdit().setIncorrectOption1Image(ev.getFile().getContents());
        getToEdit().setIncorrectOption1ImageFilename(ev.getFile().getFileName());
	}

	public void uploadIncorrectOption2Pic(FileUploadEvent ev) {
		getToEdit().setIncorrectOption2Image(ev.getFile().getContents());
        getToEdit().setIncorrectOption2ImageFilename(ev.getFile().getFileName());
	}

	public void uploadIncorrectOption3Pic(FileUploadEvent ev) {
		getToEdit().setIncorrectOption3Image(ev.getFile().getContents());
        getToEdit().setIncorrectOption3ImageFilename(ev.getFile().getFileName());
	}

    public UploadedFile getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(UploadedFile questionImage) {
        this.questionImage = questionImage;
        if (questionImage != null)
            toEdit.setQuestionImage(questionImage.getContents());
    }

    public UploadedFile getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(UploadedFile answerImage) {
        this.answerImage = answerImage;
        if (answerImage != null)
            toEdit.setAnswerImage(answerImage.getContents());
    }

    public UploadedFile getIncorrectOption1Image() {
        return incorrectOption1Image;
    }

    public void setIncorrectOption1Image(UploadedFile incorrectOption1Image) {
        this.incorrectOption1Image = incorrectOption1Image;
        if (incorrectOption1Image != null)
            toEdit.setIncorrectOption1Image(incorrectOption1Image.getContents());
    }

    public UploadedFile getIncorrectOption2Image() {
        return incorrectOption2Image;
    }

    public void setIncorrectOption2Image(UploadedFile incorrectOption2Image) {
        this.incorrectOption2Image = incorrectOption2Image;
        if (incorrectOption2Image != null)
            toEdit.setIncorrectOption2Image(incorrectOption2Image.getContents());
    }

    public UploadedFile getIncorrectOption3Image() {
        return incorrectOption3Image;
    }

    public void setIncorrectOption3Image(UploadedFile incorrectOption3Image) {
        this.incorrectOption3Image = incorrectOption3Image;
        if (incorrectOption3Image != null)
            toEdit.setIncorrectOption3Image(incorrectOption3Image.getContents());
    }
    
    public boolean isDesignerFieldVisible(){
        return designerFieldVisible;
    }
    
    public boolean isDesignerSelected() {
        return this.toEdit.getDesigner() != null;
    }
    
    public String getCourseNoSelectItemText(){
        if (isDesignerSelected())
            return "لطفاً درس را انتخاب کنید";
        else
            return "ابتدا طراح را انتخاب کنید.";
    }    
    
    public void setQuestionLevel(int level) {
        if (toEdit != null)
            toEdit.setQuestionLevel(QuestionLevel.values()[level]);        
    }
    
    public int getQuestionLevel() {
        if (toEdit != null)
            return toEdit.getQuestionLevel().ordinal();
        return 0;
    }
    
    public void updateCourseList(AjaxBehaviorEvent event) {
        loadCourseList();
    }
}
