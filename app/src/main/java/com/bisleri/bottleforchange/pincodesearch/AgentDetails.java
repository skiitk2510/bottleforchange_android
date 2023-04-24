package com.bisleri.bottleforchange.pincodesearch;

import android.os.Parcel;
import android.os.Parcelable;

public class AgentDetails implements Parcelable {
    public String contact_person_name;
    public String mobile_number;
    public String alternate_mobile_number;
    public String address;
    public String user_id;

    protected AgentDetails(Parcel in) {
        contact_person_name = in.readString();
        mobile_number = in.readString();
        alternate_mobile_number = in.readString();
        address = in.readString();
        user_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contact_person_name);
        dest.writeString(mobile_number);
        dest.writeString(alternate_mobile_number);
        dest.writeString(address);
        dest.writeString(user_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AgentDetails> CREATOR = new Creator<AgentDetails>() {
        @Override
        public AgentDetails createFromParcel(Parcel in) {
            return new AgentDetails(in);
        }

        @Override
        public AgentDetails[] newArray(int size) {
            return new AgentDetails[size];
        }
    };
}
