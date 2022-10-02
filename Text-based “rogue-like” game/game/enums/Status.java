package game.enums;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 *
 * @author Ng Kai Yi
 * @author Jerrold Wong Youn Zhuet
 * @version 2
 */

public enum Status {
    HOSTILE_TO_ENEMY,
    DISARMED,
    DEAD,
    HOSTILE_TO_PLAYER_ONLY,
    STUNNED,
    ENRAGED, // use this capability to be hostile towards something (e.g., to be attacked by enemy);
    NOT_ACTIVATED,
    CANT_TELEPORT,
}
