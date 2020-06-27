package Main.TelegramReporter.utils;

public class PJStr {
   public static String[] Separate(String str, String separator) {
      if (separator.length() != 1) {
         return null;
      } else {
         int count = 0;

         for(int i = 0; i < str.length(); ++i) {
            if (str.substring(i, i + 1).equals(separator)) {
               ++count;
            }
         }

         String[] res = new String[count];
         int startindex = 0;
         int finishindex = str.indexOf(separator);

         for(int i = 0; i < count; ++i) {
            res[i] = str.substring(startindex, finishindex);
            startindex = finishindex + 1;
            finishindex = str.indexOf(separator, startindex);
         }

         return res;
      }
   }
}
