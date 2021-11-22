
package ntpproje;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
//import sun.audio.AudioStream;


public class Oyun extends JPanel implements KeyListener, ActionListener{
   
    Timer timer = new Timer(5, this);
    Random rnd = new Random();
    
    JLabel l1,l2,l3; 
    
    Font drawFont = new Font("Arial", 30, 30){} ;
    
    private int gecen_sure = 0;
    private int kalan_sure = 10000;
    private int puan = 0;
    
    private BufferedImage kedi, kuruKafa, buyultme, kucultme;
  
    private int kediX = 0;
    private int kediY = 450;
    private int dirKediY = 30;

    private int kediBoyut = 125;

    private int buyultmeX = 3200;
    private int buyultmeY = rnd.nextInt(700);
    
    private int kuruKafaX = 2000;
    private int kuruKafaY = rnd.nextInt(700);
    
    private int sarıCisimX = 1400;
    private int sarıCisimY = rnd.nextInt(700);
    
    private int kucultmeY = rnd.nextInt(700);
    private int kucultmeX = 2600;
    
    private int sayi = rnd.nextInt(10) + 1;
    
    private int sarıBoyut = 250 - sayi*20;
    
    private int katsayı = 3;
    
    public Oyun() {
        
        l1 = new JLabel();
        add(l1);
        
        l2 = new JLabel();
        add(l2);
        
        l3 = new JLabel();
        add(l3);
        
        String ana_ses = "AnaSes.wav";
        Cal(ana_ses);
        
        try {
            
            kedi = ImageIO.read(new FileImageInputStream(new File("kedi.png")));
            kuruKafa = ImageIO.read(new FileImageInputStream(new File("bomba.png")));
            buyultme = ImageIO.read(new FileImageInputStream(new File("büyült.png")));
            kucultme = ImageIO.read(new FileImageInputStream(new File("küçült.png")));
            
        } 
        catch (IOException ex) {
            
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        
        } 
        
        this.setBackground(Color.CYAN);
        
        timer.start();
           
    }

    @Override
    public void paint(Graphics g) {
        
        super.paint(g);
        
        g.setColor(Color.red);
        g.drawRect(0, 875, 1500, 50);
        
        g.drawLine(0, 875, 1500, 875);
        
        g.drawImage(buyultme, buyultmeX, buyultmeY, 100, 100, this);

        g.drawImage(kucultme, kucultmeX, kucultmeY, 100, 100, this);
        
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 3.0F);

        g.drawImage(kedi, kediX, kediY, kediBoyut, kediBoyut, this);
        
        g.setColor(Color.YELLOW);
        g.fillOval(sarıCisimX, sarıCisimY, sarıBoyut, sarıBoyut);
        
        g.setColor(Color.BLUE);
        g.setFont(newFont);
  
        g.drawString(sayi + "", sarıCisimX+(sarıBoyut/2)-9, sarıCisimY+(sarıBoyut/2)+12);
        
        g.drawImage(kuruKafa,kuruKafaX, kuruKafaY, 100, 100, this);
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int c = e.getKeyCode();
        
        if (c == KeyEvent.VK_UP) {
            
            if (kediY >= 0) {
                
                kediY -= dirKediY;
                repaint();
                
            }
        }
                
        if (c == KeyEvent.VK_DOWN){
            
            if (kediY < 700) {
                
                kediY += dirKediY;
                repaint();
                
            }
        }      
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        gecen_sure += 5;
        kalan_sure -= 5;

        if (kalan_sure == 0){
            
            timer.stop();
            
            JOptionPane.showMessageDialog(this, "OYUN BİTTİ \n"
                                        + "Geçen süre:"+ gecen_sure/1000.0 +"\n"
                                        + "Puan: "+ puan);
            
        }
        
        kontrolBuyultme();
        
        if(katsayı >=5){
            
            katsayı = 5;
            kediBoyut = 175;
        }
        
        if(kontrolBuyultme()){
            
            katsayı++;
            kediBoyut += 25;
            
        }
        
        if (buyultmeX <= 124){
            
            buyultmeY = rnd.nextInt(700);
            buyultmeX = 3200;
            
        }
        
        buyultmeX -= 4;
        
        if(katsayı <=1){
            
            katsayı = 1;
            kediBoyut = 75;
            
        }
        
        kontrolKucultme();
        
