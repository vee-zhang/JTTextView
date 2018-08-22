package com.william.jttextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;


public class JTTextView extends EditText {

    private static final int ACTION_NONE = -1;
    public static final int ACTION_START = 0;
    public static final int ACTION_TOP = 1;
    public static final int ACTION_END = 2;
    public static final int ACTION_BOTTOM = 3;

    private Drawable[] currentDrawables;
    private Drawable[] actionDrawables = new Drawable[4];
    private Drawable[] cacheDrawable = new Drawable[4];

    /**
     * 自动复位模式   true：开    false：关
     */
    private boolean autoReset;

    /**
     * 自动显示/隐藏模式   true：开    false：关
     */
    private boolean autoDisplay;

    public boolean isUnderLine() {
        return underLine;
    }

    public void setUnderLine(boolean underLine) {
        this.underLine = underLine;
    }

    public boolean isBottomLine() {
        return bottomLine;
    }

    public void setBottomLine(boolean bottomLine) {
        this.bottomLine = bottomLine;
    }

    public int getBottomLineColor() {
        return bottomLineColor;
    }

    public void setBottomLineColor(int bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    public int getBottomLineFocusColor() {
        return bottomLineFocusColor;
    }

    public void setBottomLineFocusColor(int bottomLineFocusColor) {
        this.bottomLineFocusColor = bottomLineFocusColor;
    }

    public float getBottomLineStroke() {
        return bottomLineStroke;
    }

    public void setBottomLineStroke(float bottomLineStroke) {
        this.bottomLineStroke = bottomLineStroke;
    }

    public float getBottomLineFocusStroke() {
        return bottomLineFocusStroke;
    }

    public void setBottomLineFocusStroke(float bottomLineFocusStroke) {
        this.bottomLineFocusStroke = bottomLineFocusStroke;
    }

    public int getBottomLineStyle() {
        return bottomLineStyle;
    }

    public void setBottomLineStyle(int bottomLineStyle) {
        this.bottomLineStyle = bottomLineStyle;
    }

    public int getBottomLineFocusStyle() {
        return bottomLineFocusStyle;
    }

    public void setBottomLineFocusStyle(int bottomLineFocusStyle) {
        this.bottomLineFocusStyle = bottomLineFocusStyle;
    }

    /**
     * 文字是否带下划线（颜色随文字颜色）
     */
    private boolean underLine;

    /**
     * 是否带底部线
     */
    private boolean bottomLine;

    /**
     * 底部线[聚焦时]的颜色，默认随主题颜色
     */
    private int bottomLineColor, bottomLineFocusColor;

    /**
     * 底部线[聚焦时]的粗度
     */
    private float bottomLineStroke, bottomLineFocusStroke;


    /**
     * 底部线(聚焦时）的样式
     */
    private int bottomLineStyle, bottomLineFocusStyle;

    /**
     * 开闭状态 true:开  false:关
     */
    private boolean[] autoResetState = new boolean[4];

    private int actionId;

    private Paint mPaint;

    public Drawable[] getActionDrawables() {
        return actionDrawables;
    }

    /**
     * 设置触发时图片
     *
     * @param leftDrawable   左图标
     * @param topDrawable    上图标
     * @param rightDrawable  右图标
     * @param bottomDrawable 下图标
     */
    public void setActionDrawables(Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable) {
        this.actionDrawables[0] = leftDrawable;
        this.actionDrawables[1] = topDrawable;
        this.actionDrawables[2] = rightDrawable;
        this.actionDrawables[3] = bottomDrawable;
    }

    /**
     * 是否随手指抬起重置
     */
    public boolean isAutoReset() {
        return autoReset;
    }

    /**
     * 设置是否随手指抬起重置
     * 手指点击事触发，抬起时失效
     *
     * @param autoReset true:是  false:否
     */
    public void setAutoReset(boolean autoReset) {
        this.autoReset = autoReset;
    }

    /**
     * 是否自动显示和隐藏
     *
     * @return true：是 false：否
     */
    public boolean isAutoDisplay() {
        return autoDisplay;
    }

    /**
     * 设置是否自动显示和隐藏
     * 成为焦点时自动显示，失去焦点时自动隐藏drawable
     *
     * @param autoDisplay true:是  false:否
     */
    public void setAutoDisplay(boolean autoDisplay) {
        this.autoDisplay = autoDisplay;
    }

    public void setOnDrawableClickListener(OnDrawableClickListener listener) {
        this.listener = listener;
    }

    private OnDrawableClickListener listener;

    public JTTextView(Context context) {
        this(context, null);
    }

    public JTTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public JTTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setBackgroundDrawable(null);
        currentDrawables = getCompoundDrawables();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.JTTextView);

