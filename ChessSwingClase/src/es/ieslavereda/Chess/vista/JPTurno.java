package es.ieslavereda.Chess.vista;

import javax.swing.JPanel;

import es.ieslavereda.Chess.model.common.Color;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class JPTurno extends JPanel {

	
	private Color turno;
	private JLabel lblTurn;
	private JLabel lblSelected;
	
	public JPTurno() {
		setBorder(new TitledBorder(null, "TURN", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panelTurn = new JPanel();
		add(panelTurn);
		panelTurn.setLayout(new MigLayout("", "[grow,center]", "[][grow,center]"));
		
		JLabel lblNewLabel = new JLabel("Move");
		panelTurn.add(lblNewLabel, "cell 0 0,alignx center,aligny center");
		
		lblTurn = new JLabel("");
		lblTurn.setIcon(new ImageIcon(JPTurno.class.getResource("/es/ieslavereda/Chess/recursos/b_peon.gif")));
		panelTurn.add(lblTurn, "cell 0 1,alignx center,aligny center");
		
		JPanel panelSelected = new JPanel();
		add(panelSelected);
		panelSelected.setLayout(new MigLayout("", "[grow,center]", "[][grow,center]"));
		
		JLabel lblMove = new JLabel("Selected");
		panelSelected.add(lblMove, "cell 0 0,alignx center,aligny center");
		
		lblSelected = new JLabel("");
		panelSelected.add(lblSelected, "cell 0 1,alignx center,aligny center");
		turno = Color.WHITE;
		
		
	}
	
	public JLabel getJLabelPieza() {
		return lblSelected;
	}
	
	public void cambioTurno() {
		turno = Color.values()[(turno.ordinal()+1)%2];
		if(turno==Color.WHITE) {
			lblTurn.setIcon(new ImageIcon(JPTurno.class.getResource("/es/ieslavereda/Chess/recursos/b_peon.gif")));
		}else {
			lblTurn.setIcon(new ImageIcon(JPTurno.class.getResource("/es/ieslavereda/Chess/recursos/n_peon.gif")));
		}
	}
	
	public Color getTurno() {
		return turno;
	}
}
