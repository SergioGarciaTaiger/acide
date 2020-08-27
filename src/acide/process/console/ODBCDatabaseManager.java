/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      -Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
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
package acide.process.console;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

/**
 * ACIDE - A Configurable IDE ODBC data base manager.
 * 
 * @version 0.16
 * @see AcideDatabaseManager
 */
public class ODBCDatabaseManager extends AcideDatabaseManager {

	/**
	 * ACIDE - A Configurable IDE ODBC data base manager active connection
	 */
	private Connection _connection;
	
	/**
	 * ACIDE - A Configurable IDE ODBC data base manager sentence to execute
	 */
	private Statement _sentence;
	
	/**
	 * ACIDE - A Configurable IDE ODBC data base manager actual database connected
	 */
	private String _actualDB = "";
	
	/**
	 * ACIDE - A Configurable IDE ODBC data base manager databases users
	 */
	private HashMap<String, String> databases = new HashMap<String, String>();

	/**
	 * Returns all the databases connected to the panel
	 * 
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getDatabases() {
		
		Set<String> it = databases.keySet();
		
		Iterator<String> it2 = it.iterator();
		
		LinkedList<String> res = new LinkedList<String>();
		
		while(it2.hasNext()){
			
			res.add(it2.next());
			
		}
		
		return res;
	}

	/**
	 * Returns all the tables of the database
	 * 
	 * @param String database
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getTables(String database) {
		
		this.setAsDefault(database);

		LinkedList<String> _tables = new LinkedList<String>();
		
		try {
			
			DatabaseMetaData metadata = _connection.getMetaData();
			
			ResultSet set =  metadata.getTables(null, null, null, null);

			while (set.next()){
				
				if (set.getString(4).equals("TABLE")){
					
					_tables.add(set.getString(3));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.setAsDefault(_actualDB);
		
		return _tables;
	}

	/**
	 * Returns all the columns of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getFields(String database, String table) {

		LinkedList<String> _tables = new LinkedList<String>();
		
		try {
			DatabaseMetaData metadata = _connection.getMetaData();
			
			ResultSet set =  metadata.getColumns(null, null, table, null);
			
			while (set.next()){
				
				_tables.add(set.getString(4)+":"+set.getString(6));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return _tables;
	}

	/**
	 * Returns all the views of the database
	 * 
	 * @param String database
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getViews(String database) {

		LinkedList<String> _tables = new LinkedList<String>();
		
		try {
			
			DatabaseMetaData metadata = _connection.getMetaData();
			
			ResultSet set =  metadata.getTables(null, null, null, null);

			while (set.next()){
				
				if (set.getString(4).equals("VIEW")){
					
					_tables.add(set.getString(3));
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return _tables;
	}

	/**
	 * Returns all the functional dependencies of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getFuncionDep(String database, String table) {
		// Not implemented yet because the driver don't supported it
		return new LinkedList<String>();
	}

	/**
	 * Returns all the integrity constraints of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getIntConst(String database, String table) {
		// Not implemented yet because the driver don't supported it
		return new LinkedList<String>();
	}

	/**
	 * Returns the candidate key of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getCandidateKey(String database, String table) {
		// Not implemented yet because the driver don't supported it
		return new LinkedList<String>();
	}

	/**
	 * Returns all the foreign keys of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getForeignKey(String database, String table) {
		// Not implemented yet because the driver don't supported it
		return new LinkedList<String>();
	}

	/**
	 * Returns the primary key of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getPrimKey(String database, String table) {
		
		// driver don't supported this operation, so ww know the PK by the index created

		LinkedList<String> _tables = new LinkedList<String>();
		
		try {
			
			DatabaseMetaData metadata = _connection.getMetaData();
			
			ResultSet set =  metadata.getIndexInfo(null, null, table, true, true);

			while (set.next()){
				
				String type = set.getString(6);
				
				if (type != null){
					
					String name = set.getString(9);
					
					if (type.equals("PrimaryKey"))
						
						_tables.add(name);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return _tables;
	}

	/**
	 * Returns the SQL command of a view
	 * 
	 * @param String database
	 * @param String view
	 * @return String
	 */
	public String getSQLText(String database, String view) {
		// Not implemented yet because the driver don't supported it
		return "";
	}

