package com.example.lorin.coretest.canvas;

import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by sunyun on 16/6/10.
 */
public class CircularProgressBar extends View {

  private Paint paint;

  private float ring1Offset;
  private float ring1Length;
  private float ring2Offset;
  private float ring2Length;
  private float ring3Offset;
  private float ring3Length;

  private boolean shouldStartAnimation;

  private ValueAnimator animator;

  public CircularProgressBar(Context context) {
    this(context, null);
  }

  public CircularProgressBar(Context context, AttributeSet attrs) {
    super(context, attrs);

    setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setStrokeWidth(7);

    setupAnimation();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawProgress(canvas);

    if (shouldStartAnimation) {
      animator.start();
      shouldStartAnimation = false;
    }
  }

  private void drawProgress(Canvas canvas) {
    float cx = canvas.getWidth() / 2;
    float cy = canvas.getHeight() / 2;
    float radius = ScreenUtils.dip2px(getContext(), 20);

    //ring green
    paint.setColor(0xff9cdb6d);
    float[] intervals = new float[]{ring3Length, (float) (2 * Math.PI * radius - ring3Length)};
    paint.setPathEffect(new DashPathEffect(intervals, ring3Offset));
    canvas.drawCircle(cx, cy, radius, paint);

    // ring blue
    paint.setColor(0xffffd629);
    intervals = new float[]{ring2Length, (float) (2 * Math.PI * radius - ring2Length)};
    paint.setPathEffect(new DashPathEffect(intervals, ring2Offset));
    canvas.drawCircle(cx, cy, radius, paint);

    // ring yellow
    paint.setColor(0xff1686e7);
    intervals = new float[]{ring1Length, (float) (2 * Math.PI * radius - ring1Length)};
    paint.setPathEffect(new DashPathEffect(intervals, ring1Offset));
    canvas.drawCircle(cx, cy, radius, paint);
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int dw = ScreenUtils.dip2px(getContext(), 50);
    int dh = ScreenUtils.dip2px(getContext(), 50);

    setMeasuredDimension(resolveSizeAndState(dw, widthMeasureSpec, 0),
        resolveSizeAndState(dh, heightMeasureSpec, 0));
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);

    if (visibility == GONE || visibility == INVISIBLE) {
      stopAnimation();
    } else {
      startAnimation();
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    startAnimation();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stopAnimation();
  }

  private void setupAnimation() {
    TypeEvaluator evaluator = new TypeEvaluator() {
      @Override
      public Object evaluate(float fraction, Object startValue, Object endValue) {
        float[] startArray = (float[]) startValue;
        float[] endArray = (float[]) endValue;
        return new float[]{
            (startArray[0] + fraction * (endArray[0] - startArray[0])),
            (startArray[1] + fraction * (endArray[1] - startArray[1]))
        };
      }
    };

    float[] fractions;
    float[][] values;
    Keyframe[] kfs;

    // ring blue
    int blueLong = 51;
    fractions = new float[]{0f, 0.25f, 0.3f, 0.45f, 0.5f, 0.6f, 0.68f, 0.75f, 0.76f, 0.86f, 1f};
    values = new float[][]{{36, blueLong}, {63, blueLong}, {68, blueLong}, {100, blueLong},
        {126, blueLong}, {236, blueLong},
        {377, blueLong}, {565, blueLong}, {608, blueLong}, {628, blueLong}, {789, blueLong}};
    kfs = new Keyframe[fractions.length];
    for (int i = 0; i < fractions.length; i++) {
      kfs[i] = Keyframe.ofObject(fractions[i], values[i]);
    }
    PropertyValuesHolder ring1ValuesHolder = PropertyValuesHolder.ofKeyframe("ring1", kfs);
    ring1ValuesHolder.setEvaluator(evaluator);
    // ring yellow
    fractions = new float[]{0f, 0.3f, 0.43f, 0.45f, 0.5f, 0.6f, 0.75f, 0.76f, 0.86f, 0.89f, 1f};
    values = new float[][]{{-32, 11}, {63, 11}, {112, 51}, {126, 51}, {161, 51}, {281, 51},
        {554, 51}, {597, 51}, {628, 51}, {620, 11}, {721, 11}};
    kfs = new Keyframe[fractions.length];
    for (int i = 0; i < fractions.length; i++) {
      kfs[i] = Keyframe.ofObject(fractions[i], values[i]);
    }
    PropertyValuesHolder ring2ValuesHolder = PropertyValuesHolder.ofKeyframe("ring2", kfs);
    ring2ValuesHolder.setEvaluator(evaluator);

    // ring green
    fractions = new float[]{0f, 0.3f, 0.45f, 0.5f, 0.6f, 0.75f, 0.76f, 0.86f, 0.89f, 1f};
    values = new float[][]{{-53, 11}, {52, 11}, {112, 51}, {151, 51}, {271, 51}, {543, 51},
        {586, 51}, {628, 51}, {610, 11}, {700, 11}};
    kfs = new Keyframe[fractions.length];
    for (int i = 0; i < fractions.length; i++) {
      kfs[i] = Keyframe.ofObject(fractions[i], values[i]);
    }
    configAnimator(kfs, evaluator, ring1ValuesHolder, ring2ValuesHolder);
  }

  void configAnimator(Keyframe[] kfs, TypeEvaluator evaluator,
      PropertyValuesHolder ring1ValuesHolder, PropertyValuesHolder ring2ValuesHolder) {
    PropertyValuesHolder ring3ValuesHolder = PropertyValuesHolder.ofKeyframe("ring3", kfs);
    ring3ValuesHolder.setEvaluator(evaluator);

    animator = ValueAnimator
        .ofPropertyValuesHolder(ring1ValuesHolder, ring2ValuesHolder, ring3ValuesHolder);
    animator.setInterpolator(new LinearInterpolator());
    animator.setDuration(2000);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float[] value1 = (float[]) animation.getAnimatedValue("ring1");
        ring1Offset = ScreenUtils.dip2px(getContext(), value1[0] / 2);
        ring1Length = ScreenUtils.dip2px(getContext(), value1[1] / 2);

        float[] value2 = (float[]) animation.getAnimatedValue("ring2");
        ring2Offset = ScreenUtils.dip2px(getContext(), value2[0] / 2);
        ring2Length = ScreenUtils.dip2px(getContext(), value2[1] / 2);

        float[] value3 = (float[]) animation.getAnimatedValue("ring3");
        ring3Offset = ScreenUtils.dip2px(getContext(), value3[0] / 2);
        ring3Length = ScreenUtils.dip2px(getContext(), value3[1] / 2);

        invalidate();
      }
    });
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setRepeatMode(ValueAnimator.RESTART);
  }

  void startAnimation() {
    if (getVisibility() != VISIBLE) {
      return;
    }

    shouldStartAnimation = true;
    postInvalidate();
  }

  void stopAnimation() {
    animator.cancel();
    shouldStartAnimation = false;
    postInvalidate();
  }
}
