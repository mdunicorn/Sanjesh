package dao;

import java.util.List;

import model.Course;

/**
 *
 * @author Muhammad
 */
public interface CourseDao extends DaoBase<Course>{
	List<Course> findByCode(String code);
	List<Course> findByCodeAndField(String code, int educationFieldId);
	void fillTopics(Course c);
}
