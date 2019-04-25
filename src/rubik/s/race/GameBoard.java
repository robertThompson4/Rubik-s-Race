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
import java.awt.Component;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;




public class GameBoard extends JPanel implements ICardListener
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
    public Solution realSolution;
    private boolean gameCheck;
    
    public GameBoard()
    {
        setLayout(new GridLayout(ROWS, COLUMNS));
        initializeTiles();
        Solution solution = new Solution(tileColors);
        realSolution=solution;
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
        
        
        
        //winGame();
        checkWin();
        if(gameCheck==true){
            try{
            endGame();}
            catch (FileNotFoundException ex) {
                Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);}
        }

    }
    
    public void winGame(){
        for(int r = 1; r < 4; r++)
        {
            for(int c=1;c<4;c++){
                
                
                tiles[r][c].frontColor=realSolution.gb.solTiles[r-1][c-1].frontColor;
                tiles[r][c].setBackColor(tiles[r][c].frontColor);
            }
        }
        
        
    }
    
    
    public void endGame() throws FileNotFoundException
    {

        endTime = getTime();
        timePlayed = endTime - startTime;
        highscores.add(timePlayed);
        Collections.sort(highscores);
        
        Object[] options = {"Restart", "Continue"};

        int restart = JOptionPane.showOptionDialog(null,
        "Memory Game Completed in " + timePlayed + " seconds",
        "Highscores",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     
        options,  
        options[0]); 
        
        if(restart == JOptionPane.YES_OPTION)
        {
            Rubiks newGame = new Rubiks();
            realSolution.dispose();
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();

        }
         
        
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
    
    public void checkWin()
    {
        boolean gameFinished1 = true;
        boolean gameFinished2 = true;
        boolean gameFinished3 = true;
        boolean gameFinished4 = true;
        Tile[][] holder= new Tile[3][3];
        
        for(int r = 1; r < 4; r++)
        {
            for(int c=1;c<4;c++){
                if(tiles[r][c].frontColor!=realSolution.gb.solTiles[r-1][c-1].frontColor)
                {
                    gameFinished1 = false;
                }
            }
        }
        holder=rotate(realSolution.gb.solTiles);
        
        for(int r = 1; r < 4; r++)
        {
            for(int c=1;c<4;c++){
                if(tiles[r][c].frontColor!=holder[r-1][c-1].frontColor)
                {
                    gameFinished2 = false;
                }
            }
        }
        holder=rotate(holder);
        for(int r = 1; r < 4; r++)
        {
            for(int c=1;c<4;c++){
                if(tiles[r][c].frontColor!=holder[r-1][c-1].frontColor)
                {
                    gameFinished3 = false;
                }
            }
        }
        holder=rotate(holder);
        for(int r = 1; r < 4; r++)
        {
            for(int c=1;c<4;c++){
                if(tiles[r][c].frontColor!=holder[r-1][c-1].frontColor)
                {
                    gameFinished4 = false;
                }
            }
        }
        boolean gameFinished = false;
        if(gameFinished1==true){
            gameFinished=true;
        }
        if(gameFinished2==true){
            gameFinished=true;
        }
        if(gameFinished3==true){
            gameFinished=true;
        }
        if(gameFinished4==true){
            gameFinished=true;
        }
        
        gameCheck=gameFinished;
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
            
            
            Color newColor = new Color((red), (green), (blue));
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

    
   

        
    public void showHighScores()
    {
        for(int i = 0; i < 25; i++)
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
    }


    private Tile[][] rotate(Tile[][] solution)
    {
        Tile[][] returnTile= new Tile[3][3];
        Color nw = solution[0][0].frontColor;
        Color n = solution[0][1].frontColor;
        Color ne = solution[0][2].frontColor;
        Color w = solution[1][0].frontColor;
        Color e = solution[1][2].frontColor;
        Color sw = solution[2][0].frontColor;
        Color s = solution[2][1].frontColor;
        Color se = solution[2][2].frontColor;
        Color c= solution[1][1].frontColor;
        
        Tile newCard = new Tile("");      
        newCard.frontColor=sw;
        returnTile[0][0] = newCard;
        
        Tile newCard2 = new Tile("");      
        newCard2.frontColor=w;
        returnTile[0][1] = newCard2;
        
        
        Tile newCard3 = new Tile("");      
        newCard3.frontColor=nw;
        returnTile[0][2] = newCard3;
        
        Tile newCard4 = new Tile("");      
        newCard4.frontColor=s;
        returnTile[1][0] = newCard4;
        
        Tile newCard5 = new Tile("");      
        newCard5.frontColor=c;
        returnTile[1][1] = newCard5;
        
        Tile newCard6 = new Tile("");      
        newCard6.frontColor=n;
        returnTile[1][2] = newCard6;
        
        Tile newCard7 = new Tile("");      
        newCard7.frontColor=se;
        returnTile[2][0] = newCard7;
        
        Tile newCard8 = new Tile("");      
        newCard8.frontColor=e;
        returnTile[2][1] = newCard8;
        
        Tile newCard9 = new Tile("");      
        newCard9.frontColor=ne;
        returnTile[2][2] = newCard9;
        
        return solution;
    }
}
