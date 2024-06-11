import bagel.*;

/**
 * Enemy Class
 */
public class Enemy extends MovableEntity implements Collidable, RandomMovable, Damager {
    private final double ENEMY_DAMAGE;
    private final int ENEMY_SPEED;
    private final Image ENEMY_IMAGE;
    private final double ENEMY_RADIUS;
    private final int ENEMY_MAX_X_DISPLACEMENT;
    private final int ENEMY_RANDOM_SPEEDX;
    private int direction;
    private int startingX;
    private boolean hasCollided = false;

    /**
     * Enemy constructor
     * @param x is the x coordinate of the enemy
     * @param y is the y coordinate of the enemy
     */
    public Enemy(int x, int y) {
        super(x,y);

        // Initialize enemy values from properties file
        ENEMY_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.enemy.image"));
        ENEMY_RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemy.radius"));
        ENEMY_DAMAGE = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemy.damageSize"));
        ENEMY_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemy.speed"));
        ENEMY_MAX_X_DISPLACEMENT = Integer.parseInt(ShadowMario.game_props.
                                    getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        ENEMY_RANDOM_SPEEDX = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemy.randomSpeed"));

        setImage(ENEMY_IMAGE);
        setSpeedX(ENEMY_SPEED);

        // initialize random direction enemy is facing
        direction = randomDirection();
        startingX = getInitialX();
    }

    /**
     * Handle collision between target and enemy
     * Left implementation blank as the enemy class doesn't use this
     * @param target collides with collidable object
     * @param collidable object that target collides with
     */
    @Override
    public void handleCollision(Targetable target, Collidable collidable) { }

    /**
     * Handle player collision with enemy
     * @param player collides with collidable object
     * @param collidable object that player collides with
     */
    @Override
    public void handlePlayerCollision(Player player, Collidable collidable) {
        if (CollisionDetector.detectCollision(player, collidable)) {
            hasCollided = true;
            inflictDamageToPlayer(player);
        }
    }

    /**
     * Inflict damage to target
     * Left implementation blank as the enemy class doesn't use this
     * @param target collides with enemy object
     */
    @Override
    public void inflictDamage(Targetable target) { }

    /**
     * Inflict damage to player
     * @param player Player object that will be damaged
     */
    @Override
    public void inflictDamageToPlayer(Player player) {
        double health = player.getHealth();

        if (player.isInvinciblePowerActivated()) {
            hasCollided = false;
        } else {
            health -= ENEMY_DAMAGE;
            player.setHealth(health);
        }

        // Ensure health doesn't go negative
        if (health <= MIN_HEALTH) {
            health = MIN_HEALTH;
            player.setHealth(health);
        }
    }

    /**
     * Performs a state update for enemy
     * @param input is the input keys from the user
     */
    @Override
    public void update(Input input) {
        draw();
        move(input);
        if (input.isDown(Keys.RIGHT)) {
            startingX -= ENEMY_SPEED;
        } else if (input.isDown(Keys.LEFT)) {
            startingX += ENEMY_SPEED;
        }
        moveRandomly();
    }

    /**
     * Resets enemy's properties
     */
    @Override
    public void reset() {
        setX(getInitialX());
        setY(getInitialY());
        startingX = getInitialX();
        hasCollided = false;
    }

    /**
     * Getter for enemy radius
     * @return enemy radius
     */
    @Override
    public double getRadius() {
        return ENEMY_RADIUS;
    }

    /**
     * Getter for hasCollided boolean
     * @return hasCollided boolean
     */
    @Override
    public boolean getHasCollided() {return hasCollided;}

    /**
     * Setter for hasCollided boolean
     * @param hasCollided boolean to change hasCollided value to
     */
    @Override
    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }

    /**
     * Getter for starting x coordinate
     * @return starting x coordinate
     */
    @Override
    public int getStartingX() {
        return startingX;
    }

    /**
     * Getter for enemy's random speed
     * @return enemy's random speed
     */
    @Override
    public int getRandomSpeedX() {
        return ENEMY_RANDOM_SPEEDX;
    }

    /**
     * Getter for enemy's max displacement
     * @return enemy's max displacement
     */
    @Override
    public int getMaxDisplacement() {
        return ENEMY_MAX_X_DISPLACEMENT;
    }

    /**
     * Getter for direction of enemy movement
     * @return direction of enemy movement
     */
    @Override
    public int getDirection() {
        return direction;
    }

    /**
     * Setter for direction of enemy movement
     * @param direction is the direction the object is set to
     */
    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }
}
