package chuong.example.appghichudata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThemAnhGhiChuActivity extends AppCompatActivity {

    Button btnadd,btnhuy;
    ImageButton imgcamera,imgfolder,imgalarm;
    EditText edttieude,edtnoidung;
    ImageView imghinh2;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER= 456;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_anh_ghi_chu);

        Anhxa();


            int maCN=getIntent().getIntExtra("Ma",456);

            if(maCN==1)
            {
                int id = getIntent().getIntExtra("Id", 1234);
                String tieude = getIntent().getStringExtra("Tieude");
                String noiDung = getIntent().getStringExtra("NoiDung");
                String ngay = getIntent().getStringExtra("Ngay");

                byte[] anh = getIntent().getByteArrayExtra("Hinhanh");
                Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
                imghinh2.setImageBitmap(bitmap);
                edttieude.setText(tieude);
                edtnoidung.setText(noiDung);
            }
            //int id = getIntent().getIntExtra("Id", 1234);
            //String tieude = getIntent().getStringExtra("Tieude");
            //String noiDung = getIntent().getStringExtra("NoiDung");
            //String ngay = getIntent().getStringExtra("Ngay");

            //byte[] anh = getIntent().getByteArrayExtra("Hinhanh");
            //Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
            //imghinh2.setImageBitmap(bitmap);
            //edttieude.setText(tieude);
            //edtnoidung.setText(noiDung);









        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ThemAnhGhiChuActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA
                );
            }
        });
        imgfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ThemAnhGhiChuActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // lay ngay hien tai
                    Date currentTime = Calendar.getInstance().getTime();
                    String formattedDate= DateFormat.getDateInstance().format(currentTime);
                    String Time = DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime);
                    String ngaynhap = formattedDate+" "+Time;
                    //chuyen img ve byte[]
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imghinh2.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhanh = byteArray.toByteArray();

                if(maCN==0){
                    MainActivity.databaseUpdate.INSERT_PICTER(
                            edttieude.getText().toString().trim(),
                            edtnoidung.getText().toString().trim(),
                            ngaynhap,
                            hinhanh
                    );
                }else {
                    MainActivity.databaseUpdate.UPDATE_PICTER(
                            getIntent().getIntExtra("Id", 1234),
                            edttieude.getText().toString().trim(),
                            edtnoidung.getText().toString().trim(),
                            ngaynhap,
                            hinhanh);
                }
                Toast.makeText(ThemAnhGhiChuActivity.this,"Cap nhat ghi chu thanh cong",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ThemAnhGhiChuActivity.this,MainActivity.class));
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemAnhGhiChuActivity.this,MainActivity.class));
            }
        });
        imgalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ThemAnhGhiChuActivity.this,ChucNangNhacNho.class);
                i.putExtra("Ma",0);
                startActivity(i);
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CODE_CAMERA:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CODE_CAMERA);
                }else
                {
                    Toast.makeText(this,"Bạn không cho phép truy cập camera",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,REQUEST_CODE_FOLDER);
                }else
                {
                    Toast.makeText(this,"Bạn không cho phép truy cập vào thư viện ảnh",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_CAMERA && resultCode== RESULT_OK && data !=null){
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            imghinh2.setImageBitmap(bitmap);
        }
        if(requestCode==REQUEST_CODE_FOLDER && resultCode== RESULT_OK && data !=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imghinh2.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Anhxa() {
        btnadd= (Button) findViewById(R.id.buttonAddAnh2);
        btnhuy = (Button)findViewById(R.id.ButtonExit2);
        imghinh2 = (ImageView) findViewById(R.id.imageViewAnh2);
        imgcamera =(ImageButton) findViewById(R.id.imageButtonCamera2);
        imgfolder =(ImageButton) findViewById( R.id.imageButtonfileFoder2);
        imgalarm =(ImageButton) findViewById(R.id.imagealarn);
        edttieude=(EditText) findViewById(R.id.editTexttieude2);
        edtnoidung=(EditText) findViewById(R.id.editTextNoidung2);

    }



}