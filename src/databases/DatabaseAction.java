package databases;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAction {

  private static DatabaseAction handler;

  private static final String DbUrl = "jdbc:derby:database;create=true";
  private static Connection connect = null;
  private static Statement state = null;

  public DatabaseAction(){
    connectionCreate();
    setUpUserTable();
  }

  void connectionCreate(){
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
      connect = DriverManager.getConnection(DbUrl);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void setUpUserTable(){

    String userTable = "User";

    try {
      state = connect.createStatement();

      DatabaseMetaData dataBaseManger = connect.getMetaData();
      ResultSet tables = dataBaseManger.getTables(null,null, userTable.toUpperCase(),null);

      if (tables.next()) {
        System.out.println("Table " + userTable + " exist");
      } else {
        state.execute("CREATE TABLE " + userTable + " ("
            + "         userName varchar (200) primary key , \n "
            + "         userPassword varchar (200), \n"
            + "         isAvail boolean default true"
            + " )");
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage() + " .... setupDatabase");
    } finally {

    }
  }
}
