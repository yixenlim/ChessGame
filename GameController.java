import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

//This class is the Controller part of the MVC
public class GameController implements ActionListener     
{
    //Store the chess board
    private ChessBoard board = new ChessBoard();
    //Controls the game saving, game loading and game deletion
    private GameManagement gm = new GameManagement();
    //If the game is a new game this variable will be null, else will be the file name of the saved game
    private String savedGameFileName = null;
    //Initialize the two players of the game
    private Player[] players = new Player[2];
    //The turn of player (Red/Blue)
    private Player currentPlayer;
    //Display the game log
    private JTextArea logArea;
    //Contains all the game log of the current game
    private ArrayList<String> gameLog = new ArrayList <> ();
    //Initialize the chess board buttons
    private JButton[][] buttons = new JButton[8][7];
    //Size of the chess board buttons
    private int chessButtonSize;
    
    //Done by Wong Phang Wei
    //To initialize the player 1 with colour "Red" and player 2 with colour "Blue"
    //Called from GameView during initialization when the program is executed
    public GameController()
    {
        players[0] = new Player("Red");
        players[1] = new Player("Blue");
    }
    
    //Done by Lim Yixen
    //This method is used to set size of chess buttons (for both preview and play)
    //Called from GameView for componentListener (When player resizes the windows)
    public void setChessButtonSize(int i)
    {chessButtonSize = i;}
    
    //Done by Lim Yixen
    //This method is used to get all the saved games in folder
    //Called from GameView for selection of saved games
    public String[] getSavedGamesList()
    {return gm.getSavedGamesList();}
    
    //Done by Wong Jit Chow
    //This method prints out the actions of the player
    public void printLog (String message)
    {
        //Add the log message into an ArrayList so that when frame is resized, 
        //the JTextArea will be cleared and the log messages will be reprinted.
        gameLog.add(message);
        
        //Appends the log message into the JTextArea
        logArea.append(message);
        logArea.append ("\n\n");
    }
    
    //Done by Wong Jit Chow
    //This method returns a JTextArea object with game log on it
    //Called from GameView when a new game start or when windows is resized
    public JTextArea getGameLog()
    {
        logArea = new JTextArea (chessButtonSize/3+6,20);//rows,columns (Size of the logArea)
        
        //Add recent game log into JTextArea
        for (int i=0; i<gameLog.size(); i++)
        {
            logArea.append(gameLog.get(i));
            logArea.append ("\n\n");
        }
        logArea.setEditable (false);
        logArea.setFont (new Font ("ROMAN", Font.BOLD, 15));
        
        return logArea;
    }
    
