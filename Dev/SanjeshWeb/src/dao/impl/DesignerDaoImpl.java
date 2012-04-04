package dao.impl;

/**
 *
 * @author Abbas
 */
import dao.DesignerDao;
import model.Designer;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import model.RegisterState;


@Stateless
public class DesignerDaoImpl extends DaoImplBase<Designer> implements DesignerDao {

    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(Designer u) {
        if( u.getState() == RegisterState.NONE)
            u.setState(RegisterState.REGISTERED);
        super.save(u);
    }

    @SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Designer> findByState(RegisterState state){
        return em.createQuery("from Designer where state=:s").
                setParameter("s", state).getResultList();
    }

    
}