	/**
	 * Returns the RA command of a view
	 * 
	 * @param String database
	 * @param String view
	 * @return String
	 */
	public String getRAText(String database, String view) {
		// Not implemented yet
		return "";
	}
	/**
	 * Returns the Datalog command of a view (NOT IMPLEMENTED)
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public String getDatalogText(String database, String view) {
		// Not implemented yet because it's a DES operation
		return "";
	}

	/**
	 * Returns all the rows of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getSelectAll(String database, String table) {
		
        //String res = setAsDefault(database);
        
        LinkedList<String> result = new LinkedList<String>();
        
        result.add("");
        
        String line = "";
       
        try {
                _sentence = _connection.createStatement();
                
                ResultSet result_set = _sentence.executeQuery("select * from "+table+"");
                
                int numColumns = result_set.getMetaData().getColumnCount();
 
                result.add("$"); 
                
                while(result_set.next()){
                	
                	line = "   ";
                        
                        for (int j = 1; j<= numColumns; j++){
                        	
                        		String tmp = result_set.getString(j);
                        		
                        		if (tmp!=null){
                        			
                            		if (tmp.startsWith("'"))
                            			
                            			tmp = tmp.substring(1);
                            		
                            		if (tmp.endsWith("'"))
                            			
                            			tmp = tmp.substring(0, tmp.length()-1);
                            		
                            		if (tmp.startsWith("timestamp(")){
                            			
                            			String aux = tmp.substring(tmp.indexOf("("), tmp.indexOf(")"));
                            			
                            			String ano = aux.substring(0, 4);
                            			
                            			aux = aux.substring(aux.indexOf(","));
                            			
                            			String mes = aux.substring(0, aux.indexOf(","));
                            			
                            			aux = aux.substring(aux.indexOf(","));
                            			
                            			String dia = aux.substring(0, aux.indexOf(","));
                            			
                            			tmp = dia+"-"+mes+"-"+ano;
                            		}
                            		if (tmp!=""){
                            			tmp = tmp.replace(" ", "-");
                            			
                            			result.add(tmp);
                            		}
                        		}else{
                        			result.add("null");
                        		}
                        }
                        line = line.substring(0, line.length()-1);
                      
                        result.add("$");
                }
                
        } catch (SQLException e) {
                e.printStackTrace();
        }
        
        result.removeLast();
        
        return result;
	}

	public LinkedList<String> getTableModel(String table) {
		// TODO
		return null;
	}

	/**
	 * Executes a command in the database connected
	 * 
	 * @param String command
	 * @return LinkedList<String>
	 */
	public LinkedList<String> executeCommand(String comandParam) {
		
		LinkedList<String> result = new LinkedList<String>();
		
		String line = "";
		
		try {
			
			_sentence = _connection.createStatement();
			
			ResultSet result_set = null;
			
			result.add("");
			
			if (comandParam.startsWith("CREATE")){
				
				_sentence.execute(comandParam);
				
			}else{
				
				result_set = _sentence.executeQuery(comandParam);
				
				 int numColumns = result_set.getMetaData().getColumnCount();
				 
	                result.add("$"); 
	                
	                while(result_set.next()){
	                	
	                	line = "   ";
	                        
	                        for (int j = 1; j<= numColumns; j++){
	                        	
	                        		String tmp = result_set.getString(j);
	                        		
	                        		if (tmp!=null){
	                        			
	                            		if (tmp.startsWith("'"))
	                            			
	                            			tmp = tmp.substring(1);
	                            		
	                            		if (tmp.endsWith("'"))
	                            			
	                            			tmp = tmp.substring(0, tmp.length()-1);
	                            		
	                            		if (tmp.startsWith("timestamp(")){
	                            			
	                            			String aux = tmp.substring(tmp.indexOf("("), tmp.indexOf(")"));
	                            			
	                            			String ano = aux.substring(0, 4);
	                            			
	                            			aux = aux.substring(aux.indexOf(","));
	                            			
	                            			String mes = aux.substring(0, aux.indexOf(","));
	                            			
	                            			aux = aux.substring(aux.indexOf(","));
	                            			
	                            			String dia = aux.substring(0, aux.indexOf(","));
	                            			
	                            			tmp = dia+"-"+mes+"-"+ano;
	                            		}
	                            		if (tmp!=""){
	                            			tmp = tmp.replace(" ", "-");
	                            			
	                            			result.add(tmp);
	                            		}
	                        		}else{
	                        			result.add("null");
	                        		}
	                        }
	                        line = line.substring(0, line.length()-1);
	                      
	                        result.add("$");
	                	}
	                }
			}
			
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		result.removeLast();
		
		return result; 
	}

