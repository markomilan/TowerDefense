package view.database;

import java.sql.*;
import java.util.Vector;

public class ConnectionFactory {
    private Connection con;
    private  Statement st;
    private ResultSet rs;
    private Vector<String> column;
    private Vector<Vector<String>> data;

    public ConnectionFactory(){
        try{
            loadData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadData(){
        try{
            open();
            String query = "select * from HighScores order by gold  DESC ";
            rs = st.executeQuery(query);
            ResultSetMetaData rsmt = rs.getMetaData();
            int c = rsmt.getColumnCount();

            column = new Vector<>(c);
            data = new Vector<>();
            Vector<String> row;

            for(int i = 1 ; i <= c; i++){
                column.add(rsmt.getColumnName(i));
            }
            int i=1;
            while(i<=5){
                if(rs.next()){
                    row = new Vector<>(c);
                    for(int j=1 ; j<=c ; j++){
                        row.add(rs.getString(j));
                    }
                    data.add(row);
                }
                ++i;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        close();
    }

    public void post(String name,int gold){
        try{
            open();
            PreparedStatement posted = con.prepareStatement("INSERT INTO HighScores (name,gold) VALUES ('"+name+"','"+gold+"')");
            posted.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        close();
    }

    public void close(){
        try {
            con.close();
            st.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void open() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/highscore","root","");
        st = con.createStatement();
    }

    public Vector<String> getColumn(){
        return this.column;
    }

    public Vector<Vector<String>> getData(){
        return this.data;
    }
}
