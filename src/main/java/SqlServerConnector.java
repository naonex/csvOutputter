
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class SqlServerConnector {
	private String connectionUrl;
	private String tableName;
	
	private void initParam() {
    	PropertyManager proManager = new PropertyManager();
        // Create a variable for the connection string.
        this.connectionUrl = proManager.properties.getProperty("val.sqlServerUrl");
        this.tableName = proManager.properties.getProperty("val.tableName");
	}

	public ArrayList<String> getTableDataListInCsvFormat() throws SQLException {
		initParam();
		
		//カラム名とデータ行を取得
		ArrayList<String> columnList = getColumnList();
		ArrayList<String> dataList = getDataListInCsvFormat(columnList);
		
		//カラム名とデータ行をまとめたCSVフォーマットリストを作成し、返却する
		ArrayList<String> tableDataList = new ArrayList<String>();
		tableDataList.add(String.join(",", columnList));
		tableDataList.addAll(dataList);
		
		return tableDataList;
	}
	
    private ArrayList<String> getColumnList() throws SQLException {
        
    	StringBuilder sb = new StringBuilder();
    	sb.append(" SELECT NAME ");
    	sb.append(" FROM   SYS.COLUMNS ");
    	sb.append(" WHERE  OBJECT_ID = (SELECT OBJECT_ID ");
    	sb.append("                     FROM   SYS.TABLES ");
    	sb.append("                     WHERE  NAME = ?) ");
    	
        ArrayList<String> columnList = new ArrayList<String>();
        try (Connection con = DriverManager.getConnection(this.connectionUrl); 
        		PreparedStatement pstmt = con.prepareStatement(sb.toString());) {

        	pstmt.setString(1, this.tableName);
            ResultSet rs = pstmt.executeQuery();
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
            	columnList.add(rs.getString(1));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    	
        return columnList;
    }

    private ArrayList<String> getDataListInCsvFormat(ArrayList<String> columnList) throws SQLException {
        
    	StringBuilder sb = new StringBuilder();
    	sb.append(" SELECT * FROM \""+ this.tableName + "\" ");
    	
        ArrayList<String> dataList = new ArrayList<String>();
        try (Connection con = DriverManager.getConnection(this.connectionUrl);
        		Statement stmt = con.createStatement();) {

            ResultSet rs = stmt.executeQuery(sb.toString());
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
            	ArrayList<String> oneRecode = new ArrayList<String>();
            	for (String column : columnList) {
            		//nullでなければ値をセット。nullであれば半角スペースをセット※後のマスキング処理でアスタリスク1文字に置換される
            		String value = " ";
            		if (Objects.nonNull(rs.getString(column))) {
            			value = rs.getString(column);
            		}
            		oneRecode.add(value);
            	}
            	dataList.add(String.join(",", oneRecode));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    	
        return dataList;
    }
    
}