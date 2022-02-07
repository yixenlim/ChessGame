import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.File;

//This class is used to place 56 chess cells
public class ChessBoard
{
    private ChessFactory factory = new ChessFactory();
    private ChessCell[][] cells;
    
    //Done by Sim Shin Xuan
    //This method is used to call the ChessFactory to remove all the chess it holds then recreate a chess board with empty chess cells
    //Called from GameController when start/restart or load a game
    public ChessBoard()
    {
        factory.removeAllChess();
        cells = new ChessCell[8][7];
        
        for (int i=0; i<8; i++)//row
        {
            for (int j=0; j<7; j++)//column
                cells[i][j] = new ChessCell();
        }
    }
    
    //Done by Sim Shin Xuan 
    //This method returns all the chess cells of chess board
    public ChessCell[][] getCells()
    {return cells;}
    
    //Done by Bryan Yeap Yee Kuong
    //This method is used to initialize the default placement of chess on the chess board
    //Called from GameController when a new game starts or restarts
    public void startNewGame()
    {
        //Name of chess
        String[] initChess = {"Plus","Triangle","Chevron","Sun","Chevron","Triangle","Plus","Arrow","Arrow","Arrow","Arrow"};
        //Coordinate(in pairs) of the default chess
        int[] initChessBlue = {0,0,0,1,0,2,0,3,0,4,0,5,0,6,1,0,1,2,1,4,1,6};
        int[] initChessRed = {7,0,7,1,7,2,7,3,7,4,7,5,7,6,6,0,6,2,6,4,6,6};
        
        //Place the chess on the chess board
        for (int i=0,j=0; i<initChess.length; i++,j+=2)
        {
			cells[initChessBlue[j]][initChessBlue[j+1]].setChess(factory.createChess(initChessBlue[j],initChessBlue[j+1],"Blue",initChess[i]));
			cells[initChessRed[j]][initChessRed[j+1]].setChess(factory.createChess(initChessRed[j],initChessRed[j+1],"Red",initChess[i]));
		}
    }
    
		//Done by Lim Yixen
    //This method is used to flip the chess board by switching two chess diagonally
    //Called from GameController when switching player's turn
    public void flipBoard()
    {
        for (int i=0; i<4; i++)
        {
            for (int j=0; j<7; j++)
            {
                //Switch the chess on diagonal chess cell
                Chess c = cells[i][j].getChess();
                Chess c2 = cells[7-i][6-j].getChess();
                cells[i][j].setChess(c2);
                cells[7-i][6-j].setChess(c);
                
                //Update the coordinate of the chess
                if (c != null)
                {
                    c.setI(7-i);
                    c.setJ(6-j);
					//Switch the status (Up/Down) of chess "Arrow" "Triangle" "Chevron" since the chess board is flipped
                    if (c.getName().equals("Arrow") || c.getName().equals("Triangle") || c.getName().equals("Chevron"))
                        c.changeStatus();
                }
                if (c2 != null)
                {
                    c2.setI(i);
                    c2.setJ(j);
					//Switch the status (Up/Down) of chess "Arrow" "Triangle" "Chevron" since the chess board is flipped
                    if (c2.getName().equals("Arrow") || c2.getName().equals("Triangle") || c2.getName().equals("Chevron"))
                        c2.changeStatus();
                }
            }
        }
    }
    
    //Done by Lim Yixen
    //This method is used to get the coordinate(s) of "Plus" and "Triangle"
    //Called from GameController to change the chess board buttons of "Plus" and "Triangle"
    public void changePlusTri()
    {
        ArrayList<Chess> toBeChanged = factory.removeCurrentPlusTri();//remove the current "Plus" and "Triangle" from chess factory and store in ArrayList
        
        //Replace the "Plus" as "Triangle" on chess cell and vice versa
        for (int a=0; a<toBeChanged.size(); a++)
        {
			int i = toBeChanged.get(a).getI();
			int j = toBeChanged.get(a).getJ();
			
            if(toBeChanged.get(a).getName().equals("Triangle"))
				cells[i][j].setChess(factory.createChess(i,j,toBeChanged.get(a).getColour(),"Plus"));
            else
			{
				cells[i][j].setChess(factory.createChess(i,j,toBeChanged.get(a).getColour(),"Triangle"));
				cells[i][j].getChess().changeStatus();//Current status is inverse with initial status, need to change to current status
			}
        }
    }
    
