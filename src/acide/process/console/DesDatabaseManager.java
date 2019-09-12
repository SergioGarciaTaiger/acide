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
package acide.process.console;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JTextPane;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.consolePanel.AcideConsolePanel;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE des database manaeger.
 *
 * @version 0.16
 * @see AcideDatabaseManager
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DesDatabaseManager extends AcideDatabaseManager {

	/**
	 * ACIDE - A Configurable IDE textPane to read the results from the shell.
	 */
	JTextPane _textComponent;

	/**
	 * ACIDE - A Configurable IDE main window unique class instance.
	 */
	public DesDatabaseManager() {

		_textComponent = new JTextPane();

	}

	/**
	 * Returns all the databases connected to the panel
	 * 
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getDatabases() {

		LinkedList<String> list = executeCommand("/show_dbs");

		return list;

	}

	/**
	 * Returns all the tables of the database
	 * 
	 * @param String
	 *            database
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getTables(String database) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/tapi /list_table_schemas");

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns all the columns of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getFields(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> columns = new LinkedList<String>();

		columns = executeCommand("/tapi /use_db " + database);

		columns = executeCommand("/list_table_schemas");

		boolean found = false;

		int i = 0;

		String info = "";

		while (!found && i < columns.size()) {

			String nameTable = table;

			if (table.contains("("))
				nameTable = table.substring(0, table.indexOf("("));

			if (columns.get(i).startsWith(nameTable)) {

				int index = columns.get(i).indexOf("(");

				if (index > 0)

					info = columns.get(i).substring(
							columns.get(i).indexOf("(") + 1,
							columns.get(i).lastIndexOf(")"));

				else

					info = columns.get(i);

				found = true;

			} else
				i++;
		}

		String[] fields = info.split(",");

		for (int j = 0; j < fields.length; j++)

			ret.add(fields[j]);

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	public LinkedList<String> getViewFields(String database, String view) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> columns = new LinkedList<String>();

		columns = executeCommand("/tapi /use_db " + database);

		columns = executeCommand("/dbschema " + view);

		boolean found = false;

		int i = 0;

		String info = "";

		while (!found && i < columns.size()) {

			if (columns.get(i).startsWith(" * " + view)) {

				info = columns.get(i).substring(
						columns.get(i).indexOf("(") + 1,
						columns.get(i).lastIndexOf(")"));

				found = true;

			} else
				i++;

		}

		String[] fields = info.split(",");

		for (int j = 0; j < fields.length; j++)

			ret.add(fields[j]);

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns all the views of the database
	 * 
	 * @param String
	 *            database
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getViews(String database) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/list_view_schemas");

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns the full name of the view
	 * 
	 * @param String
	 *            database, String view
	 * @return String
	 */
	public String getView(String database, String oldView) {

		String view = "";
		String childClear = "";
		boolean found = false;

		int i = 0;

		LinkedList<String> views = getViews(database);

		while ((i < views.size()) && (!found)) {
			String aux = views.get(i);
			aux = aux.substring(0, aux.indexOf("("));
			if (aux.equals(oldView)) {
				view = views.get(i);
				found = true;
			}
			i++;
		}

		if (found) {
			String[] cleanViews = view.split(",");
			for (int j = 0; j < cleanViews.length; j++) {
				if (j == (cleanViews.length - 1))
					childClear = childClear
							+ cleanViews[j].substring(0,
									cleanViews[j].indexOf(":")) + ")";
				else
					childClear = childClear
							+ cleanViews[j].substring(0,
									cleanViews[j].indexOf(":")) + ",";
			}

		}
		return childClear;

	}

	/**
	 * Returns all the functional dependencies of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getFuncionDep(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> cons = new LinkedList<String>();

		cons = executeCommand("/tapi /use_db " + database);

		cons = executeCommand("/list_table_constraints " + table);

		boolean found = false;

		int i = 0;

		while (!found && i < cons.size()) {

			if (cons.get(i).startsWith("    - FD")) {

				found = true;

			} else
				i++;

		}

		found = false;

		while (!found && i < cons.size()) {

			String c;

			if (cons.get(i).contains("    - ")
					&& !cons.get(i).contains("    - FD"))

				found = true;

			else {

				if (cons.get(i).startsWith("    - FD"))

					c = cons.get(i).replaceAll("    - FD:", "");

				else

					c = cons.get(i);

				ret.add(c);

				i++;

			}
		}

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns all the integrity constraints of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getIntConst(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> cons = new LinkedList<String>();

		cons = executeCommand("/tapi /use_db " + database);

		cons = executeCommand("/list_table_constraints " + table);

		boolean found = false;

		int i = 0;

		while (!found && i < cons.size()) {

			if (cons.get(i).startsWith("    - IC")) {

				found = true;

			} else
				i++;

		}

		found = false;

		while (!found && i < cons.size()) {

			String c = "";

			if (cons.get(i).contains("    - ")
					&& !cons.get(i).contains("    - IC"))

				found = true;

			else {

				if (cons.get(i).startsWith("    - IC"))

					c = cons.get(i).replaceAll("    - IC:", "");

				else {

					while (i < cons.size() && !cons.get(i).startsWith(":-")
							&& !cons.get(i).startsWith("    - IC")) {
						String aux = cons.get(i);
						aux = aux.replace("      ", "");
						aux = aux.replace("        ", "");
						c = c + aux;
						i++;
					}

					ret.add(c);
				}

				i++;

			}

		}

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns the candidate key of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getCandidateKey(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> cons = new LinkedList<String>();

		cons = executeCommand("/tapi /use_db " + database);

		cons = executeCommand("/list_table_constraints " + table);

		boolean found = false;

		int i = 0;

		while (!found && i < cons.size()) {

			if (cons.get(i).startsWith("    - CK")) {

				found = true;

			} else
				i++;

		}

		found = false;

		while (!found && i < cons.size()) {

			String c;

			if (cons.get(i).contains("    - ")
					&& !cons.get(i).contains("    - CK"))

				found = true;

			else {

				if (cons.get(i).startsWith("    - CK"))

					c = cons.get(i).replaceAll("    - CK:", "");

				else

					c = cons.get(i);

				ret.add(c);

				i++;

			}

		}

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns all the foreign keys of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getForeignKey(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> cons = new LinkedList<String>();

		if (table.contains("(")) {

			table = table.substring(0, table.indexOf("("));

		}

		cons = executeCommand("/tapi /use_db " + database);

		cons = executeCommand("/list_table_constraints " + table);

		boolean found = false;

		int i = 0;

		while (!found && i < cons.size()) {

			if (cons.get(i).startsWith("    - FK")) {

				found = true;

			} else
				i++;

		}

		found = false;

		while (!found && i < cons.size()) {

			String c;

			if (cons.get(i).contains("    - ")
					&& !cons.get(i).contains("    - FK"))

				found = true;

			else {

				if (cons.get(i).startsWith("    - FK"))

					c = cons.get(i).replaceAll("    - FK: ", "");

				else

					c = cons.get(i);

				ret.add(c);

				i++;

			}

		}

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns the primary key of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getPrimKey(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> cons = new LinkedList<String>();

		cons = executeCommand("/tapi /use_db " + database);

		cons = executeCommand("/list_table_constraints " + table);

		boolean found = false;

		int i = 0;

		while (!found && i < cons.size()) {

			if (cons.get(i).startsWith("    - PK"))
				found = true;

			else
				i++;

		}

		found = false;

		while (!found && i < cons.size()) {

			String c;

			if (cons.get(i).contains("    - ")
					&& !cons.get(i).contains("    - PK"))

				found = true;

			else {

				if (cons.get(i).startsWith("    - PK"))
					c = cons.get(i).replaceAll("    - PK:", "");

				else
					c = cons.get(i);

				ret.add(c);

				i++;

			}

		}

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	public LinkedList<String> getNullables(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> cons = new LinkedList<String>();

		cons = executeCommand("/tapi /use_db " + database);

		cons = executeCommand("/list_table_constraints " + table);

		boolean found = false;

		int i = 0;

		while (!found && i < cons.size()) {

			if (cons.get(i).startsWith("    - NN")) {

				found = true;

			} else
				i++;

		}

		found = false;

		while (!found && i < cons.size()) {

			String c;

			if (cons.get(i).contains("    - ")
					&& !cons.get(i).contains("    - NN"))

				found = true;

			else {

				if (cons.get(i).startsWith("    - NN"))

					c = cons.get(i).replaceAll("    - NN:", "");

				else

					c = cons.get(i);

				ret.add(c);

				i++;

			}

		}

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Returns the type of the view: SQL or RA
	 * 
	 * @return String
	 */
	public String getViewType(String database, String view) {

		LinkedList<String> ret = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/tapi /dbschema " + view);

		String result = "";

		boolean typeFound = false;

		int i = 0;

		while (!typeFound && i < ret.size()) {

			if (ret.get(i).contains("$sql")) {
				result = "SQL";
				typeFound = true;
			} else if (ret.get(i).contains("$ra")) {
				result = "RA";
				typeFound = true;
			}
			i++;
		}

		return result;
	}

	/**
	 * Returns the SQL command of a view
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            view
	 * @return String
	 */
	public String getSQLText(String database, String view) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/dbschema " + view);

		String result = "";

		boolean foundSQL = false;

		int i = 0;

		while (!foundSQL && i < ret.size()) {

			if (ret.get(i).contains("    - Defining SQL statement:")) {

				foundSQL = true;

			}

			i++;

		}

		String aux = "";

		while (i < ret.size() && !aux.contains("- Datalog equivalent rules:")) {

			result += aux;

			aux = ret.get(i) + "\n";

			i++;
		}

		if (result.length() > 0) {

			result = result.substring(0, result.length() - 1);

			result = result.replace("        ", "");

		}

		executeCommand("/tapi /use_db " + db);

		return result;

	}

	/**
	 * Returns the RA command of a view
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            view
	 * @return String
	 */
	public String getRAText(String database, String view) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/dbschema " + view);

		String result = "";

		boolean foundRA = false;

		int i = 0;

		while (!foundRA && i < ret.size()) {

			if (ret.get(i).contains("    - Defining RA statement:")) {

				foundRA = true;

			}

			i++;

		}

		String aux = "";

		while (i < ret.size() && !aux.contains("- Datalog equivalent rules:")) {

			result += aux;

			aux = ret.get(i) + "\n";

			i++;
		}

		if (result.length() > 0) {

			result = result.substring(0, result.length() - 1);

			result = result.replace("        ", "");

		}

		executeCommand("/tapi /use_db " + db);

		return result;

	}

	/**
	 * Returns the Datalog command of a view
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public String getDatalogText(String database, String view) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/dbschema " + view);

		String result = "";

		boolean foundDatalog = false;

		int i = 0;

		while (!foundDatalog && i < ret.size()) {

			if (ret.get(i).contains("- Datalog equivalent rules:")) {

				foundDatalog = true;

			}

			i++;

		}

		while (i < ret.size() && !ret.get(i).contains("constraints.")) {

			result += ret.get(i) + "\n";

			i++;

		}

		if (result.length() > 0) {

			result = result.substring(0, result.length() - 1);

			result = result.replace("        ", "");

		}

		executeCommand("/tapi /use_db " + db);

		return result;

	}

	/**
	 * Returns all the rows of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getSelectAll(String database, String table) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		LinkedList<String> res = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/tapi select * from " + table);

		int j = 0;

		boolean error = false;

		while (j < ret.size() && !error) {

			if (ret.get(j).contains("error"))

				error = true;

			j++;

		}

		if (error)

			return ret;

		if (!database.equals("$des"))

			res.add("");

		for (int i = 0; i < ret.size(); i++) {

			String line = ret.get(i);

			if (line.contains("answer"))

				continue;

			else
				res.add(line);

		}

		executeCommand("/tapi /use_db " + db);

		return res;

	}

	/**
	 * Executes a command in the database connected
	 * 
	 * @param String
	 *            command
	 * @return LinkedList<String>
	 */
	public LinkedList<String> executeCommand(String comandParam) {

		LinkedList<String> result = new LinkedList<String>();

		try {

			LinkedList<String> info = executeCommandfinal("/show_compilations");

			if (!info.isEmpty() && info.get(0).contains("is on")) {

				executeCommandfinal("/show_compilations off");

				result = executeCommandfinal(comandParam);

				executeCommandfinal("/show_compilations on");

			} else {

				result = executeCommandfinal(comandParam);

			}

			return result;

		} catch (Exception e) {

			result.add("error");

			return result;

		}

	}

	public synchronized LinkedList<String> executeCommandfinal(
			String commandParam) {

		LinkedList<String> res = new LinkedList<String>();

		AcideConsolePanel panel = AcideMainWindow.getInstance()
				.getConsolePanel();

		panel.getProcessThread().getOutputGobbler().set_sendToConsole(false);

		panel.sendCommandToConsole(commandParam, "");

		boolean received = panel.getProcessThread().getOutputGobbler()
				.waitForOKResult(2200);

		if (received) {

			String[] lines = panel.getProcessThread().getOutputGobbler()
					.getText().split("\n");

			for (int i = 0; i < lines.length; i++) {

				// dates to catch for ddbb
				if (!lines[i].startsWith("*")
						&& !lines[i].equalsIgnoreCase("$eot")
						//&& !lines[i].startsWith("DES>")
						&& !lines[i].startsWith(">")
						&& !lines[i].startsWith("DES")
						&& !lines[i].startsWith("HR")
						//&& !lines[i].startsWith("DES:$des>")
						&& !lines[i].startsWith("?-")
						//&& !lines[i].equals("$error")
						&& !lines[i].equals("$success")
						&& !lines[i].contains("already in use.")
						&& !lines[i].endsWith("$eot")) {

					if (!lines[i].matches("")) {

						String line = lines[i];

						res.add(line);

					}
				}
			}
		}

		panel.getProcessThread().getOutputGobbler().set_text("KO");

		panel.getProcessThread().getOutputGobbler().set_sendToConsole(true);

		return res;
	}

	/**
	 * Returns the integrity constraints of a database
	 * 
	 * @param String
	 *            database
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getIntConst(String database) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		ret = executeCommand("/tapi /use_db " + database);

		ret = executeCommand("/dbschema");

		int i = 0, j = 0;

		while (i < 4 && j < ret.size()) {

			if (ret.get(j).startsWith("Info"))

				i++;

			j++;

		}

		if (i == 4) {

			String intConst = ret.get(j - 1);

			ret.clear();

			intConst = intConst.substring(intConst.indexOf(":") + 1,
					intConst.length());

			if (!intConst.startsWith(" No integrity")) {

				String[] results = intConst.split(",");

				for (int k = 0; k < results.length; k++) {

					ret.add(results[k]);

				}

			}

		}

		executeCommand("/tapi /use_db " + db);

		return ret;

	}

	/**
	 * Test the ODBC driver
	 * 
	 * @return String
	 */
	public String test() {

		String shellPath = AcideProjectConfiguration.getInstance()
				.getShellPath();

		// LinkedList<String> lines = null;
		LinkedList<String> lines = executeCommand("/tapi /test_tapi");
		/*
		 * if (shellPath != null && (shellPath.endsWith("\\des.exe") ||
		 * shellPath .endsWith("/des"))) { lines =
		 * executeCommand("/tapi /test_tapi"); }
		 */

		if (lines.equals("$success") || lines == null || lines.size() == 0)
			return "$success";

		return "$error";
	}

	/**
	 * Opens a new connection
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            user
	 * @param String
	 *            password
	 * @return String
	 */
	public String createDatabase(String database, String user, String password) {
		LinkedList<String> red = new LinkedList<String>();

		red = executeCommand("/tapi /open_db '" + database + "'");

		boolean error = false;

		int i = 0;

		while (i < red.size() && !error) {

			if (red.get(i).contains("$error") || red.get(i).contains("Error"))

				error = true;

			else
				i++;

		}

		if (error)
			return "$error";

		else
			return "$success";

	}

	/**
	 * Makes a database the default connection
	 * 
	 * @param String
	 *            database
	 * @return LinkedList<String>
	 */
	public String setAsDefault(String database) {

		LinkedList<String> red = new LinkedList<String>();

		red = executeCommand("/tapi /use_db " + database);

		boolean error = false;

		int i = 0;

		while (i < red.size() && !error) {

			if (red.get(i).contains("$error") || red.get(i).contains("Error"))

				error = true;

			else
				i++;

		}

		if (error)
			return "$error";

		else
			return "$success";

	}

	/**
	 * Close the connection with the given database
	 * 
	 * @param String
	 *            database
	 * @return LinkedList<String>
	 */
	public String closeConnection(String database) {

		LinkedList<String> red = new LinkedList<String>();

		red = executeCommand("/tapi /use_db " + database);

		red = executeCommand("/tapi /close_db");

		boolean error = false;

		int i = 0;

		while (i < red.size() && !error) {

			if (red.get(i).contains("$error") || red.get(i).contains("Error"))

				error = true;

			else
				i++;

		}

		if (error)
			return "$error";

		else
			return "$success";

	}

	public String createTable(String command) {

		String db = currentDB();

		LinkedList<String> ret = new LinkedList<String>();

		String table = command.substring(12, command.length());

		table = table.substring(0, table.indexOf(" "));

		ret = executeCommand("/tapi /relation_exists " + table);

		executeCommand("/tapi /use_db " + db);

		if (ret.get(0).contains("true")) {

			return "exists";

		} else {

			ret = executeCommand(command);

			if (ret.get(0).contains("error"))
				return "error";

			return "success";

		}
	}

	/**
	 * Drop the given restriction
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @param String
	 *            type_restriction
	 * @param String
	 *            restriction
	 * @return LinkedList<String>
	 */
	public String dropRestriction(String restriction) {

		String db = currentDB();

		String command = "/tapi /drop_ic " + restriction;

		LinkedList<String> res = executeCommand(command);

		boolean success = true;

		if (res.size() == 1)
			success = !res.get(0).contains("$error");

		else {
			Iterator<String> it = res.iterator();

			while (it.hasNext()) {
				success = it.next().contains("$success");
			}
		}

		executeCommand("/tapi /use_db " + db);

		if (success)
			return "$success";

		else
			return res.get(2);

	}

	/**
	 * Returns if a relation exists
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return LinkedList<String>
	 */
	public boolean existsRelation(String database, String table) {

		String db = currentDB();

		LinkedList<String> result = executeCommand("/tapi /use_db " + database);

		result = executeCommand("/tapi /relation_exists " + table);

		executeCommand("/tapi /use_db " + db);

		if (result.get(0).contains("true"))
			return true;

		return false;

	}

	/**
	 * Inserts values in a table
	 * 
	 * @param String
	 *            table
	 * @param Vector
	 *            <String> dataColumns
	 * @return String
	 */
	public String insertValues(String table, Vector<String> dataColumns) {

		String db = currentDB();

		String result = "ko";

		String data = String.valueOf(dataColumns.get(0));

		for (int i = 1; i < dataColumns.size(); i++) {
			data = data + "," + dataColumns.get(i);

		}

		LinkedList<String> ret = executeCommand("/tapi insert into [" + table
				+ "] values(" + data + ")");

		executeCommand("/tapi /use_db " + db);

		if (ret.size() == 1)
			result = "success";

		else
			result = ret.get(2);

		return result;
	}

	/**
	 * Drop a view
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            view
	 * @return String
	 */
	public String dropView(String database, String view) {
		String db = currentDB();

		LinkedList<String> result = executeCommand("/tapi /use_db '" + database
				+ "'");

		result = executeCommand("/tapi drop view " + view);

		boolean error = false;

		int i = 0;

		while (i < result.size() && !error) {

			if (result.get(i).contains("$error")
					|| result.get(i).contains("Error"))

				error = true;

			else
				i++;
		}

		executeCommand("/tapi /use_db " + db);

		if (!error)
			return "success";

		return result.get(2);
	}

	/**
	 * Renames a view
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            view
	 * @param String
	 *            newName
	 * @return String
	 */
	public String renameView(String database, String view, String newName) {

		String db = currentDB();

		LinkedList<String> result = executeCommand("/tapi /use_db '" + database
				+ "'");

		result = executeCommand("/tapi rename view " + view + " to " + newName);

		boolean error = false;

		int i = 0;

		while (i < result.size() && !error) {

			if (result.get(i).contains("$error")
					|| result.get(i).contains("Error"))

				error = true;

			else
				i++;

		}

		executeCommand("/tapi /use_db " + db);

		if (!error)
			return "success";

		return "error";

	}

	/**
	 * Paste a view
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            newview
	 * @param String
	 *            oldview
	 * @return String
	 */
	public String pasteView(String database, String newView, String oldView) {

		String db = currentDB();

		LinkedList<String> result = new LinkedList<String>();

		result = executeCommand("/tapi /use_db '" + database + "'");

		result = executeCommand("/multiline on");

		String type = getViewType(database, oldView);

		String text = "";

		String command = "";

		if (type.equals("SQL")) {
			String columnsOldView = getView(database, oldView);
			columnsOldView = columnsOldView.substring(oldView.length());
			newView = newView + columnsOldView;
			text = getSQLText(database, oldView);
			text = text.replace("\n", " ");
			text = text.substring(0, text.length() - 1);
			command = "/tapi create view " + newView + " as " + text;
		} else if (type.equals("RA")) {
			text = getRAText(database, oldView);
			text = text.replace("\n", " ");
			text = text.substring(0, text.length() - 1);
			command = "/tapi " + newView + " := " + text;
		}

		result = executeCommand(command);

		result = executeCommand("/multiline off");

		boolean error = false;

		int i = 0;

		while (i < result.size() && !error) {

			if (result.get(i).contains("$error")
					|| result.get(i).contains("Error"))

				error = true;

			else
				i++;

		}

		executeCommand("/tapi /use_db " + db);

		if (!error)
			return "success";

		return result.get(2);
	}

	/**
	 * Creates a new restriction
	 * 
	 * @param String
	 *            restriction
	 * @return String
	 */
	public String createRestriction(String restriction) {

		String db = currentDB();

		String command = "/tapi " + restriction;

		LinkedList<String> res = executeCommand(command);

		boolean success = true;
		String result = "";

		if (!res.isEmpty())
			if (res.get(0).contains("$error")) {
				success = false;
				for (int i = 2; i < res.size(); i++) {
					String aux = res.get(i).trim();
					result = result + aux + "\n";
				}
			}

		executeCommand("/tapi /use_db " + db);

		if (success)
			return "$success";

		else
			return result;
	}

	/**
	 * Drop a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return String
	 */
	public LinkedList<String> dropTable(String database, String table) {

		String db = currentDB();

		executeCommand("/tapi /use_db " + database);

		String command = "/tapi drop table " + table;

		LinkedList<String> res = executeCommand(command);

		executeCommand("/tapi /use_db " + db);

		return res;
	}

	/**
	 * Paste a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            result
	 * @param String
	 *            option
	 * @return String
	 */
	public String pasteTable(String destino, String origen, int option) {

		String db = currentDB();

		String command = "/tapi create table " + destino + " like " + origen;

		LinkedList<?> res = executeCommand(command);

		if (option == 1) {

			command = "/tapi insert into " + destino + " select * from "
					+ origen;

			res = executeCommand(command);
		}

		executeCommand("/tapi /use_db " + db);

		if (res.isEmpty() || res.size() == 1)
			return "success";

		else
			return (String) res.get(2);
	}

	/**
	 * Renames a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @param String
	 *            newNAme
	 * @return String
	 */
	public String renameTable(String database, String table, String seleccion) {

		String db = currentDB();

		executeCommand("/tapi /use_db " + database);

		String command = "/tapi rename table " + table + " to " + seleccion;

		LinkedList<String> res = executeCommand(command);

		executeCommand("/tapi /use_db " + db);

		if (res.isEmpty() || res.size() == 1)
			return "success";

		else
			return (String) res.get(2);
	}

	public boolean isTable(String db, String tabla) {

		String currentDB = currentDB();

		LinkedList<String> result = executeCommand("/tapi /use_db " + db);

		result = executeCommand("/list_tables");

		executeCommand("/tapi /use_db " + currentDB);

		int i = 0;

		boolean found = false;

		while (!found && i < result.size()) {

			found = result.get(i).equals(tabla);

			i++;

		}

		return found;

	}

	/**
	 * Returns the info of the restrictions
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return Vector<Boolean>
	 */
	public Vector<Boolean> infoRestrictions(String db, String tabla) {

		String currentDB = currentDB();

		Vector<Boolean> res = new Vector<Boolean>();

		LinkedList<String> result = getNullables(db, tabla);

		res.add(!result.isEmpty());

		result = getPrimKey(db, tabla);

		res.add(!result.isEmpty());

		result = getCandidateKey(db, tabla);

		res.add(!result.isEmpty());

		result = getForeignKey(db, tabla);

		res.add(!result.isEmpty());

		result = getFuncionDep(db, tabla);

		res.add(!result.isEmpty());

		result = getIntConst(db, tabla);

		res.add(!result.isEmpty());

		executeCommand("/tapi /use_db " + currentDB);

		return res;

	}

	/**
	 * Returns if a table is empty
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @return boolean
	 */
	public boolean isEmpty(String db, String table) {

		String currentDB = currentDB();

		LinkedList<String> res = executeCommand("/tapi /use_db " + db);

		res = executeCommand("/tapi /is_empty " + table);

		executeCommand("/tapi /use_db " + currentDB);

		if (res.get(0).contains("true"))
			return true;

		return false;
	}

	public boolean pk(String db, String table, LinkedList<String> pk) {

		String currentDB = currentDB();

		LinkedList<String> res = executeCommand("/tapi /use_db " + db);

		res = executeCommand("/dbschema");

		String sPk = "";

		for (int i = 0; i < pk.size(); i++)

			sPk += pk.get(i) + ", ";

		sPk = sPk.substring(0, sPk.length() - 2);

		String command = "/tapi :-pk(" + table + ", [" + sPk + "])";

		res = executeCommand(command);

		executeCommand("/tapi /use_db " + currentDB);

		if (res.size() == 0)
			return true;

		if (res.get(0).contains("$error"))
			return false;

		return true;
	}

	public boolean nn(String db, String table, LinkedList<String> nn) {

		String currentDB = currentDB();

		LinkedList<String> res = executeCommand("/tapi /use_db " + db);

		String snn = "";

		for (int i = 0; i < nn.size(); i++)

			snn += nn.get(i) + ", ";

		snn = snn.substring(0, snn.length() - 2);

		res = executeCommand("/tapi :-nn(" + table + ", [" + snn + "])");

		executeCommand("/tapi /use_db " + currentDB);

		if (res.size() == 0)
			return true;

		if (res.get(0).contains("$error"))
			return false;

		return true;

	}

	public boolean type(String db, String table, LinkedList<String> cols) {

		String currentDB = currentDB();

		LinkedList<String> res = executeCommand("/tapi /use_db " + db);

		String sCols = "";

		for (int i = 0; i < cols.size(); i++)

			sCols = cols.get(i) + ", ";

		sCols = sCols.substring(0, sCols.length() - 2);

		res = executeCommand("/tapi :-type(" + table + ", [" + sCols + "])");

		executeCommand("/tapi /use_db " + currentDB);

		if (res.get(0).contains("$error"))
			return false;

		return true;
	}

	/**
	 * Delete a row of a table
	 * 
	 * @param String
	 *            database
	 * @param String
	 *            table
	 * @param Vector
	 *            <Vector<String>> dataColumns
	 * @param Vector
	 *            <String> columnsNames
	 * @return String
	 */
	public String deleteRow(String database, String table,
			Vector<Vector<String>> dataColumns, Vector<String> columnNames) {

		String res = "ok";

		String db = currentDB();

		LinkedList<?> listpk = getPrimKey(database, table);

		executeCommand("/tapi /use_db " + database);

		for (int row = 0; row < dataColumns.size(); row++) {

			int recordCount = getRecordCount(database, table, columnNames,
					dataColumns.get(row));

			if (!listpk.isEmpty()) { // tiene pk

				String command = "/tapi delete from [" + table + "] where [";

				Object[] hs = listpk.toArray();

				String pk = ((String) hs[0]).replace("[", "");

				pk = pk.replace("]", "");

				Object[] g = pk.split(",");

				for (int i = 0; i < g.length; i++) {

					command += ((String) g[i]).trim() + "]="
							+ dataColumns.get(i);

					if (i < columnNames.size() - 1)

						command += " and [";
				}

				LinkedList<String> ret = executeCommand(command);

				if (ret.size() == 1)
					res = "success";

				else
					res = ret.get(2);

				return res;

			} else {

				res = deleteRows(database, table, dataColumns.get(row),
						columnNames); // borramos

				if (!res.equals("success"))
					return res;

				if (recordCount > 1) {// hay repetidos y no tiene pk

					for (int i = 0; i < recordCount - 1; i++) { // insertamos
																// num-1 tuplas

						res = insertValues(table, dataColumns.get(row));

						if (!res.equals("success"))
							return res;

					}
				}
			}
		}

		executeCommand("/tapi /use_db " + db);

		return res;
	}

	public String deleteRows(String database, String table,
			Vector<String> dataColumns, Vector<String> columnNames) {

		String res = "ok";
		// /tapi DELETE FROM [t] WHERE [ a]=1 AND [b]='zz'

		String command = "/tapi delete from [" + table + "] where ";

		for (int i = 0; i < columnNames.size(); i++) {

			String data = dataColumns.get(i);

			if (data == null)

				command += "[" + columnNames.get(i) + "] IS NULL ";// +data;

			else

				command += "[" + columnNames.get(i) + "]=" + data;

			if (i < columnNames.size() - 1)

				command += " and ";

		}

		LinkedList<String> ret = executeCommand(command);

		if (ret.size() == 1)
			res = "success";

		else
			res = ret.get(2);

		return res;

	}

	@Override
	public String updateField(String table, String database,
			Vector<String> columnsNames, Vector infoRow, Vector newInfoRow) {

		String res = "ok";

		String db = currentDB();

		LinkedList<?> executeResult;

		LinkedList<?> listpk = getPrimKey(database, table);

		int recordCount = getRecordCount(database, table, columnsNames, infoRow);

		executeCommand("/tapi /use_db " + database);

		if (!listpk.isEmpty()) { // tiene pk

			// /tapi update[t] set values WHERE [ a]=1 AND [b]='zz'

			String command = "UPDATE " + table + " SET ";

			command = command + columnsNames.get(0) + "=" + newInfoRow.get(0)
					+ " ";

			for (int i = 1; i < columnsNames.size(); i++) {

				command = command + " , " + columnsNames.get(i) + "="
						+ newInfoRow.get(i) + " ";

			}

			command += "WHERE ";

			Object[] hs = listpk.toArray();

			String pk = ((String) hs[0]).replace("[", "");

			pk = pk.replace("]", "");

			Object[] g = pk.split(",");

			for (int i = 0; i < g.length; i++) {

				command += "[" + ((String) g[i]).trim() + "]=" + infoRow.get(i);

				if (i < columnsNames.size() - 1)

					command += " and";
			}

			LinkedList<String> ret = executeCommand(command);

			if (ret.size() == 1)
				res = "ok";

			else
				res = ret.get(2);

		} else {

			if (recordCount > 1) {// hay repetidos y no tiene pk

				res = deleteRows(database, table, columnsNames, infoRow); // borramos

				for (int i = 0; i < recordCount - 1; i++) { // insertamos num-1
															// tuplas

					res = insertValues(table, infoRow);

					if (!res.equals("ok"))
						break;
				}

				res = insertValues(table, newInfoRow); // insertamos el
														// modificado

			} else {

				String command = "/tapi UPDATE [" + table + "] SET [";

				command += columnsNames.get(0) + "]=" + newInfoRow.get(0) + " ";

				for (int i = 1; i < columnsNames.size(); i++) {

					command += " , [" + columnsNames.get(i) + "]="
							+ newInfoRow.get(i) + " ";
				}

				command = command + "WHERE [" + columnsNames.get(0) + "]="
						+ infoRow.get(0) + " ";

				for (int i = 1; i < columnsNames.size(); i++) {

					String data = (String) infoRow.get(i);

					if (data == null || data.equals(""))

						command += " and [" + columnsNames.get(i)
								+ "] IS NULL ";

					else

						command += " and [" + columnsNames.get(i) + "]="
								+ infoRow.get(i) + " ";

				}

				executeResult = executeCommand(command);

				if (((String) executeResult.get(0)).contains("error"))
					res = (String) executeResult.get(2);

			}

		}

		executeCommand("/tapi /use_db " + db);

		return res;

	}

	private int getRecordCount(String database, String table,
			Vector columnsNames, Vector infoRow) {

		int result = 1;

		String db = currentDB();

		executeCommand("/tapi /use_db " + database);

		String command = "/tapi SELECT COUNT(*) FROM " + table + " WHERE "
				+ columnsNames.get(0) + "=" + infoRow.get(0) + " ";
		// tapi SELECT COUNT(*) FROM tabla WHERE condición

		for (int i = 1; i < columnsNames.size(); i++) {

			String data = (String) infoRow.get(i);

			if (data == null || data.equals(""))

				command += " and " + columnsNames.get(i) + " IS NULL ";

			else

				command += " and " + columnsNames.get(i) + "=" + data + " ";

		}

		LinkedList<?> executeResult = executeCommand(command);

		try {

			result = Integer.valueOf((String) executeResult.get(executeResult
					.size() - 1));

		} catch (NumberFormatException e) {

			result = -1;

		}

		executeCommand("/tapi /use_db " + db);

		return result;

	}

	@Override
	public String currentDB() {

		LinkedList<String> res = executeCommand("/tapi /current_db");

		String result = "$des";

		if (!res.isEmpty())

			result = res.get(0);

		return result;

	}

	/**
	 * Executes a sorting query
	 * 
	 * @param table
	 * @param command
	 */
	public LinkedList<String> orderColumns(String table, String command) {

		if (command.endsWith(",")) {
			command = command.substring(0, command.lastIndexOf(","));
		}

		String commandLine = "/tapi SELECT * FROM " + table + " ORDER BY "
				+ command;

		LinkedList<String> res = executeCommand(commandLine);

		return res;

	}
}
