import bagel.*;

/**
 * GameEntity parent abstract class
 */
public abstract class GameEntity {
    private final int initialX;
    private final int initialY;
    private int x;
    private int y;
    private Image image;

    /**
     * GameEntity Constructor
     * @param x is the x coordinate of the entity
     * @param y is the y coordinate of the entity
     */
    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;

        // Initialized to save initial x and y coordinates for game reset
        initialX = x;
        initialY = y;
    }

    /**
     * Draws the image associated with the entity
     */
    protected void draw() {
        image.draw(x,y);
    }

    /**
     * Resets the entity to its initial x and y coordinate
     */
    public void reset() {
        x = initialX;
        y = initialY;
    }

    /**
     * Updates the entity
     * @param input is the Input keys
     */
    public void update(Input input) {
        draw();
    }

    /**
     * Sets the image of the entity
     * @param image is the image of the image
     */
    protected void setImage (Image image) {
        this.image = image;
    }

    /**
     * Gets the x coordinate of the entity
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for the x coordinate of the entity
     * @param x is the new x coordinate of the entity
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y coordinate of the entity
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for the y coordinate of the entity
     * @param y is the new y coordinate of the entity
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter for the initial x of the entity
     * @return initial x coordinate
     */
    protected int getInitialX() {
        return initialX;
    }

    /**
     * Getter for the initial y of the entity
     * @return initial y coordinate
     */
    protected int getInitialY() {
        return initialY;
    }

}
