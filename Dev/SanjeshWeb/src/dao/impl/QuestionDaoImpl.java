package dao.impl;

/**
 *
 * @author Abbas
 */
import java.util.List;

import dao.QuestionDao;
import model.Designer;
import model.Person;
import model.Question;
import model.QuestionLevel;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import controller.LoginBean;


@Stateless
public class QuestionDaoImpl extends DaoImplBase<Question> implements QuestionDao {
        
    @Override
    public Question newEntity() {
        Question q = super.newEntity();
        q.setQuestionLevel(QuestionLevel.NotSpecified);
        Person p = LoginBean.getInstance().getRelatedPerson();
        if (p instanceof Designer)
            q.setDesigner((Designer)p);
        return q;
    }
    
    @Override
    public byte[] getQuestionImage(int questionId) {
        List<byte[]> result = em.createQuery("select q.questionImage from Question q where q.id=?1", byte[].class).
            setParameter(1, questionId).getResultList();
        if (result.size() == 0)
            return null;
        return result.get(0);
    }

    @Override
    public byte[] getAnswerImage(int questionId) {
        List<byte[]> result = em.createQuery("select q.answerImage from Question q where q.id=?1", byte[].class).
            setParameter(1, questionId).getResultList();
        if (result.size() == 0)
            return null;
        return result.get(0);
    }

    @Override
    public byte[] getIncorrectOption1Image(int questionId) {
        List<byte[]> result = em.createQuery("select q.incorrectOption1Image from Question q where q.id=?1", byte[].class).
            setParameter(1, questionId).getResultList();
        if (result.size() == 0)
            return null;
        return result.get(0);
    }

    @Override
    public byte[] getIncorrectOption2Image(int questionId) {
        List<byte[]> result = em.createQuery("select q.incorrectOption2Image from Question q where q.id=?1", byte[].class).
            setParameter(1, questionId).getResultList();
        if (result.size() == 0)
            return null;
        return result.get(0);
    }

    @Override
    public byte[] getIncorrectOption3Image(int questionId) {
        List<byte[]> result = em.createQuery("select q.incorrectOption3Image from Question q where q.id=?1", byte[].class).
            setParameter(1, questionId).getResultList();
        if (result.size() == 0)
            return null;
        return result.get(0);
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Question> loadQuestionsCreatedByUser(int userId) {
        String cmd =
                "SELECT d.* FROM "
                + "  question d "
                + "  LEFT JOIN question_aud da ON d.question_id=da.question_id "
                + "  LEFT JOIN revinfo ri ON da.rev=ri.rev "
                + "WHERE "
                + "  da.revtype=0 AND ri.suser=?";
        
        return em.createNativeQuery(cmd, Question.class).
                setParameter(1, userId).
                getResultList();
    }
}
