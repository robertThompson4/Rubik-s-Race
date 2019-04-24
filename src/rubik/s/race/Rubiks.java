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

import java.awt.Menu;
import javax.swing.JFrame;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;





public class Rubiks extends JFrame
{
   private GameBoard gameboard = new GameBoard();
   
   private MenuBar menuBar = new MenuBar();
   
   private Solution solution = new Solution();
  
   
    public Rubiks()
    {
        add(gameboard);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        initializeMenu();
        //add(gameboard);
        
        
    }
    
    private void initializeMenu()
    {
        
        Menu Game = new Menu("Game");
        menuBar.add(Game);
        
        MenuItem startOver = new MenuItem("Start Over");
        Game.add(startOver); 
        MenuItem showSolution = new MenuItem("Show Solution");
        Game.add(showSolution);
        MenuItem viewHighScores = new MenuItem("View High Scores");
        Game.add(viewHighScores);
        
//        startOver.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//               for(int i = 0; i < GameBoard.cards.size(); i++)
//               {
//                   GameBoard.cards.get(i).flipBack();
//                   GameBoard.cards.get(i).makeUnMatched();
//                   GameBoard.startTime = gameboard.getTime();
//               }
//            }
//        });
//        
//        showSolution.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//               for(int i = 0; i < GameBoard.cards.size(); i++)
//               {
//                   GameBoard.tiles.get(i).flip();
//                   GameBoard.tiles.get(i).makeMatched();
//                   
//               }
//            }
//        });
//        
//        viewHighScores.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//               gameboard.showHighScores();
//            }
//        });
                
        setMenuBar(menuBar);
    }
}

