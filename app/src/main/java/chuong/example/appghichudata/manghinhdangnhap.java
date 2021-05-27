package chuong.example.appghichudata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class manghinhdangnhap extends AppCompatActivity {
    public static String matkhau;
    public static SharedPreferences luuMK;
    TextView t1,t2;
    EditText e1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manghinhdangnhap);
        luuMK = getSharedPreferences("MatKhau",MODE_PRIVATE);
        //anh xa
        t1= (TextView) findViewById(R.id.dangnhap);
        t2 = (TextView) findViewById(R.id.thongbao);
        e1=(EditText) findViewById(R.id.nhapmatkhau);
        b1= (Button) findViewById(R.id.login);
        e1.setText(null);

        matkhau=luuMK.getString("ma","1234");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mk = e1.getText().toString().trim();
                if(mk.equals(matkhau)){
                    Intent i= new Intent(manghinhdangnhap.this,MainActivity.class);
                    i.putExtra("Pass",matkhau);
                    startActivity(i);

                }
                else{
                    Toast.makeText(manghinhdangnhap.this,"Mật khẩu sai mời nhập lại ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        luuMatkhau(matkhau);

    }
    public void luuMatkhau(String passwork){
        SharedPreferences.Editor editor = luuMK.edit();
        editor.putString("ma",passwork);
        editor.commit();

    }
}