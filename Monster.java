import java.util.ArrayList;
import java.util.*;

public class Monster {
    char monsterChar;
    String monsterName;
    int monsterScore;
    String monsterPosition;

    /**
     * Rather than having an add Monster method, I use the constructor of
     * the monster to set the fields.
     */
    public Monster(String monster)
    {
        monsterChar = monster.charAt(0);
        monsterName = monster.toUpperCase();
        monsterScore = 0;
    }   
    /**
     * Returns the name of the monster.
     */
    public String getName()
    {
        return monsterName;
    }
    /**
     * Returns the first character of the monsters name.
     */
    public char getChar() {
        return monsterChar;
    }
    /**
     * Increased the score of the monster by 1.
     */
    public void increaseScore()
    {
        monsterScore++;
    }
    /**
     * Returns the score of the monsters.
     */
    public int getScore()
    {
        return monsterScore;
    }

    /**
     * Sets the position of the monster in string format since a direct return of coordinates is 
     * not needed in the current code.
     * 
     * However, in a scaleable version an int return would be useful
     */
    public void setMonsterPosition(int a, int b)
    {
        monsterPosition = "i:"+a+"| j:"+b;
    }
    /**
     * Returns the position of the monster as a string.
     */
    public String getMonsterPosition()
    {
        return monsterPosition;
    }
    
    /**
      * public void removeMonster(String monster) { for(int i = 0; i <
      * monsterList.size(); i++) { if (monsterList.contains(monster)) {
      * monsterList.remove(i); } } }
      */

}