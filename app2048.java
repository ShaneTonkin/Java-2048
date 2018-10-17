import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
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
  
  //Visual representations of each tile
  private Rectangle rect00;
  private Rectangle rect01;
  private Rectangle rect02;
  private Rectangle rect03;
  private Rectangle rect10;
  private Rectangle rect11;
  private Rectangle rect12;
  private Rectangle rect13;
  private Rectangle rect20;
  private Rectangle rect21;
  private Rectangle rect22;
  private Rectangle rect23;
  private Rectangle rect30;
  private Rectangle rect31;
  private Rectangle rect32;
  private Rectangle rect33;
  
  private int x_topleft = 5;
  private int y_topleft = 5;
  private int tile_padding = 5;
  private int tile_size = 70;
  
  public void init(){
    this.addKeyListener(this);
    
    //Create top row of tiles
    rect00 = new Rectangle(x_topleft, y_topleft, tile_size, tile_size);
    rect01 = new Rectangle(x_topleft + tile_size + tile_padding, y_topleft, tile_size, tile_size);
    rect02 = new Rectangle(x_topleft + 2*tile_size + 2*tile_padding, y_topleft, tile_size, tile_size);
    rect03 = new Rectangle(x_topleft + 3*tile_size + 3*tile_padding, y_topleft, tile_size, tile_size);
    
    //Create 2nd row of tiles
    rect10 = new Rectangle(x_topleft, y_topleft + tile_size + tile_padding, tile_size, tile_size);
    rect11 = new Rectangle(x_topleft + tile_size + tile_padding,
                           y_topleft + tile_size + tile_padding, tile_size, tile_size);
    rect12 = new Rectangle(x_topleft + 2*tile_size + 2*tile_padding, 
                           y_topleft + tile_size + tile_padding, tile_size, tile_size);
    rect13 = new Rectangle(x_topleft + 3*tile_size + 3*tile_padding, 
                           y_topleft + tile_size + tile_padding, tile_size, tile_size);
    
    //Create 3rd row of tiles
    rect20 = new Rectangle(x_topleft, y_topleft + 2*tile_size + 2*tile_padding, tile_size, tile_size);
    rect21 = new Rectangle(x_topleft + tile_size + tile_padding,
                           y_topleft + 2*tile_size + 2*tile_padding, tile_size, tile_size);
    rect22 = new Rectangle(x_topleft + 2*tile_size + 2*tile_padding, 
                           y_topleft + 2*tile_size + 2*tile_padding, tile_size, tile_size);
    rect23 = new Rectangle(x_topleft + 3*tile_size + 3*tile_padding,
                           y_topleft + 2*tile_size + 2*tile_padding, tile_size, tile_size);
    
    //Create 4th row of tiles
    rect30 = new Rectangle(x_topleft, y_topleft + 3*tile_size + 3*tile_padding, tile_size, tile_size);
    rect31 = new Rectangle(x_topleft + tile_size + tile_padding,
                           y_topleft + 3*tile_size + 3*tile_padding, tile_size, tile_size);
    rect32 = new Rectangle(x_topleft + 2*tile_size + 2*tile_padding, 
                           y_topleft + 3*tile_size + 3*tile_padding, tile_size, tile_size);
    rect33 = new Rectangle(x_topleft + 3*tile_size + 3*tile_padding,
                           y_topleft + 3*tile_size + 3*tile_padding, tile_size, tile_size);
    
    //Insert the initial value into the grid
    insertValue(grid);
  }
  
  public void paint(Graphics g){
    Graphics2D g2 = (Graphics2D)g;
    //Draws the 4 x 4 grid of squares
    g2.fill(rect00);
    g2.fill(rect01);
    g2.fill(rect02);
    g2.fill(rect03);
    g2.fill(rect10);
    g2.fill(rect11);
    g2.fill(rect12);
    g2.fill(rect13);
    g2.fill(rect20);
    g2.fill(rect21);
    g2.fill(rect22);
    g2.fill(rect23);
    g2.fill(rect30);
    g2.fill(rect31);
    g2.fill(rect32);
    g2.fill(rect33);
    //Change colour to white and changes font for drawing the number in the square
    g2.setColor(Color.WHITE);
    g2.setFont(new Font("TimesRoman", Font.BOLD, 20));
    if(grid[0][0] != 0){
      g2.drawString(Integer.toString(grid[0][0]), 35, 45);
    }if(grid[0][1] != 0){
      g2.drawString(Integer.toString(grid[0][1]), 35 + tile_size + tile_padding, 45);
    }if(grid[0][2] != 0){
      g2.drawString(Integer.toString(grid[0][2]), 35 + 2*tile_size + 2*tile_padding, 45);
    }if(grid[0][3] != 0){
      g2.drawString(Integer.toString(grid[0][3]), 35 + 3*tile_size + 3*tile_padding, 45);
    }if(grid[1][0] != 0){
      g2.drawString(Integer.toString(grid[1][0]), 35, 45+tile_size+tile_padding);
    }if(grid[1][1] != 0){
      g2.drawString(Integer.toString(grid[1][1]), 35 + tile_size + tile_padding, 45+tile_size+tile_padding);
    }if(grid[1][2] != 0){
      g2.drawString(Integer.toString(grid[1][2]), 35 + 2*tile_size + 2*tile_padding, 45+tile_size+tile_padding);
    }if(grid[1][3] != 0){
      g2.drawString(Integer.toString(grid[1][3]), 35 + 3*tile_size + 3*tile_padding, 45+tile_size+tile_padding);
    }if(grid[2][0] != 0){
      g2.drawString(Integer.toString(grid[2][0]), 35, 45+2*tile_size+2*tile_padding);
    }if(grid[2][1] != 0){
      g2.drawString(Integer.toString(grid[2][1]), 35 + tile_size + tile_padding, 45+2*tile_size+2*tile_padding);
    }if(grid[2][2] != 0){
      g2.drawString(Integer.toString(grid[2][2]), 35 + 2*tile_size + 2*tile_padding, 45+2*tile_size+2*tile_padding);
    }if(grid[2][3] != 0){
      g2.drawString(Integer.toString(grid[2][3]), 35 + 3*tile_size + 3*tile_padding, 45+2*tile_size+2*tile_padding);
    }if(grid[3][0] != 0){
      g2.drawString(Integer.toString(grid[3][0]), 35, 45+3*tile_size+3*tile_padding);
    }if(grid[3][1] != 0){
      g2.drawString(Integer.toString(grid[3][1]), 35 + tile_size + tile_padding, 45+3*tile_size+3*tile_padding);
    }if(grid[3][2] != 0){
      g2.drawString(Integer.toString(grid[3][2]), 35 + 2*tile_size + 2*tile_padding, 45+3*tile_size+3*tile_padding);
    }if(grid[3][3] != 0){
      g2.drawString(Integer.toString(grid[3][3]), 35 + 3*tile_size + 3*tile_padding, 45+3*tile_size+3*tile_padding);
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

