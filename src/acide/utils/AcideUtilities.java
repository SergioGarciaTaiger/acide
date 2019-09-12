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

import java.awt.Color;
import java.util.Scanner;

/**
 * ACIDE - A Configurable IDE utilities class.
 * 
 * @version 0.11
 */
public class AcideUtilities {

	/**
	 * ACIDE - A Configurable IDE utilities unique class instance.
	 */
	private static AcideUtilities _instance;

	/**
	 * Returns the ACIDE - A Configurable IDE utilities unique class instance.
	 * 
	 * @return the ACIDE - A Configurable IDE utilities unique class instance.
	 */
	public static AcideUtilities getInstance() {
		if (_instance == null)
			_instance = new AcideUtilities();
		return _instance;
	}

	/**
	 * Parses a java.awt.Color[r=x,g=x,b=x] format into a color.
	 * 
	 * @param color
	 *            string that contains the color.
	 * 
	 * @return the parsed color from the string.
	 */
	@SuppressWarnings("resource")
	public Color parseStringToColor(String color) {

		// Creates the scanner with the color string
		Scanner scanner = new Scanner(color);
		
		// Removes everything except integers
		scanner.useDelimiter("\\D+");
		
		// Returns the color
		return new Color(scanner.nextInt(), scanner.nextInt(),
				scanner.nextInt());
	}
}
