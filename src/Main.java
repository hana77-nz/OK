import view.GameView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameView view = new GameView();
        view.run(scanner);
    }

}