        if(kontrolKucultme()){
           
           katsayı--;
           kediBoyut -= 25;
 
        }
        
        if (kucultmeX <= 124){
           
            kucultmeY = rnd.nextInt(700);
            kucultmeX = 2600;
            
        }
        
        kucultmeX -= 4;
           
        kontrolSarıCisim();
        
        if (kontrolSarıCisim()) {
            
            sarıCisimY = rnd.nextInt(700);
            sarıCisimX = 1400;
            
        }
        
        if (sarıCisimX <= 0) {
            
            sayi = rnd.nextInt(10) + 1;
            
            if (sayi == 10) {

                sarıBoyut = 50;
                repaint();
            }
            else{
                
                sarıCisimBoyut(sayi);
                
            }
            
            sarıCisimY = rnd.nextInt(700);
            sarıCisimX = 1400;
            
        }
        
        sarıCisimX -= sayi;
       
        String sure = "Kedinin Ömrü : "+ kalan_sure/1000;
        
        l1.setText(sure);
        l1.setLocation(0, 850);
        l1.setSize(300, 100);
        l1.setFont(drawFont);
        
        l2.setLocation(650, 850);
        l2.setSize(200, 100);
        l2.setFont(drawFont);
        l2.setText("Puanınız: " + puan);
        
        l3.setLocation(1200, 825);
        l3.setSize(300, 150);
        l3.setFont(drawFont);
        
        String gecenSure = "Geçen Süre : " + gecen_sure/1000 + " (s)";
        l3.setText(gecenSure);
        
        kontrolKuruKafa();
        
        if (kontrolKuruKafa()) {
            
            Cal("ses.wav");
            
            timer.stop();
            
            JOptionPane.showMessageDialog(this, "OYUN BİTTİ \n"
                                        + "Geçen süre:"+ gecen_sure/1000.0 +"\n"
                                        + "Puan: "+ puan);
            
        }
        if (kuruKafaX <= 0) {
            
            int yeniY = rnd.nextInt(700);
            kuruKafaY = yeniY;
            kuruKafaX = 2000;
            
        }
        
        kuruKafaX -= 4;
        
        repaint();
    }
    
    public boolean kontrolKuruKafa(){
        
        if (new Rectangle(kediX, kediY, kediBoyut, kediBoyut).intersects(new Rectangle(kuruKafaX, kuruKafaY, 30, 30))) {
            
            return true;
        }
        
        return false;
    
    }
    
    
    public boolean kontrolBuyultme(){
        
        if (new Rectangle(buyultmeX, buyultmeY, 100, 100).intersects(new Rectangle(kediX, kediY, 125, 125))) {
                
            String buyume_ses = "büyültme.wav";
            Cal(buyume_ses);
            
            return true;
        }
        
        return false;
        
    }
    
    public boolean kontrolKucultme(){
        
        if (new Rectangle(kucultmeX, kucultmeY, 100, 100).intersects(new Rectangle(kediX, kediY, 125, 125))) {
            
            String kucultme_ses = "küçültme.wav";
            Cal(kucultme_ses);

            return true;
        }
        
        return false;
          
    }
    
    public boolean kontrolSarıCisim(){
        
        if (new Rectangle(sarıCisimX,sarıCisimY, 150, 150).intersects(new Rectangle(kediX, kediY, 125, 125))) {
           
            puan += sayi;
            
            String sarı = "sarıtop.wav";
            Cal(sarı);
            
            String getPuan = "Puanınız : "+ puan;
            l2.setText(getPuan);
 
            sarıCisimX = 1400;
            sarıCisimY = rnd.nextInt(700);
            kalan_sure += sayi*1000;
            
            sayi = rnd.nextInt(10) +1;
            sarıCisimBoyut(sayi);
            
            return true;    
        }
        
        return false;
    
    }
    
    public void sarıCisimBoyut(int n){
        
        sarıBoyut = 250 - sayi*20;
        
    }
    
    public static void Cal(String path){

    try{
        
        InputStream muzikal = new FileInputStream(path);
   //     AudioStream muzikstreamet = new AudioStream(muzikal);
   //     AudioPlayer.player.start(muzikstreamet);
        
    }
    
    catch (Exception e){

        System.out.println("Oluşan Hata: "+ e.getMessage());
        
   } 
    
  } 
    
}
 
