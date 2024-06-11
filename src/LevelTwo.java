import bagel.Input;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelTwo is a child class of the Level abstract parent class
 */
public class LevelTwo extends Level{
    private static final String levelFile = ShadowMario.game_props.getProperty("level2File");
    private final ArrayList<FlyingPlatform> flyingPlatforms = new ArrayList<>();
    private final ArrayList<Power> powers = new ArrayList<>();

    /**
     * Constructor for LevelTwo
     * Creates new entities not found in the Level parent class
     */
    public LevelTwo() {
        super(levelFile);
        createNewEntities();
    }

    // Creates new entities from LevelTwo and adds them to their appropriate array lists
    private void createNewEntities() {
        List<String[]> csvData = IOUtils.readCsv(levelFile);

        for (String[] row: csvData) {
            String entityType = row[0];
            int x = Integer.parseInt(row[1]);
            int y = Integer.parseInt(row[2]);

            switch(entityType) {
                case "FLYING_PLATFORM":
                    FlyingPlatform flyingPlatform = new FlyingPlatform(x,y);
                    addEntity(flyingPlatform);
                    flyingPlatforms.add(flyingPlatform);
                    break;
                case "DOUBLE_SCORE":
                    DoubleScorePower doubleScorePower = new DoubleScorePower(x,y);
                    addEntity(doubleScorePower);
                    addCollidable(doubleScorePower);
                    powers.add(doubleScorePower);
                    break;
                case "INVINCIBLE_POWER":
                    InvinciblePower invinciblePower = new InvinciblePower(x,y);
                    addEntity(invinciblePower);
                    addCollidable(invinciblePower);
                    powers.add(invinciblePower);
                    break;
            }
        }
    }

    /**
     * Performs a state update of the level
     * Loops through each array list to perform their respective conditions
     * Checks if the winning or losing condition is true
     * @param input Input keys
     */
    @Override
    public void update (Input input) {
        // Updates game entities and check player collisions
        handlePlayerCollisions();
        updateEntities(input);

        // checks if player is on the flying platform
        for (FlyingPlatform flyingPlatform : flyingPlatforms) {
            flyingPlatform.onFlyingPlatform(getPlayer());
        }

        // checks if power is still active
        for (Power power : powers) {
            power.deactivate(getPlayer());
        }

        // checks winning and losing condition
        loseLevel();
        winLevel();
    }
}
