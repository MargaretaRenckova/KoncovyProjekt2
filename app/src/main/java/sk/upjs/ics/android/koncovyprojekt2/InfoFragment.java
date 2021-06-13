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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sk.upjs.ics.android.koncovyprojekt2.Defaults.DISMISS_ACTION;
import static sk.upjs.ics.android.koncovyprojekt2.MainActivity.*;


public class InfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView menoTextView;
    private TextView priezviskoTextView;
    private TextView ockovanie1;
    private TextView ockovanie2;
    private LinearLayout menoapriezvisko;
    private ListView testyGridView;
    private SimpleCursorAdapter adapter;


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
        menoTextView.setText(meno);
        priezviskoTextView.setText(priezvisko);
        ockovanie1 = frameLayout.findViewById(R.id.ockovanie1);
        ockovanie2 = frameLayout.findViewById(R.id.ockovanie2);
        if (isPrvadavka) {
            ockovanie1.setText("- Dátum 1. dávky: " + datumPrvadavka);
            ockovanie2.setText("  Vakcína: " + firma);
            ockovanie1.setVisibility(View.VISIBLE);
            ockovanie2.setVisibility(View.VISIBLE);
            if (isDruhadavka) {
                ockovanie2.setText("- Dátum 2. dávky: " + datumPrvadavka + "\n  Vakcína: " + firma);
            }
        }
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
                menoBuilder.setText(meno);
                priezviskoBuilder.setText(priezvisko);
                builder.setPositiveButton("ULOŽ", (dialog, which) -> {
                    meno = menoBuilder.getText().toString();
                    priezvisko = priezviskoBuilder.getText().toString();
                    menoTextView.setText(meno);
                    priezviskoTextView.setText(priezvisko);
                });
                builder.setNegativeButton("Zrušiť", DISMISS_ACTION);
                builder.show();
            }
        });
        testyGridView = frameLayout.findViewById(R.id.testyGridView);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i = 0; i < testy.size(); i++) {
            Map<String, String> datum = new HashMap<String, String>(3);
            datum.put("FL", testy.get(i));
            datum.put("SL", testyDetail.get(i).split(",")[0]);
            datum.put("TL", testyDetail.get(i).split(",")[1]);
            data.add(datum);
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.test_layout, R.id.itemTypTestu, testy);
        SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), data,
                R.layout.test_layout,
                new String[] {"FL", "SL", "TL" },
                new int[] {R.id.itemTypTestu, R.id.itemVysledokTestu, R.id.itemDatumTestu });
        testyGridView.setAdapter(adapter);
        return frameLayout;
    }


}