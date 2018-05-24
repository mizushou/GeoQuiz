package com.example.shouhei.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

  private Button mTrueButton;
  private Button mFalseButton;
  private ImageButton mNextButton;
  private ImageButton mPrevButton;
  private TextView mQuestionTextView;

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
    setContentView(R.layout.activity_quiz);

    // This is connect
    mQuestionTextView = findViewById(R.id.questionTextView);
    int question = mQuestionBank[mCurrentIndex].getmTextResId();
    mQuestionTextView.setText(question);

    mTrueButton = findViewById(R.id.true_button);
    mFalseButton = findViewById(R.id.false_button);

    mTrueButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            checkAnswer(true);
          }
        });

    mFalseButton.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            checkAnswer(false);
          }
        });

    mNextButton = findViewById(R.id.next_button);
    mNextButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
          }
        });

    mPrevButton = findViewById(R.id.previous_button);
    mPrevButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mCurrentIndex = (Math.abs(mCurrentIndex - 1)) % mQuestionBank.length;
            updateQuestion();
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
  }

  private void updateQuestion() {
    int question = mQuestionBank[mCurrentIndex].getmTextResId();
    mQuestionTextView.setText(question);
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
