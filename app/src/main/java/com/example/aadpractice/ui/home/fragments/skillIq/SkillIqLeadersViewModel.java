package com.example.aadpractice.ui.home.fragments.skillIq;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aadpractice.Api.ApiClient;
import com.example.aadpractice.model.Leader;
import com.example.aadpractice.ui.home.activity.HomeViewModel;
import com.example.aadpractice.utils.Consts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SkillIqLeadersViewModel extends ViewModel {
    private static final String TAG = HomeViewModel.class.getSimpleName();
    MutableLiveData mListResponseMutableLiveData = new MutableLiveData<>();

    public void getList(){
        ApiClient.getINSTANCE(Consts.GET_LEADERS_BASE_URL).getSkillIqLeaders(
        ).enqueue(new Callback<List<Leader>>() {
            @Override
            public void onResponse(@NonNull Call<List<Leader>> call, @NonNull Response<List<Leader>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mListResponseMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Leader>> call,@NonNull Throwable t) {
                mListResponseMutableLiveData.setValue(null);
            }
        });
    }
}

