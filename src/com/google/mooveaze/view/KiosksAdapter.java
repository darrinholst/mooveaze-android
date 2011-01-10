package com.google.mooveaze.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.model.Kiosk;

import java.text.NumberFormat;
import java.util.List;

public class KiosksAdapter extends ArrayAdapter<Kiosk> {
    private List<Kiosk> kiosks;

    public KiosksAdapter(Context context, List<Kiosk> kiosks) {
        super(context, 0, kiosks);
        this.kiosks = kiosks;
    }

    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.kiosk, null);
        }

        Kiosk kiosk = kiosks.get(position);

        if(kiosk != null) {
            setText(view, R.id.kiosk_name, kiosk.getVendor());
            setText(view, R.id.kiosk_address, kiosk.getAddress());
            setText(view, R.id.kiosk_city, kiosk.getCity() + ", " + kiosk.getState());

            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(1);
            String distance = format.format(kiosk.getDistance());
            setText(view, R.id.kiosk_distance, distance);
        }

        return view;
    }

    private void setText(View view, int id, String text) {
        TextView title = (TextView) view.findViewById(id);
        title.setText(text);
    }
}
