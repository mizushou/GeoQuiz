package com.example.shouhei.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

  private static final String TAG = "CheatActivity";
  private static final String EXTRA_ANSWER_IS_TRUE = "com.example.shouhei.geoquiz.answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = "com.example.shouhei.geoquiz.answer_shown";
  private boolean mAnswerIsTrue;
  private TextView mAnswerTextView;
  private Button mShowAnswerButton;

  public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
    Intent intent = new Intent(packageContext, CheatActivity.class);
    intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    return intent;
  }

  public static boolean wasAnswerShown(Intent result) {
      return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called");
    setContentView(R.layout.activity_cheat);
    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

    mAnswerTextView = findViewById(R.id.answer_text_view);

    mShowAnswerButton = findViewById(R.id.show_answer_button);
    mShowAnswerButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (mAnswerIsTrue) {
              mAnswerTextView.setText(R.string.true_button);
            } else {
              mAnswerTextView.setText(R.string.false_button);
            }
            setAnswerShownResult(true);
          }
        });
  }

  private void setAnswerShownResult(boolean isAnswerShown) {
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }
}
