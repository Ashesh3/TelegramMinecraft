package Main.TelegramReporter.Telegram;

import Main.TelegramReporter.Main.TelegramReporter;
import java.net.URL;
import org.bukkit.ChatColor;
import Main.TelegramReporter.utils.ErrorLogger;

public class BroadCast implements Runnable {
   private String message = null;
   private String[] chat_ids;
   private Boolean notify;

   public BroadCast(String message, String[] chat_ids, Boolean notify) {
      this.message = message;
      this.chat_ids = chat_ids;
      this.notify = notify;
   }

   public void run() {
      if (this.message != null) {
         SendMessage sm = new SendMessage();
         sm.setText(this.message);
         sm.setHTML(true);
         sm.setNotify(this.notify);
         String[] var2 = this.chat_ids;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String chat_id = var2[var4];
            sm.setChatId(chat_id);
            String api = sm.getSendApiString();
            api = api.replace("{token}", TelegramReporter.token);

            try {
               (new URL(api)).openStream().close();
            } catch (Exception var8) {
               TelegramReporter.plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "\"" + chat_id + "\" " + ChatColor.RED + "is not a valid chat_id or you bot are blocked from this chat_id\nCheck your ChatIds in ChatIds section in config.yml\nYou can see the full report of this error in ErrorLog.log");
               ErrorLogger.logError(var8.getStackTrace(), TelegramReporter.dataFolder);
            }
         }

      }
   }
}
