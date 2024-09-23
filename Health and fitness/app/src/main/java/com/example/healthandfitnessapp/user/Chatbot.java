package com.example.healthandfitnessapp.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.database.QADao;
import com.example.healthandfitnessapp.database.QAModel;

import java.util.List;

public class Chatbot extends AppCompatActivity {


    private QADao qaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        ImageButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this::onSendButtonClicked);

        qaDao = new QADao(Chatbot.this);

        // Insert sample data
        qaDao.insertQA("Hello", "Hi there!");
        qaDao.insertQA("Bye,Bie", "See you later! If you ever need advice or just want to chat, I'll be here. Stay fit and happy, goodbye!");
        qaDao.insertQA("How are you?", "I'm fine, thank you!");
        qaDao.insertQA("Hi", "Hi there!");
        qaDao.insertQA("What is Your Name ", "I am a simple chatbot!!");
        qaDao.insertQA("How can I lose weight?", "Try exercises like walking, running, and eating more veggies. It's about moving more and eating healthier.");
        qaDao.insertQA("How much sleep should I get every night?", "Adults should aim for 7-9 hours of sleep each night for feeling good and staying healthy.");
        qaDao.insertQA("What should I eat to get stronger muscles?", " Eat foods like chicken, brown rice, fruits, and nuts for stronger muscles.");
        qaDao.insertQA("How do I become more flexible? ", "Stretch regularly, and you can also try activities like yoga or Pilates.");
        qaDao.insertQA("Why is it important to drink water during workouts?", " Drinking water helps you perform better, stay cool, and get the nutrients your body needs during exercise.");
        qaDao.insertQA("How often should I do strength exercises?", "Try to do strength exercises two times a week, giving your body time to rest in between.");
        qaDao.insertQA("Can exercise help reduce stress?", "Yes, exercises like walking or yoga can really help you relax and feel less stressed.");
        qaDao.insertQA("Why do I need to warm up before exercising? ", "Warming up helps your body get ready for exercise, reducing the chance of getting hurt.");
        qaDao.insertQA("What should I choose when eating out to stay healthy?", "Go for grilled options, smaller portions, and add veggies. Watch out for too much sugar and fat.");
        qaDao.insertQA("How can I manage my weight better?", " Eat balanced meals, exercise regularly, and make small changes you can keep up with over time.");
        qaDao.insertQA("What are easy exercises I can do at home?", "Try activities like jumping jacks, squats, and push-ups. No fancy equipment needed!");
        qaDao.insertQA("How can I boost my energy levels naturally?", "Eat snacks like nuts and fruits, and stay hydrated. Also, make sure to get enough sleep.");
        qaDao.insertQA("Can you suggest a quick and healthy breakfast recipe?", "Sure! Try a yogurt parfait with granola and fresh berries. It's delicious and packed with nutrients.");
        qaDao.insertQA(" What's a good snack option for weight loss?", "Snack on veggies with hummus or a small handful of nuts. They're tasty and satisfying.");
        qaDao.insertQA("How do I stay motivated to exercise regularly?", "Find activities you enjoy, set achievable goals, and invite a friend to join you. It's more fun together!");
        qaDao.insertQA("What's a simple recipe for a nutritious lunch?", "Make a colorful salad with mixed greens, grilled chicken, and your favorite veggies. Top it with olive oil dressing.");
        qaDao.insertQA("Can you recommend a healthy dessert option?", "Absolutely! Try frozen banana slices dipped in dark chocolate. It's a sweet treat with a healthier twist.");
        qaDao.insertQA(" How much water should I drink in a day? ", "Aim for about 8 cups (64 ounces) of water a day. Carry a reusable water bottle to make it easier.");
        qaDao.insertQA("What's a good exercise for improving posture?", " Practice yoga or do exercises that strengthen your core, like planks or bridges.");



    }

    public void onSendButtonClicked(View view) {
        EditText inputEditText = findViewById(R.id.inputEditText);
        String userInput = inputEditText.getText().toString().trim().toLowerCase();
        String chatbotResponse = getResponse(userInput);

        // Add user message
        addUserMessage(userInput);

        // Add chatbot message
        addChatbotMessage(chatbotResponse);

        // Clear the input field
        inputEditText.setText("");
    }


    private void addUserMessage(String message) {
        LinearLayout chatContainer = findViewById(R.id.chatContainer);
        CardView userCard = createUserCard(message);
        chatContainer.addView(userCard);
    }

    private void addChatbotMessage(String message) {
        LinearLayout chatContainer = findViewById(R.id.chatContainer);
        CardView chatbotCard = createChatbotCard(message);
        chatContainer.addView(chatbotCard);
    }

    private CardView createUserCard(String message) {
        CardView cardView = new CardView(this);
        cardView.setLayoutParams(createUserCardLayoutParams());
        cardView.setCardBackgroundColor(getResources().getColor(R.color.black));
        cardView.setRadius(16);
        cardView.setCardElevation(8);

        TextView labelTextView = createLabelTextView("User:");
        TextView textView = createMessageTextView(message);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(labelTextView);
        linearLayout.addView(textView);

        cardView.addView(linearLayout);

        return cardView;
    }

    private CardView createChatbotCard(String message) {
        CardView cardView = new CardView(this);
        cardView.setLayoutParams(createChatbotCardLayoutParams());
        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));
        cardView.setRadius(16);
        cardView.setCardElevation(8);

        TextView labelTextView = createLabelTextView("Bot:");
        TextView textView = createMessageTextView(message);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(labelTextView);
        linearLayout.addView(textView);

        cardView.addView(linearLayout);

        return cardView;
    }

    private TextView createLabelTextView(String label) {
        TextView textView = new TextView(this);
        textView.setText(label);
        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        textView.setTextSize(18);
        textView.setPadding(16, 0, 16, 4);
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.marcellus));
        return textView;
    }

    private TextView createMessageTextView(String message) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(20);
        textView.setPadding(16, 4, 16, 16);
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.marcellus));
        return textView;
    }

    private LinearLayout.LayoutParams createUserCardLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.85),
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(30, 8, 0, 8);
        return layoutParams;
    }

    private LinearLayout.LayoutParams createChatbotCardLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.85),
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 8, 30, 8);
        return layoutParams;
    }

    private String getResponse(String userQuery) {
        List<QAModel> qaList = qaDao.getAllQA();
        for (QAModel qaModel : qaList) {
            String question = qaModel.getQuestion().toLowerCase();
            if (userQuery.contains(question) || question.contains(userQuery)) {
                return qaModel.getAnswer();
            }
        }
        return "I'm a simple chatbot. Ask me anything!";
    }
}
