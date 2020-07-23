package engine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class (Service) responsible for the extraction of user input.
 * Implements Singleton Design Pattern.
 *
 * @author - Viktor Kurtev
 */
public final class UserInputService {

    /**
     * Static BufferedReader variable that will be instanced one time only.
     */
    private static BufferedReader br;

    /**
     * Static method responsible for the extraction of user input.
     * Keeps sure that we have only one instance of type BufferedReader.
     *
     * @return single line of text from the user input .
     */
    public static String getUserInput() {
        if (br == null) {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Method responsible for closing the buffered reader.
     * Prevents memory leaks.
     */
    public static void closeBFReader() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
