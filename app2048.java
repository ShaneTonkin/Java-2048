import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.*;

public class app2048 extends Applet implements KeyListener{
  
  //The matrix to keep track of the game board
  private int[][] grid = { { 0, 0, 0, 0 }, 
    { 0, 0, 0, 0 }, 
    { 0, 0, 0, 0 },
    { 0, 0, 0, 0 } };
  
  private Rectangle background;
  
  //Storage of images
  private BufferedImage image0;
  private BufferedImage image2;
  private BufferedImage image4;
  private BufferedImage image8;
  private BufferedImage image16;
  private BufferedImage image32;
  private BufferedImage image64;
  private BufferedImage image128;
  private BufferedImage image256;
  private BufferedImage image512;
  private BufferedImage image1024;
  private BufferedImage image2048;
  private BufferedImage image4096;
  private BufferedImage image8192;
  
  //FramePaddingLeft;
  private int fPL = 44;
  //FramePaddingTop;
  private int fPT = 10;
  private int tile_padding = 5;
  private int tile_size = 70;
  
  public void loadImages(){
    try{
      image0 = ImageIO.read(new File("resources/image0.png"));
      image2 = ImageIO.read(new File("resources/image2.png"));
      image4 = ImageIO.read(new File("resources/image4.png"));
      image8 = ImageIO.read(new File("resources/image8.png"));
      image16 = ImageIO.read(new File("resources/image16.png"));
      image32 = ImageIO.read(new File("resources/image32.png"));
      image64 = ImageIO.read(new File("resources/image64.png"));
      image128 = ImageIO.read(new File("resources/image128.png"));
      image256 = ImageIO.read(new File("resources/image256.png"));
      image512 = ImageIO.read(new File("resources/image512.png"));
      image1024 = ImageIO.read(new File("resources/image1024.png"));
      image2048 = ImageIO.read(new File("resources/image2048.png"));
      image4096 = ImageIO.read(new File("resources/image4096.png"));
      image8192 = ImageIO.read(new File("resources/image8192.png"));
      
    }catch (IOException e){
      e.printStackTrace();
    }                       
  }
  
  //Select the appropriate tile to draw
  public BufferedImage getImage(int value){
    if(value == 0){
      return image0;
    }else if(value == 2){
      return image2;
    }else if(value == 4){
      return image4;
    }else if(value == 8){
      return image8;
    }else if(value == 16){
      return image16;
    }else if(value == 32){
      return image32;
    }else if(value == 64){
      return image64;
    }else if(value == 128){
      return image128;
    }else if(value == 256){
      return image256;
    }else if(value == 512){
      return image512;
    }else if(value == 1024){
      return image1024;
    }else if(value == 2048){
      return image2048;
    }else if(value == 4096){
      return image4096;
    }else{
      return image8192;
    }
  }
  
  
  public void init(){
    this.addKeyListener(this);
    
    //Create the background
    background = new Rectangle(fPL,fPT, tile_size*4 + tile_padding*5, tile_size*4 + tile_padding*5);
  
    //Insert the initial value into the grid
    insertValue(grid);
  }
  
  public void paint(Graphics g){
    Graphics2D g2 = (Graphics2D)g;
    //Loads Images, would prefer to do this only once -- needs attention
    loadImages();
    //Draws the 4 x 4 grid of squares in Black
    g2.fill(background);
    //Draw top line of tiles
    for(int i = 0; i < 4; i++){
      g2.drawImage(getImage(grid[0][i]), fPL + (i+1)*tile_padding + i*tile_size, fPT + tile_padding,
                   tile_size, tile_size, null);
    }
    //Draw 2nd line of tiles
    for(int i = 0; i < 4; i++){
      g2.drawImage(getImage(grid[1][i]), fPL + (i+1)*tile_padding + i*tile_size, fPT + 2*tile_padding+tile_size,
                   tile_size, tile_size, null);
    }
    //Draw 3rd line of tiles
    for(int i = 0; i < 4; i++){
      g2.drawImage(getImage(grid[2][i]), fPL + (i+1)*tile_padding + i*tile_size, fPT + 3*tile_padding+2*tile_size,
                   tile_size, tile_size, null);
    }
    //Draw final line of tiles
    for(int i = 0; i < 4; i++){
      g2.drawImage(getImage(grid[3][i]), fPL + (i+1)*tile_padding + i*tile_size, fPT + 4*tile_padding+3*tile_size,
                   tile_size, tile_size, null);
    }
  }
  
