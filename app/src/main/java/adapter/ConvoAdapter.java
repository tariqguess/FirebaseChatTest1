package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mac.firebasechat.ChatActivity;
import com.example.mac.firebasechat.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Message;

public class ConvoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int SELF = -100;

    private Context context;
    private ArrayList<Message> messageArrayList;

    public ConvoAdapter(Context mContext, ArrayList<Message> listMessage) {
        this.context = mContext;
        this.messageArrayList = listMessage;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == SELF) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_mon_item, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_autre_item, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ViewHolder) holder).message.setText(messageArrayList.get(position).getMessage());
        ((ViewHolder) holder).timestamp.setText(getTimeStamp(messageArrayList.get(position).getDate()));

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(messageArrayList.get(position).getId_emmeteur() == "1") {
            return SELF;
        }
        return position;
    }

    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = "";

        Calendar calendar = Calendar.getInstance();
        String today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("HH:mm") : new SimpleDateFormat("dd LLL, HH:mm");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message, timestamp;
        //RoundedImageView imageView;
        RelativeLayout loadingPanel;

        public ViewHolder(View view) {
            super(view);
            message = itemView.findViewById(R.id.message);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }

}

