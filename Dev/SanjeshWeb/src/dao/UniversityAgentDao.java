package dao;


import model.UniversityAgent;

/**
 *
 * @author Abbas
 */

public interface UniversityAgentDao extends DaoBase<UniversityAgent>{
	public UniversityAgent findByUser(int userId);
}
