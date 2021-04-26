package es.ieslavereda.Chess.model.common;

import java.io.Serializable;
import java.util.Set;

public abstract class Pieza implements Serializable {

	protected Tipo tipo;
	protected JPTablero tablero;
	protected Coordenada posicion;
	
	public Pieza(Coordenada posicion, JPTablero tablero) {
		super();
		this.posicion = posicion;
		this.tablero = tablero;
		
//		colocate(posicion);
	}
	
	protected void colocate(Coordenada c) {
		
		tablero.getCeldaAt(posicion).setPieza(null);
		posicion = c;
		tablero.getCeldaAt(posicion).setPieza(this);
		
	}
	
	public void moveTo(Coordenada c) {
		
		if(tablero.getPiezaAt(c)==null) {
			colocate(c);
		} else {
			tablero.eliminarPieza(tablero.getPiezaAt(c));
			colocate(c);
		}
	}
	public boolean canMoveTo(Coordenada c) {
		return this.getNextMovements().contains(c);
	}

	public Color getColor() {
		return tipo.getColor();
	}
	
	public String getFileName() {
		return tipo.getFileName();
	}
	
	public Coordenada getPosicion() {
		return posicion;
	}

	@Override
	public String toString() {
		return tipo.getFileName();
	}
	
	
	public void setPosicion(Coordenada posicion) {
		this.posicion = posicion;
	}
	

	public void setTablero(JPTablero tablero) {
		this.tablero = tablero;
	}

	public abstract Set<Coordenada> getNextMovements();
}











