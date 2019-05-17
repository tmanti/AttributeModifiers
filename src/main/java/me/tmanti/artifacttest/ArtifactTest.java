package me.tmanti.artifacttest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArtifactTest extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player;
        if(sender instanceof Player){
            player = (Player) sender;
        } else {
            return true;
        }
        Double amount = 0.0;
        if(args.length == 1){
            try {
                amount = Double.parseDouble(args[0]);
            } catch (Exception e){
                return true;
            }
        } else if (!label.equalsIgnoreCase("getTest")){
            sender.sendMessage(ChatColor.BLUE + "[Test Plugin]" + ChatColor.GRAY + "need a number");
            return true;
        }
        if(label.equalsIgnoreCase("add")){
            player.getInventory().addItem(newTestItem(label, amount));
        }
        else if(label.equalsIgnoreCase("addscalar")){
            player.getInventory().addItem(newTestItem(label, amount));
        }
        else if(label.equalsIgnoreCase("multiplyscalar")){
            player.getInventory().addItem(newTestItem(label, amount));
        }
        else if(label.equalsIgnoreCase("getTest")){
            player.getInventory().addItem(newTestItem(label, amount));
        }
        return true;
    }

    public ItemStack newTestItem(String attributeModification, Double amount){
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Stick of Truth");
        meta.setUnbreakable(true);
        if(attributeModification.equals("add")) meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("testModAdd", amount, AttributeModifier.Operation.ADD_NUMBER));
        if(attributeModification.equals("addscalar")) meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("testMod", amount, AttributeModifier.Operation.ADD_SCALAR));
        if(attributeModification.equals("multiplyscalar")) meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("testModMultiply", amount, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item != null){
            if((event.getAction().equals(Action.RIGHT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK))&&(item.getItemMeta().getDisplayName().equals("Stick of Truth"))){
                Player player = event.getPlayer();
                player.sendMessage("Base Move Speed Value: " + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
                player.sendMessage("Move Speed Value: " + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue());
            }
        }
    }
}
