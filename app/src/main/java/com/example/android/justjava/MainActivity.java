package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price=calculatePrice();
        boolean cream=hasWhippedCream();
        boolean choco=hasChocolate();

        EditText text=(EditText) findViewById(R.id.name);
        String value=text.getText().toString();
        String priceMessage=createOrderSummary(price,cream,choco,value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for "+value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method increments order.
     */
    public void increment(View view) {
        if (quantity==100) {
            return;
        }
        quantity=quantity+ 1;
        display(quantity);
    }

    /**
     * This method increments order.
     */
    public void decrement(View view) {
        if (quantity==1) {
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *
     * @return price of cups of coffee ordered
     */
    private int calculatePrice() {
        int price = quantity * 5;
        if(hasWhippedCream()) {
            price = price + 1;
        }
        if (hasChocolate()) {
            price = price + 2;
        }
        return price;
    }

    private boolean hasWhippedCream() {
        CheckBox cream= (CheckBox) findViewById(R.id.whippedcream);
        return cream.isChecked();

    }

    private boolean hasChocolate() {
        CheckBox chocolate= (CheckBox) findViewById(R.id.chocolate);
        return chocolate.isChecked();

    }

    /**
     * creates summary of order
     *
     * @return summary of order
     */
    private String createOrderSummary(int price, boolean cream, boolean chocolate, String value) {
        return "Name: "+value+
                "\nAdd whipped cream? "+cream+
                "\nAdd chocolate? "+chocolate+
                "\nQuantity: "+quantity+
                "\nTotal: "+price+"\nThank you!";
    }

}