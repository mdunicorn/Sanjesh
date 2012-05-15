package dao.impl;

import java.util.List;

import dao.UniversityDao;
import model.University;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * 
 * @author Muhammad
 */
@Stateless
public class UniversityDaoImpl extends DaoImplBase<University> implements
		UniversityDao {

	@Override
	public boolean validateSave(University u, List<String> messages) {
		boolean isValid = super.validateSave(u, messages);
		if (!isValid)
			return false;

		List<University> l = findByCode(u.getCode());
		if (l.size() > 1 || (l.size() == 1 && l.get(0).getId() != u.getId())) {
			messages.add("کد وارد شده تکراری است.");
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<University> findByCode(String code) {
		return em.createQuery("from University where code=:c")
				.setParameter("c", code).getResultList();
	}
}
