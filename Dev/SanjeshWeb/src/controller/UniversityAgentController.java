package controller;

import dao.UniversityAgentDao;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import model.UniversityAgent;


/**
 *
 * @author Abbas
 */
@ManagedBean
@ViewScoped
public class UniversityAgentController extends EntityControllerBase<UniversityAgent> {
    
    @Inject
    private UniversityAgentDao dao;
        
    @PostConstruct
    public void init() {
        super.init(dao);
    }
}
