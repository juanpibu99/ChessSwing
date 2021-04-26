package es.ieslavereda.Chess.model.common;

import java.util.HashSet;
import java.util.Set;

public class King extends Pieza {

	public King(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		if (color == Color.WHITE)
			tipo = Tipo.WHITE_KING;
		else
			tipo = Tipo.BLACK_KING;
		
		colocate(posicion);
	}

	@Override
	public Set<Coordenada> getNextMovements() {

		Set<Coordenada> movimientos = new HashSet<Coordenada>();

		addCoordenada(posicion.up(), movimientos);
		addCoordenada(posicion.right(), movimientos);
		addCoordenada(posicion.down(), movimientos);
		addCoordenada(posicion.left(), movimientos);
		addCoordenada(posicion.diagonalUpRight(), movimientos);
		addCoordenada(posicion.diagonalUpLeft(), movimientos);
		addCoordenada(posicion.diagonalDownRight(), movimientos);
		addCoordenada(posicion.diagonalDownLeft(), movimientos);

		return movimientos;
	}

	private void addCoordenada(Coordenada c, Set<Coordenada> movimientos) {
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
	}

}
