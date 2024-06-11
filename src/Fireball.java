import bagel.Image;
import bagel.Input;

/**
 * Fireball class
 */
public class Fireball extends MovableEntity implements Damager, Collidable {
    private Image FIREBALL_IMAGE;
    private double FIREBALL_RADIUS;
    private int FIREBALL_PROJECTILE_SPEED;
    private double FIREBALL_DAMAGE;
    private int FIREBALL_SPEED;
    private boolean isActive;
    private boolean hasCollided;
    private int targetX;
    private Targetable target;

    /**
     * Fireball constructor
     * @param x is the x coordinate of fireball
     * @param y is the y coordinate of fireball
     */
    public Fireball(int x, int y) {
        super(x,y);
    }

    /**
     * Fireball constructor
     * @param x is the x coordinate of fireball
     * @param y is the y coordinate of fireball
     * @param target is the Targetable object of the fireball
     */
    public Fireball(int x, int y, Targetable target) {
        super(x,y);

        // Initialize fireball values from the properties file
        FIREBALL_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.fireball.image"));
        FIREBALL_RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.fireball.radius"));
        FIREBALL_PROJECTILE_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.fireball.speed"));
        FIREBALL_DAMAGE = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.fireball.damageSize"));

        // assumes all movable entities has the same speed
        FIREBALL_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemy.speed"));

        setImage(FIREBALL_IMAGE);
        setSpeedX(FIREBALL_SPEED);

        this.target = target;
        targetX = target.getX();
        isActive = true;
    }

    // Moves the fireball towards the target
    private void moveProjectile() {
        if (getInitialX() < targetX) {
            setX(getX() + FIREBALL_PROJECTILE_SPEED);
        } else if (getInitialX() > targetX) {
            setX(getX() - FIREBALL_PROJECTILE_SPEED);
        }
    }

    /**
     * Inflicts damage to target
     * @param target Targetable object that will be damaged
     */
    @Override
    public void inflictDamage(Targetable target) {
        double health = target.getHealth();
        // no damage if target is a player and has invincible power activated
        if (target instanceof Player && ((Player) target).isInvinciblePowerActivated()) {
            target.setHealth(health);
        } else {
            health -= FIREBALL_DAMAGE;
            target.setHealth(health);
        }

        // Ensure health doesn't go negative
        if (health <= MIN_HEALTH) {
            health = MIN_HEALTH;
            target.setHealth(health);
        }
    }

    /**
     * Inflicts damage to player
     * Leave implementation blank as the fireball class doesn't use this
     * @param player Player object that will be damaged
     */
    @Override
    public void inflictDamageToPlayer(Player player) { }

    /**
     * Handles collision with player
     * Leave implementation blank as the fireball class doesn't use this
     * @param player collides with collidable object
     * @param collidable object that player collides with
     */
    @Override
    public void handlePlayerCollision(Player player, Collidable collidable) { }

    /**
     * Handles collision between target and fireball
     * @param target collides with fireball
     * @param collidable fireball that target collides with
     */
    @Override
    public void handleCollision(Targetable target, Collidable collidable) {
        // inflicts damage if fireball collides with target
        if (CollisionDetector.detectCollision(target, collidable)) {
            hasCollided = true;
            isActive = false;
            inflictDamage(target);

         // if the fireball has reached the left or right boundary of the window
        } else if (getX() < 0 || getX() > ShadowMario.windowWidth) {
            isActive = false;
            hasCollided = false;
        }
    }

    /**
     * Performs a state update for fireball object
     * @param input is the input keys from the user
     */
    @Override
    public void update(Input input) {
        draw();
        move(input);
        moveProjectile();
    }

    /**
     * Getter method for target
     * @return target of fireball
     */
    public Targetable getTarget() {
        return target;
    }

    /**
     * Getter for isActive boolean variable
     * @return isActive boolean variable
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Getter for fireball radius
     * @return radius of fireball object
     */
    @Override
    public double getRadius() {
        return FIREBALL_RADIUS;
    }

    /**
     * Getter for getHasCollided boolean variable
     * @return getHasCollided boolean variable
     */
    @Override
    public boolean getHasCollided() {
        return hasCollided;
    }

    /**
     * Setter for hasCollided boolean
     * @param hasCollided boolean to change hasCollided value to
     */
    @Override
    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }
}
