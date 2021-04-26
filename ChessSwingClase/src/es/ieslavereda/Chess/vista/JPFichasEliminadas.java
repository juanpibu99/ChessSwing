package es.ieslavereda.Chess.vista;

import javax.swing.JPanel;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class JPFichasEliminadas extends JPanel {
	
	private JPanel panelBlancas;
	private JPanel panelNegras;

	
	public JPFichasEliminadas() {
		setLayout(new GridLayout(2, 0, 0, 0));
		
		panelBlancas = new JPFichas("WHITE");
		add(panelBlancas);
		
		panelNegras = new JPFichas("BLACK");
		add(panelNegras);

	}


	public JPanel getPanelBlancas() {
		return panelBlancas;
	}


	public JPanel getPanelNegras() {
		return panelNegras;
	}
	


}
