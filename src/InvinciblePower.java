import bagel.Image;
import bagel.Input;

/**
 * Invincible Power class
 */
public class InvinciblePower extends MovableEntity implements Collidable, Collectable,Power {
    private static final int Y_STATIONARY = 0;
    private static final int zeroFrames = 0;
    private final Image INVINCIBLE_POWER_IMAGE;
    private final int INVINCIBLE_POWER_SPEED;
    private final double INVINCIBLE_POWER_RADIUS;
    private final int INVINCIBLE_MAX_FRAMES;
    private int remainingFrames;
    private int speedY = Y_STATIONARY;
    private boolean hasCollided = false;
    private boolean isVisible = false;


    /**
     * Constructor for invincible power
     * @param x is the x coordinate of the invincible power
     * @param y is the y coordinate of the invincible power
     */
    public InvinciblePower(int x, int y) {
        super(x, y);

        // Initialize invincible power values from properties file
        INVINCIBLE_POWER_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.invinciblePower.image"));
        INVINCIBLE_POWER_SPEED = Integer.parseInt(
                                ShadowMario.game_props.getProperty("gameObjects.invinciblePower.speed"));
        INVINCIBLE_POWER_RADIUS = Double.parseDouble(
                                ShadowMario.game_props.getProperty("gameObjects.invinciblePower.radius"));
        INVINCIBLE_MAX_FRAMES = Integer.parseInt(
                                ShadowMario.game_props.getProperty("gameObjects.invinciblePower.maxFrames"));

        setImage(INVINCIBLE_POWER_IMAGE);
        setSpeedX(INVINCIBLE_POWER_SPEED);
    }

    /**
     * Handles player collision with invincible power
     * @param player collides with collidable object
     * @param collidable object that player collides with
     */
    @Override
    public void handlePlayerCollision(Player player, Collidable collidable) {
        if (CollisionDetector.detectCollision(player, collidable)) {
            hasCollided = true;
            isVisible = true;
            collect(player);
        }
    }

    /**
     * Handle collision between target and invincible power
     * Left implementation blank as the invincible power class doesn't use this
     * @param target collides with collidable object
     * @param collidable object that target collides with
     */
    @Override
    public void handleCollision(Targetable target, Collidable collidable) { }

    /**
     * Activates invincible power's ability on player
     * @param player Player object
     */
    @Override
    public void collect(Player player) {
        player.setInvinciblePowerActivated(true);
        remainingFrames = INVINCIBLE_MAX_FRAMES;

    }

    /**
     * Deactivates invincible power's ability when it reaches the max frame
     * @param player Player object
     */
    @Override
    public void deactivate(Player player) {
        if (player.isInvinciblePowerActivated() && remainingFrames > zeroFrames) {
            remainingFrames--;
            if (remainingFrames == zeroFrames) {
                player.setInvinciblePowerActivated(false);
            }
        }
    }

    /**
     * Performs a state update for invincible power
     * @param input is the input keys from user
     */
    @Override
    public void update(Input input) {
        draw();
        move(input);
        disappears();
    }

    /**
     * Resets invincible power's properties
     */
    @Override
    public void reset() {
        setX(getInitialX());
        setY(getInitialY());
        speedY = Y_STATIONARY;
        hasCollided = false;
        isVisible = false;
        remainingFrames = zeroFrames;
    }

    /**
     * Getter for the radius of invincible power
     * @return invincible power radius
     */
    @Override
    public double getRadius() {
        return INVINCIBLE_POWER_RADIUS;
    }

    /**
     * Getter for getHasCollided boolean
     * @return getHasCollided variable
     */
    @Override
    public boolean getHasCollided() {return hasCollided;}

    /**
     * Setter for hasCollided boolean
     * @param hasCollided boolean to change hasCollided value to
     */
    @Override
    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }

    /**
     * Getter for IsVisible boolean variable
     * @return IsVisible boolean
     */
    @Override
    public boolean getIsVisible() {return isVisible;}

    /**
     * Setter for IsVisible boolean variable
     * @param isVisible boolean to set isVisible to
     */
    @Override
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Getter for speedY
     * @return speedY of invincible power
     */
    @Override
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Setter for speedY
     * @param speedY value to set the invincible power's speedY
     */
    @Override
    public void setSpeedY(int speedY) {this.speedY = speedY;}

}
