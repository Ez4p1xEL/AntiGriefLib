package net.momirealms.antigrieflib;

import net.thenextlvl.protect.ProtectPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

final class ProtectCompatibility extends AbstractAntiGriefCompatibility {

    public ProtectCompatibility(Plugin plugin) {
        super(plugin);

    }

    @Override
    public void init() {
        registerFlagTester(Flag.PLACE, this::canPlace);
        registerFlagTester(Flag.BREAK, this::canBreak);
        registerFlagTester(Flag.INTERACT, this::canInteract);
        registerFlagTester(Flag.INTERACT_ENTITY, this::canInteractEntity);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::canDamageEntity);
        registerFlagTester(Flag.OPEN_CONTAINER, this::canInteract);
        registerFlagTester(Flag.OPEN_DOOR, this::canInteract);
        registerFlagTester(Flag.USE_BUTTON, this::canInteract);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::canPhysicalInteract);
    }

    private boolean canPlace(Player player, Location location) {
        return ((ProtectPlugin) plugin).protectionService().canPlace(player, location);
    }

    private boolean canBreak(Player player, Location location) {
        return ((ProtectPlugin) plugin).protectionService().canDestroy(player, location);
    }

    private boolean canInteract(Player player, Location location) {
        return ((ProtectPlugin) plugin).protectionService().canInteract(player, location);
    }

    private boolean canPhysicalInteract(Player player, Location location) {
        return ((ProtectPlugin) plugin).protectionService().canInteractPhysical(player, location);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return ((ProtectPlugin) plugin).protectionService().canInteract(player, entity.getLocation());
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return ((ProtectPlugin) plugin).protectionService().canAttack(player, entity);
    }
}
