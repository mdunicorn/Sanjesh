package model;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Abbas
 */

@Entity
@Audited
public class Designer implements EntityBase, Serializable {

   	private static final long serialVersionUID = 1L;
   	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="designer_id")
    private int id;
//    @NotBlank(message="لطفاً نام طراح را وارد نمایید.")
//    @NotNull
//    @Column(nullable=false)
    private String name;
    @NotBlank(message="لطفاً نام خانوادگی طراح را وارد نمایید.")
    @NotNull
    @Column(nullable=false)
    private String family;
//    private String nationalCode;
    private String organizationCode;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDate;
    private String birthLocation;
    private String emailAddress;
    
    @ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="grade_ref")
    private Grade grade;
    
    
    @Column(name="registerstate")
    private RegisterState state;

    public Designer(){
        state = RegisterState.NONE;
    }

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

//    public String getNationalCode() {
//        return nationalCode;
//    }
//
//    public void setNationalCode(String nationalCode) {
//        this.nationalCode = nationalCode;
//    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

    public RegisterState getState(){
        return state;
    }
    
    public void setState(RegisterState s){
        state = s;
    }
}
