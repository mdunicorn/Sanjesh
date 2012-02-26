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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import org.hibernate.validator.constraints.Email;
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
	
    private String name;
    
    @NotBlank(message="لطفاً نام خانوادگی کارشناس را وارد نمایید.")
    @Column(nullable=false)
    private String family;
    
    @NotBlank(message="لطفاً آدرس ایمیل را وارد نمایید.")
    @Email(message="لطفاً آدرس ایمیل را به درستی وارد نمایید.")
    private String emailAddress;
    
//    private String nationalCode;
    
    private String organizationCode;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDate;
    private String birthLocation;

    private boolean isQuestionExpert;
    private boolean isArbiterExpert;
    private boolean isDesignerExpert;
    private boolean isDataExpert;
    
    @OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name = "suser_ref", nullable = false)
    private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
	public String getFullName(){
		if( this.name == null || "".equals("")){
			return this.family;
		}
		return this.name + " " + this.family;
	}

	public boolean isDesignerExpert() {
		return isDesignerExpert;
	}

	public void setDesignerExpert(boolean isDesignerExpert) {
		this.isDesignerExpert = isDesignerExpert;
	}

	public boolean isDataExpert() {
		return isDataExpert;
	}

	public void setDataExpert(boolean isDataExpert) {
		this.isDataExpert = isDataExpert;
	}
}
