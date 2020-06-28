package Main.TelegramReporter.EventManager;

import Main.TelegramReporter.Main.TelegramReporter;
import Main.TelegramReporter.Telegram.BroadCast;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Statistic;
import Main.TelegramReporter.utils.PJStr;
import java.time.LocalTime;

public class PlayerLoginListener implements Listener {
   private TelegramReporter plugin;
   public static String[] notify_staff;

   public PlayerLoginListener(TelegramReporter plugin) {
      this.plugin = plugin;
      this.loadStaffNotify();
   }
   private String formatLocation(Location loc){
      return "{" + "world=" + loc.getWorld().getName() + ",x=" + loc.getX() + ",y=" + loc.getY() + ",z=" + loc.getZ() + "}";
   }

   private String formatTime(long millis){
         long days = TimeUnit.MILLISECONDS.toDays( millis );
         millis -= TimeUnit.DAYS.toMillis( days );
         long hours = TimeUnit.MILLISECONDS.toHours( millis );
         millis -= TimeUnit.HOURS.toMillis( hours );
         long minutes = TimeUnit.MILLISECONDS.toMinutes( millis );
         millis -= TimeUnit.MINUTES.toMillis( minutes );
         long seconds = TimeUnit.MILLISECONDS.toSeconds( millis );
 
         return days + " days "+ hours +" hours "+ minutes +" mintues "+ seconds +" seconds";
     
   }

   private void loadStaffNotify() {
      int size = this.plugin.getConfig().getList("settings.notify-staff.names_and_chatids").size();
      if (size != 0) {
         notify_staff = new String[size];

         for(int i = 0; i < size; ++i) {
            notify_staff[i] = this.plugin.getConfig().getList("settings.notify-staff.names_and_chatids").get(i).toString();
         }

      }
   }

   @EventHandler
   public void onPlayerLogin(PlayerLoginEvent event) {
      if (this.plugin.getConfig().getBoolean("settings.notify-staff.enable")) {
         String playername = event.getPlayer().getName().toLowerCase();
         String[] var3 = notify_staff;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String staffname = var3[var5];
            if (playername.equalsIgnoreCase(staffname.substring(0, staffname.indexOf(":")))) {
               String chatids = staffname.substring(staffname.indexOf(":") + 1);
               chatids = chatids + ":";
               String[] chatidsarray = PJStr.Separate(chatids, ":");
               String msg = String.format("<b>ALERT!</b>\n<b>%s</b> <code>joined the game!</code>\n<b>Login details:</b>\n<b>IP address:</b> <code>%s</code>\n<b>Date:</b> <code>%s</code>", event.getPlayer().getName(), event.getAddress().toString().replace("/", ""), (new Date()).toString());
               Thread broadcast = new Thread(new BroadCast(msg, chatidsarray, !this.plugin.getConfig().getBoolean("settings.notify-staff.disable-notofication")));
               broadcast.start();
               break;
            }
         }
      }

   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      if (this.plugin.getConfig().getBoolean("settings.join-leave-players.enable")) {
         String message = String.format("<b>%s joined the game!</b>\n<b>IP: </b><code>%s</code>\n<b>UUID: </b><code>%s</code>", event.getPlayer().getName(), event.getPlayer().getAddress().toString().replace("/", ""), event.getPlayer().getUniqueId().toString());
         Thread broadcast = new Thread(new BroadCast(message, TelegramReporter.chat_ids, !this.plugin.getConfig().getBoolean("settings.new-players.disable-notification")));
         broadcast.start();
      }
   }
   
   @EventHandler
   public void onPlayerLeave(PlayerQuitEvent event) {
      if (this.plugin.getConfig().getBoolean("settings.join-leave-players.enable")) {
         String message = String.format("<b>%s left the game!</b>\n<b>Total Time Played: </b><code>%s</code>\n<b>Last Location: </b><code>%s</code>\n<b>Reason: </b><code>%s</code>\n<b>UUID: </b><code>%s</code>", event.getPlayer().getName(), formatTime((long)(event.getPlayer().getStatistic( Statistic.PLAY_ONE_MINUTE ) * 0.05 * 1000 )), formatLocation(event.getPlayer().getLocation()), event.getQuitMessage() ,event.getPlayer().getUniqueId().toString());
         Thread broadcast = new Thread(new BroadCast(message, TelegramReporter.chat_ids, !this.plugin.getConfig().getBoolean("settings.new-players.disable-notification")));
         broadcast.start();
      }
   }
}
