/**
 * Interface representing a Collidable object that can be collided with
 */
public interface Collidable {
    /**
     *  Method that handles collisions between a player and collidable
     * @param player collides with collidable object
     * @param collidable object that player collides with
     */
    void handlePlayerCollision(Player player, Collidable collidable);

    /**
     * Method that handles collision between a target and collidable
     * @param target collides with collidable object
     * @param collidable object that target collides with
     */
    void handleCollision(Targetable target, Collidable collidable);

    /**
     * Getter for getHasCollided boolean
     * @return getHasCollided variable
     */
    boolean getHasCollided();

    /**
     * Setter for hasCollided boolean
     * @param hasCollided boolean to change hasCollided value to
     */
    void setHasCollided(boolean hasCollided);

    /**
     * Getter for radius
     * @return radius of collidable object
     */
    double getRadius();

    /**
     * Getter for x coordinate
     * @return x coordinate of the collidable object
     */
    int getX();

    /**
     * Getter for y coordinate
     * @return y coordinate of the collidable object
     */
    int getY();

}
