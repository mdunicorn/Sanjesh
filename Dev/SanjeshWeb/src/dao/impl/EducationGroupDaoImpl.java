package dao.impl;

import java.util.List;

import dao.EducationGroupDao;
import model.EducationGroup;
import javax.ejb.Stateless;

/**
 * 
 * @author Muhammad
 */

@Stateless
public class EducationGroupDaoImpl extends DaoImplBase<EducationGroup> implements EducationGroupDao {

	@Override
	public boolean validateSave(EducationGroup e, List<String> messages) {

		if (!super.validateSave(e, messages))
			return false;
		List<EducationGroup> l = findByCode(e.getCode());
		if (l.size() > 1 || (l.size() == 1 && l.get(0).getId() != e.getId())) {
			messages.add("کد وارد شده تکراری است.");
			return false;
		}

		return true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EducationGroup> findByCode(String code) {
		return em.createQuery("from EducationGroup where code=:c")
				.setParameter("c", code).getResultList();
	}
}
