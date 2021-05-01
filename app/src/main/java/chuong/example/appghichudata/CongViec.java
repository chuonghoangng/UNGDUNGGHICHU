package chuong.example.appghichudata;

public class CongViec {
    private  int IdCV;
    private  String Tieude;
    private  String Noidung;
    private  String Ngayghi;

    public CongViec(int idCV, String tieude, String noidung, String ngayghi) {
        IdCV = idCV;
        Tieude = tieude;
        Noidung = noidung;
        Ngayghi = ngayghi;
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
}
