package net.momirealms.antigrieflib;

import fr.xyness.SCS.API.SimpleClaimSystemAPI;
import fr.xyness.SCS.API.SimpleClaimSystemAPI_Provider;
import fr.xyness.SCS.Types.Claim;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

final class SimpleClaimSystemV1Compatibility extends AbstractMemberAntiGriefCompatibility {
    private SimpleClaimSystemAPI scs;

    public SimpleClaimSystemV1Compatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.scs = SimpleClaimSystemAPI_Provider.getAPI();
    }

    @Override
    public boolean isMemberOrWild(Player player, Location location) {
        Claim claim = this.scs.getClaimAtChunk(location.getChunk());
        if (claim == null) return true;
        return claim.isMember(player.getUniqueId()) || player.getUniqueId().equals(claim.getUUID());
    }
}
