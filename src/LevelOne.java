/**
 * LevelOne is a child class of the abstract parent class Level
 */
public class LevelOne extends Level{
    private static final String levelFile = ShadowMario.game_props.getProperty("level1File");

    /**
     * Constructor for level 1
     */
    public LevelOne() {
        super(levelFile);
    }
}
