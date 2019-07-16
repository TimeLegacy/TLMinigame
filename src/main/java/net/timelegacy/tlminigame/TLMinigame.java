package net.timelegacy.tlminigame;

import java.io.File;
import net.timelegacy.tlminigame.listener.PlayerJoinListener;
import net.timelegacy.tlminigame.listener.PlayerMovementListener;
import net.timelegacy.tlminigame.listener.PlayerPvPListener;
import net.timelegacy.tlminigame.listener.PlayerQuitListener;
import net.timelegacy.tlminigame.listener.SettingsListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TLMinigame extends JavaPlugin {

	private static Plugin plugin;

	private static File gameWorlds;

	private static boolean debug;

	/**
	 * Get whether or not the API is in debug mode, as set in the config.yml.
	 *
	 * @return boolean debug mode.
	 */
	public static boolean debugMode() {
		return debug;
	}

	public static File getGameWorldsFolder() {
		return gameWorlds;
	}

	public static Plugin getPlugin() {
		return plugin;


	}

	public static void logInfo(String msg) {
		System.out.println("[TLMinigame] [INFO] " + msg);
	}

	public static void sendDebugMessage(String message, Plugin plugin) {
		if (TLMinigame.debugMode()) {
			System.out.println(
					"[TLMinigame]" + (plugin.getName().equals("TLMinigame") ? "" : " [" + plugin.getName() + "]") + " [DEBUG] "
							+ message);
		}
	}

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PlayerMovementListener(), this);
		Bukkit.getPluginManager().registerEvents(new SettingsListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerPvPListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		plugin = this;

		gameWorlds = new File("GameWorlds");

		if (!gameWorlds.exists()) {
			gameWorlds.mkdir();
		}
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}

		this.saveDefaultConfig();
		debug = this.getConfig().getBoolean("debug");

	}
	
	
}
