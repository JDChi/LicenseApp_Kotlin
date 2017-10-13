// ILicense.license.jdnew.com.serverapp.aidl
package license.jdnew.com.serverapp.aidl;

// Declare any non-default types here with import statements
import license.jdnew.com.serverapp.aidl.RootLicenseData;
interface ILicense {
    RootLicenseData returnLicense(in String licenseData);

          String returnCheckResult(in String submitData);
}
