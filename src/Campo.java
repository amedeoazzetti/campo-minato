
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Campo extends JPanel {

    private Cella[][] campo;

    private final int mine;
    private int nBandiere = 0;

    private final int MINA = -1;

    public Campo(int righe, int colonne, int maxMine, Container content) {
        setPreferredSize(new Dimension(450, 300));
        campo = new Cella[righe][colonne];
        this.mine = maxMine;

        // gestire input delle celle 
        for (int r = 0; r < campo.length; r++) {
            for (int c = 0; r < campo[0].length; c++) {

                // instanzinare la cella e posizionarle a schermo
                campo[r][c] = new Cella(30, r, c);
                content.add(campo[r][c]);
                campo[r][c].setLocation(campo[r][c].getWidth() * c, campo[r][c].getHeight() * r);

                campo[r][c].addMouseListener(new MouseAdapter() {

                    // usiamo una classe anonima perche dobbiamo inplementare diversi metodi astratti
                    // Non e' possibile usare una lamda function!!
                    @Override
                    public void mouseReleased(MouseEvent e) {

                        Cella cliccata = (Cella) e.getSource();

                        // button1: tasto sinistro
                        // button2: tasto rotellina
                        // button3: tasto destro
                        switch (e.getButton()) {
                            case MouseEvent.BUTTON1:
                                // scopro la cella(...)
                                scoprieCella(cliccata.getR(), cliccata.getC());
                                break;
                            case MouseEvent.BUTTON3:
                                if (cliccata.isScoperta()) {
                                    break;
                                }

                                if (!cliccata.isBandiera() && nBandiere == mine) {
                                    JOptionPane.showMessageDialog(null, "Hai usato tutte le bandiere");
                                }
                                cliccata.toggleBandiera();
                                // se sono bandiera, incrementa, se coperta decrementa
                                nBandiere += cliccata.isBandiera() ? +1 : -1;

                                break;
                        }

                        super.mouseReleased(e);

                    }
                });
            }
        }

        // generare la posizione delle mine
        generaMine();

        //  imposta gli indizi
        contaIndizi();

    }

    /**
     * Posizionare le mine casualmente nelle celle
     */
    private void generaMine() {
        Random rand = new Random();

        int minePosizionate = 0;

        // Continua a posizionare mine finché non raggiungiamo il numero massimo
        while (minePosizionate < mine) {
            int riga = rand.nextInt(campo.length); // Seleziona una riga casuale
            int colonna = rand.nextInt(campo[0].length); // Seleziona una colonna casuale

            // Se la cella non contiene già una mina, posiziona una
            if (campo[riga][colonna].getContenuto() != MINA) {
                campo[riga][colonna].setContenuto(MINA);
                minePosizionate++;
            }
        }

    }

    /**
     * Analizzare le celle adiancenti contanto le mine, e imposta gli indizi
     * numerici.
     */
    private void contaIndizi() {
        int mineContato;
        for (int r = 0; r < campo.length; r++) {
            for (int c = 0; c < campo.length; c++) {

                mineContato = 0;

                // se e' una mina, la salto
                if(campo[r][c].getContenuto() == MINA){
                    continue; // salta le mine
                }
                
                // scorro le adiacenti 3x3
                for(int riga = r-1; riga <= r+1; riga++) {
                    for(int col = c-1; col <= c+1; col++){
                        // controllare la validita delle cella adiacenti 
                        try {
                          if(campo[riga][col].getContenuto() == MINA) {
                              mineContato++;
                          }
                        }catch ( ArrayIndexOutOfBoundsException e){
                              // cella non valida, salto
                        } 
                    }
                    campo[r][c].setContenuto(mineContato);
                }
            }
        }
    }
    
    /**
     * Analizza e scopre le celle adiacenti se vuote
     *
     * @param r riga della cella di partenza
     * @param c colonna della cella di partenza
     */
    public void scoprieCella(int r, int c) {
        // clausola di chiusura
        
        if (campo[r][c].isBandiera()) {
            // cella non valida
            return;
        }
        campo[r][c].setVisible(true);

        // caso base
        Cella cella = campo[r][c];
        if (cella.isScoperta() || cella.isBandiera()) {
            return; // cella gia' scoperta o con bandiera
        }

        // controllo se ho perso
        if (cella.getContenuto() ==MINA){
            JOptionPane.showMessageDialog(null, "Game Over");
            System.exit(0);
        }

        // clausola di chiusura 
        if(cella .getContenuto() > 0) {
            return;
        }

        // chiamata ricorsiva 
        for(int riga = r-1; riga <= r+1; riga++){
            for(int col = c-1; col <= c+1; col++){

                // salto la cella attuale
                if (riga == r && col == c)  {
                    continue;
                }

                // controllo se le celle adiacenti sono coperte
                try{
                    if(!campo[riga][col].isScoperta()){
                        scoprieCella(riga, col);
                    }
                } 
                catch(ArrayIndexOutOfBoundsException e) {/* salto la cella  */}
            }
        }
    }
}
