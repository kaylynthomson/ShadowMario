import java.util.Random;

/**
 * Interface representing a RandomMovable object that can move randomly
 */
public interface RandomMovable {

    /**
     * Default method to randomly choose a direction
     * @return 1 or -1 representing right or left direction
     */
    default int randomDirection() {
        int direction;
        Random random = new Random();
        if (random.nextBoolean()) {
            // Right direction
            direction = 1;
        } else {
            // Left direction
            direction = -1;
        }
        return direction;
    }

    /**
     * Default method to move a RandomMovable object in its random direction
     * If the object hits the max displacement, switch the direction
     */
    default void moveRandomly() {
        int displacementX = getX() - getStartingX();

        if (Math.abs(displacementX) >= getMaxDisplacement()) {
            setDirection(getDirection() * -1); // reverse direction
        }

        setX(getX() + (getDirection() * getRandomSpeedX()));
    }

    /**
     * Getter method that gets the x coordinate
     * @return x coordinate of the object
     */
    int getX();

    /**
     * Setter method for the x coordinate
     * @param x is the x coordinate the object is set to
     */
    void setX(int x);

    /**
     * Getter method that gets the starting x coordinate
     * @return starting x coordinate of the object
     */
    int getStartingX();

    /**
     * Getter method of the random speed
     * @return random speedx of the object
     */
    int getRandomSpeedX();

    /**
     * Getter method of the max displacement
     * @return max displacement of the object
     */
    int getMaxDisplacement();

    /**
     * Getter method for the direction
     * @return direction of the object
     */
    int getDirection();

    /**
     * Setter method for the direction
     * @param direction is the direction the object is set to
     */
    void setDirection(int direction);

}
