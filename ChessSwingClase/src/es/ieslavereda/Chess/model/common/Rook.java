package es.ieslavereda.Chess.model.common;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Rook extends Pieza {

	public Rook(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		// TODO Auto-generated constructor stub
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_ROOK;
		else
			tipo = Tipo.BLACK_ROOK;
		
		colocate(posicion);
	}

	@Override
	public Set<Coordenada> getNextMovements() {
		
		return getNextMovements(this);
	}
	
	public static Set<Coordenada> getNextMovements(Pieza p){
		
		JPTablero t = p.tablero;
		Set<Coordenada> posicionesCandidatas = new LinkedHashSet<Coordenada>();
		Coordenada c;

		// Superior
		c = p.posicion;
		do {
			c = c.up();
			addCoordenada(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		// Inferior
		c = p.posicion;
		do {
			c = c.down();
			addCoordenada(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		// Izquierda
		c = p.posicion;
		do {
			c = c.left();
			addCoordenada(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		// Derecha
		c = p.posicion;
		do {
			c = c.right();
			addCoordenada(c, posicionesCandidatas,p);
		} while (t.contiene(c) && t.getCeldaAt(c).getPieza() == null);

		return posicionesCandidatas;
		
	}
	private static void addCoordenada(Coordenada c, Set<Coordenada> posicionesCandidatas, Pieza p) {
		JPTablero t = p.tablero;
		if (t.contiene(c)	&& (t.getCeldaAt(c).getPieza() == null || t.getCeldaAt(c).getPieza().getColor() != p.getColor()))
			posicionesCandidatas.add(c);
	}
}
