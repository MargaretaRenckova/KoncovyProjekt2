package sk.upjs.ics.android.koncovyprojekt2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.DISMISS_ACTION;


public class SearchFragment extends Fragment {
    private EditText name;
    private EditText phone;
    private EditText dateOfBirth;
    private EditText birthNumber;
    private EditText covidPass;
    private Button buttonOdosli;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_search, container, false);
        name=frameLayout.findViewById(R.id.name);
        phone=frameLayout.findViewById(R.id.phone);
        dateOfBirth=frameLayout.findViewById(R.id.dateOfBirth);
        birthNumber =frameLayout.findViewById(R.id.birthNumber);
        covidPass=frameLayout.findViewById(R.id.covidPass);
        buttonOdosli=frameLayout.findViewById(R.id.odoslatButton);
        buttonOdosli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menoString= String.valueOf(name.getText());
                String cisloString= String.valueOf(phone.getText());
                String datumnarString= String.valueOf(dateOfBirth.getText());
                String rodcisloString=String.valueOf(birthNumber.getText());
                String covidPassString=String.valueOf(covidPass.getText());
                LayoutInflater inflanter = getLayoutInflater();
                View x = inflanter.inflate(R.layout.odoslat, null);
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
                builder.setTitle(" Skontrolujte svoje údaje ");
                final View customLayout = getLayoutInflater().inflate(R.layout.odoslat, null);
                builder.setView(customLayout);
                builder.create();

                final TextView txtname = customLayout.findViewById(R.id.menoBuilder);
                txtname.setText(menoString);
                final TextView txtphone=customLayout.findViewById(R.id.cislo);
                txtphone.setText(cisloString);
                final TextView txtDateOfBirth=customLayout.findViewById(R.id.datumnarodenia);
                txtDateOfBirth.setText(datumnarString);
                final TextView txtbirthNumber=customLayout.findViewById(R.id.rodnecislo);
                txtbirthNumber.setText(rodcisloString);
                final TextView txtcovidPass=customLayout.findViewById(R.id.pass);
                txtcovidPass.setText(covidPassString);


                builder.setPositiveButton("Odoslať", (dialog, which) -> {

                });
                builder.setNegativeButton("Zrušiť", DISMISS_ACTION);
                builder.show();

            }
        });
        return frameLayout;
    }


}