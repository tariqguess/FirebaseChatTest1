package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mac.firebasechat.ChatActivity;
import com.example.mac.firebasechat.R;

import java.util.ArrayList;

import model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder>{

    ArrayList<User> userList;
    Context context;

    public UserListAdapter(Context context, ArrayList<User> userList){

        this.userList = userList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);
        return new UserListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Log.d("UserName", userList.get(i).getName()+" name");
        myViewHolder.name.setText(userList.get(i).getName());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.cardview);
        }


    }
}
