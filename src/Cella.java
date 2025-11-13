
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Cella extends Canvas {

    private int contenuto;
    private StatoCella stato;
    private final int MINA = -1;
    private int r,c;

    // Colori dei numeri
    private Color[] colori;
    
    public Cella(int dim, int r, int c) {
        setSize(dim, dim);
        this.r = r;
        this.c = c;
        this.contenuto = 0;
        this.stato = StatoCella.Coperta;

        colori = new Color[] {
            Color.white,
            Color.blue,
            new Color(70, 170, 70),   // green
            Color.red,
            new Color(0, 0, 128),     // dark blue
            new Color(150, 50, 50),   // brown
            new Color(50, 255, 200),  // aqua
            Color.darkGray,
            Color.black
        };
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // estendiamo il metodo
    }
    
    public int getContenuto(){
        return contenuto;
    }

    public int getR(){
        return r;
    }

    public int getC(){
        return c;
    }

    public boolean isScoperta(){
        return stato == StatoCella.Scoperta;
    }

    public boolean isBandiera(){
        return stato == StatoCella.Bandiera;
    }

    public void setContenuto(int contenuto){
        if(contenuto >= -1 && contenuto <= 8){
            this.contenuto = contenuto;
        }
    }

    public void toggleBandiera(){
        if(stato == StatoCella.Coperta)
            stato = StatoCella.Bandiera;
        else
            stato = StatoCella.Coperta;
        repaint();
    }

    public void setVisible(boolean mostra){
        if (mostra && stato == StatoCella.Coperta) 
            stato = StatoCella.Scoperta;
        repaint();

    }
}