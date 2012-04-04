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

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Muhammad
 */

@Entity
@Audited
public class Grade implements EntityBase, Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int id;
    @NotBlank(message="لطفاً نام رتبه عملی را وارد نمایید.")
    @Column(nullable = false)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
