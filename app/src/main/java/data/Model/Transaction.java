package data.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import data.TransactionType;

public class Transaction implements Parcelable{
    private Integer TrID;
    private String AccNo;
    private String Trtype;
    private double TrCharge;

    public Transaction(String AccNo,String type, double TrCharge,double Amount,String reference) {
        this.AccNo = AccNo;
        this.Trtype = type;
        this.TrCharge = TrCharge;
        this.Amount = Amount;
        this.reference=reference;
    }

    protected Transaction(Parcel in) {
        if (in.readByte() == 0) {
            TrID = null;
        } else {
            TrID = in.readInt();
        }
        AccNo = in.readString();
        Trtype = in.readString();
        TrCharge = in.readDouble();
        reference = in.readString();
        Amount = in.readDouble();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getReference() {
        return reference;
    }

    private String reference;

    public double getAmount() {
        return Amount;
    }

    private double Amount;

    public Integer getTrID() {
        return TrID;
    }

    public String getAccNo() {
        return AccNo;
    }


    public String getType() {
        return Trtype;
    }


    public double getTrCharge() {
        return TrCharge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (TrID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(TrID);
        }
        parcel.writeString(AccNo);
        parcel.writeString(Trtype);
        parcel.writeDouble(TrCharge);
        parcel.writeString(reference);
        parcel.writeDouble(Amount);
    }
}
