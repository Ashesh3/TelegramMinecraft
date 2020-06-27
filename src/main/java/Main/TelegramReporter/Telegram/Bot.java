package Main.TelegramReporter.Telegram;

import java.io.IOException;
import java.net.URL;

public class Bot {
   private String token = null;

   public Bot(String token) {
      this.token = token;
   }

   public void ImplementApiMethodThread(String api) {
      if (api != null || this.token != null) {
         api = api.replace("{token}", this.token);
         Thread thread = new Thread(new ImplementApiThread(api));
         thread.start();
      }
   }

   public void ImplementApiMethod(String api) {
      if (api != null || this.token != null) {
         api = api.replace("{token}", this.token);

         try {
            (new URL(api)).openStream().close();
         } catch (IOException var3) {
            var3.printStackTrace();
         }

      }
   }
}
