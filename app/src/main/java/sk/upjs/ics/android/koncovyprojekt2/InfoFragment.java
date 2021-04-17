package sk.upjs.ics.android.koncovyprojekt2;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import sk.upjs.ics.android.koncovyprojekt2.provider.NoteContentProvider;
import sk.upjs.ics.android.koncovyprojekt2.provider.Provider;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_COOKIE;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_CURSOR;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_FLAGS;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_SELECTION;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_SELECTION_ARGS;


public class InfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TEST_LOADER_ID = 0;
    private static final int DELETE_TEST_TOKEN = 0;
    private static final int UPDATE_TEST_TOKEN = 0;
    private String mParam1;
    private String mParam2;
    private SimpleCursorAdapter adapter;
    private ListView notesGridView;

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
        notesGridView = (ListView) frameLayout.findViewById(R.id.notesGridView);
        notesGridView.setAdapter(initializeAdapter());
        notesGridView.setOnItemClickListener(this);
        return frameLayout;
    }

    private ListAdapter initializeAdapter() {
        String[] from = { Provider.Test.TEST_TYPE };
        int[] to = { R.id.notesGridView };
        this.adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.test_layout, NO_CURSOR, from, to, NO_FLAGS);
        return this.adapter;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader loader = new CursorLoader(this.getActivity());
        loader.setUri(NoteContentProvider.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        this.adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        this.adapter.swapCursor(NO_CURSOR);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor  selectedTestCursor = (Cursor) parent.getItemAtPosition(position);
        int descriptionColumnIndex = selectedTestCursor.getColumnIndex(Provider.Test.TEST_TYPE);
        String aktualnyTyp=selectedTestCursor.getString(descriptionColumnIndex);
        String aktualnaDlzka=selectedTestCursor.getString(descriptionColumnIndex+1);
        String aktualnyDatum=selectedTestCursor.getString(descriptionColumnIndex+2);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Test");
        final View customLayout=getLayoutInflater().inflate(R.layout.dialog,null);
        builder.setView(customLayout);
        builder.create();

        final RadioGroup tests = customLayout.findViewById(R.id.tests);
        final RadioGroup days  = customLayout.findViewById(R.id.days);
        final CalendarView calendar= customLayout.findViewById(R.id.calendar);

        switch (aktualnyTyp){
            case "antigen" : tests.check(R.id.Anti_test); break;
            case "PCR" : tests.check(R.id.PCR_test);
        }
        switch (aktualnaDlzka){
            case "7": days.check(R.id.day7); break;
            case "14": days.check(R.id.day14); break;
            case "21": days.check(R.id.day21); break;
        }
        //calendar.setDate(Long.parseLong(aktualnyDatum));

    }

    private void deleteNote(long id,boolean delete) {
        Uri selectedNoteUri = ContentUris.withAppendedId(NoteContentProvider.CONTENT_URI, id);

        AsyncQueryHandler deleteHandler = new AsyncQueryHandler(getActivity().getContentResolver()) {
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result)
            { if (delete){
                Toast.makeText(getActivity(), "Test bol odstránený", Toast.LENGTH_SHORT).show();}
            }
        };
        deleteHandler.startDelete(DELETE_TEST_TOKEN, NO_COOKIE, selectedNoteUri, NO_SELECTION, NO_SELECTION_ARGS);
    }


}