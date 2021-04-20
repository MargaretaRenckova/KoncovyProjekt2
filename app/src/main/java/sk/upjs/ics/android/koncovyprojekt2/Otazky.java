package sk.upjs.ics.android.koncovyprojekt2;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ms.square.android.expandabletextview.ExpandableTextView;


public class Otazky extends Fragment {
    public Otazky() {
    }
    
    public static Otazky newInstance(String param1, String param2) {
        Otazky fragment = new Otazky();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_otazky, container, false);
       // ImageView obrazok=(ImageView) frameLayout.findViewById(R.id.skuska);

        ExpandableTextView textView = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view);
        textView.setText(getString(R.string.IzolaciaTrva));

        ExpandableTextView textView2 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view2);
        textView2.setText(getString(R.string.UzkyKontakt));

        ExpandableTextView textView3 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view3);
        textView3.setText(getString(R.string.BeznyKontakt));

        ExpandableTextView textView4 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view4);
        textView4.setText(getString(R.string.SomPozitivny));

        ExpandableTextView textView5 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view5);
        textView5.setText(getString(R.string.UkoncenieKaranteny));

        ExpandableTextView textView6 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view6);
        textView6.setText(getString(R.string.RizikoNakazenia));

        ExpandableTextView textView7 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view7);
        textView7.setText(getString(R.string.ockovanie));

        ExpandableTextView textView8 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view8);
        textView8.setText(getString(R.string.covidautomat));


        return frameLayout;
    }

}