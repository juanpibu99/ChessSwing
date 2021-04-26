package es.ieslavereda.Chess.model.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import java.awt.GridLayout;

public class JPTablero extends JPanel {

	private HashMap<Coordenada, Celda> tablero;
	private ArrayList<Pieza> blancas;
	private ArrayList<Pieza> negras;
	private Pieza blackKing;
	private Pieza whiteKing;

	public JPTablero() {
		super();
		setBounds(new Rectangle(0, 0, 500, 500));
		setLayout(new GridLayout(10, 10, 0, 0));

		tablero = new HashMap<Coordenada, Celda>();
		blancas = new ArrayList<>();		
		negras = new ArrayList<>();

		inicializar();
	}

	private void inicializar() {

		// Inicializamos el tablero
		for (int fila = 0; fila < 8; fila++) {
			for (int col = 0; col < 8; col++)
				tablero.put(new Coordenada((char) ('A' + col), 1 + fila), new Celda());
		}

		whiteKing = new King(Color.WHITE, new Coordenada('E', 1), this);
		blancas.add(whiteKing);

		blancas.add(new Rook(Color.WHITE, new Coordenada('A', 1), this));
		blancas.add(new Knight(Color.WHITE, new Coordenada('B', 1), this));
		blancas.add(new Bishop(Color.WHITE, new Coordenada('C', 1), this));
		blancas.add(new Queen(Color.WHITE, new Coordenada('D', 1), this));
		blancas.add(new Bishop(Color.WHITE, new Coordenada('F', 1), this));
		blancas.add(new Knight(Color.WHITE, new Coordenada('G', 1), this));
		blancas.add(new Rook(Color.WHITE, new Coordenada('H', 1), this));

		blackKing = new King(Color.BLACK, new Coordenada('E', 8), this);
		negras.add(blackKing);
		negras.add(new Rook(Color.BLACK, new Coordenada('A', 8), this));
		negras.add(new Knight(Color.BLACK, new Coordenada('B', 8), this));
		negras.add(new Bishop(Color.BLACK, new Coordenada('C', 8), this));
		negras.add(new Queen(Color.BLACK, new Coordenada('D', 8), this));
		negras.add(new Bishop(Color.BLACK, new Coordenada('F', 8), this));
		negras.add(new Knight(Color.BLACK, new Coordenada('G', 8), this));
		negras.add(new Rook(Color.BLACK, new Coordenada('H', 8), this));

		for (int i = 0; i < 8; i++) {
			blancas.add(new Pawn(Color.WHITE, new Coordenada((char) ('A' + i), 2), this));
			negras.add(new Pawn(Color.BLACK, new Coordenada((char) ('A' + i), 7), this));
		}

		addToPanel();

	}

	private void addToPanel() {

		// Añadir parte superior
		add(getNewLabel(""));
		for (int i = 0; i < 8; i++)
			add(getNewLabel(String.valueOf((char) ('A' + i))));
		add(getNewLabel(""));

		for (int fil = 8; fil >= 1; fil--) {
			add(getNewLabel(String.valueOf(fil)));
			for (int col = 0; col < 8; col++) {

				Coordenada c = new Coordenada((char) ('A' + col), fil);

				Celda celda = tablero.get(c);
				if ((fil + col) % 2 == 0)
					celda.setAsWhiteCell();
				else
					celda.setAsBlackCell();

				add(celda);
			}
			add(getNewLabel(String.valueOf(fil)));
		}

		// Añadir parte inferior
		add(getNewLabel(""));
		for (int i = 0; i < 8; i++)
			add(getNewLabel(String.valueOf((char) ('A' + i))));
		add(getNewLabel(""));

	}
	
	
	public void repaintBoard() {
		
		for (int fil = 8; fil >= 1; fil--) {
			for (int col = 0; col < 8; col++) {

				Coordenada c = new Coordenada((char) ('A' + col), fil);

				Celda celda = tablero.get(c);
				if ((fil + col) % 2 == 0)
					celda.setAsWhiteCell();
				else
					celda.setAsBlackCell();


			}
			
		}
	}

	public boolean contiene(Coordenada c) {
		return !(c.getRow() > 8 || c.getRow() < 1 || c.getColumn() < 'A' || c.getColumn() > 'H');
	}

	public Pieza getPiezaAt(Coordenada c) {
		if (!contiene(c))
			return null;
		else
			return getCeldaAt(c).getPieza();
	}

	public ArrayList<Pieza> getBlancas() {
		return blancas;
	}
	

	public ArrayList<Pieza> getNegras() {
		return negras;
	}

	public void eliminarPieza(Pieza p) {

		if (p.getColor() == Color.WHITE) {			
			blancas.remove(blancas.indexOf(p));
		} else
			negras.remove(negras.indexOf(p));

	}

	public Celda getCeldaAt(Coordenada c) {
		return tablero.get(c);
	}

	public boolean blackKingIsAlive() {
		return negras.contains(blackKing);
	}

	public boolean whiteKingIsAlive() {
		return blancas.contains(whiteKing);
	}

	private JLabel getNewLabel(String text) {
		JLabel label = new JLabel(text);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(java.awt.Color.DARK_GRAY);
		label.setForeground(java.awt.Color.WHITE);
		return label;
	}

	public Coordenada getCoordenadaOfCelda(Celda c) {

		Set<Coordenada> coordenadas = tablero.keySet();

		Iterator<Coordenada> it = coordenadas.iterator();
		boolean encontrado = false;
		Coordenada coordenada=null;
		
		while (it.hasNext() && !encontrado) {
			coordenada = it.next();
			if(tablero.get(coordenada).equals(c))
				encontrado=true;
		}
		
		if(encontrado)
			return coordenada;
		else
			return null;

	}
	public boolean check(Color color) {
		Set l=new HashSet();
		if(color==Color.BLACK) {
			for(int i=0;i<blancas.size();i++) {
				l.addAll(blancas.get(i).getNextMovements());
			}
			if(l.contains(blackKing.posicion)==true)
				return true;
			else
				return false;
		}else {
			for(int i=0;i<negras.size();i++) {
				l.addAll(negras.get(i).getNextMovements());
			}
			if(l.contains(whiteKing.posicion)==true)
				return true;
			else
				return false;
		}
		
	}

	public HashMap<Coordenada, Celda> getTablero() {
		return tablero;
	}
	

}
