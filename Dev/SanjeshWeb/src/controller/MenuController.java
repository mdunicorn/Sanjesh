package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import core.SecurityItems;
import core.SecurityService;

@ManagedBean
@RequestScoped
public class MenuController {
	
	public boolean getHasAccessToBasicDataRoot(){
		return SecurityService.hasPermission(SecurityItems.BasicDataRoot);
	}

	public boolean getHasAccessToUniversity(){
		return SecurityService.hasPermission(SecurityItems.University);
	}

	public boolean getHasAccessToGrade(){
		return SecurityService.hasPermission(SecurityItems.Grade);
	}

	public boolean getHasAccessToEducationGroup(){
		return SecurityService.hasPermission(SecurityItems.EducationGroup);
	}

	public boolean getHasAccessToEducationField(){
		return SecurityService.hasPermission(SecurityItems.EducationField);
	}

	public boolean getHasAccessToCourse(){
		return SecurityService.hasPermission(SecurityItems.Course);
	}

	public boolean getHasAccessToTopic(){
		return SecurityService.hasPermission(SecurityItems.Topic);
	}

	public boolean getHasAccessToUsersRoot(){
		return SecurityService.hasPermission(SecurityItems.UsersRoot);
	}
	
	public boolean getHasAccessToSanjeshAgent(){
		return SecurityService.hasPermission(SecurityItems.SanjeshAgent);
	}

	public boolean getHasAccessToUniversityAgent(){
		return SecurityService.hasPermission(SecurityItems.UniversityAgent);
	}

	public boolean getHasAccessToDesigner(){
		return SecurityService.hasPermission(SecurityItems.Designer);
	}
	
	public boolean getHasAccessToQuestionRoot(){
		return SecurityService.hasPermission(SecurityItems.QuestionRoot);
	}
	
	public boolean getHasAccessToQuestion(){
		return SecurityService.hasPermission(SecurityItems.Question);
	}
	
	

}
