package controller;

import dao.EducationGroupDao;
import javax.faces.bean.ViewScoped;
import model.EducationGroup;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class EducationGroupController extends EntityControllerBase<EducationGroup>{

    @Inject
    private EducationGroupDao dao;

    @PostConstruct
    public void init() {
        super.init(dao);
    }

    @Override
    public String getEntityName() {
        return "گروه تحصیلی";
    }
}
