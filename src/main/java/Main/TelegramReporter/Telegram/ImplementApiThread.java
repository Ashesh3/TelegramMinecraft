package Main.TelegramReporter.Telegram;

import java.io.IOException;
import java.net.URL;

public class ImplementApiThread implements Runnable {
   String ApiReq = null;

   public ImplementApiThread(String ApiReq) {
      this.ApiReq = ApiReq;
   }

   public void run() {
      if (this.ApiReq != null) {
         try {
            (new URL(this.ApiReq)).openStream().close();
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }

   }
}
