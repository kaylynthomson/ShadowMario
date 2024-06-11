import bagel.Image;
import bagel.Input;

/**
 * Coin Class
 */
public class Coin extends MovableEntity implements Collidable, Collectable {
    private static final int Y_STATIONARY = 0;
    private static final int DOUBLE_SCORE_MULTIPLIER = 2;
    private final int COIN_VALUE;
    private final Image COIN_IMAGE;
    private final int COIN_SPEED;
    private final double COIN_RADIUS;
    private int speedY = Y_STATIONARY;
    private boolean hasCollided = false;
    private boolean isVisible = false;

    /**
     * Coin constructor
     * @param x is the x coordinate of the object
     * @param y is the y coordinate of the object
     */
    public Coin(int x, int y) {

        super(x,y);

        // Initialize coin values from properties file
        COIN_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.coin.image"));
        COIN_RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.coin.radius"));
        COIN_VALUE = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.coin.value"));
        COIN_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.coin.speed"));

        setImage(COIN_IMAGE);
        setSpeedX(COIN_SPEED);
    }

    /**
     * Increases player's score
     * @param player Player object
     */
    @Override
    public void collect(Player player) {
        int score = player.getScore();
        if (player.isDoubleScoreActivated()) {
            score += (COIN_VALUE * DOUBLE_SCORE_MULTIPLIER);
        } else {
            score += COIN_VALUE;
        }
        player.setScore(score);
    }

    /**
     * Handle collision with target and coin
     * Left implementation blank as the coin class doesn't use this
     * @param target collides with collidable object
     * @param collidable object that target collides with
     */
    @Override
    public void handleCollision(Targetable target, Collidable collidable) { }

    /**
     * Handles coin collision with player
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
     * Performs a state update for collectable objects
     */
    @Override
    public void update(Input input) {
        draw();
        move(input);
        disappears();
    }

    /**
     * Resets collectable's properties
     */
    @Override
    public void reset() {
        setX(getInitialX());
        setY(getInitialY());
        speedY = Y_STATIONARY;
        hasCollided = false;
        isVisible = false;
    }

    /**
     * Getter for isVisible boolean
     * @return isVisible boolean
     */
    @Override
    public boolean getIsVisible() {return isVisible;}

    /**
     * Setter for isVisible boolean
     * @param isVisible boolean to set isVisible to
     */
    @Override
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Getter for coin's speedY
     * @return speedY of coin
     */
    @Override
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Setter for coin's speedY
     * @param speedY value to set the object's speedY
     */
    @Override
    public void setSpeedY(int speedY) {this.speedY = speedY;}

    /**
     * Getter for coin radius
     * @return coin radius
     */
    @Override
    public double getRadius() {
        return COIN_RADIUS;
    }

    /**
     * Getter for hasCollided boolean
     * @return hasCollided boolean
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
}
