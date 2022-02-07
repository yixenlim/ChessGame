import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.File;

//This class is the View part of the MVC
public class GameView
{
    private static JFrame frame;
    private static JPanel mainPanel,boardPanel,previewPanel;
    private static GameController gc = new GameController();
    
    //Done by Lim Yixen
    //This is the method that will run when program is executed
    public static void main(String[] args)
    {new GameView("New Game");}
    
    //Done by Lim Yixen
    //This is the contrusctor of GameView which opens a window to display main menu and chess board
    //Called from GameController or GameView
    public GameView(String mode)//mode = New Game/Load Game
    {
        frame = new JFrame("Webale Chess");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addComponentListener(new ComponentAdapter(){
            public void componentHidden(ComponentEvent ce) {};
            public void componentShown(ComponentEvent ce) {};
            public void componentMoved(ComponentEvent ce) {};
            public void componentResized(ComponentEvent ce)//Update the size of chess board buttons when windows is resized
            {
                //Calculate and set the button size of chess board buttons
                int currentMinimumSize = 0;
                if (frame.getHeight() < frame.getWidth())
                    currentMinimumSize = frame.getHeight();
                else
                    currentMinimumSize = frame.getWidth();
                gc.setChessButtonSize((currentMinimumSize/10) - 5);
            }
        });
        
        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);
        
        if (mode.equals("New Game"))
            displayMainMenu();
        else if (mode.equals("Load Game"))
        {
            addPlayResize();//Replace the componentListener which will refresh the chess board and game log
            displayBoardLogPanels();
        }
        
        frame.pack();
        frame.setMinimumSize(new Dimension(1000,750));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    //Done by Wong Phang Wei
    //This method is used to display the Main Menu
    //Called from GameView when program is first executed or from GameController when player is redirected from load game page to Main Menu
    public static void displayMainMenu()
    {
        mainPanel.removeAll();
        mainPanel.revalidate();
        
        JPanel mainMenuPanel = new JPanel(new GridBagLayout());
        mainMenuPanel.setBackground(new Color(1,121,111));
        mainPanel.add(mainMenuPanel);
        
        //Title of the game
        JLabel label = new JLabel("WEBALE CHESS",SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.PLAIN,30));
        
        //3 buttons in Main Menu
        JButton playButton = gc.getMainMenuButton("New Game");
        JButton loadButton = gc.getMainMenuButton("Load Game");
        JButton quitButton = gc.getMainMenuButton("Quit");
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.ipady = 40; //Increase height of the title
        c.weightx = 0.5;
        c.gridwidth = 4;
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0;
        c.gridy = 0;
        mainMenuPanel.add(label,c);  
        
        c.gridx = 0;
        c.gridy = 2;
        mainMenuPanel.add(playButton,c); 
        
        c.gridx = 0;
        c.gridy = 4;
        mainMenuPanel.add(loadButton,c);  
        
