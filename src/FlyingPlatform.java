import bagel.Image;
import bagel.Input;
import bagel.Keys;

/**
 * Flying Platform class
 */
public class FlyingPlatform extends MovableEntity implements RandomMovable {
    private final Image FLYING_PLATFORM_IMAGE;
    private final int FLYING_PLATFORM_SPEED;
    private final int FLYING_PLATFORM_MAX_X_DISPLACEMENT;
    private final int FLYING_PLATFORM_HALF_HEIGHT;
    private final int FLYING_PLATFORM_HALF_LENGTH;
    private final int FLYING_PLATFORM_RANDOM_SPEEDX;
    private int startingX;
    private int direction;

    /**
     * Flying Platform constructor
     * @param x is the x coordinate of the object
     * @param y is the y coordinate of the object
     */
    public FlyingPlatform(int x, int y) {
        super(x, y);

        // Initialize flying platform values from the properties file
        FLYING_PLATFORM_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.image"));
        FLYING_PLATFORM_SPEED = Integer.parseInt(
                                ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.speed"));
        FLYING_PLATFORM_RANDOM_SPEEDX = Integer.parseInt(
                                        ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        FLYING_PLATFORM_HALF_HEIGHT = Integer.parseInt(
                                        ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.halfHeight"));
        FLYING_PLATFORM_HALF_LENGTH = Integer.parseInt(
                                        ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.halfLength"));
        FLYING_PLATFORM_MAX_X_DISPLACEMENT = Integer.parseInt(
                            ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));

        setImage(FLYING_PLATFORM_IMAGE);
        setSpeedX(FLYING_PLATFORM_SPEED);

        this.direction = randomDirection();
        startingX = getInitialX();
    }

    /**
     * Checks if the player is on the flying platform
     * @param player Player object
     */
    public void onFlyingPlatform(Player player) {
        int xDistanceDifference = Math.abs(getX() - player.getX());
        int yDistanceDifference = getY() - player.getY();

        if ((xDistanceDifference < FLYING_PLATFORM_HALF_LENGTH) && (yDistanceDifference <= FLYING_PLATFORM_HALF_HEIGHT)
                && (yDistanceDifference >= (FLYING_PLATFORM_HALF_HEIGHT - 1))) {
            player.setSpeedY(0);
            player.setOnFlyingPlatform(true);
            player.setJumping(false);
        }
    }

    /**
     * Performs a state update for flying platform
     * @param input is the input keys from the user
     */
    @Override
    public void update(Input input) {
        draw();
        move(input);
        if (input.isDown(Keys.RIGHT)) {
            startingX -= FLYING_PLATFORM_SPEED;
        } else if (input.isDown(Keys.LEFT)) {
            startingX += FLYING_PLATFORM_SPEED;
        }
        moveRandomly();
    }

    /**
     * Resets the flying platform's properties
     */
    @Override
    public void reset() {
        setX(getInitialX());
        setY(getInitialY());
        startingX = getInitialX();
    }

    /**
     * Getter for flying platform's max displacement
     * @return flying platform's max displacement
     */
    @Override
    public int getMaxDisplacement() {
        return FLYING_PLATFORM_MAX_X_DISPLACEMENT;
    }

    /**
     * Getter for flying platform's random speed
     * @return flying platform's random speed
     */
    @Override
    public int getRandomSpeedX() {
        return FLYING_PLATFORM_RANDOM_SPEEDX;
    }

    /**
     * Getter for flying platform's starting x coordinate
     * @return flying platform's starting x coordinate
     */
    @Override
    public int getStartingX() {
        return startingX;
    }

    /**
     * Getter for flying platform's direction
     * @return flying platform's direction (1 = right, -1 = left)
     */
    @Override
    public int getDirection() {
        return direction;
    }

    /**
     * Setter for flying platform's direction
     * @param direction is the direction the object is set to
     */
    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }
}

