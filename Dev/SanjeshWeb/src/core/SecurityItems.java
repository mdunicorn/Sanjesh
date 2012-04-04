package core;

import java.util.ArrayList;
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
	public final static SecurityItem Designer = new SecurityItem("Designer", "طراح",
		DesignerNew, DesignerEdit, DesignerDelete);

	public final static SecurityItem UsersRoot = new SecurityItem("UsersRoot", "کاربران",
		SanjeshAgent, UniversityAgent, Designer);

	public final static SecurityItem QuestionNew = new SecurityItem("New", "جدید");
	public final static SecurityItem QuestionEdit = new SecurityItem("Edit", "ویرایش");
	public final static SecurityItem QuestionDelete = new SecurityItem("Delete", "حذف");
	public final static SecurityItem QuestionAccept = new SecurityItem("Accept", "تأیید/رد");
	public final static SecurityItem Question = new SecurityItem("Question", "سؤال", QuestionNew,
		QuestionEdit, QuestionDelete);

	public final static SecurityItem QuestionRoot = new SecurityItem("QuestionRoot", "سؤال",
		Question);

	public final static HashMap<Integer, List<String>> RoleAccessKeys;

	static {
		RoleAccessKeys = new HashMap<Integer, List<String>>();

		RoleAccessKeys.put(Role.QUESTION_EXPERT_ROLE_ID, getAllKeys(SecurityItems.Question));
		RoleAccessKeys.get(Role.QUESTION_EXPERT_ROLE_ID).add(SecurityItems.QuestionRoot.getFullKey());

		RoleAccessKeys.put(Role.DESIGNER_EXPERT_ROLE_ID, getAllKeys(SecurityItems.Designer));
		RoleAccessKeys.get(Role.DESIGNER_EXPERT_ROLE_ID).add(SecurityItems.UsersRoot.getFullKey());

		// RoleAccessKeys.put(Role.ARBITER_EXPERT_ROLE_ID,
		// getAllKeys(SecurityItems.)

		RoleAccessKeys.put(Role.DATA_EXPERT_ROLE_ID, getAllKeys(SecurityItems.BasicDataRoot));

		RoleAccessKeys.put(Role.UNIVERSITY_AGENT_ROLE_ID, getAllKeys(SecurityItems.Designer));
		RoleAccessKeys.get(Role.UNIVERSITY_AGENT_ROLE_ID).add(SecurityItems.UsersRoot.getFullKey());

		RoleAccessKeys.put(Role.DESIGNER_ROLE_ID, getAllKeys(SecurityItems.Question));
		RoleAccessKeys.get(Role.DESIGNER_ROLE_ID).remove(SecurityItems.QuestionAccept.getFullKey());
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
}