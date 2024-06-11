import bagel.*;

/**
 * Movable Entity class that is a child of GameEntity
 */
public abstract class MovableEntity extends GameEntity{
    private int speedX;

    /**
     * Constructor for movable entity
     * @param x is the x coordinate of the entity
     * @param y is the y coordinate of the entity
     */
    public MovableEntity(int x, int y) {
        super(x,y);
    }

    /**
     * Method to move the entity
     * @param input is the input keys from the user
     */
    protected void move(Input input) {
        if (input.isDown(Keys.RIGHT)) {
            setX(getX() - speedX);
        } else if (input.isDown(Keys.LEFT)) {
            setX(getX() + speedX);
        }
    }

    /**
     * Update method of the entity
     * @param input is the input keys from the user
     */
    @Override
    public void update(Input input) {
        draw();
        move(input);
    }

    /**
     * Setter to set speed of x
     * @param speedX is the new speedX value
     */
    protected void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
}
