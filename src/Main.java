import engine.IGame;
import engine.impl.GreenVsRed;

public class Main {
    public static void main(String[] args) {
        IGame game = new GreenVsRed();
        System.out.println("Executing ...");
        game.execute();
    }
}
