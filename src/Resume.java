/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
