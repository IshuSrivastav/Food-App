package com.example.food.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Adapter.ChatAdapter;
import com.example.food.Models.Message;
import com.example.food.R;

import java.util.ArrayList;
import java.util.List;

public class SupportFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messages;
    private EditText userInput;
    private TextView welcomeText;

    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        userInput = view.findViewById(R.id.message_edit_text);
        ImageButton sendButton = view.findViewById(R.id.send_btn);
        welcomeText = view.findViewById(R.id.welcome_text);

        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = userInput.getText().toString().trim();
                if (!message.isEmpty()) {
                    // Hide welcome text when the user or bot sends a message
                    welcomeText.setVisibility(View.GONE);

                    // Add user message to the list
                    messages.add(new Message(message, Message.SENT_BY_ME));
                    chatAdapter.notifyDataSetChanged();
                    scrollToBottom();

                    // Simulate bot reply
                    String botReply = "Thank you for contacting us. You can share your problem on this email id. We will solve your problem as soon as possible.";
                    // Add bot reply to the list
                    messages.add(new Message(botReply, Message.SENT_BY_BOT));
                    chatAdapter.notifyDataSetChanged();
                    scrollToBottom();

                    // Clear the input field
                    userInput.getText().clear();
                }
            }
        });

        return view;
    }

    private void scrollToBottom() {
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }
}
