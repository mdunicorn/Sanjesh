package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Parameter;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "question_evaluation")
@Audited
public class QuestionEvaluation implements EntityBase, Serializable {

    private static final long serialVersionUID = -4130326381680908478L;

    // ---------------------- Enumeration

    public enum EvaluationResult {

        SelectedExactly(1, "عیناً انتخاب شد"),
        SelectedWithModification(2,"با اصلاحات انتخاب شد"),
        NotSolved(10, "حل نشده است"),
        VeryEasy(20, "خیلی آسان است"),
        Easy(22,"آسان است"),
        Medium(24, "متوسط است"),
        Hard(26, "دشوار است"),
        VeryHard(28, "خیلی دشوار است"),
        Repetitive(30, "تکراری است"),
        OutOfTopic(35, "خارج از سرفصل است"),
        Incorrect(40, "اشتباه است"),
        NotSelected(50, "به دلیل دیگری انتخاب نشد");        

        private int value;
        private String name;

        EvaluationResult(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public int toInt() {
            return value;
        }

        public static EvaluationResult fromInt(int value) {
            for (EvaluationResult er : EvaluationResult.values())
                if (er.toInt() == value)
                    return er;
            return null;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }

    // ---------------------- Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_evaluation_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "question_ref", nullable = false)
    @NotNull(message = "لطفاً سؤال را انتخاب نمایید.")
    private Question question;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "arbiter_ref", nullable = false)
    @NotNull(message = "لطفاً داور را انتخاب نمایید.")
    private Arbiter arbiter;
    
    @NotNull(message = "لطفاً نتیجه ارزیابی را انتخاب نمایید.")
    @Type(
            type="core.hibernate.IntegerEnumType",
            parameters = {                    
                    @Parameter(
                            name="enumClass",
                            value="model.QuestionEvaluation$EvaluationResult")
            }
    )
    private EvaluationResult result;
    
    private String reason;
    
    @NotNull(message="لطفاً زمان لازم برای پاسخ گویی را وارد نمایید.")
    @Column(name="answer_time")
    private int answerTime;
    
    private String comments;

    @Version
    private int version;

    //---------------------- getters and setters
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Arbiter getArbiter() {
        return arbiter;
    }

    public void setArbiter(Arbiter arbiter) {
        this.arbiter = arbiter;
    }

    public EvaluationResult getResult() {
        return result;
    }

    public void setResult(EvaluationResult result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    @Override
    public String toString() {
        return "سؤال آقای " + question.getDesigner().getFullName();
    }

}
