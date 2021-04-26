package es.ieslavereda.Chess.vista;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import es.ieslavereda.Chess.model.common.Movimiento;

import javax.swing.JScrollPane;

public class JPMovements extends JPanel {
	private JButton btnPrev;
	private JButton btnNext;
	private JScrollPane scrollPane;
	private JList<Movimiento> list;

	/**
	 * Create the panel.
	 */
	public JPMovements() {
		setBorder(new TitledBorder(null, "MOVEMENTS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0,grow");
		
		list = new JList<Movimiento>();
		scrollPane.setViewportView(list);
		
		JPanel panelBotones = new JPanel();
		add(panelBotones, "cell 0 1,grow");
		
		btnPrev = new JButton("<");
		
		btnNext = new JButton(">");
		GroupLayout gl_panelBotones = new GroupLayout(panelBotones);
		gl_panelBotones.setHorizontalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBotones.createSequentialGroup()
					.addComponent(btnPrev)
					.addPreferredGap(ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
					.addComponent(btnNext))
		);
		gl_panelBotones.setVerticalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBotones.createSequentialGroup()
					.addGroup(gl_panelBotones.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPrev)
						.addComponent(btnNext))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelBotones.setLayout(gl_panelBotones);

	}

	public JList<Movimiento> getList() {
		return list;
	}

	public JButton getBtnPrev() {
		return btnPrev;
	}

	public JButton getBtnNext() {
		return btnNext;
	}
}
