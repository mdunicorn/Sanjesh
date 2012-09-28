package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.Role;

public class SecurityItems {

	public final static SecurityItem UniversityNew = new SecurityItem("New", "جدید");
	public final static SecurityItem UniversityEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem UniversityDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem University = new SecurityItem("University", "دانشگاه",
		UniversityNew, UniversityEdit, UniversityDelete);

	public final static SecurityItem GradeNew = new SecurityItem("New", "جدید");
	public final static SecurityItem GradeEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem GradeDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem Grade = new SecurityItem("Grade", "رتبه علمی",
		GradeNew, GradeEdit, GradeDelete);

	public final static SecurityItem EducationFieldNew = new SecurityItem("New", "جدید");
	public final static SecurityItem EducationFieldEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem EducationFieldDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem EducationField = new SecurityItem("EducationField",
		"رشته تحصیلی", EducationFieldNew, EducationFieldEdit, EducationFieldDelete);

	public final static SecurityItem EducationGroupNew = new SecurityItem("New", "جدید");
	public final static SecurityItem EducationGroupEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem EducationGroupDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem EducationGroup = new SecurityItem("EducationGroup",
		"گروه تحصیلی", EducationGroupNew, EducationGroupEdit, EducationGroupDelete);

	public final static SecurityItem CourseNew = new SecurityItem("New", "جدید");
	public final static SecurityItem CourseEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem CourseDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem Course = new SecurityItem("Course", "درس",
		CourseNew, CourseEdit, CourseDelete);

	public final static SecurityItem TopicNew = new SecurityItem("New", "جدید");
	public final static SecurityItem TopicEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem TopicDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem Topic = new SecurityItem("Topic", "سرفصل",
		TopicNew, TopicEdit, TopicDelete);

	public final static SecurityItem BasicDataRoot = new SecurityItem("BasicDataRoot",
		"اطلاعات پایه", University, Grade, EducationField, EducationGroup, Course, Topic);

	public final static SecurityItem SanjeshAgentNew = new SecurityItem("New", "جدید");
	public final static SecurityItem SanjeshAgentEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem SanjeshAgentDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem SanjeshAgent = new SecurityItem("SanjeshAgent",
		"نماینده سازمان سنجش", SanjeshAgentNew, SanjeshAgentEdit, SanjeshAgentDelete);

	public final static SecurityItem UniversityAgentNew = new SecurityItem("New", "جدید");
	public final static SecurityItem UniversityAgentEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem UniversityAgentDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem UniversityAgentAccept = new SecurityItem("Accept", "تأیید/رد");
	public final static SecurityItem UniversityAgent = new SecurityItem("UniversityAgent",
		"نماینده دانشگاه", UniversityAgentNew, UniversityAgentEdit, UniversityAgentDelete, UniversityAgentAccept);

	public final static SecurityItem DesignerNew = new SecurityItem("New", "جدید");
	public final static SecurityItem DesignerEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem DesignerDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem DesignerAccept = new SecurityItem("Accept", "تأیید/رد");
	public final static SecurityItem DesignerViewAll = new SecurityItem("ViewAll", "مشاهده همه موارد");
	public final static SecurityItem Designer = new SecurityItem("Designer", "طراح",
		DesignerNew, DesignerEdit, DesignerDelete, DesignerAccept, DesignerViewAll);
	
	public final static SecurityItem ArbiterNew = new SecurityItem("New", "جدید");
    public final static SecurityItem ArbiterEdit = new SecurityItem("Edit", "ویرایش");
    public final static SecurityItem ArbiterDelete = new SecurityItem("Delete", "حذف");
    public final static SecurityItem Arbiter = new SecurityItem("Arbiter", "داور",
            ArbiterNew, ArbiterEdit, ArbiterDelete);

	public final static SecurityItem UsersRoot = new SecurityItem("UsersRoot", "کاربران",
		SanjeshAgent, UniversityAgent, Designer, Arbiter);

	public final static SecurityItem QuestionNew = new SecurityItem("New", "جدید");
	public final static SecurityItem QuestionEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem QuestionDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem QuestionAccept = new SecurityItem("Accept", "تأیید/رد");
	public final static SecurityItem QuestionViewAll = new SecurityItem("ViewAll", "مشاهده همه موارد");
	public final static SecurityItem Question = new SecurityItem("Question", "سؤال",
	        QuestionNew, QuestionEdit, QuestionDelete, QuestionAccept, QuestionViewAll);

