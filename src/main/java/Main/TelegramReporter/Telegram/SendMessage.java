package Main.TelegramReporter.Telegram;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SendMessage {
   private String text = null;
   private Boolean HTML = false;
   private String chat_id = null;
   private Boolean notify = true;

   public void setText(String text) {
      try {
         this.text = URLEncoder.encode(text, "UTF-8");
      } catch (UnsupportedEncodingException var3) {
         var3.printStackTrace();
      }

   }

   public void setChatId(String chat_id) {
      this.chat_id = chat_id;
   }

   public void setChatId(long chat_id) {
      this.chat_id = Long.toString(chat_id);
   }

   public void setHTML(Boolean b) {
      this.HTML = b;
   }

   public void setNotify(Boolean b) {
      this.notify = b;
   }

   public String getSendApiString() {
      if (this.text != null && this.chat_id != null) {
         String url = String.format("https://api.telegram.org/bot{token}/sendmessage?chat_id=%s&text=%s", this.chat_id, this.text);
         if (this.HTML) {
            url = url + "&parse_mode=html";
         }

         if (!this.notify) {
            url = url + "&disable_notification=true";
         }

         return url;
      } else {
         return null;
      }
   }
}
