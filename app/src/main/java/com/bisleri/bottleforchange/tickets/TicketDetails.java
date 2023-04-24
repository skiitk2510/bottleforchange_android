package com.bisleri.bottleforchange.tickets;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketDetails implements Parcelable {

    public String contact_person_name;
    public String submission_timestamp;
    public String support_ticket_id;
    public String user_id;
    public String mobile_number;
    public String address;
    public String time_of_cancellation;
    public String add_timestamp;
    public String date;

    protected TicketDetails(Parcel in) {
        contact_person_name = in.readString();
        submission_timestamp = in.readString();
        support_ticket_id = in.readString();
        user_id = in.readString();
        mobile_number = in.readString();
        address = in.readString();
        time_of_cancellation = in.readString();
        add_timestamp = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contact_person_name);
        dest.writeString(submission_timestamp);
        dest.writeString(support_ticket_id);
        dest.writeString(user_id);
        dest.writeString(mobile_number);
        dest.writeString(address);
        dest.writeString(time_of_cancellation);
        dest.writeString(add_timestamp);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TicketDetails> CREATOR = new Creator<TicketDetails>() {
        @Override
        public TicketDetails createFromParcel(Parcel in) {
            return new TicketDetails(in);
        }

        @Override
        public TicketDetails[] newArray(int size) {
            return new TicketDetails[size];
        }
    };
}