    //Done by Lim Yixen
    //This method is used to move a chess to destination
    //Called from GameController in order to change the chess board buttons
    public ArrayList<String> moveChess(int locationI,int locationJ,int i,int j)//locationIJ is source chess coordinate, ij is the destination coordinate
    {
        ArrayList<String> currentLog = new ArrayList <> ();
        
        if (cells[i][j].getChess() != null)//If destination has opponent chess
        {
            currentLog.add(cells[i][j].getChess().getKilledLabel());//Add the killed label into currentLog
            factory.removeChess(cells[i][j].getChess());//Remove the opponent chess from chess factory
            if (cells[i][j].getChess().getName().equals("Sun"))//If the chess killed is "Sun"
                currentLog.add(cells[locationI][locationJ].getChess().getColour());//Add the colour of the winner into currentLog
        }
        
        //Update the moved chess location in chess factory
        cells[locationI][locationJ].getChess().setI(i);
        cells[locationI][locationJ].getChess().setJ(j);
        
        //Move the chess to destination chess and empty the source chess coordinate
        cells[i][j].setChess(cells[locationI][locationJ].getChess());
        cells[locationI][locationJ].setChess(null);
        if (cells[i][j].getChess().getName().equals("Arrow"))//If the chess moved is "Arrow"
            cells[i][j].getChess().setReachEnd(false);//Set reachEnd = false (since it moved)
        
        return currentLog;
    }
    
    //Done by Lim Yixen
    //This method is used to get chess properties from Chess Factory
    //Called from GameController when a game is saved
    public ArrayList<String> saveGameDetails()
    {return factory.getAllChessProperties();}
    
    //Done by Lim Yixen
    //This method is used to check whether two ends have any "Arrow"
    //Called from GameController after a chess is moved
    public void checkArrow()
    {
        for (int i=0; i<8; i+=7)//Only check both ends
        {
            for (int j=0; j<7; j++)
            {
                //If it is "Arrow" at the end and it is the first time it reaches the end (reachEnd == false)
                if (cells[i][j].getChess() != null && cells[i][j].getChess().getName().equals("Arrow") && !cells[i][j].getChess().getReachEnd())
                {
                    cells[i][j].getChess().setReachEnd(true);
                    cells[i][j].getChess().changeStatus();//Reverse the status
                }
            }
        }
    }
    
    //Done by Wong Jit Chow
    //This method is used to place chess on chess board based on the saved game info
    //Called from GameController when previewing or loading a saved game
    public void loadGameBoard(ArrayList<String> gameInfo)
    {
        for (int c=3; c<gameInfo.size(); c+=4)
        {
            //Coordinate of a chess
            String i = gameInfo.get(c);
            String j = gameInfo.get(c+1);
            
            //Colour of the chess, either "Red" or "Blue"
            String colour = gameInfo.get(c+2);
            
            //Name of the Chess ("Sun" "Chevron" etc...)
            String name = gameInfo.get(c+3);
            
            //Convert coordinate of chess into Integer
            int locationI = Integer.parseInt(i);
            int locationJ = Integer.parseInt(j);
            
            //Place the chess on the chessboard
			cells[locationI][locationJ].setChess(factory.createChess(locationI,locationJ,colour,name));
            
            //Set chess status is Up/Down
            if(name.equals("Triangle") || name.equals("Chevron") || name.equals("Arrow"))
            {
                cells[locationI][locationJ].getChess().setStatus(gameInfo.get(c+4));
                if (name.equals("Arrow") && (locationI == 0 || locationI == 7))
                    cells[locationI][locationJ].getChess().setReachEnd(true);
                c++;
            }
        }   
    }
}