package chuong.example.appghichudata;

public class GhiChuAnh {
    private  int IdCV;
    private  String Tieude;
    private  String Noidung;
    private  String Ngayghi;
    private byte[] Hinhanh;

    public GhiChuAnh(int idCV, String tieude, String noidung, String ngayghi, byte[] hinhanh) {

        IdCV = idCV;
        Tieude = tieude;
        Noidung = noidung;
        Ngayghi = ngayghi;
        Hinhanh = hinhanh;
    }

    public int getIdCV() {
        return IdCV;
    }

    public void setIdCV(int idCV) {
        IdCV = idCV;
    }

    public String getTieude() {
        return Tieude;
    }

    public void setTieude(String tieude) {
        Tieude = tieude;
    }

    public String getNoidung() {
        return Noidung;
    }

    public void setNoidung(String noidung) {
        Noidung = noidung;
    }

    public String getNgayghi() {
        return Ngayghi;
    }

    public void setNgayghi(String ngayghi) {
        Ngayghi = ngayghi;
    }

    public byte[] getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        Hinhanh = hinhanh;
    }
}
