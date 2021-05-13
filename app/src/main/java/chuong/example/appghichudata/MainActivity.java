package chuong.example.appghichudata;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentProviderClient;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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
    //khai bao cho dialog color
    TextView color;
    ImageButton img1,img2,img3,img4,img5,img6,img7;
    //khai báo cho database
    public static Database databaseUpdate;
    //khai báo cho constrainLayout Main
    ConstraintLayout constraintLayout;
    //khai báo danh sách
    ListView lvGhiChuAnh;
    //khai báo cho danh sách class
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
        // xử lí sự kiện thêm ảnh tren form moi
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
        //chọn để cập nhật
        lvGhiChuAnh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showthemhinhanh(position);
            }
        });
        //xóa ghi chú khỏi danh sách
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
    // Xét sự kiện menu
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
            DialogMaunen();
            //constraintLayout.setBackgroundColor(Color.WHITE);
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
        dialog.setContentView(R.layout.dialog_setting_color);
        //Anh xa

        color = (TextView) dialog.findViewById(R.id.COLOR);
        img1 =(ImageButton) dialog.findViewById(R.id.imageButton1);
        img2 =(ImageButton) dialog.findViewById(R.id.imageButton2);
        img3 =(ImageButton) dialog.findViewById(R.id.imageButton3);
        img4 =(ImageButton) dialog.findViewById(R.id.imageButton4);
        img5 =(ImageButton) dialog.findViewById(R.id.imageButton5);
        img6 =(ImageButton) dialog.findViewById(R.id.imageButton6);
        img7 =(ImageButton) dialog.findViewById(R.id.imageButton7);
        //sử lý sự kiện
        ClickColor();
        //show dialog
        dialog.show();

    }

    private void ClickColor() {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //constraintLayout.setBackgroundColor(Color.RED);
                constraintLayout.setBackgroundResource(R.drawable.maudo);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //constraintLayout.setBackgroundColor(Color.);
                constraintLayout.setBackgroundResource(R.drawable.maucam);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //constraintLayout.setBackgroundColor(Color.YELLOW);
                constraintLayout.setBackgroundResource(R.drawable.mauvang);


            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //constraintLayout.setBackgroundColor(Color.GREEN);
                constraintLayout.setBackgroundResource(R.drawable.mauluc);


            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //constraintLayout.setBackgroundColor(Color.BLUE);
                constraintLayout.setBackgroundResource(R.drawable.maulam);

            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setBackgroundResource(R.drawable.mautim);
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }


}