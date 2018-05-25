package com.example.shouhei.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";
  private static final String KEY_IS_ANSWERED = "isAnswered";
  private Button mTrueButton;
  private Button mFalseButton;
  private ImageButton mNextButton;
  private ImageButton mPrevButton;
  private TextView mQuestionTextView;
  private boolean mIsAnswered;

  private Question[] mQuestionBank =
      new Question[] {
        new Question(R.string.question_australia, true),
        new Question(R.string.question_oceans, false),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
      };

  private int mCurrentIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called");
    setContentView(R.layout.activity_quiz);

    mTrueButton = findViewById(R.id.true_button);
    mFalseButton = findViewById(R.id.false_button);

    if (savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
      mIsAnswered = savedInstanceState.getBoolean(KEY_IS_ANSWERED);
    }

    // This is connect
    mQuestionTextView = findViewById(R.id.questionTextView);
    int question = mQuestionBank[mCurrentIndex].getmTextResId();
    mQuestionTextView.setText(question);

    mTrueButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            checkAnswer(true);

            mIsAnswered = true;
            updateAnswerButton();
          }
        });

    mFalseButton.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            checkAnswer(false);

            mIsAnswered = true;
            updateAnswerButton();
          }
        });

    mNextButton = findViewById(R.id.next_button);
    mNextButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
            mIsAnswered = false;
            updateAnswerButton();
          }
        });

    mPrevButton = findViewById(R.id.previous_button);
    mPrevButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mCurrentIndex = (Math.abs(mCurrentIndex - 1)) % mQuestionBank.length;
            updateQuestion();
            mIsAnswered = false;
            updateAnswerButton();
          }
        });

    mQuestionTextView = findViewById(R.id.questionTextView);
    mQuestionTextView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
          }
        });

    updateQuestion();
    updateAnswerButton();
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
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSaveInstanceState");
    savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    savedInstanceState.putBoolean(KEY_IS_ANSWERED, mIsAnswered);
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

  private void updateQuestion() {
    int question = mQuestionBank[mCurrentIndex].getmTextResId();
    mQuestionTextView.setText(question);
  }

  private void updateAnswerButton() {
    if (mIsAnswered) {
      mTrueButton.setEnabled(false);
      mFalseButton.setEnabled(false);
    } else {
      mTrueButton.setEnabled(true);
      mFalseButton.setEnabled(true);
    }
  }

  private void checkAnswer(boolean userPressedTrue) {
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();

    int messageResId = 0;

    if (userPressedTrue == answerIsTrue) {
      messageResId = R.string.correct_toast;
    } else {
      messageResId = R.string.incorrect_toast;
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
  }
}
