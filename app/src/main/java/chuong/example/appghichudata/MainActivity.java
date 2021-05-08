package chuong.example.appghichudata;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //khai bao
    Button btnThemAnh;
    public static Database databaseUpdate;
    ConstraintLayout constraintLayout;
    ListView lvGhiChuAnh;

    public ArrayList<GhiChuAnh> arrayGhiChuAnh;


    GhiChuAnhAdapter adapterAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AnhXaAnh()
        constraintLayout=(ConstraintLayout) findViewById(R.id.menumanghinh);

        lvGhiChuAnh = (ListView) findViewById(R.id.listviewghichu2);
        btnThemAnh= (Button) findViewById(R.id.buttonThemAnh);
        // thêm ảnh tren form moi
        btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,ThemAnhGhiChuActivity.class));
                Intent i= new Intent(MainActivity.this,ThemAnhGhiChuActivity.class);
                i.putExtra("Ma",0);
                startActivity(i);
            }
        });

        //tao Arraylist Anh
        //tao arraylist
        arrayGhiChuAnh = new ArrayList<>();
        //lien ket giua layout va class
        adapterAnh = new GhiChuAnhAdapter(this, R.layout.dong_ghi_chu_hinh_anh, arrayGhiChuAnh);
        lvGhiChuAnh.setAdapter(adapterAnh);


        // tao databaseUpdate 2
        databaseUpdate = new Database(this, "ghichu2.sqlite", null, 1);
        //tao bang cong viec cua ghi chu 2
        databaseUpdate.QueryData("CREATE TABLE IF NOT EXISTS GhiChu2(Id INTEGER PRIMARY KEY AUTOINCREMENT,TieuDe VARCHAR(200),NoiDung VARCHAR(100000),Ngay VARCHAR(20),Hinhanh BLOB NULL)");
        //databaseUpdate.QueryData("DELETE FROM GhiChu2");

        //insert database
        //database.QueryData("INSERT INTO CongViec VALUES(null,'The thao','toan, ly, hoa','12/04/2013')");
        //xuat danh sach

        GetDataGhiChuAnh();
        lvGhiChuAnh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showthemhinhanh(position);
            }
        });
        lvGhiChuAnh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                XoaGhiChuAnh(position);
                return true;
            }
        });
    }
    public  void XoaGhiChuAnh(int position)
    {
        GhiChuAnh s = arrayGhiChuAnh.get(position);
        Dialog_XoaGhiChuAnh(s.getIdCV(),s.getTieude());
    }
    public void showthemhinhanh(int position)
    {
        GhiChuAnh s = arrayGhiChuAnh.get(position);
        Intent i= new Intent(MainActivity.this,ThemAnhGhiChuActivity.class);
        i.putExtra("Ma",1);
        i.putExtra("Id",s.getIdCV());
        i.putExtra("Tieude",s.getTieude());
        i.putExtra("NoiDung",s.getNoidung());
        i.putExtra("Ngay",s.getNgayghi());
        i.putExtra("Hinhanh",s.getHinhanh());
        startActivity(i);


    }

    private void GetDataGhiChuAnh(){
        Cursor data = databaseUpdate.GetData("SELECT * FROM GhiChu2");
        arrayGhiChuAnh.clear();
        while (data.moveToNext()){
            arrayGhiChuAnh.add(new GhiChuAnh(
                    data.getInt(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getBlob(4)));
        }
        adapterAnh.notifyDataSetChanged();
    }
    // ham tao menu them


    public void Dialog_XoaGhiChuAnh(int Id,String tieude){
        AlertDialog.Builder dialog_xoa = new AlertDialog.Builder(this);
        dialog_xoa.setMessage("Bạn có muốn xóa ghi chú "+ tieude +" hay không ?");

        dialog_xoa.setNegativeButton(" Không ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog_xoa.setPositiveButton(" Có ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseUpdate.QueryData("DELETE FROM GhiChu2 WHERE Id='"+Id+"'");
                Toast.makeText(MainActivity.this,"Đã xóa ghi chú "+tieude,Toast.LENGTH_SHORT);
                GetDataGhiChuAnh();
            }
        });
        dialog_xoa.show();
    }

    //cài đặt menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //lay layout tu add_ghichu
        getMenuInflater().inflate(R.menu.add_ghichu,menu);
        //tra ve
        return super.onCreateOptionsMenu(menu);
    }
    // Xét sự kiện
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuAdd)
        {
            Intent i= new Intent(MainActivity.this,ThemAnhGhiChuActivity.class);
            i.putExtra("Ma",0);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.menuDeleteAll)
        {
            DeleteAll();
        }
        else if(item.getItemId()==R.id.menumanghinh)
        {
            //constraintLayout.setBackgroundColor(Color.WHITE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void DeleteAll() {
        AlertDialog.Builder dialog_xoa = new AlertDialog.Builder(this);
        dialog_xoa.setMessage("Bạn có muốn xóa tất cả ghi chú hay không ?");

        dialog_xoa.setNegativeButton(" Không ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog_xoa.setPositiveButton(" Có ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseUpdate.QueryData("DELETE FROM GhiChu2 ");
                Toast.makeText(MainActivity.this,"Đã xóa ",Toast.LENGTH_SHORT);
                GetDataGhiChuAnh();
            }
        });
        dialog_xoa.show();
    }
    private  void DialogMaunen(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_settingcolor);


        //show dialog
        dialog.show();

    }


}