	/**
	 * Returns the integrity constraints of a database
	 * 
	 * @param String database
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getIntConst(String database) {
		// Not implemented yet because the driver don't supported it
		return new LinkedList<String>();
	}

	/**
	 * Test the ODBC driver
	 * 
	 * @return String
	 */
	public String test() {
		
		try{
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			return "$success";
			
		} catch(Exception e) {
			
			System.out.println("No se ha cargado  Driver JDBC-ODBC");
			
			return "$error";
		}
	}

	/**
	 * Opens a new connection
	 * 
	 * @param String database
	 * @param String user
	 * @param String password
	 * @return String
	 */
	public String createDatabase(String database, String user, String password) {
		
		try {
			
			_connection = DriverManager.getConnection("jdbc:odbc:"+database, user, password);
			
			databases.put(database, user+"+"+password);
			
			_actualDB = database;
			
			return "$success";
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return "$error";
		}
	}

	/**
	 * Makes a database the default connection
	 * 
	 * @param String database
	 * @return LinkedList<String>
	 */
	public String setAsDefault(String database) {
		try {
			
			String user="", password="";
			
			String info = databases.get(database);
			
			if (info!=null){
				
				user = info.substring(0, info.indexOf("+"));
				
				password= info.substring(info.indexOf("+"));
			}
			
			_connection = DriverManager.getConnection("jdbc:odbc:"+database, user, password);
			
			databases.put(database, user+"+"+password);
			
			return "$success";
			
		} catch (SQLException e) {
			
			return "$error";
			
		}
	}

	/**
	 * Close the connection with the given database
	 * 
	 * @param String database
	 * @return LinkedList<String>
	 */
	public String closeConnection(String database) {
		
		try {
			
			_connection.close();
			
			databases.remove(database);
			
			return "$success";
			
		} catch (SQLException e) {
			
			return "$error";
			
		}
	}

	/**
	 * Drop the gicen restriction
	 * 
	 * @param String database
	 * @param String table
	 * @param String type_restriction
	 * @param String restriction
	 * @return LinkedList<String>
	 */
	public String dropRestriction(String restriction) {
		
		// Not implemented yet because the driver don't supported it
		return "$success";
	}

