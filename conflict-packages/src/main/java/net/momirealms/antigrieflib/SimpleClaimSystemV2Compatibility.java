package net.momirealms.antigrieflib;

import fr.xyness.SimpleClaimSystem.API.SCS_API;
import fr.xyness.SimpleClaimSystem.API.SCS_API_Provider;
import fr.xyness.SimpleClaimSystem.Types.Claim;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

final class SimpleClaimSystemV2Compatibility extends AbstractMemberAntiGriefCompatibility {

    public SimpleClaimSystemV2Compatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean isMemberOrWild(Player player, Location location) {
        SCS_API api = getApiOrNull();
        if (api == null) return true;
        Optional<Claim> claim = api.getClaim(location.getChunk());
        return claim.map(value -> value.isMember(player.getUniqueId()) || player.getUniqueId().equals(value.getOwnerUuid())).orElse(true);
    }

    public SCS_API getApiOrNull() {
        if (SCS_API_Provider.isRegistered()) {
            return SCS_API_Provider.get();
        }
        return null;
    }

    @Override
    public void init() {
    }
}
