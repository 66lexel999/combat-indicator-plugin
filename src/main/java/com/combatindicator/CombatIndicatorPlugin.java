package com.combatindicator;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
		name = "Combat Indicator",
		description = "Shows whether you are idle, walking, or in combat",
		tags = {"combat", "indicator", "overlay", "idle", "walking"}
)
public class CombatIndicatorPlugin extends Plugin
{
	enum PlayerState { IDLE, WALKING, IN_COMBAT }

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CombatIndicatorOverlay overlay;

	@Inject
	private CombatIndicatorConfig config;

	public PlayerState playerState = PlayerState.IDLE;
	private WorldPoint lastPosition = null;

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		playerState = PlayerState.IDLE;
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		Player local = client.getLocalPlayer();
		if (local == null) return;

		Actor interacting = local.getInteracting();
		WorldPoint currentPosition = local.getWorldLocation();

		if (interacting != null)
		{
			playerState = PlayerState.IN_COMBAT;
		}
		else if (lastPosition != null && !currentPosition.equals(lastPosition))
		{
			playerState = PlayerState.WALKING;
		}
		else
		{
			playerState = PlayerState.IDLE;
		}

		lastPosition = currentPosition;
	}

	@Provides
	CombatIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CombatIndicatorConfig.class);
	}
}