package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.IOException;
//import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
import javax.inject.Inject;
//import javax.servlet.ServletContext;

//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
//    private String reportsPath;
    private boolean isShowingReport = false;

    @PostConstruct
    public void init() {
//        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
//                .getExternalContext().getContext();
//        reportsPath = File.separator + "WEB-INF" + File.separator + "reports";
//        reportsPath = servletContext.getRealPath(reportsPath) + File.separator;
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

    public boolean getIsShowingReport() {
        return isShowingReport;
    }
    
    private List<Question> getQuestions() {
        return questionDao.findByDesignerAndCourse(designerId, courseId);
    }

//    private JasperPrint createReportPrint() throws JRException {
//        List<Question> data = getQuestions();
//        String file = reportsPath + "questions.jrxml";
//        JasperReport jasperReport = JasperCompileManager.compileReport(file);
//        return JasperFillManager.fillReport(
//                jasperReport, new HashMap<String,Object>(), new JRBeanCollectionDataSource(data));
//    }
//
//    public StreamedContent createPdf() throws JRException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JasperExportManager.exportReportToPdfStream(createReportPrint(), outputStream);
//        return new DefaultStreamedContent(new ByteArrayInputStream(outputStream.toByteArray()));
//    }
//    
//    public StreamedContent createXml() throws JRException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JasperExportManager.exportReportToXmlStream(createReportPrint(), outputStream);
//        return new DefaultStreamedContent(new ByteArrayInputStream(outputStream.toByteArray()));
//    }
    
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

    public void showReport() {
        isShowingReport = true;        
    }
}
