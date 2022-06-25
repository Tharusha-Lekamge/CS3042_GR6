package data.Model;

import java.util.Date;

import data.TransactionType;

public class Transaction {
    private String TrID;
    private String AccNo;
    private Date date;
    private Date time;
    private TransactionType type;
    private float TrCharge;

    public String getTrID() {
        return TrID;
    }

    public String getAccNo() {
        return AccNo;
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public TransactionType getType() {
        return type;
    }

    public float getTrCharge() {
        return TrCharge;
    }

    public Transaction(String TrID, String AccNo, Date date, Date time, TransactionType type, float TrCharge) {
        this.TrID = TrID;
        this.AccNo = AccNo;
        this.date = date;
        this.time = time;
        this.type = type;
        this.TrCharge = TrCharge;
    }
}
