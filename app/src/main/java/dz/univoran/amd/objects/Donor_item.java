package dz.univoran.amd.objects;

public class Donor_item {

    private int Donor_Id;
    private String Nom;
    private String DateNaissance;
    private String Sex;
    private String Address;

    public Donor_item(int donor_Id, String nom, String dateNaissance, String sex, String address, String groupeSanguins, String numero, String email_Address) {
        Donor_Id = donor_Id;
        Nom = nom;
        DateNaissance = dateNaissance;
        Sex = sex;
        Address = address;
        GroupeSanguins = groupeSanguins;
        this.numero = numero;
        this.email_Address = email_Address;
    }

    private String GroupeSanguins;
    private String numero;
    private String email_Address;

    public int getDonor_Id() {
        return Donor_Id;
    }

    public void setDonor_Id(int donor_Id) {
        Donor_Id = donor_Id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGroupeSanguins() {
        return GroupeSanguins;
    }

    public void setGroupeSanguins(String groupeSanguins) {
        GroupeSanguins = groupeSanguins;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail_Address() {
        return email_Address;
    }

    public void setEmail_Address(String email_Address) {
        this.email_Address = email_Address;
    }
}
