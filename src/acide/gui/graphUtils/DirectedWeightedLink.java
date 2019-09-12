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
/**
 * 
 * ACIDE - A Configurable IDE directed weighted link
 *
 * @version 0.12
 * @see {@link #DirectedWeightedGraph}.
 */
public class DirectedWeightedLink {

	/**
	 * ACIDE - A Configurable IDE Link id.
	 */
	private int id;
	/**
	 * ACIDE - A Configurable IDE origin node of the link.
	 */
	private Node origin;
	/**
	 * ACIDE - A Configurable IDE destiny node of the link.
	 */
	private Node destiny;
	/**
	 * ACIDE - A Configurable IDE weight of the link.
	 */
	private int weight;
	
	/**
	 * Creates a ACIDE - A Configurable IDE Link.
	 * 
	 */
	public DirectedWeightedLink(){
		
	}
	
	/**
	 * Creates a ACIDE - A Configurable IDE Link.
	 * 
	 * @param id identifier of the link.
	 * @param origin node of the link.
	 * @param destiny node of the link.
	 * @param wheight of the link.
	 */
	public DirectedWeightedLink(int id, Node origin, Node destiny, int weight) {
		super();
		this.id = id;
		this.origin = origin;
		this.destiny = destiny;
		this.weight = weight;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE link id.
	 * @return the ACIDE - A Configurable IDE link id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets a new values of the ACIDE - A Configurable IDE link id.
	 * @param id the new value to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE origin node of the link.
	 * @return the ACIDE - A Configurable IDE origin node of the link.
	 */
	public Node getOrigin() {
		return origin;
	}

	/**
	 * Sets a new values of the ACIDE - A Configurable IDE link origin.
	 * @param origin the new value to set.
	 */
	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE destiny node of the link.
	 * @return the ACIDE - A Configurable IDE destiny node of the link.
	 */
	public Node getDestiny() {
		return destiny;
	}

	/**
	 * Sets a new values of the ACIDE - A Configurable IDE link destiny.
	 * @param origin the new value to set.
	 */
	public void setDestiny(Node destiny) {
		this.destiny = destiny;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE weight of the link.
	 * @return the ACIDE - A Configurable IDE weight node of the link.
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets a new values of the ACIDE - A Configurable IDE link weight.
	 * @param weight the new value to set.
	 */
	public void setWheight(int weight) {
		this.weight = weight;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DirectedWeightedLink [id=" + id + ", origin=" + origin
				+ ", destiny=" + destiny + ", weight=" + weight + "]";
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destiny == null) ? 0 : destiny.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + weight;
		return result;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectedWeightedLink other = (DirectedWeightedLink) obj;
		if (destiny == null) {
			if (other.destiny != null)
				return false;
		} else if (!destiny.equals(other.destiny))
			return false;		
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
	
	
	
	
}
