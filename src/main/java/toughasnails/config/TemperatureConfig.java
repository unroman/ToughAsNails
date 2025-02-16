/*******************************************************************************
 * Copyright 2021, the Glitchfiend Team.
 * All rights reserved.
 ******************************************************************************/
package toughasnails.config;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;
import toughasnails.core.ToughAsNails;
import toughasnails.temperature.BuiltInTemperatureModifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TemperatureConfig
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.IntValue temperatureChangeDelay;
    public static ForgeConfigSpec.IntValue armorTemperatureChangeDelay;
    public static ForgeConfigSpec.IntValue handheldTemperatureChangeDelay;
    public static ForgeConfigSpec.IntValue playerTemperatureChangeDelay;
    public static ForgeConfigSpec.IntValue extremityReboundTemperatureChangeDelay;
    public static ForgeConfigSpec.IntValue extremityDamageDelay;
    public static ForgeConfigSpec.IntValue climateClemencyDuration;
    public static ForgeConfigSpec.BooleanValue climateClemencyRespawning;

    public static ForgeConfigSpec.IntValue temperatureDropAltitude;
    public static ForgeConfigSpec.IntValue temperatureRiseAltitude;
    public static ForgeConfigSpec.IntValue environmentalModifierAltitude;

    public static ForgeConfigSpec.IntValue nearHeatCoolProximity;

    public static ForgeConfigSpec.IntValue nightHotTemperatureChange;
    public static ForgeConfigSpec.IntValue nightTemperatureChange;

    public static ForgeConfigSpec.IntValue onFireTemperatureChange;
    public static ForgeConfigSpec.IntValue powderSnowTemperatureChange;
    public static ForgeConfigSpec.IntValue wetTemperatureChange;
    public static ForgeConfigSpec.IntValue snowTemperatureChange;
    public static ForgeConfigSpec.IntValue wetTicks;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> temperatureModifierOrder;

    private static final List<String> DEFAULT_TEMPERATURE_MODIFIER_ORDER = ImmutableList.of(BuiltInTemperatureModifier.PLAYER_MODIFIERS, BuiltInTemperatureModifier.ITEM_MODIFIER, BuiltInTemperatureModifier.ARMOR_MODIFIER)
        .stream().map(e -> e.toString().toLowerCase()).toList();

    private static final Predicate<Object> TEMPERATURE_MODIFIER_VALIDATOR = o -> {
        return o instanceof String && Arrays.stream(BuiltInTemperatureModifier.values()).map(BuiltInTemperatureModifier::toString).anyMatch(s -> s.equalsIgnoreCase((String)o));
    };


    private static List<BuiltInTemperatureModifier> temperatureModifierOrderCache;
    public static List<BuiltInTemperatureModifier> getTemperatureModifierOrder()
    {
        if (temperatureModifierOrderCache == null)
        {
            temperatureModifierOrderCache = temperatureModifierOrder.get().stream().map(s -> BuiltInTemperatureModifier.valueOf(s.toUpperCase())).toList();
        }
        return temperatureModifierOrderCache;
    }

    static
    {
        BUILDER.comment("Please be advised that certain temperature-related options are world-specific and are located in <Path to your world folder>/serverconfig/toughasnails-server.toml.");
        BUILDER.push("general");
        temperatureChangeDelay = BUILDER.comment("Number of ticks to delay changing the player's temperature after their temperature changes.").defineInRange("temperature_change_delay", 500, 0, Integer.MAX_VALUE);
        armorTemperatureChangeDelay = BUILDER.comment("Number of ticks to delay changing the player's temperature after their temperature changes when wearing armor.").defineInRange("armor_temperature_change_delay", 50, 0, Integer.MAX_VALUE);
        handheldTemperatureChangeDelay = BUILDER.comment("Number of ticks to delay changing the player's temperature after their temperature changes when holding an item.").defineInRange("handheld_temperature_change_delay", 375, 0, Integer.MAX_VALUE);
        playerTemperatureChangeDelay = BUILDER.comment("Number of ticks to delay changing the player's temperature after their temperature changes when affected by a player-based temperature modifier.").defineInRange("player_temperature_change_delay", 125, 0, Integer.MAX_VALUE);
        extremityReboundTemperatureChangeDelay = BUILDER.comment("Number of ticks to delay changing the player's temperature after their temperature changes when rebounding from an extreme temperature.").defineInRange("extremity_rebound_temperature_change_delay", 250, 0, Integer.MAX_VALUE);
        extremityDamageDelay = BUILDER.comment("Number of ticks to delay taking damage when icy or hot.").defineInRange("extremity_damage_delay", 500, 0, Integer.MAX_VALUE);
        climateClemencyDuration = BUILDER.comment("Number of ticks for the duration of Climate Clemency.").defineInRange("climate_clemency_duration", 6000, 0, Integer.MAX_VALUE);
        climateClemencyRespawning = BUILDER.comment("Whether or not Climate Clemency should be granted when respawning.").define("climate_clemency_respawning", false);
        temperatureModifierOrder = BUILDER.comment("The order in which to apply built-in temperature modifiers").defineList("temperature_modifier_order", DEFAULT_TEMPERATURE_MODIFIER_ORDER, TEMPERATURE_MODIFIER_VALIDATOR);
        BUILDER.pop();
        BUILDER.push("altitude");
        temperatureDropAltitude = BUILDER.comment("Y level to drop the temperature at when above").defineInRange("temperature_drop_altitude", 1024, -64, 1024);
        temperatureRiseAltitude = BUILDER.comment("Y level to rise the temperature at when below").defineInRange("temperature_rise_altitude", -64, -64, 1024);
        environmentalModifierAltitude = BUILDER.comment("Y level above which environmental modifiers are applied").defineInRange("environmental_modifier_altitude", 50, -64, 256);
        BUILDER.pop();
        BUILDER.push("blocks");
        nearHeatCoolProximity = BUILDER.comment("The proximity which constitutes near a heat or cool source").defineInRange("near_heat_cool_proximity", 7, 1, 16);
        BUILDER.pop();
        BUILDER.push("immersion");
        onFireTemperatureChange = BUILDER.comment("Amount to change the temperature by when on fire.").defineInRange("on_fire_temperature_change", 2, -4, 4);
        powderSnowTemperatureChange = BUILDER.comment("Amount to change the temperature by when in powdered snow.").defineInRange("powdered_snow_temperature_change", -2, -4, 4);
        wetTemperatureChange = BUILDER.comment("Amount to change the temperature by when wet.").defineInRange("wet_temperature_change", -1, -4, 4);
        snowTemperatureChange = BUILDER.comment("Amount to change the temperature by when snowing.").defineInRange("snow_temperature_change", -1, -4, 4);
        wetTicks = BUILDER.comment("Number of ticks a player stays wet for after touching water, rain or snow.").defineInRange("wet_ticks", 40, 0, Integer.MAX_VALUE);
        BUILDER.pop();
        BUILDER.push("time");
        nightTemperatureChange = BUILDER.comment("Amount to change the temperature at night when the original temperature is not hot.").defineInRange("night_temperature_change", -1, -4, 4);
        nightHotTemperatureChange = BUILDER.comment("Amount to change the temperature at night when the original temperature is hot.").defineInRange("night_hot_temperature_change", -2, -4, 4);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
