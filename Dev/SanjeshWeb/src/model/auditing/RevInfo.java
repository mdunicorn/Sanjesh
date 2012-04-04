package model.auditing;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@RevisionEntity(RevisionChangeListener.class)
public class RevInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@RevisionNumber
	private int rev;

	@RevisionTimestamp
	private long timestamp;

	@Column(name="suser")
	private int userId;

	public int getRev() {
		return rev;
	}

	public void setRev(int rev) {
		this.rev = rev;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

    @Transient
    public Date getRevisionDate() {
        return new Date(timestamp);
    }
    
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
