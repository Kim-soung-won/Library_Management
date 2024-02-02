import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class sql_connect {
    public static frame ff;
    public static String serverAddress = "localhost";
    public static int portNumber = 1433;
    public static String databaseName = "Library_management";
    public static String userName = "sa";
    public static String passWord = "123456";
    public static String url = "jdbc:sqlserver://DESKTOP-97S6RD5\\"+serverAddress+":"+portNumber+";databaseName="+databaseName;
    public static JTable id_select(String a,String b, int id) {
        JTable table = null;
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "SELECT * FROM "+a+" where "+ b +" = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            // ResultSet을 JTable로 변환
            table = new JTable(buildTableModel(resultSet));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return table;
    }
    public static JTable name_select(String a,String b, String name) {
        JTable table = null;
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "SELECT * FROM " + a + " where " + b + " = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            // ResultSet을 JTable로 변환
            table = new JTable(buildTableModel(resultSet));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return table;
    }
    public static JTable select(String a) {
        JTable table = null;
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "SELECT * FROM "+a;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println(resultSet);

            // ResultSet을 JTable로 변환
            table = new JTable(buildTableModel(resultSet));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return table;
    }
    private static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // 열(Column) 이름 가져오기
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int column = 1; column <= columnCount; column++) {
            columnNames[column - 1] = metaData.getColumnName(column);
        }

        // 테이블 데이터 가져오기
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int column = 1; column <= columnCount; column++) {
                rowData[column - 1] = resultSet.getObject(column);
            }
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    public static boolean check_ID(int memberID, String tableName, String id){
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "SELECT COUNT(*) FROM "+ tableName + " WHERE "+id+ " = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, memberID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void c_UpdateData(String table,int a, String name, String phone, String email, String address){
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "UPDATE "+ table+" set 이름=?, 전화번호=?,이메일=?,주소=? where 고객_ID =?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, phone);
                stmt.setString(3, email);
                stmt.setString(4, address);
                stmt.setInt(5, a);

                int rowsAffected = stmt.executeUpdate();

                System.out.println("업데이트된 행 수: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void b_UpdateData(String table,int a, String name, String author, String publisher, String year,String category, int count){
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "UPDATE "+ table+" set 도서명=?, 저자=?,출판사=?,출판년도=?,장르=?,대출가능권수=? where 도서_ID =?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, author);
                stmt.setString(3, publisher);
                stmt.setString(4, year);
                stmt.setString(5, category);
                stmt.setInt(6, count);
                stmt.setInt(7, a);

                int rowsAffected = stmt.executeUpdate();

                System.out.println("업데이트된 행 수: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void c_InsertData(String table,int a, String name, String phone, String email, String address){
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "insert into "+ table+" values (?,?, ?, ?,?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, a);
                stmt.setString(2, name);
                stmt.setString(3, phone);
                stmt.setString(4, email);
                stmt.setString(5, address);

                int rowsAffected = stmt.executeUpdate();

                System.out.println("업데이트된 행 수: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void r_InsertData(String table,int a, int cus_id, String book_Name){
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String selectQuery = "SELECT 도서_ID FROM 도서 WHERE 도서명 = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, book_Name);
                ResultSet resultSet = selectStmt.executeQuery();
                if (resultSet.next()) {
                    int 도서ID = resultSet.getInt("도서_ID");
                    String checkcount = "select 대출가능권수 from 도서 where 도서_ID = ?";
                    try(PreparedStatement checkStmt = connection.prepareStatement(checkcount)){
                        checkStmt.setInt(1, 도서ID);
                        ResultSet cnn = checkStmt.executeQuery();
                        if(cnn.next()){
                            int count = cnn.getInt("대출가능권수");
                            if(count>0) {
                                String updateQuery = "UPDATE 도서 SET 대출가능권수 = 대출가능권수 - 1 where 도서_ID = ?";
                                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                                    updateStmt.setInt(1, 도서ID);
                                    updateStmt.executeUpdate();
                                }
                                // 대출 관리 테이블에 데이터 삽입
                                String insertQuery = "INSERT INTO 대출이력 (대출_ID,도서_ID,고객_ID,대출일자) VALUES (?,?, ?,?)";
                                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                                    insertStmt.setInt(1, a);
                                    insertStmt.setInt(2, 도서ID);
                                    insertStmt.setInt(3, cus_id);
                                    LocalDateTime borrowday = LocalDateTime.now();
                                    Timestamp borrowTimestamp = Timestamp.valueOf(borrowday);
                                    insertStmt.setTimestamp(4, borrowTimestamp);
                                    insertStmt.executeUpdate();
                                }
                            }else {
                                System.out.println("sold out");
                            }
                        }
                    }
                } else {
                    System.out.println("도서명에 해당하는 도서가 없습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void b_InsertData(String table,int a, String name, String author, String publisher, String year,String category, int count) {
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "insert into " + table + " values (?,?, ?, ?,?,?,?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, a);
                stmt.setString(2, name);
                stmt.setString(3, author);
                stmt.setString(4, publisher);
                stmt.setString(5, year);
                stmt.setString(6, category);
                stmt.setInt(7, count);

                int rowsAffected = stmt.executeUpdate();

                System.out.println("업데이트된 행 수: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void r_deleteData(int a){
        int 도서ID;
        Date borrowday = null;
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "select 도서_ID,대출일자 from 대출이력 where 대출_ID=?";
            try (PreparedStatement st = connection.prepareStatement(query)) {
                st.setInt(1,a);
                ResultSet resultSet = st.executeQuery();
                if(resultSet.next()) {
                    도서ID = resultSet.getInt("도서_ID");
                    borrowday = resultSet.getDate("대출일자");
                    String query1 = "update 도서 set 대출가능권수 = 대출가능권수 + 1 where 도서_ID=?";
                    try (PreparedStatement stmt = connection.prepareStatement(query1)) {
                        stmt.setInt(1, 도서ID);
                        stmt.executeUpdate();
                    }
                }
            }
            String query2 = "update 대출이력 set 반납일자 = ?, 소유기간 = ? where 대출_ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query2)) {
                LocalDateTime returnday = LocalDateTime.now();
                Timestamp returnTimestamp = Timestamp.valueOf(returnday);
                stmt.setTimestamp(1, returnTimestamp);
                long diffIndays = ChronoUnit.DAYS.between(borrowday.toLocalDate(),returnday.toLocalDate());
                stmt.setLong(2,diffIndays);
                stmt.setInt(3, a);

                int rowsAffected = stmt.executeUpdate();

                System.out.println("업데이트된 행 수: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static JTable nation(){
        JTable table = null;
        try (Connection connection = DriverManager.getConnection(url,userName,passWord)){
            String query = "SELECT DISTINCT c.고객_ID, c.이름, " +
                    "CASE WHEN MAX(b.소유기간) > 7 THEN 'O' ELSE 'X' END AS 연체여부 " +
                    "FROM 고객 c JOIN 대출이력 b ON c.고객_ID = b.고객_ID " +
                    "GROUP BY c.고객_ID, c.이름";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            table = new JTable(buildTableModel(resultSet));
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return table;
    }
    public static boolean deleteData(String table, String a, int id){
        int have;
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "IF EXISTS (SELECT 1 FROM 대출이력 WHERE " + a + " = ?) " +
                    "SELECT CAST(1 AS bit) AS 결과 " +
                    "ELSE SELECT CAST(0 AS bit) AS 결과";
            try (PreparedStatement st = connection.prepareStatement(query)) {
                st.setInt(1,id);
                ResultSet resultSet = st.executeQuery();
                if(resultSet.next()) {
                    have = resultSet.getInt("결과");
                    if(have==1){
                        return true;
                    }
                    else {
                        String deleteQuery = "DELETE FROM " + table + " WHERE " + a + " = ?";
                        try(PreparedStatement delete = connection.prepareStatement(deleteQuery)){
                            delete.setInt(1,id);
                            delete.executeUpdate();
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    return true;
    }
    public static void delete(int a){
        try (Connection connection = DriverManager.getConnection(url, userName, passWord)) {
            String query = "delete from 대출이력 where 대출_ID = "+ a;
            try (PreparedStatement st = connection.prepareStatement(query)) {
                st.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}