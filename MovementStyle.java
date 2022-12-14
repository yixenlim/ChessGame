import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.File;

//Done by Bryan Yeap Yee Kuong and Sim Shin Xuan
//This interface will be used to create movement of every chess by calculating the available coordinate(s) for the chess to move
//This interface is part of the design pattern "Strategy" which we involved in this Webale Chess Game
//Every chess will have the subclass of this interface as one of the member variables, which indicates how the chess can move
public interface MovementStyle
{
    //i is the y-coordinate of the chess
    //j is the x-coordinate of the chess
    //cells is the chess cells on the chess board
    public ArrayList<Integer> getMove(int i,int j,ChessCell[][] cells);
}

//Done by Sim Shin Xuan 
//The class calculates the movement for the "Sun" Chess 
//The "Sun" Chess can only move one steps in any direction 
class SunMovement implements MovementStyle
{
    public ArrayList<Integer> getMove(int i,int j,ChessCell[][] cells)
    {
        ArrayList<Integer> moves = new ArrayList <> ();
        
        //Calculates moves to move up
        if (i-1 >= 0 && (cells[i-1][j].getChess() == null || !cells[i-1][j].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i-1);
            moves.add(j);
        }
        //Calculates moves to move down
        if (i+1 <= 7 && (cells[i+1][j].getChess() == null || !cells[i+1][j].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i+1);
            moves.add(j);
        }
        //Calculates moves to move left
        if (j-1 >= 0 && (cells[i][j-1].getChess() == null || !cells[i][j-1].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i);
            moves.add(j-1);
        }
        //Calculates moves to move right
        if (j+1 <= 6 && (cells[i][j+1].getChess() == null || !cells[i][j+1].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i);
            moves.add(j+1);
        }
        //Calculates moves to move to top right
        if (i-1 >= 0 && j+1 <= 6 && (cells[i-1][j+1].getChess() == null || !cells[i-1][j+1].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i-1);
            moves.add(j+1);
        }
        //Calculates moves to move to top left
        if (i-1 >= 0 && j-1 >= 0 && (cells[i-1][j-1].getChess() == null || !cells[i-1][j-1].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i-1);
            moves.add(j-1);
        }
        //Calculates moves to move to bottom left
        if (i+1 <= 7 && j-1 >= 0 && (cells[i+1][j-1].getChess() == null || !cells[i+1][j-1].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i+1);
            moves.add(j-1);
        }
        //Calculates moves to move to bottom right
        if (i+1 <= 7 && j+1 <= 6 && (cells[i+1][j+1].getChess() == null || !cells[i+1][j+1].getChess().getColour().equals(cells[i][j].getChess().getColour())))
        {
            moves.add(i+1);
            moves.add(j+1);
        }
        return moves;
    }
}

//Done by Bryan Yeap Yee Kuong
//The class calculates the movement for the "Chevron" Chess 
//The "Chevron" Chess can move in an L shape
class ChevronMovement implements MovementStyle
{
    public ArrayList<Integer> getMove(int i,int j,ChessCell[][] cells)
    {
        ArrayList<Integer> moves = new ArrayList <> ();
        
        if (i-2 >= 0 && j-1 >= 0)
        {
            if (cells[i-2][j-1].getChess() == null || !cells[i-2][j-1].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i-2);
                moves.add(j-1);
            }
        }
        if (i-2 >= 0 && j+1 <= 6)
        {
            if (cells[i-2][j+1].getChess() == null || !cells[i-2][j+1].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i-2);
                moves.add(j+1);
            }
        }
        if (i-1 >= 0 && j-2 >= 0)
        {
            if (cells[i-1][j-2].getChess() == null || !cells[i-1][j-2].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i-1);
                moves.add(j-2);
            }
        }
        if (i-1 >= 0 && j+2 <= 6)
        {
            if (cells[i-1][j+2].getChess() == null || !cells[i-1][j+2].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i-1);
                moves.add(j+2);
            }
        }
        if (i+1 <= 7 && j-2 >= 0)
        {
            if (cells[i+1][j-2].getChess() == null || !cells[i+1][j-2].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i+1);
                moves.add(j-2);
            }
        }
        if (i+1 <= 7 && j+2 <= 6)
        {
            if (cells[i+1][j+2].getChess() == null || !cells[i+1][j+2].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i+1);
                moves.add(j+2);
            }
        }
        if (i+2 <= 7 && j-1 >= 0)
        {
            if (cells[i+2][j-1].getChess() == null || !cells[i+2][j-1].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i+2);
                moves.add(j-1);
            }
        }
        if (i+2 <= 7 && j+1 <= 6)
        {
            if (cells[i+2][j+1].getChess() == null || !cells[i+2][j+1].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i+2);
                moves.add(j+1);
            }
        }
        return moves;
    }
}

