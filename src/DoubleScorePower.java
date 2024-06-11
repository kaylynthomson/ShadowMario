import bagel.Image;
import bagel.Input;

/**
 * Double Score Power class
 */
public class DoubleScorePower extends MovableEntity implements Collidable, Collectable, Power {
    private static final int Y_STATIONARY = 0;
    private static final int zeroFrames = 0;
    private final Image DOUBLE_SCORE_IMAGE;
    private final int DOUBLE_SCORE_SPEED;
    private final double DOUBLE_SCORE_RADIUS;
    private final int DOUBLE_SCORE_MAX_FRAMES;
    private int remainingFrames;
    private int speedY = Y_STATIONARY;
    private boolean hasCollided = false;
    private boolean isVisible = false;

    /**
     * Double Score Power constructor
     * @param x is the x coordinate of the double score power
     * @param y is the y coordinate of the double score power
     */
    public DoubleScorePower(int x, int y) {
        super(x, y);

        // Initialize double score values from properties file
        DOUBLE_SCORE_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.doubleScore.image"));
        DOUBLE_SCORE_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.doubleScore.speed"));
        DOUBLE_SCORE_RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.doubleScore.radius"));
        DOUBLE_SCORE_MAX_FRAMES = Integer.parseInt(ShadowMario.game_props.getProperty
                                    ("gameObjects.doubleScore.maxFrames"));

        setImage(DOUBLE_SCORE_IMAGE);
        setSpeedX(DOUBLE_SCORE_SPEED);
    }

    /**
     * Handles player collision with double score power
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
     * Handle collision between target and double score power
     * Left implementation blank as the double score power class doesn't use this
     * @param target collides with collidable object
     * @param collidable object that target collides with
     */
    @Override
    public void handleCollision(Targetable target, Collidable collidable) { }

    /**
     * Activates double score power's ability on player
     * @param player Player object
     */
    @Override
    public void collect(Player player) {
        player.setDoubleScoreActivated(true);
        remainingFrames = DOUBLE_SCORE_MAX_FRAMES;
    }

    /**
     * Deactivates double score power's ability when it reaches the max frame
     * @param player Player object
     */
    @Override
    public void deactivate(Player player) {
        if (player.isDoubleScoreActivated() && remainingFrames > zeroFrames) {
            remainingFrames--;
            if (remainingFrames == zeroFrames) {
                player.setDoubleScoreActivated(false);
            }
        }
    }

    /**
     * Performs a state update for double score power
     */
    @Override
    public void update(Input input) {
        draw();
        move(input);
        disappears();
    }

    /**
     * Resets double score power properties
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
     * Getter for the radius of double score
     * @return double score radius
     */
    @Override
    public double getRadius() {
        return DOUBLE_SCORE_RADIUS;
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
     * @return speedY of double score power
     */
    @Override
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Setter for speedY
     * @param speedY value to set the double score power's speedY
     */
    @Override
    public void setSpeedY(int speedY) {this.speedY = speedY;}

}
