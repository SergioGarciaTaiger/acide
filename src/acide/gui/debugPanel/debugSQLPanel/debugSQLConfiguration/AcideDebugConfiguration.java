package acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration;

public class AcideDebugConfiguration {

    /**
     * ACIDE - A Configurable IDE trust_table.
     */
    private Trust_tables trust_tables;
    private Debug debug;
    private String trust_file;
    private String oracle_file;
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
        trust_file = null;
        oracle_file = null;
    }

    public String getDebugConfiguration(){
        String configuration = " ";
        if(trust_tables == Trust_tables.NO)
            configuration += "trust_tables(" + trust_tables.getValue() + ") ";
        if(debug == Debug.PLAIN)
            configuration += "debug(" + debug.getValue() + ") ";
        if(order == Order.TOPDOWN)
            configuration += "order(" + order.getValue() + ") ";
        if(trust_file == null || trust_file.isEmpty())
            configuration += "trust_file(" + trust_file + ") ";
        if(oracle_file == null || oracle_file.isEmpty())
            configuration += "oracle_file(" + oracle_file + ") ";
        return configuration;
    }

    public void saveConfiguration(AcideDebugConfiguration configuration){
        instance = configuration;
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

    public String getOracle_file() {
        return oracle_file;
    }

    public void setOracle_file(String oracle_file) {
        this.oracle_file = oracle_file;
    }

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
}
