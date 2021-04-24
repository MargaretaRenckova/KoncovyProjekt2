package sk.upjs.ics.android.koncovyprojekt2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;

public class HomeFragment extends Fragment {
    private ImageView refreshButton;
    private TextView PCRVykonane;
    private TextView PCRPlus;
    private TextView umrtiaPlus;
    private TextView umrtiaCelkom;
    private TextView AntigenVykonane;
    private TextView AntigenPlus;
    private TextView PCRCelkom;
    private TextView PDPlus;
    private TextView PDCelkom;
    private TextView DDPlus;
    private TextView DDCelkom;
    private TextView hospitalizacieCelkom;
    private TextView aktualizacia;
    private RequestQueue mQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_home, container, false);
        refreshButton = frameLayout.findViewById(R.id.refresh);

        PCRVykonane = frameLayout.findViewById(R.id.PCRVykonane);
        PCRPlus = frameLayout.findViewById(R.id.PCRPlus);
        umrtiaPlus = frameLayout.findViewById(R.id.umrtiaPlus);
        umrtiaCelkom = frameLayout.findViewById(R.id.umrtiaCelkom);
        AntigenVykonane = frameLayout.findViewById(R.id.antigenVykonane);
        AntigenPlus = frameLayout.findViewById(R.id.antigenPlus);
        PCRCelkom = frameLayout.findViewById(R.id.PCRCelkom);
        PDPlus = frameLayout.findViewById(R.id.PDPlus);
        PDCelkom = frameLayout.findViewById(R.id.PDCelkom);
        DDPlus = frameLayout.findViewById(R.id.DDPlus);
        DDCelkom = frameLayout.findViewById(R.id.DDCelkom);
        hospitalizacieCelkom = frameLayout.findViewById(R.id.hospitalizacieCelkom);
        aktualizacia = frameLayout.findViewById(R.id.aktualizacia);

        mQueue = Volley.newRequestQueue(getActivity());
        jsonParse();
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualizacia.setText("Posledná aktualizácia: ");
                jsonParse();
            }
        });
        return frameLayout;
    }

    private void jsonParse() {
        String url = "https://api.apify.com/v2/key-value-stores/GlTLAdXAuOz6bLAIO/records/LATEST";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String string1 = response.getString("newTestedPCR");
                            string1 = customFormat("###,###,###", Integer.parseInt(string1));
                            PCRVykonane.setText("");
                            PCRVykonane.append(string1);
                            String string2 = response.getString("newInfectedPCR");
                            string2 = customFormat("###,###,###", Integer.parseInt(string2));
                            PCRPlus.setText("");
                            PCRPlus.append("+" + string2);

                            String string3 = response.getString("newDeceased");
                            umrtiaPlus.setText("");
                            umrtiaPlus.append("+" + string3);
                            String string4 = response.getString("deceased");
                            string4 = customFormat("###,###,###", Integer.parseInt(string4));
                            umrtiaCelkom.setText("");
                            umrtiaCelkom.append(string4);

                            String string5 = response.getString("newInfectedAG");
                            string5 = customFormat("###,###,###", Integer.parseInt(string5));
                            AntigenPlus.setText("");
                            AntigenPlus.append("+" + string5);
                            String string6 = response.getString("newTestedAG");
                            string6 = customFormat("###,###,###", Integer.parseInt(string6));
                            AntigenVykonane.setText("");
                            AntigenVykonane.append(string6);


                            String string7 = response.getString("newVacinatedFirstDose");
                            string7 = customFormat("###,###,###", Integer.parseInt(string7));
                            PDPlus.setText("");
                            PDPlus.append("+" + string7);
                            String string8 = response.getString("newVacinatedSecondDose");
                            string8 = customFormat("###,###,###", Integer.parseInt(string8));
                            DDPlus.setText("");
                            DDPlus.append("+" + string8);

                            String string9 = response.getString("testedPCR");
                            string9 = customFormat("###,###,###", Integer.parseInt(string9));
                            PCRCelkom.setText("");
                            PCRCelkom.append(string9);

                            String string10 = response.getString("hospitalized");
                            string10 = customFormat("###,###,###", Integer.parseInt(string10));
                            hospitalizacieCelkom.setText("");
                            hospitalizacieCelkom.append(string10);

                            String string11 = response.getString("lastUpdatedAtSource");
                            string11 = string11.substring(0, 10);
                            string11 = peknyDatum(string11);
                            aktualizacia.setText("");
                            aktualizacia.setText("Posledná aktualizácia: " + string11);

                            String string12 = response.getString("vacinatedFirstDose");
                            string12 = customFormat("###,###,###", Integer.parseInt(string12));
                            PDCelkom.setText("");
                            PDCelkom.append(string12);

                            String string13 = response.getString("vacinatedSecondDose");
                            string13 = customFormat("###,###,###", Integer.parseInt(string13));
                            DDCelkom.setText("");
                            DDCelkom.append(string13);
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Chyba pri načítaní dát", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Chyba pri načítaní dát", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private String peknyDatum(String string11) {
        String[] x = new String[3];
        if (string11.charAt(8)=='0'){
            x[0] = Character.toString(string11.charAt(9));
        }
        else x[0] = string11.substring(8);
        if (string11.charAt(5)=='0'){
            x[1] = Character.toString(string11.charAt(6));
        }
        else x[1] = string11.substring(5, 7);
        x[2] = string11.substring(0, 4);

        return (x[0] + ". " + x[1] + ". " + x[2]);
    }

    static public String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return (output);
    }

}
