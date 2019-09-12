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
package acide.configuration.menu;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import acide.gui.fileEditor.fileEditorPanel.popup.listeners.AcideToggleCommentMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideCloseConsoleMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideConfigureMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideConsoleDisplayOptionsMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideConsoleLineWrappingMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideDocumentConsoleLexiconMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideExternalCommandMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideSaveConsoleContentIntoFileMenuItemListener;
import acide.gui.menuBar.configurationMenu.consoleMenu.listeners.AcideSearchConsoleMenuItemListener;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.listeners.AcideDesPanelMenuItemListener;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.listeners.AcideOdbcPanelMenuItemListener;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.listeners.AcideShowNameFieldsMenuItemListener;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.listeners.AcideShowNameFieldsTypesMenuItemListener;
import acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.listeners.AcideShowNameMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelArrowColorDirectMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelArrowColorInverseMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelArrowShapeLineMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelArrowShapePolygonMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelNodeColorItemMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelNodeShapeCircleMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelNodeShapeSquareMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelNodeSizeMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelSelectedNodeColorItemMenuItemListener;
import acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners.AcideDebugPanelShowLabelsMenuItemListener;
import acide.gui.menuBar.configurationMenu.fileEditor.listeners.AcideAutomaticIndentMenuItemListener;
import acide.gui.menuBar.configurationMenu.fileEditor.listeners.AcideFileEditorDisplayOptionsMenuItemListener;
import acide.gui.menuBar.configurationMenu.fileEditor.listeners.AcideLineWrappingMenuItemListener;
import acide.gui.menuBar.configurationMenu.fileEditor.listeners.AcideMaximumLinesToConsoleMenuItemListener;
import acide.gui.menuBar.configurationMenu.fileEditor.listeners.AcidePreferencesMenuItemListener;
import acide.gui.menuBar.configurationMenu.fileEditor.listeners.AcideSendToConsoleConfirmationMenuItemListener;
import acide.gui.menuBar.configurationMenu.grammarMenu.listeners.AcideAutoAnalysisMenuItemListener;
import acide.gui.menuBar.configurationMenu.grammarMenu.listeners.AcideLoadGrammarMenuItemListener;
import acide.gui.menuBar.configurationMenu.grammarMenu.listeners.AcideModifyGrammaMenuItemrListener;
import acide.gui.menuBar.configurationMenu.grammarMenu.listeners.AcideNewGrammarMenuItemListener;
import acide.gui.menuBar.configurationMenu.grammarMenu.listeners.AcideSaveAsGrammarMenuItemListener;
import acide.gui.menuBar.configurationMenu.grammarMenu.listeners.AcideSaveGrammarMenuItemListener;
import acide.gui.menuBar.configurationMenu.grammarMenu.listeners.AcideSetPathsMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideArrowColorDirectMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideArrowColorInverseMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideArrowShapeLineMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideArrowShapePolygonMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideNodeColorItemMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideNodeShapeCircleMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideNodeShapeSquareMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideNodeSizeMenuItemListener;
import acide.gui.menuBar.configurationMenu.graphPanelMenu.listeners.AcideShowLabelsMenuItemListener;
import acide.gui.menuBar.configurationMenu.lexiconMenu.listeners.AcideDefaultLexiconsMenuItemListener;
import acide.gui.menuBar.configurationMenu.lexiconMenu.listeners.AcideDocumentLexiconMenuItemListener;
import acide.gui.menuBar.configurationMenu.lexiconMenu.listeners.AcideModifyLexiconMenuItemListener;
import acide.gui.menuBar.configurationMenu.lexiconMenu.listeners.AcideNewLexiconMenuItemListener;
import acide.gui.menuBar.configurationMenu.listeners.AcideCompilerMenuItemListener;
import acide.gui.menuBar.configurationMenu.toolBarMenu.listeners.AcideLoadToolBarMenuItemListener;
import acide.gui.menuBar.configurationMenu.toolBarMenu.listeners.AcideModifyToolBarMenuItemListener;
import acide.gui.menuBar.configurationMenu.toolBarMenu.listeners.AcideNewToolBarMenuItemListener;
import acide.gui.menuBar.configurationMenu.toolBarMenu.listeners.AcideSaveToolBarMenuItemListener;
import acide.gui.menuBar.configurationMenu.toolBarMenu.listeners.AcideToolBaAsMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideCapitalizeMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideCopyMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideCutMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideGoToLineMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideInvertCaseMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideLowerCaseMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideMakeCommentMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcidePasteMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideRedoAction;
import acide.gui.menuBar.editMenu.listeners.AcideReleaseCommentMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideReplaceMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideSearchMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideSelectAllMenuItemListener;
import acide.gui.menuBar.editMenu.listeners.AcideUndoAction;
import acide.gui.menuBar.editMenu.listeners.AcideUpperCaseMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideCloseAllFilesMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideCloseFileMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideExitMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideNewFileMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideOpenAllFilesMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideOpenFileMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcidePrintFileMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideSaveAllFilesMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideSaveFileAsMenuItemListener;
import acide.gui.menuBar.fileMenu.listeners.AcideSaveFileMenuItemListener;
import acide.gui.menuBar.helpMenu.listeners.AcideShowAboutUsMenuItemListener;
import acide.gui.menuBar.helpMenu.listeners.AcideShowHelpMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideAddFileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideAddFolderMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideAddOpenedFilesMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideCloseProjecMenuItemtListener;
import acide.gui.menuBar.projectMenu.listeners.AcideCompileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideDeleteFileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideExecuteMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideNewProjectFileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideNewProjectMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideOpenProjectMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideRemoveFileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideRemoveFolderMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideSaveProjectAsMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideSaveProjectMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideSetCompilableFileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideSetMainFileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideUnsetCompilableFileMenuItemListener;
import acide.gui.menuBar.projectMenu.listeners.AcideUnsetMainFileMenuItemListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideAssertedDatabasePanelMenuItemListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideConsolePanelMenuItemListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideDataBasePanelMenuItemListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideDebugPanelMenuItemListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideExplorerPanelMenuItemListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideGraphPanelMenuItemListener;
import acide.gui.menuBar.viewMenu.listeners.AcideShowAcideLogTabMenuItemListener;
import acide.language.AcideLanguageManager;

