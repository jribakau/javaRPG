package com.mygdx.game.assets;

import lombok.Getter;

/**
 * CharacterType - Enum for all character sprites from rogues.png
 */
@Getter
public enum CharacterType {
    // Row 1
    DWARF(0, 0),
    ELF(1, 0),
    RANGER(2, 0),
    ROGUE(3, 0),
    BANDIT(4, 0),

    // Row 2
    MALE_KNIGHT(0, 1),
    MALE_FIGHTER(1, 1),
    FEMALE_KNIGHT(2, 1),
    FEMALE_KNIGHT_HELMETLESS(3, 1),
    SHIELD_KNIGHT(4, 1),

    // Row 3
    MONK(0, 2),
    PRIEST(1, 2),
    FEMALE_WAR_CLERIC(2, 2),
    MALE_WAR_CLERIC(3, 2),
    TEMPLAR(4, 2),

    // Row 4
    MALE_BARBARIAN(0, 3),
    MALE_WINTER_BARBARIAN(1, 3),
    FEMALE_WINTER_BARBARIAN(2, 3),
    SWORDSMAN(3, 3),

    // Row 5
    FEMALE_WIZARD(0, 4),
    MALE_WIZARD(1, 4),
    DRUID(2, 4),
    DESERT_SAGE(3, 4);

    private final int x;
    private final int y;

    CharacterType(int x, int y) {
        this.x = x;
        this.y = y;
    }
}