package es.ieslavereda.Chess.vista;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;

public class JPFichas extends JPanel {

	
	public JPFichas(String titulo) {
		setBorder(new TitledBorder(null, titulo, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

	}

}
