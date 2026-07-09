package net.momirealms.antigrieflib;

import com.craftaro.skyblock.api.SkyBlockAPI;
import com.craftaro.skyblock.api.island.IslandRole;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

final class FabledSkyBlockCompatibility extends AbstractMemberAntiGriefCompatibility {

    public FabledSkyBlockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean isMemberOrWild(Player player, Location location) {
        return Optional.ofNullable(SkyBlockAPI.getIslandManager().getIslandAtLocation(location))
                .map(island -> {
                            if (island.isCoopPlayer(player))
                                return true;
                            return Optional.ofNullable(island.getRole(player))
                                    .map(islandRole -> islandRole != IslandRole.VISITOR)
                                    .orElse(false);
                        }
                )
                .orElse(true);
    }
}