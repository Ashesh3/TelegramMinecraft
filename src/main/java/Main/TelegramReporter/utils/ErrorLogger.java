package Main.TelegramReporter.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorLogger {
   public static void logError(StackTraceElement[] ErrorLog, File dataFolder) {
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] var3 = ErrorLog;
      int var4 = ErrorLog.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         StackTraceElement ste = var3[var5];
         sb.append(ste.toString());
      }

      if (!dataFolder.exists()) {
         dataFolder.mkdir();
      }

      File saveTo = new File(dataFolder, "ErrorLog.log");
      if (!saveTo.exists()) {
         try {
            saveTo.createNewFile();
         } catch (IOException var8) {
            return;
         }
      }

      try {
         FileWriter writer = new FileWriter(saveTo, true);
         writer.write(sb.toString() + "\n-----------------------------------------\n");
         writer.flush();
         writer.close();
      } catch (IOException var7) {
      }
   }
}
