package customnpc.customnpc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Customnpc extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new Join(),this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("createnpc")){
            if(!(sender instanceof Player)){
                return true;
            }
            Player pl = (Player) sender;
            NPC.createNPC(pl);
            pl.sendMessage("NPC CREATED");
        }

        return false;
    }
}
