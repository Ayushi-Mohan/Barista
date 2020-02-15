package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText name = (EditText) findViewById(R.id.editText);
        String Name = name.getText().toString();
        Log.v("MainActivity" , "Name:" + Name);
        int price = calculatePrice ( hasWhippedCream , hasChocolate);
        String msg = createorderSummary(price, hasWhippedCream , hasChocolate, Name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "mohanaashi16@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + Name);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "Cannot order more than 100 cups" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "Cannot order less than 1 cup", Toast.LENGTH_SHORT).show();
            return ;
        }
        quantity = quantity - 1;
        display(quantity);
    }



    private int calculatePrice(boolean addWhippedCream , boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream == true) {
                   basePrice += 1 ;
        }

        if (addChocolate == true) {
                   basePrice += 2 ;
        }

        int price = basePrice* quantity;
        return price;
    }

    private String createorderSummary(int price, boolean addWhippedCream, boolean addChocolate , String name1) {
        String priceMessage = "Name : " + name1;
        priceMessage += "\nAdd whipped cream?" + addWhippedCream;
        priceMessage += "\nAdd chocolate?" + addChocolate;
        priceMessage += "\nQuantity:" + quantity + "\nTotal : $" + price + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    }