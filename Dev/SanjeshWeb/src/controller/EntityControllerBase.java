package controller;

import dao.DaoBase;
import model.EntityBase;
import java.util.List;
//import javax.annotation.PostConstruct;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ValidationException;

public abstract class EntityControllerBase<T extends EntityBase> {

	private DaoBase<T> dao;
	private List<T> list;
	private T toEdit = null;
	private T toDelete = null;

	public void init(DaoBase<T> d) {
		dao = d;
		list = dao.findAll();
	}

	public List<T> getList() {
		return list;
	}

	public T getToEdit() {
		return toEdit;
	}

	public void setToDelete(T u) {
		toDelete = u;
	}

	public void remove() {
		try {
			dao.remove(toDelete);
		} catch (EJBException e) {
			if (e.getCause() instanceof ValidationException) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getCause().getMessage()));
				return;
			}
			throw e;
		}
		list = dao.findAll();
	}

	public void edit(T u) {
		this.toEdit = u;
	}

	public void createNew() {
		this.toEdit = dao.newEntity();
	}

	public void save() {
		try {
			dao.save(toEdit);
		} catch (EJBException e) {
			if (e.getCause() instanceof ValidationException) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getCause().getMessage()));
				return;
			}
			throw e;
		}
		
		toEdit = null;
		list = dao.findAll();
	}

	public void showList() {
		toEdit = null;
		list = dao.findAll();
	}

}
