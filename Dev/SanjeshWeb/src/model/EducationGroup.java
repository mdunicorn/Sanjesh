package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Muhammad
 */

@Entity
@Audited
public class EducationGroup implements EntityBase, Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "educationgroup_id")
    private int id;
	
	@NotBlank(message="لطفاً کد گروه را وارد نمایید.")
	private String code;
    @NotBlank(message = "لطفاً نام گروه تحصیلی را وارد نمایید.")
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "group")
    private List<EducationField> fields;

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

//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }

    public List<EducationField> getFields() {
        return fields;
    }

    public void setFields(List<EducationField> fields) {
        this.fields = fields;
    }
}
