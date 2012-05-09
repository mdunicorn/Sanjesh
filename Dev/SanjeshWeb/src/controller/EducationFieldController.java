package controller;

import dao.EducationFieldDao;
import dao.EducationGroupDao;
import javax.faces.bean.ViewScoped;
import model.EducationField;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import model.EducationGroup;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class EducationFieldController extends EntityControllerBase<EducationField>{

    @Inject
    private EducationFieldDao dao;
    @Inject
    private EducationGroupDao groupDao;


    @PostConstruct
    public void init() {
        super.init(dao);
    }

    @Override
    public String getEntityName() {
        return "رشته تحصیلی";
    }

    @Override
    public void saveAndNew() {
        EducationGroup g = getToEdit().getGroup();
        super.saveAndNew();
        getToEdit().setGroup(g);
    }

    public List<EducationGroup> getEducationGroups(){
        return groupDao.findAll();
    }
    
    public int getSelectedGroupId(){
        if( getToEdit().getGroup() != null)
            return getToEdit().getGroup().getId();
        return 0;
    }
    
    public void setSelectedGroupId(int id){
        this.getToEdit().setGroup(groupDao.findById(id));
    }
}
