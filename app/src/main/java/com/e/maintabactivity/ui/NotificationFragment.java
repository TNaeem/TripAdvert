package com.e.maintabactivity.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.R;
import com.e.maintabactivity.adapters.NotificationAdapter;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.NotificationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {
    private static final String TAG = "NotificationFragment";

    private RecyclerView mRecyclerView;
    private List<NotificationModel> allNotifications;
    private MaterialTextView noNotification;
    private Context context = getContext();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            //Checking app registration
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task .isSuccessful()){
                    Log.i(TAG, "Task Failed");
                    return;
                }
                Log.i(TAG, "onComplete: " + task.getResult().getToken());
            }
        });

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        noNotification = view.findViewById(R.id.no_notification_message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView = view.findViewById(R.id.fragment_notification_recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new NotificationAdapter(context, new ArrayList<NotificationModel>()));
        return view;
    }

    public void getAllNotifications(int userId){

        UserApiInterface userApiInterface = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);
        userApiInterface.getAllNotifications(userId).enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                if(response.body() != null && response.body().size() > 0){
                    noNotification.setVisibility(View.GONE);
                    allNotifications = response.body();
                    mRecyclerView.setAdapter(new NotificationAdapter(context, allNotifications));
                }
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
            }
        });

    }
}
