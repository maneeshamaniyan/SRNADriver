package com.ryx.srnadriver.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ryx.srnadriver.API.ApiClient;
import com.ryx.srnadriver.API.ApiInterface;
import com.ryx.srnadriver.Adapter.CurrentTripAdapter;
import com.ryx.srnadriver.Adapter.PastTripAdapter;
import com.ryx.srnadriver.Model.CurrentTripResponse;
import com.ryx.srnadriver.Model.PastItemModel;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.Util.Constatnts;
import com.ryx.srnadriver.databinding.FragmentTripBinding;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TripFragment extends Fragment {

    FragmentTripBinding binding;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    public ApiInterface api = ApiClient.getApi();
    public CompositeDisposable disposables = new CompositeDisposable();
    private final String TAG = "SrnaDriver" + this.getClass().getSimpleName();
    int driverId;
    SharedPreferences prefs;
    CurrentTripAdapter currentTripAdapter;
    PastTripAdapter pastTripAdapter;
    public TripFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs = getContext().getSharedPreferences(Constatnts.app,MODE_PRIVATE);
        binding = FragmentTripBinding.inflate(inflater, container, false);
        initView();
        initEvent();
        return binding.getRoot();
    }

    private void initEvent() {

        binding.btncurrent.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.btn_black_submit) );
        binding.btncurrent.setTextColor(Color.WHITE);
        binding.tabTitle.setText(R.string.current_trip);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Current", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btncurrent.setOnClickListener(v->{

            binding.recyclerviewSchedule.setVisibility(View.GONE);
            binding.recyclerviewPast.setVisibility(View.GONE);
            binding.btncurrent.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.btn_black_submit) );
            binding.btncurrent.setTextColor(Color.WHITE);
            binding.btnSchedule.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.black_with_boarder) );
            binding.btnSchedule.setTextColor(Color.BLACK);
            binding.btnPastt.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.black_with_boarder) );
            binding.btnPastt.setTextColor(Color.BLACK);
            binding.tabTitle.setText(R.string.current_trip);
            searchPadel("current");
        });
        binding.btnSchedule.setOnClickListener(v->{

            binding.recyclerviewCurrent.setVisibility(View.GONE);
            binding.recyclerviewPast.setVisibility(View.GONE);
            linearLayoutManager3 = new LinearLayoutManager(getContext());
            linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
            binding.recyclerviewSchedule.setHasFixedSize(true);
            binding.recyclerviewSchedule.setLayoutManager(linearLayoutManager3);

            binding.btncurrent.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.black_with_boarder) );
            binding.btncurrent.setTextColor(Color.BLACK);
            binding.btnSchedule.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.btn_black_submit) );
            binding.btnSchedule.setTextColor(Color.WHITE);
            binding.btnPastt.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.black_with_boarder));
            binding.btnPastt.setTextColor(Color.BLACK);
            binding.tabTitle.setText(R.string.schedule_trip);

        });
        binding.btnPastt.setOnClickListener(v->{


            binding.recyclerviewCurrent.setVisibility(View.GONE);
            binding.recyclerviewSchedule.setVisibility(View.GONE);


            linearLayoutManager2 = new LinearLayoutManager(getContext());
            linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
            binding.recyclerviewPast.setHasFixedSize(true);
            binding.recyclerviewPast.setLayoutManager(linearLayoutManager2);
            binding.recyclerviewPast.setVisibility(View.VISIBLE);
            binding.btncurrent.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.black_with_boarder) );
            binding.btncurrent.setTextColor(Color.BLACK);
            binding.btnSchedule.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.black_with_boarder) );
            binding.btnSchedule.setTextColor(Color.BLACK);
            binding.btnPastt.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.btn_black_submit));
            binding.btnPastt.setTextColor(Color.WHITE);
            binding.tabTitle.setText(R.string.past_trip);
            pastData();
        });
    }

    private void initView() {

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerviewCurrent.setHasFixedSize(true);
        binding.recyclerviewCurrent.setLayoutManager(linearLayoutManager);


    }

    private void searchPadel(String type) {

        if(type.equals("current"))
        {
            api.currentTripRequest(prefs.getInt(Constatnts.driverId,0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CurrentTripResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposables.add(d);
                            loadingvisible();
                        }

                        @Override
                        public void onNext(CurrentTripResponse currentTripResponse) {
                            Log.d("CurrentTripResponse",new Gson().toJson(currentTripResponse));
                            try {
                                if(currentTripResponse.getData().getTripInfo()==null)
                                {

                                    binding.emptyDataText.setVisibility(View.VISIBLE);
                                }
                                else {

                                    binding.emptyDataText.setVisibility(View.GONE);
                                    currentTripAdapter = new CurrentTripAdapter(getContext(),currentTripResponse);
                                    binding.recyclerviewCurrent.setAdapter(currentTripAdapter);
                                    loadinghidden();
                                }

                            }
                            catch (Exception e)
                            {
                                Log.d("Exception",e.getMessage());
                            }

                        }




                        @Override
                        public void onError(Throwable e) {

                            loadinghidden();
                            Log.e("Response", "onNext: " + e.getMessage());
                            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onComplete(){
                            loadinghidden();
                        }
                    });;
        }

    }


    private void scheduleData() {


        api.currentTripRequest(prefs.getInt(Constatnts.driverId,0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentTripResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                        loadingvisible();
                    }

                    @Override
                    public void onNext(CurrentTripResponse currentTripResponse) {
                        Log.d("CurrentTripResponse",new Gson().toJson(currentTripResponse));
                        try {
                            if(currentTripResponse.getData().getTripInfo()==null)
                            {

                                binding.emptyDataText.setVisibility(View.VISIBLE);
                            }
                            else {

                                binding.emptyDataText.setVisibility(View.GONE);
                                currentTripAdapter = new CurrentTripAdapter(getContext(),currentTripResponse);
                                binding.recyclerviewCurrent.setAdapter(currentTripAdapter);
                                loadinghidden();
                            }

                        }
                        catch (Exception e)
                        {
                            Log.d("Exception",e.getMessage());
                        }

                    }




                    @Override
                    public void onError(Throwable e) {

                        loadinghidden();
                        Log.e("Response", "onNext: " + e.getMessage());
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete(){
                        loadinghidden();
                    }
                });


    }


    private void pastData() {

            api.pastTripRequest(prefs.getInt(Constatnts.driverId,0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PastItemModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposables.add(d);
                           loadingvisible();
                        }

                        @Override
                        public void onNext(PastItemModel pastItemModel) {
                            Log.d("PastItemModel",new Gson().toJson(pastItemModel));
                            try {
                            /*courtAdapter = new CourtAdapter(getContext(),bookingResponse);

                            binding.rvFav.setAdapter(courtAdapter);
*/
                                if(pastItemModel.getData().getTripInfo()==null)
                                {

                                    binding.emptyDataText.setVisibility(View.VISIBLE);
                                }
                                else {

                                    binding.emptyDataText.setVisibility(View.GONE);
                                    pastTripAdapter = new PastTripAdapter(getContext(),pastItemModel);
                                    binding.recyclerviewPast.setAdapter(pastTripAdapter);
                                    loadinghidden();
                                }

                            }
                            catch (Exception e)
                            {
                                loadinghidden();
                                Log.d("Exception",e.getMessage());
                            }



                            //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                        }




                        @Override
                        public void onError(Throwable e) {

                            binding.progressbar.getRoot().setVisibility(View.GONE);
                            Log.e("Response", "onNext: " + e.getMessage());
                            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            loadinghidden();
                        }

                        @Override
                        public void onComplete(){
                           loadinghidden();
                        }
                    });;


    }


    private void loadingvisible()
    {
        binding.progressbar.getRoot().setVisibility(View.VISIBLE);
    }

    private void loadinghidden(){
        binding.progressbar.getRoot().setVisibility(View.GONE);
    }

}