/**
 * Interface for Targetable object
 */
public interface Targetable {
    /**
     * Fall speed of the targetable object
     */
    int FALL_SPEED = 2;

    /**
     * Default method implementation for falling motion
     */
    default void falls() {
        setY(getY() + FALL_SPEED);
        setSpeedY(getSpeedY());
    }

    /**
     * Getter for x coordinate
     * @return x coordinate of object
     */
    int getX();

    /**
     * Getter for y coordinate
     * @return y coordinate of object
     */
    int getY();

    /**
     * Setter for y coordinate
     * @param y coordinate to set y to
     */
    void setY(int y);

    /**
     * Getter for speedY
     * @return speedY of the object
     */
    int getSpeedY();

    /**
     * Setter for speedY
     * @param speedY value to set the target's speedY
     */
    void setSpeedY(int speedY);

    /**
     * Getter for radius
     * @return radius of object
     */
    double getRadius();

    /**
     * Getter for health
     * @return health of the object
     */
    double getHealth();

    /**
     * Setter for health
     * @param health value to set the target's health
     */
    void setHealth(double health);
}
