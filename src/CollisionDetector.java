/**
 * CollisionDetector class that checks collisions
 */
public class CollisionDetector {

    /**
     * Method to detect collision between a Targetable and Collidable entity
     * @param target a Targetable object that can be collided into
     * @param collidable a Collidable object that can collide with the target
     * @return true if a collision is detected, otherwise false
     */
    /*
    public static boolean detectCollision (Targetable target, Collidable collidable) {
        double distance = Math.sqrt(Math.pow(collidable.getX()-target.getX(), 2) +
                Math.pow(collidable.getY() - target.getY(), 2));
        double range = target.getRadius() + collidable.getRadius();
        return distance <= range;
    }

     */
    public static boolean detectCollision (Targetable target, Collidable collidable) {
        double distance = Math.sqrt(Math.pow(target.getX() - collidable.getX(), 2) +
                Math.pow(target.getY() - collidable.getY(), 2));
        double range = target.getRadius() + collidable.getRadius();
        return distance <= range;
    }
}