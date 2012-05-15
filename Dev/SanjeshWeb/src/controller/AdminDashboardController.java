package controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import dao.DesignerDao;
import model.Designer;
import model.RegisterState;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class AdminDashboardController {
    
    @Inject
    private DesignerDao designerDao;
    private List<Designer> unacceptedDesigners;
    
    @PostConstruct
    public void init(){
        unacceptedDesigners = designerDao.findByState(RegisterState.REGISTERED);
    }
    
    public List<Designer> getUnacceptedDesigners(){
        return unacceptedDesigners;
    }
    
}
