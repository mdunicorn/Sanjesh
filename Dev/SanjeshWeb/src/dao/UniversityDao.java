package dao;

import java.util.List;

import model.University;

/**
 *
 * @author Muhammad
 */
public interface UniversityDao extends DaoBase<University> {
	
	List<University> findByCode(String code);
	
}
