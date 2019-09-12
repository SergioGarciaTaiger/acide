/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
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
 *      -Version from 0.12 to 0.16
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
package acide.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;

import acide.configuration.icons.AcideAddedIcon;
import acide.configuration.icons.AcideMenuIconsConfiguration;

public class IconsUtils {

	public static ImageIcon iconDefaultSize(ImageIcon imag){
        //nuevos ancho y alto: para que conserve la proporcion pasamos -1
		ImageIcon imagen = new ImageIcon(imag.getImage().getScaledInstance(14, 14, java.awt.Image.SCALE_DEFAULT));
		return imagen;	      
	}
	
	public static ImageIcon getIcon(String name){
		if (name.equals(""))
			return null;
		if (!name.startsWith(".")){
			name = name.replace(File.separatorChar, '/');
			if (!AcideMenuIconsConfiguration.getInstance().hasIcon(name)){
				String nameInsertedIcon = getName(name);
				String dest = getRelativePath(nameInsertedIcon);
				
			
				AcideAddedIcon icon = new AcideAddedIcon(name, dest, nameInsertedIcon);
				AcideMenuIconsConfiguration.getInstance().insertObject(icon);
				AcideMenuIconsConfiguration.getInstance().save();
			
				try {
					copyFile(name, dest);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return iconDefaultSize(new ImageIcon(
					AcideMenuIconsConfiguration.getInstance()
					.getMenuIconsManager().getObject(name).getRelative()));
		} else{
			return iconDefaultSize(new ImageIcon(name));
		}
	}
	
	private static String getName(String name) {
		String aux = name.substring(name.lastIndexOf("/") + 1, name.length());
		
		return aux;
	}
	
	private static String getRelativePath(String name) {
		//"./resources/icons/added/newFile.png";
		String result = "./resources/icons/added/";
		result = result + name;
		
		int number = AcideMenuIconsConfiguration.getInstance()
				.getMenuIconsManager().numberOf(name);
		if (number > 0){
			
			String ext = result.substring(result.lastIndexOf("."), result.length());
			String nameAux = result.substring(0, result.lastIndexOf("."));
			result = nameAux + " (";
			result = result + number + ")";
			result = result + ext;
			
		}

		
		return result;
	}

	public static void copyFile(String source, String dest) throws IOException { 

		  File sourceFile =  new File(source);
				 // new File("C:/Users/Usuario/workspace/acide/resources/icons/menu/file/newFile.png"); 

		  File destFile = new File(dest);
				 // new File("C:/Users/Usuario/workspace/newFile.png");  
		  
		  
		
		if(!destFile.exists()) { 
			   destFile.createNewFile(); 
		   } 

		   FileChannel sourceChannel = null; 
		   FileChannel destination = null; 

		   try { 
		    sourceChannel = new FileInputStream(sourceFile).getChannel(); 
		    
		    destination = new FileOutputStream(destFile).getChannel(); 

		    destination.transferFrom(sourceChannel, 0, sourceChannel.size()); 

		   } 

		   finally { 

			   if(sourceChannel != null) { 
		    	
				   sourceChannel.close(); 
			   } 

			   if(destination != null) { 

				   destination.close(); 

			   } 
		   } 
		 }
}
