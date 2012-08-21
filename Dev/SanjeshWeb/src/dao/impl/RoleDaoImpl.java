package dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import model.Role;
import dao.RoleDao;

@Stateless
public class RoleDaoImpl extends DaoImplBase<Role> implements RoleDao {

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Role getSystemRole(int id) {
		Role r = findById(id);
		if (r == null) {
			throw new RuntimeException(
					"نقش پیدا نشد. لطفا بانک اطلاعاتی را به روز رسانی نمایید.");
		}
		return r;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Role getQuestionExpertRole() {
		return getSystemRole(Role.QUESTION_EXPERT_ROLE_ID);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Role getDesignerExpertRole() {
		return getSystemRole(Role.DESIGNER_EXPERT_ROLE_ID);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Role getArbiterExpertRole() {
		return getSystemRole(Role.ARBITER_EXPERT_ROLE_ID);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Role getDataExpertRole() {
		return getSystemRole(Role.DATA_EXPERT_ROLE_ID);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Role getUniversityAgentRole() {
		return getSystemRole(Role.UNIVERSITY_AGENT_ROLE_ID);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Role getDesignerRole() {
		return getSystemRole(Role.DESIGNER_ROLE_ID);
	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Role getArbiterRole() {
        return getSystemRole(Role.ARBITER_ROLE_ID);
    }
}
