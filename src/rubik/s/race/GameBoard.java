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
    private int BlackRowValue;
    private int BlackColValue;
    private Tile[][] tiles = new Tile[5][5];
    //private Card firstCard = null;
    //private Card secondCard = null;
    private Color[][] tileColors = new Color[5][5];
    private boolean finishedGame;
    static long startTime = 0;
    private long endTime = 0;
    private long timePlayed = 0;
    private ArrayList<Long> highscores = new ArrayList();
    private Color BLACK = new Color(0,0,0);

    
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
                newCard.rValue=r;
                newCard.cValue=c;
                //add(newCard);
                //cards.add(newCard);
                tiles[r][c] = newCard;
                newCard.addCardClickedListener(this);
                
                //Collections.shuffle(cards);
                
            }
        }
        shuffleTile(5);
        for(int r = 0; r < 5; r++)
        {
            for(int c = 0; c < 5; c++)
            {
                add(tiles[r][c]);
            }
        }
        for(int r = 0; r < 5; r++)
        {
            for(int c = 0; c < 5; c++)
            {
                if(tiles[r][c].frontColor==BLACK){
                    BlackRowValue=r;
                    BlackColValue=c;
                }
            }
        }
       
        startTime = getTime();
     }

     public void shuffleTile(int size){
         Random random1 = new Random();
         Random random2 = new Random();
         Tile[][] temp= new Tile[5][5];
         int holder1;
         int holder2;
         boolean[][] check=new boolean[size][size];
         int count=0;
         int orgRValue=0;
         int orgCValue=0;
         while(count !=((size*size)-1)){
             int rValue= random1.nextInt(size);
             int cValue= random2.nextInt(size);
             if(check[rValue][cValue]==false){
                 
                 
                 temp[orgRValue][orgCValue]=tiles[rValue][cValue];
                 tiles[rValue][cValue]=tiles[orgRValue][orgCValue];
                 tiles[rValue][cValue].rValue=rValue;
                 tiles[rValue][cValue].cValue=cValue;
                 tiles[orgRValue][orgCValue]=temp[orgRValue][orgCValue];
                 tiles[orgRValue][orgCValue].cValue=orgCValue;
                 tiles[orgRValue][orgCValue].rValue=orgRValue;
                 check[rValue][cValue]=true;
                 orgCValue++;
                 if(orgCValue==(size)){
                     orgRValue++;
                     orgCValue=0;
                 }
                     
                 count++;
             }
         }
             
         
     }
     
     
    @Override
    public void cardClicked(Tile tile) {
        
         
         Color temp;
        System.out.println(tile);
        if(tile.rValue==BlackRowValue){
            if(tile.cValue>BlackColValue){
                
                for(int i=BlackColValue;i<tile.cValue;i++){
                    temp=tiles[tile.rValue][(i+1)].frontColor;
                    tiles[tile.rValue][i].frontColor=temp;
                    tiles[tile.rValue][i].setBackColor(tiles[tile.rValue][i].frontColor);
                }
                
                
            }
            else{
                
                for(int i=BlackColValue;i>tile.cValue;i--){
                    temp=tiles[tile.rValue][(i-1)].frontColor;
                    tiles[tile.rValue][i].frontColor=temp;
                    tiles[tile.rValue][i].setBackColor(tiles[tile.rValue][i].frontColor);
                }
            }
            tiles[tile.rValue][tile.cValue].frontColor=BLACK;  
            tiles[tile.rValue][tile.cValue].setBackColor(tiles[tile.rValue][tile.cValue].frontColor);
            BlackRowValue=tile.rValue;
            BlackColValue=tile.cValue;
        }
        else if(tile.cValue==BlackColValue){
             if(tile.rValue>BlackRowValue){
                
                for(int i=BlackRowValue;i<tile.rValue;i++){
                    temp=tiles[(i+1)][tile.cValue].frontColor;
                    tiles[i][tile.cValue].frontColor=temp;
                    tiles[i][tile.cValue].setBackColor(tiles[i][tile.cValue].frontColor);
                }
                
                
            }
            else{
                
                for(int i=BlackRowValue;i>tile.rValue;i--){
                    temp=tiles[(i-1)][tile.cValue].frontColor;
                    tiles[i][tile.cValue].frontColor=temp;
                    tiles[i][tile.cValue].setBackColor(tiles[i][tile.cValue].frontColor);
                }
            }
            tiles[tile.rValue][tile.cValue].frontColor=BLACK; 
            tiles[tile.rValue][tile.cValue].setBackColor(tiles[tile.rValue][tile.cValue].frontColor);
            BlackRowValue=tile.rValue;
            BlackColValue=tile.cValue;
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
        //tileColors = new Color[5][5];
        Random random = new Random();
        
        int r=0;
        int c=0;
       while (r!=5){

            float red = random.nextFloat();
            float green = random.nextFloat();
            float blue = random.nextFloat();  
            Color newColor = new Color(red, green, blue);
            if(c==5){
                r++;
                c=0;
            }
            if(r!=5){
            tileColors[r][c] = newColor;
            c++;}
            if(c==5){
                r++;
                c=0;
            } 
            if(r !=5){
            tileColors[r][c] = newColor;
            c++;}
             if(c==5){
                r++;
                c=0;
            }
             if(r!=5){
            tileColors[r][c] = newColor;
            c++;}
             if(c==5){
                r++;
                c=0;
            }
             if(r!=5){       
                    
            tileColors[r][c] = newColor;
            c++;
             }
            //backColors.add(newColor);
            
        }
       tileColors[4][4]= BLACK;
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
