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
import java.awt.Button;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Tile extends Button
{
    public int rValue;
    public int cValue;
    public Color frontColor = null;
    private boolean isMatched;
    //private ICardListener[][] clickListeners = new ICardListener[5][5];
    private ArrayList<ICardListener> clickListeners = new ArrayList<ICardListener>();
    //private ArrayList<ArrayList<ICardListener>> clickListeners = new Array<ArrayList<ICardListener>>();
    
    public Tile(String label)
    {
        super(label);
            addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(!isMatched)
                {
                    clickListeners.forEach((clickListener) -> {
                        clickListener.cardClicked((Tile)e.getSource());
                    }); //for( clickListeners)
                }
            }
        });
    }

   

     public void setBackColor(Color newFrontColor)
    {
        frontColor = newFrontColor;
        setBackground(frontColor);
    }
    
    public void addCardClickedListener(ICardListener listener)
    {
        clickListeners.add(listener);
    }
    
    
//    private void buttonClicked(MouseEvent e)
//    {
//         setBackground(Color.red);
//    }

}