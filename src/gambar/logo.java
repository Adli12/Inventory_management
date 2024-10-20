/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gambar;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Adli1
 */
public class logo extends JPanel{
    private Image image;
    public logo(){
        image = new ImageIcon(getClass().getResource("/gambar/logoo.png")).getImage();
        setOpaque(false);
    }
    
protected void paintComponent(Graphics graphics){
    super.paintComponent(graphics);
    Graphics2D gd = (Graphics2D)graphics.create();
    gd.setComposite(AlphaComposite.SrcOver);
    int desireWidth = 130;
    int desireheight = 130;
    gd.drawImage(image, 0, 0, desireWidth,
            desireheight,null);
    
    gd.dispose();
}
}
