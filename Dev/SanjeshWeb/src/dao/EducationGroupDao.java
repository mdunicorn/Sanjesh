package dao;

import java.util.List;

import model.EducationGroup;

/**
 *
 * @author Muhammad
 */
public interface EducationGroupDao extends DaoBase<EducationGroup> {
	List<EducationGroup> findByCode(String code);
}
