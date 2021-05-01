package chuong.example.appghichudata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;


    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
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
        ImageView imgUpdate,imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null)
        {
            holder= new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(layout,null);
            holder.txtTieude = (TextView) convertView.findViewById(R.id.Tieude);
            holder.txtNoidung= (TextView) convertView.findViewById(R.id.noidung);
            holder.txtNgaynhap = (TextView) convertView.findViewById(R.id.Ngaynhap);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.Xoa);
            holder.imgUpdate = (ImageView) convertView.findViewById(R.id.Capnhat);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        CongViec congViec =congViecList.get(position);
        holder.txtTieude.setText(congViec.getTieude());
        holder.txtNoidung.setText((congViec.getNoidung()));
        holder.txtNgaynhap.setText(congViec.getNgayghi());

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.Dialog_SuaGhiChu(congViec.getIdCV(),congViec.getTieude(),congViec.getNoidung());
            }
        });
        holder.imgDelete.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.Dialog_XoaGhiChu(congViec.getIdCV(),congViec.getTieude());

            }
        }));
        return convertView;

    }
}
