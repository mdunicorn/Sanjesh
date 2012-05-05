package model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author Muhammad
 */

@Entity
@Table(name = "suser")
@Audited
public class User implements EntityBase, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suser_id")
    private int id;

    @NotBlank(message = "لطفاً نام کاربری را وارد نمایید.")
    private String userName;

    @NotBlank(message = "لطفاً رمز ورود را وارد نمایید.")
    private String password;

    @NotBlank(message = "لطفاً نام کامل کاربر را وارد نمایید.")
    private String fullName;

    @NotNull
    private boolean isActive;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "suser_ref"), inverseJoinColumns = @JoinColumn(name = "role_ref"))
    private Set<Role> roles;

    @Version
    private int version;

    public User() {
        isActive = true;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Role> getRoles() {
        if (roles == null)
            roles = new HashSet<Role>();
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return getId() == 1;
    }

    @Override
    public int hashCode() {
        return userName == null ? 0 : userName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            return ((User) o).userName.equals(this.userName);
        }
        return false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
