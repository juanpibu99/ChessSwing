package es.ieslavereda.Chess.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import es.ieslavereda.Chess.model.common.JPTablero;

import java.awt.Color;

public class VistaPrincipal extends JFrame {

	private JPanel contentPane;
	private JPTablero panelTablero;
	private JPFichasEliminadas panelEliminados;
	private JPTurno panelTurno;
	private JPMovements panelMovimientos;
	private JMenuItem mntmPreferences;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenuItem mntmNew;
	/**
	 * Create the frame.
	 */
	public VistaPrincipal() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 448);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		
		mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmPreferences = new JMenuItem("Preferences");
		mnEdit.add(mntmPreferences);
		
		JMenu mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnAbout.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panelTablero = new JPTablero();
		
		panelEliminados = new JPFichasEliminadas();
		
		panelTurno = new JPTurno();
		
		panelMovimientos = new JPMovements();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelTablero, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelEliminados, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panelTurno, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelMovimientos, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panelMovimientos, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
								.addComponent(panelTurno, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelEliminados, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelTablero, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public JPTablero getPanelTablero() {
		return panelTablero;
	}
	public JMenuItem getMntmPreferences() {
		return mntmPreferences;
	}
	public JPFichasEliminadas getPanelEliminados() {
		return panelEliminados;
	}
	public JPTurno getPanelTurno() {
		return panelTurno;
	}
	public JPMovements getPanelMovimientos() {
		return panelMovimientos;
	}
	public JMenuItem getMntmOpen() {
		return mntmOpen;
	}
	public JMenuItem getMntmSave() {
		return mntmSave;
	}
	public JMenuItem getMntmNew() {
		return mntmNew;
	}
	
	
}
