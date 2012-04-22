/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import model.Topic;

/**
 *
 * @author Muhammad
 */
public interface TopicDao extends DaoBase<Topic>{
	List<Topic> findByName(String name);
}
