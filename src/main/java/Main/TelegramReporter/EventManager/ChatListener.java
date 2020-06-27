package Main.TelegramReporter.EventManager;

import Main.TelegramReporter.Main.TelegramReporter;
import Main.TelegramReporter.Telegram.BroadCast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
   private TelegramReporter plugin;
   public static String[] words;

   public ChatListener(TelegramReporter plugin) {
      this.plugin = plugin;
      if (plugin.getConfig().getBoolean("settings.advance-send-in-game-chat.enabled")) {
         this.loadWords();
      }

   }

   @EventHandler
   public void onPlayerChat(AsyncPlayerChatEvent event) {
      if (this.plugin.getConfig().getBoolean("settings.send-in-game-chat")) {
         String text = String.format("<b>%s</b> : <code>%s</code>", event.getPlayer().getName(), event.getMessage());
         if (this.plugin.getConfig().getBoolean("settings.advance-send-in-game-chat.enabled")) {
            String[] var3 = words;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String word = var3[var5];
               if (event.getMessage().toLowerCase().contains(word.toLowerCase())) {
                  Thread broadcast = new Thread(new BroadCast(text, TelegramReporter.chat_ids, !this.plugin.getConfig().getBoolean("settings.disable-notification-in-send-chat")));
                  broadcast.start();
                  break;
               }
            }
         } else {
            Thread broadcast = new Thread(new BroadCast(text, TelegramReporter.chat_ids, !this.plugin.getConfig().getBoolean("settings.disable-notification-in-send-chat")));
            broadcast.start();
         }

      }
   }

   private void loadWords() {
      int size = this.plugin.getConfig().getList("settings.advance-send-in-game-chat.words").size();
      if (size != 0) {
         words = new String[size];

         for(int i = 0; i < size; ++i) {
            words[i] = this.plugin.getConfig().getList("settings.advance-send-in-game-chat.words").get(i).toString().toLowerCase().trim();
         }

      }
   }
}
