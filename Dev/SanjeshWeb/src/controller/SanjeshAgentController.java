package controller;

import dao.SanjeshAgentDao;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import model.SanjeshAgent;

/**
 *
 * @author Abbas
 */
@ManagedBean
@ViewScoped
public class SanjeshAgentController extends EntityControllerBase<SanjeshAgent> {
    
    @Inject
    private SanjeshAgentDao dao;
        
    @PostConstruct
    public void init() {
        super.init(dao);
    }    
}