    //Done by Wong Jit Chow
    //This method is used to confirm whether a player wants to load a saved game
    //Return true if player selects "Yes" and the selected game will be loaded, false if player selects "No"
    public boolean loadConfirmation(String selectedFile)
    {
        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to load this saved game?", "Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION)
        {
            //Get all the gameinfo from file and store it in an ArrayList
            ArrayList<String> gameInfo = gm.getLoadGameInfo(selectedFile);
            
            //Initialize all the game setting of the saved game
            loadGameSetting(gameInfo);
            
            //Create a new chess board and load the chess pieces into their saved positions 
            savedGameFileName = selectedFile;
            gameLog.clear();
            return true;
        }
        return false;
    }
    
    //Done by Wong Jit Chow
    //This method is used to confirm whether a player wants to delete a saved game
    //Return true if player selects "Yes" and the selected game will be deleted, false if player selects "No"
    public boolean deleteConfirmation(String selectedFile)
    {
        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this saved game?", "Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION)
        {
            //Delete the saved game file
            gm.deleteSavedGame(selectedFile);
            return true;
        }
        return false;
    }
    
    //Done by Wong Jit Chow
    //This method loads a preview chess board for the selected saved game
    //Called from GameView when a saved game is selected when a player wants to load a saved game
    public void loadPreviewDetails(String selectedFile)
    {
        //Clear the chess board
        board = new ChessBoard();
        
        //Load the game info into chess board only if player selects a file
        if (selectedFile != null)
        {
            ArrayList<String> gameInfo = gm.getLoadGameInfo(selectedFile);
            board.loadGameBoard(gameInfo);
        }
    }
    
    //Done by Wong Jit Chow
    //This method initializes all the game setting of the saved game
    public void loadGameSetting(ArrayList<String> gameInfo)
    {
        //Index 0 and 1 is the number of moves each player have made
        players[0].setMoves(Integer.parseInt(gameInfo.get(0)));
        players[1].setMoves(Integer.parseInt(gameInfo.get(1)));
        
        //Index 2 is the String that determines which player is currently playing
        if (gameInfo.get(2).equals("Red"))
            currentPlayer = players[0];
        else
            currentPlayer = players[1];
    }
    
    //Done by Wong Phang Wei
    //This method is used to create and return the buttons in Main Menu for "New Game" "Load Game" "Quit"
    //Called from GameView when Main Menu is displayed
    public JButton getMainMenuButton(String name)
    {
        JButton btn = new JButton(name);
        btn.setPreferredSize(new Dimension(250,30));
        btn.setBackground(Color.white);
        btn.setFocusPainted(false);
        btn.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        btn.addActionListener(this);
        return btn;
    }
    
    //Done by Wong Phang Wei
    //This method is used to create and return the buttons in menubar during game play for "Restart" "Load Game" "Save Game" "Quit"
    //Called from GameView when enter a new game or saved game
    public JButton getMenubarButton(String name)
    {
        JButton btn = new JButton(name);
        btn.setBackground(Color.white);
        btn.setFocusPainted(false);
        btn.setFont(new Font(Font.DIALOG,Font.PLAIN,13));
        btn.addActionListener(this);
        return btn;
    }
    
    //Done by Sim Shin Xuan 
    //This method is used to get the chess board buttons during preview or play
    //Called from GameView when chess board is displayed/refreshed
    public JButton getChessBoardButton(int i,int j,String mode)//mode = Play/Preview
    {
        buttons[i][j] = new JButton();
        buttons[i][j].setBackground(Color.WHITE);
        buttons[i][j].setRolloverEnabled(false);
        buttons[i][j].setFocusPainted(false);
        buttons[i][j].setPreferredSize(new Dimension(chessButtonSize,chessButtonSize));//need to set the size of chess board buttons according to the windows size 
        
        ChessCell[][] cells = board.getCells();
        
        //Set the icon if there is a chess on this chess cell
        if (cells[i][j].getChess() != null)
            buttons[i][j].setIcon(cells[i][j].getChess().getChessIcon(chessButtonSize*8/10));  
        
        if (mode.equals("Play"))
            buttons[i][j].addActionListener(this);//Attach ActionListener so that chess board buttons will trigger event hen clicked
        else if (mode.equals("Preview"))
            buttons[i][j].setModel(new FixedStateButtonModel());//Make the chess board button unclickable
            
        return buttons[i][j];
    }
    
    //Done by Lim Yixen
    //This method is used to change two chess board buttons
    //Called when a chess is moved
    public void changeButton(int i,int j,char sourceI,char sourceJ)//locationIJ is source chess location,ij is destination
    {
        //Clear yellow movable chesscells
        clearValidMoves();
		
        //Change the source chess location into Integer
        int locationI = Character.getNumericValue(sourceI);
        int locationJ = Character.getNumericValue(sourceJ);
        
        //Move the chess and check whether there is any game log
        ArrayList<String> currentLog = board.moveChess(locationI,locationJ,i,j);
        if (currentLog.size() != 0)//There is a chess being killed
        {
            printLog(currentLog.get(0));
            
            if (currentLog.size() == 2)//The chess being killed is "Sun"
            {
				//Update the icon of chess board buttons
				ChessCell[][] cells = board.getCells();
				for (int a=0; a<8; a++)
				{
					for (int b=0; b<7; b++)
					{
						if (cells[a][b].getChess() == null)//If the chess cell has no chess
							buttons[a][b].setIcon(null);//Set the icon of buttons to null
						else//If the chess cell has chess
							buttons[a][b].setIcon(cells[a][b].getChess().getChessIcon(chessButtonSize*8/10));//Set the icon of buttons
					}
				}
				
                JOptionPane.showMessageDialog(null,"Congratulations!\nPlayer " + currentLog.get(1) + " won!\nYou will now be redirected back to main menu.");
                if (savedGameFileName != null)//If this game was saved
                {
                    gm.deleteSavedGame(savedGameFileName);//Delete the saved game from file (Since it ends)
                    savedGameFileName = null;//Remove saved file name
                }
                GameView.displayMainMenu();
            }
        }
		
		//Add the moves of player
        currentPlayer.addMoves();
		
        //If it is the time to change "Plus" and "Triangle"
        if (currentPlayer.getMoves() != 0 && currentPlayer.getMoves()%2 == 0 && currentPlayer.getColour().equals("Blue"))
        {
            board.changePlusTri();
			printLog(" Triangles and Pluses have\n been changed!");
        }
		
        //Switch player's turn
        if (currentPlayer == players[0])
            currentPlayer = players[1];
        else
            currentPlayer = players[0];
        
		//Check whether there is any "Arrow" reach the end
        board.checkArrow();
		
		//Flip the chess board
        board.flipBoard();
		
		//Update the icon of chess board buttons
		ChessCell[][] cells = board.getCells();
        for (int a=0; a<8; a++)
        {
            for (int b=0; b<7; b++)
            {
                if (cells[a][b].getChess() == null)//If the chess cell has no chess
                    buttons[a][b].setIcon(null);//Set the icon of buttons to null
                else//If the chess cell has chess
                    buttons[a][b].setIcon(cells[a][b].getChess().getChessIcon(chessButtonSize*8/10));//Set the icon of buttons
            }
        }
    }
	
    //Done by Sim Shin Xuan 
    //This method will set the valid moves when the user selects the chess 
    //Called when user selects a chess board button that contains his chess
    public void chessSelected(int i,int j)
    {
        //Set all cells' backgrounds to white first(in case user selects many pieces of chess)
        clearValidMoves();
        ChessCell[][] cells = board.getCells();
        
		//Get the movable coordinate of the chess selected
        ArrayList<Integer> moves = cells[i][j].getChess().getMovementStyle().getMove(i,j,cells);
        
        for (int z=0; z<moves.size();z+=2)
        {
            buttons[moves.get(z)][moves.get(z+1)].setBackground(new Color(253,253,150));//Set the movable coordinate of chess cells to yellow colour
            buttons[moves.get(z)][moves.get(z+1)].setName(Integer.toString(i) + Integer.toString(j));//Set coordinate of the chess as the name of the movable coordinate of chess cells (To track back which chess to be moved onto this chess cell)
        }
    }
    
    //Done by Sim Shin Xuan 
    //This method will set all the chess board buttons a white background
    //Called when user selects multiple chess without select destination or opponent's chess,after moving a chess and invalid moves
    public void clearValidMoves()
    {
       for (int a=0; a<8; a++)
       {
           for (int b=0; b<7; b++)
           {
               //Set the chess board buttons to white colour and clear the name of the buttons
               buttons[a][b].setBackground(Color.WHITE);
               buttons[a][b].setName(null);
           }
       } 
    }
    
    public void resetGameSetting()
    {
        currentPlayer = players[0];//Set the default turn of player (Red)
        savedGameFileName = null;//Remove savedGameFileName (If any)
        players[0].setMoves(0);//Clear the moves of players
        players[1].setMoves(0);//Clear the moves of players
        gameLog.clear();//Clear the game log of current game
    }
    
    //Done by Wong Phang Wei,Wong Jit Chow,Lim Yixen
    //This method is the ActionListener of buttons
    public void actionPerformed(ActionEvent e)
    {
        //Done by Wong Phang Wei
        //Reset the game setting and chess board when a game is started or restarted
        if (e.getActionCommand().equals("New Game") || (e.getActionCommand().equals("Restart") &&  JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,"Are you sure you want to restart?\nWarning: Current game will not be saved automatically.", "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)))
        {
            board = new ChessBoard();//Empty the chess board
            board.startNewGame();//Place the default chess onto chess board
            resetGameSetting();
            GameView.addPlayResize();//Enable the chess board and game log to be resized when windows is resized 
            GameView.displayBoardLogPanels();//Display the chess board and game log
        }
        else if (e.getActionCommand().equals("Save Game"))//Done by Lim Yixen
        {
            //If player resave the same game
            if (savedGameFileName != null)
                gm.deleteSavedGame(savedGameFileName);//Delete the current saved game
            
            //Save the game details into ArrayList and replace the savedGameFileName
            ArrayList<String> chessDetails = board.saveGameDetails();  
            //Save the game details to text file and get the new fileName
            savedGameFileName = gm.saveGame(chessDetails,players[0].getMoves(),players[1].getMoves(),currentPlayer.getColour());
            JOptionPane.showMessageDialog(null,"Current game saved.\nReminder: Game log will NOT be saved.");
        }
        else if (e.getActionCommand().equals("Load Game"))//Done by Wong Jit Chow
        {
            //Get the list of saved games
            String [] savedGamesList = gm.getSavedGamesList();
            
            if (savedGamesList.length == 0)//If there is no saved games
                JOptionPane.showMessageDialog(null,"No saved games yet.");
            else if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,"Are you sure you want to load saved games?\nWarning: Current game will not be saved automatically.", "Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE))
                GameView.loadSavedGameWindow(savedGamesList);//Opens up another windows to choose which saved game to load    
        }
        else if (e.getActionCommand().equals("Quit"))//Done by Wong Phang Wei
        {
            //Close the program after getting confirmation from player
            if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,"Are you sure you want to quit the game?\nWarning: Current game will not be saved automatically.", "Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE))
                System.exit(0);
        }
        else//Done by Lim Yixen
        {
            //ActionListener of chess board buttons
            for (int i=0; i<8; i++)
            {
                for (int j=0; j<7; j++)
                {
                    if (e.getSource() == buttons[i][j])//Locate the coordinate of the button
                    {
                        if (buttons[i][j].getName() != null)//Player wants to move a chess to this coordinate
                            changeButton(i,j,buttons[i][j].getName().charAt(0),buttons[i][j].getName().charAt(1));//change button appearance
                        else if (board.getCells()[i][j].getChess() != null)//Player selects the chess on this coordinate to move
                        {
                            if (currentPlayer.getColour().equals(board.getCells()[i][j].getChess().getColour()))//If the player selects his chess
                                chessSelected(i,j);
                            else//If the player selects opponent's chess
                            {
                                printLog(" It is opponent's chess.");
                                clearValidMoves();
                            }
                        }
                        else//Player selects an empty white chess board button
                        {
                            printLog(" Invalid move.");
                            clearValidMoves();
                        }
                    }
                }
            }
        }
    }
}

class FixedStateButtonModel extends DefaultButtonModel    
{
    public boolean isPressed() 
    {return false;}

    public boolean isRollover()
    {return false;}

    public void setRollover(boolean b) 
    {}
}