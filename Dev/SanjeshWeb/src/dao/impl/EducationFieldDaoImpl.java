package dao.impl;

import java.util.List;

import dao.EducationFieldDao;
import model.EducationField;

import javax.ejb.Stateless;

/**
 *
 * @author Muhammad
 */
@Stateless
public class EducationFieldDaoImpl extends DaoImplBase<EducationField> implements EducationFieldDao {
	
	@Override
	public boolean validateSave(EducationField e, List<String> messages) {

		if (!super.validateSave(e, messages))
			return false;
		
		List<EducationField> l = findByCodeAndGroup(e.getCode(), e.getGroup().getId());
		if (l.size() > 1 || (l.size() == 1 && l.get(0).getId() != e.getId())) {
			messages.add("کد وارد شده در این گروه تکراری است.");
			return false;
		}

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EducationField> findByCode(String code) {
		return em.createQuery("from EducationField where code=:c")
				.setParameter("c", code).getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EducationField> findByCodeAndGroup(String code, int educationGroupId){
		return em.createQuery("from EducationField where code=:c and group.id=:gid")
				.setParameter("c", code).setParameter("gid", educationGroupId).getResultList();
	}

}
