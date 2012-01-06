package controller;

import dao.GradeDao;
import javax.faces.bean.ViewScoped;
import model.Grade;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class GradeController extends EntityControllerBase<Grade> {

    @Inject
    private GradeDao dao;

    @PostConstruct
    public void init() {
        super.init(dao);
    }
}
