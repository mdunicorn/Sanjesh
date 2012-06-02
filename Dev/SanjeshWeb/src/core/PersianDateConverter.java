package core;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import farsilibrary.PersianDate;

@FacesConverter("persianDateConverter")
public class PersianDateConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext fc, UIComponent component, String value) {
		if( value== null || "".equals(value)){
			return null;
		}
		return farsilibrary.PersianDateConverter.ToGregorianDateTime(value);
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent component, Object value) {
		if( value == null )
			return null;
		Date d = (Date)value;
		PersianDate pd = farsilibrary.PersianDateConverter.ToPersianDate(d);
		return pd.toString();
	}

}
