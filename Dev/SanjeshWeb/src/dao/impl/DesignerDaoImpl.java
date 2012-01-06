package dao.impl;

/**
 *
 * @author Abbas
 */
import dao.DesignerDao;
import model.Designer;
import java.util.List;
import javax.ejb.Stateless;
import model.RegisterState;


@Stateless
public class DesignerDaoImpl extends DaoImplBase<Designer> implements DesignerDao {

    @Override
    public void save(Designer u) {
        if( u.getState() == RegisterState.NONE)
            u.setState(RegisterState.REGISTERED);
        super.save(u);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Designer> findByState(RegisterState state){
        return em.createQuery("from Designer where state=:s").
                setParameter("s", state).getResultList();
    }

    
}
