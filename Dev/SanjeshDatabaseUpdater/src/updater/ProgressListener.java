/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package updater;

/**
 *
 * @author Muhammad
 */
public interface ProgressListener {
    void progressChanged(float percent);
    void logEvent(LogCategory category, String message);
    void executingQuery(String query);
}
