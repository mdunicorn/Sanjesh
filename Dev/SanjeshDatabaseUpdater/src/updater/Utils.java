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
    
    public static String[] joinArrays(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        return c;
    }
}
