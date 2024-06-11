import bagel.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2024
 * @author Kaylyn Thomson Phan
 */
public class ShadowMario extends AbstractGame {
    /**
     * game_props variable to store reading app properties file
     */
    public static final Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    /**
     * message_props variable to store reading message properties file
     */
    public static final Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    /**
     * windowWidth of the game
     */
    public static final int windowWidth = Integer.parseInt(game_props.getProperty("windowWidth"));
    private final Image BACKGROUND_IMAGE;
    private final Font TITLE_FONT;
    private final Font INSTRUCTION_FONT;
    private final Font MESSAGE_FONT;
    private final ArrayList<Level> levels = new ArrayList<>();
    private int levelOneIndex;
    private int levelTwoIndex;
    private int levelThreeIndex;
    private boolean start = false;
    private boolean finished = false;

    /**
     * Shadow Mario Constructor
     * @param game_props game properties file
     * @param message_props message properties file
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(windowWidth, Integer.parseInt(game_props.getProperty("windowHeight")),
                message_props.getProperty("title"));

        // Initialising values from the property files
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        TITLE_FONT = new Font(game_props.getProperty("font"),
                Integer.parseInt(game_props.getProperty("title.fontSize")));
        INSTRUCTION_FONT = new Font(game_props.getProperty("font"),
                Integer.parseInt(game_props.getProperty("instruction.fontSize")));
        MESSAGE_FONT = new Font(game_props.getProperty("font"),
                Integer.parseInt(game_props.getProperty("message.fontSize")));

        // Create the three levels in the game
        createLevels();
    }

    // Instantiate levels in the game and store their indexes
    private void createLevels() {
        LevelOne levelOne = new LevelOne();
        levels.add(levelOne);
        levelOneIndex = levels.indexOf(levelOne);

        LevelTwo levelTwo = new LevelTwo();
        levels.add(levelTwo);
        levelTwoIndex = levels.indexOf(levelTwo);

        LevelThree levelThree = new LevelThree();
        levels.add(levelThree);
        levelThreeIndex = levels.indexOf(levelThree);
    }

    // Checks if a level is finished
    private void checkFinishLevel() {
        for (Level level : levels) {
            if (level.getFinishedLevel()) {
                finished = true;
                break;
            }
        }
    }

    // Displays start screen with game instructions
    private void renderStartScreen() {
        TITLE_FONT.drawString(message_props.getProperty("title"),
                Integer.parseInt(game_props.getProperty("title.x")),
                Integer.parseInt(game_props.getProperty("title.y")));
        INSTRUCTION_FONT.drawString(message_props.getProperty("instruction"),
                Window.getWidth()/2.0 - INSTRUCTION_FONT.getWidth(message_props.getProperty("instruction"))/2.0,
                Integer.parseInt(game_props.getProperty("instruction.y")));
    }

    // Displays end screen depending on win or lose condition
    private void renderEndScreen() {
        for (Level level : levels) {
            if (level.getGameLost()) {
                MESSAGE_FONT.drawString(message_props.getProperty("gameOver"),
                        Window.getWidth()/3.5,
                        Integer.parseInt(game_props.getProperty("message.y")));
            } else if (level.getGameWon()) {
                MESSAGE_FONT.drawString(message_props.getProperty("gameWon"),
                        Window.getWidth()/3.5,
                        Integer.parseInt(game_props.getProperty("message.y")));
            }
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update of the selected level.
     * Allows the game to exit when the escape key is pressed.
     * Handle screen navigation between levels and instruction pages here.
     */
    @Override
    protected void update(Input input) {

        // draw background image
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        // close window
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // display start screen with instructions when 1,2,3 is not pressed
        if (!start) {
            renderStartScreen();
            if (input.wasPressed(Keys.NUM_1)) {
                start = true;
                levels.get(levelOneIndex).setStartedLevel(true);
            } else if (input.wasPressed(Keys.NUM_2)) {
                start = true;
                levels.get(levelTwoIndex).setStartedLevel(true);
            } else if (input.wasPressed(Keys.NUM_3)) {
                start = true;
                levels.get(levelThreeIndex).setStartedLevel(true);
            }

        // display end screen and allow game restart when space is pressed
        } else if (finished) {
            renderEndScreen();
            if (input.wasPressed(Keys.SPACE)) {
                for (Level level : levels) {
                    if (level.getStartedLevel()) {
                        level.resetGame();
                        start = false;
                        finished = false;
                        break;
                    }
                }
            }
        }

        // display the game play depending on the level picked
        else {
            for (Level level : levels) {
                if (level.getStartedLevel()) {
                    level.update(input);
                    break;
                }
            }
            checkFinishLevel();
        }
    }
}
