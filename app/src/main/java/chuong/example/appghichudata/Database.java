package chuong.example.appghichudata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // truy van khong tra ve ket qua
    public void QueryData(String sql )
    {
        SQLiteDatabase database= getWritableDatabase();
        database.execSQL(sql);
    }
    //truy van tra ve ket qua
    public Cursor GetData(String sql)
    {
        SQLiteDatabase database= getReadableDatabase();
        return  database.rawQuery(sql,null);
    }
    //INSERT ANH
    public void INSERT_PICTER(String tieude,String noidung,String ngaynhap,byte[] hinh){
        SQLiteDatabase database= getWritableDatabase();
        String sql = "INSERT INTO GhiChu2 VALUES(null,?,?,?,?)";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.bindString(1,tieude);
        statement.bindString(2,noidung);
        statement.bindString(3,ngaynhap);
        statement.bindBlob(4,hinh);

        statement.executeInsert();



    }
    public void UPDATE_PICTER(int Id,String tieudemoi,String noidungmoi,String ngaynhapmoi,byte[] hinhmoi){


        //QueryData("UPDATE GhiChu2 SET TieuDe = '"+tieudemoi+"',NoiDung= '"+noidungmoi+"',Ngay ='"+ngaynhapmoi+"',Hinhanh='"+hinhmoi+"' WHERE Id='"+Id+"'");
        SQLiteDatabase database= getWritableDatabase();
        String sql = "UPDATE GhiChu2 SET TieuDe = ?,NoiDung= ?,Ngay =?,Hinhanh=? WHERE Id='"+Id+"'";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.bindString(1,tieudemoi);
        statement.bindString(2,noidungmoi);
        statement.bindString(3,ngaynhapmoi);
        statement.bindBlob(4,hinhmoi);


        statement.executeInsert();



    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
