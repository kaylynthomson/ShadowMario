import bagel.*;

/**
 * EndFlag class
 */
public class EndFlag extends MovableEntity implements Collidable {
    private final Image END_FLAG_IMAGE;
    private final double END_FLAG_RADIUS;
    private final int END_FLAG_SPEED;
    private boolean hasCollided = false;

    /**
     * End Flag constructor
     * @param x is the x coordinate of the EndFlag object
     * @param y is the y coordinate of the EndFlag object
     */
    public EndFlag(int x, int y) {
        super(x,y);

        // Initialize end flag values from properties file
        END_FLAG_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.endFlag.image"));
        END_FLAG_RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.endFlag.radius"));
        END_FLAG_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.endFlag.speed"));

        setImage(END_FLAG_IMAGE);
        setSpeedX(END_FLAG_SPEED);

    }

    /**
     * Handle collision between player and endflag
     * @param player collides with EndFlag object
     * @param collidable EndFlag object that player collides with
     */
    @Override
    public void handlePlayerCollision(Player player, Collidable collidable) {
        if (CollisionDetector.detectCollision(player, collidable)) {
            hasCollided = true;
        }
    }

    /**
     * Handle collision between target and endflag
     * Left implementation blank as the endflag class doesn't use this
     * @param target collides with collidable object
     * @param collidable object that target collides with
     */
    @Override
    public void handleCollision(Targetable target, Collidable collidable) { }

    /**
     * Resets the endflag properties
     */
    @Override
    public void reset(){
        setX(getInitialX());
        setY(getInitialY());
        hasCollided = false;
    }

    /**
     * Getter for the end flag radius
     * @return end flag radius
     */
    @Override
    public double getRadius() {
        return END_FLAG_RADIUS;
    }

    /**
     * Getter for getHasCollided boolean
     * @return getHasCollided variable
     */
    @Override
    public boolean getHasCollided() {
        return hasCollided;
    }

    /**
     * Setter for hasCollided boolean
     * @param hasCollided boolean to change hasCollided value to
     */
    @Override
    public void setHasCollided(boolean hasCollided) {this.hasCollided = hasCollided;}


}
