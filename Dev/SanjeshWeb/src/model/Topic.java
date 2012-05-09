/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Muhammad
 */

@Entity
@Audited
public class Topic implements EntityBase, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private int id;
	
//    @NotNull(message="لطفاً")
//    @Column(nullable = false)
//    private int code;
	
    @NotBlank(message="لطفاً نام سرفصل را وارد نمایید.")
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "course_ref", nullable=false)
    @NotNull(message="لطفاً درس را انتخاب نمایید.")
    private Course course;
    
//    @NotNull
//    @Min(value=0, message="تعداد سؤالات نباید منفی باشد.")
//    private int numberOfQuestions;

    @Version
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
