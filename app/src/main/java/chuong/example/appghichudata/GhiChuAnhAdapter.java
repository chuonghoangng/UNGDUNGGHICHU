package chuong.example.appghichudata;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.function.BinaryOperator;

public class GhiChuAnhAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    public List<GhiChuAnh> ghiChuAnhList;

    public GhiChuAnhAdapter(MainActivity context, int layout, List<GhiChuAnh> ghiChuAnhList) {
        this.context = context;
        this.layout = layout;
        this.ghiChuAnhList = ghiChuAnhList;
    }

    @Override
    public int getCount() {
        return ghiChuAnhList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class  ViewHolder{
        TextView txtTieude,txtNoidung,txtNgaynhap;
        ImageView imgUpdate,imgDelete,imgHinh;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GhiChuAnhAdapter.ViewHolder holder;
        if (convertView==null)
        {
            holder= new GhiChuAnhAdapter.ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(layout,null);
            holder.txtTieude = (TextView) convertView.findViewById(R.id.Tieude2);
            holder.txtNoidung= (TextView) convertView.findViewById(R.id.noidung2);
            holder.txtNgaynhap = (TextView) convertView.findViewById(R.id.Ngaynhap2);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.Xoa2);
            holder.imgUpdate = (ImageView) convertView.findViewById(R.id.Capnhat2);
            //holder.imgHinh = (ImageView) convertView.findViewById(R.id.imageAnh2);
            convertView.setTag(holder);
        }else {
            holder=(GhiChuAnhAdapter.ViewHolder) convertView.getTag();
        }
        GhiChuAnh ghiChuAnh = ghiChuAnhList.get(position);
        holder.txtTieude.setText(ghiChuAnh.getTieude());
        holder.txtNoidung.setText((ghiChuAnh.getNoidung()));
        holder.txtNgaynhap.setText(ghiChuAnh.getNgayghi());
        // chuyen tu byte sang bitmap
        //byte[] hinhanh = ghiChuAnh.getHinhanh();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
        //holder.imgHinh.setImageBitmap(bitmap);



        return convertView;
    }


}
