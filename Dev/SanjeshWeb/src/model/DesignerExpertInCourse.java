package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.CascadeType;

import org.hibernate.envers.Audited;

@Entity
@Table(name="designer_expertincourses")
@Audited
public class DesignerExpertInCourse implements EntityBase {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="designer_expertincourses_id")
    private int id;
    
    @ManyToOne(cascade = {})
    @JoinColumn(name = "designer_ref")
    private Designer designer;

    @ManyToOne
    @JoinColumn(name = "course_ref")
    private Course course;
    
    @Temporal(TemporalType.DATE)
    @Column(name="start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name="end_date")
    private Date endDate;
    
    @Version
    private int version;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        //if (this.designer != null) { this.designer.internalRemoveExpertInCourse(this); }
        this.designer = designer;
        //if (designer != null) { designer.internalAddExpertInCourse(this); }        
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date d) {
        this.startDate = d;
    }
    
    public String getStartDateString() {
        if (startDate == null)
            return null;
        return farsilibrary.PersianDateConverter.ToPersianDate(startDate).toString();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date d) {
        this.endDate = d;
    }

    public String getEndDateString() {
        if (endDate == null)
            return null;
        return farsilibrary.PersianDateConverter.ToPersianDate(endDate).toString();
    }
    
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    @Override
    public boolean equals(Object o) {
        if( o instanceof DesignerExpertInCourse) {
            DesignerExpertInCourse d = (DesignerExpertInCourse)o;
            if( designer == null) {
                if( d.designer != null)
                    return false;
            } else if( !designer.equals(d.designer) )
                return false;
            if (course == null) {
                if (d.course != null)
                    return false;
            } else if (!course.equals(d.course))
                return false;
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        if (designer != null)
            hash = designer.hashCode();
        if (course != null)
            hash += course.hashCode();
        return hash;
    }

}
