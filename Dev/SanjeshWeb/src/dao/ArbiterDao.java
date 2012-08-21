package dao;

import java.util.List;

import model.Arbiter;

public interface ArbiterDao extends DaoBase<Arbiter> {

    Arbiter findByUser(int userId);
    List<Arbiter> findByEducationGroup(int egroupId);
}
