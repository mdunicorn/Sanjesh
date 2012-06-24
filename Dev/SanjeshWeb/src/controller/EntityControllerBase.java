package controller;

import dao.DaoBase;
import model.EntityBase;
import java.util.List;
//import javax.annotation.PostConstruct;

import core.Utils;

public abstract class EntityControllerBase<T extends EntityBase> {

	private DaoBase<T> dao;
	private List<T> list;
	protected T toEdit = null;
	protected T toDelete = null;
	private ControllerState controllerState;
	
	public EntityControllerBase(){
	    controllerState = ControllerState.NONE;
	}

	public void init(DaoBase<T> d) {
		dao = d;
		loadList();
	}
	
	public abstract String getEntityName();
	
	private String getEntityDisplayTitle(T t){
	    String e = t.toString();
	    if( e == null || e.isEmpty())
	        return getEntityName();
	    return getEntityName() + " '" + e + "'";
	}

	public List<T> getList() {
		return list;
	}
	
	protected void setList(List<T> list) {
	    this.list = list;
	}

	public T getToEdit() {
		return toEdit;
	}

	public void setToDelete(T u) {
		toDelete = u;
	}

	public void remove() {
        controllerState = ControllerState.AFTER_DELETE;
		try {
		    if (!beforeRemove())
		        return;
			dao.remove(toDelete);
			afterRemove();
			Utils.addFacesInformationMessage(getEntityDisplayTitle(toDelete) + " حذف شد.");
		} catch (Throwable e) {
		    controllerState = ControllerState.DELETE_ERROR;
			if (Utils.handleBeanException(e))
				return;
			else
				throw e;
		}
		loadList();
	}
	
	protected boolean beforeRemove() {return true;}
	protected void afterRemove() {}

	public void edit(T u) {
		this.toEdit = u;
        controllerState = ControllerState.AFTER_EDIT;
	}

	public void createNew() {
		this.toEdit = dao.newEntity();
        controllerState = ControllerState.AFTER_NEW;
	}

	public void save() {
        controllerState = ControllerState.AFTER_SAVE;
		try {
            if (!beforeSave())
                return;
			dao.save(toEdit);
			afterSave();
	        Utils.addFacesInformationMessage(getEntityDisplayTitle(toEdit) + " ذخیره شد.");
		} catch (Throwable e) {
            controllerState = ControllerState.SAVE_ERROR;
			if (Utils.handleBeanException(e))
				return;
			else
				throw e;
		}
		showList();
	}
	
	protected boolean beforeSave() {return true;}
	protected void afterSave() {}
	
	public void saveAndNew() {
        controllerState = ControllerState.AFTER_SAVEANDNEW;
	    try {
            if (!beforeSave())
                return;
	        dao.save(toEdit);
            afterSave();
	        Utils.addFacesInformationMessage(getEntityDisplayTitle(toEdit) + " ذخیره شد.");
	        this.toEdit = dao.newEntity();
        } catch (Throwable e) {
            controllerState = ControllerState.SAVE_ERROR;
            if (Utils.handleBeanException(e))
                return;
            else
                throw e;
        }
	}

	public void showList() {
		toEdit = null;
		loadList();
	}
	
	public void loadList() {
        dao.clear();
	    list = dao.findAll();
	}

    public ControllerState getControllerState() {
        return controllerState;
    }

    public void setControllerState(ControllerState controllerState) {
        this.controllerState = controllerState;
    }
    
    public boolean isAnyErrorMessageQueued(){
        return Utils.isAnyFacesErrorMessageQueued();
    }

}
