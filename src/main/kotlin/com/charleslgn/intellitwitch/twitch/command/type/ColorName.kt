package com.charleslgn.intellitwitch.twitch.command.type

/**
 * Allows you to change the color of your username. Normal users can choose only between these color.
 */
enum class ColorName(private val color: String) {
    /** The color blue  */
    BLUE("Blue"),

    /** The color coral  */
    CORAL("Coral"),

    /** The color godger blue  */
    DODGER_BLUE("DodgerBlue"),

    /** The color spring green  */
    SPRING_GREEN("SpringGreen"),

    /** The color yellow-green  */
    YELLOW_GREEN("YellowGreen"),

    /** The color green  */
    GREEN("Green"),

    /** The color orange-red  */
    ORANGE_RED("OrangeRed"),

    /** The color red  */
    RED("Red"),

    /** The color golden rod  */
    GOLDEN_ROD("GoldenRod"),

    /** The color hot pink  */
    HOT_PINK("HotPink"),

    /** The color codet blue  */
    CADET_BLUE("CadetBlue"),

    /** The color sea green  */
    SEA_GREEN("SeaGreen"),

    /** The color chocolate  */
    CHOCOLATE("Chocolate"),

    /** The color blue-violet  */
    BLUE_VIOLET("BlueViolet"),

    /** The color firegrick  */
    FIREBRICK("Firebrick");

    override fun toString() = color
}
