import bagel.Input;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelThree is a child class of the Level abstract class
 */
public class LevelThree extends Level{
    private static final String levelFile = ShadowMario.game_props.getProperty("level3File");
    private final ArrayList<FlyingPlatform> flyingPlatforms = new ArrayList<>();
    private final ArrayList<Power> powers = new ArrayList<>();
    private final ArrayList<Shooter> shooters = new ArrayList<>();
    private final ArrayList<Fireball> fireballs = new ArrayList<>();
    private EnemyBoss enemyBoss;

    /**
     * Constructor for LevelThree
     * Creates new entities not found in the Level parent class
     */
    public LevelThree() {
        super(levelFile);
        createNewEntities();
    }

    // Creates new entities from LevelThree and adds them to their appropriate array lists
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
                case "ENEMY_BOSS":
                    this.enemyBoss = new EnemyBoss(x,y);
                    addEntity(enemyBoss);
                    shooters.add(enemyBoss);
                    enemyBoss.setTarget(getPlayer());

            }
        }

        shooters.add(getPlayer());
        getPlayer().setTarget(enemyBoss);
    }

    // Method that handles the shooting and disappearing of fireballs
    private void handleFireballs(Input input) {
        List<Fireball> fireballsToRemove = new ArrayList<>();

        // Shoots a fireball if shooters shooting condition is held
        if (enemyBoss.isAlive()) {
            for (Shooter shooter : shooters) {
                shooter.shootFireball(shooter.getTarget());
                if (shooter.getShotFireball()) {
                    Fireball fireball = new Fireball(shooter.getX(), shooter.getY(), shooter.getTarget());
                    fireballs.add(fireball);
                }
            }
        }


        // Removes fireball if they're not active, otherwise update it
        for (Fireball fireball : fireballs) {
            if (!fireball.isActive() || fireball.getHasCollided()) {
                fireballsToRemove.add(fireball);
            } else {
                fireball.update(input);
                fireball.handleCollision(fireball.getTarget(), fireball);
            }
        }
        fireballs.removeAll(fireballsToRemove);
    }

    // Checks the win level conditions
    @Override
    protected void winLevel() {
        if (enemyBoss.isAlive()) {
            getCollidables().get(getEndFlagIndex()).setHasCollided(false);
        } else if (getCollidables().get(getEndFlagIndex()).getHasCollided() && !enemyBoss.isAlive()) {
            setFinishedLevel(true);
            setGameWon(true);
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
        updateEntities(input);
        handlePlayerCollisions();

        // checks if player is on the flying platform
        for (FlyingPlatform flyingPlatform : flyingPlatforms) {
            flyingPlatform.onFlyingPlatform(getPlayer());
        }

        // checks if power is still active
        for (Power power : powers) {
            power.deactivate(getPlayer());
        }

        // handles shooter and fireball conditions
        handleFireballs(input);

        // checks losing and winning condition
        loseLevel();
        winLevel();
    }
}
