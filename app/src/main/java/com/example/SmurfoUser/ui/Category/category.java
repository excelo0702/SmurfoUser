package com.example.SmurfoUser.ui.Category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.onBackPressed;
import com.example.SmurfoUser.ui.home.HomeFragment;

public class category extends Fragment implements onBackPressed {


    private TextView Breaking,Krump,locking,popping,house,waacking,hip_hop,bharatnatyam;
    private TextView kathak,khatakali,kuchipudi,manipuri,mohiniyattam,odissi,sattriya;
    private TextView afro,ballet,contemporary,dance_hall,jazz,litfeet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category, container, false);
        Breaking = root.findViewById(R.id.breaking);
        Krump = root.findViewById(R.id.krump);
        locking = root.findViewById(R.id.locking);
        popping = root.findViewById(R.id.popping);

        house= root.findViewById(R.id.house);
        waacking = root.findViewById(R.id.waacking);
        hip_hop= root.findViewById(R.id.hip_hop);
        bharatnatyam = root.findViewById(R.id.bharatnatyam);
        kathak = root.findViewById(R.id.kathak);
        khatakali = root.findViewById(R.id.khatkali);
        kuchipudi = root.findViewById(R.id.kuchipudi);
        manipuri = root.findViewById(R.id.manipuri);
        mohiniyattam = root.findViewById(R.id.mohiniyattam);
        odissi = root.findViewById(R.id.odissi);
        sattriya = root.findViewById(R.id.sattriya);
        afro = root.findViewById(R.id.afro);

        ballet= root.findViewById(R.id.ballet);
        contemporary = root.findViewById(R.id.contemporary);
        dance_hall = root.findViewById(R.id.dance_hall);
        jazz = root.findViewById(R.id.jazz);
        litfeet = root.findViewById(R.id.litfeet);

        Breaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"breaking");
            }
        });

        Krump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"krump");
            }
        });

        locking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"locking");
            }
        });

        popping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"popping");
            }
        });

        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"house");
            }
        });

        waacking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"waacking");
            }
        });
        hip_hop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"hip_hop");
            }
        });
        bharatnatyam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"bharatnatyam");
            }
        });

        kathak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"kathak");
            }
        });

        khatakali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"khatakali");
            }
        });

        kuchipudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"kuchipudi");
            }
        });

        manipuri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"manipuri");
            }
        });

        mohiniyattam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"mohiniyattam");
            }
        });
        odissi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"odissi");
            }
        });
        sattriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"sattriya");
            }
        });
        afro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"afro");
            }
        });
        ballet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"ballet");
            }
        });
        contemporary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"contemporary");
            }
        });
        dance_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"dance_hall");
            }
        });
        jazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"jazz");
            }
        });
        litfeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new category_view(),"litfeet");
            }
        });

        return root;
    }

    private void setFragment(Fragment fragment, String value) {
        Bundle bundle = new Bundle();
        bundle.putString("category",value);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout,fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        View BottomnavBar = getActivity().findViewById(R.id.bottom_navigation);
        BottomnavBar.setVisibility(View.VISIBLE);
        setFragment(new HomeFragment(),"");
    }

}