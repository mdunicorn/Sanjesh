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
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Audited
public class Arbiter implements EntityBase, Person, Serializable {
    
    private static final long serialVersionUID = -2136837403440455510L;

    // ---------------------- Fields

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "arbiter_id")
    private int id;
    
    private String name;
    
    @NotBlank(message="لطفاً نام خانوادگی کارشناس را وارد نمایید.")
    @Column(nullable=false)
    private String family;

    @NotBlank(message="لطفاً آدرس ایمیل را وارد نمایید.")
    @Email(message="لطفاً آدرس ایمیل را به درستی وارد نمایید.")
    private String emailAddress;

    @Column(name="national_code")
    @NotBlank(message="لطفاً کد ملی را وارد نمایید.")
    private String nationalCode;

    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "educationgroup_ref", nullable = false)
    @NotNull(message="لطفاً گروه تحصیلی را انتخاب نمایید.")
    private EducationGroup educationGroup;

    @OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name = "suser_ref", nullable = false)
    private User user;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public EducationGroup getEducationGroup() {
        return educationGroup;
    }

    public void setEducationGroup(EducationGroup educationGroup) {
        this.educationGroup = educationGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFullName(){
        if( this.name == null || "".equals(this.name)){
            return this.family;
        }
        return this.name + " " + this.family;
    }
    
    @Override
    public String toString() {
        return getFullName();
    }

}
