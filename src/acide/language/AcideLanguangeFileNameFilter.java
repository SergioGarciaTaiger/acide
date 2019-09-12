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
package acide.language;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * ACIDE - A Configurable IDE file name Filter
 * 
 * @author JuanJesús
 * @version 0.14
 */
public class AcideLanguangeFileNameFilter implements FilenameFilter {
	
	/**
	 * ACIDE - A Configurable IDE file name filter extension filter
	 */
	private FileNameExtensionFilter extensionFilter;
	
	/**
	 * Creates a new ACIDE - A Configurable IDE file name Filter 
	 */
	public AcideLanguangeFileNameFilter(){
		extensionFilter= new FileNameExtensionFilter("", "properties");
	}
	/*
	 * (non-Javadoc)
	 * @see java.io.FilenameFilter#actionPerformedaccept(java.io.File dir, java.lang.String name)
	 */
	@Override
	public boolean accept(File dir, String name) {
		return extensionFilter.accept(new File(dir.getAbsolutePath()+File.separator+name));
		
	}

}
