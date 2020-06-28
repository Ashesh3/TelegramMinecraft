package Main.TelegramReporter.Telegram;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.*;

import Main.TelegramReporter.Main.TelegramReporter;

import org.bukkit.Bukkit;

public class getUpdates extends Thread {
   private String token = null;

   public getUpdates(String token) {
      this.token = token;
   }

   public void run() {
      String inline = "";
      try {
         URL url = new URL("https://api.telegram.org/bot" + this.token + "/getUpdates");
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.connect();
         Scanner sc = new Scanner(url.openStream());
         while (sc.hasNext()) {
            inline += sc.nextLine();
         }
         sc.close();
         JSONParser parse = new JSONParser();
         JSONObject jobj = (JSONObject) parse.parse(inline);
         JSONArray jsonarr_1 = (JSONArray) jobj.get("result");
         String[] whitelisted_chatids = TelegramReporter.chat_ids;
         for (int i = 0; i < jsonarr_1.size(); i++) {
            String chatID = (String) (((JSONObject) ((JSONObject) ((JSONObject) jsonarr_1.get(i)).get("message"))
                  .get("chat")).get("id").toString());
            for (int j = 0; j < whitelisted_chatids.length; j++) {
               if (whitelisted_chatids[j].equals(chatID)) {

                  if (((Object) ((JSONObject) ((JSONObject) jsonarr_1.get(i)).get("message")).get("text")) != null) {
                     String message = "<"
                           + (String) (((JSONObject) ((JSONObject) ((JSONObject) jsonarr_1.get(i)).get("message"))
                                 .get("from")).get("first_name").toString())
                           + "> "
                           + ((String) ((JSONObject) ((JSONObject) jsonarr_1.get(i)).get("message")).get("text"));
                     Bukkit.broadcastMessage(message);
                  }
                  break;
               }
            }
         }
         if(jsonarr_1.size() > 0)
         {
            URL url2 = new URL("https://api.telegram.org/bot" + token + "/getUpdates?offset=" + (Integer
               .parseInt((((JSONObject) (jsonarr_1.get(jsonarr_1.size() - 1))).get("update_id").toString())) + 1));
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setRequestMethod("GET");
            conn2.connect();
            url2.openStream();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
}
