/**
 * Interface representing a Damager object that can damage a target or player
 */
public interface Damager {
    /**
     * Minimum health of damager
     */
    int MIN_HEALTH = 0;

    /**
     * Inflicts damage to the targetable object
     * @param target Targetable object that will be damaged
     */
    void inflictDamage(Targetable target);

    /**
     * Inflicts damage to the player
     * @param player Player object that will be damaged
     */
    void inflictDamageToPlayer(Player player);
}
