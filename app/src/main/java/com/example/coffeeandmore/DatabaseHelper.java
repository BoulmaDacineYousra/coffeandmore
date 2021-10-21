package com.example.coffeeandmore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private final static String TAG = "helper";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "name.db", null, 21);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists UserData (id integer  primary key" +
                " autoincrement , name text , email text unique , password text )");

        db.execSQL("create table if not exists ShoopingCart(id_user integer ,id_product integer ,  quantity integer , " +
                "total integer ,FOREIGN KEY(id_user) REFERENCES UserData(id))," +
                "FOREIGN KEY(id_product) REFERENCES products(id)");
    //    db.execSQL("create table if not exists favorites(id_user , id_product ,FOREIGN KEY(id_user) REFERENCES UserData(id))) , z ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists UserData ");
        db.execSQL("drop table if exists ShoppingCart");
        onCreate(db);
    }


    public Boolean Regester(String name, String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);


        long user = sqLiteDatabase.insert("UserData", null, contentValues);

        if (user != -1) {
            return true;
        } else {
            return false;

        }


    }


    public Boolean AddToCart(int idUser, int idProduct, int quantity, int total) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id_user", idUser);
        contentValues.put("id_product", idProduct);
        contentValues.put("quantity", quantity);
        contentValues.put("total", total);

        long user = sqLiteDatabase.insert("ShoopingCart", null, contentValues);
        if (user != -1) {
            return true;
        } else {
            return false;

        }

    }

    public UserData getUserData(String email, String password) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM UserData  WHERE EMAIL=? AND PASSWORD=?",
                new String[]{email, password});
        mCursor.moveToFirst();

        UserData user = new UserData();

       user.setUid(mCursor.getInt(0));
       user.setUNAME(mCursor.getString(1));
        user.setEMAIL(mCursor.getString(2));
        user.setPWD(mCursor.getString(3));

        mCursor.close();
        return user;


    }

    public boolean getData(String email, String password) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM UserData  WHERE EMAIL=? AND PASSWORD=?",
                new String[]{email, password});


        //  if (mCursor != null) {
        if (mCursor.getCount() > 0) {

            mCursor.close();
            return true;
        } else {
            return false;
        }
        //  }

    }


    //get the all notes
    public List<ModelProduct> GetProducts() {
        ModelProduct product = null;
        List<ModelProduct> List = new ArrayList<>();

        // select all query
        String select_query = "SELECT *FROM Products";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelProduct noteModel = new ModelProduct();
            noteModel.setId(cursor.getInt(0));
            noteModel.setProductName(cursor.getString(2));
            noteModel.setProductPrice(cursor.getInt(3));
            noteModel.setProductDescription(cursor.getString(4));
            List.add(noteModel);
            cursor.moveToNext();

        }

        return List;
    }

    public List<String> GetCategories (){
        ModelProduct product = null;
        List<String> List = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Categories " , null , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            List.add(cursor.getString(1));
            cursor.moveToNext();
        }
        return List;

    }

    public List<ModelProduct> GetFavorites(int uid ) {
        ModelProduct product = null;
        List<ModelProduct> List = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from products  where id in ( select id_product from favorites where id_user = ?)" ,  new String[] {String.valueOf(uid)} );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelProduct noteModel = new ModelProduct();
            noteModel.setId(cursor.getInt(0));
            noteModel.setProductName(cursor.getString(2));
            noteModel.setProductPrice(cursor.getInt(3));
            noteModel.setProductDescription(cursor.getString(4)) ;
            List.add(noteModel);
            cursor.moveToNext();
        }
        return List;
    }


    public List<ModelProduct> search(String filter  ) {
        ModelProduct product = null;
        List<ModelProduct> List = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Products where (product_name like ? or product_descroption like ?)" ,  new String[] {"%"+filter+"%" , "%"+filter+"%"  } );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelProduct noteModel = new ModelProduct();
            noteModel.setId(cursor.getInt(0));
            noteModel.setProductName(cursor.getString(2));
            noteModel.setProductPrice(cursor.getInt(3));
            noteModel.setProductDescription(cursor.getString(4)) ;
            List.add(noteModel);
            cursor.moveToNext();
        }
        return List;
    }




    public boolean LogInUser(String email, String passrord) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM UserData", null, null);
        while (cursor.moveToNext()) {
            String Email = cursor.getString(3);
            String Password = cursor.getString(4);
            if (TextUtils.equals(Email, email) && TextUtils.equals(passrord, Password)) {
                return true;
            }


        }
        return false;
    }

    public Boolean FavoriteState(int uid , int pid) {

       // String Query = "select * from favorites where id_user = " +uid +"and id_product ="+ pid ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery( "select * from favorites where ( id_user = ?  and id_product = ?) " ,  new String[]{String.valueOf(uid), String.valueOf(pid)} );
               // new String[] {  String.valueOf( uid) });

        mCursor.moveToFirst() ;
        if (mCursor.getCount() > 0) {

            mCursor.close();
            return true;
        } else {
            return false;
        }
    }


    public void UpdateQuatity(int uid , int pid , int qnt ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqliteQuery = "update table shoopingcart set quantity ="+qnt+"where id_user = " +uid +"and id_product = "+ pid ;
        db.execSQL(sqliteQuery);

    }


     public  void RemoveFromCart(int uid , int pid ){
          SQLiteDatabase db = this.getReadableDatabase();
          db.rawQuery( "delete  from shoopingcart where ( id_user = ?  and id_product = ?) " ,  new String[]{String.valueOf(uid), String.valueOf(pid)} );
         // new String[] {  String.valueOf( uid) });


     }

    public Boolean AddTofavorites(int idUser, int idProduct ) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id_user", idUser);
        contentValues.put("id_product", idProduct);


        long user = sqLiteDatabase.insert("favorites", null, contentValues );
        if (user != -1) {
            return true;
        } else {
            return false;

        }

    }



    public boolean RemoveFromfavorites(int uid, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery( "delete  from favorites where ( id_user = ?  and id_product = ?) " ,
                new String[]{String.valueOf(uid), String.valueOf(id)} );
        // new String[] {  String.valueOf( uid) });


        if (mCursor.getCount() > 0) {

            mCursor.close();
            return true;
        } else {
            return false;
        }
    }

    //get the all notes
    public List<ModelProduct> GetCartShopping (int uid ) {
        ModelProduct product = null;
        List<ModelProduct> List = new ArrayList<>();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from products  where id in ( select id_product from shoopingcart where id_user = ? ) " , new String[]{String.valueOf(uid)} );
               // , new String[] {String.valueOf(uid)} );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelProduct products = new ModelProduct();
            products.setId(cursor.getInt(0));
            products.setProductName(cursor.getString(2));
            products.setProductPrice(cursor.getInt(3));
            products.setProductDescription(cursor.getString(4));
            List.add(products);
            cursor.moveToNext();

        }

        return List;
    }

  /*  public int getQuantity(int Uid , int Pid ){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery( "SELECT * from shoopingcart where (id_user= 2  and id_product= 5 ", new String[]{ String.valueOf(Uid) , String.valueOf(Pid) } );



        if (mCursor.getCount() > 0) {

            return mCursor.getInt(2);


        } else {
            return 0 ;
        }

    } */
}

