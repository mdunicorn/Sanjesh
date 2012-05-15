package dao.impl;

import java.util.List;

import dao.TopicDao;
import model.Topic;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * 
 * @author Muhammad
 */
@Stateless
public class TopicDaoImpl extends DaoImplBase<Topic> implements TopicDao {

	@Override
	public boolean validateSave(Topic t, List<String> messages) {
		if (!super.validateSave(t, messages))
			return false;
		List<Topic> l = findByName(t.getName());
		for (Topic t2 : l) {
			if (t.getCourse() != null && t.getCourse().getId() == t2.getCourse().getId()
					&& t2.getId() != t.getId()) {
				messages.add("نام وارد شده برای موضوع در این درس تکراری است.");
				return false;
			}
		}

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Topic> findByName(String name) {
		return em.createQuery("from Topic where name=:n").setParameter("n", name).getResultList();
	}
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Topic> findByCourse(int courseId) {
	    return em.createNamedQuery("findTopicByCourse", Topic.class).
	            setParameter("cid", courseId).getResultList();
	}

}
