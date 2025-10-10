package com.mygdx.game.assets;

import lombok.Getter;

/**
 * TileType - Enum for all tile sprites from tiles.png
 */
@Getter
public enum TileType {
    // Row 1 - Brown Dirt Tiles
    DIRT_BRICK_FLOOR(0, 0),
    DIRT_BRICK_WALL(1, 0),
    DARK_VOID(2, 0),

    // Row 2 - Light Blue Stone Tiles
    ICE_STONE_FLOOR(0, 1),
    ICE_STONE_WALL(1, 1),

    // Row 3 - Gray Brick Tiles
    STONE_BRICK_FLOOR(0, 2),
    STONE_BRICK_WALL(1, 2),
    STONE_BRICK_DOORWAY(2, 2),

    // Row 4 - Dark Cobblestone Tiles
    DARK_COBBLESTONE_FLOOR(0, 3),
    DARK_COBBLESTONE_WALL(1, 3),

    // Row 5 - Red Brick Tiles
    RED_BRICK_FLOOR(0, 4),
    RED_BRICK_WALL(1, 4),

    // Row 6 - Skull Tiles
    SKULL_TILE_FLOOR(0, 5),
    SKULL_TILE_WALL(1, 5),

    // Row 7 & 8 - Dark Cave Floor Details
    DARK_CAVE_FLOOR(0, 6),
    DARK_CAVE_PEBBLES_1(1, 6),
    DARK_CAVE_PEBBLES_2(2, 6),
    DARK_CAVE_PEBBLES_3(3, 6),
    PURPLE_CAVE_PEBBLES_1(4, 6),
    PURPLE_CAVE_PEBBLES_2(5, 6),
    PURPLE_CAVE_PEBBLES_3(6, 6),

    PURPLE_CAVE_FLOOR(0, 7),
    DARK_CAVE_GRASS_1(1, 7),
    DARK_CAVE_GRASS_2(2, 7),
    DARK_CAVE_GRASS_3(3, 7),
    PURPLE_CAVE_GRASS_1(4, 7),
    PURPLE_CAVE_GRASS_2(5, 7),
    PURPLE_CAVE_GRASS_3(6, 7),

    // Row 9, 10, 11 - More Dark Cave Floor Details
    DARK_CAVE_GOLD_ORE_1(1, 8),
    DARK_CAVE_GOLD_ORE_2(2, 8),
    DARK_CAVE_GOLD_ORE_3(3, 8),
    PURPLE_CAVE_GOLD_ORE_1(4, 8),
    PURPLE_CAVE_GOLD_ORE_2(5, 8),
    PURPLE_CAVE_GOLD_ORE_3(6, 8),

    DARK_CAVE_BLUE_BRICKS_1(1, 9),
    DARK_CAVE_BLUE_BRICKS_2(2, 9),
    DARK_CAVE_BLUE_BRICKS_3(3, 9),
    PURPLE_CAVE_BLUE_BRICKS_1(4, 9),
    PURPLE_CAVE_BLUE_BRICKS_2(5, 9),
    PURPLE_CAVE_BLUE_BRICKS_3(6, 9),

    DARK_CAVE_BONES_1(1, 10),
    DARK_CAVE_BONES_2(2, 10),
    DARK_CAVE_BONES_3(3, 10),
    PURPLE_CAVE_BONES_1(4, 10),
    PURPLE_CAVE_BONES_2(5, 10),
    PURPLE_CAVE_BONES_3(6, 10),

    // Row 12 - Red Cave Floor Details
    MAROON_CAVE_FLOOR(0, 11),
    MAROON_CAVE_BRICKS_1(1, 11),
    MAROON_CAVE_BRICKS_2(2, 11),
    MAROON_CAVE_BRICKS_3(3, 11),
    RED_CAVE_BRICKS_1(4, 11),
    RED_CAVE_BRICKS_2(5, 11),
    RED_CAVE_BRICKS_3(6, 11),

    // Row 13 - Blue Floor Details
    DEEP_BLUE_FLOOR(0, 12),
    DEEP_BLUE_BRICKS_1(1, 12),
    DEEP_BLUE_BRICKS_2(2, 12),
    DEEP_BLUE_BRICKS_3(3, 12),

    // Row 14 & 15 - Green Grass Floor Details
    GRASS_FLOOR(0, 13),
    GRASS_DIRT_PATCH_1(1, 13),
    GRASS_DIRT_PATCH_2(2, 13),
    GRASS_DIRT_PATCH_3(3, 13),
    GRASS_TUFT_1(1, 14),
    GRASS_TUFT_2(2, 14),
    GRASS_TUFT_3(3, 14),

    // Row 16 - Brown Floor Details
    BROWN_DIRT_FLOOR(0, 15),
    BROWN_DIRT_BONES_1(1, 15),
    BROWN_DIRT_BONES_2(2, 15),
    BROWN_DIRT_BONES_3(3, 15),

    // Row 17 - Doors & Stairs
    WOODEN_DOOR(0, 16),
    GREEN_PLANK_DOOR(1, 16),
    ROUND_TOP_DOOR(2, 16),
    ARCHED_DOOR(3, 16),
    DUNGEON_DOOR(4, 16),
    GATED_DOOR(5, 16),
    CELL_DOOR(6, 16),
    STAIRS_DOWN(7, 16),
    STAIRS_UP(8, 16),

    // Row 18 - Containers
    CHEST_CLOSED(0, 17),
    CHEST_OPEN(1, 17),
    CLAY_POT(2, 17),
    BROKEN_POT(3, 17),
    BARREL(4, 17),
    SACK(5, 17),
    WOOD_PILE(6, 17),

    // Row 19 - Rocks
    SMALL_ROCK(0, 18),
    LARGE_ROCK(1, 18),

    // Row 20 - Plants & Flowers
    WHITE_FLOWER(0, 19),
    TALL_WHITE_FLOWER(1, 19),
    ORANGE_LILY(2, 19),
    FERN(3, 19),
    LEAFY_PLANT(4, 19),
    BAMBOO_SHOOTS(5, 19),
    YELLOW_DAFFODIL(6, 19),
    WHEAT(7, 19),
    CORN(8, 19),
    RED_SNAPDRAGON(9, 19),
    RED_BERRIES(10, 19),
    PINK_ROSEBUD(11, 19),
    RED_TULIP(12, 19),
    WITHERED_PLANT(13, 19),
    BLUE_CENTER_FLOWER(14, 19),
    PINK_CARNATION(15, 19),

    // Row 21 - Mushrooms
    RED_MUSHROOMS(0, 20),
    LARGE_RED_MUSHROOM(1, 20),

    // Row 22 - Remains
    SKELETON_PILE(0, 21),
    BONE_PILE(1, 21),

    // Row 23 - Blood Splatters
    BLOOD_SPLATTER_1(0, 22),
    BLOOD_SPLATTER_2(1, 22);

    private final int x;
    private final int y;

    TileType(int x, int y) {
        this.x = x;
        this.y = y;
    }

}