  public void keyPressed(KeyEvent e){
    //Create a copy of the grid before a move is made
    int gridCheck[][] = { { 0, 0, 0, 0 }, 
      { 0, 0, 0, 0 }, 
      { 0, 0, 0, 0 },
      { 0, 0, 0, 0 } };
    for(int i=0; i<grid.length; i++){
      for(int j=0; j<grid[i].length; j++){
        gridCheck[i][j]=grid[i][j];
      }
    }
    
    if(e.getKeyCode() == KeyEvent.VK_RIGHT){
      //Shift all tiles right
      mergeRows(grid, "R");
    }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
      //Shift all tiles left
      mergeRows(grid, "L");
    }else if(e.getKeyCode() == KeyEvent.VK_UP){
      //Shift all tiles up
      mergeColsUp(grid);
    }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
      //Shift all tiles down
      mergeColsDown(grid);
    }else if(e.getKeyCode() == KeyEvent.VK_R){
      //Restart the game
      for(int i = 0; i<4; i++){
        for(int j = 0; j<4; j++){
          grid[j][i] = 0;
        }
      }
      insertValue(grid);
      repaint();
      return;
    }else{
      System.out.println("Invalid Key Press: " + e.getKeyChar());
      return;
    }
    //Check to see if a valid move was made
    if(Arrays.equals(gridCheck[0], grid[0]) &&  Arrays.equals(gridCheck[1], grid[1])
         && Arrays.equals(gridCheck[2], grid[2]) && Arrays.equals(gridCheck[3], grid[3])){
      System.out.println("Invalid move");
      //Check to see if no move is possible
      mergeRows(gridCheck,"R");
      mergeRows(gridCheck,"L");
      mergeColsDown(gridCheck);
      mergeColsUp(gridCheck);
      if(Arrays.equals(gridCheck[0], grid[0]) &&  Arrays.equals(gridCheck[1], grid[1])
           && Arrays.equals(gridCheck[2], grid[2]) && Arrays.equals(gridCheck[3], grid[3])){
        System.out.println("Game Over man, game over");
        return;
      }
    }else{
      insertValue(grid);
      repaint();
    }
  }
  
  public void keyReleased(KeyEvent e){
  }
  
  public void keyTyped(KeyEvent e){
  }
  
  //Places a 2 or a 4 into an empty slot
  public static void insertValue(int[][] grid){
    //if grid is not full;
    int i = (int) (Math.random() * 4.0);
    int j = (int) (Math.random() * 4.0);
    if(i != 4 && j != 4){
      if(grid[i][j] == 0){
        if(Math.random() <= 0.1){
          grid[i][j] = 4;
        }else{ 
          grid[i][j] = 2;
        }
      }else{
        insertValue(grid); 
      }
    }
  }
  
  public static int[] mergeRowLeft(int[] grid){
    int[] newGrid = {0,0,0,0};
    for(int i = 0; i<3; i++){
      if(grid[i] == grid[i+1]){
        grid[i] += grid[i];
        grid[i+1] = 0;
      }else if(i < 2 && grid[i] == grid[i+2] && grid[i+1] == 0){
        grid[i] += grid[i];
        grid[i+2] = 0;
      }else if(i < 1 && grid[i] == grid[i+3] && grid[i+1] == 0 && grid[i+2] == 0){
        grid[i] += grid[i];
        grid[i+3] = 0;
      }
    }
    int a = 0;
    for(int i = 0; i<4; i++){
      if(grid[i] != 0){
        newGrid[a] = grid[i];
        a++;
      }
    }
    return newGrid;
  }
  
  public static int[] mergeRowRight(int[] grid){
    int[] newGrid = {0,0,0,0};
    for(int i = 3; i>0; i--){
      if(grid[i-1] == grid[i]){
        grid[i] += grid[i];
        grid[i-1] = 0;
      }else if(i > 1 && grid[i] == grid[i-2] && grid[i-1] == 0){
        grid[i] += grid[i];
        grid[i-2] = 0;
      }else if(i > 2 && grid[i] == grid[i-3] && grid[i-1] == 0 && grid[i-2] == 0){
        grid[i] += grid[i];
        grid[i-3] = 0;
      }
    }
    int a = 3;
    for(int i = 3; i>-1; i--){
      if(grid[i] != 0){
        newGrid[a] = grid[i];
        a--;
      }
    }
    return newGrid;
  }
  
  static void mergeColsUp(int[][] grid){
    int col0[] = {0,0,0,0};
    int col1[] = {0,0,0,0};
    int col2[] = {0,0,0,0};
    int col3[] = {0,0,0,0};
    
    for(int i = 0; i<4; i++){
      col0[i] = grid[i][0];
      col1[i] = grid[i][1];
      col2[i] = grid[i][2];
      col3[i] = grid[i][3];
    }
    
    col0 = mergeRowLeft(col0);
    col1 = mergeRowLeft(col1);
    col2 = mergeRowLeft(col2);
    col3 = mergeRowLeft(col3);
    
    for(int i=0; i<4; i++){
      grid[i][0] = col0[i];
      grid[i][1] = col1[i];
      grid[i][2] = col2[i];
      grid[i][3] = col3[i];
    }
  }
  
  static void mergeColsDown(int[][]grid){
    int col0[] = {0,0,0,0};
    int col1[] = {0,0,0,0};
    int col2[] = {0,0,0,0};
    int col3[] = {0,0,0,0};
    
    for(int i = 0; i<4; i++){
      col0[i] = grid[i][0];
      col1[i] = grid[i][1];
      col2[i] = grid[i][2];
      col3[i] = grid[i][3];
    }
    
    col0 = mergeRowRight(col0);
    col1 = mergeRowRight(col1);
    col2 = mergeRowRight(col2);
    col3 = mergeRowRight(col3);
    
    for(int i=0; i<4; i++){
      grid[i][0] = col0[i];
      grid[i][1] = col1[i];
      grid[i][2] = col2[i];
      grid[i][3] = col3[i];
    }
  }
  
  //Main call for merging rows either left or right depending on what key is pressed
  public static void mergeRows(int[][] grid, String direction){
    if(direction == "L"){
      for(int i = 0; i < 4; i++){
        grid[i] = mergeRowLeft(grid[i]);
      }
    }else if(direction == "R"){
      for(int i = 0; i < 4; i++){
        grid[i] = mergeRowRight(grid[i]);
      } 
    }
  }
  
  
}

