package engine;

/**
 * Interface that contains the basic game functions
 *
 * @author - Viktor Kurtev
 */
public interface IGame {

    /**
     * execute() - starts the program for the game, contains setup() and start(),
     */
    void execute();

    /**
     * * setup() - initiates any prerequisite operations need to prepare the game before starting,
     */
    void setup();

    /**
     * start() - starts the core logic
     */
    void start();

}
