import bagel.*;

/**
 * Player Class
 */
public class Player extends GameEntity implements Targetable, Shooter {
    private static final int Y_STATIONARY = 0;
    private static final int JUMP_SIZE = 20;
    private static final int GRAVITY = 1;
    private static final int MIN_HEALTH = 0;
    private static final int INITIAL_SCORE = 0;
    private final Font SCORE_FONT;
    private final Font PLAYER_HEALTH_FONT;
    private final Image PLAYER_RIGHT_IMAGE;
    private final Image PLAYER_LEFT_IMAGE;
    private final double MAX_HEALTH;
    private final double PLAYER_RADIUS;
    private double health;
    private Targetable target;
    private int speedY = Y_STATIONARY;
    private boolean isJumping = false;
    private String lastDirection = "RIGHT";
    private int score = INITIAL_SCORE;
    private boolean onFlyingPlatform = false;
    private boolean doubleScoreActivated = false;
    private boolean invinciblePowerActivated = false;
    private boolean shotFireball = true;
    private boolean pressedSKey = false;

    /**
     * Player constructor
     * @param x is the x coordinate of the object
     * @param y is the y coordinate of the object
     */
    public Player(int x, int y) {
        super(x,y);

        // Initialize values from property files
        PLAYER_RIGHT_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.player.imageRight"));
        PLAYER_LEFT_IMAGE = new Image(ShadowMario.game_props.getProperty("gameObjects.player.imageLeft"));
        MAX_HEALTH = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.player.health"));
        health = MAX_HEALTH;
        PLAYER_RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.player.radius"));

        SCORE_FONT = new Font(ShadowMario.game_props.getProperty("font"),
                Integer.parseInt(ShadowMario.game_props.getProperty("score.fontSize")));
        PLAYER_HEALTH_FONT = new Font(ShadowMario.game_props.getProperty("font"),
                Integer.parseInt(ShadowMario.game_props.getProperty("playerHealth.fontSize")));
    }

    // Sets the last direction variable based on input
    private void setLastDirection(Input input) {
        if (input.isDown(Keys.LEFT)) {
            lastDirection = "LEFT";
        } else if (input.isDown(Keys.RIGHT)) {
            lastDirection = "RIGHT";
        }
    }

    // Player's jump motion
    private void jump(Input input) {
        if (!isJumping && input.wasPressed(Keys.UP) && (getY() == getInitialY() || onFlyingPlatform)) {
            isJumping = true;
            speedY = -JUMP_SIZE;
        }

        setY(getY() + speedY);
        speedY += GRAVITY;

        /*
         * Case when player has reached the platform and finished jumping
         * Assumes that player starts the game on the platform with initialY
         */
        if (getY() >= getInitialY() && speedY > 0) {
            setY(getInitialY());
            isJumping = false;
            speedY = Y_STATIONARY;
            onFlyingPlatform = false;
        }
    }

    /**
     * Shoot Fireball at target
     * @param target is targetable object
     */
    @Override
    public void shootFireball(Targetable target) {
        double distance = Math.abs(target.getX() - getX());
        int MAXIMUM_RANGE = 500;
        shotFireball = (distance < MAXIMUM_RANGE) && pressedSKey && isAlive();
    }

    // Draws the player's properties
    @Override
    protected void draw() {
        final int SCALING_FACTOR = 100;

        // draw player depending on the direction it's facing
        if (lastDirection.equals("LEFT")) {
            PLAYER_LEFT_IMAGE.draw(getX(), getY());
        } else {
            PLAYER_RIGHT_IMAGE.draw(getX(), getY());
        }

        // draw player properties (score, health)
        SCORE_FONT.drawString(ShadowMario.message_props.getProperty("score") + score,
                Integer.parseInt(ShadowMario.game_props.getProperty("score.x")),
                Integer.parseInt(ShadowMario.game_props.getProperty("score.y")));
        PLAYER_HEALTH_FONT.drawString(ShadowMario.message_props.getProperty("health") +
                        Math.round(health*SCALING_FACTOR),
                Integer.parseInt(ShadowMario.game_props.getProperty("playerHealth.x")),
                Integer.parseInt(ShadowMario.game_props.getProperty("playerHealth.y")));
    }

    /**
     * Performs a state update of the player
     * @param input is the Input keys
     */
    @Override
    public void update(Input input) {
        setLastDirection(input);
        draw();
        if (!isAlive()) {
            falls();
        } else {
            jump(input);
        }
        pressedSKey = input.wasPressed(Keys.S);
    }

    /**
     * Resets the player's properties
     */
    @Override
    public void reset() {
        setX(getInitialX());
        setY(getInitialY());
        health = MAX_HEALTH;
        score = INITIAL_SCORE;
        lastDirection = "RIGHT";
        isJumping = false;
        speedY = Y_STATIONARY;
        onFlyingPlatform = false;
        doubleScoreActivated = false;
        invinciblePowerActivated = false;
    }

    /**
     * Boolean to check player's alive status
     */
    public boolean isAlive() {
        return health > MIN_HEALTH;
    }

    /**
     * Getter for player's score
     * @return player's score
     */
    public int getScore() {return score;}

    /**
     * Setter for player's score
     * @param score value to set the player's score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Setter for isJumping variable
     * @param jumping boolean to set isJumping variable
     */
    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    /**
     * Setter for onFlyingPlatform variable
     * @param onFlyingPlatform boolean to set onFlyingPlatform variable
     */
    public void setOnFlyingPlatform(boolean onFlyingPlatform) {
        this.onFlyingPlatform = onFlyingPlatform;
    }

    /**
     * Getter for doubleScoreActivated variable
     * @return doubleScoreActivated boolean variable
     */
    public boolean isDoubleScoreActivated() {
        return doubleScoreActivated;
    }

    /**
     * Setter for doubleScoreActivated boolean variable
     * @param doubleScoreActivated boolean to set doubleScoreActivated variable
     */
    public void setDoubleScoreActivated(boolean doubleScoreActivated) {
        this.doubleScoreActivated = doubleScoreActivated;
    }

    /**
     * Getter for invinciblePowerActivated variable
     * @return invinciblePowerActivated boolean variable
     */
    public boolean isInvinciblePowerActivated() {
        return invinciblePowerActivated;
    }

    /**
     * Setter for invinciblePowerActivated boolean variable
     * @param invinciblePowerActivated boolean to set invinciblePowerActivated variable
     */
    public void setInvinciblePowerActivated(boolean invinciblePowerActivated) {
        this.invinciblePowerActivated = invinciblePowerActivated;
    }

    /**
     * Setter for player's target
     * @param target Targetable object to set target variable
     */
    public void setTarget(Targetable target) {
        this.target = target;
    }

    /**
     * Getter for player's target
     * @return player's target
     */
    @Override
    public Targetable getTarget() {
        return target;
    }

    /**
     * Getter for player's health
     * @return player's health
     */
    @Override
    public double getHealth() {
        return health;
    }

    /**
     * Setter for player's health
     * @param health value to set the player's health
     */
    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Getter for player radius
     * @return player radius
     */
    @Override
    public double getRadius() {
        return PLAYER_RADIUS;
    }

    /**
     * Setter for player's speedY
     * @param speedY value to set the player's speedY
     */
    @Override
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    /**
     * Getter for player's speedY
     * @return player's speedY
     */
    @Override
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Getter for shotFireball variable
     * @return shotFireball boolean variable
     */
    @Override
    public boolean getShotFireball() {
        return shotFireball;
    }

}
