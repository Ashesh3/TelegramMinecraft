package Main.TelegramReporter.utils;

import Main.TelegramReporter.Main.TelegramReporter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
   static String filePath = "plugins/TelegramReporter/TelReporter.db";

   public static void createNewTable(String SqlQuery) {
      String url = "jdbc:sqlite:" + filePath;

      try {
         Connection conn = DriverManager.getConnection(url);
         Throwable var3 = null;

         try {
            Statement stmt = conn.createStatement();
            Throwable var5 = null;

            try {
               stmt.execute(SqlQuery);
            } catch (Throwable var30) {
               var5 = var30;
               throw var30;
            } finally {
               if (stmt != null) {
                  if (var5 != null) {
                     try {
                        stmt.close();
                     } catch (Throwable var29) {
                        var5.addSuppressed(var29);
                     }
                  } else {
                     stmt.close();
                  }
               }

            }
         } catch (Throwable var32) {
            var3 = var32;
            throw var32;
         } finally {
            if (conn != null) {
               if (var3 != null) {
                  try {
                     conn.close();
                  } catch (Throwable var28) {
                     var3.addSuppressed(var28);
                  }
               } else {
                  conn.close();
               }
            }

         }
      } catch (SQLException var34) {
         var34.printStackTrace();
      }

   }

   public static Boolean CheckIfUserExist(String playername) {
      String sql = String.format("SELECT COUNT(username) FROM usernames WHERE username ='%s';", playername);
      Connection c = null;
      Statement stmt = null;

      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:" + filePath);
         c.setAutoCommit(false);
         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         int i = rs.getInt(1);
         stmt.close();
         c.commit();
         c.close();
         return i != 1;
      } catch (Exception var6) {
         ErrorLogger.logError(var6.getStackTrace(), TelegramReporter.dataFolder);
         return null;
      }
   }

   public static void InsertNewUser(String playername) {
      String sql = String.format("INSERT INTO usernames (username) VALUES ('%s')", playername);
      Insert_Update_Delete_Query(sql);
   }

   private static Boolean Insert_Update_Delete_Query(String query) {
      Connection c = null;
      Statement stmt = null;

      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:" + filePath);
         c.setAutoCommit(false);
         stmt = c.createStatement();
         stmt.executeUpdate(query);
         stmt.close();
         c.commit();
         c.close();
         return true;
      } catch (Exception var4) {
         ErrorLogger.logError(var4.getStackTrace(), TelegramReporter.dataFolder);
         return false;
      }
   }
}
