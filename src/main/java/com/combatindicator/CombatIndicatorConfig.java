package com.combatindicator;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("combatindicator")
public interface CombatIndicatorConfig extends Config
{
    @Range(min = 50, max = 300)
    @ConfigItem(
            keyName = "overlaySize",
            name = "Overlay Size",
            description = "Adjust the size of the overlay"
    )
    default int overlaySize()
    {
        return 160;
    }
}