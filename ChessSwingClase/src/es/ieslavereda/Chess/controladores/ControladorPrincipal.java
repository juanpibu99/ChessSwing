package es.ieslavereda.Chess.controladores;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.ieslavereda.Chess.config.MyConfig;
import es.ieslavereda.Chess.vista.JPTurno;
import es.ieslavereda.Chess.vista.Preferencias;
import es.ieslavereda.Chess.vista.VistaPrincipal;
import es.ieslavereda.Chess.model.common.*;

public class ControladorPrincipal implements ActionListener,MouseListener,Serializable {

	private VistaPrincipal vista;
	private JPTurno jpTurno;
	private Pieza piezaSeleccionada;
	private Preferencias jfPreferencias;
	private GestionFichasEliminadas gestionFichasEliminadas;
	private DefaultListModel<Movimiento> dlm;
	private Deque<Movimiento> stack;


	public ControladorPrincipal(VistaPrincipal vista) {
		super();
		this.vista = vista;
		
		stack = new ArrayDeque<Movimiento>();

		inicializar();
	}

	private void inicializar() {

		gestionFichasEliminadas = new ControladorFichasEliminadas(vista.getPanelEliminados());

		jpTurno = vista.getPanelTurno();

		dlm = new DefaultListModel<Movimiento>();
		vista.getPanelMovimientos().getList().setModel(dlm);

		Component[] components = vista.getPanelTablero().getComponents();

		for (Component component : components) {
			if (component instanceof Celda) {
				((Celda) component).addActionListener(this);
			}
		}

		// Añadimos los MouseListener
		vista.getPanelMovimientos().getList().addMouseListener(this);
		
		// Añadimos los ActionListener
		vista.getMntmPreferences().addActionListener(this);
		vista.getPanelMovimientos().getBtnPrev().addActionListener(this);
		vista.getPanelMovimientos().getBtnNext().addActionListener(this);
		vista.getMntmSave().addActionListener(this);
		vista.getMntmOpen().addActionListener(this);

		// Añadimos los ActionCommand
		vista.getMntmPreferences().setActionCommand("Abrir preferencias");
		vista.getPanelMovimientos().getBtnPrev().setActionCommand("Previous movement");
		vista.getPanelMovimientos().getBtnNext().setActionCommand("Next movement");
		vista.getMntmSave().setActionCommand("Guardar");
		vista.getMntmOpen().setActionCommand("Cargar");
	}

	public void go() {
		vista.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String comando = arg0.getActionCommand();
		if (comando.equals("Abrir preferencias")) {
			abrirPreferencias();
		} else if (comando.equals("Cambiar Color Celda Blanca")) {
			cambiarColorCeldaBlanca();
		} else if (comando.equals("Cambiar Color Celda Negra")) {
			cambiarColorCeldaNegra();
		} else if (comando.equals("Cambiar Color Borde Celda")) {
			cambiarColorBordeNormal();
		} else if (comando.equals("Cambiar Color Borde Celda Comer")) {
			cambiarColorBordeMatando();
		} else if (comando.equals("Guardar")) {
			guardar();
		} else if (comando.equals("Cargar")) {
			cargar();
		} else if (comando.equals("Previous movement")) {
			if(piezaSeleccionada!=null) {
				desmarcarPosiblesDestinos();
			}
			previousMovement();
			piezaSeleccionada=null;
		} else if (comando.equals("Next movement")) {
			if(piezaSeleccionada!=null) {
				desmarcarPosiblesDestinos();
			}
			nextMovement();
			piezaSeleccionada=null;
		} else if (arg0.getSource() instanceof Celda) {
			comprobarMovimiento((Celda) arg0.getSource());
		}

	}

