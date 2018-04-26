package dz.univoran.amd.objects;

public class BankBlood {
    private int BloodBankId;
    private String BloodBankName;
    private String Address;
    private String Phone;
    private String City;

    public BankBlood(int bloodBankId, String bloodBankName, String address, String phone, String city) {
        BloodBankId = bloodBankId;
        BloodBankName = bloodBankName;
        Address = address;
        Phone = phone;
        City = city;
    }

    public int getBloodBankId() {
        return BloodBankId;
    }

    public void setBloodBankId(int bloodBankId) {
        BloodBankId = bloodBankId;
    }

    public String getBloodBankName() {
        return BloodBankName;
    }

    public void setBloodBankName(String bloodBankName) {
        BloodBankName = bloodBankName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
