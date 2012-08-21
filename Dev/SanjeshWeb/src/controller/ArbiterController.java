package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import dao.ArbiterDao;
import dao.EducationGroupDao;

import model.Arbiter;
import model.EducationGroup;

@ManagedBean
@ViewScoped
public class ArbiterController extends EntityControllerBase<Arbiter> {
    
    @Inject
    ArbiterDao dao;
    @Inject
    private EducationGroupDao groupDao;

    private String passwordCoinfirmation;

    @PostConstruct
    public void init() {
        super.init(dao);
    }

    @Override
    public String getEntityName() {
        return "داور";
    }

    public List<EducationGroup> getEducationGroups(){
        return groupDao.findAll();
    }
    
    public int getSelectedGroupId(){
        if( getToEdit().getEducationGroup() != null)
            return getToEdit().getEducationGroup().getId();
        return 0;
    }
    
    public void setSelectedGroupId(int id){
        if (id == 0)
            getToEdit().setEducationGroup(null);
        else
            getToEdit().setEducationGroup(groupDao.findById(id));
    }

    public String getPasswordCoinfirmation() {
        return passwordCoinfirmation;
    }

    public void setPasswordCoinfirmation(String passwordCoinfirmation) {
        this.passwordCoinfirmation = passwordCoinfirmation;
    }

    @Override
    public boolean beforeSave() {
        Arbiter ar = this.getToEdit();
        if (!this.getPasswordCoinfirmation().equals(
                ar.getUser().getPassword())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "رمز عبور وارد شده مطابق تکرار آن نمیباشد.", "لطفاً با دقت بیشتری رمز و تکرار آن را وارد نمایید."));
            return false;
        }
        else{
            this.setPasswordCoinfirmation(null);
            return true;
        }
    }
    
    @Override
    public void edit(Arbiter a){
        super.edit(a);
        this.setPasswordCoinfirmation(a.getUser().getPassword());
    }
    
    @Override
    public void createNew() {
        super.createNew();
        setPasswordCoinfirmation(null);
    }

}
