package config;

public class QueryConfig {
    private String beforeFileName;
    private String cutInTheEnd;
    private int quantityTop;
    private String relativePathAfterWork;
    private boolean cleanPath;

    public String getBeforeFileName() {
        return beforeFileName;
    }

    public void setBeforeFileName(String beforeFileName) {
        this.beforeFileName = beforeFileName;
    }

    public String getCutInTheEnd() {
        return cutInTheEnd;
    }

    public void setCutInTheEnd(String cutInTheEnd) {
        this.cutInTheEnd = cutInTheEnd;
    }

    public int getQuantityTop() {
        return quantityTop;
    }

    public void setQuantityTop(int quantityTop) {
        this.quantityTop = quantityTop;
    }

    public String getRelativePathAfterWork() {
        return relativePathAfterWork;
    }

    public void setRelativePathAfterWork(String relativePathAfterWork) {
        this.relativePathAfterWork = relativePathAfterWork;
    }

    public boolean getCleanPath() {
        return cleanPath;
    }

    public void setCleanPath(boolean cleanPath) {
        this.cleanPath = cleanPath;
    }
}
