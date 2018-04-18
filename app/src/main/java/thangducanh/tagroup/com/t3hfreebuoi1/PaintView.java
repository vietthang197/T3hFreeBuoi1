package thangducanh.tagroup.com.t3hfreebuoi1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

// Class custom View  là PaintView dùng để lắng nghe sự kiện vẽ ở layout vẽ, lấy các điểm mà tay người dùng vẽ ở màn hình Points
public class PaintView extends View{
    private static final int COLOR_DEFAULT = Color.BLACK; // màu mặc định là đen
    private static final float STROKE_WIDTH_DEFAULT = 16F; // size của nét vẽ mặc định

    private Paint paint; // bút vẽ
    private int color; // màu sắc , có nhiều màu nên phải là thuộc tính
    private float strokeWidth; // size của nét vẽ để thay đổi
    private Path path; // đường thằng nối 2 điểm di tay của người dùng
    private ArrayList<Path> paths; // một tập chứa list points của người dùng

    private float xTouch, yTouch; // tọa độ nơi mà người dùng chạm tay tới

    // Override 3 constructor đầu tiên, không cần thằng cuối
    public PaintView(Context context) {
        super(context);
        setup();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    // Phương thức gọi lần đầu tiên để khởi tạo các thành phần
    private void setup(){
        color = COLOR_DEFAULT;
        strokeWidth = STROKE_WIDTH_DEFAULT;

        paint = new Paint();
        paint.setColor(color); // set màu mặc định cho bút vẽ là màu đen
        paint.setAntiAlias(true); // loại bỏ răng cưa
        paint.setStyle(Paint.Style.STROKE); // hình có đường viền
        paint.setStrokeCap(Paint.Cap.ROUND); // đầu bút vẽ là hình tròn
        paint.setStrokeWidth(strokeWidth); // size của nét vẽ mặc định là 16F

        paths = new ArrayList<>();
    }

    // Bề mặt người dùng vẽ lên là canvas, lấy các điểm của người dùng đã vẽ (paths) để vẽ lên canvas
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save(); // tối ưu bộ nhớ, theo thầy bảo thế :v

        canvas.drawColor(Color.WHITE);
        for(Path path : paths){
            canvas.drawPath(path, paint); // vẽ các điểm đó lên canvas
        }

        canvas.restore();
    }

    // bắt sự kiện vẽ của người dùng
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(); // lấy tọa độ tại thời điểm hiện tại của ngón tay
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: // action khi người dùng chạm tay vào PaintView
                path = new Path();
                path.moveTo(x,y); // di chuyển path tới nơi bắt đầu vẽ
                paths.add(path); // lưu lại những điểm đã vẽ

                xTouch = x;
                yTouch = y;
                invalidate(); //cập nhật lại hình vẽ
                break;
            case MotionEvent.ACTION_MOVE: // action di chuyển trên màn hình
                float dX = Math.abs(x - xTouch); // tính khoảng cách giữa 2 điểm
                float dY = Math.abs(y - yTouch);

                if(dX >= 4F || dY >= 4F){
                    path.quadTo(xTouch,yTouch,x,y); // phương trình biến các đường gấp khúc thành đường cong -> cái này đéo biết

                    xTouch = x;
                    yTouch =y;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP: // action khi người dùng  rời tay ra khỏi PaintView
                path.lineTo(x,y);
                invalidate();
                break;
            default:
                invalidate();
                break;
        }

        return true;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
        invalidate();
    }
}
