package controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ValidationException;

import core.NotLoggedInException;
import core.Utils;
import dao.SanjeshAgentDao;
import dao.UniversityAgentDao;
import dao.UserDao;

import model.Person;
import model.SanjeshAgent;
import model.UniversityAgent;
import model.User;

@ManagedBean
@ViewScoped
public class UserProfileController {

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;
	private User currentUser;
	private Person relatedPerson;

	@Inject
	private SanjeshAgentDao sanjeshAgentDao;
	@Inject
	private UniversityAgentDao universityAgentDao;
	// @Inject
	// private DesignerDao designerDao;
	@Inject
	private UserDao userDao;

	private boolean editUserName = false;
	private boolean editPassword = false;

	private String oldPassword, newPassword, confirmNewPassword;

	@PostConstruct
	public void init() {
		if (loginController == null || loginController.getCurrentUser() == null) {
			throw new NotLoggedInException();
		}
		currentUser = loginController.reloadCurrentUser();
		loadRelatedPerson();
	}

	private void loadRelatedPerson() {
		relatedPerson = sanjeshAgentDao.findByUser(currentUser.getId());
		if (relatedPerson == null)
			relatedPerson = universityAgentDao.findByUser(currentUser.getId());
		// if( relatedPerson == null )
		// relatedPerson = designerDao.find
	}
	
	private void refreshRelatedPerson(){
		if (relatedPerson instanceof UniversityAgent) {
			relatedPerson = universityAgentDao.refresh((UniversityAgent) relatedPerson);
		} else if (relatedPerson instanceof SanjeshAgent) {
			relatedPerson = sanjeshAgentDao.refresh((SanjeshAgent) relatedPerson);
		}		
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController lc) {
		this.loginController = lc;
	}

	public Person getRelatedPerson() {
		return relatedPerson;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public boolean isEditUserName() {
		return editUserName;
	}

	public boolean isEditPassword() {
		return editPassword;
	}

	public void setEditUserName() {
		editPassword = false;
		editUserName = true;
	}

	public void setEditPassword() {
		editUserName = false;
		editPassword = true;
	}

	public void cancelEdit() {
		editUserName = false;
		editPassword = false;
		
		refreshRelatedPerson();
		currentUser = loginController.reloadCurrentUser();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public void saveUserName() {

		try {
			
			userDao.save(currentUser);

			if (relatedPerson != null) {
				if (relatedPerson instanceof UniversityAgent) {
					UniversityAgent ua = (UniversityAgent) relatedPerson;
					ua.setUser(currentUser);
					universityAgentDao.save(ua);
				} else if (relatedPerson instanceof SanjeshAgent) {
					SanjeshAgent sa = (SanjeshAgent) relatedPerson;
					sa.setUser(currentUser);
					sanjeshAgentDao.save(sa);
				}
			}

			currentUser = loginController.reloadCurrentUser();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "تغییرات ثبت شدند.", null));

			editUserName = false;
			editPassword = false;
			
		} catch (Throwable e) {
			if( Utils.handleBeanException(e))
				return;
			else
				throw e;
		}
	}

	public void savePassword() {

		try {

			currentUser = loginController.reloadCurrentUser();

			if (!oldPassword.equals(currentUser.getPassword())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"رمز قبلی وارد شده صحیح نمی باشد.", null));
				return;
			}

			if (!confirmNewPassword.equals(newPassword)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"رمز جدید مطابق تکرار آن نمیباشد.",
								"لطفاً با دقت بیشتری رمز و تکرار آن را وارد نمایید."));
				return;
			}

			currentUser.setPassword(newPassword);
			userDao.save(currentUser);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "رمز جدید ثبت شد.", null));

			editUserName = false;
			editPassword = false;

		} catch (EJBException e) {
			if (e.getCause() instanceof ValidationException) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getCause().getMessage(),
								null));
				return;
			}
			throw e;
		}
	}
}
