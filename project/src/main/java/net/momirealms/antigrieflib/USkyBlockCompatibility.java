package net.momirealms.antigrieflib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import java.util.Optional;

final class USkyBlockCompatibility extends AbstractMemberAntiGriefCompatibility {

    private uSkyBlockAPI api;

    public USkyBlockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        api = (uSkyBlockAPI) Bukkit.getPluginManager().getPlugin("uSkyBlock");
    }

    @Override
    public boolean isMemberOrWild(Player player, Location location) {
        return Optional.ofNullable(api.getIslandInfo(location))
                .map(islandInfo -> islandInfo.getMembers().contains(player.getName()))
                .orElse(true);
    }
}
