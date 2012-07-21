package dao;


import java.util.List;

import model.Question;
/**
 *
 * @author Abbas
 */
public interface QuestionDao extends DaoBase<Question>{

    byte[] getQuestionImage(int questionId);
    byte[] getAnswerImage(int questionId);
    byte[] getIncorrectOption1Image(int questionId);
    byte[] getIncorrectOption2Image(int questionId);
    byte[] getIncorrectOption3Image(int questionId);
    List<Question> loadQuestionsCreatedByUser(int userId);
}