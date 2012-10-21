package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import dao.CourseDao;
import dao.DesignerDao;
import dao.QuestionDao;
import dao.QuestionEvaluationDao;

import model.Course;
import model.Designer;
import model.Question;
import model.QuestionEvaluation;

@ManagedBean
@ViewScoped
public class QuestionsReportController {

    @Inject
    private DesignerDao designerDao;
    @Inject
    private QuestionDao questionDao;
    @Inject
    private CourseDao courseDao;
    @Inject
    private QuestionEvaluationDao qEvalDao;

    private List<Designer> designersList;
    private List<Course> courseList;
    private int designerId = 0;
    private int courseId = 0;
    private boolean showDesigner = false, showCourse = false, showEvaluation = false, showOtherInfo = false;
    private HashMap<Integer, QuestionEvaluation> loadedEvalueations =
            new HashMap<Integer, QuestionEvaluation>();

    @PostConstruct
    public void init() {
    }

    public void loadDesigners() {
        designersList = designerDao.findAll();
    }
    
    public void loadCourses() {
        courseList = courseDao.findAll();
    }

    public List<Designer> getDesignersList() {
        if (designersList == null)
            loadDesigners();
        return designersList;
    }
    
    public List<Course> getCoursesList() {
        if (courseList == null)
            loadCourses();
        return courseList;
    }

    public int getDesignerId() {
        return designerId;
    }

    public void setDesignerId(int id) {
        designerId = id;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int id) {
        courseId = id;
    }
    
    public boolean getShowDesigner() {
        return showDesigner;
    }
    
    public void setShowDesigner (boolean showDesigner) {
        this.showDesigner = showDesigner;
    }
    
    public boolean getShowCourse() {
        return showCourse;
    }

    public void setShowCourse(boolean showCourse) {
        this.showCourse = showCourse;
    }

    public boolean getShowEvaluation() {
        return showEvaluation;
    }

    public void setShowEvaluation(boolean showEvaluation) {
        this.showEvaluation = showEvaluation;
    }

    public boolean getShowOtherInfo() {
        return showOtherInfo;
    }

    public void setShowOtherInfo(boolean showOtherInfo) {
        this.showOtherInfo = showOtherInfo;
    }

    public List<Question> getQuestions() {
        return questionDao.findByDesignerAndCourse(designerId, courseId);
    }

    
    public StreamedContent createExcel() throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.setRightToLeft(true);
        
        Font font = wb.createFont();
        font.setFontName("Tahoma");
        font.setFontHeightInPoints((short)10);
        
        HSSFCellStyle defStyle = wb.createCellStyle();
        defStyle.setFont(font);
        defStyle.setAlignment(CellStyle.ALIGN_RIGHT);
                
        Font headerFont = wb.createFont();
        headerFont.setFontName("Tahoma");
        headerFont.setFontHeightInPoints((short)10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        HSSFCellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        headerStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        HSSFRow row = sheet.createRow(0);
        row.setRowStyle(headerStyle);
        
        row.createCell(0).setCellValue("طراح");
        row.createCell(1).setCellValue("درس");
        row.createCell(2).setCellValue("سؤال");
        row.createCell(3).setCellValue("جواب صحیح");
        row.createCell(4).setCellValue("گزینه نادرست اول");
        row.createCell(5).setCellValue("گزینه نادرست دوم");
        row.createCell(6).setCellValue("گزینه نادرست سوم");
        row.createCell(7).setCellValue("درجه سختی سؤال");
        row.createCell(8).setCellValue("زمان لازم برای پاسخگویی");
        row.createCell(9).setCellValue("نتیجه ارزیابی");
        row.createCell(10).setCellValue("ارزیابی - علت");
        row.createCell(11).setCellValue("ارزیابی - زمان لازم برای پاسخگویی");
        row.createCell(12).setCellValue("ارزیابی - توضیحات");
        sheet.setColumnWidth(0, 15*256);
        sheet.setColumnWidth(1, 15*256);
        sheet.setColumnWidth(2, 20*256);
        sheet.setColumnWidth(3, 20*256);
        sheet.setColumnWidth(4, 20*256);
        sheet.setColumnWidth(5, 20*256);
        sheet.setColumnWidth(6, 20*256);
        sheet.setColumnWidth(7, 15*256);
        sheet.setColumnWidth(8, 15*256);
        sheet.setColumnWidth(9, 20*256);
        sheet.setColumnWidth(10, 20*256);
        sheet.setColumnWidth(11, 20*256);
        sheet.setColumnWidth(12, 20*256);
        
        for (int i = 0; i <= 12; i++)
            row.getCell(i).setCellStyle(headerStyle);
        
        int rowIndex = 1;
        for( Question q : getQuestions() ) {
            row = sheet.createRow(rowIndex);
            row.setRowStyle(defStyle);
            
            row.createCell(0).setCellValue(q.getDesigner().toString());
            row.createCell(1).setCellValue(q.getCourse().toString());
            row.createCell(2).setCellValue(extractTextFromHtml(q.getQuestionText()));
            row.createCell(3).setCellValue(extractTextFromHtml(q.getAnswerText()));
            row.createCell(4).setCellValue(extractTextFromHtml(q.getIncorrectOption1Text()));
            row.createCell(5).setCellValue(extractTextFromHtml(q.getIncorrectOption2Text()));
            row.createCell(6).setCellValue(extractTextFromHtml(q.getIncorrectOption3Text()));
            row.createCell(7).setCellValue(q.getQuestionLevel().toString());
            row.createCell(8).setCellValue(q.getAnswerTime());
            
            int numCells = 8;
            
            List<QuestionEvaluation> qes = qEvalDao.findByQuestion(q.getId());
            if (qes.size() > 0) {
                
                QuestionEvaluation qe = qes.get(0);
                            
                row.createCell(9).setCellValue(qe.getResult().getName());
                row.createCell(10).setCellValue(extractTextFromHtml(qe.getReason()));
                row.createCell(11).setCellValue(qe.getAnswerTime());
                row.createCell(12).setCellValue(extractTextFromHtml(qe.getComments()));
                
                numCells = 12;
            }

            for (int i = 0; i <= numCells; i++)
                row.getCell(i).setCellStyle(defStyle);

            rowIndex++;
        }
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        wb.write(output);
        
        return new DefaultStreamedContent( new ByteArrayInputStream(output.toByteArray()), "application/vnd.ms-excel", "question.xls");
    }
    