	public final static SecurityItem QuestionEvaluationNew = new SecurityItem("New", "جدید");
	public final static SecurityItem QuestionEvaluationEdit = new SecurityItem("Edit", "ویرایش");
    public final static SecurityItem QuestionEvaluationDelete = new SecurityItem("Delete", "حذف");
    public final static SecurityItem QuestionEvaluationViewAll = new SecurityItem("ViewAll", "مشاهده همه");
	public final static SecurityItem QuestionEvaluation = new SecurityItem(
	        "QuestionEvaluation", "ارزیابی سؤال", QuestionEvaluationNew, QuestionEvaluationEdit,
	        QuestionEvaluationViewAll, QuestionEvaluationDelete);

    public final static SecurityItem QuestionRoot = new SecurityItem("QuestionRoot", "سؤال",
            Question, QuestionEvaluation);
    
    public final static SecurityItem AllQuestionsReport = new SecurityItem(
            "AllQuestions", "تمام سؤالات");
    
    public final static SecurityItem ReportsRoot = new SecurityItem(
            "ReportsRoot", "گزارشات", AllQuestionsReport );
        

    public final static HashMap<Integer, List<String>> RoleAccessKeys;

	static {
		RoleAccessKeys = new HashMap<Integer, List<String>>();

		addAccessKeysToRole(
		        Role.QUESTION_EXPERT_ROLE_ID,
		            SecurityItems.QuestionRoot.getFullKey(),
		            getAllKeys(SecurityItems.Question));

		addAccessKeysToRole(
		        Role.DESIGNER_EXPERT_ROLE_ID,
		            SecurityItems.UsersRoot.getFullKey(),
		            getAllKeys(SecurityItems.Designer));
		

		addAccessKeysToRole(
		        Role.ARBITER_EXPERT_ROLE_ID,
		            getAllKeys(SecurityItems.Arbiter));		        

		addAccessKeysToRole(
		        Role.DATA_EXPERT_ROLE_ID,
		            getAllKeys(SecurityItems.BasicDataRoot));

		addAccessKeysToRole(
		        Role.UNIVERSITY_AGENT_ROLE_ID,
		            SecurityItems.UsersRoot.getFullKey(),
		            getAllKeys(SecurityItems.Designer)).
		        remove(SecurityItems.DesignerAccept.getFullKey());
		getRoleKeyList(Role.UNIVERSITY_AGENT_ROLE_ID).remove(SecurityItems.DesignerViewAll.getFullKey());

		addAccessKeysToRole(
		        Role.DESIGNER_ROLE_ID,
		            SecurityItems.QuestionRoot.getFullKey(),
		            getAllKeys(SecurityItems.Question)).
		        remove(SecurityItems.QuestionAccept.getFullKey());
		getRoleKeyList(Role.DESIGNER_ROLE_ID).remove(SecurityItems.QuestionViewAll.getFullKey());
		
		addAccessKeysToRole(
		        Role.ARBITER_ROLE_ID,
		            SecurityItems.QuestionRoot.getFullKey(),
		            SecurityItems.Question.getFullKey());
        addAccessKeysToRole(
                Role.ARBITER_ROLE_ID,
		            getAllKeys(SecurityItems.QuestionEvaluation)).
		        remove(SecurityItems.QuestionEvaluationViewAll.getFullKey());
	}

	private static List<String> getAllKeys(SecurityItem si) {
		ArrayList<String> keys = new ArrayList<String>();
		getAllKeys(si, keys);
		return keys;
	}

	private static void getAllKeys(SecurityItem securityItem, List<String> keys) {
		keys.add(securityItem.getFullKey());
		for (SecurityItem si : securityItem.getChildren())
			getAllKeys(si, keys);
	}
	
    private static List<String> getRoleKeyList(int roleId) {
        List<String> keyList = RoleAccessKeys.get(roleId);
	    if (null == keyList) {
	        keyList = new ArrayList<String>();
	        RoleAccessKeys.put(roleId, keyList);
	    }
        return keyList;
    }
	
    private static List<String> addAccessKeysToRole(int roleId, String... keys) {
        List<String> keyList = getRoleKeyList(roleId);
        keyList.addAll(Arrays.asList(keys));
        return keyList;
    }

    private static List<String> addAccessKeysToRole(int roleId, List<String> keys) {
        List<String> keyList = getRoleKeyList(roleId);
        keyList.addAll(keys);
        return keyList;
    }
    
    private static List<String> addAccessKeysToRole(int roleId, String key, List<String> keys) {
	    List<String> keyList = getRoleKeyList(roleId);
	    keyList.add(key);
	    keyList.addAll(keys);
	    return keyList;
	}

}