package controller;

import java.util.List;

import dao.ArbiterDao;
import dao.CourseDao;
import dao.DesignerDao;
import dao.QuestionDao;
import dao.QuestionEvaluationDao;

import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import model.Arbiter;
import model.Course;
import model.Designer;
import model.Person;
import model.Question;
import model.QuestionEvaluation;
import model.QuestionLevel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import core.SecurityItems;
import core.SecurityService;
import core.Utils;

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
	@Inject
	private QuestionEvaluationDao questionEvaluationDao;
    @Inject
    private ArbiterDao arbiterDao;

    private UploadedFile questionImage;
    private UploadedFile answerImage;
    private UploadedFile incorrectOption1Image;
    private UploadedFile incorrectOption2Image;
    private UploadedFile incorrectOption3Image;
    private boolean designerFieldVisible;
    private boolean designerFieldEditable = false;
    private List<Designer> designerList;
    private List<Course> courseList;
    
    private Question toView = null;
    private int toViewQuestionId = 0;
    private QuestionEvaluation evaluation;
    private Arbiter currentArbiter = null;
    private List<Arbiter> arbiterList;

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
        designerFieldVisible =
                toEdit.getDesigner() == null || hasAccessToViewAllQuestions();
        designerFieldEditable = true;
        loadDetailLists();
    }
    
    
    @Override
    public void edit(Question q) {
        super.edit(q);
        designerFieldVisible = hasAccessToViewAllQuestions();
        designerFieldEditable = false;
        loadDetailLists();
    }
    
    public void view(Question q) {
        if (q == null) {
            Utils.addFacesInformationMessage("سؤال مشخص شده یافت نشد.");
            return;
        }
        toView = q;
        Person p = SecurityService.getLoginBean().getRelatedPerson(); 
        if (p instanceof Arbiter)
            currentArbiter = (Arbiter)p;
        else
        {
            currentArbiter = null;
            arbiterList = arbiterDao.findByEducationGroup(q.getCourse().getField().getGroup().getId());
        }
        if (getHasAccessToEvaluateQuestion()) {
            List<QuestionEvaluation> evals = questionEvaluationDao.findByQuestion(q.getId());
            if (evals.size() == 0) {
                evaluation = questionEvaluationDao.newEntity();
                evaluation.setQuestion(q);
                if (currentArbiter != null)
                    evaluation.setArbiter(currentArbiter);
            } else {
                evaluation = evals.get(0);
            }
        }
    }

    @Override
    public void loadList() {
        if (hasAccessToViewAllQuestions())
            super.loadList();
        else {
            dao.clear();
            LoginBean lb = SecurityService.getLoginBean();
            Person p = lb.getRelatedPerson();
            if (p instanceof Arbiter) {
                setList(dao.findByEducatoinGroup(((Arbiter)p).getEducationGroup().getId()));
            } else {
                int currentUserId = lb.getCurrentUser().getId();
                setList(dao.loadQuestionsCreatedByUser(currentUserId));
            }
        }
    }
    
    @Override
    public void showList() {
        super.showList();
        toView = null;
    }
    
    public boolean hasAccessToViewAllQuestions() {
        return SecurityService.hasPermission(SecurityItems.QuestionViewAll);
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
    
    public boolean isDesignerFieldEditable() {
        return designerFieldEditable;
    }
    
    public boolean getShouldRenderDesignerSelector() {
        return designerFieldVisible && designerFieldEditable;
    }
    
    public boolean getShouldRenderDesignerLable() {
        return designerFieldVisible && !designerFieldEditable;
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
    
    public boolean getHasAccessToEditQuestion() {
        return SecurityService.hasPermission(SecurityItems.QuestionEdit);
    }

    public boolean getHasAccessToDeleteQuestion() {
        return SecurityService.hasPermission(SecurityItems.QuestionDelete);
    }
    
    public boolean getHasAccessToEvaluateQuestion() {
        return SecurityService.hasPermission(SecurityItems.QuestionEvaluationNew);
    }
        
    public Question getToView() {
        return toView;
    }
    
    public int getToViewQuestionId() {
        return toViewQuestionId;
    }
    
    public void setToViewQuestionId(int id) { 
        toViewQuestionId = id;
    }
    
    public void initViewParams() {
        if (!FacesContext.getCurrentInstance().isPostback() && toViewQuestionId > 0) {
            view(dao.findById(toViewQuestionId));
        }
    }
    
    public QuestionEvaluation getEvaluation() {
        return evaluation;
    }
    
    public QuestionEvaluation.EvaluationResult[] getEvaluationResultList() {
        return QuestionEvaluation.EvaluationResult.values();
    }
    
    public void saveEvaluation() {
        try {
            questionEvaluationDao.save(evaluation);
            Utils.addFacesInformationMessage("ارزیابی ثبت شد.");
        } catch (Throwable e) {
            if (Utils.handleBeanException(e))
                return;
            else
                throw e;
        }
        showList();
    }
    
    public boolean showArbiterInEvaluation() {
        return currentArbiter == null;
    }
    
    public List<Arbiter> getArbiterList() {
        return arbiterList;
    }
    
    public int getSelectedArbiterId() {
        if (evaluation == null || evaluation.getArbiter() == null)
            return 0;
        return evaluation.getArbiter().getId();
    }
    
    public void setSelectedArbiterId (int id) {
        if (evaluation != null) {
            if (id == 0)
                evaluation.setArbiter(null);
            else
                evaluation.setArbiter(arbiterDao.findById(id));
        }            
    }
    
    public String getEvaluateButtonText(Question q) {
        if (getHasAccessToEvaluateQuestion())
        {
            if (questionHasEvaluation(q))
                return "مشاهده ارزیابی";
            else
                return "ارزیابی";
        } else {
            return "مشاهده";
        }
    }
    
    public boolean questionHasEvaluation(Question q) {
        return q.getEvaluation() != null && q.getEvaluation().size() > 0;
    }
}
