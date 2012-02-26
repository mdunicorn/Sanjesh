﻿package controller;

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
					new FacesMessage(
							"رمز عبور وارد شده مطابق تکرار آن نمیباشد."));
			return;
		}
		else{
			ua.getUser().setFullName(ua.getFullName());
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
