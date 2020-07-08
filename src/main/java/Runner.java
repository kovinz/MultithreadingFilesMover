import config.DirectoryConfig;
import config.MainConfig;
import config.QueryConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Runner implements Runnable {
    private List<TableRow> tableRowList;
    private QueryConfig queryConfig;
    private DirectoryConfig directoryConfig;
    private DataSource dataSource;

    public Runner(List<TableRow> tableRowList, MainConfig mainConfig, DataSource dataSource) {
        this.tableRowList = tableRowList;
        this.queryConfig = mainConfig.getQueryConfig();
        this.directoryConfig = mainConfig.getDirectoryConfig();
        this.dataSource = dataSource;
    }

    @Override
    public void run() {
        TableRow currentTableRow = null;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseDao.UPDATE_TABLEROW_DIRECTORY)) {
            for (TableRow tableRow : tableRowList) {
                try {
                    currentTableRow = tableRow;
                    Path fromPath = getInputPath(tableRow);

                    if (Files.exists(fromPath)) {
                        Path outputPath = getOutputPath(tableRow);
                        Files.createDirectories(outputPath.getParent());
                        Files.move(fromPath, outputPath);

                        String directoryForDatabase = getPathStrForDataBase(outputPath);
                        DatabaseDao.changeDirectory(preparedStatement, tableRow.getId(), directoryForDatabase);
                        System.out.println(tableRow.getId() + " moved " + tableRow.getFilePath() + " to " + directoryForDatabase);
                    } else {
                        System.out.println(tableRow.getId() + " file does not exist: " + tableRow.getFilePath());
                    }
                } catch (FileSystemException ex) {
                    System.out.println(currentTableRow.getId() + " processed with " + ex.getClass() +
                            " which is in " + currentTableRow.getFilePath() + " and was trying to move to " + ex.getFile());
                } catch (IOException ex) {
                    System.out.println(currentTableRow.getId() + " processed with " + ex.getClass() +
                            " which is in " + currentTableRow.getFilePath() + " message: " + ex.getMessage());
                } catch (SQLException ex) {
                    System.out.println(currentTableRow.getId() + " exception with updating table " + ex.getClass());
                }
            }
        } catch (SQLException ex) {
            System.out.println("������ ��� ���������� � ���� ������");
        }
    }

    /**
     * ������ �� ������ � tableRow � ������� ���������� ����, �� �������� ����� ��������� ����
     *
     * @param tableRow �������, ������������ ���� ��� ��������
     * @return ����, �� �������� ����� ��������� ����
     */
    private Path getInputPath(TableRow tableRow) {
        return Paths.get(directoryConfig.getPredicateFromDirectory(), cutInTheEnd(tableRow.getFilePath()));
    }

    /**
     * ������ �� ������ � tableRow � ������� ���������� ����, � ������� ����� ��������� ����
     *
     * @param tableRow �������, ������������ ���� ��� ��������
     * @return ����, � ������� ����� ��������� ����
     */
    private Path getOutputPath(TableRow tableRow) {
        Path pathFromDB = Paths.get(cutInTheEnd(tableRow.getFilePath()));
        LocalDate createDate = tableRow.getCreateDate();
        return Paths.get(directoryConfig.getPredicateToDirectory(), queryConfig.getRelativePathAfterWork(),
                String.valueOf(createDate.getYear()), String.valueOf(createDate.getMonthValue()),
                String.valueOf(createDate.getDayOfMonth()), getEndOfPath(pathFromDB));

    }

    private String getEndOfPath(Path pathFromDB) {
        if (queryConfig.getCleanPath()) {
            return pathFromDB.getFileName().toString();
        } else {
            return Paths.get(queryConfig.getBeforeFileName()).relativize(pathFromDB).toString();
        }
    }

    /**
     * �������� ������������� �������� ���� cleanPath == true � � ����� ������ �������� ������ ����������
     *
     * @param pathToChange ���� ��� ���������
     * @return ���� � ���������� ������ ����������� � ��������� ������������������������, ���� cleanPath == true
     */
    private Path changeDirectory(Path pathToChange) {
        if (queryConfig.getCleanPath()) {
            return Paths.get(queryConfig.getRelativePathAfterWork(), pathToChange.getFileName().toString());
        } else {
            Path beforeFileNamePath = Paths.get(queryConfig.getBeforeFileName());
            return Paths.get(queryConfig.getRelativePathAfterWork(), beforeFileNamePath.relativize(pathToChange).toString());
        }
    }

    /**
     * �������� � ����� ���� � ����� ������, ������� ������� � cutInTheEnd �������
     *
     * @param filePath ���� � ����� �� ��
     * @return ������ � ���������� ������ � �����, ������� (�����) ������� � cutInTheEnd �������
     */
    private String cutInTheEnd(String filePath) {
        return filePath.substring(0, filePath.length() - queryConfig.getCutInTheEnd().length());
    }

    /**
     * �������� � ���� ����� � ������, ������� ������� � predicateToDirectory �������, ��������� � ������ � ���������
     * ����� cutInTheEnd
     *
     * @param outputPath ������ ����, � ������� ����� ��������� ����
     * @return ���� � �����, ������� ����� ������� � ��
     */
    private String getPathStrForDataBase(Path outputPath) {
        return File.separator + Paths.get(directoryConfig.getPredicateToDirectory()).relativize(outputPath).toString()
                + queryConfig.getCutInTheEnd();
    }
}
