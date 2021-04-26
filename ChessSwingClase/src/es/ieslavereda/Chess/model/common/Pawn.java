package es.ieslavereda.Chess.model.common;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Pawn extends Pieza implements Serializable {

	public Pawn(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_PAWN;
		else
			tipo = Tipo.BLACK_PAWN;
		
		colocate(posicion);
	}

	
	public void moveTo(Coordenada c) {
		super.moveTo(c);
		
		if(getColor()==Color.WHITE && posicion.getRow()==8) {
			tablero.eliminarPieza(this);
			tablero.getBlancas().add(new Queen(Color.WHITE,c,tablero));
		} else if (getColor()==Color.BLACK && posicion.getRow()==1){
			tablero.eliminarPieza(this);
			tablero.getNegras().add(new Queen(Color.BLACK,c,tablero));
		}		
	}
	
	@Override
	public Set<Coordenada> getNextMovements() {
		
		// Comprobamos el color de la ficha para ver el sentido
		if (getColor() == Color.BLACK) {
			return movementsAsPawnBlack();
		} else {
			return movementsAsPawnWhite();
		}
	}

	private Set<Coordenada> movementsAsPawnWhite() {
		
		Set<Coordenada> posicionesCandidatas = new LinkedHashSet<Coordenada>();
		Coordenada c;

		// posicion delante
		c = posicion.up();

		if (tablero.contiene(c) && tablero.getCeldaAt(c).getPieza() == null)
			posicionesCandidatas.add(c);

		// avanza matando
		c = posicion.diagonalUpLeft();
		if (tablero.contiene(c)
				&& (tablero.getCeldaAt(c).getPieza() != null && tablero.getCeldaAt(c).getPieza().getColor() != getColor()))
			posicionesCandidatas.add(c);
		c = posicion.diagonalUpRight();
		if (tablero.contiene(c)
				&& (tablero.getCeldaAt(c).getPieza() != null && tablero.getCeldaAt(c).getPieza().getColor() != getColor()))
			posicionesCandidatas.add(c);

		// Si esta en la posicion inicial se le permite avanzar 2 posiciones
		if (posicion.getRow() == 2) {
			c = posicion.up();
			if (tablero.contiene(c) && tablero.getCeldaAt(c).getPieza() == null) {
				c = c.up();
				if (tablero.contiene(c) && tablero.getCeldaAt(c).getPieza() == null) {
					posicionesCandidatas.add(c);
				}
			}
		}
		
		return posicionesCandidatas;

	}

	private Set<Coordenada> movementsAsPawnBlack() {

		Set<Coordenada> posicionesCandidatas = new LinkedHashSet<Coordenada>();
		Coordenada c;

		// posicion delante
		c = posicion.down();

		if (tablero.contiene(c) && tablero.getCeldaAt(c).getPieza() == null)
			posicionesCandidatas.add(c);

		// avanza matando
		c = posicion.diagonalDownLeft();
		if (tablero.contiene(c)
				&& (tablero.getCeldaAt(c).getPieza() != null && tablero.getCeldaAt(c).getPieza().getColor() != getColor()))
			posicionesCandidatas.add(c);
		c = posicion.diagonalDownRight();
		if (tablero.contiene(c)
				&& (tablero.getCeldaAt(c).getPieza() != null && tablero.getCeldaAt(c).getPieza().getColor() != getColor()))
			posicionesCandidatas.add(c);

		// Si esta en la posicion inicial se le permite avanzar 2 posiciones
		if (posicion.getRow() == 7) {
			c = posicion.down();
			if (tablero.contiene(c) && tablero.getCeldaAt(c).getPieza() == null) {
				c = c.down();
				if (tablero.contiene(c) && tablero.getCeldaAt(c).getPieza() == null) {
					posicionesCandidatas.add(c);
				}
			}
		}
		
		return posicionesCandidatas;

	}

}
