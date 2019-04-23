/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubik.s.race;

/**
 *
 * @author robert
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class GameBoard  extends JPanel implements ICardListener
{
    private final int ROWS = 5;
    private final int COLUMNS = 5;
    private int EmptyRowValue;
    private int EmptyColValue;
    private Tile[][] tiles = new Tile[5][5];
    //private Card firstCard = null;
    //private Card secondCard = null;
    private Color[][] tileColors = new Color[5][5];
    private boolean finishedGame;
    static long startTime = 0;
    private long endTime = 0;
    private long timePlayed = 0;
    private ArrayList<Long> highscores = new ArrayList();

    
    public GameBoard()
    {
        setLayout(new GridLayout(ROWS, COLUMNS));
        initializeTiles();
    }


     public void initializeTiles()
    {
        setColors();
        //cards = new ArrayList();
        for(int r = 0; r < 5; r++)
        {
            for(int c = 0; c < 5; c++)
            {
                Tile newCard = new Tile("");
                newCard.setBackColor(tileColors[r][c]);
                //add(newCard);
                //cards.add(newCard);
                tiles[r][c] = newCard;
                newCard.addCardClickedListener(this);
                shuffleTile(5);
                //Collections.shuffle(cards);
                add(tiles[r][c]);
            }
        }
       
        startTime = getTime();
     }

     public void shuffleTile(int size){
         Random random1 = new Random();
         Random random2 = new Random();
         boolean[][] check=new boolean[size][size];
         int count=0;
         int orgRValue=0;
         int orgCValue=0;
         while(count !=((size*size)-1)){
             int rValue= random1.nextInt(size);
             int cValue= random2.nextInt(size);
             if(check[rValue][cValue]==false){
                 tiles[rValue][cValue]=tiles[orgRValue][orgCValue];
                 check[rValue][cValue]=true;
                 orgCValue++;
                 if(orgCValue==(size-1)){
                     orgRValue++;
                     orgCValue=0;
                 }
                     
                 count++;
             }
         }
             
         
     }
     
     
    @Override
    public void cardClicked(Tile tile) {
        
//        card.flip();
//        if(firstCard == null)
//        {
//            firstCard = card;
//        }
//        else if(secondCard == null)
//        {
//            secondCard = card;
//            
//            if(firstCard.getBackColor() == secondCard.getBackColor())
//            {
//                firstCard.makeMatched();
//                secondCard.makeMatched();
//            }
//            else
//            {
//                try {
//                    Thread.sleep(700);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                firstCard.flipBack();
//                secondCard.flipBack();  
//            }
//
//            firstCard = null;
//            secondCard = null;
//        }
//        finishedGame = checkWinsetColors();();
//        if(finishedGame)
//        {  
//            try {
//                endGame();
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
    
    public void endGame() throws FileNotFoundException
    {

        endTime = getTime();
        timePlayed = endTime - startTime;
        highscores.add(timePlayed);
        Collections.sort(highscores);
        
        JOptionPane.showMessageDialog(null,
        "Memory Game Completed in " + timePlayed + " seconds",
          "Highscores",JOptionPane.PLAIN_MESSAGE);
         
        
        ObjectOutputStream oos = null;
        try {

            FileOutputStream fout = new FileOutputStream("highscores.ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(highscores);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
       
    }
    
    public long getTime()
    {
        long result;
        result = System.currentTimeMillis()/1000;
        
        return result;
    }
    
    private boolean checkWin()
    {
        boolean gameFinished = true;
        
        for(int r = 0; r < 3; r++)
        {
            for(int c=0;c<3;c++){
//                if(!tiles[r][c].getIsMatched())
//                {
//                    gameFinished = false;
//                }
            }
        }
        return gameFinished;
    }
    
    public void setColors()
    {
        //4 pieces of each of the 6 colors to be 
        
        //backColors = new ArrayList;
        tileColors = new Color[5][5];
        Random random = new Random();
        
        
        for(int r = 0; r < 5; r++)
        {
            for(int c = 0; c < 5; c++)
            {

            float red = random.nextFloat();
            float green = random.nextFloat();
            float blue = random.nextFloat();  
            Color newColor = new Color(red, green, blue);  
            tileColors[r][c] = newColor;
            c++;
            tileColors[r][c] = newColor;
            c++;
            tileColors[r][c] = newColor;
            c++;
            tileColors[r][c] = newColor;
            
            //backColors.add(newColor);
            }
        }
    }

    
   

        /*
    public void showHighScores()
    {
        for(int i = 0; i < cards.size(); i++)
               {
                    File f = new File("highscores.ser");
                    if(f.exists()) { 

                        //https://www.tutorialspoint.com/java/java_serialization.htm

                        try {
                         FileInputStream fileIn = new FileInputStream("highscores.ser");
                         ObjectInputStream in = new ObjectInputStream(fileIn);
                         highscores = (ArrayList<Long>) in.readObject();
                         in.close();
                         fileIn.close();
                      } catch (IOException e) {
                         e.printStackTrace();
                         return;
                      } catch (ClassNotFoundException ex) {
                            Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     }

                           }
                        
            
                    String scoreOutput = "";
                    
                    for(int c = 0; c < highscores.size(); c++)
                    {
                       scoreOutput = scoreOutput.concat((c+1) + ". " + highscores.get(c) + "\n");
                    }

                    JOptionPane.showMessageDialog(null,
                        scoreOutput, "Highscores",JOptionPane.PLAIN_MESSAGE);
    }*/

//    @Override
//    public void cardClicked(Card card) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
