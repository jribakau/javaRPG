package com.mygdx.game.assets.types;

import lombok.Getter;

/**
 * AnimalType - Enum for all animal sprites from animals.png
 */
@Getter
public enum AnimalType {
    // Row 1 - Bears
    GRIZZLY_BEAR(0, 0),
    BLACK_BEAR(1, 0),
    POLAR_BEAR(2, 0),
    PANDA(3, 0),

    // Row 2 - Apes
    CHIMPANZEE(0, 1),
    GORILLA(1, 1),
    ORANGUTAN(2, 1),

    // Row 3 - Monkeys
    AYE_AYE(0, 2),
    GIBBON(1, 2),
    MANDRILL(2, 2),
    CAPUCHIN(3, 2),
    LANGUR(4, 2),

    // Row 4 - Cats
    CAT(0, 3),
    BOBCAT(1, 3),
    COUGAR(2, 3),
    CHEETAH(3, 3),
    LYNX(4, 3),
    OCELOT(5, 3),
    MALE_LION(6, 3),
    FEMALE_LION(7, 3),

    // Row 5 - Dogs
    DOG(0, 4),
    PUPPY(1, 4),
    HYENA(2, 4),
    FOX(3, 4),
    JACKAL(4, 4),
    COYOTE(5, 4),

    // Row 6 - Rodents
    CAPYBARA(0, 5),
    BEAVER(1, 5),
    MINK(2, 5),
    MONGOOSE(3, 5),
    MARMOT(4, 5),
    GROUNDHOG(5, 5),
    CHINCHILLA(6, 5),
    ECHIDNA(7, 5),

    // Row 7 - Small Mammals
    AARDVARK(0, 6),
    ARMADILLO(1, 6),
    BADGER(2, 6),
    HONEY_BADGER(3, 6),
    COATI(4, 6),
    OPOSSUM(5, 6),
    RABBIT(6, 6),
    HARE(7, 6),

    // Row 8 - Snakes
    SNAKE(0, 7),
    COBRA(1, 7),
    KING_SNAKE(2, 7),
    BLACK_MAMBA(3, 7),

    // Row 9 - Reptiles
    ALLIGATOR(0, 8),
    MONITOR_LIZARD(1, 8),
    IGUANA(2, 8),
    TORTOISE(3, 8),
    SNAPPING_TURTLE(4, 8),
    ALLIGATOR_SNAPPING_TURTLE(5, 8),

    // Row 10 - Farm Animals
    COW(0, 9),
    HORSE(1, 9),
    DONKEY(2, 9),
    MULE(3, 9),
    ALPACA(4, 9),
    LLAMA(5, 9),
    PIG(6, 9),

    // Row 11 - Large Herbivores
    CAMEL(0, 10),
    REINDEER_CARIBOU(1, 10),
    WATER_BUFFALO(2, 10),
    YAK(3, 10),

    // Row 12 - Birds of Prey
    SEAGULL(0, 11),
    BARN_OWL(1, 11),
    COMMON_BUZZARD(2, 11),

    // Row 13 - Marsupials
    KANGAROO(0, 12),
    KOALA(1, 12),

    // Row 14 - Flightless Birds
    PENGUIN(0, 13),
    LITTLE_PENGUIN(1, 13),
    CASSOWARY(2, 13),
    EMU(3, 13),

    // Row 15 - Poultry
    CHICKEN(0, 14),
    ROOSTER(1, 14),
    MALLARD_DUCK(2, 14),
    SWAN(3, 14),
    TURKEY(4, 14),
    GUINEAFOWL(5, 14),
    PEACOCK(6, 14),

    // Row 16 - Goats and Sheep
    GOAT(0, 15),
    MOUNTAIN_GOAT(1, 15),
    IBEX(2, 15),
    SHEEP_RAM(3, 15),
    SHEEP_EWE(4, 15);

    private final int x;
    private final int y;

    AnimalType(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
