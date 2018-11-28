package dadm.scaffold.counter;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.SoundManager;
import dadm.scaffold.model.SettingsInfo;


public class SelectCharacterFragment extends BaseFragment implements View.OnClickListener {

    public SelectCharacterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_character, container, false);

        Resources r = getResources();
        rootView.setBackground(r.getDrawable(R.drawable.backmenu));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.select_A).setOnClickListener(this);
        view.findViewById(R.id.select_A).setBackgroundResource( R.drawable.select1);

        view.findViewById(R.id.select_B).setOnClickListener(this);
        view.findViewById(R.id.select_B).setBackgroundResource( R.drawable.select2);

        view.findViewById(R.id.select_C).setOnClickListener(this);
        view.findViewById(R.id.select_C).setBackgroundResource( R.drawable.select3);

        view.findViewById(R.id.select_D).setOnClickListener(this);
        view.findViewById(R.id.select_D).setBackgroundResource( R.drawable.select4);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.select_A) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.ship_a);
            ((ScaffoldActivity) getActivity()).startGame();
        }

        if (v.getId() == R.id.select_B) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.ship_b);
            ((ScaffoldActivity) getActivity()).startGame();
        }

        if (v.getId() == R.id.select_C) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.ship_c);
            ((ScaffoldActivity) getActivity()).startGame();
        }

        if (v.getId() == R.id.select_D) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.ship_d);
            ((ScaffoldActivity) getActivity()).startGame();
        }
        }

}
