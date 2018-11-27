package dadm.scaffold.counter;

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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.select_A).setOnClickListener(this);
        view.findViewById(R.id.select_B).setOnClickListener(this);
        view.findViewById(R.id.select_C).setOnClickListener(this);
        view.findViewById(R.id.select_D).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.select_A) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.s_duck_a);
            ((ScaffoldActivity) getActivity()).startGame();
        }

        if (v.getId() == R.id.select_B) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.ship);
            ((ScaffoldActivity) getActivity()).startGame();
        }

        if (v.getId() == R.id.select_C) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.robot);
            ((ScaffoldActivity) getActivity()).startGame();
        }

        if (v.getId() == R.id.select_D) {
            SettingsInfo.getInstance().setDrawableRes( R.drawable.s_coffee);
            ((ScaffoldActivity) getActivity()).startGame();
        }
        }

}
