import java.time.LocalDate;

public class TableRow {
    private final Integer id;
    private final String filePath;
    private final LocalDate createDate;

    public TableRow(Integer id, String filePath, LocalDate createDate) {
        this.id = id;
        this.filePath = filePath;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
