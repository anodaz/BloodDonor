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
        db.execSQL("create table IF NOT EXISTS donor(KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,donor_Id INTEGER ,nom TEXT,dateNaissance TEXT,sex TEXT,groupeSanguin TEXT,address TEXT,numero TEXT,adresseMail TEXT)");
        db.execSQL("create table IF NOT EXISTS BankBlood(bloodBankId INTEGER,bloodBankName TEXT,address TEXT,phone TEXT,city TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if EXISTS donor");
        db.execSQL("Drop table if EXISTS BankBlood");
        onCreate(db);
    }
    public void addDonor(int donor_Id, String nom, String dateNaissance, String sex, String address, String groupeSanguin, String numero, String adresseMail){
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor res=db1.rawQuery("select * from donor where donor_Id = "+donor_Id,null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            System.out.println("hna "+res.getString(0));
            return;
        }
        ContentValues contentValues=new ContentValues();
        contentValues.put("donor_Id",donor_Id);
        contentValues.put("nom",nom);
        contentValues.put("dateNaissance",dateNaissance);
        contentValues.put("sex",sex);
        contentValues.put("address",address);
        contentValues.put("groupeSanguin",groupeSanguin);
        contentValues.put("numero",numero);
        contentValues.put("adresseMail",adresseMail);
        db.insert("donor",null,contentValues);
       // Cursor rowInserted=db.rawQuery("insert into donor (donor_Id, nom, dateNaissance, sex, address, groupeSanguin, numero, adresseMail) values\n" +
             //   "("+donor_Id+", '"+nom+"','"+dateNaissance+"','"+sex+"','"+address+"','"+groupeSanguin+"','"+numero+"','"+adresseMail+"');",null);
        System.out.println("hna");

    }
    public void addBankBlood(int bloodBankId, String bloodBankName, String address, String phone, String city){
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor res=db1.rawQuery("select * from BankBlood where bloodBankId = "+bloodBankId,null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            System.out.println("hna "+res.getString(0));
            return;
        }
        ContentValues contentValues=new ContentValues();
        contentValues.put("bloodBankId",bloodBankId);
        contentValues.put("bloodBankName",bloodBankName);
        contentValues.put("address",address);
        contentValues.put("phone",phone);
        contentValues.put("city",city);
        db.insert("BankBlood",null,contentValues);
        //db.rawQuery("insert or replace into BankBlood (bloodBankId, bloodBankName, address, phone, city) values\n" +
            //   "((select bloodBankId from BankBlood where bloodBankId = "+bloodBankId+"), '"+bloodBankName+"','"+address+"','"+phone+"','"+city+"');",null);

    }
    public ArrayList<BankBlood> getBanks(String city){
        ArrayList<BankBlood> rentalProperties=new ArrayList<BankBlood>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from BankBlood where city = '"+city+"'",null);
        res.moveToFirst();
       // System.out.println("hna "+res.getInt(res.getColumnIndex("bloodBankId")));
        while (res.isAfterLast() == false){
            rentalProperties.add(new BankBlood(res.getInt(res.getColumnIndex("bloodBankId")),res.getString(res.getColumnIndex("bloodBankName")),res.getString(res.getColumnIndex("address")),res.getString(res.getColumnIndex("phone")),res.getString(res.getColumnIndex("city"))));
            res.moveToNext();

        }

        return rentalProperties;
    }
    public ArrayList<Donor_item> selectBloodGroup(String group){
        ArrayList<Donor_item> rentalProperties=new ArrayList<Donor_item>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from donor where groupeSanguin = '"+group+"'",null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            System.out.println("hna "+res.getInt(res.getColumnIndex("donor_Id")));
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

