package com.example.gp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    Button btnCircle, btnRectangle, btnSquare, btnLine, btnOval, btnTriangle, btnStar, btnVoice;
    Spinner colorSpinner;
    View shapeView;

    String selectedColor = "#4A90E2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCircle = findViewById(R.id.btnCircle);
        btnSquare = findViewById(R.id.btnSquare);
        btnRectangle = findViewById(R.id.btnRectangle);
        btnLine = findViewById(R.id.btnLine);
        btnOval = findViewById(R.id.btnOval);
        btnTriangle = findViewById(R.id.btnTriangle);
        btnStar = findViewById(R.id.btnStar);
        btnVoice = findViewById(R.id.btnVoice);

        colorSpinner = findViewById(R.id.colorSpinner);
        shapeView = findViewById(R.id.shapeView);

        // Dropdown Colors
        String[] colors = {"Blue", "Green", "Orange", "Pink", "Purple", "Teal", "Red"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, colors);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        colorSpinner.setAdapter(adapter);

        colorSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: selectedColor = "#4A90E2"; break;
                    case 1: selectedColor = "#50C878"; break;
                    case 2: selectedColor = "#F5A623"; break;
                    case 3: selectedColor = "#E94E77"; break;
                    case 4: selectedColor = "#BD10E0"; break;
                    case 5: selectedColor = "#9013FE"; break;
                    case 6: selectedColor = "#D0021B"; break;
                }
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // Button Clicks
        btnCircle.setOnClickListener(v -> drawShape("circle"));
        btnSquare.setOnClickListener(v -> drawShape("square"));
        btnRectangle.setOnClickListener(v -> drawShape("rectangle"));
        btnLine.setOnClickListener(v -> drawShape("line"));
        btnOval.setOnClickListener(v -> drawShape("oval"));
        btnTriangle.setOnClickListener(v -> drawShape("triangle"));
        btnStar.setOnClickListener(v -> drawShape("star"));

        btnVoice.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
            } else {
                startVoiceRecognition();
            }
        });
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a shape name");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not supported on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0).toLowerCase();
                handleVoiceCommand(spokenText);
            }
        }
    }

    private void handleVoiceCommand(String command) {
        if (command.contains("circle")) {
            drawShape("circle");
        } else if (command.contains("square")) {
            drawShape("square");
        } else if (command.contains("rectangle")) {
            drawShape("rectangle");
        } else if (command.contains("line")) {
            drawShape("line");
        } else if (command.contains("oval")) {
            drawShape("oval");
        } else if (command.contains("triangle")) {
            drawShape("triangle");
        } else if (command.contains("star")) {
            drawShape("star");
        } else {
            Toast.makeText(this, "Shape not recognized: " + command, Toast.LENGTH_SHORT).show();
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void drawShape(String type) {
        Drawable backgroundDrawable = null;

        // Reset size to default before drawing a new shape
        shapeView.getLayoutParams().width = dpToPx(160);
        shapeView.getLayoutParams().height = dpToPx(160);

        switch (type) {
            case "circle": {
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(Color.parseColor(selectedColor));
                shapeView.getLayoutParams().width = dpToPx(150);
                shapeView.getLayoutParams().height = dpToPx(150);
                backgroundDrawable = shape;
                break;
            }
            case "oval": {
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(Color.parseColor(selectedColor));
                shapeView.getLayoutParams().width = dpToPx(160);
                shapeView.getLayoutParams().height = dpToPx(100);
                backgroundDrawable = shape;
                break;
            }
            case "square": {
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setColor(Color.parseColor(selectedColor));
                shapeView.getLayoutParams().width = dpToPx(150);
                shapeView.getLayoutParams().height = dpToPx(150);
                backgroundDrawable = shape;
                break;
            }
            case "rectangle": {
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setCornerRadius(dpToPx(16));
                shape.setColor(Color.parseColor(selectedColor));
                shapeView.getLayoutParams().width = dpToPx(170);
                shapeView.getLayoutParams().height = dpToPx(120);
                backgroundDrawable = shape;
                break;
            }
            case "line": {
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setColor(Color.parseColor(selectedColor));
                shapeView.getLayoutParams().width = dpToPx(8);
                shapeView.getLayoutParams().height = dpToPx(220);
                backgroundDrawable = shape;
                break;
            }
            case "triangle":
                shapeView.getLayoutParams().width = dpToPx(120);
                shapeView.getLayoutParams().height = dpToPx(120);
                backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.triangle_shape);
                break;
            case "star":
                shapeView.getLayoutParams().width = dpToPx(150);
                shapeView.getLayoutParams().height = dpToPx(150);
                backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.star_shape);
                break;
        }

        if (backgroundDrawable != null) {
            // Tint the drawable with the selected color
            Drawable wrappedDrawable = DrawableCompat.wrap(backgroundDrawable.mutate());
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(selectedColor));
            shapeView.setBackground(wrappedDrawable);
            shapeView.requestLayout();
            shapeView.setVisibility(View.VISIBLE);

            ScaleAnimation scale = new ScaleAnimation(0.6f, 1f, 0.6f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
            scale.setDuration(250);
            shapeView.startAnimation(scale);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecognition();
            } else {
                Toast.makeText(this, "Permission denied. To use voice commands, please enable the microphone permission in settings.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
