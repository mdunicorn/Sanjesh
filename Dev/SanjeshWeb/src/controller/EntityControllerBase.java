package controller;

import dao.DaoBase;
import model.EntityBase;
import java.util.List;
//import javax.annotation.PostConstruct;

import core.Utils;

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
		} catch (Throwable e) {
			if (Utils.handleBeanException(e))
				return;
			else
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
		} catch (Throwable e) {
			if (Utils.handleBeanException(e))
				return;
			else
				throw e;
		}
		showList();
	}

	public void showList() {
		toEdit = null;
		list = dao.findAll();
	}

}