	/**
	 * Returns if a relation exists
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public boolean existsRelation(String database, String table) {

		setAsDefault(database);
		
		try {
			DatabaseMetaData metadata = _connection.getMetaData();
			
			ResultSet set =  metadata.getTables(null, null, null, null);
			
			while (set.next()){
				
				if (set.getString(4).equals("TABLE") && set.getString(3).equals(table)){
					
					return true;
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Inserts values in a table
	 * 
	 * @param String table
	 * @param Vector<String> dataColumns
	 * @return String
	 */
	public String insertValues(String table, Vector<String> dataColumns) {
		
		try {
			
			_sentence = _connection.createStatement();
			
			String values = "";
			
			for (int i = 0; i<dataColumns.size(); i++){
				
				values += dataColumns.get(i)+", ";
				
			}
			
			values= values.substring(0, values.length()-2);
			
			_sentence.execute("INSERT INTO "+ table+" VALUES("+values+")");
			
			return "success";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "error";
	}

	/**
	 * Drop a view
	 * 
	 * @param String database
	 * @param String view
	 * @return String
	 */
	public String dropView(String database, String view) {
		
		setAsDefault(database);
		
		try {
			
			_sentence = _connection.createStatement();
			
			_sentence.executeUpdate("DROP TABLE "+view);
			
			_connection.commit();
			
			return "$success";
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return "$error";
			
		}
	}

	/**
	 * Renames a view
	 * 
	 * @param String database
	 * @param String view
	 * @param String newName
	 * @return String
	 */
	public String renameView(String database, String view, String newName) {
		// Not implemented yet because the driver don't supported it
		return "$success";
	}

	/**
	 * Paste a view
	 * 
	 * @param String database
	 * @param String newview
	 * @param String oldview
	 * @return String
	 */
	public String pasteView(String database, String newView, String oldView) {
		// Not implemented yet because the driver don't supported it
		return "$success";
	}

	/**
	 * Creates a new restriction
	 * 
	 * @param String restriction
	 * @return String
	 */
	public String createRestriction(String restriction) {
		// Not implemented yet because the driver don't supported it
		return "$success";
	}

	/**
	 * Drop a table
	 * 
	 * @param String database
	 * @param String table
	 * @return String
	 */
	public LinkedList<String> dropTable( String database, String table) {
		
		LinkedList<String> res = null;
		
		setAsDefault(database);
		
		try {
			
			_sentence = _connection.createStatement();
			
			_sentence.executeUpdate("DROP TABLE "+table+"");
			
			_connection.commit();
			
			return res;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return res;
			
		}
	}

	/**
	 * Paste a table
	 * 
	 * @param String database
	 * @param String result
	 * @param String option
	 * @return String 
	 */
	public String pasteTable(String s, String result, int option) {
		
		try {
			
			_sentence = _connection.createStatement();
			
			_sentence.execute("CREATE TABLE "+s+" LIKE "+result);
			
			if(option == 1){
				
				_sentence = _connection.createStatement();
				
				_sentence.execute("INSERT INTO "+s+" SELECT * FROM "+result);
			}
			
			return "success";
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return "Error";
			
		}
	}

	/**
	 * Renames a table
	 * 
	 * @param String database
	 * @param String table
	 * @param String newNAme
	 * @return String
	 */
	public String renameTable(String database, String table, String selection) {
		
		setAsDefault(database);
		
		try {
			
			_sentence = _connection.createStatement();
			
			_sentence.execute("RENAME TABLE "+table+" TO "+selection+"");
			
			_connection.commit();
			
			return "success";
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return "Error";
			
		}
	}

	/**
	 * Returns the info of the restrictions
	 * 
	 * @param String database
	 * @param String table
	 * @return Vector<Boolean>
	 */
	public Vector<Boolean> infoRestrictions(String db, String tabla) {
		
		Vector<Boolean> res = new Vector<Boolean>();
		
		res.add(false);
		
		LinkedList<String> result = getPrimKey(db, tabla);
		
		res.add(!result.isEmpty());
		
		result = getCandidateKey(db, tabla);
		
		res.add(!result.isEmpty());
		
		result = getForeignKey(db, tabla);
		
		res.add(!result.isEmpty());
		
		result = getFuncionDep(db, tabla);
		
		res.add(!result.isEmpty());
		
		result = getIntConst(db, tabla);
		
		res.add(!result.isEmpty());
		
		return res;
	}

	/**
	 * return the nullables fields of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getNullables(String string, String substring) {
		// Not implemented yet because the driver don't supported it
		return new LinkedList<String>();
	}

	/**
	 * Return the columns of a view
	 * 
	 * @param String database
	 * @param String view
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getViewFields(String database, String view) {

		LinkedList<String> _tables = new LinkedList<String>();

		setAsDefault(database);
		
		try {
			
			DatabaseMetaData metadata = _connection.getMetaData();
			
			ResultSet set =  metadata.getColumns(null, null, view, null);
			
			while (set.next()){
				
				_tables.add(set.getString(4)+":"+set.getString(6));
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return _tables;
	}

	/**
	 * Returns if a name is of a table
	 * 
	 * @param String database
	 * @param String table
	 * @return boolean
	 */
	public boolean isTable(String db, String tabla) {
		
		
		setAsDefault(db);
		
		try {
			
			DatabaseMetaData metadata = _connection.getMetaData();
			
			ResultSet set =  metadata.getTables(null, null, null, null);
			
			while (set.next()){
				
				if (set.getString(4).equals("TABLE") && set.getString(3).equals(tabla)){
					
					return true;
					
				}
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return false;
	}

	/**
	 * Delete a row of a table
	 * 
	 * @param String database
	 * @param String table
	 * @param Vector<Vector<String>> dataColumns
	 * @param Vector<String> columnsNames
	 * @return String
	 */
	public String deleteRow(String database,String table, Vector<Vector<String>> dataColumns, Vector<String> columnsNames) {
		
		setAsDefault(database);
		
		try {
			
			for(int row=0; row<dataColumns.size(); row++){
				
				_sentence = _connection.createStatement();
				
				String where = "";
				
				for (int i = 0; i<dataColumns.size(); i++){
					
					where += columnsNames.get(i)+" = '"+dataColumns.get(row).get(i)+"' and ";
					
				}
				
				where= where.substring(0, where.length()-5);
				
				_sentence.execute("DELETE FROM "+ table+" WHERE "+where);

			}
			
			return "success";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "error";
	}

	/**
	 * Returns if a table is empty
	 * 
	 * @param String database
	 * @param String table
	 * @return boolean
	 */
	public boolean isEmpty(String db, String table) {
		return false;
	}

	@Override
	public boolean pk(String db, String table,LinkedList<String> pk) {
		return  false;
	}

	@Override
	public boolean nn(String db, String table,LinkedList<String> nn) {
		return false;
	}

	@Override
	public boolean type(String db, String table,LinkedList<String> cols) {
		return false;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public String updateField(String table, String database,Vector<String> columnsNames, Vector infoRow, Vector newInfoRow) {
		
		setAsDefault(database);
		
		try {
			_sentence = _connection.createStatement();

			String update = "", where = "WHERE ", fullCommand="UPDATE "+ table +" SET ";
			
			for (int i = 0; i<columnsNames.size(); i++){
				
				if (newInfoRow.get(i)!=null)
					update += columnsNames.get(i) + " = "+ newInfoRow.get(i)+",";
				else
					update += columnsNames.get(i) + "is null,";
				
				//String a = (String) infoRow.get(i);
				
				
				if (infoRow.get(i)!=null && infoRow.get(i)!=""){
					where += columnsNames.get(i) + " = "+ infoRow.get(i)+" and ";
				}
				else
					where += columnsNames.get(i) + " is null and ";
			}
			
			update = update.substring(0, update.length()-1);
			where = where.substring(0, where.length()-5);
			
			fullCommand += update +" "+ where;
			
			_sentence.execute(fullCommand);
			
			_connection.commit();
			
			return "ok";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "no";
	}

	@Override
	public String currentDB() {
		return _actualDB;
	}
	
	/**
	 * Returns the type of a given view
	 * @return
	 */
	public String getViewType(String database, String node){
		// Not implemented yet because the driver don't supported it
		return "";
	}
	
	public LinkedList<String> orderColumns(String table, String command){
		// Not implemented yet because the driver don't supported it
		return null;
	}

	@Override
	public LinkedList<String> startDebug(String view, String debugConfiguration, String option) {
		//TODO
		return null;
	}

	@Override
	public LinkedList<String> setNodeState(String nodeName, String state) {
		//TODO
		return null;
	}

	@Override
	public LinkedList<String> debugCurrentQuestion() {
		//TODO
		return null;
	}

	@Override
	public LinkedList<String> debugCurrentAnswer(String question, String answer) {
		//TODO
		return null;
	}

	@Override
	public void stopDebug() {
		//TODO
	}

	@Override
	public LinkedList<String> getDebugStats() {
		//TODO
		return null;
	}

	public boolean isDebugging(){
		//TODO
		return true;
	}
}