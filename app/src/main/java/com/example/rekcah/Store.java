package com.example.rekcah;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Store extends AppCompatActivity {
    private TextView tvUserCredit;
    private int userCredit = 3000;
    private int itemPrice1 = 1500, itemPrice2 = 500, itemPrice3 = 1000, itemPrice4 = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store);

        // Initialize user credit TextView and get its value
        tvUserCredit = findViewById(R.id.TVUserCredit);
        tvUserCredit.setText(String.valueOf(userCredit));

        // Find CardViews instead of ConstraintLayouts
        CardView cvItem1 = findViewById(R.id.CVStoreItem1);
        CardView cvItem2 = findViewById(R.id.CVStoreItem2);
        CardView cvItem3 = findViewById(R.id.CVStoreItem3);
        CardView cvItem4 = findViewById(R.id.CVStoreItem4);

        cvItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showStorePopup(itemPrice1);
            }
        });

        cvItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StoreActivity", "CVStoreItem2 clicked");
                showStorePopup(itemPrice2);
            }
        });

        cvItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StoreActivity", "CVStoreItem3 clicked");
                showStorePopup(itemPrice3);
            }
        });

        cvItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StoreActivity", "CVStoreItem4 clicked");
                showStorePopup(itemPrice4);
            }
        });
    }

    private void showStorePopup(int itemPrice) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View popupView = getLayoutInflater().inflate(R.layout.storepopup, null);
            builder.setView(popupView);

            final AlertDialog dialog = builder.create();

            // Set dialog window properties
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                // This will center the dialog
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
            }

            Button btnBuy = popupView.findViewById(R.id.BtnBuy);
            Button btnCancel = popupView.findViewById(R.id.BtnCancel);
            TextView messageText = popupView.findViewById(R.id.textView);

            // Update message to show price
            messageText.setText("Do you want to exchange this item for " + itemPrice + " credits?");

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userCredit >= itemPrice) {
                        userCredit -= itemPrice;
                        tvUserCredit.setText(String.valueOf(userCredit));
                        Toast.makeText(Store.this, "Purchase successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Store.this, "Insufficient credits!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            Log.e("StoreActivity", "Error showing popup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showSimpleDialog(int itemPrice) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Purchase")
                .setMessage("Do you want to buy this item for " + itemPrice + " credits?")
                .setPositiveButton("Buy", (dialog, which) -> {
                    Toast.makeText(this, "Buy clicked", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}