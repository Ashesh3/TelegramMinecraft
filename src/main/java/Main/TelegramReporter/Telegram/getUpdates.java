package Main.TelegramReporter.Telegram;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Main.TelegramReporter.Main.TelegramReporter;

import org.bukkit.Bukkit;

public class getUpdates{
   private String token = null;
   private static HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_2).build();
   private static String max_update_id = "";
   private static String last_broadcasted_msg = "";

   public getUpdates(String token) {
      this.token = token;
   }

   public boolean fetch() {
      HttpResponse<String> response = null;
      try {
         HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create("https://api.telegram.org/bot" + this.token + "/getUpdates?offset="+max_update_id))
         .build();
         response = httpClient.send(request, BodyHandlers.ofString());    
      } catch (Exception e) {
         System.out.println("MAKE SURE YOU ARE USING JDK 11 or above!");
         e.printStackTrace();
         return false;
      }
      try{
         String[] whitelisted_chatids = TelegramReporter.chat_ids;
         JsonParser parser = new JsonParser();
         JsonObject jsonrespone = parser.parse(response.body()).getAsJsonObject();
         if (jsonrespone.has("result")) {
            JsonArray jarr = jsonrespone.get("result").getAsJsonArray();
            if(jarr.size()>0)
            {
               max_update_id = Integer.toString(jarr.get(jarr.size()-1).getAsJsonObject().get("update_id").getAsInt() + 1);
               for (int i = 0; i < jarr.size(); i++) {
                  String chatID = jarr.get(0).getAsJsonObject().get("message").getAsJsonObject().get("chat").getAsJsonObject().get("id").getAsString();
                  for (int j = 0; j < whitelisted_chatids.length; j++) {
                     if (whitelisted_chatids[j].equals(chatID)) {
                        if (jarr.get(0).getAsJsonObject().get("message").getAsJsonObject().has("text")) {
                           String message = "<" + jarr.get(0).getAsJsonObject().get("message").getAsJsonObject().get("from").getAsJsonObject().get("first_name").getAsString() + "> "
                                          + jarr.get(0).getAsJsonObject().get("message").getAsJsonObject().get("text").getAsString();
                         if(!message.equals(last_broadcasted_msg)){
                              Bukkit.broadcastMessage(message);
                              last_broadcasted_msg = message;
                              return true;
                          }
                        }
                        break;
                     }
                  }
               }
            }
         }
      }catch(Exception e){
         e.printStackTrace();
         return false;
      }
      return true;
   }
}
