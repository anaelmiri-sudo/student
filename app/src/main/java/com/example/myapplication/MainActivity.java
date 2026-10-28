package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail;
    private RadioGroup rgClass;
    private Spinner spinnerMajor;
    private CheckBox cbNotifications, cbTerms;
    private Button btnSubmit;
    private TextView tvResult;

    private static final String PREFS_NAME = "StudentPrefs";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CLASS = "class";
    private static final String KEY_MAJOR = "major";
    private static final String KEY_NOTIF = "notif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // קישור רכיבים
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        rgClass = findViewById(R.id.rgClass);
        spinnerMajor = findViewById(R.id.spinnerMajor);
        cbNotifications = findViewById(R.id.cbNotifications);
        cbTerms = findViewById(R.id.cbTerms);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvResult = findViewById(R.id.tvResult);

        // הגדרת ערכים ל-Spinner (מגמות)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.majors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(adapter);

        // טעינת נתונים קודמים מ-SharedPreferences אם קיימים
        loadSavedData();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    // שליפת הנתונים מהמסך
                    String fName = etFirstName.getText().toString().trim();
                    String lName = etLastName.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();

                    int selectedClassId = rgClass.getCheckedRadioButtonId();
                    RadioButton selectedRb = findViewById(selectedClassId);
                    String className = selectedRb.getText().toString();

                    String major = spinnerMajor.getSelectedItem().toString();
                    boolean notif = cbNotifications.isChecked();

                    // יצירת אובייקט תלמיד
                    Student student = new Student(fName, lName, email, className, major, notif);

                    // שמירה ב-SharedPreferences (מחליף את הקודמים)
                    saveDataToPrefs(student);

                    // הצגת התוצאה ב-TextView והפיכתו לנראה
                    tvResult.setText(student.toString());
                    tvResult.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean validateData() {
        boolean isValid = true;

        String fName = etFirstName.getText().toString().trim();
        String lName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (fName.isEmpty()) {
            etFirstName.setError(getString(R.string.err_required));
            isValid = false;
        }

        if (lName.isEmpty()) {
            etLastName.setError(getString(R.string.err_required));
            isValid = false;
        }

        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.err_required));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.err_email));
            isValid = false;
        }

        if (rgClass.getCheckedRadioButtonId() == -1) {
            isValid = false;
        }

        if (!cbTerms.isChecked()) {
            cbTerms.setError(getString(R.string.err_terms));
            isValid = false;
        }

        return isValid;
    }

    private void saveDataToPrefs(Student student) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_FIRST, student.getFirstName());
        editor.putString(KEY_LAST, student.getLastName());
        editor.putString(KEY_EMAIL, student.getEmail());
        editor.putString(KEY_CLASS, student.getStudentClass());
        editor.putString(KEY_MAJOR, student.getMajor());
        editor.putBoolean(KEY_NOTIF, student.isNotifications());
        editor.apply();
    }

    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.contains(KEY_FIRST)) {
            String fName = prefs.getString(KEY_FIRST, "");
            String lName = prefs.getString(KEY_LAST, "");
            String email = prefs.getString(KEY_EMAIL, "");
            String className = prefs.getString(KEY_CLASS, "");
            String major = prefs.getString(KEY_MAJOR, "");
            boolean notif = prefs.getBoolean(KEY_NOTIF, false);

            Student student = new Student(fName, lName, email, className, major, notif);
            tvResult.setText(student.toString());
            tvResult.setVisibility(View.VISIBLE);
        }
    }
}