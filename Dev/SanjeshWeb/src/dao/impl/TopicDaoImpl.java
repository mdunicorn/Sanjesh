/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import java.util.List;

import dao.TopicDao;
import model.Topic;
import javax.ejb.Stateless;

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
	public List<Topic> findByName(String name) {
		return em.createQuery("from Topic where name=:n").setParameter("n", name).getResultList();
	}

}
