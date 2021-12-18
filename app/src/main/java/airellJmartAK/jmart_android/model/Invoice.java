package airellJmartAK.jmart_android.model;

import java.util.Date;

public class Invoice extends Serializable {
    public enum Status{
        WAITING_CONFIRMATION, CANCELLED, ON_PROGRESS, ON_DELIVERY, COMPLAINT, FINISHED, FAILED;
    }

    public enum Rating{
        NONE,BAD,NEUTRAL,GOOD;
    }

    public Date date;
    public int buyerId;
    public int productId;
    public int complaintId;
    public Rating rating;
}
