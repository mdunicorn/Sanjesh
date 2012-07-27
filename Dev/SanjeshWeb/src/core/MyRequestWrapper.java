package core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequestWrapper extends HttpServletRequestWrapper {

    public MyRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public String getParameter(String key) {
        String value = super.getParameter(key);
        return Utils.replaceCharacters(value);
    }
}
