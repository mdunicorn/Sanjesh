/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package updater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Muhammad
 */
public class Utils {

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    public static String readFile(String fileName) throws FileNotFoundException, IOException {
        File f = new File(fileName);
        char[] buf = new char[(int) f.length()];
        FileReader reader = new FileReader(fileName);
        try {
            reader.read(buf);
        } finally {
            reader.close();
        }
        return new String(buf);
    }
}
