package com.example.daboos.mochat.adabtor;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daboos.mochat.R;
import com.example.daboos.mochat.model.Massage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdabtor extends RecyclerView.Adapter<ChatAdabtor.ChatViedholder> {
    @BindView(R.id.email_TV)
    TextView emailTV;
    @BindView(R.id.masage_TV)
    TextView masageTV;
    private Context mcontext;
    private List<Massage> massageList;

    public ChatAdabtor(Context mcontext, List<Massage> massageList) {
        this.mcontext = mcontext;
        this.massageList = massageList;
    }

    @NonNull
    @Override
    public ChatViedholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item, parent, false);
        return new ChatViedholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViedholder holder, int position) {
        Massage massage = massageList.get(position);

        if (massage.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            holder.emailTV.setTextColor(Color.RED);
            holder.emailTV.setGravity(Gravity.END);
            holder.masageTV.setGravity(Gravity.END);


        } else {
            holder.emailTV.setTextColor(Color.BLUE);
            holder.emailTV.setGravity(Gravity.START);
            holder.masageTV.setGravity(Gravity.START);
        }


       holder.emailTV.setText(massage.getEmail());
        holder.masageTV.setText(massage.getMassage());
    }

    @Override
    public int getItemCount() {
        return massageList.size();
    }

    public class ChatViedholder extends RecyclerView.ViewHolder {
        @BindView(R.id.email_TV)
        TextView emailTV;
        @BindView(R.id.masage_TV)
        TextView masageTV;

        public ChatViedholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
