package com.example.shouhei.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class QuizActivity extends AppCompatActivity {

  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";
  private static final String KEY_IS_ANSWERED_ARRAY = "IsAnsweredArray";
  private static final String KEY_ANSWER_SHEET = "AnswerSheet";
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

  private boolean[] mIsAnsweredArray = new boolean[mQuestionBank.length];
  private boolean[] mAnswerSheet = new boolean[mQuestionBank.length];

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
      mIsAnsweredArray = savedInstanceState.getBooleanArray(KEY_IS_ANSWERED_ARRAY);
      mAnswerSheet = savedInstanceState.getBooleanArray(KEY_ANSWER_SHEET);
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
            updateQuestionCheckArray();

            updateAnswerButton();
            checkComplete();
          }
        });

    mFalseButton.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            checkAnswer(false);
            updateQuestionCheckArray();

            updateAnswerButton();
            checkComplete();
          }
        });

    mNextButton = findViewById(R.id.next_button);
    mNextButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            Log.d(TAG, "Go to next " + String.valueOf(mCurrentIndex));
            updateQuestion();
            updateAnswerButton();
          }
        });

    mPrevButton = findViewById(R.id.previous_button);
    mPrevButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //            int tempIndex = mQuestionBank.length - mCurrentIndex;
            //            if (tempIndex == mQuestionBank.length) {
            //              tempIndex = 0;
            //            }
            //            int tempIndex2 = (tempIndex + 1) % mQuestionBank.length;
            //            if (tempIndex2 != 0) {
            //              mCurrentIndex = mQuestionBank.length - tempIndex2;
            //            } else {
            //              mCurrentIndex = tempIndex2;
            //            }
            if (mCurrentIndex == 0) {
              mCurrentIndex = 6;
            }
            mCurrentIndex = (Math.abs(mCurrentIndex - 1)) % mQuestionBank.length;
            Log.d(TAG, "Go to prev " + String.valueOf(mCurrentIndex));
            updateQuestion();
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
    savedInstanceState.putBooleanArray(KEY_IS_ANSWERED_ARRAY, mIsAnsweredArray);
    savedInstanceState.putBooleanArray(KEY_ANSWER_SHEET, mAnswerSheet);
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
    if (mIsAnsweredArray[mCurrentIndex]) {
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
      mAnswerSheet[mCurrentIndex] = true;
      messageResId = R.string.correct_toast;
    } else {
      messageResId = R.string.incorrect_toast;
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
  }

  private void updateQuestionCheckArray() {
    mIsAnsweredArray[mCurrentIndex] = true;
  }

  private void checkComplete() {

    if (isCompleted()) {

      String message = getString(R.string.complete_toast);
      String res = getResultAsPercentage();

      Toast toast = Toast.makeText(QuizActivity.this, message + " " + res, Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.TOP, 0, 0);
      toast.show();
    }
  }

  private boolean isCompleted() {
    for (boolean b : mIsAnsweredArray) {
      if (!b) return false;
    }
    return true;
  }

  private int getNumOfCorrect() {
    int cnt = 0;
    for (boolean b : mAnswerSheet) {
      if (b) cnt++;
    }
    return cnt;
  }

  private String getResultAsPercentage() {
    BigDecimal numOfCorrect = BigDecimal.valueOf(getNumOfCorrect());
    BigDecimal numOfQuestion = BigDecimal.valueOf(mQuestionBank.length);
    BigDecimal res = numOfCorrect.divide(numOfQuestion, 2, BigDecimal.ROUND_HALF_UP);
    Log.d(TAG, "Correct is " + String.valueOf(numOfCorrect));
    Log.d(TAG, "Question is " + String.valueOf(numOfQuestion));
    Log.d(TAG, "Result is " + String.valueOf(res));
    NumberFormat formatPer = NumberFormat.getPercentInstance();
    return formatPer.format(res.doubleValue());
  }
}
