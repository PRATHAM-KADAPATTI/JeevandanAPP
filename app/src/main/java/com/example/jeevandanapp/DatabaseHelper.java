package com.example.jeevandanapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "jeevandanApp_v1.db";
    public static final int DATABASE_VERSION = 2;

    // Table names
    public static final String TABLE_DOCTOR = "doctor";
    public static final String TABLE_RECIPIENT = "recipient";
    public static final String TABLE_DONOR = "donor";


    // Column names for doctor table
    public static final String DOCTOR_COLUMN_ID = "id";
    public static final String DOCTOR_COLUMN_FULL_NAME = "full_name";
    public static final String DOCTOR_COLUMN_HOSPITAL_NAME = "hospital_name";
    public static final String DOCTOR_COLUMN_SPECIALIZATION = "specialization";
    public static final String DOCTOR_COLUMN_GENDER = "gender";
    public static final String DOCTOR_COLUMN_USERNAME = "username";
    public static final String DOCTOR_COLUMN_PASSWORD = "password";

    // Column names for recipient table
    public static final String RECIPIENT_COLUMN_ID = "id";
    public static final String RECIPIENT_COLUMN_FULL_NAME = "full_name";
    public static final String RECIPIENT_COLUMN_GENDER = "gender";
    public static final String RECIPIENT_COLUMN_ORGAN_REQUIRED = "organ_required";
    public static final String RECIPIENT_COLUMN_BLOOD_GROUP = "blood_group";
    public static final String RECIPIENT_COLUMN_USERNAME = "username";
    public static final String RECIPIENT_COLUMN_EMAIL = "email";
    public static final String RECIPIENT_COLUMN_AGE = "age";
    public static final String RECIPIENT_COLUMN_PHONE_NUMBER = "phone_number";
    public static final String RECIPIENT_COLUMN_PASSWORD = "password";
    public static final String RECIPIENT_COLUMN_ALLOCATION_STATUS = "allocation_status";
    public static final String RECIPIENT_COLUMN_SUPER_URGENT_STATUS = "superurgent_status";
    public static final String RECIPIENT_COLUMN_FILE_PATH = "report";
    

    // Column names for donor table
    public static final String DONOR_COLUMN_ID = "id";
    public static final String DONOR_COLUMN_FULL_NAME = "full_name";
    public static final String DONOR_COLUMN_GENDER = "gender";
    public static final String DONOR_COLUMN_ORGAN_DONATING = "organ_donating";
    public static final String DONOR_COLUMN_BLOOD_GROUP = "blood_group";
    public static final String DONOR_COLUMN_AGE = "age";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the doctor table
        String createDoctorTable = "CREATE TABLE " + TABLE_DOCTOR + " (" +
                DOCTOR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DOCTOR_COLUMN_FULL_NAME + " TEXT, " +
                DOCTOR_COLUMN_HOSPITAL_NAME + " TEXT, " +
                DOCTOR_COLUMN_SPECIALIZATION + " TEXT, " +
                DOCTOR_COLUMN_GENDER + " TEXT, " +
                DOCTOR_COLUMN_USERNAME + " TEXT, " +
                DOCTOR_COLUMN_PASSWORD + " TEXT)";
        sqLiteDatabase.execSQL(createDoctorTable);

        // Create the recipient table
        String createRecipientTable = "CREATE TABLE " + TABLE_RECIPIENT + " (" +
                RECIPIENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPIENT_COLUMN_FULL_NAME + " TEXT, " +
                RECIPIENT_COLUMN_GENDER + " TEXT, " +
                RECIPIENT_COLUMN_ORGAN_REQUIRED + " TEXT, " +
                RECIPIENT_COLUMN_BLOOD_GROUP + " TEXT, " +
                RECIPIENT_COLUMN_USERNAME + " TEXT, " +
                RECIPIENT_COLUMN_EMAIL + " TEXT, " +
                RECIPIENT_COLUMN_AGE + " TEXT, " +
                RECIPIENT_COLUMN_PHONE_NUMBER + " TEXT, " +
                RECIPIENT_COLUMN_PASSWORD + " TEXT, " +
                RECIPIENT_COLUMN_ALLOCATION_STATUS + " TEXT, " +
                RECIPIENT_COLUMN_SUPER_URGENT_STATUS + " TEXT, " +
                RECIPIENT_COLUMN_FILE_PATH + " TEXT)";

        sqLiteDatabase.execSQL(createRecipientTable);

        // Create the donor table
        String createDonorTable = "CREATE TABLE " + TABLE_DONOR + " (" +
                DONOR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DONOR_COLUMN_FULL_NAME + " TEXT, " +
                DONOR_COLUMN_GENDER + " TEXT, " +
                DONOR_COLUMN_ORGAN_DONATING + " TEXT, " +
                DONOR_COLUMN_BLOOD_GROUP + " TEXT, " +
                DONOR_COLUMN_AGE + " TEXT)";
        sqLiteDatabase.execSQL(createDonorTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }

    public long insertDoctor(String fullName, String hospitalName, String specialization, String gender, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOCTOR_COLUMN_FULL_NAME, fullName);
        values.put(DOCTOR_COLUMN_HOSPITAL_NAME, hospitalName);
        values.put(DOCTOR_COLUMN_SPECIALIZATION, specialization);
        values.put(DOCTOR_COLUMN_GENDER, gender);
        values.put(DOCTOR_COLUMN_USERNAME, username);
        values.put(DOCTOR_COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_DOCTOR, null, values);
        db.close();
        return result;
    }

    public long insertRecipient(String fullName, String gender, String organRequired, String bloodGroup,
                                String username, String email, String age, String phoneNumber, String password,
                                String filePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECIPIENT_COLUMN_FULL_NAME, fullName);
        values.put(RECIPIENT_COLUMN_GENDER, gender);
        values.put(RECIPIENT_COLUMN_ORGAN_REQUIRED, organRequired);
        values.put(RECIPIENT_COLUMN_BLOOD_GROUP, bloodGroup);
        values.put(RECIPIENT_COLUMN_USERNAME, username);
        values.put(RECIPIENT_COLUMN_EMAIL, email);
        values.put(RECIPIENT_COLUMN_AGE, age);
        values.put(RECIPIENT_COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(RECIPIENT_COLUMN_PASSWORD, password);
        values.put(RECIPIENT_COLUMN_ALLOCATION_STATUS, "no"); // Set allocation status as "no"
        values.put(RECIPIENT_COLUMN_SUPER_URGENT_STATUS, "no"); // Set super urgent status as "no"
        values.put(RECIPIENT_COLUMN_FILE_PATH, filePath); // Set the file path
        long result = db.insert(TABLE_RECIPIENT, null, values);
        db.close();
        return result;
    }


    public long insertDonor(String fullName, String gender, String organ, String bloodGroup, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DONOR_COLUMN_FULL_NAME, fullName);
        values.put(DONOR_COLUMN_GENDER, gender);
        values.put(DONOR_COLUMN_ORGAN_DONATING, organ);
        values.put(DONOR_COLUMN_BLOOD_GROUP, bloodGroup);
        values.put(DONOR_COLUMN_AGE, age);
        long result = db.insert(TABLE_DONOR, null, values);
        db.close();
        return result;
    }

    public List<String> getAllRecipients() {
        List<String> recipientsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {RECIPIENT_COLUMN_FULL_NAME, RECIPIENT_COLUMN_ORGAN_REQUIRED};
        Cursor cursor = db.query(TABLE_RECIPIENT, columns, null, null, null, null, null);

        int nameIndex = cursor.getColumnIndex(RECIPIENT_COLUMN_FULL_NAME);
        int organRequiredIndex = cursor.getColumnIndex(RECIPIENT_COLUMN_ORGAN_REQUIRED);

        if (nameIndex >= 0 && organRequiredIndex >= 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIndex);
                String organRequired = cursor.getString(organRequiredIndex);

                String recipientInfo = "Name: " + name + ", Organs Required: " + organRequired;
                recipientsList.add(recipientInfo);
            }
        }

        cursor.close();
        db.close();

        return recipientsList;
    }

    public Cursor getRecipientDetails(String recipientName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                RECIPIENT_COLUMN_FULL_NAME,
                RECIPIENT_COLUMN_USERNAME,
                RECIPIENT_COLUMN_GENDER,
                RECIPIENT_COLUMN_ORGAN_REQUIRED,
                RECIPIENT_COLUMN_BLOOD_GROUP,
                RECIPIENT_COLUMN_AGE,
                RECIPIENT_COLUMN_EMAIL,
                RECIPIENT_COLUMN_PHONE_NUMBER,
                RECIPIENT_COLUMN_FILE_PATH
        };
        String selection = RECIPIENT_COLUMN_FULL_NAME + " = ?";
        String[] selectionArgs = {recipientName};

        return db.query(TABLE_RECIPIENT, columns, selection, selectionArgs, null, null, null);
    }

    public List<String> getAllocatedRecipients() {
        List<String> allocatedRecipientsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {RECIPIENT_COLUMN_FULL_NAME, RECIPIENT_COLUMN_ORGAN_REQUIRED};
        String selection = RECIPIENT_COLUMN_ALLOCATION_STATUS + " = ?";
        String[] selectionArgs = {"yes"};
        Cursor cursor = db.query(TABLE_RECIPIENT, columns, selection, selectionArgs, null, null, null);

        int nameIndex = cursor.getColumnIndex(RECIPIENT_COLUMN_FULL_NAME);
        int organRequiredIndex = cursor.getColumnIndex(RECIPIENT_COLUMN_ORGAN_REQUIRED);

        if (nameIndex >= 0 && organRequiredIndex >= 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIndex);
                String organRequired = cursor.getString(organRequiredIndex);

                String recipientInfo = "Name: " + name + ", Organ Required: " + organRequired;
                allocatedRecipientsList.add(recipientInfo);
            }
        }

        cursor.close();
        db.close();

        return allocatedRecipientsList;
    }

    public List<String> getSuperUrgentRecipients() {
        List<String> superUrgentRecipientsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {RECIPIENT_COLUMN_FULL_NAME, RECIPIENT_COLUMN_ORGAN_REQUIRED};
        String selection = RECIPIENT_COLUMN_SUPER_URGENT_STATUS + " = ?";
        String[] selectionArgs = {"yes"};
        Cursor cursor = db.query(TABLE_RECIPIENT, columns, selection, selectionArgs, null, null, null);

        int nameIndex = cursor.getColumnIndex(RECIPIENT_COLUMN_FULL_NAME);
        int organRequiredIndex = cursor.getColumnIndex(RECIPIENT_COLUMN_ORGAN_REQUIRED);

        if (nameIndex >= 0 && organRequiredIndex >= 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIndex);
                String organRequired = cursor.getString(organRequiredIndex);

                String recipientInfo = "Name: " + name + ", Organs Required: " + organRequired;
                superUrgentRecipientsList.add(recipientInfo);
            }
        }

        cursor.close();
        db.close();

        return superUrgentRecipientsList;
    }

    public void updateAllocationStatus(String name, String allocationStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPIENT_COLUMN_ALLOCATION_STATUS, allocationStatus);

        String whereClause = RECIPIENT_COLUMN_FULL_NAME + " = ?";
        String[] whereArgs = {name};

        // Update the row
        db.update(TABLE_RECIPIENT, values, whereClause, whereArgs);

        // Close the database connection
        db.close();
    }


    public Pair<String, String> getOrganAndBloodGroupForRecipient(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {RECIPIENT_COLUMN_ORGAN_REQUIRED, RECIPIENT_COLUMN_BLOOD_GROUP};
        String selection = RECIPIENT_COLUMN_FULL_NAME + " = ?";
        String[] selectionArgs = {name};

        Cursor cursor = db.query(TABLE_RECIPIENT, columns, selection, selectionArgs, null, null, null);

        Pair<String, String> organAndBloodGroup = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String organRequired = cursor.getString(cursor.getColumnIndex(RECIPIENT_COLUMN_ORGAN_REQUIRED));
            @SuppressLint("Range") String bloodGroup = cursor.getString(cursor.getColumnIndex(RECIPIENT_COLUMN_BLOOD_GROUP));
            organAndBloodGroup = new Pair<>(organRequired, bloodGroup);
            cursor.close();
        }

        db.close();
        return organAndBloodGroup;
    }


    public boolean checkCompatibleDonor(String organRequired, String bloodGroup) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {DONOR_COLUMN_ID};
        String selection = DONOR_COLUMN_ORGAN_DONATING + " = ? AND " + DONOR_COLUMN_BLOOD_GROUP + " = ?";
        String[] selectionArgs = {organRequired, bloodGroup};

        Cursor cursor = db.query(TABLE_DONOR, columns, selection, selectionArgs, null, null, null);

        boolean hasCompatibleDonor = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return hasCompatibleDonor;
    }




    public void updateSuperUrgentStatus(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPIENT_COLUMN_SUPER_URGENT_STATUS, "yes");

        String whereClause = RECIPIENT_COLUMN_FULL_NAME + " = ?";
        String[] whereArgs = {name};

        // Update the row
        db.update(TABLE_RECIPIENT, values, whereClause, whereArgs);

        // Close the database connection
        db.close();
    }

    public Cursor getAllDonors() {
        SQLiteDatabase sb = this.getReadableDatabase();
        return sb.query(TABLE_DONOR, null, null, null, null, null, null);
    }


    public Cursor getDoctorProfileDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                DOCTOR_COLUMN_FULL_NAME,
                DOCTOR_COLUMN_HOSPITAL_NAME,
                DOCTOR_COLUMN_SPECIALIZATION,
                DOCTOR_COLUMN_GENDER
        };
        String selection = DOCTOR_COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };
        return db.query(TABLE_DOCTOR, columns, selection, selectionArgs, null, null, null);
    }


    public boolean doctorExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DOCTOR +
                " WHERE " + DOCTOR_COLUMN_USERNAME + " = ? AND " + DOCTOR_COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean recipientExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = RECIPIENT_COLUMN_USERNAME + " = ? AND " + RECIPIENT_COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_RECIPIENT, null, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // In the DatabaseHelper class, add the following method
    public Cursor getRecipientProfileDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                RECIPIENT_COLUMN_FULL_NAME,
                RECIPIENT_COLUMN_GENDER,
                RECIPIENT_COLUMN_ORGAN_REQUIRED,
                RECIPIENT_COLUMN_BLOOD_GROUP,
                RECIPIENT_COLUMN_EMAIL,
                RECIPIENT_COLUMN_AGE,
                RECIPIENT_COLUMN_PHONE_NUMBER,
                RECIPIENT_COLUMN_ALLOCATION_STATUS
        };
        String selection = DOCTOR_COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };
        return db.query(TABLE_RECIPIENT, columns, selection, selectionArgs, null, null, null);
    }

}