//Done by Bryan Yeap Yee Kuong
//The class calculates the movement for the "Triangle" Chess 
//The "Triangle" Chess can move any number of steps diagonally 
class TriangleMovement implements MovementStyle
{
    public ArrayList<Integer> getMove(int i,int j,ChessCell[][] cells)
    {
        ArrayList<Integer> moves = new ArrayList <> ();
        
        //Calculates movement towards top right
        for (int x=1; x<=6; x++)
        {
            if (i-x >= 0 && j+x <= 6)
            {
                if (cells[i-x][j+x].getChess() == null || !cells[i-x][j+x].getChess().getColour().equals(cells[i][j].getChess().getColour()))
                {
                    moves.add(i-x);
                    moves.add(j+x);
                    if (cells[i-x][j+x].getChess() != null)//Cannot move further if current cell contains a chess ("Triangle" cannot skip over other pieces)
                        break;
                }
                else//Cannot move further if current cell contains own chess (Moves will not be added)
                    break;
            }
        }
        
        //Calculates movement towards top left
        for (int x=1; x<=6; x++)
        {
            if (i-x >= 0 && j-x >= 0)
            {
                if (cells[i-x][j-x].getChess() == null || !cells[i-x][j-x].getChess().getColour().equals(cells[i][j].getChess().getColour()))
                {
                    moves.add(i-x);
                    moves.add(j-x);
                    if (cells[i-x][j-x].getChess() != null)//Cannot move further if current cell contains a chess ("Triangle" cannot skip over other pieces)
                        break;
                }
                else//Cannot move further if current cell contains own chess (Moves will not be added)
                    break;
            }
        }
        
        //Calculates movement towards bottom left
        for (int x=1; x<=6; x++)
        {
            if (i+x <= 7 && j-x >= 0)
            {
                if (cells[i+x][j-x].getChess() == null || !cells[i+x][j-x].getChess().getColour().equals(cells[i][j].getChess().getColour()))
                {
                    moves.add(i+x);
                    moves.add(j-x);
                    if (cells[i+x][j-x].getChess() != null)//Cannot move further if current cell contains a chess ("Triangle" cannot skip over other pieces)
                        break;
                }
                else//Cannot move further if current cell contains own chess (Moves will not be added)
                    break;
            }
        }
        
        //Calculates movement towards bottom right
        for (int x=1; x<=6; x++)
        {
            if (i+x <= 7 && j+x <= 6)
            {
                if (cells[i+x][j+x].getChess() == null || !cells[i+x][j+x].getChess().getColour().equals(cells[i][j].getChess().getColour()))
                {
                    moves.add(i+x);
                    moves.add(j+x);
                    if (cells[i+x][j+x].getChess() != null)//Cannot move further if current cell contains a chess ("Triangle" cannot skip over other pieces)
                        break;
                }
                else//Cannot move further if current cell contains own chess (Moves will not be added)
                    break;
            }
        }
        return moves;
    }
}

