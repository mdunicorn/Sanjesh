package model;



import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
@NamedQuery(name="loadDesignerExpertInCourse", query="select d from DesignerExpertInCourse d where d.designer.id=:d")
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
    
    @Column(name="national_code")
    private String nationalCode;
    
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

	//@OneToMany(mappedBy="designer", fetch = FetchType.EAGER)
	@Transient
	private List<DesignerExpertInCourse> expertInCourses;    

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
    
    @Column(name = "id_number")
    private String idNumber;
    
    @Column(name="id_issue_location")
    private String idIssueLocation;
    
    @Column(name="phone_home")
    private String homePhone;
    
    @Column(name="phone_cell")
    private String cellPhone;
    
    @ManyToOne
    @JoinColumn(name="educationfield_ref")
    private EducationField educationField;
    
    @Column(name="educationfield_other")
    private String educationFieldOther;
    
    @Column(name="last_degree")
    private String lastDegree;
    
    @ManyToOne
    @JoinColumn(name="degree_university_ref")
    private University degreeUniversity;

    @Column(name="degree_university_other")
    private String degreeUniversityOther;
    
    
    @Column(name="home_address")
    private String homeAddress;
    
    @Column(name="zip_code")
    private String zipCode;
    
    //-------------
    @ManyToOne
    @JoinColumn(name="work_university_ref")
    private University workUniversity;
    
    @Column(name="work_university_other")
    private String workUniversityOther;
    
    private String faculty;
    
    @ManyToOne
    @JoinColumn(name="educationgroup_ref")
    private EducationGroup educationGroup;
    
    @Column(name="educationgroup_other")
    private String educationGroupOther;
    
    @Column(name="work_startdate")
    @Temporal(TemporalType.DATE)
    private Date workStartDate;
    
    @Column(name="phone_work")
    private String workPhone;
    
    @Column(name="fax_work")
    private String workFax;
    
    @Column(name="work_position")
    private String workPosition;
    
    @Column(name="position_startdate")
    @Temporal(TemporalType.DATE)
    private Date positionStartDate;
    
    @Column(name="position_enddate")
    @Temporal(TemporalType.DATE)
    private Date positionEndDate;
    
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
	
    public List<DesignerExpertInCourse> getExpertInCourses() {
        return expertInCourses;
    }
    
    public void setExpertInCourses(List<DesignerExpertInCourse> expertInCourses) {
        this.expertInCourses = expertInCourses;
    }
    
//    void internalAddExpertInCourse (DesignerExpertInCourse c) {
//        getExpertInCoursesInternal().add(c);
//    }
//    
//    void internalRemoveExpertInCourse (DesignerExpertInCourse c) {
//        getExpertInCoursesInternal().remove(c);
//    }
    
//    public void addExpertInCourse (DesignerExpertInCourse c) {
//        c.setDesigner(this);
//    }
//    
//    public void removeExpertInCourse (DesignerExpertInCourse c) {
//        c.setDesigner(null);
//    }

	public Set<Course> getExpertInCoursesQuestions() {
		if (expertInCoursesQuestions == null)
			expertInCoursesQuestions = new HashSet<Course>();
		return expertInCoursesQuestions;
	}

	public void setExpertInCoursesQuestions(Set<Course> expertInCoursesQuestions) {
		this.expertInCoursesQuestions = expertInCoursesQuestions;
	}

	public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdIssueLocation() {
        return idIssueLocation;
    }

    public void setIdIssueLocation(String idIssueLocation) {
        this.idIssueLocation = idIssueLocation;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public EducationField getEducationField() {
        return educationField;
    }

    public void setEducationField(EducationField educationField) {
        this.educationField = educationField;
    }

    public String getEducationFieldOther() {
        return educationFieldOther;
    }

    public void setEducationFieldOther(String educationFieldOther) {
        this.educationFieldOther = educationFieldOther;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public University getDegreeUniversity() {
        return degreeUniversity;
    }
    
    public void setDegreeUniversity(University u) {
        this.degreeUniversity = u;
    }

    public String getDegreeUniversityOther() {
        return degreeUniversityOther;
    }

    public void setDegreeUniversityOther(String u) {
        this.degreeUniversityOther = u;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public University getWorkUniversity() {
        return workUniversity;
    }

    public void setWorkUniversity(University workUniversity) {
        this.workUniversity = workUniversity;
    }

    public String getWorkUniversityOther() {
        return workUniversityOther;
    }

    public void setWorkUniversityOther(String workUniversityOther) {
        this.workUniversityOther = workUniversityOther;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public EducationGroup getEducationGroup() {
        return educationGroup;
    }

    public void setEducationGroup(EducationGroup educationGroup) {
        this.educationGroup = educationGroup;
    }

    public String getEducationGroupOther() {
        return educationGroupOther;
    }

    public void setEducationGroupOther(String educationGroupOther) {
        this.educationGroupOther = educationGroupOther;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkFax() {
        return workFax;
    }

    public void setWorkFax(String workFax) {
        this.workFax = workFax;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public Date getPositionStartDate() {
        return positionStartDate;
    }

    public void setPositionStartDate(Date positionStartDate) {
        this.positionStartDate = positionStartDate;
    }

    public Date getPositionEndDate() {
        return positionEndDate;
    }

    public void setPositionEndDate(Date positionEndDate) {
        this.positionEndDate = positionEndDate;
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
    
    @Override
    public boolean equals(Object o) {
        if (null != o && o instanceof Designer) {
            Designer d = (Designer)o;
            if (null == family) {
                if (null != d.family)
                    return false;                    
            }
            else if (!family.equals(d.family))
                return false;
            if (emailAddress == null) {
                if (d.emailAddress != null)
                    return false;
            }
            else if (!emailAddress.equals(d.emailAddress))
                return false;
            if (user == null) {
                if (d.user != null)
                    return false;
            }
            else if (!user.equals(d.user))
                return false;
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        if (null != family)
            hash = family.hashCode();
        if (null != emailAddress)
            hash += emailAddress.hashCode();
        if (null != user)
            hash += user.hashCode();
        return hash;
    }
}
