package com.bisleri.bottleforchange.submittedplastics;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

class SubmittedPlasticResponse implements Parcelable {
    public ArrayList<SubmittedContent> content;
    public String total;

    protected SubmittedPlasticResponse(Parcel in) {
        content = in.createTypedArrayList(SubmittedContent.CREATOR);
        total = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(content);
        dest.writeString(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubmittedPlasticResponse> CREATOR = new Creator<SubmittedPlasticResponse>() {
        @Override
        public SubmittedPlasticResponse createFromParcel(Parcel in) {
            return new SubmittedPlasticResponse(in);
        }

        @Override
        public SubmittedPlasticResponse[] newArray(int size) {
            return new SubmittedPlasticResponse[size];
        }
    };
}
