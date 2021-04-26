package es.ieslavereda.Chess.model.common;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import es.ieslavereda.Chess.config.MyConfig;

public class Celda extends JButton implements Serializable {

	private Pieza pieza;

	private Dimension dimension = new Dimension(50, 50);

	public static java.awt.Color colorCeldaNegra = new java.awt.Color(MyConfig.getInstancia().getBlackCellColor());
	public static java.awt.Color colorCeldaBlanca = new java.awt.Color(MyConfig.getInstancia().getWhiteCellColor());
	public static java.awt.Color colorBordeCelda = new java.awt.Color(MyConfig.getInstancia().getBorderNormalCell());
	public static java.awt.Color colorBordeCeldaComer = new java.awt.Color(MyConfig.getInstancia().getBorderKillCell());

	public Celda() {
		super();
		pieza = null;
		
		setPreferredSize(dimension);
	}

	public Pieza getPieza() {
		return pieza;
	}

	public void setPieza(Pieza pieza) {
		this.pieza = pieza;
			
		if (pieza != null) {
			Image image = (new ImageIcon(Celda.class.getResource("/es/ieslavereda/Chess/recursos/" + pieza.getFileName())).getImage());
			ImageIcon imageIconResized = new ImageIcon(getScaledImage(image,25));
			setIcon(imageIconResized);
		} else {
			setIcon(null);
		}
	}

	public boolean contienePieza() {
		return pieza != null;
	}
	public void setAsWhiteCell() {
		setBackground(colorCeldaBlanca);
	}
	public void setAsBlackCell() {
		setBackground(colorCeldaNegra);
	}
	
	public void resaltar(java.awt.Color color, int size) { 
		setBorder(new LineBorder(color, size));
	}
	
	private Image getScaledImage(Image srcImg, int size){
		int h = size, w = size;
		
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	@Override
	public String toString() {
		if (pieza == null)
			return " ";
		else
			return pieza.toString();
	}
}
