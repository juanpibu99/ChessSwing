package es.ieslavereda.Chess.model.common;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Bishop extends Pieza {

	public Bishop(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);

		if (color == Color.WHITE)
			tipo = Tipo.WHITE_BISHOP;
		else
			tipo = Tipo.BLACK_BISHOP;

		colocate(posicion);
	}

	@Override
	public Set<Coordenada> getNextMovements() {
		// TODO Auto-generated method stub
		return getNextMovements(this);
	}

	public static Set<Coordenada> getNextMovements(Pieza p) {

		JPTablero t = p.tablero;
		Set<Coordenada> posicionesCandidatas = new LinkedHashSet<Coordenada>();
		Coordenada c;

		// Comprobamos que la ficha este en el tablero
		if (p.posicion == null)
			return posicionesCandidatas;

		// Diagonal superior izq
		c = p.posicion;
		do {
			c = c.diagonalUpLeft();
			addCelda(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		// Diagonal superior der
		c = p.posicion;
		do {
			c = c.diagonalUpRight();
			addCelda(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		// Diagonal inferior izq
		c = p.posicion;
		do {
			c = c.diagonalDownLeft();
			addCelda(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		// Diagonal inferior der
		c = p.posicion;
		do {
			c = c.diagonalDownRight();
			addCelda(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		return posicionesCandidatas;
	}
	private static void addCelda(Coordenada c, Set<Coordenada> posicionesCandidatas, Pieza p) {
		JPTablero t = p.tablero;
		if (t.contiene(c)	&& (t.getCeldaAt(c).getPieza() == null || t.getCeldaAt(c).getPieza().getColor() != p.getColor()))
			posicionesCandidatas.add(c);
	}
}
