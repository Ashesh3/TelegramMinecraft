package Main.TelegramReporter.EventManager;

import Main.TelegramReporter.Main.TelegramReporter;
import Main.TelegramReporter.Telegram.BroadCast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
   private TelegramReporter plugin;
   public static String[] Command_abuse;
   public static String[] Command_spy;

   public CommandListener(TelegramReporter plugin) {
      this.plugin = plugin;
      this.loadCommandabuse();
      this.loadCommandSpy();
   }

   @EventHandler
   public void onCommand(PlayerCommandPreprocessEvent event) {
      String cmd = event.getMessage().toLowerCase() + " ";
      String[] commandsList;
      int i;
      String currCmd;
      String text;
      Thread broadcast;
      if (this.plugin.getConfig().getBoolean("settings.report-command-abuse.enable")) {
         commandsList = Command_abuse;
         for(i = 0; i < commandsList.length; ++i) {
            currCmd = commandsList[i];
            if (cmd.startsWith(currCmd) || currCmd.equals("/ ")) {
               if (event.getPlayer().hasPermission("tr.commandabuse.bypass")) {
                  return;
               }
               System.out.println("INSIDE");
               text = String.format("<b>WARNING!</b>\nPlayer <code>%s</code> tried to do this: <code>%s</code>\n<b>UUID: </b><code>%s</code>", event.getPlayer().getName(), event.getMessage(),event.getPlayer().getUniqueId().toString());
               broadcast = new Thread(new BroadCast(text, TelegramReporter.chat_ids, !this.plugin.getConfig().getBoolean("settings.report-command-abuse.disable-notofication")));
               broadcast.start();
               if (this.plugin.getConfig().getBoolean("settings.report-command-abuse.disable-command-action")) {
                  event.setCancelled(true);
                  event.getPlayer().sendMessage(this.plugin.getConfig().getString("settings.report-command-abuse.disable-command-action-message").replaceAll("&", "§"));
               }
               break;
            }
         }
      }

      if (this.plugin.getConfig().getBoolean("settings.command-spy.enable")) {
         commandsList = Command_spy;
         for(i = 0; i < commandsList.length; ++i) {
            currCmd = commandsList[i];
            if (cmd.startsWith(currCmd)) {
               if (event.getPlayer().hasPermission("tr.commandspy.bypass")) {
                  return;
               }

               text = String.format("<b>Command Spy!</b>\n<b>%s</b> : <code>%s</code>", event.getPlayer().getName(), cmd);
               broadcast = new Thread(new BroadCast(text, TelegramReporter.chat_ids, !this.plugin.getConfig().getBoolean("settings.command-spy.disable-notofication")));
               broadcast.start();
               break;
            }
         }
      }

   }

   private void loadCommandabuse() {
      int size = this.plugin.getConfig().getList("settings.report-command-abuse.commands").size();
      if (size != 0) {
         Command_abuse = new String[size];

         for(int i = 0; i < size; ++i) {
            Command_abuse[i] = this.plugin.getConfig().getList("settings.report-command-abuse.commands").get(i).toString().toLowerCase().trim() + " ";
         }

      }
   }

   private void loadCommandSpy() {
      int size = this.plugin.getConfig().getList("settings.command-spy.commands").size();
      if (size != 0) {
         Command_spy = new String[size];

         for(int i = 0; i < size; ++i) {
            Command_spy[i] = this.plugin.getConfig().getList("settings.command-spy.commands").get(i).toString().toLowerCase().trim() + " ";
         }

      }
   }
}