        c.gridx = 0;
        c.gridy = 6;
        mainMenuPanel.add(quitButton,c);
    }
    
    //Done by Wong Phang Wei and Wong Jit Chow
    //This method is used to setup the chess board and game log
    //Called when starting a new game or loading a saved game
    public static void displayBoardLogPanels()
    {
        mainPanel.removeAll();
        mainPanel.revalidate();
        
        //Menubar (top)
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(menuPanel,BorderLayout.NORTH);
        menuPanel.setBackground(new Color(102,205,170));
        
        //3 buttons in menubar
        JButton restartBtn = gc.getMenubarButton("Restart");
        menuPanel.add(restartBtn);
        JButton loadBtn = gc.getMenubarButton("Load Game");
        menuPanel.add(loadBtn);
        JButton saveBtn = gc.getMenubarButton("Save Game");
        menuPanel.add(saveBtn);
        JButton quitBtn = gc.getMenubarButton("Quit");
        menuPanel.add(quitBtn);
        
        //gamePanel contains chess board and game log
        JPanel gamePanel = new JPanel(new GridBagLayout());
        mainPanel.add(gamePanel);
        gamePanel.setBackground(new Color(1,121,111));
        
        //boardPanel contains chess board
        boardPanel = new JPanel(new GridBagLayout());
        boardPanel.setBackground(Color.WHITE);
        
        //logPanel contains logArea which is a JTextArea to display game log
        JPanel logPanel = new JPanel();
        JTextArea logArea = gc.getGameLog();
        JScrollPane scrollpane = new JScrollPane (logArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logPanel.add(scrollpane);
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        gamePanel.add(boardPanel,c);
        
        c.gridx = 0;
        c.gridx = 1;
        gamePanel.add(logPanel,c);
        
        //Call to display the chess board
        displayChessBoard("Play",null);
    }
    
    //Done by Lim Yixen
    //This method is used to replace the componentListener that will refresh the chess board and game log
    //Called from GameController (for New Game or Restart) or from GameView (for Load Game)
    public static void addPlayResize()
    {
        frame.addComponentListener(new ComponentAdapter(){
            public void componentHidden(ComponentEvent ce) {};
            public void componentShown(ComponentEvent ce) {};
            public void componentMoved(ComponentEvent ce) {};
            public void componentResized(ComponentEvent ce)//Update the size of chess board buttons when window is resized
            {
                //Calculate and set the button size of chess board buttons
                int currentMinimumSize = 0;
                if (frame.getHeight() < frame.getWidth())
                    currentMinimumSize = frame.getHeight();
                else
                    currentMinimumSize = frame.getWidth();
                gc.setChessButtonSize((currentMinimumSize/10) - 5);
                
				displayBoardLogPanels();//Refresh the chess board and game log
            }
        });
    }
    
    //Done by Wong Jit Chow
    //This method displays the load saved games window
    //Takes in an array of String which is the list of saved games
    //Called from GameController when player wants to load saved games
    public static void loadSavedGameWindow(String[] savedGamesList)
    {
        //Recreate a new windows
        frame.dispose();
        frame = new JFrame("Load Saved Games");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //Contains list of saved games and preview chess board
        JPanel panelListPreview = new JPanel(new GridBagLayout());
        panelListPreview.setBackground(new Color(1,121,111));
        frame.add(panelListPreview);
        
        //Contains list of saved games
        JPanel listPanel = new JPanel (new GridBagLayout());
        listPanel.setBackground(new Color(1,121,111));
        
        //Contains preview chess board
        previewPanel = new JPanel (new GridBagLayout());
        previewPanel.setBackground(new Color(1,121,111));
        
        //Load empty chessboard for preview
        displayChessBoard("Preview",null);
        
        //Create JList and load saved games in it
        JList<String> list = new JList <> (savedGamesList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//Can only select one item
		list.setFont(new Font ("ROMAN", Font.PLAIN, 18));
        list.setPreferredSize(new Dimension (300,300));
        //Add selection listener to know which saved game the player has chosen
        list.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e)
            {
                //Refresh the preview chess board when a saved gamed is selected
                if (list.getSelectedValue() != null)
                    displayChessBoard("Preview",list.getSelectedValue().toString());
            }
        });
        //Add the list into a scrollpane
        JScrollPane sp = new JScrollPane(list);
        
        //Create load button below JList
        JButton load = new JButton("Load");
        load.setFont(new Font(Font.DIALOG,Font.PLAIN,15));
        load.setBackground(Color.white);
        load.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (list.getSelectedValue() == null)//If player does not select a saved game
                    JOptionPane.showMessageDialog (null, "Please select a game to load.");
                else
                {
                    if (gc.loadConfirmation(list.getSelectedValue().toString()))//Prompt player to confirm loading
                    {
                        frame.dispose();//Close load saved game windows
                        new GameView("Load Game");
                    }
                }   
            }
        });
        
        //Create delete button below JList
        JButton delete = new JButton("Delete");
        delete.setFont(new Font(Font.DIALOG,Font.PLAIN,15));
        delete.setBackground(Color.white);
        delete.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e)
			{
                if (list.getSelectedValue() == null)//If player does not select a saved game
                    JOptionPane.showMessageDialog (null, "Please select a game to delete.");
                else
                {
                    if(gc.deleteConfirmation(list.getSelectedValue().toString()))//Prompt player to confirm deletion
                    {
                        String[] gameList = gc.getSavedGamesList();
                        if (gameList.length == 0)//After deletion no more saved games
                        {
                            frame.dispose();//Dispose load saved game windows and back to main menu
                            new GameView("New Game");
                            JOptionPane.showMessageDialog (null, "No saved games.\n You have been redirected back to main menu.");
                        }
                        else//After deletion there are still saved games
                        {
                            displayChessBoard("Preview",null);//Clear the preview chess board
                            
                            //Updates the saved games list after deletion
                            list.setListData(gameList);
                            sp.revalidate();
                            sp.repaint();
                        }
                    }
                }
            }   
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0,50,0,50);
        c.gridx=0;
        c.gridy=0;
        panelListPreview.add(listPanel,c);
        
        c.insets = new Insets(0,0,0,0);
        c.gridx=0;
        c.gridx=1;
        panelListPreview.add(previewPanel,c);
        
        c = new GridBagConstraints(); 
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        listPanel.add(sp,c);
        
        c.insets = new Insets(20,50,5,50);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        listPanel.add(load,c);
        
        c.gridy = 1;
        c.gridx = 1;
        listPanel.add(delete,c);
        
        //Create a menubar below for back to main menu
        JPanel menubar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        menubar.setBackground(new Color(1,121,111));
        frame.add(menubar,BorderLayout.SOUTH);
        
        //Button to go back to Main Menu
        JButton back = new JButton("Main Menu");
        back.setFont(new Font(Font.DIALOG,Font.PLAIN,15));
        back.setBackground(Color.white);
        back.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e)
            {
                frame.dispose();//Dispose load saved game windows and back to main menu
                new GameView("New Game");
            }
        });
        menubar.add(back);
        
        frame.addComponentListener(new ComponentAdapter(){
            public void componentHidden(ComponentEvent ce) {};
            public void componentShown(ComponentEvent ce) {};
            public void componentMoved(ComponentEvent ce) {};
            public void componentResized(ComponentEvent ce)
            {
                //Calculate and set the button size of chess board buttons
                int currentMinimumSize = 0;
                if (frame.getHeight() < frame.getWidth()-200)
                    currentMinimumSize = frame.getHeight();
                else
                    currentMinimumSize = frame.getWidth()-200;
                gc.setChessButtonSize((currentMinimumSize/10) - 5);
                
                //Refresh the preview chess board
                if (list.getSelectedValue() != null)
                    displayChessBoard("Preview",list.getSelectedValue().toString());
                else
                    displayChessBoard("Preview",null);
            }
        });
        frame.pack();
        frame.setMinimumSize(new Dimension(1200,750));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    //Done by Wong Phang Wei
    //This method is used to display chess board
    //Called during previewing chess board or during game
    public static void displayChessBoard(String mode,String selectedFile)//mode = Play/Preview
    {
        //Clear the chess board (for both preview and play)
        if (mode.equals("Play"))
        {
            boardPanel.removeAll();
            boardPanel.revalidate();
        }
        else if(mode.equals("Preview"))
        {
            gc.loadPreviewDetails(selectedFile);//Load chess onto chess board
            previewPanel.removeAll();
            previewPanel.revalidate();
        }
         
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        
        for (int i=0; i<8; i++)//row
        {
            c.gridx = 0;
            for (int j=0; j<7; j++)//column
            {
                c.gridx++;
                JButton btn = gc.getChessBoardButton(i,j,mode);
                if (mode.equals("Play"))//clickable button
                    boardPanel.add(btn,c);
                else if (mode.equals("Preview"))//unclickable button
                    previewPanel.add(btn,c);
            }
            c.gridy++;
        }
    }
}