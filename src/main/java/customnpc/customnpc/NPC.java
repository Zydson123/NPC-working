package customnpc.customnpc;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NPC {
    private static List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
    public static void createNPC(Player pl){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(pl.getWorld())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "kihfas");
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile);
        npc.b(pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ(), pl.getLocation().getYaw(), pl.getLocation().getPitch());
        addNPCpacket(npc);
        NPC.add(npc);
    }

    public static void addNPCpacket(EntityPlayer npc){
        for(Player pl : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer) pl).getHandle().b;
            connection.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
            connection.a(new PacketPlayOutEntityHeadRotation(npc, (byte)(npc.getBukkitYaw()*256/360)));
        }
    }
    public static void addJoinPacket(Player pl){
        for(EntityPlayer npc : NPC){
            PlayerConnection connection = ((CraftPlayer) pl).getHandle().b;
            connection.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
            connection.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.getBukkitYaw()*256/360) ));
        }
    }

    public static List<EntityPlayer> getNPC() {
        return NPC;
    }

}
