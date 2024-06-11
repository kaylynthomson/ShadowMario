import bagel.Window;

/**
 * Interface representing a Collectable
 */
public interface Collectable {
    /**
     * Disappear speed of collectable
     */
    int DISAPPEAR_SPEED = 10;

    /**
     * Implements player ability from collecting collectable
     * @param player Player object
     */
    void collect(Player player);

    /**
     * Implements disappearing motion when a player collides with collectable
     */
    default void disappears() {
        if (getIsVisible()) {
            setY(getY() + getSpeedY());

            setSpeedY(-DISAPPEAR_SPEED);

            // Check if the collectable has moved off the screen
            if (getY() > Window.getHeight()) {
                setIsVisible(false);
            }
        }
    }

    /**
     * Getter for IsVisible boolean variable
     * @return IsVisible boolean
     */
    boolean getIsVisible();

    /**
     * Setter for IsVisible boolean variable
     * @param isVisible boolean to set isVisible to
     */
    void setIsVisible(boolean isVisible);

    /**
     * Getter for speedY
     * @return speedY of the object
     */
    int getSpeedY();

    /**
     * Setter for speedY
     * @param speedY value to set the object's speedY
     */
    void setSpeedY(int speedY);

    /**
     * Getter for y coordinate
     * @return y coordinate of the object
     */
    int getY();

    /**
     * Setter for y coordinate
     * @param y coordinate to set the object's y coordinate
     */
    void setY(int y);

}
