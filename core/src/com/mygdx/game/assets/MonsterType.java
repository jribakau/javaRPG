package com.mygdx.game.assets;

import lombok.Getter;

/**
 * MonsterType - Enum for all monster sprites from monsters.png
 */
@Getter
public enum MonsterType {
    // Row 1 - Orcs and Goblins
    ORC(0, 0),
    ORC_WIZARD(1, 0),
    GOBLIN(2, 0),

    // Row 2 - Giants
    ETTIN(0, 1),
    TWO_HEADED_ETTIN(1, 1),
    GREEN_GUY(2, 1),

    // Row 3 - Slimes
    SMALL_SLIME(0, 2),
    BIG_SLIME(1, 2),
    SLIME_BODY(2, 2),

    // Row 4 - Evil Clerics
    FACELESS_MONK(0, 3),
    UNHOLY_CARDINAL(1, 3),

    // Row 5 - Undead
    SKELETON(0, 4),
    SKELETON_ARCHER(1, 4),
    LICH(2, 4),
    DEATH_KNIGHT(3, 4),
    ZOMBIE(4, 4),
    GHOUL(5, 4),

    // Row 6 - Spirits
    BANSHEE(0, 5),
    REAPER(1, 5),
    WRAITH(2, 5),

    // Row 7 - Beasts
    GIANT_CENTIPEDE(0, 6),
    LAMPREYMANDER(1, 6),
    GIANT_EARTHWORM(2, 6),
    MANTICORE(3, 6),
    GIANT_ANT(4, 6),
    LYCANTHROPE(5, 6),
    GIANT_BAT(6, 6),

    // Row 8 - Mythical Creatures
    DRYAD(0, 7),
    WENDIGO(1, 7),
    ROCK_GOLEM(2, 7),
    CENTAUR(3, 7),
    NAGA(4, 7);

    private final int x;
    private final int y;

    MonsterType(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
