import bagel.*;

/**
 * Platform class
 */
public class Platform extends MovableEntity {
    private static final int MAX_X_COORDINATE = 3000;
    private final Image PLATFORM_IMAGE;
    private final int PLATFORM_SPEED;

    /**
     * Platform Constructor
     * @param x is the x coordinate of the platform
     * @param y is the y coordinate of the platform
     */
    public Platform(int x, int y) {
        super(x, y);

        // Initialize platform values from properties file
        PLATFORM_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.platform.image"));
        PLATFORM_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.platform.speed"));

        setImage(PLATFORM_IMAGE);
        setSpeedX(PLATFORM_SPEED);
    }

    /**
     * Implement platform's left and right movement
     * @param input is the input keys from the user
     */
    @Override
    protected void move(Input input) {
        if (input.isDown(Keys.RIGHT)) {
            setX(getX() - PLATFORM_SPEED);
        } else if (input.isDown(Keys.LEFT) && (getX() < MAX_X_COORDINATE)) {
            setX(getX() + PLATFORM_SPEED);
        }
    }
}
