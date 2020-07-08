import config.MainConfig;
import config.QueryConfig;
import config.ThreadConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseDao {

    public static String SELECT_ALL_TABLEROWS = "SELECT %s ID, FILE_DIRECTORY, CREATEDATE FROM DOCUMENTS " +
            "WHERE FILE_DIRECTORY LIKE '%s'";
    public static String UPDATE_TABLEROW_DIRECTORY = "UPDATE DOCUMENTS SET FILE_DIRECTORY = ? WHERE ID = ?";

    private ThreadConfig threadConfig;
    private MainConfig config;
    private QueryConfig queryConfig;
    private DataSource dataSource;

    DatabaseDao(MainConfig config, DataSource dataSource) {
        this.config = config;
        this.threadConfig = config.getThreadConfig();
        this.queryConfig = config.getQueryConfig();
        this.dataSource = dataSource;
    }

    public void processTable() {
        ExecutorService executorService = Executors.newFixedThreadPool(threadConfig.getQuantityOfThreads());

        String quantityTop = queryConfig.getQuantityTop() == 0 ? "" : "TOP " + queryConfig.getQuantityTop();
        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement stmnt =
                        connection.prepareStatement(String.format(SELECT_ALL_TABLEROWS, quantityTop,
                                queryConfig.getBeforeFileName() + "%" + queryConfig.getCutInTheEnd()));

                ResultSet rs = stmnt.executeQuery()
        ) {
            List<TableRow> tableRowList = new ArrayList<>();

            while (rs.next()) {
                tableRowList.add(constructTableRow(rs));

                if (tableRowList.size() >= threadConfig.getQuantityOfRowsForThread()) {
                    executorService.execute(new Runner(tableRowList, config, dataSource));
                    tableRowList = new ArrayList<>();
                }
            }
            if (!tableRowList.isEmpty()) {
                executorService.execute(new Runner(tableRowList, config, dataSource));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private TableRow constructTableRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String file = rs.getString("FILE_DIRECTORY");
        LocalDate date = rs.getDate("CREATEDATE").toLocalDate();

        return new TableRow(id, file, date);
    }

    public static void changeDirectory(PreparedStatement preparedStatement, int id, String newDirectory) throws SQLException {
        preparedStatement.setString(1, newDirectory);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }
}