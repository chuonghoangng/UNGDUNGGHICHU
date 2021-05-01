package chuong.example.appghichudata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
    Database database;
    public static Database databaseUpdate;
    ListView lvCongViec;
    ListView lvGhiChuAnh;
    public ArrayList<CongViec> arrayCongViec;
    public ArrayList<GhiChuAnh> arrayGhiChuAnh;

    CongViecAdapter adapter;
    GhiChuAnhAdapter adapterAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AnhXa();
        lvCongViec = (ListView) findViewById(R.id.listviewghichu);

        //AnhXaAnh()
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
        //tao arraylist
        arrayCongViec = new ArrayList<>();
        //lien ket giua layout va class
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec3, arrayCongViec);
        lvCongViec.setAdapter(adapter);
        //tao Arraylist Anh
        //tao arraylist
        arrayGhiChuAnh = new ArrayList<>();
        //lien ket giua layout va class
        adapterAnh = new GhiChuAnhAdapter(this, R.layout.dong_ghi_chu_hinh_anh, arrayGhiChuAnh);
        lvGhiChuAnh.setAdapter(adapterAnh);
        // tao database
        database = new Database(this, "ghichu.sqlite", null, 1);
        //tao bang cong viec cua ghi chu
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT,TieuDe VARCHAR(200),NoiDung VARCHAR(100000),Ngay VARCHAR(20))");
        // tao databaseUpdate 2
        databaseUpdate = new Database(this, "ghichu2.sqlite", null, 1);
        //tao bang cong viec cua ghi chu 2
        databaseUpdate.QueryData("CREATE TABLE IF NOT EXISTS GhiChu2(Id INTEGER PRIMARY KEY AUTOINCREMENT,TieuDe VARCHAR(200),NoiDung VARCHAR(100000),Ngay VARCHAR(20),Hinhanh BLOB NULL)");
        //databaseUpdate.QueryData("DELETE FROM GhiChu2");

        //insert database
        //database.QueryData("INSERT INTO CongViec VALUES(null,'The thao','toan, ly, hoa','12/04/2013')");
        //xuat danh sach
        GetDataGhiChu();
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
                return false;
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
    private  void GetDataGhiChu(){
        //xuat theo sqlite
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()) {
            //xuat theo cot cua database
            int id = dataCongViec.getInt(0);
            String tieude= dataCongViec.getString(1);
            String noidung = dataCongViec.getString(2);
            String ngay = dataCongViec.getString(3);
            // them vao arraylist
            arrayCongViec.add(new CongViec(id, tieude, noidung, ngay));

            //select database
            //GetDataGhiChu();
        }
        adapter.notifyDataSetChanged();
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
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }
    public void Dialog_SuaGhiChu(int Id,String tieude,String noidung){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_ghichu);
        //anh xa co dialog
        TextView textghichu=(TextView) dialog.findViewById(R.id.textghichuEdit);
        EditText edittieude = (EditText) dialog.findViewById(R.id.edittieudeEdit);
        EditText editnoidung = (EditText) dialog.findViewById(R.id.editnoidungEdit);
        Button btnSave = (Button) dialog.findViewById(R.id.buttoSaveEdit);
        Button btnExit = (Button) dialog.findViewById(R.id.buttonExitEdit);
        //do du lieu ra edit text
        edittieude.setText(tieude);
        editnoidung.setText(noidung);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thoat dialog
                dialog.dismiss();

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieudemoi = edittieude.getText().toString();
                String noidungmoi = editnoidung.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                String formattedDate= DateFormat.getDateInstance().format(currentTime);
                String Time = DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime);
                String ngaynhapmoi = formattedDate+" "+Time;

                if (tieudemoi.equals("")||noidungmoi.equals(""))
                {
                    //thong bao khi nhap khong day du
                    Toast.makeText(MainActivity.this,"Vui lòng nhập thông tin đầy đủ",Toast.LENGTH_SHORT).show();
                }

                else{
                    //update database
                    database.QueryData("UPDATE CongViec SET TieuDe = '"+tieudemoi+"',NoiDung= '"+noidungmoi+"',Ngay ='"+ngaynhapmoi+"' WHERE Id='"+Id+"'");
                    //tat dialog
                    dialog.dismiss();
                    //thong bao cap nhat thanh cong
                    Toast.makeText(MainActivity.this,"Cập nhật ghi chú thành công ",Toast.LENGTH_SHORT).show();
                    //xuat danh sach
                    GetDataGhiChu();
                }

            }
        });

        dialog.show();
    }
    private  void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_ghi_chu);
        //anh xa co dialog
        TextView textghichu=(TextView) dialog.findViewById(R.id.textghichu);
        EditText edittieude = (EditText) dialog.findViewById(R.id.edittieude);
        EditText editnoidung = (EditText) dialog.findViewById(R.id.editnoidung);
        Button btnSave = (Button) dialog.findViewById(R.id.buttoSave);
        Button btnExit = (Button) dialog.findViewById(R.id.buttonExit);
        //thuc hien chuc nang thoat
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thoat dialog
                dialog.dismiss();
            }
        });
        //thuc hien chuc nang luu ghi chu khi them
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gan gia tri
                String tieude = edittieude.getText().toString();
                String noidung = editnoidung.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                String formattedDate= DateFormat.getDateInstance().format(currentTime);
                String Time = DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime);

                String ngaynhap = formattedDate+" "+Time;

                if (tieude.equals("")||noidung.equals(""))
                {
                    //thong bao khi nhap khong day du
                    Toast.makeText(MainActivity.this,"Vui lòng nhập thông tin đầy đủ",Toast.LENGTH_SHORT).show();
                }

                else{
                    //insert database
                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+tieude+"','"+noidung+"','"+ngaynhap+"')");
                    //tat dialog
                    dialog.dismiss();
                    //thong bao khi nhap thanh cong
                    Toast.makeText(MainActivity.this,"Thêm ghi chú thành công",Toast.LENGTH_SHORT).show();
                    //xuat danh sach
                    GetDataGhiChu();
                }

            }
        });
        //show dialog
        dialog.show();

    }
    public void Dialog_XoaGhiChu(int Id,String tieude){
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
                database.QueryData("DELETE FROM CongViec WHERE Id='"+Id+"'");
                Toast.makeText(MainActivity.this,"Đã xóa ghi chú "+tieude,Toast.LENGTH_SHORT);
                GetDataGhiChu();
            }
        });
        dialog_xoa.show();
    }
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

}