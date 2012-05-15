package dao;

import java.util.List;

import model.Topic;

/**
 *
 * @author Muhammad
 */
public interface TopicDao extends DaoBase<Topic>{
	List<Topic> findByName(String name);
	List<Topic> findByCourse(int courseId);
}
