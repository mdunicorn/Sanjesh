package controller;

import java.util.List;

import dao.DesignerDao;
import dao.GradeDao;

import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import model.Designer;
import model.Grade;

/**
 *
 * @author Abbas
 */
@ManagedBean
@ViewScoped
public class DesignerController extends EntityControllerBase<Designer> {
    
    @Inject
    private DesignerDao dao;
    @Inject
    private GradeDao gradeDao;
        
    @PostConstruct
    public void init() {
        super.init(dao);
    }
    
    public List<Grade> getGradeList(){
    	return gradeDao.findAll();
    }
    
    public int getSelectedGradeId(){
    	if( getToEdit().getGrade() != null)
    		return getToEdit().getGrade().getId();
    	return 0;    		
    }
    
    public void setSelectedGradeId(int gradeId){
    	this.getToEdit().setGrade(gradeDao.findById(gradeId));
    }

}
