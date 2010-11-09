
package api.datastructs;

import java.util.Date;

/**
 *
 * @author pieterc
 */
public class ViaTripNode {

    private String platform;
    private Date time;

    public ViaTripNode(String platform, Date time) {
        this.platform = platform;
        this.time = time;
    }

    public String getPlatform() {
        return platform;
    }

    public Date getTime() {
        return time;
    }
}
