import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

//Done by Bryan Yeap Yee Kuong
//This class is used to place chess on chess cell
public class ChessCell
{
    //The chess on this chess cell (might be null)
    private Chess chess;
    
    //This method returns the chess on this chess cell (might be null)
    public Chess getChess()
    {return chess;}
    
    //This method set the chess on this chess cell (might be null)
    public void setChess(Chess c)
    {chess = c;}
}