/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
 package com.example.ashmita.happybirthday;


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

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocoCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        EditText nameText = (EditText)findViewById(R.id.name_edit_text);
        boolean hasWhippedCream = checkBox.isChecked();
        boolean hasChocolate = chocoCheckBox.isChecked();
        String name = nameText.getText().toString();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java Order For "+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

    int calculatePrice(boolean whippedCream, boolean chocolate)
    {
        int price = 5;
        if(whippedCream==true)
            price += 1;
        if(chocolate ==true)
            price +=2;
        return quantity*price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    String createOrderSummary(int price, boolean hasWhippedCream,boolean hasChocolate, String name)
    {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + hasWhippedCream;
        priceMessage +="\nAdd Chocolate? " + hasChocolate;

        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: ₹ " + price;
        priceMessage += "\nThank You!";
        return priceMessage;
    }

    /**
     * This method displays the given price on the screen.
     */

    public void increment(View view){
        if(quantity==100)
        {
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_LONG).show();
        }
        else
            quantity++;
        display(quantity);
    }

    public void decrement(View view){
        if(quantity==1)
        {
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_LONG).show();

        }
        else
            quantity--;
        display(quantity);
    }
}