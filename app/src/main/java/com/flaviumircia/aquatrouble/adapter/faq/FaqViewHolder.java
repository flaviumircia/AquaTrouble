package com.flaviumircia.aquatrouble.adapter.faq;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class FaqViewHolder extends RecyclerView.ViewHolder {
    private TextView question_title,content;
    private ImageButton see_more;
    private boolean state;
    public FaqViewHolder(@NonNull View itemView) {
        super(itemView);
        question_title=itemView.findViewById(R.id.dropdown_title);
        content=itemView.findViewById(R.id.content_faq);
        see_more=itemView.findViewById(R.id.show_more_button);
        state=true;

    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public TextView getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(TextView question_title) {
        this.question_title = question_title;
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }

    public ImageButton getSee_more() {
        return see_more;
    }

    public void setSee_more(ImageButton see_more) {
        this.see_more = see_more;
    }


}
