import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Images extends Applet{
 
  public BufferedImage image2;
  public BufferedImage image4;
  public BufferedImage image8;
  public BufferedImage image16;
  public BufferedImage image32;
  public BufferedImage image64;
  public BufferedImage image128;
  public BufferedImage image256;
  public BufferedImage image512;
  public BufferedImage image1024;
  public BufferedImage image2048;
  public Rectangle rect;
  
  public void loadImages(){
    try{
      image2 = ImageIO.read(new File("resources/image2.png"));
      System.out.println("Found image2.png");
    }catch(IOException e){
      e.printStackTrace();
    }
  }
    
  
  public void init(){
    loadImages();
  }
  
  public void paint(Graphics g){
    loadImages();
    Graphics2D g2 = (Graphics2D)g;
    g2.drawImage(image2, 5, 10, 50, 50, null);
    g2.drawImage(image2, 60, 10, 50, 50, null);
    g2.drawImage(image2, 115, 10, 50, 50, null);
    g2.drawImage(image2, 170, 10, 50, 50, null);
  }
  
  
}