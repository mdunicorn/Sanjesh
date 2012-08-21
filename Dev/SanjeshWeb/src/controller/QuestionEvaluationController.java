package controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import core.SecurityItems;
import core.SecurityService;
import dao.QuestionEvaluationDao;

import model.Arbiter;
import model.Person;
import model.QuestionEvaluation;


@ManagedBean
@ViewScoped
public class QuestionEvaluationController extends EntityControllerBase<QuestionEvaluation>{

    @Inject
    QuestionEvaluationDao dao;
    
    @PostConstruct
    public void init() {
        super.init(dao);
    }
    
    @Override
    public String getEntityName() {
        return "ارزیابی";
    }

    public boolean getHasAccessToEditQuestionEvaluation() {
        return SecurityService.hasPermission(SecurityItems.QuestionEvaluationEdit);
    }

    public boolean getHasAccessToDeleteQuestionEvaluation() {
        return SecurityService.hasPermission(SecurityItems.QuestionEvaluationDelete);
    }
    
    @Override
    public void loadList() {
        if (hasAccessToViewAllEvaluations()) {
            super.loadList();            
        } else {
            dao.clear();
            LoginBean lb = SecurityService.getLoginBean();
            Person p = lb.getRelatedPerson();
            if (p instanceof Arbiter) {
                setList(dao.findByArbiter(((Arbiter) p).getId()));
            } else {
                // What should I do?
            }            
        }
    }
    
    public boolean hasAccessToViewAllEvaluations() {
        return SecurityService.hasPermission(SecurityItems.QuestionEvaluationViewAll);
    }
}
