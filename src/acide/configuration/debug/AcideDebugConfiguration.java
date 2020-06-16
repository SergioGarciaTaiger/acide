package acide.configuration.debug;

import acide.language.AcideLanguageManager;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class AcideDebugConfiguration {

    /**
     * ACIDE - A Configurable IDE trust_table.
     */
    private Trust_tables trust_tables;
    private Debug debug;
    private String trust_file;
    //private String oracle_file;
    private Order order;

    private static AcideDebugConfiguration instance;

    AcideDebugConfiguration(){
        setDefaultConfiguration();
    }

    public static AcideDebugConfiguration getInstance() {
        if(instance == null){
            instance = new AcideDebugConfiguration();
        }
        return instance;
    }

    public void setDefaultConfiguration(){
        trust_tables = Trust_tables.YES;
        debug = Debug.FULL;
        order = Order.CARDINALITY;
        trust_file = "";
        //oracle_file = null;
    }

    public String getDebugConfiguration(){
        String configuration = " ";
        if(trust_tables == Trust_tables.NO)
            configuration += "trust_tables(" + trust_tables.getValue() + ") ";
        if(debug == Debug.PLAIN)
            configuration += "debug(" + debug.getValue() + ") ";
        if(order == Order.TOPDOWN)
            configuration += "order(" + order.getValue() + ") ";
        if(trust_file != null && !trust_file.isEmpty())
            configuration += "trust_file('" + trust_file + "') ";
//        if(oracle_file != null && !oracle_file.isEmpty())
//            configuration += "oracle_file('" + oracle_file + "') ";
        return configuration;
    }

    public void saveConfiguration(AcideDebugConfiguration configuration){
        instance = configuration;
        try {
            String debugConfigurationPath = AcideResourceManager.getInstance().getProperty("debugConfigurationPath");
            Properties debugProperties = new Properties();
            debugProperties.setProperty("trust_tables", trust_tables.getValue());
            debugProperties.setProperty("debug", debug.getValue());
            debugProperties.setProperty("trust_file", trust_file);
            debugProperties.setProperty("order", order.getValue());
            debugProperties.store(new FileOutputStream(debugConfigurationPath),null);
        } catch (MissedPropertyException | IOException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, AcideLanguageManager.getInstance().getLabels()
                    .getString("s2341"));
        }
    }

    public Trust_tables getTrust_tables() {
        return trust_tables;
    }

    public void setTrust_tables(Trust_tables trust_tables) {
        this.trust_tables = trust_tables;
    }

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    public String getTrust_file() {
        return trust_file;
    }

    public void setTrust_file(String trust_file) {
        this.trust_file = trust_file;
    }

//    public String getOracle_file() {
//        return oracle_file;
//    }
//
//    public void setOracle_file(String oracle_file) {
//        this.oracle_file = oracle_file;
//    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public enum Trust_tables {
        YES("yes"), NO("no");

        private String value;

        Trust_tables(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Debug {
        FULL("full"), PLAIN("plain");

        private String value;

        Debug(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Order {
        CARDINALITY("cardinality"), TOPDOWN("topdown");

        private String value;

        Order(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void load(String fileName) throws MissedPropertyException {
//        String fileContent = AcideFileManager.getInstance().load(configuration);
//        if(fileContent.isEmpty())
//            throw new MissedPropertyException(AcideLanguageManager.getInstance().getLabels()
//                    .getString("s2340"));
//        else{
//            fileContent = fileContent.replaceAll("\r\n", "\n");
        try{
            // Creates the file input file
            FileInputStream configurationFile = new FileInputStream(fileName);
            Properties debugProperties = new Properties();
            debugProperties.load(configurationFile);

            setProperties(debugProperties);

            // Closes the configuration file input stream
            configurationFile.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new MissedPropertyException(AcideLanguageManager.getInstance().getLabels()
                    .getString("s2340"));
        }
    }

    private void setProperties(Properties properties){
        setTrust_tables(properties.getProperty("trust_tables").equals(Trust_tables.NO.getValue()) ? Trust_tables.NO : Trust_tables.YES);
        setDebug(properties.getProperty("debug").equals(Debug.PLAIN.getValue()) ? Debug.PLAIN : Debug.FULL);
        setTrust_file(properties.getProperty("trust_file"));
        setOrder(properties.getProperty("order").equals(Order.TOPDOWN.getValue()) ? Order.TOPDOWN : Order.CARDINALITY);
    }
}
