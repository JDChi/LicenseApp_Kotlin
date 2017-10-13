package license.jdnew.com.serverapp.aidl

import android.os.Parcel
import android.os.Parcelable

/**
 * description
 * Created by JD
 * on 2017/10/13.
 */
data class RootLicenseData(val license: String, val rsaPublicKey: ByteArray?) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.createByteArray()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(license)
        writeByteArray(rsaPublicKey)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<RootLicenseData> = object : Parcelable.Creator<RootLicenseData> {
            override fun createFromParcel(source: Parcel): RootLicenseData = RootLicenseData(source)
            override fun newArray(size: Int): Array<RootLicenseData?> = arrayOfNulls(size)
        }
    }
}