package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int soLuong = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Phương thức được gọi khi nút + được nhấn.
     */
    public void tang(View v) {
        if (soLuong == 100){
            Toast.makeText(MainActivity.this, "Bạn không thể chọn hơn 100 cốc coffe", Toast.LENGTH_SHORT).show();
            return;
        }
        soLuong = soLuong + 1;
        hienThiSoLuong(soLuong);
    }

    /**
     * Phương thức được gọi khi nút - được nhấn.
     */
    public void giam(View v) {
        if (soLuong == 1){
            Toast.makeText(MainActivity.this, "Bạn không thể chọn ít hơn 1 cốc coffe", Toast.LENGTH_SHORT).show();
            return;
        }
        soLuong = soLuong - 1;
        hienThiSoLuong(soLuong);
    }

    /**
     * Phương thức được gọi khi nút ĐẶT HÀNG được nhấn.
     */
    public void thucHienDatHang(View v) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox suaCheckBox = (CheckBox) findViewById(R.id.sua_checkbox);
        boolean coThemSua = suaCheckBox.isChecked();

        CheckBox socolaCheckBox = (CheckBox) findViewById(R.id.socola_checkbox);
        boolean coThemSocola = socolaCheckBox.isChecked();

        int gia = tinhGia(coThemSua, coThemSocola);
        String thongBaoGia = tomTatDonHang(gia, coThemSua, coThemSocola, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Đặt hàng từ ứng dụng Just Java - " + name);
        intent.putExtra(Intent.EXTRA_TEXT, thongBaoGia);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Thực hiện tính giá dựa theo lượng đặt hàng.
     *
     * @param themSua    lựa chọn thêm sữa
     * @param themSocola lựa chọn thêm sô cô la
     * @return trả về tổng giá trị đơn hàng
     */
    private int tinhGia(boolean themSua, boolean themSocola) {
        int giaChuan = 20000;

        if (themSua) {
            giaChuan = giaChuan + 5000;
        }

        if (themSocola) {
            giaChuan = giaChuan + 10000;
        }
        return giaChuan * soLuong;
    }

    /**
     * Tạo bản tóm tắt cho đơn hàng.
     *
     * @param name       tên khách hàng
     * @param themSua    lựa chọn thêm sữa
     * @param themSocola lựa chọn thêm sô cô la
     * @param gia        giá trị đơn hàng
     * @return trả về bản tóm tắt
     */
    private String tomTatDonHang(int gia, boolean themSua, boolean themSocola, String name) {
        String thongBaoGia = getString(R.string.ten_dat_hang, name);
        thongBaoGia += "\n" + getString(R.string.chon_them_sua) + themSua;
        thongBaoGia += "\n" + getString(R.string.chon_them_socola) + themSocola;
        thongBaoGia += "\n" + getString(R.string.so_luong) + soLuong;
        thongBaoGia += "\n" + getString(R.string.tong) + NumberFormat.getCurrencyInstance().format(gia);
        thongBaoGia += "\n" + getString(R.string.cam_on);
        return thongBaoGia;
    }

    /**
     * Phương thức hiển thị giá trị của số lượng trên màn hình.
     */
    public void hienThiSoLuong(int number) {
        TextView soLuongTextView = (TextView) findViewById(R.id.so_luong_text_view);
        soLuongTextView.setText("" + number);
    }
}
