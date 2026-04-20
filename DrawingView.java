package com.example.gp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingView extends View {

    public enum Shape {
        CIRCLE,
        RECTANGLE,
        SQUARE,
        LINE
    }

    private static class DrawnShape {
        Shape shape;
        Paint paint;
        float x1, y1, x2, y2;

        DrawnShape(Shape shape, Paint paint, float x1, float y1, float x2, float y2) {
            this.shape = shape;
            this.paint = paint;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        void draw(Canvas canvas) {
            switch (shape) {
                case CIRCLE:
                    float radius = (float) (Math.hypot(x2 - x1, y2 - y1) / 2);
                    float centerX = (x1 + x2) / 2;
                    float centerY = (y1 + y2) / 2;
                    canvas.drawCircle(centerX, centerY, radius, paint);
                    break;
                case RECTANGLE:
                    canvas.drawRect(x1, y1, x2, y2, paint);
                    break;
                case SQUARE:
                    float side = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
                    canvas.drawRect(x1, y1, x1 + side, y1 + side, paint);
                    break;
                case LINE:
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10f);
                    canvas.drawLine(x1, y1, x2, y2, paint);
                    break;
            }
        }
    }

    private final List<DrawnShape> drawnShapes = new ArrayList<>();
    private final Random random = new Random();

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void addShape(Shape shape) {
        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return;
        }

        float x1 = random.nextFloat() * width;
        float y1 = random.nextFloat() * height;
        float x2 = random.nextFloat() * width;
        float y2 = random.nextFloat() * height;

        Paint paint = new Paint();
        paint.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        paint.setStyle(Paint.Style.FILL);

        drawnShapes.add(new DrawnShape(shape, paint, x1, y1, x2, y2));
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (DrawnShape shape : drawnShapes) {
            shape.draw(canvas);
        }
    }
}
