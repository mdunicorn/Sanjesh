package dao;

import model.Role;

public interface RoleDao extends DaoBase<Role> {
	Role getQuestionExpertRole();
	Role getDesignerExpertRole();
	Role getArbiterExpertRole();
	Role getDataExpertRole();
	Role getUniversityAgentRole();
	Role getDesignerRole();
}
