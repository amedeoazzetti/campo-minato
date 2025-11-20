
import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("Campo Minato");
        
        // creazione campo di gioco

        Campo gioco = new Campo(10, 15, 25, f.getContentPane());
        
        // aggiunta del campo di gioco al frame
        f.getContentPane().add(gioco);
        
        
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(466, 342);
        f.setLocationRelativeTo(null);
    }
}
