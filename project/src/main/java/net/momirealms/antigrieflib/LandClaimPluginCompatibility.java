package net.momirealms.antigrieflib;

import org.ayosynk.landClaimPlugin.api.LandClaimAPI;
import org.ayosynk.landClaimPlugin.managers.PermissionResolver;
import org.ayosynk.landClaimPlugin.managers.WildernessProtection;
import org.ayosynk.landClaimPlugin.models.ClaimProfile;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

final class LandClaimPluginCompatibility extends AbstractAntiGriefCompatibility {

    public LandClaimPluginCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        registerFlagTester(Flag.PLACE, this::canPlace);
        registerFlagTester(Flag.BREAK, this::canBreak);
        registerFlagTester(Flag.INTERACT, this::canBreak);
        registerFlagTester(Flag.INTERACT_ENTITY, this::canDamageEntity);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::canDamageEntity);
        registerFlagTester(Flag.OPEN_CONTAINER, this::canBreak);
        registerFlagTester(Flag.OPEN_DOOR, this::canBreak);
        registerFlagTester(Flag.USE_BUTTON, this::canBreak);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::canBreak);
    }

    private boolean checkPermission(Player player, Location location, String permission) {
        if (player.hasPermission("landclaim.admin"))
            return true;
        ClaimProfile profile = LandClaimAPI.getInstance().getClaimAt(location);
        if (profile != null) {
            return PermissionResolver.hasPermission(profile, player.getUniqueId(), permission);
        }
        return !WildernessProtection.isDenied(location.getWorld(), player, permission);
    }

    private boolean canPlace(Player player, Location location) {
        return checkPermission(player, location, "BLOCK_PLACE");
    }

    private boolean canBreak(Player player, Location location) {
        return checkPermission(player, location, "BLOCK_BREAK");
    }

    private boolean canDamageEntity(Player player, Entity target) {
        if (target instanceof Animals || target instanceof Fish || target instanceof WaterMob) {
            return checkPermission(player, target.getLocation(), "DAMAGE_ANIMALS");
        } else if (target instanceof Monster || target instanceof Slime || target instanceof Flying) {
            return checkPermission(player, target.getLocation(), "DAMAGE_MONSTERS");
        } else if (target instanceof ArmorStand) {
            return checkPermission(player, target.getLocation(), "MODIFY_ARMOR_STANDS");
        } else if (target instanceof Hanging) {
            return checkPermission(player, target.getLocation(), "MODIFY_ITEM_FRAMES");
        }
        return true;
    }
}
