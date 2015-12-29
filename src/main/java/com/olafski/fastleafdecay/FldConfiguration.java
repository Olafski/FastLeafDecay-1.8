package com.olafski.fastleafdecay;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * Created by Olaf on 29-12-2015.
 */
public class FldConfiguration {
    public static Configuration config;

    public static int minimumDecayTime;
    public static int maximumDecayTime;

    public static void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.setCategoryComment("main", "There are 20 ticks in a second. Default settings are 4 minimum and 11 maximum decay time.");

        Property minimumDecayTimeProperty = config.get("main", "MinimumDecayTime", 4, "Minimum time in ticks for leaf decay. Must be lower than MaximumDecayTime!");
        Property maximumDecayTimeProperty = config.get("main", "MaximumDecayTime", 11, "Maximum time in ticks for leaf decay. Must be higher than MinimumDecayTime!");

        minimumDecayTime = minimumDecayTimeProperty.getInt();
        maximumDecayTime = maximumDecayTimeProperty.getInt();

        if (minimumDecayTime >= maximumDecayTime) {
            Logger logger = event.getModLog();
            logger.warn("MinimumDecayTime needs to be lower than MaximumDecayTime, resetting to default values!");

            minimumDecayTimeProperty.set(4);
            maximumDecayTimeProperty.set(11);

            minimumDecayTime = 4;
            maximumDecayTime = 11;
        }

        if (config.hasChanged()){
            config.save();
        }
    }

}
