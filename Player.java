import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

//Done by Wong Phang Wei
//This class contains the details of a player
public class Player
{
    //The team/colour of a player (Red/Blue)
    private String colour;
    //Default moves of player is 0
    private int moves = 0;
    
    //Default constructor
    public Player()
    {}
    
    //Constructor declaration of class "Player" to set the colour of player
    public Player(String colour)
    {this.colour = colour;}
    
    //This method is used to set the move of a player 
    public void setMoves(int i)
    {moves = i;}
    
    //This method is used to set the team/colour of a player
    public void setColour(String colour)
    {this.colour = colour;}
    
    //This method returns the team/colour of a player
    public String getColour()
    {return colour;}
    
    //This method is used to increase the moves of a player by 1
    public void addMoves()
    {moves++;}
    
    //This method returns the moves of a player
    public int getMoves()
    {return moves;}
}