package model;



import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Abbas
 */

@Entity
@Audited
public class Designer implements EntityBase, Person, Serializable {

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
    @NotBlank(message="لطفاً آدرس ایمیل را وارد نمایید.")
    @Email(message="لطفاً آدرس ایمیل را به درستی وارد نمایید.")
    private String emailAddress;
    
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_ref")
	private Grade grade;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH },
			fetch = FetchType.EAGER)
	@JoinTable(
			name="designer_expertincourses",
			joinColumns = @JoinColumn(name = "designer_ref"),
			inverseJoinColumns = @JoinColumn(name = "course_ref"))
	private Set<Course> expertInCourses;    

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH },
			fetch = FetchType.EAGER)
	@JoinTable(
			name="designer_expertincoursesquestions",
			joinColumns = @JoinColumn(name = "designer_ref"),
			inverseJoinColumns = @JoinColumn(name = "course_ref"))
	private Set<Course> expertInCoursesQuestions;
	
    @Column(name="registerstate")
    private RegisterState state;

    @OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name = "suser_ref", nullable = false)
    private User user;
    
    @Version
    private int version;


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

    public Set<Course> getExpertInCourses() {
    	if (expertInCourses == null)
    		expertInCourses = new HashSet<Course>();
		return expertInCourses;
	}

	public void setExpertInCourses(Set<Course> expertInCourses) {
		this.expertInCourses = expertInCourses;
	}

	public Set<Course> getExpertInCoursesQuestions() {
		if (expertInCoursesQuestions == null)
			expertInCoursesQuestions = new HashSet<Course>();
		return expertInCoursesQuestions;
	}

	public void setExpertInCoursesQuestions(Set<Course> expertInCoursesQuestions) {
		this.expertInCoursesQuestions = expertInCoursesQuestions;
	}

	public RegisterState getState(){
        return state;
    }
    
    public void setState(RegisterState s){
        state = s;
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
    public String toString(){
        return getFullName();
    }
}
