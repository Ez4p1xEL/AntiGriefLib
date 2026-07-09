package net.momirealms.antigrieflib;

import me.ulrich.clans.Clans;
import me.ulrich.clans.interfaces.ClaimImplement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

final class UltimateClansCompatibility extends AbstractMemberAntiGriefCompatibility {

    private Clans clans;

    public UltimateClansCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        this.clans = (Clans) Bukkit.getPluginManager().getPlugin("UltimateClans");
    }

    @Override
    public boolean isMemberOrWild(Player player, Location location) {
        for (Map.Entry<String, ClaimImplement> entry : this.clans.getClaimAPI().findClaimedLocationImplement(location)) {
            ClaimImplement implement = entry.getValue();
            if (implement.canDestroyClaimLocation(player, location)) {
                continue;
            }
            if (!implement.isOwnerClaimLocation(player, location) && !implement.isMemberClaimLocation(player, location)) {
                return false;
            }
        }
        return true;
    }
}
