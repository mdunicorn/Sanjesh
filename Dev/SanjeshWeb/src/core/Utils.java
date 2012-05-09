package core;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.OptimisticLockException;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

public class Utils {

	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(
				context, "#{" + beanName + "}",
				Object.class);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Throwable> T findExceptionInChain(Throwable t, Class<? extends Throwable> type) {
		while (t != null && !t.getClass().isAssignableFrom(type)) {
			t = t.getCause();
		}
		return (T) t;
	}
	
	public static void addFacesErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}
	
	public static void addFacesInformationMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}
	
    public static boolean isAnyFacesErrorMessageQueued() {
        for (FacesMessage m : FacesContext.getCurrentInstance().getMessageList()) {
            if (m.getSeverity() == FacesMessage.SEVERITY_ERROR)
                return true;
        }
        return false;
    }
	
	public static boolean handleBeanException(Throwable e) {
		ValidationException vx = Utils.findExceptionInChain(e, ValidationException.class);
		if( vx != null ){
			Utils.addFacesErrorMessage(vx.getMessage());
			return true;
		}
		ConstraintViolationException cvx = Utils.findExceptionInChain(e, ConstraintViolationException.class);
		if( cvx != null )
		{
			String msg = cvx.getMessage();
			System.err.println(msg);

			if( StringUtils.containsIgnoreCase(msg, "fkey_educationfield_educationgroup")){
				addFacesErrorMessage("امکان حذف گروه تحصیلی وجود ندارد. این مورد در تعریف رشته تحصیلی استفاده شده است.");
			}
			else if( StringUtils.containsIgnoreCase(msg, "fkey_topic_course")){
				addFacesErrorMessage("امکان حذف درس وجود ندارد. این مورد در تعریف سرفصل استفاده شده است.");
			}
			else if( StringUtils.containsIgnoreCase(msg, "fkey_course_educationfield")){
				addFacesErrorMessage("امکان حذف رشته تحصیلی وجود ندارد. این مورد در تعریف درس استفاده شده است.");
			}
			else if( StringUtils.containsIgnoreCase(msg, "fkey_designer_grade")){
				addFacesErrorMessage("امکان حذف رتبه علمی وجود ندارد. این مورد در تعریف طراح استفاده شده است.");
			}
			else{ // not handled
				return false;
			}
			// handled in one of the above cases.
			return true;
		}
        OptimisticLockException le = findExceptionInChain(e, OptimisticLockException.class);
        if (le != null) {
            addFacesErrorMessage("امکان ثبت تغییرات وجود ندارد زیرا این مورد توسط شخص دیگری وایریش شده است. لطفا پس از زدن دکمه لغو، مجددا از روی لیست عمل ویرایش را انجام دهید.");
            return true;
        }

		return false;
	}

}
