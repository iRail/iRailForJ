package api.datastructs;
import java.util.Date;

public class Arrdep{
        private Station s;
        private Vehicle v;
        private Date d;
        private String platform;

        public Date getD() {
            return d;
        }

        public String getPlatform() {
            return platform;
        }

        public Station getS() {
            return s;
        }

        public Vehicle getV() {
            return v;
        }

        public Arrdep(Station s, Vehicle v, Date d, String platform) {
            this.s = s;
            this.v = v;
            this.d = d;
            this.platform = platform;
        }
}
