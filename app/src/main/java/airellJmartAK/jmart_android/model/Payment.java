package airellJmartAK.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Payment extends Invoice {
    public Invoice shipment;
    public int productCount;
    public ArrayList<Record> history = new ArrayList<>();

    public static class Record {
        public final Date date;
        public String massage;
        public Status status;

        public Record(Invoice.Status status, String massage) {
            this.date = java.util.Calendar.getInstance().getTime();
            this.status = status;
            this.massage = massage;
        }
    }
}
