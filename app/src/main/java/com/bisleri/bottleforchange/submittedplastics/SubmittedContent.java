package com.bisleri.bottleforchange.submittedplastics;

import android.os.Parcel;
import android.os.Parcelable;

class SubmittedContent implements Parcelable {
    public String total_per_day;
    public String submission_timestamp;
    public String date;
    public String ticket_to_user_id_name;
    public String name;
    public String name_of_entity;

    protected SubmittedContent(Parcel in) {
        total_per_day = in.readString();
        submission_timestamp = in.readString();
        date = in.readString();
        ticket_to_user_id_name = in.readString();
        name = in.readString();
        name_of_entity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total_per_day);
        dest.writeString(submission_timestamp);
        dest.writeString(date);
        dest.writeString(ticket_to_user_id_name);
        dest.writeString(name);
        dest.writeString(name_of_entity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubmittedContent> CREATOR = new Creator<SubmittedContent>() {
        @Override
        public SubmittedContent createFromParcel(Parcel in) {
            return new SubmittedContent(in);
        }

        @Override
        public SubmittedContent[] newArray(int size) {
            return new SubmittedContent[size];
        }
    };
}
