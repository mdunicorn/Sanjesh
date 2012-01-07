/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;



import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Abbas
 */
@Entity
public class SanjeshAgent implements EntityBase, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="sanjeshagent_id")
    private int id;
	
//    @NotBlank(message="لطفاً نام کارشناس را وارد نمایید.")
//    @NotNull
//    @Column(nullable=false)
    private String name;
    
    @NotBlank(message="لطفاً نام خانوادگی کارشناس را وارد نمایید.")
    @Column(nullable=false)
    private String family;
    private String emailAddress;
//    private String nationalCode;
    private String organizationCode;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDate;
    private String birthLocation;

    private boolean isQuestionExpert;
    private boolean isArbiterExpert;

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

	public boolean isQuestionExpert() {
		return isQuestionExpert;
	}

	public void setQuestionExpert(boolean isQuestionExpert) {
		this.isQuestionExpert = isQuestionExpert;
	}

	public boolean isArbiterExpert() {
		return isArbiterExpert;
	}

	public void setArbiterExpert(boolean isArbiterExpert) {
		this.isArbiterExpert = isArbiterExpert;
	}
    
}
