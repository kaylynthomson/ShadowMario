import bagel.Font;
import bagel.Image;
import bagel.*;
import bagel.util.Colour;

import java.util.Random;

/**
 * EnemyBoss class
 */
public class EnemyBoss extends MovableEntity implements Shooter, Targetable {
    private static final int FRAME_INTERVAL = 100;
    private static final int zeroFrames = 0;
    private static final int Y_STATIONARY = 0;
    private static final double MIN_HEALTH = 0;
    private final Image BOSS_IMAGE;
    private final int BOSS_SPEED;
    private final double BOSS_RADIUS;
    private final int BOSS_ACTIVATION_RADIUS;
    private final double MAX_HEALTH;
    private final Font BOSS_HEALTH_FONT;
    private double health;
    private int frameCount;
    private int speedY = Y_STATIONARY;
    private boolean shotFireball;
    private Targetable target;


    /**
     * Enemy Boss Constructor
     * @param x is the x coordinate of the object
     * @param y is the y coordinate of the object
     */
    public EnemyBoss(int x, int y) {
        super(x, y);

        // initialize values from the properties file
        BOSS_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.image"));
        MAX_HEALTH = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.health"));
        health = MAX_HEALTH;
        BOSS_RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.radius"));
        BOSS_ACTIVATION_RADIUS = Integer.parseInt(
                                 ShadowMario.game_props.getProperty("gameObjects.enemyBoss.activationRadius"));
        BOSS_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.speed"));
        BOSS_HEALTH_FONT = new Font(ShadowMario.game_props.getProperty("font"),
                Integer.parseInt(ShadowMario.game_props.getProperty("enemyBossHealth.fontSize")));

        setSpeedX(BOSS_SPEED);
        setImage(BOSS_IMAGE);
        frameCount = FRAME_INTERVAL;
    }

    /**
     * Method that shoots fireball at target randomly every 100 frames
     * @param target Targetable object
     */
    @Override
    public void shootFireball(Targetable target) {
        frameCount++;
        double distance = Math.abs(target.getX() - getX());
        if (frameCount % FRAME_INTERVAL == 0) {
            Random random = new Random();
            boolean canFire = random.nextBoolean();
            if (canFire && distance < BOSS_ACTIVATION_RADIUS && isAlive()) {
                shotFireball = true;
            }
        } else {
            shotFireball = false;
        }
    }

    /**
     * Draw Enemy Boss
     */
    @Override
    protected void draw() {
        final int SCALING_FACTOR = 100;

        BOSS_IMAGE.draw(getX(), getY());
        BOSS_HEALTH_FONT.drawString(ShadowMario.message_props.getProperty("health") +
                        Math.round(health*SCALING_FACTOR),
                Integer.parseInt(ShadowMario.game_props.getProperty("enemyBossHealth.x")),
                Integer.parseInt(ShadowMario.game_props.getProperty("enemyBossHealth.y")),
                new DrawOptions().setBlendColour(Colour.RED));
    }

    /**
     * Performs a state update for enemy boss
     * @param input is the input keys from the user
     */
    @Override
    public void update(Input input) {
        draw();
        if (!isAlive()) {
            falls();
        } else {
            move(input);
        }
    }

    /**
     * Resets the enemy boss's properties
     */
    @Override
    public void reset() {
        setX(getInitialX());
        setY(getInitialY());
        health = MAX_HEALTH;
        speedY = Y_STATIONARY;
        frameCount = zeroFrames;
    }

    /**
     * Boolean to check boss's alive status
     */
    public boolean isAlive() {
        return health > MIN_HEALTH;
    }

    /**
     * Setter for the boss's target
     * @param target Targetable object that boss targets
     */
    public void setTarget(Targetable target) {
        this.target = target;
    }

    /**
     * Getter for getShotFireball boolean
     * @return getShotFireball boolean
     */
    @Override
    public boolean getShotFireball() {
        return shotFireball;
    }

    /**
     * Getter for the boss's target
     * @return boss's target
     */
    @Override
    public Targetable getTarget() {
        return target;
    }

    /**
     * Getter for boss's health
     * @return boss's health
     */
    @Override
    public double getHealth() {
        return health;
    }

    /**
     * Setter for boss's health
     * @param health value to set the boss's health
     */
    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Getter for boss's radius
     * @return boss's radius
     */
    @Override
    public double getRadius() {
        return BOSS_RADIUS;
    }

    /**
     * Getter for boss's speedY
     * @return boss's speedY
     */
    @Override
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Setter for boss's speedY
     * @param speedY value to set the target's speedY
     */
    @Override
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

}
