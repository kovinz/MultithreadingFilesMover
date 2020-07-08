import java.time.LocalDate;
import java.time.ZoneId;

public class TableRow {
    private final Integer id;
    private final String filePath;
    private final LocalDate createDate;

    public TableRow(Integer id, String filePath, LocalDate createDate) {
        this.id = id;
        this.filePath = filePath;
        this.createDate = createDate;
    }

    public static void main(String[] args) {
        ZoneId.getAvailableZoneIds().stream()
                .sorted().forEach(System.out::println);
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
