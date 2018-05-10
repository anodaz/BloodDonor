package dz.univoran.amd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import dz.univoran.amd.objects.BankBlood;
import dz.univoran.amd.objects.Donor_item;

public class DBSqliteCon extends SQLiteOpenHelper {
    public DBSqliteCon(Context context) {
        super(context, Constants.DBNAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS donor(donor_Id INTEGER primary key,nom TEXT,dateNaissance TEXT,sex TEXT,groupeSanguin TEXT,address TEXT,numero TEXT,adresseMail TEXT)");
        db.execSQL("create table IF NOT EXISTS BankBlood(bloodBankId INTEGER primary key,bloodBankName TEXT,address TEXT,phone TEXT,city TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if EXISTS donor");
        db.execSQL("Drop table if EXISTS BankBlood");
        onCreate(db);
    }
    public void addDonor(int donor_Id, String nom, String dateNaissance, String sex, String address, String groupeSanguins, String numero, String email_Address){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("donor_Id",donor_Id);
        contentValues.put("nom",nom);
        contentValues.put("dateNaissance",dateNaissance);
        contentValues.put("sex",sex);
        contentValues.put("address",address);
        contentValues.put("groupeSanguins",groupeSanguins);
        contentValues.put("numero",numero);
        contentValues.put("email_Address",email_Address);
        db.insert("donor",null,contentValues);

    }
    public void addBankBlood(int bloodBankId, String bloodBankName, String address, String phone, String city){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("bloodBankId",bloodBankId);
        contentValues.put("bloodBankName",bloodBankName);
        contentValues.put("address",address);
        contentValues.put("phone",phone);
        contentValues.put("city",city);
        db.insert("BankBlood",null,contentValues);

    }
    public ArrayList<BankBlood> getBanks(String city){
        ArrayList<BankBlood> rentalProperties=new ArrayList<BankBlood>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from BankBlood where city = "+city,null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            rentalProperties.add(new BankBlood(res.getInt(res.getColumnIndex("bloodBankId")),res.getString(res.getColumnIndex("bloodBankName")),res.getString(res.getColumnIndex("address")),res.getString(res.getColumnIndex("phone")),res.getString(res.getColumnIndex("city"))));
            res.moveToNext();
        }
        return rentalProperties;
    }
    public ArrayList<Donor_item> selectBloodGroup(String group){
        ArrayList<Donor_item> rentalProperties=new ArrayList<Donor_item>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from donor where groupeSanguin = "+group,null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            rentalProperties.add(new Donor_item(res.getInt(res.getColumnIndex("donor_Id")),res.getString(res.getColumnIndex("nom")),res.getString(res.getColumnIndex("dateNaissance")),res.getString(res.getColumnIndex("sex")),res.getString(res.getColumnIndex("address")),res.getString(res.getColumnIndex("groupeSanguin")),res.getString(res.getColumnIndex("numero")),res.getString(res.getColumnIndex("adresseMail"))));
            res.moveToNext();
        }
        return rentalProperties;
    }
    public Donor_item getProfile(int id){
        Donor_item donor=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from donor where donor_Id = "+id,null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            donor=new Donor_item(res.getInt(res.getColumnIndex("donor_Id")),res.getString(res.getColumnIndex("nom")),res.getString(res.getColumnIndex("dateNaissance")),res.getString(res.getColumnIndex("sex")),res.getString(res.getColumnIndex("address")),res.getString(res.getColumnIndex("groupeSanguin")),res.getString(res.getColumnIndex("numero")),res.getString(res.getColumnIndex("adresseMail")));
            res.moveToNext();
        }
        return donor;
    }

}

