package dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import dao.QuestionEvaluationDao;

import model.QuestionEvaluation;

@Stateless
public class QuestionEvaluationDaoImpl extends DaoImplBase<QuestionEvaluation>
    implements QuestionEvaluationDao {
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<QuestionEvaluation> findByQuestion(int questionId) {
        return em.createQuery("from QuestionEvaluation where question.id=?1", QuestionEvaluation.class).
                setParameter(1, questionId).getResultList();
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<QuestionEvaluation> findByArbiter( int arbiterId ) {
        return em.createQuery("from QuestionEvaluation where arbiter.id=?1", QuestionEvaluation.class).
                setParameter(1, arbiterId).getResultList();
    }

}
