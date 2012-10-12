package model;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Abbas
 */

@Entity
@Audited
public class Question implements EntityBase, Serializable {

   	private static final long serialVersionUID = 1L;
   	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="question_id")
    private int id;
	
    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "designer_ref", nullable = false)
    @NotNull(message="لطفاً طراح سؤال را تعیین کنید.")
	private Designer designer;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "course_ref", nullable = false)
    @NotNull(message="لطفاً درس را انتخاب کنید.")
    private Course course;

    @NotBlank(message="لطفاً متن سوال را وارد نمایید.")
    @Column(name="question_text")
    private String questionText;
    @Column(name="question_image")
    private byte[] questionImage;
    @Column(name="question_image_filename")
    private String questionImageFilename;
    
    @NotNull
    private boolean taught;

    @NotNull(message="لطفاً زمان پاسخگویی سؤال را وارد نمایید.")
    @Min(value=1, message="لطفاً زمان پاسخگویی سؤال را بر حسب  ثانیه(بزرگتر از 0) وارد نمایید.")
    private int answerTime;

    @NotNull(message="لطفاً درجه سختی سؤال را انتخاب نمایید.")
    private QuestionLevel questionLevel;
    
    @Column(name="answer_text")
    private String answerText;

    @Column(name="answer_image")
    private byte[] answerImage;
    
    @Column(name="answer_image_filename")
    private String answerImageFilename;
    
    @Column(name="incorrect_option1_text")
    private String incorrectOption1Text;

    @Column(name="incorrect_option1_image")
    private byte[] incorrectOption1Image;
    
    @Column(name="incorrect_option1_image_filename")
    private String incorrectOption1ImageFilename;

    @Column(name="incorrect_option2_text")
    private String incorrectOption2Text;

    @Column(name="incorrect_option2_image")
    private byte[] incorrectOption2Image;
    
    @Column(name="incorrect_option2_image_filename")
    private String incorrectOption2ImageFilename;

    @Column(name="incorrect_option3_text")
    private String incorrectOption3Text;

    @Column(name="incorrect_option3_image")
    private byte[] incorrectOption3Image;
    
    @Column(name="incorrect_option3_image_filename")
    private String incorrectOption3ImageFilename;
    
    @Temporal(TemporalType.DATE)
    private Date designDate;
    
    private RegisterState registerState;

    @Version
    private int version;
    
    @OneToMany(mappedBy="question", fetch=FetchType.EAGER)
    private List<QuestionEvaluation> evaluations;
    
    @Transient
    private String uniqueIdentifier; 
    

    public Question(){
    	registerState = RegisterState.NONE;
    	taught = false;
    	answerTime = 0;
    }
    

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public byte[] getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(byte[] questionImage) {
        this.questionImage = questionImage;
    }    

    public String getQuestionImageFilename() {
        return questionImageFilename;
    }


    public void setQuestionImageFilename(String questionImageFilename) {
        this.questionImageFilename = questionImageFilename;
    }


    public int getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

    public boolean isTaught() {
        return taught;
    }

    public void setTaught(boolean taught) {
        this.taught = taught;
    }

    public QuestionLevel getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(QuestionLevel questionLevel) {
        this.questionLevel = questionLevel;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public byte[] getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(byte[] answerImage) {
        this.answerImage = answerImage;
    }

    public String getAnswerImageFilename() {
        return answerImageFilename;
    }


    public void setAnswerImageFilename(String answerImageFilename) {
        this.answerImageFilename = answerImageFilename;
    }


    public String getIncorrectOption1Text() {
        return incorrectOption1Text;
    }

    public void setIncorrectOption1Text(String incorrectOption1Text) {
        this.incorrectOption1Text = incorrectOption1Text;
    }

    public byte[] getIncorrectOption1Image() {
        return incorrectOption1Image;
    }

    public void setIncorrectOption1Image(byte[] incorrectOption1Image) {
        this.incorrectOption1Image = incorrectOption1Image;
    }

    public String getIncorrectOption1ImageFilename() {
        return incorrectOption1ImageFilename;
    }


    public void setIncorrectOption1ImageFilename(String incorrectOption1ImageFilename) {
        this.incorrectOption1ImageFilename = incorrectOption1ImageFilename;
    }


    public String getIncorrectOption2Text() {
        return incorrectOption2Text;
    }

    public void setIncorrectOption2Text(String incorrectOption2Text) {
        this.incorrectOption2Text = incorrectOption2Text;
    }

    public byte[] getIncorrectOption2Image() {
        return incorrectOption2Image;
    }

    public void setIncorrectOption2Image(byte[] incorrectOption2Image) {
        this.incorrectOption2Image = incorrectOption2Image;
    }

    public String getIncorrectOption2ImageFilename() {
        return incorrectOption2ImageFilename;
    }


    public void setIncorrectOption2ImageFilename(String incorrectOption2ImageFilename) {
        this.incorrectOption2ImageFilename = incorrectOption2ImageFilename;
    }


    public String getIncorrectOption3Text() {
        return incorrectOption3Text;
    }

    public void setIncorrectOption3Text(String incorrectOption3Text) {
        this.incorrectOption3Text = incorrectOption3Text;
    }

    public byte[] getIncorrectOption3Image() {
        return incorrectOption3Image;
    }

    public void setIncorrectOption3Image(byte[] incorrectOption3Image) {
        this.incorrectOption3Image = incorrectOption3Image;
    }

    public String getIncorrectOption3ImageFilename() {
        return incorrectOption3ImageFilename;
    }


    public void setIncorrectOption3ImageFilename(String incorrectOption3ImageFilename) {
        this.incorrectOption3ImageFilename = incorrectOption3ImageFilename;
    }


    public Date getDesignDate() {
        return designDate;
    }

    public void setDesignDate(Date designDate) {
        this.designDate = designDate;
    }

    public RegisterState getRegisterState() {
        return registerState;
    }

    public void setRegisterState(RegisterState registerState) {
        this.registerState = registerState;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    public List<QuestionEvaluation> getEvaluation() {
        return evaluations;
    }

    
    @Override
    public String toString(){
        return "";
    }


    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }


    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
