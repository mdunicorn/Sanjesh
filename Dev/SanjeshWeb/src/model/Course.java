package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

/**
 *
 * @author Muhammad
 */
@Entity
//@NamedQuery(name="findAll", query="from Course")
public class Course implements EntityBase, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "course_id")
    private int id;
//    @NotNull
//    @Column(nullable = false)
//    private int code;
    @NotBlank(message="لطفاً نام درس را وارد نمایید.")
    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "educationfield_ref", nullable = false)
    @NotNull(message="یک رشته انتخاب نمایید.")
    private EducationField field;
    @OneToMany(mappedBy="course")
    private List<Topic> topics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EducationField getField() {
        return field;
    }

    public void setField(EducationField field) {
        this.field = field;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
