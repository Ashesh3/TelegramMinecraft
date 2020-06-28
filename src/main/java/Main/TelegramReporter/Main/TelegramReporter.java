package Main.TelegramReporter.Main;

import Main.TelegramReporter.Commands.CommandReload;
import Main.TelegramReporter.Commands.CommandReport;
import Main.TelegramReporter.EventManager.ChatListener;
import Main.TelegramReporter.EventManager.CommandListener;
import Main.TelegramReporter.EventManager.PlayerLoginListener;
import Main.TelegramReporter.Telegram.getMe;
import Main.TelegramReporter.Telegram.getUpdates;
import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import Main.TelegramReporter.utils.DataBase;
import Main.TelegramReporter.utils.ErrorLogger;

public class TelegramReporter extends JavaPlugin {
   public static String token = null;
   public static String[] chat_ids;
   public static File dataFolder;
   public static TelegramReporter plugin;

   public void onEnable() {
      this.saveDefaultConfig();
      if (!this.getConfig().getBoolean("enable")) {
         this.getServer().getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[TelegramReporter] " + ChatColor.DARK_AQUA + "Please insert your bot token and set enable to true then reload/restart your server!");
         this.getPluginLoader().disablePlugin(this);
      } else {
         token = this.getConfig().getString("bot_token").trim();
         getMe getme = new getMe(this);
         getme.run();
         if (!getMe.field_0) {
            this.getPluginLoader().disablePlugin(this);
         } else {
            dataFolder = this.getDataFolder();
            plugin = this;
            this.loadChatids();
            this.getCommand("report").setExecutor(new CommandReport(this));
            this.getCommand("trreload").setExecutor(new CommandReload(this));
            this.getServer().getPluginManager().registerEvents(new CommandListener(this), this);
            this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
            this.getServer().getPluginManager().registerEvents(new PlayerLoginListener(this), this);
            getUpdates getUpds = new getUpdates(token);
            this.getServer().getScheduler().runTaskTimerAsynchronously(this,getUpds,20l,20l);
         }
      }
   }

   public void onDisable() {
   }

   private void loadChatids() {
      int size = this.getConfig().getList("ChatIds").size();
      if (size != 0) {
         chat_ids = new String[size];

         for(int i = 0; i < size; ++i) {
            chat_ids[i] = this.getConfig().getList("ChatIds").get(i).toString();
         }

      }
   }

   private void checkDatabase() {
      File datafolder = this.getDataFolder();
      File database = new File(datafolder, "TelReporter.db");
      if (!database.exists()) {
         try {
            database.createNewFile();
            DataBase.createNewTable("CREATE TABLE `usernames` (\n\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n\t`username`\tTEXT NOT NULL UNIQUE\n)");
         } catch (IOException var4) {
            this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database error :|");
            ErrorLogger.logError(var4.getStackTrace(), this.getDataFolder());
         }
      }

   }
}
