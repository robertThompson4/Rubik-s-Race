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
    private Tile[][] cards = new Tile[5][5];
    //private Card firstCard = null;
    //private Card secondCard = null;
    private Color[][] cardColors = new Color[5][5];
    private boolean finishedGame;
    static long startTime = 0;
    private long endTime = 0;
    private long timePlayed = 0;
    private ArrayList<Long> highscores = new ArrayList();

    
    public GameBoard()
    {
        setLayout(new GridLayout(ROWS, COLUMNS));
        initializeCards();
    }


     public void initializeCards()
    {
        setColors();
        //cards = new ArrayList();
        for(int r = 0; r < 5; r++)
        {
            for(int c = 0; c < 5; c++)
            {
                Tile newCard = new Card("");
                newCard.setBackColor(cardColors[r][c]);
                //add(newCard);
                //cards.add(newCard);
                cards[r][c] = newCard;
                newCard.addCardClickedListener(this);

                //Collections.shuffle(cards);
                add(cards[r][c]);
            }
        }
        startTime = getTime();
     }

    @Override
    public void cardClicked(Card card) {
        card.flip();
        if(firstCard == null)
        {
            firstCard = card;
        }
        else if(secondCard == null)
        {
            secondCard = card;
            
            if(firstCard.getBackColor() == secondCard.getBackColor())
            {
                firstCard.makeMatched();
                secondCard.makeMatched();
            }
            else
            {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                firstCard.flipBack();
                secondCard.flipBack();  
            }

            firstCard = null;
            secondCard = null;
        }
        finishedGame = checkWinsetColors();();
        if(finishedGame)
        {  
            try {
                endGame();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        
        for(int i = 0; i < cards.size(); i++)
        {
            if(!cards.get(i).getIsMatched())
            {
                gameFinished = false;
            }
        }
        return gameFinished;
    }
    
    public void setColors()
    {
        //4 pieces of each of the 6 colors to be 
        
        //backColors = new ArrayList;
        cardColors = new Color[5][5];
        Random random = new Random();

        for(int r = 0; r < 5; r++)
        {
            for(int c = 0; c < 5; c++)
            {

            float red = random.nextFloat();
            float green = random.nextFloat();
            float blue = random.nextFloat();
            
            Color newColor = new Color(red, green, blue);
            cardColors[r][c] = newColor;
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
}
