package exception;

import java.io.IOException;

/**
 * Custom exception that handles user input.
 * With the help of the validations for user input, this exception handles every possible invalid user input
 * and assures that the program wont accept any unnecessary user input, therefore preventing any following unwanted behaviour of the program.
 *
 * @author - Viktor Kurtev
 */
public final class InvalidUserInputException extends IOException {
    public InvalidUserInputException(String message) {
        super(message);
    }
}
