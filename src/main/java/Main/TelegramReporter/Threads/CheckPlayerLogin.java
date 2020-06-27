package Main.TelegramReporter.Threads;

public class CheckPlayerLogin implements Runnable {
   private String playername = null;
   private Boolean notify = false;

   public CheckPlayerLogin(String playername, Boolean notify) {
      this.playername = playername;
      this.notify = notify;
   }

   public void run() {
   }
}
