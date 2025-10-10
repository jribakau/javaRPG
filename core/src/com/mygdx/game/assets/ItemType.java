package com.mygdx.game.assets;

import lombok.Getter;

/**
 * ItemType - Enum for all item sprites from items.png
 */
@Getter
public enum ItemType {
    // Row 1 - Swords
    DAGGER(0, 0),
    SHORTSWORD(1, 0),
    ARMING_SWORD(2, 0),
    LONGSWORD(3, 0),
    GREATSWORD(4, 0),
    ZWEIHANDER(5, 0),

    // Row 2 - Wide & Curved Swords
    FALCHION(0, 1),
    BROADSWORD(1, 1),
    SABRE(2, 1),
    RAPIER(3, 1),
    FLAMBERGE(4, 1),
    EXECUTIONER_SWORD(5, 1),

    // Row 3 - Curved Blades
    SICKLE(0, 2),
    SCIMITAR(1, 2),

    // Row 4 - Axes
    HATCHET(0, 3),
    BATTLE_AXE(1, 3),
    GREATAXE(2, 3),

    // Row 5 - Hammers & Mauls
    WOODEN_MALLET(0, 4),
    BLACKSMITH_HAMMER(1, 4),
    WARHAMMER(2, 4),
    HEAVY_WARHAMMER(3, 4),
    MAUL(4, 4),

    // Row 6 - Maces
    FLANGED_MACE(0, 5),
    MORNING_STAR(1, 5),
    SCEPTER_MACE(2, 5),

    // Row 7 - Spears
    SPEAR(0, 6),

    // Row 8 - Flails
    GRAIN_FLAIL(0, 7),
    MILITARY_FLAIL(1, 7),
    THREE_HEADED_FLAIL(2, 7),

    // Row 9 - Clubs
    WOODEN_CLUB(0, 8),
    SPIKED_CLUB(1, 8),
    HEAVY_CLUB(2, 8),

    // Row 10 - Ranged Weapons
    HAND_CROSSBOW(0, 9),
    SHORTBOW(1, 9),
    LONGBOW(2, 9),

    // Row 11 - Staves
    APPRENTICE_STAFF(0, 10),
    MAGE_STAFF(1, 10),
    NATURE_STAFF(2, 10),
    CRYSTAL_STAFF(3, 10),
    GOLDEN_STAFF(4, 10),
    NECROMANCER_STAFF(5, 10),

    // Row 12 - Shields
    BUCKLER(0, 11),
    HEATER_SHIELD(1, 11),
    CRUSADER_SHIELD(2, 11),
    TOWER_SHIELD(3, 11),

    // Row 13 - Chest Armor
    LEATHER_TUNIC(0, 12),
    STUDDED_LEATHER(1, 12),
    BLUE_GAMBESON(2, 12),
    BRIGANDINE_ARMOR(3, 12),
    SCALE_MAIL(4, 12),
    WHITE_SHIRT(5, 12),

    // Row 14 - Gloves
    LEATHER_GLOVES(0, 13),
    REINFORCED_GLOVES(1, 13),
    BLUE_GLOVES(2, 13),
    PLATE_GAUNTLETS(3, 13),

    // Row 15 - Boots
    LEATHER_BOOTS(0, 14),
    HEAVY_BOOTS(1, 14),
    BLUE_BOOTS(2, 14),
    PLATE_GREAVES(3, 14),

    // Row 16 - Head Armor
    PURPLE_HOOD(0, 15),
    GALEA_HELMET(1, 15),
    WIZARD_HAT(2, 15),
    GREY_COIF(3, 15),
    NASAL_HELMET(4, 15),
    BARBUTE_HELMET(5, 15),
    GREAT_HELM(6, 15),
    ORNATE_FULL_HELM(7, 15),

    // Row 17 - Necklaces
    RUBY_AMULET(0, 16),
    SAPPHIRE_AMULET(1, 16),
    LAPIS_AMULET(2, 16),
    GOLDEN_PENDANT(3, 16),
    KEY_NECKLACE(4, 16),
    STONE_AMULET(5, 16),

    // Row 18 - Gemmed Rings
    GOLD_EMERALD_RING(0, 17),
    GOLD_BAND(1, 17),
    JADE_RING(2, 17),
    SILVER_SAPPHIRE_RING(3, 17),
    PLAIN_GOLD_RING(4, 17),
    SILVER_AMETHYST_RING(5, 17),

    // Row 19 - Signet Rings
    GOLD_SIGNET_RING(0, 18),
    SILVER_SIGNET_RING(1, 18),
    SILVER_JADE_RING(2, 18),
    SILVER_BAND(3, 18),
    GOLD_TOPAZ_RING(4, 18),
    ORNATE_SILVER_RING(5, 18),

    // Row 20 - Small Potions
    LESSER_MANA_POTION(0, 19),
    LESSER_HEALTH_POTION(1, 19),
    ANTIDOTE_VIAL(2, 19),
    HEALTH_POTION(3, 19),
    LESSER_STAMINA_POTION(4, 19),

    // Row 21 - Large Potions
    QUICKNESS_POTION(0, 20),
    STAMINA_POTION(1, 20),
    ELIXIR_OF_STRENGTH(2, 20),
    MANA_POTION(3, 20),
    CONCOCTION_POTION(4, 20),

    // Row 22 - Scrolls and Books
    SCROLL(0, 21),
    SPELLBOOK(1, 21);

    private final int x;
    private final int y;

    ItemType(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
