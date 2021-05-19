package sk.upjs.ics.android.koncovyprojekt2;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import static sk.upjs.ics.android.koncovyprojekt2.Defaults.DISMISS_ACTION;


public class InfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ListView notesGridView;
    private TextView menoTextView;
    private TextView priezviskoTextView;
    private TextView ockovanie1;
    private TextView ockovanie2;
    private LinearLayout menoapriezvisko;



    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_info, container, false);
        menoTextView = frameLayout.findViewById(R.id.menoBuilder);
        priezviskoTextView = frameLayout.findViewById(R.id.priezviskoBuilder);
        menoTextView.setText(MainActivity.meno);
        priezviskoTextView.setText(MainActivity.priezvisko);
        ockovanie1 = frameLayout.findViewById(R.id.ockovanie1);
        ockovanie2 = frameLayout.findViewById(R.id.ockovanie2);
        menoapriezvisko = frameLayout.findViewById(R.id.menoapriezvisko);
        menoapriezvisko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
                builder.setTitle("Zmeniť");
                final View customLayout = getLayoutInflater().inflate(R.layout.zmena, null);
                builder.setView(customLayout);
                builder.create();
                final EditText menoBuilder = customLayout.findViewById(R.id.menoBuilder);
                final EditText priezviskoBuilder = customLayout.findViewById(R.id.priezviskoBuilder);
                menoBuilder.setText(MainActivity.meno);
                priezviskoBuilder.setText(MainActivity.priezvisko);
                builder.setPositiveButton("ULOŽ", (dialog, which) -> {
                    MainActivity.meno = menoBuilder.getText().toString();
                    MainActivity.priezvisko = priezviskoBuilder.getText().toString();
                    menoTextView.setText(MainActivity.meno);
                    priezviskoTextView.setText(MainActivity.priezvisko);
                });
                builder.setNegativeButton("Zrušiť", DISMISS_ACTION);
                builder.show();
            }
        });
        return frameLayout;
    }


}