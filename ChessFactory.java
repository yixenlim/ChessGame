import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.File;

//This class is part of the design pattern "Factory Method" that we involved in Webale Chess Game
//This is a factory that used to create chess,remove chess and contains the information of all the chess on chess board
public class ChessFactory
{
    //Contains all the chess on the chess board of the current game
    private ArrayList<Chess> allChess = new ArrayList <> ();
    
    //Done by Sim Shin Xuan 
    //This method is used to remove a chess from factory when it is killed 
    public void removeChess(Chess c)
    {allChess.remove(c);}
    
    //Done by Sim Shin Xuan
    //This methos is used to remove all chess in factory and their information
    //Called from ChessBoard when ChessBoard is cleared when start/restart or load a saved game
    public void removeAllChess()
    {allChess.clear();}
    
    //Done by Lim Yixen
    //This method is used to save all the chess properties into ArrayList
    //Called from ChessBoard
    public ArrayList<String> getAllChessProperties()
    {
        ArrayList<String> gameDetails = new ArrayList <> ();
        
        for (int i=0; i<allChess.size(); i++)
        {
            //Save coordinates, colour and name of chess
            gameDetails.add(Integer.toString(allChess.get(i).getI()));
            gameDetails.add(Integer.toString(allChess.get(i).getJ()));
            gameDetails.add(allChess.get(i).getColour());
            gameDetails.add(allChess.get(i).getName());
            
            //Save the status (Up/Down) of "Arrow" "Triangle" and "Chevron"
            if (allChess.get(i).getName().equals("Arrow") || allChess.get(i).getName().equals("Triangle") || allChess.get(i).getName().equals("Chevron"))
                gameDetails.add(allChess.get(i).getStatus());
        }
        
        return gameDetails;
    }
    
    //Done by Lim Yixen
    //This method is used to remove the current "Plus" and "Triangle" in factory
    //Called from ChessBoard
    public ArrayList<Chess> removeCurrentPlusTri()
    {
        ArrayList<Chess> toBeChanged = new ArrayList <> ();
        
        for (int i=allChess.size()-1; i>=0;i--)
        {
            if (allChess.get(i).getName().equals("Triangle") || allChess.get(i).getName().equals("Plus"))
                toBeChanged.add(allChess.remove(i));
        }
        
        return toBeChanged;
    }
    
    //Done by Sim Shin Xuan 
    //This method is use to create all the chess
    //Called from ChessBoard when first place a chess on chess board
    public Chess createChess(int i,int j,String colour,String name)//ij is the coordinate of the chess
    {
        Chess c = new Chess();
        if (name.equals("Sun"))
            c = new Sun(colour,i,j);
        else if (name.equals("Chevron"))
            c = new Chevron(colour,i,j);
        else if (name.equals("Triangle"))
            c = new Triangle(colour,i,j);
        else if (name.equals("Plus"))
            c = new Plus(colour,i,j);
        else if (name.equals("Arrow"))
            c = new Arrow(colour,i,j);
        
        allChess.add(c);
        return c;
    }
}