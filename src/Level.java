import bagel.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract parent class for Levels
 */
public abstract class Level {
    private final String levelFile;
    private final List<GameEntity> entities = new ArrayList<>();
    private final List<Collidable> collidables = new ArrayList<>();
    private Player player;
    private int endFlagIndex;
    private boolean startedLevel = false;
    private boolean finishedLevel = false;
    private boolean gameLost = false;
    private boolean gameWon = false;

    /**
     * Level constructor
     * @param levelFile String containing the level csv
     */
    public Level(String levelFile) {
        this.levelFile = levelFile;
        createEntities();
    }

    // Create game objects from the ArrayList returned by the IOUtils.readCsv helper method
    public void createEntities() {
        List<String[]> csvData = IOUtils.readCsv(levelFile);

        for (String[] row : csvData) {
            String entityType = row[0];
            int x = Integer.parseInt(row[1]);
            int y = Integer.parseInt(row[2]);

            switch(entityType) {
                case "PLATFORM":
                    Platform platform = new Platform(x, y);
                    this.entities.add(platform);
                    break;
                case "ENEMY":
                    Enemy enemy = new Enemy(x, y);
                    this.entities.add(enemy);
                    this.collidables.add(enemy);
                    break;
                case "COIN":
                    Coin coin = new Coin(x, y);
                    this.entities.add(coin);
                    this.collidables.add(coin);
                    break;
                case "PLAYER":
                    this.player = new Player(x, y);
                    this.entities.add(player);
                    break;
                case "END_FLAG":
                    EndFlag endFlag = new EndFlag(x, y);
                    this.entities.add(endFlag);
                    this.collidables.add(endFlag);
                    endFlagIndex = collidables.indexOf(endFlag);
                    break;
            }
        }
    }

    // Updates the state of all game entities
    protected void updateEntities(Input input) {
        for (GameEntity entity : entities) {
            entity.update(input);
        }
    }

    // Checks collision conditions between player and collidable object
    protected void handlePlayerCollisions() {
        for (Collidable collidable : collidables) {
            if (!collidable.getHasCollided()) {
                collidable.handlePlayerCollision(player, collidable);
            }
        }
    }

    // Lose level condition
    protected void loseLevel() {
        if (!player.isAlive()) {
            if (player.getY() > Window.getHeight()) {
                finishedLevel = true;
                gameLost = true;
            }
        }
    }

    // Win level condition
    protected void winLevel() {
        if (collidables.get(endFlagIndex).getHasCollided()) {
            finishedLevel = true;
            gameWon = true;
        }
    }

    /**
     * Method that performs a reset for a level
     */
    public void resetGame () {
        startedLevel = false;
        finishedLevel = false;
        gameWon = false;
        gameLost = false;
        for (GameEntity entity : entities) {
            entity.reset();
        }
    }

    /**
     * Performs a state update of the level
     * Loops through each array list to perform their respective conditions
     * Checks if the winning or losing condition is true
     * @param input Input keys
     */
    public void update (Input input) {
        // Updates game entities and check player collisions
        updateEntities(input);
        handlePlayerCollisions();

        // Checks winning and losing condition
        loseLevel();
        winLevel();
    }

    // Adds a game entity to the GameEntity array list
    protected void addEntity(GameEntity entity) {
        entities.add(entity);
    }

    // Adds a collidable to the collidable array list
    protected void addCollidable(Collidable collidable) {
        collidables.add(collidable);
    }

    // Getter for the Collidable array list
    protected List<Collidable> getCollidables() {
        return collidables;
    }

    // Getter for the player object
    protected Player getPlayer() {
        return player;
    }

    // Getter for the End Flag index in the GameEntity array list
    protected int getEndFlagIndex() {return endFlagIndex;}

    /**
     * Getter for the started level
     * @return true if level has started, otherwise false
     */
    public boolean getStartedLevel() {
        return startedLevel;
    }

    /**
     * Setter for the started level
     * @param startedLevel boolean that indicates whether a level is started
     */
    public void setStartedLevel(boolean startedLevel) {
        this.startedLevel = startedLevel;
    }

    /**
     * Getter for the finished level
     * @return true if level has finished, otherwise false
     */
    public boolean getFinishedLevel() {
        return finishedLevel;
    }

    // Setter for the finished level
    protected void setFinishedLevel(boolean finishedLevel) {
        this.finishedLevel = finishedLevel;
    }

    /**
     * Getter for game Lost
     * @return true if a level is lost, otherwise false
     */
    public boolean getGameLost() {
        return gameLost;
    }

    /**
     * Getter for game Won
     * @return true if a level is won, otherwise false
     */
    public boolean getGameWon() {
        return gameWon;
    }

    // Setter for game Won
    protected void setGameWon(boolean condition) {
        this.gameWon = condition;
    }

    public void removeEntity(GameEntity gameEntity) {
        this.entities.remove(gameEntity);
    }

}