    private String extractTextFromHtml(String html) {
        if (html == null || html.isEmpty())
            return null;
        Document doc = Jsoup.parseBodyFragment(html);
        return doc.body().text();
    }

    public void showReport() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest)ec.getRequest()).getContextPath() +
                "/reports/showqreport.xhtml?designerId=" + designerId +
                "&courseId=" + courseId + "&showDesigner=" + showDesigner +
                "&showCourse=" + showCourse + "&showEvaluation=" + showEvaluation +
                "&showOtherInfo=" + showOtherInfo);
    }
    
    public QuestionEvaluation getEvaluation(int questionId) {
        if (loadedEvalueations.containsKey(questionId))
            return loadedEvalueations.get(questionId);

        List<QuestionEvaluation> qes = qEvalDao.findByQuestion(questionId);
        if (qes.size() > 0) {
            QuestionEvaluation qe = qes.get(0);
            loadedEvalueations.put(questionId, qe);
            return qe;
        }
        return null;        
    }
    
    public StreamedContent getImagesZip() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(out);
        
        int i = 1;
        for( Question q : getQuestions() ) {
            
            String number = Integer.toString(i);
            
            if (q.getQuestionImage() != null) {
                zip.putNextEntry(new ZipEntry(number + "-1-Question." +
                        getImageExtension(q.getQuestionImageFilename())));
                zip.write(q.getQuestionImage());
            }
            
            if (q.getAnswerImage() != null) {
                zip.putNextEntry(new ZipEntry(number + "-2-Answer." +
                        getImageExtension(q.getAnswerImageFilename())));
                zip.write(q.getAnswerImage());
            }

            if (q.getIncorrectOption1Image() != null) {
                zip.putNextEntry(new ZipEntry(number + "-3-Option1." +
                        getImageExtension(q.getIncorrectOption1ImageFilename())));
                zip.write(q.getIncorrectOption1Image());
            }

            if (q.getIncorrectOption2Image() != null) {
                zip.putNextEntry(new ZipEntry(number + "-4-Option2." +
                        getImageExtension(q.getIncorrectOption2ImageFilename())));
                zip.write(q.getIncorrectOption2Image());
            }
            
            if (q.getIncorrectOption3Image() != null) {
                zip.putNextEntry(new ZipEntry(number + "-5-Option3." +
                        getImageExtension(q.getIncorrectOption3ImageFilename())));
                zip.write(q.getIncorrectOption3Image());
            }
            
            i++;
        }
        zip.close();
        return new DefaultStreamedContent( new ByteArrayInputStream(out.toByteArray()), "", "images.zip");
    }
    
    private String getImageExtension(String fileName) {
        String ext = FilenameUtils.getExtension(fileName);
        if (ext == null || ext == "")
            return "jpg";
        return ext;
    }

}
