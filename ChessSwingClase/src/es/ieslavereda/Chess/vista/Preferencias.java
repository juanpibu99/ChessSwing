package es.ieslavereda.Chess.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.ieslavereda.Chess.config.MyConfig;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Preferencias extends JFrame {

	private JPanel contentPane;
	private JButton btnColorCeldaBlanca;
	private JButton btnColorCeldaNegra;
	private JButton btnColorBordeCelda;
	private JButton btnColorBordeCeldaComer;

	/**
	 * Create the frame.
	 */
	public Preferencias() {
		setTitle("Preferencias");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[198.00][]", "[][25.00][25][25][25][][]"));
		
		JLabel lblColorCeldaBlanca = new JLabel("Color Celda Blanca");
		contentPane.add(lblColorCeldaBlanca, "cell 0 1");
		
		btnColorCeldaBlanca = new JButton("");
		btnColorCeldaBlanca.setBackground(new Color(MyConfig.getInstancia().getWhiteCellColor()));
		contentPane.add(btnColorCeldaBlanca, "cell 1 1,alignx center");
		
		JLabel lblColorCeldaNegra = new JLabel("Color Celda Negra");
		contentPane.add(lblColorCeldaNegra, "cell 0 2");
		
		btnColorCeldaNegra = new JButton("");
		btnColorCeldaNegra.setBackground(new Color(MyConfig.getInstancia().getBlackCellColor()));
		contentPane.add(btnColorCeldaNegra, "cell 1 2,alignx center");
		
		JLabel lblColorBordeCelda = new JLabel("Color Borde Celda");
		contentPane.add(lblColorBordeCelda, "cell 0 3");
		
		btnColorBordeCelda = new JButton("");
		btnColorBordeCelda.setBackground(new Color(MyConfig.getInstancia().getBorderNormalCell()));
		contentPane.add(btnColorBordeCelda, "cell 1 3,alignx center");
		
		JLabel lblColorBordeCelda_1 = new JLabel("Color Borde Celda Comer");
		contentPane.add(lblColorBordeCelda_1, "cell 0 4");
		
		btnColorBordeCeldaComer = new JButton("");
		btnColorBordeCeldaComer.setBackground(new Color(MyConfig.getInstancia().getBorderKillCell()));
		contentPane.add(btnColorBordeCeldaComer, "cell 1 4,alignx center");
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btnCerrar, "cell 1 6");
	}

	public JButton getBtnColorCeldaBlanca() {
		return btnColorCeldaBlanca;
	}

	public JButton getBtnColorCeldaNegra() {
		return btnColorCeldaNegra;
	}

	public JButton getBtnColorBordeCelda() {
		return btnColorBordeCelda;
	}

	public JButton getBtnColorBordeCeldaComer() {
		return btnColorBordeCeldaComer;
	}

}