/**																
 * ACIDE - A Configurable IDE inserted items listeners manager.
 *					
 * @version 0.14																														
 */
public class AcideInsertedItemListenersManager {
	/**
	 * ACIDE - A Configurable IDE menu inserted items listeners default path.
	 */
	public static final String DEFAULT_PATH = "./configuration/menu/";
	
	/**
	 * ACIDE - A Configurable IDE menu inserted items listeners default name.
	 */
	public static final String DEFAULT_NAME = "cods.ini";
	/**
	 * ACIDE - A Configurable IDE inserted items listeners unique class instance
	 */
	private static AcideInsertedItemListenersManager _instance;
	
	/**
	 * ACIDE - A Configurable IDE menu inserted items listeners
	 */
	private Properties _cods;
	
	public AcideInsertedItemListenersManager(){
		//Create the properties
		_cods = new Properties();
		
		FileInputStream in;
		try {
			//Creates the path for the menu defaults inserted items listeners
			in = new FileInputStream(DEFAULT_PATH + DEFAULT_NAME);
			//Loads the list
			_cods.load(in);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * Returns the ACIDE - A Configurable IDE unique class instance.
	 * 
	 * @return the ACIDE - A Configurable IDE unique class instance.
	 */
	public static AcideInsertedItemListenersManager getInstance() {
		if (_instance == null)
			_instance = new AcideInsertedItemListenersManager();
		return _instance;
	}
	
	/**
	 * Executes the action loaded for the cod given as parameter.
	 * 
	 * @param cod
	 * 			the cod for the action.
	 * 
	 * @param event
	 * 			the event of the inserted item.
	 * 
	 */
	public void executeAction(String cod, ActionEvent event){
		//Gets the number for the cod
		String aux = AcideInsertedItemListenersManager
				.getInstance().getCods().getProperty(cod);
		int i = 0;
		
		if (aux != null){
			i = Integer.parseInt(aux);
		}
	
		//Executes the action that corresponds to the cod
		switch (i){
		case 101:
			AcideNewFileMenuItemListener.action(event);
			break;
		case 102: 
			AcideOpenFileMenuItemListener.action(event);
			break;
		case 103:
			AcideOpenAllFilesMenuItemListener.action(event);
			break;
		case 104:
			AcideSaveFileAsMenuItemListener.action(event);
			break;
		case 105:
			AcideSaveFileMenuItemListener.action(event);
			break;
		case 106:
			AcidePrintFileMenuItemListener.action(event);
			break;
		case 107:
			AcideExitMenuItemListener.action(event);
			break;
		case 108:
			AcideCloseFileMenuItemListener.action(event);
			break;
		case 109:
			AcideCloseAllFilesMenuItemListener.action(event);
			break;
		case 110:
			AcideSaveAllFilesMenuItemListener.action(event);
			break;
		case 201:
			AcideUndoAction.action(event);
			break;
		case 202:
			AcideRedoAction.action(event);
			break;
		case 203:
			AcideSearchMenuItemListener.action(event);
			break;
		case 204:
			AcideReplaceMenuItemListener.action(event);
			break;
		case 205:
			AcideCutMenuItemListener.action(event);
			break;
		case 206:
			AcidePasteMenuItemListener.action(event);
			break;
		case 207:
			AcideCopyMenuItemListener.action(event);
			break;
		case 208:
			AcideSelectAllMenuItemListener.action(event);
			break;
		case 209: 
			AcideGoToLineMenuItemListener.action(event);
			break;
		case 210: 
			AcideToggleCommentMenuItemListener.action(event);
			break;
		case 211: 
			AcideUpperCaseMenuItemListener.action(event);
			break;
		case 212: 
			AcideLowerCaseMenuItemListener.action(event);
			break;
		case 213: 
			AcideCapitalizeMenuItemListener.action(event);
			break;
		case 214: 
			AcideInvertCaseMenuItemListener.action(event);
			break;
		case 215: 
			AcideMakeCommentMenuItemListener.action(event);
			break;
		case 216: 
			AcideReleaseCommentMenuItemListener.action(event);
			break;
		case 301:
			AcideNewProjectMenuItemListener.action(event);
			break;
		case 302:
			AcideOpenProjectMenuItemListener.action(event);
			break;
		case 303:
			AcideCloseProjecMenuItemtListener.action(event);
			break;
		case 304:
			AcideSaveProjectMenuItemListener.action(event);
			break;
		case 305:
			AcideSaveProjectAsMenuItemListener.action(event);
			break;
		case 306:
			AcideAddOpenedFilesMenuItemListener.action(event);
			break;
		case 307:
			AcideNewProjectFileMenuItemListener.action(event);
			break;
		case 308: 
			AcideAddFileMenuItemListener.action(event);
			break;
		case 309:
			AcideRemoveFileMenuItemListener.action(event);
			break;
		case 310:
			AcideDeleteFileMenuItemListener.action(event);
			break;
		case 311:
			AcideAddFolderMenuItemListener.action(event);
			break;
		case 312:
			AcideRemoveFolderMenuItemListener.action(event);
			break;
		case 313:
			AcideCompileMenuItemListener.action(event);
			break;
		case 314:
			AcideExecuteMenuItemListener.action(event);
			break;
		case 315:
			AcideSetCompilableFileMenuItemListener.action(event);
			break;
		case 316:
			AcideUnsetCompilableFileMenuItemListener.action(event);
			break;
		case 317:
			AcideSetMainFileMenuItemListener.action(event);
			break;
		case 318:
			AcideUnsetMainFileMenuItemListener.action(event);
			break;
		case 401:
			AcideShowAcideLogTabMenuItemListener.action(event);
			break;
		case 402:
			AcideShowAcideExplorerPanelMenuItemListener.action(event);
			break;
		case 403:
			AcideShowAcideConsolePanelMenuItemListener.action(event);
			break;
		case 404:
			AcideShowAcideDataBasePanelMenuItemListener.action(event);
			break;
		case 405:
			AcideShowAcideGraphPanelMenuItemListener.action(event);
			break;
		case 406:
			AcideShowAcideDebugPanelMenuItemListener.action(event);
			break;
		case 407:
			AcideShowAcideAssertedDatabasePanelMenuItemListener.action(event);
			break;
		case 501:
			AcideNewLexiconMenuItemListener.action(event);
			break;
		case 502:
			AcideModifyLexiconMenuItemListener.action(event);
			break;
		case 503:
			AcideDocumentLexiconMenuItemListener.action(event);
			break;
		case 504:
			AcideDefaultLexiconsMenuItemListener.action(event);
			break;
		case 505:
			AcideNewGrammarMenuItemListener.action(event);
			break;
		case 506:
			AcideLoadGrammarMenuItemListener.action(event);
			break;
		case 507:
			AcideModifyGrammaMenuItemrListener.action(event);
			break;
		case 508:
			AcideSaveGrammarMenuItemListener.action(event);
			break;
		case 509:
			AcideSaveAsGrammarMenuItemListener.action(event);
			break;
		case 510:
			AcideSetPathsMenuItemListener.action(event);
			break;
		case 511:
			AcideAutoAnalysisMenuItemListener.action(event);
			break;
		case 512:
			AcideCompilerMenuItemListener.action(event);
			break;
		case 513:
			AcideFileEditorDisplayOptionsMenuItemListener.action(event);
			break;
		case 514:
			AcideLineWrappingMenuItemListener.action(event);
			break;
		case 515:
			AcideAutomaticIndentMenuItemListener.action(event);
			break;
		case 516:
			AcideMaximumLinesToConsoleMenuItemListener.action(event);
			break;
		case 517:
			AcideSendToConsoleConfirmationMenuItemListener.action(event);
			break;
		case 518:
			AcideConfigureMenuItemListener.action(event);
			break;
		case 519:
			AcideExternalCommandMenuItemListener.action(event);
			break;
		case 520:
			AcideConsoleDisplayOptionsMenuItemListener.action(event);
			break;
		case 521:
			AcideSaveConsoleContentIntoFileMenuItemListener.action(event);
			break;
		case 522:
			AcideDocumentConsoleLexiconMenuItemListener.action(event);
			break;
		case 523:
			AcideSearchConsoleMenuItemListener.action(event);
			break;
		case 524:
			AcideCloseConsoleMenuItemListener.action(event);
			break;
		case 525:
			AcideDesPanelMenuItemListener.action(event);
			break;
		case 526:
			AcideOdbcPanelMenuItemListener.action(event);
			break;
		case 527:
//			AcideSpanishMenuItemListener.action(event);
			break;
		case 528:
//			AcideEnglishMenuItemListener.action(event);
			break;
		case 529:
			AcideNewToolBarMenuItemListener.action(event);
			break;
		case 530:
			AcideLoadToolBarMenuItemListener.action(event);
			break;
		case 531:
			AcideModifyToolBarMenuItemListener.action(event);
			break;
		case 532:
			AcideSaveToolBarMenuItemListener.action(event);
			break;
		case 533:
			AcideToolBaAsMenuItemListener.action(event);
			break;
		case 535:
			AcideNodeColorItemMenuItemListener.action(event);
			break;
		case 536:
			AcideNodeSizeMenuItemListener.action(event);
			break;
		case 537:
			AcideConsoleLineWrappingMenuItemListener.action(event);
			break;
		case 540:
			AcideNodeShapeCircleMenuItemListener.action(event);
			break;
		case 541:
			AcideNodeShapeSquareMenuItemListener.action(event);
			break;
		case 542:
			AcideArrowShapeLineMenuItemListener.action(event);
			break;
		case 543:
			AcideArrowShapePolygonMenuItemListener.action(event);
			break;
		case 544:
			AcideArrowColorDirectMenuItemListener.action(event);
			break;
		case 545:
			AcideArrowColorInverseMenuItemListener.action(event);
			break;
		case 546:
			AcideShowLabelsMenuItemListener.action(event);
			break;
		case 547:
			AcidePreferencesMenuItemListener.action(event);
			break;
		case 548:
			AcideShowNameMenuItemListener.action(event);
			break;
		case 549:
			AcideShowNameFieldsMenuItemListener.action(event);
			break;
		case 550:
			AcideShowNameFieldsTypesMenuItemListener.action(event);
			break;
		case 601:
			AcideShowHelpMenuItemListener.action(event);
			break;
		case 602:
			AcideShowAboutUsMenuItemListener.action(event);
			break;
		case 603:
			AcideDebugPanelNodeColorItemMenuItemListener.action(event);
			break;
		case 604:
			AcideDebugPanelShowLabelsMenuItemListener.action(event);			
			break;
		case 605:
			AcideDebugPanelNodeSizeMenuItemListener.action(event);
			break;
		case 606:
			AcideDebugPanelNodeShapeCircleMenuItemListener.action(event);			
			break;
		case 607:
			AcideDebugPanelNodeShapeSquareMenuItemListener.action(event);			
			break;
		case 608:
			AcideDebugPanelArrowColorDirectMenuItemListener.action(event);					
			break;
		case 609:
			AcideDebugPanelArrowColorInverseMenuItemListener.action(event);
			break;
		case 610:
			AcideDebugPanelArrowShapePolygonMenuItemListener.action(event);						
			break;
		case 611:			
			AcideDebugPanelArrowShapeLineMenuItemListener.action(event);
			break;
		case 612:
			AcideDebugPanelSelectedNodeColorItemMenuItemListener.action(event);
			break;			
		default:
			// Displays a message
			JOptionPane.showMessageDialog(null, AcideLanguageManager
					.getInstance().getLabels().getString("s2213"));
			break;
		}

	}

	/**
	 * Returns the cods properties.
	 * 
	 * @return the cods properties.
	 */
	public Properties getCods() {
		return _cods;
	}

	/**
	 * Sets a new value to the cods properties.
	 * 
	 * @param new value to the cods properties.
	 */
	public void setCods(Properties cods) {
		this._cods = cods;
	}
	
	

}
