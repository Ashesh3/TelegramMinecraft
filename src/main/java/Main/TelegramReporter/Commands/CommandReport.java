package Main.TelegramReporter.Commands;

import Main.TelegramReporter.Main.TelegramReporter;
import Main.TelegramReporter.Telegram.BroadCast;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReport implements CommandExecutor {
   private TelegramReporter plugin;
   public static String[] exempt_players;

   public CommandReport(TelegramReporter plugin) {
      this.plugin = plugin;
      this.loadExpemtPlayers();
   }

   public boolean onCommand(CommandSender Sender, Command command, String s, String[] args) {
      if (!(Sender instanceof Player)) {
         Sender.sendMessage(ChatColor.RED + "This command can only executed by a player");
         return true;
      } else {
         Player p = (Player)Sender;
         if (!this.plugin.getConfig().getBoolean("settings.enable-report")) {
            p.sendMessage(ChatColor.RED + "Report command is currently disable!");
            return true;
         } else if (!p.hasPermission("tr.report")) {
            p.sendMessage(this.plugin.getConfig().getString("messages.no-permission").replaceAll("&", "§"));
            return true;
         } else if (args.length == 1) {
            p.sendMessage(this.plugin.getConfig().getString("messages.report-without-reason-error").replaceAll("&", "§"));
            return true;
         } else if (args.length == 0) {
            p.sendMessage(this.plugin.getConfig().getString("messages.report-without-player-name").replaceAll("&", "§"));
            return true;
         } else {
            String reported = args[0];
            String[] var7 = exempt_players;
            int i = var7.length;

            String text;
            for(int var9 = 0; var9 < i; ++var9) {
               text = var7[var9];
               if (reported.equalsIgnoreCase(text)) {
                  p.sendMessage(this.plugin.getConfig().getString("messages.cant-report-staff-member").replaceAll("&", "§"));
                  return true;
               }
            }

            if (args[0].equalsIgnoreCase(p.getName())) {
               p.sendMessage(ChatColor.RED + "You can't report your self!");
               return true;
            } else {
               StringBuilder reason = new StringBuilder();

               String reporter;
               for(i = 1; i < args.length; ++i) {
                  reporter = args[i];
                  reason.append(reporter);
                  reason.append(" ");
               }

               String Reason = reason.toString();
               reporter = p.getName();
               text = String.format("<b>NEW REPORT</b>\n<b>Reporter:</b> <code>%s</code>\n<b>Reported player:</b> <code>%s</code>\n<b>Reason:</b> <code>%s</code>", reporter, reported, Reason);
               Thread broadcast = new Thread(new BroadCast(text, TelegramReporter.chat_ids, !this.plugin.getConfig().getBoolean("settings.disable-report-notification")));
               broadcast.start();
               p.sendMessage(this.plugin.getConfig().getString("messages.success-report").replaceAll("&", "§"));
               return true;
            }
         }
      }
   }

   private void loadExpemtPlayers() {
      int size = this.plugin.getConfig().getList("settings.exempt-players").size();
      if (size != 0) {
         exempt_players = new String[size];

         for(int i = 0; i < size; ++i) {
            exempt_players[i] = this.plugin.getConfig().getList("settings.exempt-players").get(i).toString().toLowerCase().trim();
         }

      }
   }
}
