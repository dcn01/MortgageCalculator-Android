package activitylifecycle.federation.edu.au.mortgagecalculator30337898;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    double loan;
    double rate;
    double term;
    int paymentFrequency;
    EditText loanAmount;
    EditText interestRate;
    EditText loanTerm;
    RadioButton selectedRadio;
    RadioGroup frequency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loanAmount = findViewById(R.id.loanAmount);
        interestRate = findViewById(R.id.interestRate);
        loanTerm = findViewById(R.id.loanTerm);

        frequency = findViewById(R.id.frequency);



    }

    protected void launchRepayments(){
        Intent intent = new Intent(this,Repayments_Displayer.class);
        intent.putExtra("loan",loan);
        intent.putExtra("rate",rate);
        intent.putExtra("term",term);
        intent.putExtra("frequency",paymentFrequency);
        startActivity(intent);

    }

    protected void errorCheck(View v)
    {
       try{
           loan = Double.parseDouble(loanAmount.getText().toString());
           rate = Double.parseDouble(interestRate.getText().toString());
           term = Integer.parseInt(loanTerm.getText().toString());
           int id= frequency.getCheckedRadioButtonId();
           selectedRadio = (RadioButton)findViewById(id);
           if(selectedRadio.getText().toString().equalsIgnoreCase("Weekly"))
               paymentFrequency=7;
           else if(selectedRadio.getText().toString().equalsIgnoreCase("fortnightly"))
               paymentFrequency=14;
           else
               paymentFrequency=30;



           launchRepayments();

       }

       catch(Exception e1)
       {
           if(loanAmount.getText().toString().isEmpty())
           {
               Toast.makeText(getApplicationContext(),"Please enter Loan Amount.",Toast.LENGTH_LONG).show();
           }
           else if(interestRate.getText().toString().isEmpty())
           {
               Toast.makeText(getApplicationContext(),"Please enter Interest Rate.",Toast.LENGTH_LONG).show();
           }
           else if(loanTerm.getText().toString().isEmpty())
           {
               Toast.makeText(getApplicationContext(),"Please enter the Loan Term.",Toast.LENGTH_LONG).show();
           }
           else if(frequency.getCheckedRadioButtonId()==-1)
           {
               Toast.makeText(getApplicationContext(), "Please Select a frequency to proceed.", Toast.LENGTH_LONG).show();
           }
           else
           {
               Toast.makeText(getApplicationContext(), "Unknown Error: "+ e1.getLocalizedMessage(), Toast.LENGTH_LONG).show();
           }
       }
    }


}
