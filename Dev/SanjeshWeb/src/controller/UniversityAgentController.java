package controller;

import dao.UniversityAgentDao;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import model.UniversityAgent;

/**
 * 
 * @author Abbas
 */
@ManagedBean
@ViewScoped
public class UniversityAgentController extends
		EntityControllerBase<UniversityAgent> {

	@Inject
	private UniversityAgentDao dao;

	private String passwordCoinfirmation;

	@PostConstruct
	public void init() {
		super.init(dao);
	}

    @Override
    public String getEntityName() {
        return "نماینده دانشگاه";
    }
    
	public String getPasswordCoinfirmation() {
		return passwordCoinfirmation;
	}

	public void setPasswordCoinfirmation(String passwordCoinfirmation) {
		this.passwordCoinfirmation = passwordCoinfirmation;
	}

	@Override
	public void save() {
		UniversityAgent ua = this.getToEdit();
		if (!this.getPasswordCoinfirmation().equals(
				ua.getUser().getPassword())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"رمز عبور وارد شده مطابق تکرار آن نمیباشد.", "لطفاً با دقت بیشتری رمز و تکرار آن را وارد نمایید."));
			return;
		}
		else{
			super.save();
			this.setPasswordCoinfirmation(null);
		}
	}
	
	@Override
	public void edit(UniversityAgent ua){
		super.edit(ua);
		this.setPasswordCoinfirmation(ua.getUser().getPassword());
	}

}