        /*获取图标*/
        actionDrawables[0] = a.getDrawable(R.styleable.JTTextView_drawableStart_action);
        actionDrawables[1] = a.getDrawable(R.styleable.JTTextView_drawableTop_action);
        actionDrawables[2] = a.getDrawable(R.styleable.JTTextView_drawableEnd_action);
        actionDrawables[3] = a.getDrawable(R.styleable.JTTextView_drawableBottom_action);

        /*获取配置*/
        autoReset = a.getBoolean(R.styleable.JTTextView_autoReset, false);
        autoDisplay = a.getBoolean(R.styleable.JTTextView_autoDisplay, false);
        underLine = a.getBoolean(R.styleable.JTTextView_underLine, false);
        bottomLine = a.getBoolean(R.styleable.JTTextView_bottomLine, false);

        if (underLine) {
            getMPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }

        if (bottomLine) {
            getMPaint();
            bottomLineColor = a.getColor(R.styleable.JTTextView_bottomLineColor, mPaint.getColor());
            bottomLineFocusColor = a.getColor(R.styleable.JTTextView_bottomLineFocusColor, bottomLineColor);
            bottomLineStroke = a.getFloat(R.styleable.JTTextView_bottomLineStroke, 1f);
            bottomLineFocusStroke = a.getFloat(R.styleable.JTTextView_bottomLineFocusStroke, bottomLineStroke);
            bottomLineStyle = a.getInt(R.styleable.JTTextView_bottomLineStyle, 0);
            bottomLineFocusStyle = a.getInt(R.styleable.JTTextView_bottomLineStyle, bottomLineStyle);
        }
        a.recycle();
    }

    /**
     * 初始化画笔
     *
     * @return 画笔
     */
    private Paint getMPaint() {
        if (mPaint == null) {
            mPaint = getPaint();
        }
        return mPaint;
    }

    private int isDrawableEvent(MotionEvent event) {

        int width = getWidth();
        int height = getHeight();

        float eventX = event.getX();
        float eventY = event.getY();

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int CompoundPaddingLeft = getCompoundPaddingLeft();
        int CompoundPaddingTop = getCompoundPaddingTop();
        int CompoundPaddingRight = getCompoundPaddingRight();
        int CompoundPaddingBottom = getCompoundPaddingBottom();

        int CompoundDrawablePadding = getCompoundDrawablePadding();

        boolean isDrawableLeftEvent =
                eventX > paddingLeft &&
                        eventX < CompoundPaddingLeft - CompoundDrawablePadding &&
                        eventY > CompoundPaddingTop &&
                        eventY < height - CompoundPaddingBottom;

        boolean isDrawableTopEvent =
                eventX > CompoundPaddingLeft &&
                        eventX < width - CompoundPaddingRight &&
                        eventY > paddingTop &&
                        eventY < CompoundPaddingTop - CompoundDrawablePadding;

        boolean isDrawableRightEvent =
                eventX > width - CompoundPaddingRight + CompoundDrawablePadding &&
                        eventX < width - paddingRight &&
                        eventY > CompoundPaddingTop &&
                        eventY < height - CompoundPaddingBottom;

        boolean isDrawableBottomEvent =
                eventX > CompoundPaddingLeft &&
                        eventX < width - CompoundPaddingRight &&
                        eventY > height - CompoundPaddingBottom + CompoundDrawablePadding &&
                        eventY < height - paddingBottom;

        if (isDrawableLeftEvent) {
            return ACTION_START;
        } else if (isDrawableTopEvent) {
            return ACTION_TOP;
        } else if (isDrawableRightEvent) {
            return ACTION_END;
        } else if (isDrawableBottomEvent) {
            return ACTION_BOTTOM;
        } else {
            return ACTION_NONE;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_UP) {
            performClick();
        }

        if (eventAction == MotionEvent.ACTION_DOWN) {
            actionId = isDrawableEvent(event);
        }

        if (actionId != ACTION_NONE) {
            if (autoReset) {
                onTouchEventByAutoReset(eventAction, actionId);
            } else {
                onTouchEvent(eventAction, actionId);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 普通的touch事件
     *
     * @param eventAction 事件id
     * @param actionId    图标方向
     */
    private void onTouchEvent(int eventAction, int actionId) {
        if (eventAction == MotionEvent.ACTION_UP) {
            autoResetState[actionId] = !autoResetState[actionId];
            if (autoResetState[actionId]) {
                cacheDrawable[actionId] = currentDrawables[actionId];
                currentDrawables[actionId] = actionDrawables[actionId];
            } else {
                currentDrawables[actionId] = cacheDrawable[actionId];
            }
            setCompoundDrawablesWithIntrinsicBounds(currentDrawables[0], currentDrawables[1], currentDrawables[2], currentDrawables[3]);
            callBack(autoResetState[actionId]);
            clearFocus();
            requestFocus();
        }
    }

    /**
     * 自复位模式下的touch事件
     *
     * @param eventAction 事件id
     * @param actionId    图标方向
     */
    private void onTouchEventByAutoReset(int eventAction, int actionId) {

        if (eventAction == MotionEvent.ACTION_DOWN) {
            cacheDrawable[actionId] = currentDrawables[actionId];
            currentDrawables[actionId] = actionDrawables[actionId];
            clearFocus();
            callBack(true);
        } else if (eventAction == MotionEvent.ACTION_UP) {
            currentDrawables[actionId] = cacheDrawable[actionId];
            requestFocus();
            callBack(false);
        }
        setCompoundDrawablesWithIntrinsicBounds(currentDrawables[0], currentDrawables[1], currentDrawables[2], currentDrawables[3]);
    }

    private void callBack(boolean b){
        if (listener != null) {
            listener.onDrawableClickListener(b, this, actionId, getText());
        }
    }

    private boolean focus;

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        focus = focused;
        if (!autoDisplay) {
            return;
        }

        if (focused) {
            setCompoundDrawablesWithIntrinsicBounds(currentDrawables[0], currentDrawables[1], currentDrawables[2], currentDrawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int lineHeight;
    private int lineStart;
    private int lineEnd;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        lineHeight = getHeight() - getCompoundPaddingBottom();
        lineStart = getCompoundPaddingLeft();
        lineEnd = getWidth() - getCompoundPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!bottomLine) {
            return;
        }
        if (focus) {
            drawFocus(canvas);
        } else {
            drawNormal(canvas);
        }
    }

    private void drawNormal(Canvas canvas) {
        mPaint.setStrokeWidth(bottomLineStroke);
        mPaint.setColor(bottomLineColor);
        switch (bottomLineStyle) {
            case 0:
                canvas.drawLine(lineStart, lineHeight, lineEnd, lineHeight, mPaint);
                break;
            case 1:
                canvas.drawLine(lineStart, lineHeight - 10, lineStart, lineHeight, mPaint);
                canvas.drawLine(lineStart, lineHeight, lineEnd, lineHeight, mPaint);
                canvas.drawLine(lineEnd, lineHeight, lineEnd, lineHeight - 10, mPaint);
                break;
        }
    }

    private void drawFocus(Canvas canvas) {
        mPaint.setStrokeWidth(bottomLineFocusStroke);
        mPaint.setColor(bottomLineFocusColor);
        switch (bottomLineFocusStyle) {
            case 0:
                canvas.drawLine(lineStart, lineHeight, lineEnd, lineHeight, mPaint);
                break;
            case 1:
                canvas.drawLine(lineStart, lineHeight - 10, lineStart, lineHeight, mPaint);
                canvas.drawLine(lineStart, lineHeight, lineEnd, lineHeight, mPaint);
                canvas.drawLine(lineEnd, lineHeight, lineEnd, lineHeight - 10, mPaint);
                break;
        }
    }

    public interface OnDrawableClickListener {
        void onDrawableClickListener(Boolean switchState, JTTextView view, int actionId, Editable currentText);
    }
}
