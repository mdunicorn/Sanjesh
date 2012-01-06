package controller;

import dao.DaoBase;
import model.EntityBase;
import java.util.List;
//import javax.annotation.PostConstruct;

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
		dao.remove(toDelete);
	}

	public void edit(T u) {
		this.toEdit = u;
	}

	public void createNew() {
		this.toEdit = dao.newEntity();
	}

	public void save() {
		dao.save(toEdit);
		toEdit = null;
		list = dao.findAll();
	}

	public void showList() {
		toEdit = null;
		list = dao.findAll();
	}

}
