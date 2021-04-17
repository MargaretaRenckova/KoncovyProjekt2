package sk.upjs.ics.android.koncovyprojekt2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ms.square.android.expandabletextview.ExpandableTextView;


public class FavoritesFragment extends Fragment {
    public FavoritesFragment() {
    }

    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        View frameLayout = inflater.inflate(R.layout.fragment_favorites, container, false);
        ImageView obrazok=(ImageView) frameLayout.findViewById(R.id.sirenie);

        ExpandableTextView textView = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view);
        textView.setText(getString(R.string.sirenie));

        ExpandableTextView textView2 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view2);
        textView2.setText(getString(R.string.priznaky));

        ExpandableTextView textView3 = (ExpandableTextView) frameLayout.findViewById(R.id.expand_tex_view3);
        textView3.setText(getString(R.string.prevencia));


        return frameLayout;
    }

}