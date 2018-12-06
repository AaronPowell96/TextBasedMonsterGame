/**
 * The base idea of this class was found from an online resource at:
 * http://www.java-gaming.org/index.php?topic=35802.0
 * 
 * Rather than randomly generating a name, I selected the predefined names using a forloop in the game class
 * to produce a unique character starting monster for the full board.
 */

public class NameGenerator {

   private static String[] names = { "Aleks", "Billy", "Callum", "Drake", "Erica",
         "Freya", "Grace", "Harry", "Isac", "Jamie", "Kate", "Laura", "Mark", "Nik",
         "Ola", "Peter", "Quinn", "Ross", "Scott", "Toby", "Uma", "Victoria", "Will",
         "Xavi", "Yuzi", "Zoe" };

   public static String generateName(int i) {           
      return names[i];          
   }

}