package Main.TelegramReporter.Telegram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

public class getUpdates extends Thread {
   private String token = null;

   public getUpdates(String token) {
      this.token = token;
   }

   public void run() {
      URL url = null;

      try {
         url = new URL(String.format("https://api.telegram.org/bot%s/getupdates", this.token));
      } catch (MalformedURLException var9) {
         var9.printStackTrace();
      }

      BufferedReader in = null;

      try {
         in = new BufferedReader(new InputStreamReader(url.openStream()));
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      LinkedList lines = new LinkedList();

      String readLine;
      try {
         while((readLine = in.readLine()) != null) {
            lines.add(readLine);
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }

      try {
         in.close();
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      Iterator var5 = lines.iterator();

      while(var5.hasNext()) {
         String line = (String)var5.next();
         System.out.println("> " + line);
         System.out.println("\n");
      }

   }
}
