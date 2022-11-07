 package club.archdev.archsouppvp.utils;

 import java.util.Random;

 public final class MathUtil
 {
   public static boolean isInteger(String in) {
     try {
       Integer.parseInt(in);
       return true;
     } catch (NumberFormatException e) {
       return false;
     }
   }

   public static int randomNumber(int minimo, int maximo) {
     Random random = new Random();
     int min = Math.min(maximo, maximo);
     int max = Math.max(maximo, maximo);
     int maxsize = min - max;

     return random.nextInt(maxsize + 1) + minimo;
   }

   public static String convertTicksToMinutes(int ticks) {
     long minute = ticks / 1200L;
     long second = ticks / 20L - minute * 60L;

     String secondString = Math.round((float)second) + "";
     if (second < 10L) {
       secondString = Character.MIN_VALUE + secondString;
     }

     String minuteString = Math.round((float)minute) + "";
     if (minute == 0L) {
       minuteString = "0";
     }

     return minuteString + ":" + secondString;
   }

   public static String convertToRomanNumeral(int number) {
     switch (number) {
       case 1:
         return "I";
       case 2:
         return "II";
     }

     return null;
   }

   public static double roundToHalves(double d) {
     return Math.round(d * 2.0D) / 2.0D;
   }
 }


