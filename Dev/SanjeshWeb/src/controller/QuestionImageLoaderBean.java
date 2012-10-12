package controller;

import java.io.ByteArrayInputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import model.Question;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import core.Utils;

import dao.QuestionDao;

@ManagedBean
@SessionScoped
public class QuestionImageLoaderBean {
    @Inject
    private QuestionDao dao;
        
    private static StreamedContent getDefaultStreamedContentImage() {
        //String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String path = "/resources/images/question2.png";
        StreamedContent sc = new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(path), "image/png");
        return sc;
    }
    
    private static StreamedContent getEmptyStreamedContentImage() {
        //String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String path = "/resources/images/spacer.gif";
        StreamedContent sc = new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(path), "image/gif");
        return sc;
    }    
    
    private static Integer getQuestionIdRequestParameter() {
        String id = FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("questionId");
        
        if (id == null || id.isEmpty())
            return null;
        return Integer.parseInt(id);
    }
    
    public String getQuestionUniqueIdRequesParameter() {
        String id = FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("uniqueId");
        if (id == null || id.isEmpty())
            return null;
        return id;                    
    }
    
    public Question getQuestionFromSession() {
        String id = getQuestionUniqueIdRequesParameter();
        if (id != null)
            return (Question)Utils.getSessionMap().get(id);
        return null;
    }
    
    private static boolean getShowDefaultImage() {
        String str = FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("nodefault");
        if (str == null || str.isEmpty())
            return true;
        return false;
    }
    
    private static StreamedContent getStreamedContent(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
        {
            if (getShowDefaultImage())
                return getDefaultStreamedContentImage();
            else
                return getEmptyStreamedContentImage();
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image");        
    }
    
    public StreamedContent getQuestionImageContent() {
        Question q = getQuestionFromSession();
        if (q != null && q.getQuestionImage() != null)
            return getStreamedContent(q.getQuestionImage());

        Integer id = getQuestionIdRequestParameter(); 
        if (id == null || id == 0)
            return getDefaultStreamedContentImage();

        return getStreamedContent( dao.getQuestionImage(id) );
    }
    
    public StreamedContent getAnswerImageContent() {
        
        Question q = getQuestionFromSession();
        if (q != null && q.getAnswerImage() != null)
            return getStreamedContent(q.getAnswerImage());
        
        Integer id = getQuestionIdRequestParameter(); 
        if (id == null || id == 0)
            return getDefaultStreamedContentImage();

        return getStreamedContent( dao.getAnswerImage(id) );
    }
    
    public StreamedContent getIncorrectOption1ImageContent() {
        Question q = getQuestionFromSession();
        if (q != null && q.getIncorrectOption1Image() != null)
            return getStreamedContent(q.getIncorrectOption1Image());

        Integer id = getQuestionIdRequestParameter(); 
        if (id == null || id == 0)
            return getDefaultStreamedContentImage();

        return getStreamedContent( dao.getIncorrectOption1Image(id) );
    }
    
    public StreamedContent getIncorrectOption2ImageContent() {
        Question q = getQuestionFromSession();
        if (q != null && q.getIncorrectOption2Image() != null)
            return getStreamedContent(q.getIncorrectOption2Image());

        Integer id = getQuestionIdRequestParameter(); 
        if (id == null || id == 0)
            return getDefaultStreamedContentImage();

        return getStreamedContent( dao.getIncorrectOption2Image(id) );
    }
    
    public StreamedContent getIncorrectOption3ImageContent() {
        Question q = getQuestionFromSession();
        if (q != null && q.getIncorrectOption3Image() != null)
            return getStreamedContent(q.getIncorrectOption3Image());

        Integer id = getQuestionIdRequestParameter(); 
        if (id == null || id == 0)
            return getDefaultStreamedContentImage();

        return getStreamedContent( dao.getIncorrectOption3Image(id) );
    }
    
}
