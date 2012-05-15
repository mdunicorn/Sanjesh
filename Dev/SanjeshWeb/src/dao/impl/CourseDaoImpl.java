package dao.impl;

import java.util.List;

import dao.CourseDao;
import dao.TopicDao;
import model.Course;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 * @author Muhammad
 */
@Stateless
public class CourseDaoImpl extends DaoImplBase<Course> implements CourseDao {
    
    @Inject
    TopicDao topicDao;
    
	@Override
	public boolean validateSave(Course e, List<String> messages) {

		if (!super.validateSave(e, messages))
			return false;
		
		List<Course> l = findByCodeAndField(e.getCode(), e.getField().getId());
		if (l.size() > 1 || (l.size() == 1 && l.get(0).getId() != e.getId())) {
			messages.add("کد وارد شده در این رشته تکراری است.");
			return false;
		}

		return true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Course> findByCode(String code) {
		return em.createQuery("from EducationField where code=:c")
				.setParameter("c", code).getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Course> findByCodeAndField(String code, int educationFieldId){
		return em.createQuery("from Course where code=:c and field.id=:gid")
				.setParameter("c", code).setParameter("gid", educationFieldId).getResultList();
	}
	
	@Override
	public void fillTopics(Course c) {
	    c.setTopics(topicDao.findByCourse(c.getId()));
	}
}
