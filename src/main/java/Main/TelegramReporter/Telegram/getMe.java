package Main.TelegramReporter.Telegram;

import Main.TelegramReporter.Main.TelegramReporter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.bukkit.ChatColor;
import Main.TelegramReporter.utils.ErrorLogger;

public class getMe implements Runnable {
   private TelegramReporter plugin;
   // $FF: renamed from: ok java.lang.Boolean
   public static Boolean field_0 = false;

   public getMe(TelegramReporter plugin) {
      this.plugin = plugin;
   }

   public void run() {
      if (TelegramReporter.token == null) {
         System.out.println("NULL TOKEN");
      } else {
         String api = String.format("https://api.telegram.org/bot%s/getme", TelegramReporter.token);
         URL url = null;

         try {
            url = new URL(api);
         } catch (MalformedURLException var10) {
            var10.printStackTrace();
         }

         BufferedReader in = null;

         try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
         } catch (IOException var9) {
            field_0 = false;
            this.plugin.getServer().getConsoleSender().sendMessage("[TelegramReporter] " + ChatColor.RED + "Error!\nPlease make sure that you are insert a right bot token!\nIf you are sure that you insert a right bot token contact the developer :)\nYou can find error message in ErrorLog.log ");
            ErrorLogger.logError(var9.getStackTrace(), this.plugin.getDataFolder());
            return;
         }

         StringBuilder respone = new StringBuilder();

         String readLine;
         try {
            while((readLine = in.readLine()) != null) {
               respone.append(readLine);
            }
         } catch (IOException var11) {
            var11.printStackTrace();
            field_0 = false;
         }

         try {
            in.close();
         } catch (IOException var8) {
            var8.printStackTrace();
            field_0 = false;
         }

         JsonParser parser = new JsonParser();
         JsonObject jsonrespone = parser.parse(respone.toString()).getAsJsonObject();
         if (jsonrespone.has("ok") && jsonrespone.get("ok").getAsBoolean()) {
            this.plugin.getServer().getConsoleSender().sendMessage("[TelegramReporter] " + ChatColor.GREEN + "Bot detected " + ChatColor.DARK_AQUA + "@" + jsonrespone.getAsJsonObject("result").get("username").toString().replaceAll("\"", ""));
            field_0 = true;
         } else {
            field_0 = false;
         }
      }
   }
}