	private void nextMovement() {

		try {
			Movimiento m = stack.pop();
			dlm.addElement(m);
			Coordenada origen, destino;

			origen = m.getOrigen();
			destino = m.getDestino();

			switch (m.getTipoAccion()) {
			case Movimiento.NOT_KILL:
				vista.getPanelTablero().getPiezaAt(origen).setPosicion(destino);
				vista.getPanelTablero().getCeldaAt(destino).setPieza(vista.getPanelTablero().getPiezaAt(origen));
				vista.getPanelTablero().getCeldaAt(origen).setPieza(null);
				

				break;

			case Movimiento.KILL:
				vista.getPanelTablero().getPiezaAt(origen).setPosicion(destino);
				vista.getPanelTablero().getCeldaAt(destino).setPieza(vista.getPanelTablero().getPiezaAt(origen));
				vista.getPanelTablero().getCeldaAt(origen).setPieza(null);
				if (m.getFicha().getColor() == Color.WHITE)
					vista.getPanelTablero().getBlancas().remove(m.getFicha());
				else
					vista.getPanelTablero().getNegras().remove(m.getFicha());
				gestionFichasEliminadas.addPiece(m.getFicha());
				break;
			case Movimiento.RISE:
				m.getFichaGenerada().setPosicion(destino);
				vista.getPanelTablero().getCeldaAt(destino).setPieza(m.getFichaGenerada());
				vista.getPanelTablero().getCeldaAt(origen).setPieza(null);
				
				if (m.getFichaPeon().getColor() == Color.WHITE) {
					vista.getPanelTablero().getBlancas().add(m.getFichaGenerada());
					vista.getPanelTablero().getBlancas().remove(m.getFichaPeon());
				}else {
					vista.getPanelTablero().getNegras().add(m.getFichaGenerada());
					vista.getPanelTablero().getNegras().remove(m.getFichaPeon());
				}
				break;
			case Movimiento.RISE_KILLING:
				vista.getPanelTablero().getCeldaAt(destino).setPieza(m.getFichaGenerada());
				vista.getPanelTablero().getCeldaAt(origen).setPieza(null);
				gestionFichasEliminadas.addPiece(m.getFicha());
				if (m.getFichaPeon().getColor() == Color.WHITE) {
					vista.getPanelTablero().getBlancas().add(m.getFichaGenerada());
					vista.getPanelTablero().getBlancas().remove(m.getFichaPeon());
					vista.getPanelTablero().getNegras().remove(m.getFicha());
					
				}else {
					vista.getPanelTablero().getBlancas().remove(m.getFicha());
					vista.getPanelTablero().getNegras().add(m.getFichaGenerada());
					vista.getPanelTablero().getNegras().remove(m.getFichaPeon());
				}
				break;
				
			default:
				throw new Exception("Error interno. Movimento desconocido");
			}

			vista.getPanelTurno().cambioTurno();
			Movimiento.increaseNumberOfMovements();

		} catch (NoSuchElementException ne) {
			JOptionPane.showMessageDialog(vista, "No hay movimientos para avanzar", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void previousMovement() {

		try {

			Movimiento m = dlm.remove(dlm.getSize() - 1);
			stack.push(m);

			Coordenada origen, destino;
			destino = m.getDestino();
			origen = m.getOrigen();

			switch (m.getTipoAccion()) {
			case Movimiento.NOT_KILL:
				vista.getPanelTablero().getPiezaAt(destino).setPosicion(origen);
				vista.getPanelTablero().getCeldaAt(origen).setPieza(vista.getPanelTablero().getPiezaAt(destino));
				vista.getPanelTablero().getCeldaAt(destino).setPieza(null);
				

				break;

			case Movimiento.KILL:
				vista.getPanelTablero().getPiezaAt(destino).setPosicion(origen);
				m.getFicha().setPosicion(destino);
				vista.getPanelTablero().getCeldaAt(origen).setPieza(vista.getPanelTablero().getPiezaAt(destino));
				vista.getPanelTablero().getCeldaAt(destino).setPieza(m.getFicha());
				gestionFichasEliminadas.removePiece(m.getFicha());

				if (m.getFicha().getColor() == Color.WHITE)
					vista.getPanelTablero().getBlancas().add(m.getFicha());
				else
					vista.getPanelTablero().getNegras().add(m.getFicha());

				break;
			
			case Movimiento.RISE:
				m.getFichaPeon().setPosicion(origen);
				vista.getPanelTablero().getCeldaAt(destino).setPieza(null);
				vista.getPanelTablero().getCeldaAt(origen).setPieza(m.getFichaPeon());
				
				if (m.getFichaPeon().getColor() == Color.WHITE) { 
					vista.getPanelTablero().getBlancas().add(m.getFichaPeon());
					vista.getPanelTablero().getBlancas().remove(m.getFichaGenerada());
				}
				else {
					vista.getPanelTablero().getNegras().add(m.getFichaPeon());
					vista.getPanelTablero().getNegras().remove(m.getFichaGenerada());
				}
				break;
			case Movimiento.RISE_KILLING:
				m.getFichaPeon().setPosicion(origen);
				m.getFicha().setPosicion(destino);
				vista.getPanelTablero().getCeldaAt(origen).setPieza(m.getFichaPeon());
				vista.getPanelTablero().getCeldaAt(destino).setPieza(m.getFicha());
				gestionFichasEliminadas.removePiece(m.getFicha());

				if (m.getFichaPeon().getColor() == Color.WHITE) {
					vista.getPanelTablero().getBlancas().remove(m.getFichaGenerada());
					vista.getPanelTablero().getBlancas().add(m.getFichaPeon());
					vista.getPanelTablero().getNegras().add(m.getFicha());
				}else {
					vista.getPanelTablero().getBlancas().add(m.getFicha());
					vista.getPanelTablero().getNegras().add(m.getFichaPeon());
					vista.getPanelTablero().getNegras().remove(m.getFichaGenerada());
				}
				break;
			default:
				throw new Exception("Tipo no conocido");

			}

			Movimiento.decreaseNumberOfMovements();
			vista.getPanelTurno().cambioTurno();

		} catch (ArrayIndexOutOfBoundsException ae) {
			JOptionPane.showMessageDialog(vista, "No hay movimentos para deshacer", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {

			JOptionPane.showMessageDialog(vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}





	private void cambiarColorCeldaBlanca() {

		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorCeldaBlanca(), "Selecciona color de las celdas blancas", jfPreferencias.getBtnColorCeldaBlanca().getBackground());

		if(color!=null) {
		jfPreferencias.getBtnColorCeldaBlanca().setBackground(color);
		MyConfig.getInstancia().setWhiteCellColor(color);
		Celda.colorCeldaBlanca=color;
		vista.getPanelTablero().repaintBoard();

		}
	}
	private void cambiarColorCeldaNegra() {

		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorCeldaNegra(), "Selecciona color de las celdas negras", jfPreferencias.getBtnColorCeldaNegra().getBackground());

		if(color!=null) {
		jfPreferencias.getBtnColorCeldaNegra().setBackground(color);
		MyConfig.getInstancia().setBlackCellColor(color);
		Celda.colorCeldaNegra=color;
		vista.getPanelTablero().repaintBoard();

		}
	}
	private void cambiarColorBordeNormal() {
		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorBordeCelda(), "Selecciona color de los bordes de los posibles movimientos", jfPreferencias.getBtnColorBordeCelda().getBackground());

		if(color!=null) {
		jfPreferencias.getBtnColorBordeCelda().setBackground(color);
		MyConfig.getInstancia().setBorderCell(color);
		Celda.colorBordeCelda=color;
		vista.getPanelTablero().repaintBoard();
		}
	}
	private void cambiarColorBordeMatando() {
		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorBordeCelda(), "Selecciona color de los bordes de los posibles movimientos matando", jfPreferencias.getBtnColorBordeCelda().getBackground());

		if(color!=null) {
		jfPreferencias.getBtnColorBordeCeldaComer().setBackground(color);
		MyConfig.getInstancia().setBorderCellKill(color);
		Celda.colorBordeCeldaComer=color;
		vista.getPanelTablero().repaintBoard();
		}
	}
	private void abrirPreferencias() {

		jfPreferencias = new Preferencias();

		jfPreferencias.setVisible(true);

		// Añadimos ActionListener
		jfPreferencias.getBtnColorCeldaBlanca().addActionListener(this);
		jfPreferencias.getBtnColorCeldaNegra().addActionListener(this);
		jfPreferencias.getBtnColorBordeCelda().addActionListener(this);
		jfPreferencias.getBtnColorBordeCeldaComer().addActionListener(this);

		// Añadimos ActionCommand
		jfPreferencias.getBtnColorCeldaBlanca().setActionCommand("Cambiar Color Celda Blanca");
		jfPreferencias.getBtnColorCeldaNegra().setActionCommand("Cambiar Color Celda Negra");
		jfPreferencias.getBtnColorBordeCelda().setActionCommand("Cambiar Color Borde Celda");
		jfPreferencias.getBtnColorBordeCeldaComer().setActionCommand("Cambiar Color Borde Celda Comer");

	}

	private void comprobarMovimiento(Celda c) {

		if (piezaSeleccionada == null) {
			movimientoSinPiezaSeleccionada(c);
		} else {
			movimientoConPiezaSeleccionada(c);
		}

	}

	private void movimientoConPiezaSeleccionada(Celda c) {

		JPTablero tablero = vista.getPanelTablero();

		if (!piezaSeleccionada.getNextMovements().contains(tablero.getCoordenadaOfCelda(c))) {
			JOptionPane.showMessageDialog(vista, "No puedes mover la pieza a esa posicion", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {

			Movimiento m = null;

			Coordenada origen = piezaSeleccionada.getPosicion();
			Coordenada destino = tablero.getCoordenadaOfCelda(c);

			desmarcarPosiblesDestinos();

			
			if (c.contienePieza()) {
		
				if ((tablero.getCoordenadaOfCelda(c).getRow() == 1 || tablero.getCoordenadaOfCelda(c).getRow() == 8)
						&& piezaSeleccionada instanceof Pawn) {
					m = new Movimiento(origen, destino, Movimiento.RISE_KILLING, c.getPieza(), null, piezaSeleccionada);

				} else {
					m = new Movimiento(origen, destino, Movimiento.KILL, c.getPieza(), null, null);
				}

				gestionFichasEliminadas.addPiece(c.getPieza());
			}

		
			if (m == null
					&& (tablero.getCoordenadaOfCelda(c).getRow() == 1 || tablero.getCoordenadaOfCelda(c).getRow() == 8)
					&& piezaSeleccionada instanceof Pawn) {
				m = new Movimiento(origen, destino, Movimiento.RISE, null, null, piezaSeleccionada);

			} else if (m == null) {
				m = new Movimiento(origen, destino, Movimiento.NOT_KILL, null, null, null);
			}

			dlm.addElement(m);

			piezaSeleccionada.moveTo(tablero.getCoordenadaOfCelda(c));
			if (m.getTipoAccion() == Movimiento.RISE || m.getTipoAccion() == Movimiento.RISE_KILLING) {
				m.setFichaGenerada(c.getPieza());
			}

			piezaSeleccionada = null;
			vista.getPanelTurno().getJLabelPieza().setIcon(null);
			if(jpTurno.getTurno()==Color.WHITE) {
				if(vista.getPanelTablero().check(Color.BLACK)) {
					JOptionPane.showMessageDialog(vista, "El rey negro esta en jaque", "JAQUE", JOptionPane.DEFAULT_OPTION);
				}
				
				
			}else {
				if(vista.getPanelTablero().check(Color.WHITE)) {
					JOptionPane.showMessageDialog(vista, "El rey blanco esta en jaque", "JAQUE", JOptionPane.DEFAULT_OPTION);
				}
				
			}
			if(!vista.getPanelTablero().blackKingIsAlive()) {
				JOptionPane.showMessageDialog(vista, "Enhorabuena, han ganado las blancas", "VICTORIA", JOptionPane.DEFAULT_OPTION);
				finalPartida();
			}else if(!vista.getPanelTablero().whiteKingIsAlive()) {
				JOptionPane.showMessageDialog(vista, "Enhorabuena, han ganado las negras", "VICTORIA", JOptionPane.DEFAULT_OPTION);
				finalPartida();
			}
			jpTurno.cambioTurno();
			stack.removeAll(stack);

		}

	}
	private void finalPartida() {
		for(Celda c:vista.getPanelTablero().getTablero().values()) {
			c.setEnabled(false);
		}
	}

	private void movimientoSinPiezaSeleccionada(Celda c) {

		if (!c.contienePieza()) {
			JOptionPane.showMessageDialog(vista, "Debes seleccionar una pieza", "Error", JOptionPane.ERROR_MESSAGE);
		} else if (c.getPieza().getColor() != jpTurno.getTurno()) {
			JOptionPane.showMessageDialog(vista, "Debes seleccionar una pieza de tu color", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (c.getPieza().getNextMovements().size() == 0) {
			JOptionPane.showMessageDialog(vista, "Esa pieza no la puedes mover", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			piezaSeleccionada = c.getPieza();
			vista.getPanelTurno().getJLabelPieza().setIcon(new ImageIcon(
					JPTurno.class.getResource("/es/ieslavereda/Chess/recursos/" + piezaSeleccionada.getFileName())));
			marcarPosiblesDestinos();

		}

	}

	private void marcarPosiblesDestinos() {
		Set<Coordenada> posiblesMovimientos = piezaSeleccionada.getNextMovements();
		JPTablero tablero = vista.getPanelTablero();

		for (Coordenada cord : posiblesMovimientos) {
			Celda celda = tablero.getCeldaAt(cord);
			if (celda.contienePieza())
				celda.resaltar(Celda.colorBordeCeldaComer, 2);
			else
				celda.resaltar(Celda.colorBordeCelda, 2);

		}
	}

	private void desmarcarPosiblesDestinos() {
		Set<Coordenada> posiblesMovimientos = piezaSeleccionada.getNextMovements();
		JPTablero tablero = vista.getPanelTablero();

		for (Coordenada cord : posiblesMovimientos) {
			Celda celda = tablero.getCeldaAt(cord);

			celda.resaltar(java.awt.Color.GRAY, 1);

		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		Component c = arg0.getComponent();
		if(c == vista.getPanelMovimientos().getList()) {
			int index = vista.getPanelMovimientos().getList().getSelectedIndex();
			
			while (dlm.getSize() > index) {
				previousMovement();
			}

		}
		
	}

	public void guardar() {
		
		JFileChooser jfc=new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Formulario file","app","miapp"));
		int opcion=jfc.showSaveDialog(vista);
		if(opcion==JFileChooser.APPROVE_OPTION) {
			
			ArrayList<Movimiento> movimientos=new ArrayList<Movimiento>();
			int m=Movimiento.getNumero();
			
			for(int i=0;i<dlm.size();i++) {
				movimientos.add(dlm.get(i));
			}
			for(Movimiento mov: stack) {
				movimientos.add(mov);
			}		
			
			try(ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(jfc.getSelectedFile()))){
				oos.writeObject(movimientos);
				oos.writeObject(m);
			}catch(FileNotFoundException o){
				o.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	private void cargar() {
		JFileChooser jfc=new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Formulario file","app","miapp"));
		int opcion=jfc.showOpenDialog(vista);
		if(opcion==JFileChooser.APPROVE_OPTION) {
			
			try(ObjectInputStream ois= new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()))){
				ArrayList<Movimiento> movimientos=(ArrayList<Movimiento>) ois.readObject();
				int m=(int) ois.readObject();
				for(int i=0;i<m;i++)
					nextMovement();
				
				
				
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}
			
	}
	


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
