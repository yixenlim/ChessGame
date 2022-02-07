import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

//Done by Lim Yixen
//This class can be used to create different chess
public class Chess
{
    private MovementStyle ms;//The movement style of the chess
    private String name,colour;//Name and colour (Red/Blue) of the chess
    private int i,j;//Coordinate of the chess
    
    //Only "Arrow" "Triangle" "Chevron" have this to indicate which chess icon should be used (Up/Down)
    private String status;
    
    //Only "Arrow" has this
    //If the "Arrow" reaches one of the end, this will be set to true(to indicate whether there is a need to change the status)
    //Once the "Arrow" move away from the end, this will be set to false, else it will remain true
    private boolean reachEnd;
    
    public ImageIcon getChessIcon(int size)
    {
		//Get the icon from the folder
        ImageIcon image;
        if (name.equals("Sun") || name.equals("Plus"))
            image = new ImageIcon("Icons/" + colour + name + ".png");  
        else
            image = new ImageIcon("Icons/" + status + colour + name + ".png");
		
		//Resize the icon
        Image scaledImage = image.getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    
    public void setStatus(String s)
    {status = s;}
    
    public void changeStatus()
    {
        if (status.equals("Up"))
            setStatus("Down");
        else if (status.equals("Down"))
            setStatus("Up");
    }
    
    public String getStatus()
    {return status;}
    
    public void setReachEnd(boolean b)
    {reachEnd = b;}
    
    public boolean getReachEnd()
    {return reachEnd;}
    
    public void setI(int i)
    {this.i = i;}
    
    public int getI()
    {return i;}
    
    public void setJ(int j)
    {this.j = j;}
    
    public int getJ()
    {return j;}
    
    public void setName(String name)
    {this.name = name;}
    
    public String getName()
    {return name;}
    
    public void setColour(String colour)
    {this.colour = colour;}
    
    public String getColour()
    {return colour;}
    
    public void setMovementStyle(MovementStyle ms)
    {this.ms = ms;}
    
    public MovementStyle getMovementStyle()
    {return ms;}
    
	//Returns the message if the chess is killed
    public String getKilledLabel()
    {return " " + colour + " " +  name + " has been killed!";}
}

//Done by Bryan Yeap Yee Kuong
//This class is used to create the chess "Sun"
class Sun extends Chess
{
    public Sun(String colour,int i,int j)
    {
        setMovementStyle(new SunMovement());//Set the movement of "Sun"
        setColour(colour);//Set the colour (Red/Blue)
        setName("Sun");//Set name to "Sun"
        
		//Set the coordinate
        setI(i);
        setJ(j);
    }
}

//Done by Bryan Yeap Yee Kuong
//This class is used to create the chess "Chevron"
class Chevron extends Chess
{
    public Chevron(String colour,int i,int j)
    {
        setMovementStyle(new ChevronMovement());//Set the movement of "Chevron"
        setColour(colour);//Set the colour (Red/Blue)
        setName("Chevron");//Set the name to "Chevron"
        
		//Set the status (Up/Down)
        if (colour.equals("Blue"))
            setStatus("Down");
        else if (colour.equals("Red"))
            setStatus("Up");
        
		//Set the coordinate
        setI(i);
        setJ(j);
    }
}

//Done by Bryan Yeap Yee Kuong
//This class is used to create the chess "Triangle"
class Triangle extends Chess
{
    public Triangle (String colour,int i,int j)
    {
        setMovementStyle(new TriangleMovement());//Set the movement of "Triangle"
        setColour(colour);//Set the colour (Red/Blue)
        setName("Triangle");//Set the name to "Triangle"
        
		//Set the status (Up/Down)
        if (colour.equals("Blue"))
            setStatus("Down");
        else if (colour.equals("Red"))
            setStatus("Up");
        
		//Set the coordinate
        setI(i);
        setJ(j);
    }
}

//Done by Bryan Yeap Yee Kuong
//This class is used to create the chess "Plus"
class Plus extends Chess
{
    public Plus(String colour,int i,int j)
    {
        setMovementStyle(new PlusMovement());//Set the movement of "Plus"
        setColour(colour);//Set the colour (Red/Blue)
        setName("Plus");//Set the name to "Plus"
        
		//Set the coordinate
        setI(i);
        setJ(j);
    }
}

//Done by Bryan Yeap Yee Kuong
//This class is used to create the chess "Arrow"
class Arrow extends Chess
{
    public Arrow(String colour,int i,int j)
    {
        setMovementStyle(new ArrowMovement());//Set the movement of "Arrow"
        setColour(colour);//Set the colour (Red/Blue)
        setName("Arrow");//Set the name to "Arrow"
        setReachEnd(false);//Set reachEnd to false (the defualt coordinate is not end of the chess board)
        
		//Set the status (Up/Down)
        if (colour.equals("Blue"))
            setStatus("Down");
        else if (colour.equals("Red"))
            setStatus("Up");
        
		//Set the coordinate
        setI(i);
        setJ(j);
    }
}