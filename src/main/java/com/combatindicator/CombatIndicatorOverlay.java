package com.combatindicator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

public class CombatIndicatorOverlay extends Overlay
{
    private final CombatIndicatorPlugin plugin;
    private final CombatIndicatorConfig config;

    @Inject
    public CombatIndicatorOverlay(CombatIndicatorPlugin plugin, CombatIndicatorConfig config)
    {
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        int size = config.overlaySize();
        int height = size / 3;
        int fontSize = height / 2;

        String label;
        Color color;

        switch (plugin.playerState)
        {
            case IN_COMBAT:
                label = "⚔ IN COMBAT";
                color = new Color(200, 0, 0, 200);
                break;
            case WALKING:
                label = "🚶 WALKING";
                color = new Color(200, 150, 0, 200);
                break;
            default:
                label = "💤 IDLE";
                color = new Color(0, 150, 200, 200);
                break;
        }

        graphics.setColor(color);
        graphics.fillRoundRect(10, 10, size, height, 10, 10);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, fontSize));

        int textX = 10 + (size / 2) - (graphics.getFontMetrics().stringWidth(label) / 2);
        int textY = 10 + (height / 2) + (fontSize / 3);
        graphics.drawString(label, textX, textY);

        return new Dimension(size + 20, height + 20);
    }
}