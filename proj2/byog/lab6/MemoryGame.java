package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Font;
import java.io.StringBufferInputStream;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40);
        game.rand.setSeed(seed);

        game.startGame();
    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random();
    }

    public String generateRandomString(int n) {
        StringBuilder rndString = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int index = rand.nextInt(26);
            rndString.append(CHARACTERS[index]);
        }
        return rndString.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.line(0,height * 0.95, width, height * 0.95);
        StdDraw.textLeft(0, height * 0.975, "Round" + s.length());
        StdDraw.text(width/2, height * 0.975, "Watch!");
        StdDraw.textRight(width, height * 0.975, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        flashSequence(s);
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        StdDraw.clear();
        char[] chars = letters.toCharArray();
        for (char c: chars) {
            StdDraw.text(width/2, height/2, String.valueOf(c));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.pause(500);
        }
        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder sb = new StringBuilder();
        while (sb.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear();
                char c = StdDraw.nextKeyTyped();
                sb.append(c);
                StdDraw.text(width / 2, height / 2, sb.toString());
                StdDraw.show();
            }
        }
        return sb.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        int round = 1;
        while (true) {
            StdDraw.clear();
            StdDraw.text(width/2, height/2, "Round:" + round);

            String rndString = generateRandomString(round);
            drawFrame(rndString);
            String answer = solicitNCharsInput(round);
            if (rndString.equals(answer)) {
                round += 1;
            } else {
                StdDraw.text(width/2, height/2, "Game Over! You made it to round:" + round);
                break;
            }
        }
        //TODO: Establish Game loop
    }


}
