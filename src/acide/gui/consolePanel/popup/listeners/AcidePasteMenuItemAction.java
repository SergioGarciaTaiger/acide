/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
 * Authors:
 * 		- Fernando Sáenz Pérez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan José Ortiz Sánchez.
 *          - Delfín Rupérez Cañas.
 *      - Version 0.7:
 *          - Miguel Martín Lázaro.
 *      - Version 0.8:
 *      	- Javier Salcedo Gómez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Gutiérrez García-Pardo.
 *      	- Elena Tejeiro Pérez de Ágreda.
 *      	- Andrés Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      - Version 0.17
 *      	- Sergio Domínguez Fuentes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package acide.gui.consolePanel.popup.listeners;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Writer;

import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE console panel popup menu console paste menu item
 * action listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcidePasteMenuItemAction implements ActionListener {

	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent actionEvent) {
		
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = cb.getContents(this);

		// Construimos el DataFlavor correspondiente al String java
		DataFlavor dataFlavorStringJava = null;
		try {
			dataFlavorStringJava = new DataFlavor("application/x-java-serialized-object; class=java.lang.String");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String texto = null;
		// Y si el dato se puede conseguir como String java, lo sacamos por pantalla
		if (t.isDataFlavorSupported(dataFlavorStringJava)) {
		   
		try {
			texto = (String) t.getTransferData(dataFlavorStringJava);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
String[] commands = texto.split("\n");
		
//		if (texto.endsWith("\n"))
//			commands[commands.length-1]+="\n";
		
		Writer wr = AcideMainWindow.getInstance().getConsolePanel().getProcessThread().getWriter();
		//Reader rd = AcideMainWindow.getInstance().getConsolePanel().getProcessThread().
				
		for (int i = 0; i<commands.length; i++){
			if (!commands[i].equalsIgnoreCase("")){
				try {
					if (i<commands.length-1){
						commands[i] +="\n";
						AcideMainWindow.getInstance().getConsolePanel().addText(commands[i]);
						wr.write(commands[i]);
						wr.flush();
					}else{
						if (texto.endsWith("\n")){
							AcideMainWindow.getInstance().getConsolePanel().addText(commands[i]+"\n");
							wr.write(commands[i]+"\n");
							wr.flush();
						}else{
							String text = "";
							text = AcideMainWindow.getInstance().getConsolePanel().getTextPane().getText();
							text = text.concat(commands[i]);
							AcideMainWindow.getInstance().getConsolePanel().getTextPane().setText(text);
						}
							
					}
					try{
						Thread.currentThread().sleep(50);//sleep for 20 ms
					}
					catch(Exception ie){
						ie.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
//		AcideMainWindow.getInstance().getConsolePanel().getTextPane().paste();
//		
//		//KeyEvent event = new Event(AcideMainWindow.getInstance().getConsolePanel(), 0, 0, 0, KeyEvent.VK_ENTER);
//		@SuppressWarnings("deprecation")
//		KeyEvent event = new KeyEvent(AcideMainWindow.getInstance(), 1, 1, KeyEvent.VK_ENTER, KeyEvent.KEY_PRESSED);
//	
//		
//		KeyListener[] listeners = AcideMainWindow.getInstance().getConsolePanel().getTextPane().getKeyListeners();
//		
//		event.setKeyCode(KeyEvent.VK_ENTER);
//		
//		for ( int i = 0; i< listeners.length; i++)
//			listeners[i].keyPressed(event);
//		
///*	// Gets the root element from the styled document
//			Element rootElement = AcideMainWindow.getInstance()
//					.getFileEditorManager().getSelectedFileEditorPanel()
//					.getStyledDocument().getDefaultRootElement();
//
//			// Gets its number of lines
//			int numberOfLines = rootElement.getElementCount();
//			
//			// If the number of lines to send is bigger than the maximum
//					String text = AcideMainWindow.getInstance()
//							.getFileEditorManager().getSelectedFileEditorPanel().getActiveTextEditionArea().getSelectedText();
//					
//					String[] commands = text.split("\n");
//					
//					Writer wr = AcideMainWindow.getInstance().getConsolePanel().getProcessThread().getWriter();
//					//Reader rd = AcideMainWindow.getInstance().getConsolePanel().getProcessThread().
//					
//					for (int i = 0; i<commands.length; i++){
//						if (!commands[i].equalsIgnoreCase("")){
//							try {
//								AcideMainWindow.getInstance().getConsolePanel().addText(commands[i]+"\n");
//								wr.write(commands[i] + "\n");
//								wr.flush();
//								try{
//									  Thread.currentThread().sleep(50);//sleep for 20 ms
//									}
//									catch(Exception ie){
//										ie.printStackTrace();
//									}
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						
//				}*/
//				

	}

		
/*	
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		// Pastes
		//AcideMainWindow.getInstance().getConsolePanel().getTextPane().paste();
		String text= AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel().getActiveTextEditionArea().getSelectedText();
		//AcideMainWindow.getInstance().getConsolePanel().addText(text);
		AcideMainWindow.getInstance().getConsolePanel().executeCommand(AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel().getActiveTextEditionArea().getSelectedText(), "");
	}*/
}
