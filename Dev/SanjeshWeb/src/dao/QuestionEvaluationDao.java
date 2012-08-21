package dao;

import java.util.List;

import model.QuestionEvaluation;

public interface QuestionEvaluationDao extends DaoBase<QuestionEvaluation> {

    List<QuestionEvaluation> findByQuestion(int questionId);
    List<QuestionEvaluation> findByArbiter(int arbiterId);

}
