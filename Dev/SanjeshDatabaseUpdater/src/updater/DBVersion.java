/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package updater;

import java.util.Date;

/**
 *
 * @author Muhammad
 */
public class DBVersion {
    private int minor, major;
    private Date insertDate;
    
    public int getMajor(){
        return major;
    }
    
    public void setMajor(int i){
        major = i;
    }
    
    public int getMinor(){
        return minor;
    }
    
    public void setMinor(int i){
        minor = i;
    }
    
    public Date getInsertDate(){
        return insertDate;
    }
    
    public void setInsertDate(Date d){
        insertDate = d;
    }
    
    public boolean isLessThan( int major, int minor ) {
        return this.major < major || this.minor < minor;
    }
    
    public boolean isGreaterThan( int major, int minor ) {
        return this.major > major || this.minor > minor;
    }
}
