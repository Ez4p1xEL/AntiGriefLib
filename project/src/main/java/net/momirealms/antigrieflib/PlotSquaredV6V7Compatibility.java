package net.momirealms.antigrieflib;

import com.plotsquared.bukkit.util.BukkitUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

final class PlotSquaredV6V7Compatibility extends AbstractMemberAntiGriefCompatibility {

    public PlotSquaredV6V7Compatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean isMemberOrWild(Player player, Location location) {
        var psLocation = BukkitUtil.adapt(location);
        if (psLocation.isPlotRoad()) return false;
        if (!psLocation.isPlotArea()) return true;
        return Optional.ofNullable(psLocation.getPlotArea()).map(area -> area.getPlot(psLocation)).map(plot -> plot.isAdded(player.getUniqueId()) || plot.isOwner(player.getUniqueId())).orElse(false);
    }
}
