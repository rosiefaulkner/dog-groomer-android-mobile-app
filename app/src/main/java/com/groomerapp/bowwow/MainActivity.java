package com.groomerapp.bowwow;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    private EditText weightValInput;

    private Handler handler = new Handler();
    private final long debounceDelay = 500;

    private Runnable workRunnable = new Runnable() {
        @Override
        public void run() {
            performAction();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Order button is disabled until order total > 0
        Button orderBtn = findViewById(R.id.order);
        orderBtn.setEnabled(false);

        // Button listeners
        findViewById(R.id.calculate).setOnClickListener(v -> onCalculateClick());
        orderBtn.setOnClickListener(v -> onOrderClick());
        findViewById(R.id.reset).setOnClickListener(v -> onResetClick());

        // Checkbox listeners
        findViewById(R.id.fleaBath).setOnClickListener(v -> onAddOnCheck());
        findViewById(R.id.nailTrim).setOnClickListener(v -> onAddOnCheck());
        findViewById(R.id.massage).setOnClickListener(v -> onAddOnCheck());

        // TextWatcher for adding debounce on weight input change
        weightValInput = findViewById(R.id.weight);

        weightValInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(workRunnable);
                handler.postDelayed(workRunnable, debounceDelay);
            }
        });
    }

    private Float calculateTotal() {
        if (!this.isWeightValidOnInputChange()) {
            Toast.makeText(this, "Please input a weight", Toast.LENGTH_SHORT).show();
            return (float) 0;
        }
        float inputWeightFloat = Float.parseFloat(weightValInput.getText().toString());
        float total = this.getNailTrimCost() + this.getFleaBathCost() + this.getMassageCost();
        if (inputWeightFloat < 30) {
            total += (float) 35;
        } else if (inputWeightFloat < 50) {
            total += (float) 45;
        } else {
            total += (float) 55;
        }

        return total;
    }

    private int getNailTrimCost() {
        CheckBox trimNails = findViewById(R.id.nailTrim);
        return trimNails.isChecked() ? 5 : 0;
    }

    private int getFleaBathCost() {
        CheckBox fleaBath = findViewById(R.id.fleaBath);
        return fleaBath.isChecked() ? 10 : 0;
    }

    private int getMassageCost() {
        CheckBox massage = findViewById(R.id.massage);
        return massage.isChecked() ? 20 : 0;
    }

    private void resetCheckBoxes() {
        CheckBox trimNails = findViewById(R.id.nailTrim);
        trimNails.setChecked(false);
        CheckBox fleaBath = findViewById(R.id.fleaBath);
        fleaBath.setChecked(false);
        CheckBox massage = findViewById(R.id.massage);
        massage.setChecked(false);
    }

    private void resetTotal() {
        TextView totalText = findViewById(R.id.total);
        totalText.setText("");
    }

    private void resetWeightInput() {
        EditText inputWeight = findViewById(R.id.weight);
        inputWeight.getText().clear();
    }

    private void displayTotal() {
        // Display total
        TextView totalText = findViewById(R.id.total);
        float total = this.calculateTotal();
        totalText.setText(MessageFormat.format("{0}${1}.00", getString(R.string.total_due), total));
        this.setOrderBtnStateOnInputChange();
    }

    private void onCalculateClick() {
        this.displayTotal();
    }

    private void onOrderClick() {
        if (this.calculateTotalOnInputChange() < 1) {
            return;
        }
        Toast.makeText(this, "Your order has been placed", Toast.LENGTH_SHORT).show();
        this.onResetClick();
    }

    private void onResetClick() {
        // Reset weight input
        this.resetWeightInput();

        // Reset checkboxes
        this.resetCheckBoxes();

        // Reset total
        this.resetTotal();

        // Reset order button state
        this.setOrderBtnStateOnInputChange();
    }

    private void onAddOnCheck() {
        this.onCalculateClick();
    }

    private boolean isWeightValidOnInputChange() {
        try {
            float inputWeightFloat = Float.parseFloat(weightValInput.getText().toString());
            if (weightValInput.getText().toString().startsWith("0")) {
                weightValInput.setText(MessageFormat.format("{0}", inputWeightFloat));
            }
            weightValInput.setSelection(weightValInput.getText().length());
            return inputWeightFloat > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Float calculateTotalOnInputChange() {
        if (!isWeightValidOnInputChange()) {
            return (float) 0;
        }
        float inputWeightFloat = Float.parseFloat(weightValInput.getText().toString());
        float total = getNailTrimCost() + getFleaBathCost() + getMassageCost();
        if (inputWeightFloat < 30) {
            total += 35;
        } else if (inputWeightFloat < 50) {
            total += 45;
        } else {
            total += 55;
        }

        return total;
    }

    private void displayTotalOnInputChange() {
        // Display total
        TextView totalText = findViewById(R.id.total);
        float total = this.calculateTotalOnInputChange();
        totalText.setText(MessageFormat.format("{0}${1}.00", getString(R.string.total_due), total));
    }

    private void setOrderBtnStateOnInputChange() {
        // set disabled if weight is invalid
        findViewById(R.id.order).setEnabled(isWeightValidOnInputChange());
    }

    private void performAction() {
        this.displayTotalOnInputChange();
        this.setOrderBtnStateOnInputChange();
    }
}