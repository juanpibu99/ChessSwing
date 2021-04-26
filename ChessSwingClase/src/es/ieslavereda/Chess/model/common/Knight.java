package es.ieslavereda.Chess.model.common;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Knight extends Pieza{

	private Set<Coordenada> posicionesCandidatas;
	public Knight(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_KNIGHT;
		else
			tipo = Tipo.BLACK_KNIGHT;
		
		colocate(posicion);
		
	}

	@Override
	public Set<Coordenada> getNextMovements() {
		// TODO Auto-generated method stub
		return getNextMovements(this);
	}
	
	public Set<Coordenada> getNextMovements(Pieza p) {
		JPTablero t = p.tablero;
		Set<Coordenada> movimientos = new HashSet<>();
		Coordenada c;
		
		c= p.posicion.up().diagonalUpRight();
		
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		
		
		c= p.posicion.up().diagonalUpLeft();
		
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		
		c= p.posicion.right().diagonalUpRight();
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		
		c= p.posicion.right().diagonalDownRight();
		
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		c= p.posicion.left().diagonalUpLeft();
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		
		c= p.posicion.left().diagonalDownLeft();
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		
		c= p.posicion.down().diagonalDownRight();
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		c= p.posicion.down().diagonalDownLeft();
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					movimientos.add(c);
			} else {
				movimientos.add(c);
			}
		}
		return movimientos;
	}

}
