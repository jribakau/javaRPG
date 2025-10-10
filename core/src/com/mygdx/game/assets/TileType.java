package com.mygdx.game.assets;

import lombok.Getter;

/**
 * TileType - Enum for all tile sprites from tiles.png
 */
@Getter
public enum TileType {
    // Row 1
    G_1(0, 0),
    G_1_WALL(1, 0),
    G_BLUE(2, 0),

    // Row 2
    ROCK_GROUND_1(0, 1),
    ROCK_WALL_1(1, 1),

    // Row 3
    BRICK_GROUND_1(0, 2),
    BRICK_WALL_1(1, 2),
    BRICK_ENTRANCE_1(2, 2),

    // Row 4
    ROCK_2_GROUND_1(0, 3),
    ROCK_2_WALL_1(1, 3),

    // Row 5
    RED_BRICK_GROUND(0, 4),
    RED_BRICK_WALL(1, 4),

    // Row 6
    SKULL_GROUND(0, 5),
    SKULL_WALL(1, 5),

    // Row 7
    BLACK_GROUND(0, 6),
    BLACK_GROUND_STONE_1(1, 6),
    BLACK_GROUND_STONE_2(2, 6),
    BLACK_GROUND_STONE_3(3, 6),
    GROUND_STONE_1(4, 6),
    GROUND_STONE_2(5, 6),
    GROUND_STONE_3(6, 6),

    // Row 8
    VIOLET_GROUND(0, 7),
    BLACK_GROUND_GRASS_1(1, 7),
    BLACK_GROUND_GRASS_2(2, 7),
    BLACK_GROUND_GRASS_3(3, 7),
    GROUND_GRASS_1(4, 7),
    GROUND_GRASS_2(5, 7),
    GROUND_GRASS_3(6, 7),

    // Row 9
    BLANK_9(0, 8),
    BLACK_GROUND_SPOT_1(1, 8),
    BLACK_GROUND_SPOT_2(2, 8),
    BLACK_GROUND_SPOT_3(3, 8),
    GROUND_SPOT_1(4, 8),
    GROUND_SPOT_2(5, 8),
    GROUND_SPOT_3(6, 8),

    // Row 10
    BLANK_10(0, 9),
    BLACK_GROUND_BLUE_BRICK_1(1, 9),
    BLACK_GROUND_BLUE_BRICK_2(2, 9),
    BLACK_GROUND_BLUE_BRICK_3(3, 9),
    GROUND_BLUE_BRICK_1(4, 9),
    GROUND_BLUE_BRICK_2(5, 9),
    GROUND_BLUE_BRICK_3(6, 9),

    // Row 11
    BLANK_11(0, 10),
    BLACK_GROUND_BONE_1(1, 10),
    BLACK_GROUND_BONE_2(2, 10),
    BLACK_GROUND_BONE_3(3, 10),
    GROUND_BONE_1(4, 10),
    GROUND_BONE_2(5, 10),
    GROUND_BONE_3(6, 10),

    // Row 12
    PINK(0, 11),
    PINK_GROUND_PINK_BRICK_1(1, 11),
    PINK_GROUND_PINK_BRICK_2(2, 11),
    PINK_GROUND_PINK_BRICK_3(3, 11),
    GROUND_PINK_BRICK_1(4, 11),
    GROUND_PINK_BRICK_2(5, 11),
    GROUND_PINK_BRICK_3(6, 11),

    // Row 13
    BLUE(0, 12),
    BLUE_GROUND_BLUE_BRICK_1(1, 12),
    BLUE_GROUND_BLUE_BRICK_2(2, 12),
    BLUE_GROUND_BLUE_BRICK_3(3, 12),

    // Row 14
    GREEN(0, 13),
    GREEN_GROUND_SPOT_1(1, 13),
    GREEN_GROUND_SPOT_2(2, 13),
    GREEN_GROUND_SPOT_3(3, 13),

    // Row 15
    BLANK_15(0, 14),
    GREEN_GROUND_GRASS_1(1, 14),
    GREEN_GROUND_GRASS_2(2, 14),
    GREEN_GROUND_GRASS_3(3, 14),

    // Row 16
    BROWN(0, 15),
    BROWN_GROUND_BONE_1(1, 15),
    BROWN_GROUND_BONE_2(2, 15),
    BROWN_GROUND_BONE_3(3, 15),

    // Row 17
    DOOR_1(0, 16),
    DOOR_2(1, 16),
    DOOR_3(2, 16),
    DOOR_4(3, 16),
    DOOR_5(4, 16),
    DOOR_6(5, 16),
    DOOR_7(6, 16),
    STAIRS_DOWN_1(7, 16),
    STAIRS_UP_1(8, 16),

    // Row 18
    CHEST_CLOSED_1(0, 17),
    CHEST_OPEN_1(1, 17),
    POT_CLOSED_1(2, 17),
    POT_OPEN_1(3, 17),
    BARREL_1(4, 17),
    BAG_1(5, 17),
    LOGS_1(6, 17),

    // Row 19
    ROCK_1(0, 18),
    ROCK_2(1, 18),

    // Row 20
    PLANT_1(0, 19),
    PLANT_2(1, 19),
    PLANT_3(2, 19),
    PLANT_4(3, 19),
    PLANT_5(4, 19),
    PLANT_6(5, 19),
    PLANT_7(6, 19),
    PLANT_8(7, 19),
    PLANT_9(8, 19),
    PLANT_10(9, 19),
    PLANT_11(10, 19),
    PLANT_12(11, 19),
    PLANT_13(12, 19),
    PLANT_14(13, 19),
    PLANT_15(14, 19),
    PLANT_16(15, 19),

    // Row 21
    MUSHROOM_1(0, 20),
    MUSHROOM_2(1, 20),

    // Row 22
    REMAINS_1(0, 21),
    REMAINS_2(1, 21),

    // Row 23
    BLOOD_1(0, 22),
    BLOOD_2(1, 22);

    private final int x;
    private final int y;

    TileType(int x, int y) {
        this.x = x;
        this.y = y;
    }
}