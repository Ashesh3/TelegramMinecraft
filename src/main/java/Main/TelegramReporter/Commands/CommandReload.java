package Main.TelegramReporter.Commands;

import Main.TelegramReporter.EventManager.ChatListener;
import Main.TelegramReporter.EventManager.CommandListener;
import Main.TelegramReporter.EventManager.PlayerLoginListener;
import Main.TelegramReporter.Main.TelegramReporter;
import Main.TelegramReporter.Telegram.getMe;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Main.TelegramReporter.utils.ErrorLogger;

public class CommandReload implements CommandExecutor {
   private TelegramReporter plugin;

   public CommandReload(TelegramReporter plugin) {
      this.plugin = plugin;
   }

   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (commandSender instanceof Player) {
         Player p = (Player)commandSender;
         if (p.hasPermission("tr.reload")) {
            try {
               this.reloadAction();
               p.sendMessage(ChatColor.DARK_AQUA + "Config successfully reloaded!");
            } catch (Exception var7) {
               p.sendMessage(ChatColor.DARK_RED + "Unable to reload config!\nHere is the ugly error message");
               var7.printStackTrace();
               ErrorLogger.logError(var7.getStackTrace(), TelegramReporter.dataFolder);
            }

            return true;
         } else {
            p.sendMessage(this.plugin.getConfig().getString("messages.no-permission").replaceAll("&", "§"));
            return true;
         }
      } else {
         try {
            this.reloadAction();
            this.plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Config successfully reloaded!");
         } catch (Exception var8) {
            this.plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Unable to reload config!\nHere is the ugly error message");
            var8.printStackTrace();
            ErrorLogger.logError(var8.getStackTrace(), TelegramReporter.dataFolder);
         }

         return true;
      }
   }

   private void reloadAction() {
      this.plugin.reloadConfig();
      this.loadToken();
      this.loadChatids();
      this.loadExpemtPlayers();
      this.loadCommandabuse();
      this.loadCommandSpy();
      this.loadStaffNotify();
      this.loadWords();
   }

   private void loadToken() {
      TelegramReporter.token = this.plugin.getConfig().getString("bot_token").trim();
      getMe getme = new getMe(this.plugin);
      getme.run();
      if (!getMe.field_0) {
         this.plugin.getPluginLoader().disablePlugin(this.plugin);
      }
   }

   private void loadChatids() {
      int size = this.plugin.getConfig().getList("ChatIds").size();
      if (size != 0) {
         TelegramReporter.chat_ids = new String[size];

         for(int i = 0; i < size; ++i) {
            TelegramReporter.chat_ids[i] = this.plugin.getConfig().getList("ChatIds").get(i).toString();
         }

      }
   }

   private void loadExpemtPlayers() {
      int size = this.plugin.getConfig().getList("settings.exempt-players").size();
      if (size != 0) {
         CommandReport.exempt_players = new String[size];

         for(int i = 0; i < size; ++i) {
            CommandReport.exempt_players[i] = this.plugin.getConfig().getList("settings.exempt-players").get(i).toString().toLowerCase().trim();
         }

      }
   }

   private void loadCommandabuse() {
      int size = this.plugin.getConfig().getList("settings.report-command-abuse.commands").size();
      if (size != 0) {
         CommandListener.Command_abuse = new String[size];

         for(int i = 0; i < size; ++i) {
            CommandListener.Command_abuse[i] = this.plugin.getConfig().getList("settings.report-command-abuse.commands").get(i).toString().toLowerCase().trim() + " ";
         }

      }
   }

   private void loadCommandSpy() {
      int size = this.plugin.getConfig().getList("settings.command-spy.commands").size();
      if (size != 0) {
         CommandListener.Command_spy = new String[size];

         for(int i = 0; i < size; ++i) {
            CommandListener.Command_spy[i] = this.plugin.getConfig().getList("settings.command-spy.commands").get(i).toString().toLowerCase().trim() + " ";
         }

      }
   }

   private void loadWords() {
      int size = this.plugin.getConfig().getList("settings.advance-send-in-game-chat.words").size();
      if (size != 0) {
         ChatListener.words = new String[size];

         for(int i = 0; i < size; ++i) {
            ChatListener.words[i] = this.plugin.getConfig().getList("settings.advance-send-in-game-chat.words").get(i).toString().toLowerCase().trim();
         }

      }
   }

   private void loadStaffNotify() {
      int size = this.plugin.getConfig().getList("settings.notify-staff.names_and_chatids").size();
      if (size != 0) {
         PlayerLoginListener.notify_staff = new String[size];

         for(int i = 0; i < size; ++i) {
            PlayerLoginListener.notify_staff[i] = this.plugin.getConfig().getList("settings.notify-staff.names_and_chatids").get(i).toString();
         }

      }
   }
}
