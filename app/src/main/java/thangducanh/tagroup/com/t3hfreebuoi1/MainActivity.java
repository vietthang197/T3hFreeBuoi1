package thangducanh.tagroup.com.t3hfreebuoi1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEraser, btnChangeColor; // button xóa và button đổi màu vẽ
    private PaintView paintView; // nền vẽ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sử dụng layout ở folder layout-land screenOrientation = landscape

        btnEraser = findViewById(R.id.btn_eraser);
        btnChangeColor = findViewById(R.id.btn_change_color);
        paintView = findViewById(R.id.paint_view);

        btnEraser.setOnClickListener(this);
        btnChangeColor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_eraser:
                paintView.setColor(Color.WHITE);
                break;
            case R.id.btn_change_color:
                paintView.setColor(Color.RED);
                break;

                default:
                    break;

        }
    }
}
