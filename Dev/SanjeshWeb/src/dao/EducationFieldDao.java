package dao;

import java.util.List;

import model.EducationField;

/**
 *
 * @author Muhammad
 */
public interface EducationFieldDao extends DaoBase<EducationField>{
	
	List<EducationField> findByCode(String code);
	List<EducationField> findByCodeAndGroup(String code, int educationGroupId);
}
