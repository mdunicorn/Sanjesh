/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class EducationField implements EntityBase, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "educationfield_id")
    private int id;
    @NotBlank(message="لطفاً کد رشته را وارد نمایید.")
    @Column(nullable = false)
    private String code;
    @NotBlank(message="لطفاً نام رشته را وارد نمایید.")
    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "educationgroup_ref", nullable = false)
    @NotNull(message="لطفاً گروه تحصیلی را انتخاب نمایید.")
    private EducationGroup group;
    @OneToMany(mappedBy = "field")
    private List<Course> courses;

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

    public EducationGroup getGroup() {
        return group;
    }

    public void setGroup(EducationGroup group) {
        this.group = group;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
