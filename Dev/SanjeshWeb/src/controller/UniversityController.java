package controller;

import dao.UniversityDao;
import javax.faces.bean.ViewScoped;
import model.University;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class UniversityController extends EntityControllerBase<University>{

    @Inject
    private UniversityDao dao;

    @PostConstruct
    public void init() {
        super.init(dao);
    }

}
