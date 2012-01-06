/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.UniversityDao;
import model.University;
import javax.ejb.Stateless;

/**
 *
 * @author Muhammad
 */
@Stateless
public class UniversityDaoImpl extends DaoImplBase<University> implements UniversityDao {
	@Override
	public void save(University u) {
		// TODO Auto-generated method stub
		super.save(u);
	}

}
