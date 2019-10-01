package activitylifecycle.federation.edu.au.mortgagecalculator30337898;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Repayments_Displayer extends AppCompatActivity {
    double loan;
    double years;
    double rate;
    int frequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayments__displayer);        //initializing everything needed and getting values from the bundle object
        Bundle bundle = getIntent().getExtras();
        loan = bundle.getDouble("loan");
        years = bundle.getDouble("term");
        rate = bundle.getDouble("rate") / 100;
        frequency = bundle.getInt("frequency");
        double rep = calcRepayments(loan, years, rate, frequency);
        double totalRep = rep * years * (365.25 / frequency);

        updateUI(rep, totalRep, frequency, (totalRep - loan));
    }

    protected double calcRepayments(double L, double Y, double p, double k) {
        double eq1 = Math.pow((1 + (p / 365)), (365 * Y));                  //calculating repayments, this function was made only to improve readability of the program
        double eq2 = (Math.pow((1 + (p / 365)), k) - 1);
        double eq3 = (Math.pow((1 + (p / 365)), (365 * Y)) - 1);
        double rep = L * eq1 * (eq2 / eq3);
        return rep;
    }

    protected void updateUI(double rep, double totalRep, int freq, double interest) {
        TextView textView = findViewById(R.id.textView5);
        TextView repay = findViewById(R.id.recPayment);
        TextView totalRepTextView = findViewById(R.id.totalRepayment);
        TextView interestDisplay = findViewById(R.id.interest);

        //modifying the repayment label
        if (freq == 7) {
            textView.setText("Weekly Repayments: ");
        } else if (freq == 14) {
            textView.setText("Fortnightly Repayments: ");
        } else {
            textView.setText("Monthly Repayments: ");
        }

        // setting text to all the fields
        repay.setText("AUD " + Math.floor(rep * 100) / 100);
        totalRepTextView.setText("AUD " + Math.floor(totalRep * 100) / 100);
        interestDisplay.setText("AUD " + Math.floor(interest * 100) / 100);

        //setting up list view
        ListView listView = (ListView) findViewById(R.id.listView);

        String repaymentsDisplay[] = new String[(int) years];
        for (int i = (int) years; i > 0; i--) {

            repaymentsDisplay[(int) years - i] = i + " Years is AUD " + Math.floor(amountDueByYear(i, rep) * 100) / 100;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, repaymentsDisplay);
        listView.setAdapter(adapter);
    }

    protected double amountDueByYear(int year, double repayment) {
        double amount = (loan *
                Math.pow((1 + (rate / 365)), (365 * year))) -
                (repayment *
                        ((Math.pow((1 + (rate / 365)), (365 * year)) - 1) /
                                (Math.pow((1 + (rate / 365)), (frequency)) - 1)));


        return amount;

    }


}

