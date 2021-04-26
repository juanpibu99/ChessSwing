package es.ieslavereda.Chess.model.common;

import java.util.Set;

public class Queen extends Pieza{

	public Queen(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_QUEEN;
		else
			tipo = Tipo.BLACK_QUEEN;
		
		colocate(posicion);
	}

	@Override
	public Set<Coordenada> getNextMovements() {
		
		Set<Coordenada> movimientos = Rook.getNextMovements(this);
		
		movimientos.addAll(Bishop.getNextMovements(this));
		
		return movimientos;
	}
	
	

}
