
package ntpproje;

import javax.swing.JFrame;

public class OyunEkranı extends JFrame{

    public OyunEkranı(String string)  {
        super(string);
    }

    public static void main(String[] args) {
        
        OyunEkranı ekran = new OyunEkranı("Oyun");
        
        ekran.setResizable(false);
        ekran.setFocusable(false);
        ekran.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ekran.setSize(1500, 950);
        
        Oyun oyun = new Oyun();
        oyun.requestFocus();
        oyun.addKeyListener(oyun);
        oyun.setFocusable(true);
        oyun.setFocusTraversalKeysEnabled(false);
        
        ekran.add(oyun);
        
        ekran.setVisible(true); 
           
    }
    
}
