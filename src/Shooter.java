/**
 * Interface to represent a Shooter
 */
public interface Shooter {
    /**
     * Checks condition of shooting a fireball to a target
     * @param target is the Targetable object of the fireball
     */
    void shootFireball(Targetable target);

    /**
     * Getter for getShotFireball boolean variable
     * @return getShotFireball boolean variable
     */
    boolean getShotFireball();

    /**
     * Gets the target of the shooter
     * @return target
     */
    Targetable getTarget();

    /**
     * Gets the x coordinate of the entity
     * @return x coordinate
     */
    int getX();

    /**
     * Gets the y coordinate of the entity
     * @return y coordinate
     */
    int getY();
}
