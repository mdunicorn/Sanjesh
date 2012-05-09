package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

/**
 *
 * @author Muhammad
 */

@Entity
@Audited
public class Course implements EntityBase, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int id;
	
    @NotBlank(message="لطفاً کد درس را وارد نمایید.")
    private String code;
    
    @NotBlank(message="لطفاً نام درس را وارد نمایید.")
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "educationfield_ref", nullable = false)
    @NotNull(message="یک رشته انتخاب نمایید.")
    private EducationField field;
    
    @OneToMany(mappedBy="course", fetch = FetchType.LAZY)
    private List<Topic> topics;
    
    @Version
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
}
