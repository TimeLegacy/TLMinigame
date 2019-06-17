package net.timelegacy.tlminigame.map;

import net.timelegacy.tlminigame.TLMinigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map {
    String configName;
    List<Location> spawns;

    public Map(String configName, List<Location> spawns) {
        this.configName = configName;
        this.spawns = spawns;
    }

    public YamlConfiguration getMapConfig() {
        File dataFile = new File(TLMinigame.getPlugin().getDataFolder(), this.configName + ".yml");
        return YamlConfiguration.loadConfiguration(dataFile);
    }

    public String getName() {
        return getMapConfig().getString("name");
    }

    public String getAuthor() {
        return getMapConfig().getString("author");
    }

    public int getMinPlayers() {
        return getMapConfig().getInt("minPlayers");
    }

    public int getSpawnCount() {
        return getMapConfig().getInt("spawnCount");
    }

    public String getWorldName() {
        return getMapConfig().getString("worldName");
    }

    public Location getCenter() {
        YamlConfiguration config = getMapConfig();

        Location center;

        String worldName = getWorldName();

        World world = Bukkit.getWorld(worldName);
        double x = config.getDouble("center.x");

        double y = config.getDouble("center.y");

        double z = config.getDouble("center.z");

        center = new Location(world, x, y, z);

        return center;
    }

    public static List<Location> spawns(String configName) {
        File dataFile = new File(TLMinigame.getPlugin().getDataFolder(), configName + ".yml");
        YamlConfiguration config = null;

        if (!dataFile.exists()) {
            try {
                if (!dataFile.getParentFile().exists()) {
                    dataFile.getParentFile().mkdirs();
                }
                dataFile.createNewFile();
                config = YamlConfiguration.loadConfiguration(dataFile);
                config.set("spawnCount", 0);
                config.set("worldName", configName);
                config.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (config == null)
            config = YamlConfiguration.loadConfiguration(dataFile);
        List<Location> temp = new ArrayList<>();
        int amount = config.getInt("spawnCount");
        String worldName = config.getString("worldName");

        World world = Bukkit.getWorld(worldName);
        if (config.getKeys(false).contains("spawns")) {
            for (int i = 0; i < amount; i++) {
                double x = config.getDouble("spawns." + i + ".x");

                double y = config.getDouble("spawns." + i + ".y");

                double z = config.getDouble("spawns." + i + ".z");

                temp.add(new Location(world, x, y, z));
            }
        }

        return temp;

    }

    public List<Location> getSpawns() {
        return this.spawns;
    }
}
