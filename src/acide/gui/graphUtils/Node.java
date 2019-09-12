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
package acide.gui.graphUtils;

import java.awt.Color;



/**
 * ACIDE - A Configurable IDE Node.
 * 
 * @version 0.12
 * @see {@link #DirectedWeightedGraph}.
 */
public class Node {
	
	/**
	 * ACIDE - A Configurable IDE Node id.
	 */
	private int id;
	/**
	 * ACIDE - A Configurable IDE Node label.
	 */
	private String label;
	/**
	 * ACIDE - A Configurable IDE Node x position.
	 */
	private float x;
	/**
	 * ACIDE - A Configurable IDE Node y position.
	 */
	private float y;
	/**
	 * ACIDE - A Configurable IDE Node is position changed.
	 */
	private boolean posicionado=false;
	/**
	 * ACIDE - A Configurable IDE Node is color changed.
	 */
		private Color nodeColor = Color.WHITE;
	
	/**
	 * Creates a ACIDE - A Configurable IDE Node.
	 */
	public Node(){
		
	}

	/**
	 * Creates a ACIDE - A Configurable IDE Node.
	 * 
	 * @param id of the node.
	 * @param label of the node.
	 * @param x position of the node.
	 * @param y position of the node.
	 */
	public Node(int id, String label, int x, int y) {
		super();
		this.id = id;
		this.label = label;
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE Node id.
	 * @return the ACIDE - A Configurable IDE Node id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets a new values of the ACIDE - A Configurable IDE Node id.
	 * @param id the new value to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE Node label.
	 * @return the ACIDE - A Configurable IDE Node label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets a new values of the ACIDE - A Configurable IDE Node label.
	 * @param label the new value to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE Node x position.
	 * @return the ACIDE - A Configurable IDE Node x position.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets a new value of the ACIDE - A Configurable IDE Node x position.
	 * @param x the new x position to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE Node y position.
	 * @return the ACIDE - A Configurable IDE Node y position.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets a new value of the ACIDE - A Configurable IDE Node y position.
	 * @param y the new y position to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Returns if the ACIDE - A Configurable IDE Node position has been setted.
	 * @return if the ACIDE - A Configurable IDE Node position has been setted.
	 */
	public boolean isPosicionado() {
		return posicionado;
	}
	
	/**
	 * Sets if the ACIDE - A Configurable IDE Node has been positioned.
	 * @param posicionado the new value to set.
	 */
	public void setPosicionado(boolean posicionado) {
		this.posicionado = posicionado;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [id=" + id + ", label=" + label + ", x=" + x + ", y=" + y
				+ ", posicionado=" + posicionado + "]";
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + (posicionado ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (posicionado != other.posicionado)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return false;	//modif 0.17 (false)
	}

	/**
	 * Moves the nodes of the ACIDE - A Configurable IDE Node.
	 * 
	 * @param widthx the horizontal shift of the node.
	 * @param height the vertical shift of the node.
	 */
	public void move( int width, int height) {
		this.x+=width;
		this.y+=height;
	}

	public Color getNodeColor() {
		return nodeColor;
	}

	public void setNodeColor(Color color) {
		this.nodeColor = color;
	}


	
}
