import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

//This class is used to save, load and delete games from files
public class GameManagement
{
    //This is the file location of all the saved games
    private String fileLocation = "Saved Games/";
    
    //Done by Lim Yixen and Wong Jit Chow
    //This method is used to get all the saved games and reformat it to be shown
    //Called from GameController
    public String[] getSavedGamesList()
    {
        //Open folder location of all the saved games
        File savedGames = new File(fileLocation);
        //Add all the files in the folder into an array
        String [] savedGamesList = savedGames.list();
        
        //Rename the filename properly for display
        for (int i=0; i<savedGamesList.length; i++)
        {
            savedGamesList[i] = savedGamesList[i].substring(0, savedGamesList[i].length() - 4);
            
            StringBuffer str = new StringBuffer(savedGamesList[i]);
            str.delete(14, 17);
            str.insert(2,'/');
            str.insert(5,'/');
            str.insert(10,' ');
            str.insert(13,':');
            str.insert(16,':');
            savedGamesList[i] = "Game on " + str.toString();
        } 
        
        return savedGamesList;
    }
    
    //Done by Wong Jit Chow
    //This method is used to delete a selected saved game from file
    public void deleteSavedGame(String fileName)//fileName in display state
    {
        //Change back to original filename
        fileName = fileName.replace("Game on ","");
        fileName = fileName.replace("/","");
        fileName = fileName.replace(" ","");
        fileName = fileName.replace(":","");
        
        //Delete file
        File file = new File(fileLocation + fileName + ".txt");
        file.delete();
    }
    
    //Done by Lim Yixen
    //This method is used to save all the game details into a text file and return the fileName
    //Called from GameController for the "Save" button in menubar
    public String saveGame(ArrayList<String> gameDetails,int player0Moves,int player1Moves,String currentColour)
    {
        //Save four game settings into the gameDetails ArrayList
        gameDetails.add(0,Integer.toString(player0Moves));
        gameDetails.add(1,Integer.toString(player1Moves));
        gameDetails.add(2,currentColour);
        
        Date date = new Date();//Get current time for fileName
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");  
        String fileName = sdf.format(date);
        
        //Store all game details into text file
        try
        {
            File file = new File(fileLocation + fileName + ".txt");
            file.createNewFile();
            
            //Write data into file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i=0; i<gameDetails.size(); i++)
                writer.write(gameDetails.get(i) + " ");
            writer.close();
        }
        catch(IOException e)
        {e.printStackTrace();}
        
        return fileName;
    }
    
    //Done by Wong Jit Chow
    //This method reads the content of the text file and stores them into an ArrayList
    //Called from GameController when previewing a chess board or when loading a saved game
    public ArrayList<String> getLoadGameInfo(String fileName)//fileName in display state
    {
        //Change back to original filename
        fileName = fileName.replace("Game on ","");
        fileName = fileName.replace("/","");
        fileName = fileName.replace(" ","");
        fileName = fileName.replace(":","");
        
        //Save data from file into ArrayList
        ArrayList<String> gameInfo = new ArrayList <> ();
        try
        {
            File file = new File(fileLocation + fileName + ".txt");
            Scanner s = new Scanner(file);
            while(s.hasNext())
            {gameInfo.add(s.next());}
            s.close();
        }
        catch(Exception e)
        {System.out.println("Saved game not exist!");}
        
        return gameInfo;
    }
}