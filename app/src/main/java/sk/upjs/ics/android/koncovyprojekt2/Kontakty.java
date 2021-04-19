package sk.upjs.ics.android.koncovyprojekt2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;

public class Kontakty extends Fragment {
    private TextView bratislava;
    private TextView kosice;
    private TextView trnava;
    private TextView trencin;
    private TextView nitra;
    private TextView zilina;
    private TextView banskaBystrica;
    private TextView presov;

    public Kontakty() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_kontakty, container, false);
        bratislava = frameLayout.findViewById(R.id.bratislava);
        bratislava.setOnClickListener(makeCall(bratislava));
        kosice = frameLayout.findViewById(R.id.kosice);
        kosice.setOnClickListener(makeCall(kosice));
        trnava = frameLayout.findViewById(R.id.trnava);
        trnava.setOnClickListener(makeCall(trnava));
        trencin = frameLayout.findViewById(R.id.trencin);
        trencin.setOnClickListener(makeCall(trencin));
        nitra = frameLayout.findViewById(R.id.nitra);
        nitra.setOnClickListener(makeCall(nitra));
        zilina = frameLayout.findViewById(R.id.zilina);
        zilina.setOnClickListener(makeCall(zilina));
        banskaBystrica = frameLayout.findViewById(R.id.bansbystrica);
        banskaBystrica.setOnClickListener(makeCall(banskaBystrica));
        presov = frameLayout.findViewById(R.id.presov);
        presov.setOnClickListener(makeCall(presov));
        return frameLayout;
    }

    public View.OnClickListener makeCall(TextView number){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number.getText()));
                startActivity(callIntent);
            }
        };

    }


}