//Done by Sim Shin Xuan 
//This class calculates the movement for the "Plus" chess
//The "Plus" chess can move any numbers steps of forward,backward, left and right 
class PlusMovement implements MovementStyle
{
    public ArrayList<Integer> getMove(int i,int j,ChessCell[][] cells)
    {
        ArrayList<Integer> moves = new ArrayList <> ();
        
        //Calculates moves to move upwards
        for (int i2=1;i-i2>=0; i2++)
        {
            if (cells[i-i2][j].getChess() == null || !cells[i-i2][j].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i-i2);
                moves.add(j);
                if (cells[i-i2][j].getChess() != null)//If the cells contains opponent's chess, the chess cannot move further as it cannot skip over other pieces
                    break;
            }
            else//If the cells contains own chess, the chess cannot move further and moves will not be added
                break;
        }
        
        //Calculates moves to move downwards
        for (int i2=1; i+i2<=7; i2++)
        {
            if (cells[i+i2][j].getChess() == null || !cells[i+i2][j].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i+i2);
                moves.add(j);
                if (cells[i+i2][j].getChess() != null)//If the cells contains opponent's chess, the chess cannot move further as it cannot skip over other pieces
                    break;
            }
            else//If the cells contains own chess, the chess cannot move further and moves will not be added
                break;
        }
        
        //Calculates moves to move to right
        for (int j2=1; j+j2<=6; j2++)
        {
            if (cells[i][j+j2].getChess() == null || !cells[i][j+j2].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i);
                moves.add(j+j2);
                if (cells[i][j+j2].getChess() != null)//If the cells contains opponent's chess, the chess cannot move further as it cannot skip over other pieces
                    break;
            }
            else//If the cells contains own chess, the chess cannot move further and moves will not be added
                break;
        }
        
        //Calculates moves to move left
        for (int j2=1; j-j2>=0; j2++)
        {
            if (cells[i][j-j2].getChess() == null || !cells[i][j-j2].getChess().getColour().equals(cells[i][j].getChess().getColour()))
            {
                moves.add(i);
                moves.add(j-j2);
                if (cells[i][j-j2].getChess() != null)//If the cells contains opponent's chess, the chess cannot move further as it cannot skip over other pieces
                    break;
            }
            else//If the cells contains own chess, the chess cannot move further and moves will not be added
                break;
        }
        return moves;
    }
}

//Done by Sim Shin Xuan 
//This class calculates the movement for the "Arrow" chess
//The "Arrow" chess can only move one or two steps forward each time and will turns around and heads back to the opposite direction when it reached the end of edges 
class ArrowMovement implements MovementStyle
{
    public ArrayList<Integer> getMove(int i,int j,ChessCell[][] cells)
    {
        ArrayList<Integer> moves = new ArrayList <> ();
        
        if (cells[i][j].getChess().getStatus().equals("Up"))//Calculating moves upwards
        {
            if (i-1 >=0 && (cells[i-1][j].getChess() == null || !cells[i-1][j].getChess().getColour().equals(cells[i][j].getChess().getColour())))
            {
                moves.add(i-1);
                moves.add(j);
                
                //This statement is applied only if the first cell is empty then can have second move 
                if (cells[i-1][j].getChess() == null && i-2 >= 0 && (cells[i-2][j].getChess() == null || !cells[i-2][j].getChess().getColour().equals(cells[i][j].getChess().getColour())))
                {
                    moves.add(i-2); 
                    moves.add(j);
                } 
            }
        }
        else//Calculating moves downwards
        {
            if (i+1 <= 7 && (cells[i+1][j].getChess() == null || !cells[i+1][j].getChess().getColour().equals(cells[i][j].getChess().getColour())))
            {
                moves.add(i+1);
                moves.add(j);
                
                //This statement is applied only if the first cell is empty then can have second move 
                if (cells[i+1][j].getChess() == null && i+2 <= 7 && (cells[i+2][j].getChess() == null || !cells[i+2][j].getChess().getColour().equals(cells[i][j].getChess().getColour())))
                {
                    moves.add(i+2);
                    moves.add(j);
                }
            }
        }
        return moves;
    }
}