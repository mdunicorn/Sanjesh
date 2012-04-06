package model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Audited
public class Role implements EntityBase, Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	public static final int QUESTION_EXPERT_ROLE_ID = 1;
	public static final int DESIGNER_EXPERT_ROLE_ID = 2;
	public static final int ARBITER_EXPERT_ROLE_ID = 3;
	public static final int DATA_EXPERT_ROLE_ID = 4;
	public static final int UNIVERSITY_AGENT_ROLE_ID = 10;
	public static final int DESIGNER_ROLE_ID = 11;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

	@NotBlank(message="لطفاً نام نقش را وارد نمایید.")
	private String name;
	private boolean fixed;

	@ManyToMany(mappedBy="roles")
	private Set<User> users;

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

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode(){
		return name == null ? 0 : name.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if( o instanceof Role ){
			return ((Role)o).name.equals(this.name);
		}
		return false;
	}
}
